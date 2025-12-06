package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

/**
 * Comprehensive end-to-end integration test for Sprint 2.
 * Tests the complete workflow: User creates alert -> Alert persists to DB -> 
 * AlertManager monitors -> Condition triggers -> Notification sent and persisted.
 * 
 * Also validates WorkflowState progression through the 3 steps.
 */
public class EndToEndWorkflowTest {

    private String jdbcUrl;
    private AlertJdbcDAO alertDAO;
    private AlertNotificationJdbcDAO notifDAO;
    private WorkflowStateJdbcDAO workflowDAO;
    private UserDaoImpl userDAO;
    private MockMarketDataAPI marketAPI;
    private NotificationService notifService;
    private AlertManager alertManager;

    @BeforeEach
    void setUp() throws Exception {
        // Setup in-memory H2 database
        jdbcUrl = "jdbc:h2:mem:e2e_test_" + System.currentTimeMillis() + ";DB_CLOSE_DELAY=-1";
        DbTestUtils.createSchema(jdbcUrl);
        
        // Initialize DAOs
        alertDAO = new AlertJdbcDAO(jdbcUrl);
        notifDAO = new AlertNotificationJdbcDAO(jdbcUrl);
        workflowDAO = new WorkflowStateJdbcDAO(jdbcUrl);
        userDAO = new UserDaoImpl(jdbcUrl);
        
        // Initialize services
        marketAPI = new MockMarketDataAPI();
        notifService = new NotificationService();
        
        // Initialize AlertManager with workflow support
        alertManager = new AlertManager(alertDAO, notifDAO, workflowDAO, marketAPI, notifService);
    }

    @Test
    void completeWorkflow_userCreatesAlert_triggersAndNotifies() throws Exception {
        // Step 1: Create a user
        User user = new User("u-123", "testuser", "hash123", "test@example.com", "555-1234");
        userDAO.save(user);
        
        // Verify user was persisted
        assertTrue(userDAO.findByUsername("testuser").isPresent(), "User should be saved");
        
        // Step 2: Create an alert through AlertManager
        AlertForm form = new AlertForm("AAPL", 150.0, "above", List.of("email"));
        Stock stock = new Stock("AAPL", "Apple Inc.", 140.0);
        
        Alert alert = alertManager.createAlert(user, stock, form);
        assertNotNull(alert, "Alert should be created");
        assertEquals("AAPL", alert.getTicker());
        
        // Verify workflow state is ACTIVE after creation
        WorkflowState workflow = alert.getWorkflowState();
        assertNotNull(workflow, "WorkflowState should exist");
        assertEquals(WorkflowState.State.ACTIVE, workflow.getCurrentState());
        assertEquals(2, workflow.getCurrentStepNumber(), "Should be at step 2 (ACTIVE)");
        
        // Verify workflow state was persisted
        assertTrue(workflowDAO.findByAlertId(alert.getAlertID()).isPresent(), 
            "WorkflowState should be persisted");
        
        // Step 3: Set market price to trigger the alert
        marketAPI.setPrice("AAPL", 155.0); // Above target of 150
        
        // Step 4: Load alerts and check once
        alertManager.loadActiveAlertsFromDAO();
        alertManager.checkAllAlertsOnce();
        
        // Step 5: Verify alert was triggered and status updated in DB
        List<Alert> allAlerts = alertDAO.getAll();
        Alert triggeredAlert = allAlerts.stream()
            .filter(a -> a.getAlertID().equals(alert.getAlertID()))
            .findFirst()
            .orElse(null);
        
        assertNotNull(triggeredAlert, "Alert should still exist in DB");
        assertEquals("triggered", triggeredAlert.getStatus(), "Alert should be triggered");
        
        // Step 6: Verify workflow progressed to TRIGGERED (Step 3)
        WorkflowState finalWorkflow = workflowDAO.findByAlertId(alert.getAlertID()).orElse(null);
        assertNotNull(finalWorkflow, "Workflow state should be persisted");
        assertEquals(WorkflowState.State.TRIGGERED, finalWorkflow.getCurrentState());
        assertEquals(3, finalWorkflow.getCurrentStepNumber(), "Should be at step 3 (TRIGGERED)");
        
        // Step 7: Verify notification was created and persisted
        List<Notification> notifications = notifDAO.listNotifications();
        assertEquals(1, notifications.size(), "One notification should be created");
        
        Notification notif = notifications.get(0);
        assertTrue(notif.getMessage().contains("AAPL"), "Notification should mention ticker");
        assertTrue(notif.getMessage().contains("150"), "Notification should mention target price");
        assertEquals("SENT", notif.getStatus(), "Notification should be marked as sent");
    }

    @Test
    void workflowState_transitionsAreValidated() {
        // Create alert in CREATED state
        Alert alert = new Alert("test-1", "TSLA", 200.0, "above", "email", "created");
        WorkflowState workflow = alert.getWorkflowState();
        
        assertEquals(WorkflowState.State.CREATED, workflow.getCurrentState());
        assertEquals(1, workflow.getCurrentStepNumber());
        
        // Valid transition: CREATED -> ACTIVE
        alert.updateStatus("active");
        assertEquals(WorkflowState.State.ACTIVE, workflow.getCurrentState());
        assertEquals(2, workflow.getCurrentStepNumber());
        
        // Valid transition: ACTIVE -> TRIGGERED
        alert.updateStatus("triggered");
        assertEquals(WorkflowState.State.TRIGGERED, workflow.getCurrentState());
        assertEquals(3, workflow.getCurrentStepNumber());
        
        // Terminal state - no further transitions allowed
        assertEquals("Step 3 of 3: Alert triggered, notification sent", 
            workflow.getProgressDescription());
    }

    @Test
    void workflowState_invalidTransitionsAreRejected() {
        WorkflowState workflow = new WorkflowState("test-invalid");
        
        // Cannot go directly from CREATED to TRIGGERED
        assertThrows(IllegalStateException.class, () -> {
            workflow.transitionTo(WorkflowState.State.TRIGGERED, "Invalid jump");
        });
        
        // Transition to ACTIVE first
        workflow.transitionTo(WorkflowState.State.ACTIVE, "Valid transition");
        
        // Cannot go back to CREATED
        assertThrows(IllegalStateException.class, () -> {
            workflow.transitionTo(WorkflowState.State.CREATED, "Cannot go backwards");
        });
        
        // Transition to TRIGGERED
        workflow.transitionTo(WorkflowState.State.TRIGGERED, "Complete");
        
        // Terminal state - no further transitions
        assertThrows(IllegalStateException.class, () -> {
            workflow.transitionTo(WorkflowState.State.ACTIVE, "Cannot leave terminal state");
        });
    }

    @Test
    void cancelledAlert_workflowTransitionsToCancelled() {
        Alert alert = new Alert("cancel-1", "GOOGL", 100.0, "below", "sms", "created");
        WorkflowState workflow = alert.getWorkflowState();
        
        // User cancels before activation
        alert.updateStatus("cancelled");
        
        assertEquals(WorkflowState.State.CANCELLED, workflow.getCurrentState());
        assertEquals(0, workflow.getCurrentStepNumber(), "Cancelled alerts are not in normal flow");
        assertEquals("Alert cancelled by user", workflow.getProgressDescription());
    }

    @Test
    void multipleAlerts_differentWorkflows() throws Exception {
        // Create multiple alerts at different stages
        Alert alert1 = new Alert("multi-1", "MSFT", 300.0, "above", "email", "created");
        Alert alert2 = new Alert("multi-2", "AMZN", 120.0, "below", "email", "created");
        Alert alert3 = new Alert("multi-3", "NVDA", 500.0, "above", "push", "created");
        
        // Progress them to different states
        alert1.updateStatus("active");
        alert2.updateStatus("active");
        alert2.updateStatus("triggered");
        alert3.updateStatus("cancelled");
        
        // Verify each has correct workflow state
        assertEquals(WorkflowState.State.ACTIVE, alert1.getWorkflowState().getCurrentState());
        assertEquals(2, alert1.getWorkflowState().getCurrentStepNumber());
        
        assertEquals(WorkflowState.State.TRIGGERED, alert2.getWorkflowState().getCurrentState());
        assertEquals(3, alert2.getWorkflowState().getCurrentStepNumber());
        
        assertEquals(WorkflowState.State.CANCELLED, alert3.getWorkflowState().getCurrentState());
        assertEquals(0, alert3.getWorkflowState().getCurrentStepNumber());
    }

    @Test
    void userPersistence_crudOperations() {
        // Create
        User user1 = new User("u-1", "alice", "hash1", "alice@test.com", "111-1111");
        userDAO.save(user1);
        
        // Read
        User retrieved = userDAO.findByUsername("alice").orElse(null);
        assertNotNull(retrieved);
        assertEquals("alice@test.com", retrieved.getEmail());
        
        // Update
        retrieved.setEmail("alice.new@test.com");
        userDAO.save(retrieved);
        
        User updated = userDAO.findByUsername("alice").orElse(null);
        assertEquals("alice.new@test.com", updated.getEmail());
        
        // Verify username uniqueness
        assertTrue(userDAO.usernameExists("alice"));
        assertFalse(userDAO.usernameExists("bob"));
    }
}

# Sprint 2 Review - Demo Script

**Sprint Goal:** Incorporate the workflow object to show progression through 3 steps, incorporate database and get working end-to-end

**Team:** Richmond & Giorgi  
**Duration:** 15-20 minutes

---

## Introduction (2 minutes)

"Welcome to the Sprint 2 Review for the Stock Alert Notification System. In this sprint, we focused on incorporating a workflow object to track alert progression through three steps, and completing database integration for a working end-to-end system."

### Sprint Goals Recap
1.  Implement WorkflowState to track alerts through CREATED → ACTIVE → TRIGGERED
2.  Complete database integration for all entities
3.  Implement User persistence layer
4.  Create comprehensive end-to-end tests
5.  Document email integration points (placeholder only)

---

## Demo Part 1: Workflow State Tracking (4 minutes)

### Show WorkflowState Class
**File:** `src/main/java/edu/gmu/cs321/WorkflowState.java`

"We created a WorkflowState class that tracks an alert's progression through three defined steps:"
- Step 1: CREATED - Alert has been created by user
- Step 2: ACTIVE - Alert is being monitored for price conditions
- Step 3: TRIGGERED - Alert condition met, notification sent

**Key Points to Highlight:**
- State enum with validation
- Transition validation prevents invalid jumps (e.g., CREATED → TRIGGERED)
- Timestamps for each state change
- Progress description methods for UI display

**Demo Code:**
```java
// Show state creation
WorkflowState workflow = new WorkflowState("alert-123");
// Step 1: CREATED
workflow.getCurrentStepNumber(); // Returns 1

// Valid transition
workflow.transitionTo(State.ACTIVE, "User activated alert");
workflow.getCurrentStepNumber(); // Returns 2

// Invalid transition (will throw exception)
// workflow.transitionTo(State.CREATED, "Cannot go back"); // IllegalStateException
```

---

## Demo Part 2: Database Integration (5 minutes)

### Show Database Schema
**File:** `src/main/java/edu/gmu/cs321/DbTestUtils.java`

"We expanded the database schema to include four tables with proper relationships:"

1. **users** - User account information
2. **alerts** - Stock alert configurations
3. **notifications** - Delivery attempts and results
4. **workflow_states** - Tracks alert progression

**Show Schema Code:**
```sql
users: user_id, username, password_hash, email, phone_number
alerts: alert_id, user_id (FK), ticker, target_price, trigger_condition, alert_type, status
notifications: notification_id, alert_id (FK), message, delivery_method, recipient_address, timestamp, status
workflow_states: alert_id (FK), current_state, created_at, activated_at, triggered_at, cancelled_at, transition_notes
```

### Show DAO Implementations

**UserDaoImpl** (`src/main/java/edu/gmu/cs321/UserDaoImpl.java`)
- JDBC implementation
- CRUD operations
- Username uniqueness enforcement

**WorkflowStateJdbcDAO** (`src/main/java/edu/gmu/cs321/WorkflowStateJdbcDAO.java`)
- Persist workflow transitions
- Reconstruct state from database

**Key Points:**
- All DAOs follow consistent pattern
- Use H2 in-memory database for testing
- Can switch to PostgreSQL/MySQL for production

---

## Demo Part 3: End-to-End Flow (6 minutes)

### Run Integration Test
**File:** `src/test/java/edu/gmu/cs321/EndToEndWorkflowTest.java`

"Let's walk through a complete user journey from alert creation to notification delivery."

**Demo Steps:**

1. **Create User**
   ```java
   User user = new User("u-123", "testuser", "hash", "test@example.com", "555-1234");
   userDAO.save(user);
   // Verify in database
   assertTrue(userDAO.findByUsername("testuser").isPresent());
   ```

2. **Create Alert** 
   ```java
   AlertForm form = new AlertForm("AAPL", 150.0, "above", List.of("email"));
   Alert alert = alertManager.createAlert(user, stock, form);
   // Check workflow state
   assertEquals(WorkflowState.State.ACTIVE, alert.getWorkflowState().getCurrentState());
   assertEquals(2, alert.getWorkflowState().getCurrentStepNumber()); // Step 2
   ```

3. **Trigger Alert**
   ```java
   marketAPI.setPrice("AAPL", 155.0); // Above target of 150
   alertManager.checkAllAlertsOnce();
   // Check workflow progressed
   WorkflowState finalWorkflow = workflowDAO.findByAlertId(alert.getAlertID()).get();
   assertEquals(WorkflowState.State.TRIGGERED, finalWorkflow.getCurrentState());
   assertEquals(3, finalWorkflow.getCurrentStepNumber()); // Step 3
   ```

4. **Verify Notification**
   ```java
   List<Notification> notifications = notifDAO.listNotifications();
   assertEquals(1, notifications.size());
   assertEquals("SENT", notifications.get(0).getStatus());
   ```

**Run the test and show it passing:**
```
> mvn test -Dtest=EndToEndWorkflowTest
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[SUCCESS]
```

---

## Demo Part 4: Email Integration Placeholder (3 minutes)

### Show NotificationService
**File:** `src/main/java/edu/gmu/cs321/NotificationService.java`

"As required, we documented where email/SMS/push integration would occur, but did not implement actual sending."

**Show Code Comments:**
```java
// EMAIL INTEGRATION PLACEHOLDER - Sprint 2
// This is where actual email/SMS/push notification sending would occur.
// For Sprint 2 demo purposes, we simulate the sending process.
//
// FUTURE IMPLEMENTATION (Sprint 3+):
// if ("email".equalsIgnoreCase(notification.getDeliveryMethod())) {
//     // JavaMail API example provided
// }
```

**Key Points:**
- Detailed code examples for JavaMail API (email)
- Examples for Twilio API (SMS)
- Examples for Firebase Cloud Messaging (push notifications)
- Current implementation simulates successful delivery
- All attempts logged to database regardless of outcome

**Show Current Output:**
```
=== NOTIFICATION SENT ===
Method: email
To: test@example.com
Message: Alert A-123: AAPL reached target 150.00 (current=155.00)
=========================
```

---

## Demo Part 5: AlertManager Scheduler (2 minutes)

### Show Scheduler Implementation
**File:** `src/main/java/edu/gmu/cs321/AlertManager.java`

"Issue #006 from Sprint 1 was already resolved. AlertManager uses ScheduledExecutorService instead of infinite loop."

**Key Features:**
- Configurable check interval (default: 60 seconds)
- Graceful startup and shutdown
- Non-blocking, runs in background
- Tested with AlertManagerSchedulerTest

**Show Code:**
```java
scheduler = Executors.newSingleThreadScheduledExecutor();
checkTask = scheduler.scheduleAtFixedRate(
    () -> checkAllAlertsOnce(),
    checkIntervalSeconds,
    checkIntervalSeconds,
    TimeUnit.SECONDS
);
```

---

## Summary & Metrics (2 minutes)

### What We Accomplished
 **Technical:**
- WorkflowState class with validated state transitions
- Complete database schema with 4 tables and foreign keys
- UserDaoImpl with JDBC persistence
- WorkflowStateJdbcDAO for tracking progression
- Comprehensive end-to-end integration tests (7 test methods)
- Email integration placeholders with detailed examples

 **Process:**
- 10 of 12 development tasks completed (83%)
- All core functionality working end-to-end
- 18 of 22 story points completed
- All tests passing

### Sprint Metrics
- **New Classes:** 5
- **Modified Classes:** 6
- **Test Coverage:** >80% for new code
- **Database Tables:** 4
- **Integration Tests:** 7 test methods, all passing

### Remaining Work (Carry-over to Sprint 3)
- Update class and component diagrams (in progress)
- Final documentation updates
- Sprint retrospective completion

---

## Questions & Discussion (3 minutes)

Open floor for questions from stakeholders/instructors.

---

## Appendix: Files to Show in Demo

### Created Files
1. `WorkflowState.java` - State tracking class
2. `WorkflowStateDAO.java` - DAO interface
3. `WorkflowStateJdbcDAO.java` - JDBC implementation
4. `UserDaoImpl.java` - User persistence
5. `EndToEndWorkflowTest.java` - Integration tests
6. `SPRINT2_STORIES.md` - User stories
7. `SPRINT2_BOARD.md` - Sprint board
8. `SPRINT2_RETROSPECTIVE.md` - Retrospective
9. `SPRINT2_COORDINATION_MEETINGS.md` - Meeting logs

### Modified Files
1. `DbTestUtils.java` - Expanded schema
2. `Alert.java` - Integrated WorkflowState
3. `AlertManager.java` - Added WorkflowStateDAO support
4. `NotificationService.java` - Email placeholders

---

**Demo Preparation Checklist:**
- [ ] Code compiles without errors
- [ ] All tests passing
- [ ] Database schema created successfully
- [ ] Demo environment set up
- [ ] Screen recording software ready
- [ ] Practice demo walkthrough
- [ ] Team members ready to present

---

_This script should guide a ~15-20 minute Sprint Review video demonstration_

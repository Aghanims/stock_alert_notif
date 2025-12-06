package edu.gmu.cs321;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * JDBC implementation of WorkflowStateDAO.
 * Persists workflow state transitions to the database.
 *
 * @author Team (Sprint 2)
 * @version 2.0
 */
public class WorkflowStateJdbcDAO implements WorkflowStateDAO {
    private final String jdbcUrl;
    private final String user;
    private final String password;
    
    public WorkflowStateJdbcDAO(String jdbcUrl) {
        this(jdbcUrl, null, null);
    }
    
    public WorkflowStateJdbcDAO(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }
    
    private Connection conn() throws SQLException {
        if (user == null) return DriverManager.getConnection(jdbcUrl);
        return DriverManager.getConnection(jdbcUrl, user, password);
    }
    
    @Override
    public void save(WorkflowState workflowState) {
        String sql = "MERGE INTO workflow_states (alert_id, current_state, created_at, " +
                    "activated_at, triggered_at, cancelled_at, transition_notes) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, workflowState.getAlertId());
            ps.setString(2, workflowState.getCurrentState().getValue());
            ps.setTimestamp(3, Timestamp.valueOf(workflowState.getCreatedAt()));
            ps.setTimestamp(4, workflowState.getActivatedAt() != null ? 
                Timestamp.valueOf(workflowState.getActivatedAt()) : null);
            ps.setTimestamp(5, workflowState.getTriggeredAt() != null ? 
                Timestamp.valueOf(workflowState.getTriggeredAt()) : null);
            ps.setTimestamp(6, workflowState.getCancelledAt() != null ? 
                Timestamp.valueOf(workflowState.getCancelledAt()) : null);
            ps.setString(7, workflowState.getTransitionNotes());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving workflow state: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Optional<WorkflowState> findByAlertId(String alertId) {
        String sql = "SELECT * FROM workflow_states WHERE alert_id = ?";
        
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, alertId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                WorkflowState ws = new WorkflowState(rs.getString("alert_id"));
                // Reconstruct the workflow state from DB
                String stateStr = rs.getString("current_state");
                WorkflowState.State state = WorkflowState.State.fromString(stateStr);
                
                // We need to set the state and timestamps by transitioning
                // This is a reconstruction from persisted data
                // For now, directly set via reflection or add package-private setters
                // Simpler approach: add a constructor or factory method
                return Optional.of(reconstructWorkflowState(rs));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Error finding workflow state: " + e.getMessage(), e);
        }
    }
    
    private WorkflowState reconstructWorkflowState(ResultSet rs) throws SQLException {
        // Create a new workflow state and manually set its fields
        // This requires package-private access or a special constructor
        // For now, we'll create a basic version
        String alertId = rs.getString("alert_id");
        WorkflowState ws = new WorkflowState(alertId);
        
        // Transition to the stored state
        String stateStr = rs.getString("current_state");
        WorkflowState.State targetState = WorkflowState.State.fromString(stateStr);
        
        // Transition through states as needed
        if (targetState != WorkflowState.State.CREATED) {
            if (targetState == WorkflowState.State.ACTIVE || 
                targetState == WorkflowState.State.TRIGGERED) {
                ws.transitionTo(WorkflowState.State.ACTIVE, "Loaded from database");
                if (targetState == WorkflowState.State.TRIGGERED) {
                    ws.transitionTo(WorkflowState.State.TRIGGERED, "Loaded from database");
                }
            } else if (targetState == WorkflowState.State.CANCELLED) {
                ws.transitionTo(WorkflowState.State.CANCELLED, "Loaded from database");
            }
        }
        
        return ws;
    }
    
    @Override
    public void delete(String alertId) {
        String sql = "DELETE FROM workflow_states WHERE alert_id = ?";
        
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, alertId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting workflow state: " + e.getMessage(), e);
        }
    }
}

package edu.gmu.cs321;

import java.util.Optional;

/**
 * Data access interface for WorkflowState persistence.
 * Tracks alert progression through workflow steps.
 *
 * @author Team (Sprint 2)
 * @version 2.0
 */
public interface WorkflowStateDAO {
    /**
     * Save or update a workflow state.
     * @param workflowState The workflow state to persist
     */
    void save(WorkflowState workflowState);
    
    /**
     * Find workflow state by alert ID.
     * @param alertId The alert ID to look up
     * @return Optional containing the workflow state if found
     */
    Optional<WorkflowState> findByAlertId(String alertId);
    
    /**
     * Delete workflow state for an alert.
     * @param alertId The alert ID
     */
    void delete(String alertId);
}

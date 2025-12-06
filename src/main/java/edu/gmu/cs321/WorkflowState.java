package edu.gmu.cs321;

import java.time.LocalDateTime;

/**
 * Represents the workflow state of an alert as it progresses through the system.
 * Tracks the three main steps: CREATED -> ACTIVE -> TRIGGERED
 * 
 * This class provides visibility into where an alert is in its lifecycle
 * and maintains timestamps for audit/tracking purposes.
 *
 * @author Team (Sprint 2)
 * @version 2.0
 */
public class WorkflowState {
    
    public enum State {
        CREATED("created"),      // Step 1: Alert has been created by user
        ACTIVE("active"),        // Step 2: Alert is being monitored
        TRIGGERED("triggered"),  // Step 3: Alert condition met, notification sent
        CANCELLED("cancelled");  // Terminal state: User cancelled
        
        private final String value;
        
        State(String value) {
            this.value = value;
        }
        
        public String getValue() {
            return value;
        }
        
        public static State fromString(String text) {
            for (State s : State.values()) {
                if (s.value.equalsIgnoreCase(text)) {
                    return s;
                }
            }
            throw new IllegalArgumentException("Unknown state: " + text);
        }
    }
    
    private String alertId;
    private State currentState;
    private LocalDateTime createdAt;
    private LocalDateTime activatedAt;
    private LocalDateTime triggeredAt;
    private LocalDateTime cancelledAt;
    private String transitionNotes;
    
    /**
     * Create a new workflow state for an alert, starting in CREATED state.
     */
    public WorkflowState(String alertId) {
        this.alertId = alertId;
        this.currentState = State.CREATED;
        this.createdAt = LocalDateTime.now();
        this.transitionNotes = "Alert created";
    }
    
    /**
     * Transition the workflow to the next state.
     * Validates that transitions follow allowed paths.
     */
    public void transitionTo(State newState, String notes) {
        // Validate transition
        if (!isValidTransition(currentState, newState)) {
            throw new IllegalStateException(
                String.format("Invalid transition from %s to %s", currentState, newState)
            );
        }
        
        // Update timestamp for the new state
        LocalDateTime now = LocalDateTime.now();
        switch (newState) {
            case ACTIVE:
                this.activatedAt = now;
                break;
            case TRIGGERED:
                this.triggeredAt = now;
                break;
            case CANCELLED:
                this.cancelledAt = now;
                break;
            default:
                break;
        }
        
        this.currentState = newState;
        this.transitionNotes = notes != null ? notes : "";
    }
    
    /**
     * Check if a state transition is valid.
     * Valid paths:
     *   CREATED -> ACTIVE -> TRIGGERED
     *   CREATED -> CANCELLED
     *   ACTIVE -> CANCELLED
     */
    private boolean isValidTransition(State from, State to) {
        if (from == to) return false; // No self-transitions
        
        switch (from) {
            case CREATED:
                return to == State.ACTIVE || to == State.CANCELLED;
            case ACTIVE:
                return to == State.TRIGGERED || to == State.CANCELLED;
            case TRIGGERED:
            case CANCELLED:
                return false; // Terminal states
            default:
                return false;
        }
    }
    
    /**
     * Get the current step number (1, 2, or 3) for display purposes.
     */
    public int getCurrentStepNumber() {
        switch (currentState) {
            case CREATED: return 1;
            case ACTIVE: return 2;
            case TRIGGERED: return 3;
            case CANCELLED: return 0; // Not in normal flow
            default: return 0;
        }
    }
    
    /**
     * Get a human-readable progress description.
     */
    public String getProgressDescription() {
        switch (currentState) {
            case CREATED:
                return "Step 1 of 3: Alert created, awaiting activation";
            case ACTIVE:
                return "Step 2 of 3: Alert active, monitoring market conditions";
            case TRIGGERED:
                return "Step 3 of 3: Alert triggered, notification sent";
            case CANCELLED:
                return "Alert cancelled by user";
            default:
                return "Unknown state";
        }
    }
    
    // Getters
    public String getAlertId() { return alertId; }
    public State getCurrentState() { return currentState; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getActivatedAt() { return activatedAt; }
    public LocalDateTime getTriggeredAt() { return triggeredAt; }
    public LocalDateTime getCancelledAt() { return cancelledAt; }
    public String getTransitionNotes() { return transitionNotes; }
    
    @Override
    public String toString() {
        return String.format("WorkflowState[alert=%s, state=%s, step=%d/3]",
            alertId, currentState, getCurrentStepNumber());
    }
}

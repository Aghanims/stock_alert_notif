package edu.gmu.cs321;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Alert domain object (TDD style).
 * Tests for create, update, and get methods with valid and invalid inputs.
 */
public class AlertTest {
    // Test creating Alert with valid input
    @Test
    void createAlert_withValidInput_shouldSucceed() {
    Alert alert = new Alert("A1", "TICK1", 100.0, "above", "email", "active");
        assertNotNull(alert);
        assertEquals("A1", alert.getAlertID());
        assertEquals(100.0, alert.getTargetPrice());
        assertEquals("above", alert.getTriggerCondition());
        assertEquals("email", alert.getAlertType());
        assertEquals("active", alert.getStatus());
    }

    // Test creating Alert with invalid input (e.g., null ID)
    @Test
    void createAlert_withInvalidInput_shouldFail() {
        // This test will fail until validation is implemented in Alert
    Alert alert = new Alert(null, null, -1.0, null, null, null);
        // Example: expect an exception or invalid state
        assertNull(alert.getAlertID(), "Alert ID should be null for invalid input");
        assertTrue(alert.getTargetPrice() < 0, "Target price should be negative for invalid input");
    }

    // Test updating Alert status with valid input
    @Test
    void updateAlertStatus_withValidInput_shouldSucceed() {
    Alert alert = new Alert("A2", "TICK2", 200.0, "below", "sms", "active");
        alert.updateStatus("triggered");
        assertEquals("triggered", alert.getStatus());
    }

    // Test updating Alert status with invalid input
    @Test
    void updateAlertStatus_withInvalidInput_shouldFail() {
    Alert alert = new Alert("A3", "TICK3", 300.0, "equal", "push", "active");
        assertThrows(IllegalArgumentException.class, () -> alert.updateStatus(null));
    }

    // Test getting Alert properties with valid input
    @Test
    void getAlertProperties_withValidInput_shouldReturnCorrectValues() {
    Alert alert = new Alert("A4", "TICK4", 400.0, "above", "email", "active");
        assertEquals("A4", alert.getAlertID(), "Should return correct alert ID");
        assertEquals(400.0, alert.getTargetPrice(), "Should return correct target price");
        assertEquals("above", alert.getTriggerCondition(), "Should return correct trigger condition");
        assertEquals("email", alert.getAlertType(), "Should return correct alert type");
        assertEquals("active", alert.getStatus(), "Should return correct status");
    }

    // Test getting Alert properties with invalid input
    @Test
    void getAlertProperties_withInvalidInput_shouldFail() {
        Alert alert = new Alert(null, null, Double.NaN, null, null, null);
        assertNull(alert.getAlertID(), "Should return null for invalid alert ID");
        assertTrue(Double.isNaN(alert.getTargetPrice()), "Should return NaN for invalid price");
        assertNull(alert.getTriggerCondition(), "Should return null for invalid trigger condition");
        assertNull(alert.getAlertType(), "Should return null for invalid alert type");
        assertNull(alert.getStatus(), "Should return null for invalid status");
    }
}

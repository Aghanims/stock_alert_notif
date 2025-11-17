package edu.gmu.cs321;

import java.time.LocalDateTime;

/**
 * Represents a single notification sent to a user.
 * This object is created when an alert is triggered.
 *
 * @author Giorgi
 * @version 1.0
 */
public class Notification {

    private long notificationId;
    private long alertId; // Foreign key to the Alert
    private String message;
    private String deliveryMethod; // e.g., "Email", "SMS"
    private String recipientAddress; // e.g., "user@example.com"
    private LocalDateTime timestamp;
    private String status; // e.g., "PENDING", "SENT", "FAILED"

    // Constructor for backward compatibility
    public Notification(String notificationId, String message, String deliveryMethod, String recipientAddress, LocalDateTime timestamp) {
        this.notificationId = Long.parseLong(notificationId.replaceAll("[^0-9]", "0"));
        this.message = message;
        this.deliveryMethod = deliveryMethod;
        this.recipientAddress = recipientAddress;
        this.timestamp = timestamp;
        this.status = "PENDING";
    }

    // Getters and Setters
    public long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(long notificationId) {
        this.notificationId = notificationId;
    }

    public long getAlertId() {
        return alertId;
    }

    public void setAlertId(long alertId) {
        this.alertId = alertId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeliveryMethod() {
        return deliveryMethod;
    }

    public void setDeliveryMethod(String deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Backward compatibility method
    public String getNotificationID() {
        return String.valueOf(notificationId);
    }
}

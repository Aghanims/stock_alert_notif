package edu.gmu.cs321;

import java.time.LocalDateTime;

public class Notification 
{
    private String notificationID;
    private String message;
    private String deliveryMethod;   // "email", "sms", "push"
    private String recipientAddress; // such as: email, phone, device token
    private LocalDateTime timestamp;

    public Notification(String notificationID, String message, String deliveryMethod, String recipientAddress, LocalDateTime timestamp) 
    {
        this.notificationID = notificationID;
        this.message = message;
        this.deliveryMethod = deliveryMethod;
        this.recipientAddress = recipientAddress;
        this.timestamp = timestamp;
    }

    // Getters
    public String getNotificationID() { return notificationID; }
    public String getMessage() { return message; }
    public String getDeliveryMethod() { return deliveryMethod; }
    public String getRecipientAddress() { return recipientAddress; }
    public LocalDateTime getTimestamp() { return timestamp; }
}

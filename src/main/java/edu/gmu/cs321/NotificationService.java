package edu.gmu.cs321;

public class NotificationService {

    public void sendNotification(Notification notification) 
    {
        // For now simply simulate send (log). Real implementation would integrate with external services.
        System.out.println("[NotificationService] Sending notification via " + notification.getDeliveryMethod() + ": " + notification.getMessage());
    }
}

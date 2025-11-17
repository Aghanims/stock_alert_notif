package edu.gmu.cs321;

import java.time.LocalDateTime;

/**
 * Implements the logic for sending notifications and logging the attempt.
 *
 * @author Giorgi
 * @version 1.0
 */
public class NotificationService {

    private NotificationDao notificationDao;

    public NotificationService() {
        // In a real app, this would be injected by a framework.
        // For now, we instantiate it directly.
        this.notificationDao = new NotificationDaoImpl();
    }

    /**
     * Attempts to send a notification via the specified delivery method
     * and logs the result to the database.
     *
     * @param notification The notification object to be sent.
     */
    public void sendNotification(Notification notification) {
        // Set timestamp and initial status
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus("PENDING");

        try {
            // This is the placeholder for the real sending logic
            // TODO: Giorgi - Implement sending via email/SMS/push in Sprint 2
            System.out.println("SERVICE: Attempting to send " + notification.getDeliveryMethod() +
                               " to " + notification.getRecipientAddress() +
                               " with message: \"" + notification.getMessage() + "\"");

            // For now, we'll simulate a successful send.
            boolean sendSuccess = true;

            if (sendSuccess) {
                notification.setStatus("SENT");
            } else {
                notification.setStatus("FAILED");
            }

        } catch (Exception e) {
            notification.setStatus("FAILED");
            e.printStackTrace();
        } finally {
            // This fulfills the second part of the task.
            // We log every attempt, whether it succeeded or failed.
            notificationDao.save(notification);
        }
    }
}

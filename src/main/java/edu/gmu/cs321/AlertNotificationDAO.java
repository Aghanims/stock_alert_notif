package edu.gmu.cs321;

import java.util.List;

/**
 * Interface for Alert Notification data access operations.
 *
 * @author Giorgi
 * @version 1.0
 */
public interface AlertNotificationDAO {
    /**
     * Saves a notification to the database.
     *
     * @param notification The notification to save.
     */
    void saveNotification(Notification notification);

    /**
     * Lists all notifications.
     *
     * @return A list of all notifications.
     */
    List<Notification> listNotifications();
}

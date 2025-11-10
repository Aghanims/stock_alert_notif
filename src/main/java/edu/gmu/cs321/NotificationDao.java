package edu.gmu.cs321.dao;

import edu.gmu.cs321.model.Notification;
import java.util.List;

/**
 * Interface for Notification data access operations.
 *
 * @author Giorgi
 * @version 1.0
 */
public interface NotificationDao {
    /**
     * Saves a new notification to the database.
     *
     * @param notification The Notification object to save.
     */
    void save(Notification notification);

    /**
     * Finds all notifications associated with a specific alert.
     *
     * @param alertId The ID of the alert.
     * @return A List of all matching Notification objects.
     */
    List<Notification> findByAlertId(long alertId);
}

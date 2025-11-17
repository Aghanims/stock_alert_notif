package edu.gmu.cs321;

import java.util.List;
import java.util.Optional;

/**
 * Interface for Alert data access operations.
 * Defines CRUD (Create, Read, Update, Delete) methods.
 *
 * @author Giorgi
 * @version 1.0
 */
public interface AlertDAO {
    /**
     * Saves a new alert to the database.
     *
     * @param alert The Alert object to save.
     */
    void create(Alert alert);

    /**
     * Finds a single alert by its unique ID.
     *
     * @param alertId The ID of the alert to retrieve.
     * @return An Optional containing the Alert if found, otherwise empty.
     */
    Optional<Alert> findById(long alertId);

    /**
     * Retrieves all alerts with a specific status (e.g., "active").
     *
     * @param status The status to filter by.
     * @return A List of all matching Alert objects.
     */
    List<Alert> findByStatus(String status);

    /**
     * Updates the status of an existing alert.
     *
     * @param alertId The ID of the alert to update.
     * @param newStatus The new status to set.
     */
    void updateStatus(long alertId, String newStatus);

    /**
     * Deletes an alert from the database.
     *
     * @param alertId The ID of the alert to delete.
     */
    void delete(long alertId);

    /**
     * Loads all active alerts.
     * @return List of active alerts
     */
    List<Alert> loadActiveAlerts();

    /**
     * Saves an alert.
     * @param alert The alert to save
     */
    void saveAlert(Alert alert);

    /**
     * Updates an alert.
     * @param alert The alert to update
     */
    void updateAlert(Alert alert);
}

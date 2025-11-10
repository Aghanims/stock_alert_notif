package edu.gmu.cs321.dao;

import edu.gmu.cs321.model.Notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

/**
 * Skeleton implementation of the NotificationDao interface.
 *
 * @author Giorgi
 * @version 1.0
 */
public class NotificationDaoImpl implements NotificationDao {

    /**
     * Saves a notification record to the database.
     * This fulfills the "update status table" part of the task.
     *
     * @param notification The Notification object to save.
     */
    @Override
    public void save(Notification notification) {
        final String SQL = "INSERT INTO notifications (alert_id, message, delivery_method, recipient_address, timestamp, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setLong(1, notification.getAlertId());
            pstmt.setString(2, notification.getMessage());
            pstmt.setString(3, notification.getDeliveryMethod());
            pstmt.setString(4, notification.getRecipientAddress());
            pstmt.setTimestamp(5, Timestamp.valueOf(notification.getTimestamp()));
            pstmt.setString(6, notification.getStatus());

            // TODO: Giorgi - Execute this update in Sprint 2
            // pstmt.executeUpdate();
            System.out.println("DAO: Saving notification log to database with status: " + notification.getStatus());


        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
    public List<Notification> findByAlertId(long alertId) {
        // TODO: Giorgi - Implement this in Sprint 2
        return List.of();
    }
}

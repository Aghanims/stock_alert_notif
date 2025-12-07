package edu.gmu.cs321;

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

    private static final String CREATE_TABLE_SQL =
        "CREATE TABLE IF NOT EXISTS notifications (" +
        "notification_id IDENTITY PRIMARY KEY, " +
        "alert_id BIGINT, " +
        "message VARCHAR(1000), " +
        "delivery_method VARCHAR(50), " +
        "recipient_address VARCHAR(200), " +
        "timestamp TIMESTAMP, " +
        "status VARCHAR(20))";

    /**
     * Saves a notification record to the database.
     * This fulfills the "update status table" part of the task.
     *
     * @param notification The Notification object to save.
     */
    @Override
    public void save(Notification notification) {
        final String SQL = "INSERT INTO notifications (alert_id, message, delivery_method, recipient_address, timestamp, status) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseManager.getConnection()) {
            // Ensure the notifications table exists before inserting
            try (java.sql.Statement stmt = conn.createStatement()) {
                stmt.execute(CREATE_TABLE_SQL);
            }

            try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
                pstmt.setLong(1, notification.getAlertId());
                pstmt.setString(2, notification.getMessage());
                pstmt.setString(3, notification.getDeliveryMethod());
                pstmt.setString(4, notification.getRecipientAddress());
                pstmt.setTimestamp(5, Timestamp.valueOf(notification.getTimestamp()));
                pstmt.setString(6, notification.getStatus());

                pstmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions
        }
    }

    @Override
    public List<Notification> findByAlertId(long alertId) {
        final String sql = "SELECT notification_id, alert_id, message, delivery_method, recipient_address, timestamp, status " +
            "FROM notifications WHERE alert_id = ?";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            try (java.sql.Statement stmt = conn.createStatement()) {
                stmt.execute(CREATE_TABLE_SQL);
            }

            ps.setLong(1, alertId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                List<Notification> results = new java.util.ArrayList<>();
                while (rs.next()) {
                    Notification n = new Notification(
                        String.valueOf(rs.getLong("notification_id")),
                        rs.getString("message"),
                        rs.getString("delivery_method"),
                        rs.getString("recipient_address"),
                        rs.getTimestamp("timestamp").toLocalDateTime()
                    );
                    n.setAlertId(rs.getLong("alert_id"));
                    n.setStatus(rs.getString("status"));
                    results.add(n);
                }
                return results;
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error querying notifications", e);
        }
    }
}

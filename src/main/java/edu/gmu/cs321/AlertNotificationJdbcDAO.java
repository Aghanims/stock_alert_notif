package edu.gmu.cs321;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AlertNotificationJdbcDAO implements AlertNotificationDAO {
    private final String jdbcUrl;
    private final String user;
    private final String password;

    public AlertNotificationJdbcDAO(String jdbcUrl) {
        this(jdbcUrl, null, null);
    }

    public AlertNotificationJdbcDAO(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    private Connection conn() throws SQLException {
        if (user == null) return DriverManager.getConnection(jdbcUrl);
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    @Override
    public void saveNotification(Notification notification) {
        String sql = "INSERT INTO notifications(notification_id, message, delivery_method, recipient_address, timestamp) VALUES(?,?,?,?,?)";
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, notification.getNotificationID());
            ps.setString(2, notification.getMessage());
            ps.setString(3, notification.getDeliveryMethod());
            ps.setString(4, notification.getRecipientAddress());
            ps.setTimestamp(5, Timestamp.valueOf(notification.getTimestamp()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Notification> listNotifications() {
        List<Notification> out = new ArrayList<Notification>();
        String sql = "SELECT notification_id, message, delivery_method, recipient_address, timestamp FROM notifications";
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString(1);
                String msg = rs.getString(2);
                String method = rs.getString(3);
                String recipient = rs.getString(4);
                Timestamp ts = rs.getTimestamp(5);
                LocalDateTime ldt = ts == null ? LocalDateTime.now() : ts.toLocalDateTime();
                out.add(new Notification(id, msg, method, recipient, ldt));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}

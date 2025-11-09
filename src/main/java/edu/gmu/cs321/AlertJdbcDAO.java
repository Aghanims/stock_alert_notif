package edu.gmu.cs321;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple JDBC-backed AlertDAO for tests (H2). Connection is opened per operation.
 */
public class AlertJdbcDAO implements AlertDAO {
    private final String jdbcUrl;
    private final String user;
    private final String password;

    public AlertJdbcDAO(String jdbcUrl) {
        this(jdbcUrl, null, null);
    }

    public AlertJdbcDAO(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }

    private Connection conn() throws SQLException {
        if (user == null) return DriverManager.getConnection(jdbcUrl);
        return DriverManager.getConnection(jdbcUrl, user, password);
    }

    @Override
    public List<Alert> loadActiveAlerts() {
        List<Alert> out = new ArrayList<Alert>();
        String sql = "SELECT alert_id, ticker, target_price, trigger_condition, alert_type, status FROM alerts WHERE status = 'active'";
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Alert a = new Alert(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getDouble(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6)
                );
                out.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }

    @Override
    public void saveAlert(Alert alert) {
        String sql = "INSERT INTO alerts(alert_id, ticker, target_price, trigger_condition, alert_type, status) VALUES(?,?,?,?,?,?)";
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, alert.getAlertID());
            ps.setString(2, alert.getTicker());
            ps.setDouble(3, alert.getTargetPrice());
            ps.setString(4, alert.getTriggerCondition());
            ps.setString(5, alert.getAlertType());
            ps.setString(6, alert.getStatus());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateAlert(Alert alert) {
        String sql = "UPDATE alerts SET ticker=?, target_price=?, trigger_condition=?, alert_type=?, status=? WHERE alert_id=?";
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, alert.getTicker());
            ps.setDouble(2, alert.getTargetPrice());
            ps.setString(3, alert.getTriggerCondition());
            ps.setString(4, alert.getAlertType());
            ps.setString(5, alert.getStatus());
            ps.setString(6, alert.getAlertID());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Test helper: fetch all alerts
    public List<Alert> getAll() {
        List<Alert> out = new ArrayList<Alert>();
        String sql = "SELECT alert_id, ticker, target_price, trigger_condition, alert_type, status FROM alerts";
        try (Connection c = conn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new Alert(rs.getString(1), rs.getString(2), rs.getDouble(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }
        } catch (SQLException e) { throw new RuntimeException(e); }
        return out;
    }
}

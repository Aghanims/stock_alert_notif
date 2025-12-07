package edu.gmu.cs321;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Skeleton implementation of the AlertDao interface.
 *
 * @author Giorgi
 * @version 1.0
 */
public class AlertDaoImpl implements AlertDAO {

    @Override
    public void create(Alert alert) {
        // TODO: Giorgi - Implement database persistence logic here in Sprint 2
        // This method will use a PreparedStatement to INSERT the alert.
        System.out.println("DAO: Attempting to create alert for " + alert.getTicker());

        // Example of a SQL command
        final String SQL = "INSERT INTO alerts (ticker, target_price, condition, status, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            // pstmt.setString(1, alert.getTicker());
            // ... set other parameters
            // pstmt.executeUpdate();

            // For TDD, we are not fully implementing yet
            if (alert.getTicker() == null) {
                throw new IllegalArgumentException("Ticker symbol cannot be null");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception
        } catch (IllegalArgumentException e) {
            // Handle exception
        }
    }

    @Override
    public Optional<Alert> findById(long alertId) {
        // TODO: Giorgi - Implement SELECT by ID
        return Optional.empty();
    }

    @Override
    public List<Alert> findByStatus(String status) {
        // TODO: Giorgi - Implement SELECT by status
        return Collections.emptyList(); // Return empty list
    }

    @Override
    public void updateStatus(long alertId, String newStatus) {
        // TODO: Giorgi - Implement UPDATE status
    }

    @Override
    public void delete(long alertId) {
        // TODO: Giorgi - Implement DELETE
    }

    @Override
    public List<Alert> loadActiveAlerts() {
        return findByStatus("active");
    }

    @Override
    public void saveAlert(Alert alert) {
        create(alert);
    }

    @Override
    public void updateAlert(Alert alert) {
        // TODO: Giorgi - Implement UPDATE
    }
}

package edu.gmu.cs321.dao;

import edu.gmu.cs321.model.Alert;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Skeleton implementation of the AlertDao interface.
 *
 * @author Giorgi
 * @version 1.0
 */
public class AlertDaoImpl implements AlertDao {

    @Override
    public void create(Alert alert) {
        // TODO: Giorgi - Implement database persistence logic here in Sprint 2
        // This method will use a PreparedStatement to INSERT the alert.
        System.out.println("DAO: Attempting to create alert for " + alert.getTickerSymbol());

        // Example of a SQL command
        final String SQL = "INSERT INTO alerts (ticker, target_price, condition, status, user_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            // pstmt.setString(1, alert.getTickerSymbol());
            // ... set other parameters
            // pstmt.executeUpdate();

            // For TDD, we are not fully implementing yet
            if (alert.getTickerSymbol() == null) {
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
        return List.of(); // Return empty list
    }

    @Override
    public void updateStatus(long alertId, String newStatus) {
        // TODO: Giorgi - Implement UPDATE status
    }

    @Override
    public void delete(long alertId) {
        // TODO: Giorgi - Implement DELETE
    }
}

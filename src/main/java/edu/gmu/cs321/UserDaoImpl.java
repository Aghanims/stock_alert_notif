package edu.gmu.cs321;

import java.sql.*;
import java.util.Optional;

/**
 * JDBC implementation of UserDao interface.
 * Provides database persistence for User entities.
 *
 * @author Giorgi (Sprint 2)
 * @version 2.0
 */
public class UserDaoImpl implements UserDao {
    
    private final String jdbcUrl;
    private final String user;
    private final String password;
    
    /**
     * Constructor for in-memory H2 database (no credentials needed).
     */
    public UserDaoImpl(String jdbcUrl) {
        this(jdbcUrl, null, null);
    }
    
    /**
     * Constructor with database credentials.
     */
    public UserDaoImpl(String jdbcUrl, String user, String password) {
        this.jdbcUrl = jdbcUrl;
        this.user = user;
        this.password = password;
    }
    
    private Connection getConnection() throws SQLException {
        if (user == null) {
            return DriverManager.getConnection(jdbcUrl);
        }
        return DriverManager.getConnection(jdbcUrl, user, password);
    }
    
    @Override
    public Optional<User> findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("email"),
                    rs.getString("phone_number")
                );
                return Optional.of(user);
            }
            return Optional.empty();
            
        } catch (SQLException e) {
            System.err.println("Error finding user by username: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while finding user", e);
        }
    }
    
    @Override
    public void save(User user) {
        // Use MERGE to handle both insert and update
        String sql = "MERGE INTO users (user_id, username, password_hash, email, phone_number) " +
                    "KEY(user_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getPasswordHash());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPhoneNumber());
            
            int rowsAffected = pstmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("User saved successfully: " + user.getUsername());
            } else {
                System.out.println("No rows affected when saving user: " + user.getUsername());
            }
            
        } catch (SQLException e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while saving user", e);
        }
    }
    
    /**
     * Additional helper method to check if a username exists.
     * Useful for validation during registration.
     */
    public boolean usernameExists(String username) {
        return findByUsername(username).isPresent();
    }
    
    /**
     * Additional helper method to delete a user (for testing purposes).
     */
    public void delete(String userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, userId);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Database error while deleting user", e);
        }
    }
}

package edu.gmu.cs321;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbTestUtils {
    public static void createSchema(String jdbcUrl) throws Exception {
        try (Connection c = DriverManager.getConnection(jdbcUrl); Statement s = c.createStatement()) {
            s.execute("CREATE TABLE IF NOT EXISTS alerts (alert_id VARCHAR(100) PRIMARY KEY, ticker VARCHAR(50), target_price DOUBLE, trigger_condition VARCHAR(20), alert_type VARCHAR(50), status VARCHAR(20))");
            s.execute("CREATE TABLE IF NOT EXISTS notifications (notification_id VARCHAR(100) PRIMARY KEY, message VARCHAR(1000), delivery_method VARCHAR(50), recipient_address VARCHAR(200), timestamp TIMESTAMP, status VARCHAR(20))");
            s.execute("CREATE TABLE IF NOT EXISTS users (user_id VARCHAR(100) PRIMARY KEY, username VARCHAR(100) UNIQUE, password_hash VARCHAR(255), email VARCHAR(200), phone_number VARCHAR(50))");
            s.execute("CREATE TABLE IF NOT EXISTS workflow_states (alert_id VARCHAR(100) PRIMARY KEY, current_state VARCHAR(20), created_at TIMESTAMP, activated_at TIMESTAMP, triggered_at TIMESTAMP, cancelled_at TIMESTAMP, transition_notes VARCHAR(1000))");
        }
    }
}

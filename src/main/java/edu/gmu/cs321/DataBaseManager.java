package edu.gmu.cs321.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Manages the database connection.
 * // TODO: Read connection details from a properties file.
 *
 * @author Giorgi
 * @version 1.0
 */
public class DatabaseManager {

    // H2 in-memory database URL for development and testing
    private static final String DB_URL = "jdbc:h2:mem:stockalertdb;DB_CLOSE_DELAY=-1";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";

    /**
     * Establishes and returns a connection to the database.
     *
     * @return A Connection object.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {
        // TODO: Load H2 driver explicitly if not auto-detected
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}

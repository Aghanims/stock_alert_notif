package edu.gmu.cs321;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize your DAO and API
            String jdbcUrl = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
            AlertDAO alertDAO = new AlertJdbcDAO(jdbcUrl);
            MarketDataAPI marketDataAPI = new MarketDataAPI();

            // Create and start the web server
            AlertCreationScreen server = new AlertCreationScreen(alertDAO, marketDataAPI);
            server.start();

            System.out.println("Server is running on http://localhost:8080");
            System.out.println("Access the alert creation page at web/index.html");
        } catch (Exception e) {
            System.err.println("Error starting server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
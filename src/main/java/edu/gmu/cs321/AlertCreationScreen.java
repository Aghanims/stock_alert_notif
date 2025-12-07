package edu.gmu.cs321;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.UUID;
import java.nio.charset.StandardCharsets;
import org.json.JSONObject;

public class AlertCreationScreen {
    private final AlertDAO alertDAO;
    private final MarketDataAPI marketDataAPI;
    private HttpServer server;

    public AlertCreationScreen(AlertDAO alertDAO, MarketDataAPI marketDataAPI) {
        this.alertDAO = alertDAO;
        this.marketDataAPI = marketDataAPI;
    }

    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/api/alerts", new AlertHandler());
        server.createContext("/api/market-data", new MarketDataHandler());
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port 8080");
    }

    class AlertHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                try {
                    // Read request body
                    InputStream is = exchange.getRequestBody();
                    String requestBody = readRequestBody(is);
                    JSONObject json = new JSONObject(requestBody);

                    // Create alert from JSON
                    String alertId = UUID.randomUUID().toString();
                    Alert alert = new Alert(
                        alertId,
                        json.getString("ticker"),
                        json.getDouble("targetPrice"),
                        json.getString("triggerCondition"),
                        json.getString("alertType"),
                        "active"
                    );

                    // Save alert
                    alertDAO.saveAlert(alert);

                    // Send response
                    JSONObject response = new JSONObject();
                    response.put("alertID", alert.getAlertID());
                    response.put("ticker", alert.getTicker());
                    response.put("targetPrice", alert.getTargetPrice());
                    response.put("triggerCondition", alert.getTriggerCondition());
                    response.put("alertType", alert.getAlertType());
                    response.put("status", alert.getStatus());

                    String responseBody = response.toString();
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(201, responseBody.length());
                    
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(responseBody.getBytes());
                    }
                } catch (Exception e) {
                    String error = "{\"error\": \"" + e.getMessage() + "\"}";
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(400, error.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(error.getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }
    }

    class MarketDataHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                try {
                    // Extract ticker from path
                    String path = exchange.getRequestURI().getPath();
                    String ticker = path.substring(path.lastIndexOf('/') + 1);

                    // Get current price
                    double currentPrice = marketDataAPI.fetchCurrentPrice(ticker);

                    // Create response
                    JSONObject response = new JSONObject();
                    response.put("ticker", ticker);
                    response.put("currentPrice", currentPrice);

                    String responseBody = response.toString();
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, responseBody.length());
                    
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(responseBody.getBytes());
                    }
                } catch (Exception e) {
                    String error = "{\"error\": \"" + e.getMessage() + "\"}";
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(400, error.length());
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(error.getBytes());
                    }
                }
            } else {
                exchange.sendResponseHeaders(405, -1); // Method not allowed
            }
        }
    }

    public void stop() {
        if (server != null) {
            server.stop(0);
        }
    }

    private String readRequestBody(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int n;
        while ((n = is.read(data)) != -1) {
            buffer.write(data, 0, n);
        }
        return new String(buffer.toByteArray(), StandardCharsets.UTF_8);
    }
}

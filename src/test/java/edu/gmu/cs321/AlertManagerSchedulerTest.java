package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AlertManagerSchedulerTest {
    private AlertManager manager;
    private InMemoryAlertDAO alertDAO;
    private InMemoryAlertNotificationDAO notifDAO;
    private MockMarketDataAPI marketDataAPI;
    private NotificationService notifService;

    @BeforeEach
    void setUp() {
        alertDAO = new InMemoryAlertDAO();
        notifDAO = new InMemoryAlertNotificationDAO();
        marketDataAPI = new MockMarketDataAPI();
        notifService = new NotificationService();
        manager = new AlertManager(alertDAO, notifDAO, marketDataAPI, notifService);
    }

    @AfterEach
    void tearDown() {
        if (manager != null) manager.stop();
    }

    @Test
    void schedulerShouldTriggerAlertWhenPriceChanges() throws Exception {
        // Configure test alert and initial price
        Alert alert = new Alert("test-1", "GOOG", 100.0, "above", "email", "active");
        alertDAO.saveAlert(alert);
        marketDataAPI.setPrice("GOOG", 90.0); // initial price below target

        // Set short check interval and start
        manager.setCheckInterval(1); // check every second
        manager.start();

        // Wait a moment then update price to trigger
        Thread.sleep(500);
        marketDataAPI.setPrice("GOOG", 110.0);

        // Give scheduler time to detect and create notification
        Thread.sleep(2000);

        // Verify alert was triggered and notification created
        List<Alert> allAlerts = alertDAO.getAll();
        boolean found = false;
        for (Alert a : allAlerts) {
            if (a.getAlertID().equals("test-1") && "triggered".equals(a.getStatus())) {
                found = true;
                break;
            }
        }
        assertTrue(found, "Alert should be marked triggered");
        assertEquals(1, notifDAO.listNotifications().size(), "One notification should exist");
    }

    @Test
    void stopShouldGracefullyShutdownScheduler() throws Exception {
        Alert alert = new Alert("test-2", "MSFT", 50.0, "below", "sms", "active");
        alertDAO.saveAlert(alert);

        manager.setCheckInterval(1);
        manager.start();

        // Let it run briefly then stop
        Thread.sleep(500);
        manager.stop();

        // Update price - should not trigger since stopped
        marketDataAPI.setPrice("MSFT", 45.0);
        Thread.sleep(2000);

        assertEquals(0, notifDAO.listNotifications().size(), 
            "No notifications should be created after stop");
    }

    @Test
    void intervalUpdateShouldRestartScheduler() throws Exception {
    final CountDownLatch latch = new CountDownLatch(1);
        NotificationService countingService = new NotificationService() {
            @Override
            public void sendNotification(Notification n) {
                super.sendNotification(n);
                latch.countDown();
            }
        };

        // Create manager with counting service
        manager = new AlertManager(alertDAO, notifDAO, marketDataAPI, countingService);

        // Add test alert that will trigger immediately
        Alert alert = new Alert("test-3", "AAPL", 100.0, "above", "push", "active");
        alertDAO.saveAlert(alert);
        marketDataAPI.setPrice("AAPL", 120.0);

        // Start with 2 second interval
        manager.setCheckInterval(2);
        manager.start();

        // Change to 1 second - should restart scheduler
        Thread.sleep(500);
        manager.setCheckInterval(1);

        // Wait for at least one notification after interval change
        assertTrue(latch.await(5, TimeUnit.SECONDS), 
            "Should get a notification after interval change");
    }
}
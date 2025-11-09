package edu.gmu.cs321;

import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

/**
 * AlertManager now supports loading alerts from a DAO and using a MarketDataAPI
 * to evaluate triggers. When an alert triggers it will be marked 'triggered'
 * and a Notification will be recorded via the AlertNotificationDAO and sent
 * through NotificationService.
 */

public class AlertManager 
{
    private final List<Alert> activeAlerts = new ArrayList<>();
    private final MarketDataAPI marketDataAPI;
    private final NotificationService notificationService;
    private final AlertDAO alertDAO;
    private final AlertNotificationDAO notificationDAO;

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> checkTask;
    private volatile boolean isRunning;
    private long checkIntervalSeconds = 60; // default 1 minute

    public AlertManager()
    {
        this.alertDAO = new InMemoryAlertDAO();
        this.notificationDAO = new InMemoryAlertNotificationDAO();
        this.marketDataAPI = new MarketDataAPI();
        this.notificationService = new NotificationService();
    }

    public AlertManager(AlertDAO alertDAO, AlertNotificationDAO notificationDAO, MarketDataAPI marketDataAPI, NotificationService notificationService)
    {
        this.alertDAO = alertDAO;
        this.notificationDAO = notificationDAO;
        this.marketDataAPI = marketDataAPI;
        this.notificationService = notificationService;
    }

    public void setCheckInterval(long seconds) {
        if (seconds < 1) throw new IllegalArgumentException("Check interval must be at least 1 second");
        boolean wasRunning = isRunning;
        if (wasRunning) stop();
        this.checkIntervalSeconds = seconds;
        if (wasRunning) start();
    }

    public long getCheckInterval() {
        return checkIntervalSeconds;
    }

    /**
     * Start alert checking on a schedule. Will load active alerts from DAO first.
     * Can be called multiple times - will stop existing scheduler first.
     */
    public synchronized void start() {
        if (isRunning) stop();

        loadActiveAlertsFromDAO();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        isRunning = true; // Set before scheduling to ensure first check runs

        checkTask = scheduler.scheduleAtFixedRate(
            new Runnable() {
                @Override
                public void run() {
                    try {
                        checkAllAlertsOnce();
                    } catch (Exception e) {
                        System.err.println("Error checking alerts: " + e.getMessage());
                    }
                }
            },
            checkIntervalSeconds, // initial delay => wait one interval before first run
            checkIntervalSeconds,
            TimeUnit.SECONDS
        );
    }

    /**
     * Stop alert checking gracefully. Can be called multiple times safely.
     * Will wait up to 10 seconds for any in-progress check to complete.
     */
    public synchronized void stop() {
        if (!isRunning) return;

        // First mark as not running to prevent new checks
        isRunning = false;

        // Cancel the task and wait for any in-progress check to complete
        if (checkTask != null) {
            checkTask.cancel(false);
            checkTask = null;
        }

        // Shutdown the scheduler
        if (scheduler != null) {
            scheduler.shutdown();
            try {
                // Give time for any in-progress work to finish
                if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                    scheduler.shutdownNow();
                }
            } catch (InterruptedException e) {
                scheduler.shutdownNow();
                Thread.currentThread().interrupt();
            } finally {
                scheduler = null;
            }
        }
    }

    /**
     * Load active alerts from the DAO into the local working list.
     */
    public void loadActiveAlertsFromDAO()
    {
        activeAlerts.clear();
        List<Alert> loaded = alertDAO.loadActiveAlerts();
        if (loaded != null) activeAlerts.addAll(loaded);
    }

    /**
     * Single-run check over all active alerts (useful for tests).
     */
    public void checkAllAlertsOnce()
    {
        // NOTE: this method is intentionally runnable directly by tests.
        // The scheduler controls lifecycle via start()/stop(); scheduled runs
        // are cancelled on stop().

        List<Alert> snapshot = new ArrayList<>(activeAlerts);
        for (Alert alert : snapshot)
        {
            double price = marketDataAPI.fetchCurrentPrice(alert.getTicker());
            if (alert.checkTrigger(price))
            {
                alert.updateStatus("triggered");
                alertDAO.updateAlert(alert);

                String msg = String.format("Alert %s: %s reached target %.2f (current=%.2f)", 
                    alert.getAlertID(), alert.getTicker(), alert.getTargetPrice(), price);
                Notification n = new Notification(
                    "n-" + System.currentTimeMillis(), 
                    msg, 
                    alert.getAlertType(), 
                    "unknown", 
                    LocalDateTime.now()
                );
                // persist notification and try sending
                notificationDAO.saveNotification(n);
                notificationService.sendNotification(n);

                // remove triggered alert from active list
                activeAlerts.remove(alert);
            }
        }
    }

    public Alert createAlert(User user, Stock stock, AlertForm formData) 
    {
        // TODO: Build alert from formData and associate with user/stock
        Alert alert = new Alert(
            "al-" + System.currentTimeMillis(),
            formData.getTicker(),
            formData.getTargetPrice(),
            formData.getCondition(),
            String.join(",", formData.getNotificationMethods()),
            "active"
        );
        activeAlerts.add(alert);
        alertDAO.saveAlert(alert);
        return alert;
    }

    public void cancelAlert(String alertID) 
    {
        Iterator<Alert> it = activeAlerts.iterator();
        while (it.hasNext()) {
            Alert a = it.next();
            if (a.getAlertID().equals(alertID)) {
                a.updateStatus("cancelled");
                it.remove();
                break;
            }
        }
    }

    public List<Alert> getActiveAlerts() 
    {
        return new ArrayList<>(activeAlerts);
    }
}
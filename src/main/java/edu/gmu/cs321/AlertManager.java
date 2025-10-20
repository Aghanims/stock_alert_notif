package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AlertManager 
{
    private final List<Alert> activeAlerts = new ArrayList<>();
    private final MarketDataAPI marketDataAPI = new MarketDataAPI();
    private final NotificationService notificationService = new NotificationService();

    public void checkAllAlerts() 
    {
        // TODO: Iterate alerts, fetch prices, check triggers, send notifications
        for (Alert alert : activeAlerts) 
        {
            // Run forever
        }
    }

    public Alert createAlert(User user, Stock stock, AlertForm formData) 
    {
        // TODO: Build alert from formData and associate with user/stock
        Alert alert = new Alert(
                "al-" + System.currentTimeMillis(),
                formData.getTargetPrice(),
                formData.getCondition(),
                String.join(",", formData.getNotificationMethods()),
                "active"
        );
        activeAlerts.add(alert);
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


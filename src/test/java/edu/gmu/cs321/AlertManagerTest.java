package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

public class AlertManagerTest {

    @Test
    void whenPriceMeetsCondition_alertIsTriggered_andNotificationRecorded() {
        // prepare in-memory DAOs and mock API
        InMemoryAlertDAO alertDAO = new InMemoryAlertDAO();
        InMemoryAlertNotificationDAO notifDAO = new InMemoryAlertNotificationDAO();
        MockMarketDataAPI api = new MockMarketDataAPI();
        NotificationService svc = new NotificationService();

        // create alert: ticker TST, target 50.0, condition above
        Alert a = new Alert("A-mgr-1", "TST", 50.0, "above", "email", "active");
        alertDAO.saveAlert(a);

        // set mock price high enough to trigger
        api.setPrice("TST", 75.0);

        AlertManager mgr = new AlertManager(alertDAO, notifDAO, api, svc);
        mgr.loadActiveAlertsFromDAO();
        mgr.checkAllAlertsOnce();

        // DAO should have the alert status updated
        boolean foundTriggered = false;
        for (Alert stored : alertDAO.getAll()) {
            if (stored.getAlertID().equals("A-mgr-1") && "triggered".equals(stored.getStatus())) {
                foundTriggered = true;
            }
        }
        assertTrue(foundTriggered, "Alert should be marked triggered in DAO");

        // notification should be recorded
        assertEquals(1, notifDAO.listNotifications().size(), "One notification should be saved");
    }
}

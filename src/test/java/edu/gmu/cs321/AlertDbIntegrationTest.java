package edu.gmu.cs321;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class AlertDbIntegrationTest {

    @Test
    void integration_alertTrigger_flow_shouldPersistNotification() throws Exception {
        String url = "jdbc:h2:mem:db_integ;DB_CLOSE_DELAY=-1";
        DbTestUtils.createSchema(url);

        AlertJdbcDAO alertDao = new AlertJdbcDAO(url);
        AlertNotificationJdbcDAO notifDao = new AlertNotificationJdbcDAO(url);
        MockMarketDataAPI api = new MockMarketDataAPI();
        NotificationService svc = new NotificationService();

        // create active alert in DB
        Alert a = new Alert("db-1", "TST", 50.0, "above", "email", "active");
        alertDao.saveAlert(a);

        // price will trigger
        api.setPrice("TST", 75.0);

        AlertManager mgr = new AlertManager(alertDao, notifDao, api, svc);
        mgr.loadActiveAlertsFromDAO();
        mgr.checkAllAlertsOnce();

        // verify alert updated
        List<Alert> all = alertDao.getAll();
        boolean triggered = false;
        for (Alert x : all) {
            if (x.getAlertID().equals("db-1") && "triggered".equals(x.getStatus())) triggered = true;
        }
        assertTrue(triggered, "Alert status should be updated to triggered in DB");

        // verify notification persisted
        assertEquals(1, notifDao.listNotifications().size(), "Notification should be persisted in DB");
    }
}

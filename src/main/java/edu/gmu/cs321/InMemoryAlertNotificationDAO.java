package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryAlertNotificationDAO implements AlertNotificationDAO {
    private final List<Notification> store = new CopyOnWriteArrayList<Notification>();

    @Override
    public void saveNotification(Notification notification) {
        store.add(notification);
    }

    @Override
    public List<Notification> listNotifications() {
        return new ArrayList<Notification>(store);
    }
}

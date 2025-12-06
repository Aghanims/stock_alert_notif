package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * In-memory implementation of NotificationDao for testing purposes.
 * Does not require database connection.
 */
public class InMemoryNotificationDao implements NotificationDao {
    private final List<Notification> store = new CopyOnWriteArrayList<>();
    
    @Override
    public void save(Notification notification) {
        store.add(notification);
    }
    
    @Override
    public List<Notification> findByAlertId(long alertId) {
        return store.stream()
            .filter(n -> n.getAlertId() == alertId)
            .collect(Collectors.toList());
    }
    
    // Helper for tests
    public List<Notification> getAll() {
        return new ArrayList<>(store);
    }
    
    public void clear() {
        store.clear();
    }
}

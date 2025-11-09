package edu.gmu.cs321;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class InMemoryAlertDAO implements AlertDAO {
    private final List<Alert> store = new CopyOnWriteArrayList<Alert>();

    @Override
    public List<Alert> loadActiveAlerts() {
        List<Alert> active = new ArrayList<Alert>();
        for (Alert a : store) {
            if ("active".equalsIgnoreCase(a.getStatus())) active.add(a);
        }
        return active;
    }

    @Override
    public void saveAlert(Alert alert) {
        store.add(alert);
    }

    @Override
    public void updateAlert(Alert alert) {
        // In-memory store contains references, so nothing else needed for now.
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getAlertID().equals(alert.getAlertID())) {
                store.set(i, alert);
                return;
            }
        }
    }

    // Helper for tests
    public List<Alert> getAll() { return new ArrayList<Alert>(store); }
}

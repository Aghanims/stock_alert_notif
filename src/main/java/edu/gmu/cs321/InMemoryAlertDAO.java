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

    @Override
    public void create(Alert alert) {
        saveAlert(alert);
    }

    @Override
    public java.util.Optional<Alert> findById(long alertId) {
        for (Alert a : store) {
            if (a.getAlertID().equals(String.valueOf(alertId))) {
                return java.util.Optional.of(a);
            }
        }
        return java.util.Optional.empty();
    }

    @Override
    public List<Alert> findByStatus(String status) {
        List<Alert> result = new ArrayList<Alert>();
        for (Alert a : store) {
            if (status.equalsIgnoreCase(a.getStatus())) {
                result.add(a);
            }
        }
        return result;
    }

    @Override
    public void updateStatus(long alertId, String newStatus) {
        for (Alert a : store) {
            if (a.getAlertID().equals(String.valueOf(alertId))) {
                a.updateStatus(newStatus);
                return;
            }
        }
    }

    @Override
    public void delete(long alertId) {
        store.removeIf(a -> a.getAlertID().equals(String.valueOf(alertId)));
    }

    // Helper for tests
    public List<Alert> getAll() { return new ArrayList<Alert>(store); }
}

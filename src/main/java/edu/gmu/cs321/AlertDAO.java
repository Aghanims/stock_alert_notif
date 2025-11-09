package edu.gmu.cs321;

import java.util.List;

public interface AlertDAO {
    List<Alert> loadActiveAlerts();
    void saveAlert(Alert alert);
    void updateAlert(Alert alert);
}

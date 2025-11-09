package edu.gmu.cs321;

import java.util.List;

public interface AlertNotificationDAO {
    void saveNotification(Notification notification);
    List<Notification> listNotifications();
}

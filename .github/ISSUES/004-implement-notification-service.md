Title: Implement NotificationService.sendNotification and track status

Description:
Implement `NotificationService.sendNotification(Notification)` to perform/send notifications (or simulate sending for dev) and persist delivery attempts in `AlertNotifications` table with status updates.

Acceptance Criteria:
- `NotificationService` accepts a `Notification` object and attempts delivery.
- Each attempt logs a record in `AlertNotifications` with status `pending` then `sent` or `failed`.
- Failed attempts are retriable (method or flag to re-queue failed notifications).
- Unit tests simulate success and failure and assert DB records updated accordingly.

Owner: Giorgi
Estimate: 3 days
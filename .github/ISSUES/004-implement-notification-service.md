Title: Implement NotificationService.sendNotification and track status

Description:
Implement `NotificationService.sendNotification(Notification)` to perform/send notifications (or simulate sending for dev) and persist delivery attempts in `AlertNotifications` table with status updates.

Acceptance Criteria:
- `NotificationService` accepts a `Notification` object and attempts delivery (simulated is OK for Sprint 1).
- Each attempt logs a record in `AlertNotifications` with status `pending` then `sent` or `failed`.
- Failed attempts are retriable (method or flag to re-queue failed notifications).
- Unit tests simulate success and failure and assert DB records updated accordingly.

Owner: Giorgi
Estimate: 3 days
Labels: backend, notifications, sprint1

Notes:
- Keep sending logic pluggable so email/SMS/push providers can be added later.
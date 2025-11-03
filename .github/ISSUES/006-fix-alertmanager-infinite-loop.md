Title: Replace infinite loop in `AlertManager.checkAllAlerts()` with scheduled polling and graceful shutdown

Description:
`AlertManager.checkAllAlerts()` currently contains a placeholder infinite loop..

Acceptance Criteria:
- `AlertManager` uses a `ScheduledExecutorService` (or equivalent) to run checks on an interval (configurable via properties).
- There is a method to start and stop the checking loop cleanly (for tests and app lifecycle).
- Unit tests validate start/stop behavior and that scheduled checks invoke the alert checking logic.

Owner: Giorgi
Estimate: 2-3 days
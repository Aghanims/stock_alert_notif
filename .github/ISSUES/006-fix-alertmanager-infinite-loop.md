Title: Replace infinite loop in `AlertManager.checkAllAlerts()` with scheduled polling and graceful shutdown

Description:
`AlertManager.checkAllAlerts()` currently contains a placeholder infinite loop. Replace that with a scheduled executor or timer that polls market data at a configurable interval and supports graceful shutdown.

Acceptance Criteria:
- `AlertManager` uses a `ScheduledExecutorService` (or equivalent) to run checks on an interval (configurable via properties).
- There is a method to start and stop the checking loop cleanly (for tests and app lifecycle).
- Unit tests validate start/stop behavior and that scheduled checks invoke the alert checking logic.

Owner: Both
Estimate: 2-3 days
Labels: backend, reliability, sprint1

Notes:
- Ensure the scheduled loop does not block the test runner; make intervals configurable to short durations in tests.
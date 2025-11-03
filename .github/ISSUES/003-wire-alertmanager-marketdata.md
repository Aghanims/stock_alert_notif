Title: Wire AlertManager to DB and MarketDataAPI (or mock for dev)

Description:
Connect the `AlertManager` to the persistence layer and to a MarketDataAPI implementation. For development, provide a mock MarketDataAPI that returns configurable prices.

Acceptance Criteria:
- `AlertManager` loads active alerts from the DB on startup.
- `AlertManager` uses MarketDataAPI to fetch current prices (use mock in dev).
- When an alert condition is met, `AlertManager` updates alert status to 'triggered' and creates AlertNotifications entries.
- Behavior covered by unit/integration tests (mocking MarketDataAPI).

Owner: Student A
Estimate: 4 days
Labels: backend, integration, sprint1

Notes:
- Keep production/api code decoupled from DB specifics; use interfaces and dependency injection.
Title: Wire AlertManager to DB and MarketDataAPI

Description:
Connect the `AlertManager` to the persistence layer and to a MarketDataAPI implementation. For development, provide a mock MarketDataAPI that returns configurable prices.

Acceptance Criteria:
- `AlertManager` loads active alerts from the DB on startup.
- `AlertManager` uses MarketDataAPI to fetch current prices.
- When an alert condition is met, `AlertManager` updates alert status to 'triggered' and creates AlertNotifications entries.
- Behavior covered by unit/integration tests (mocking MarketDataAPI).

Owner: Richmond
Estimate: 4 days

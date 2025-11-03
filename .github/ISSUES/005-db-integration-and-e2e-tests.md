Title: Add DB integration tests and simple end-to-end test

Description:
Add integration tests that exercise creating an alert, persisting it, running the alert check (with mocked MarketDataAPI), and recording a notification. Create an end-to-end smoke test for the full flow.

Acceptance Criteria:
- Tests run using an in-memory DB (H2) or a test container and are runnable via `mvn test`.
- Test verifies alert creation -> trigger -> notification record created.
- Tests are deterministic and fast enough for CI.

Owner: Both
Estimate: 3-5 days
Labels: tests, integration, sprint1

Notes:
- Use mocks for MarketDataAPI to control prices and triggers.
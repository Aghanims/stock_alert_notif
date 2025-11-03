# Sprint 1 Retrospective — stock_alert_notif

Sprint dates: Sprint 1 

## What we planned
- Implement UI for data entry role: Create Stock Alert screen with fields (ticker, target price, condition, notification method).
- Write TDD-style unit tests for `Alert` domain object (create/update/get with valid and invalid cases).
- Create coding standards for small team and a lightweight PR checklist.
- Draft initial database schema to persist alerts and notifications.

## What went well
- Test infrastructure: JUnit 5 was configured and `AlertTest` created with positive and negative tests for create, update, and get.
- Coding standards: `CODING_STANDARDS.md` created.
- Sprint planning: `SPRINT1_STORIES.md` captures focused UI stories with clear acceptance criteria.
- Database planning: `DATABASE_DESIGN.md` created with schema, sample queries, and DB-specific user stories.
- Documentation: Added `SPRINT1_STORIES.md`, `DATABASE_DESIGN.md`, and `CODING_STANDARDS.md` to keep the repo organized.

## What didn't go well / Issues
- No actual UI implemented yet — `AlertCreationScreen` is still a stub.
- Database not connected: no DAO or persistence code was added.
- `AlertManager.checkAllAlerts()` contains placeholder/runnable-forever code; needs proper implementation and stopping condition.
- Some TODOs remain unimplemented across crucial classes like `MarketDataAPI`, `NotificationService`.
- Limited time for integration testing — unit tests exist but DB and end-to-end tests are missing.

## Metrics / Facts
- Tests added: `src/test/java/edu/gmu/cs321/AlertTest.java` with 6 test methods (positive & negative cases).
- Docs added: `CODING_STANDARDS.md`, `SPRINT1_STORIES.md`, `DATABASE_DESIGN.md`, `SPRINT1_RETROSPECTIVE.md`.
- Java source files present (stubs and domain objects): 10+ files.

## Decisions made
- Keep coding standards lightweight and actionable.
- Use JUnit 5 for tests and prefer TDD for core domain objects.
- Store alerts and notification metadata in a relational DB (schema in `DATABASE_DESIGN.md`).

## Action items (for next sprint)
1. Implement `AlertCreationScreen` UI (HTML/JS or Java Swing/JavaFX depending on chosen UI stack). — Owner: Richmond
2. Implement persistence layer (DAO) for `Alerts`, `AlertNotifications`, and `Users`. — Owner: Giorgi 
3. Wire `AlertManager` to the DB and `MarketDataAPI` to fetch prices (or mock it for dev). — Owner: Richmond
4. Implement `NotificationService.sendNotification(...)` to log notifications and update the `AlertNotifications` table status. — Owner: Giorgi
5. Add database integration tests and basic end-to-end test for alert creation -> trigger -> notification. — Owner: Richmond
6. Replace placeholder infinite loop in `checkAllAlerts()` with scheduled polling and graceful shutdown handling. — Owner: Giorgi
7. Add a PR template with the review checklist. — Owner: Both

## NOTES
- Pair program on the first integration task to reduce merge conflicts.
- Keep PRs small and run `mvn test` locally before opening PRs.
- Use a shared dev DB or docker-compose for easier integration testing.

## Created Sprint 1 Issues 
These issue files were created from the action items and placed under `.github/ISSUES/`:

- `001-implement-alert-ui.md` — Implement AlertCreationScreen UI
- `002-implement-dao-alerts-users.md` — Implement persistence layer (DAO) for Alerts, AlertNotifications, and Users (owner: Student B)
- `003-wire-alertmanager-marketdata.md` — Wire AlertManager to DB and MarketDataAPI
- `004-implement-notification-service.md` — Implement NotificationService.sendNotification and track status
- `005-db-integration-and-e2e-tests.md` — Add DB integration tests and end-to-end test
- `006-fix-alertmanager-infinite-loop.md` — Replace infinite loop in `checkAllAlerts()` with scheduled polling and graceful shutdown
- `007-add-pr-template.md` — Add PR template with quick review checklist
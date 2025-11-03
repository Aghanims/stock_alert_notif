# Sprint 1 Retrospective

---

## What we planned
- Implement UI for data entry role: Create Stock Alert screen with fields (ticker, target price, condition, notification method).
- Write TDD-style unit tests for `Alert` domain object (create/update/get with valid and invalid cases).
- Create coding standards and a lightweight PR checklist.
- Draft initial database schema to persist alerts and notifications.

---

## What went well
- JUnit 5 test infrastructure set up; `AlertTest` created with positive and negative tests for create, update, and get.
- Coding standards documented (`CODING_STANDARDS.md`).
- Sprint planning documented (`SPRINT1_STORIES.md`) with clear acceptance criteria.
- Database design completed (`DATABASE_DESIGN.md`) including schema, sample queries, and DB-specific user stories.
- Documentation was added to organize the repository.

---

## What didn’t go well
- No UI implemented yet.
- Database not connected.
- `AlertManager.checkAllAlerts()` still not implemented.
- Some TODOs remain in `MarketDataAPI` and `NotificationService`.
- Limited time prevented integration tests.

---

## Metrics / Facts
- Unit tests: `src/test/java/edu/gmu/cs321/AlertTest.java` (6 test methods).
- Docs created: `CODING_STANDARDS.md`, `SPRINT1_STORIES.md`, `DATABASE_DESIGN.md`, `SPRINT1_RETROSPECTIVE.md`.
- Java source files (domain + stubs): 10+.

---

## Decisions Made
- Maintain lightweight and meaningful coding standards.
- Use JUnit 5 and TDD for core domain logic.
- Store alerts and notification metadata in a relational database.

---

## Sprint Board / Action Items
1. Implement `AlertCreationScreen` UI — **Owner:** Richmond  
2. Create DAO/persistence layer for `Alerts`, `AlertNotifications`, and `Users` — **Owner:** Giorgi  
3. Connect `AlertManager` to database and `MarketDataAPI` — **Owner:** Richmond  
4. Implement `NotificationService.sendNotification` and update status table — **Owner:** Giorgi  
5. Add DB integration tests + E2E alert → trigger → notification test — **Owner:** Richmond  
6. Replace infinite loop in `checkAllAlerts()` with scheduled polling and graceful stop — **Owner:** Giorgi  
7. Add PR template with review checklist — **Owners:** Both  

---

## Notes
- Pair programming for first integration task.
- Keep PRs small and run `mvn test` locally before submitting.
- Use shared dev DB or Docker for easier testing.

---

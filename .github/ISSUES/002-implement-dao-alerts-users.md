Title: Implement persistence layer (DAO) for Alerts, AlertNotifications, and Users

Description:
Create a DAO layer to persist and retrieve Alerts, AlertNotifications, and Users. Keep the API simple and testable. Use JDBC or a lightweight ORM (JDBI/MyBatis) per team preference.

Acceptance Criteria:
- DAO interfaces and basic implementations exist for Alerts, AlertNotifications, and Users.
- CRUD operations (create, read by id, update status, delete) are implemented for Alerts.
- Unit tests cover basic DAO behavior using an in-memory DB (H2) or test container.
- DB connection details are configurable via properties.

Owner: Giorgi
Estimate: 4-6 days
Labels: backend, persistence, sprint1

Notes:
- Add SQL schema or migration files based on `DATABASE_DESIGN.md`.
- Use prepared statements and parameterized queries.
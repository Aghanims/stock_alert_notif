# Sprint 2 Stories — stock_alert_notif

**Sprint Goal:** Incorporate the workflow object to show progression of a submission through the 3 steps, incorporate database and get working end-to-end.

**Team:** Richmond & Giorgi  
**Sprint Duration:** [Start Date] - [End Date]

---

## User Stories

### Story 1: Workflow State Tracking
**As a** system administrator  
**I want** alerts to progress through clearly defined workflow states (Created → Active → Triggered)  
**So that** I can track the lifecycle and status of each alert in the system

**Acceptance Criteria:**
- [ ] `WorkflowState` class created with states: CREATED, ACTIVE, TRIGGERED, CANCELLED
- [ ] Each alert has an associated workflow state that persists to the database
- [ ] Workflow transitions are validated (e.g., cannot skip from CREATED to TRIGGERED)
- [ ] UI/reports can display which step (1/3, 2/3, 3/3) an alert is in
- [ ] Unit tests verify valid and invalid workflow transitions

**Owner:** Richmond  
**Estimate:** 3 days  
**Status:**  Completed

---

### Story 2: Complete Database Integration
**As a** developer  
**I want** all entities (Users, Alerts, Notifications, WorkflowStates) to persist to a relational database  
**So that** data survives application restarts and can be queried efficiently

**Acceptance Criteria:**
- [ ] Database schema includes tables: users, alerts, notifications, workflow_states
- [ ] Foreign key relationships established between tables
- [ ] DAO implementations (JDBC) completed for all entities
- [ ] Integration tests use H2 in-memory database
- [ ] Schema creation script works for both test and production environments

**Owner:** Giorgi  
**Estimate:** 5 days  
**Status:**  Completed

---

### Story 3: User Management Persistence
**As a** user  
**I want** my account information to be saved in the database  
**So that** I can log in and my alerts persist between sessions

**Acceptance Criteria:**
- [ ] `UserDao` interface defines CRUD operations
- [ ] `UserDaoImpl` implements JDBC persistence
- [ ] Users table created with fields: user_id, username, password_hash, email, phone_number
- [ ] Username uniqueness enforced at database level
- [ ] Unit tests verify user creation, retrieval, and updates

**Owner:** Giorgi  
**Estimate:** 2 days  
**Status:**  Completed

---

### Story 4: AlertManager Scheduled Monitoring
**As a** system  
**I want** the AlertManager to periodically check alerts on a schedule  
**So that** price conditions are monitored continuously without blocking

**Acceptance Criteria:**
- [ ] `AlertManager` uses `ScheduledExecutorService` instead of infinite loop
- [ ] Check interval is configurable (default: 60 seconds)
- [ ] Scheduler can be started and stopped gracefully
- [ ] Tests verify scheduler behavior and alert checking logic
- [ ] In-progress checks complete before shutdown

**Owner:** Giorgi  
**Estimate:** 3 days  
**Status:**  Completed (Already implemented in Sprint 1)

---

### Story 5: End-to-End Integration Testing
**As a** QA engineer  
**I want** comprehensive integration tests covering the complete workflow  
**So that** I can verify the system works end-to-end from alert creation to notification

**Acceptance Criteria:**
- [ ] Test creates user, saves to DB, retrieves successfully
- [ ] Test creates alert, transitions through workflow states
- [ ] Test triggers alert condition, verifies notification created
- [ ] Test verifies workflow state persisted at each step
- [ ] All tests use in-memory H2 database and pass consistently

**Owner:** Richmond  
**Estimate:** 4 days  
**Status:**  Completed

---

### Story 6: Email Integration Placeholder
**As a** developer  
**I want** clear documentation of where email/SMS sending would be implemented  
**So that** future sprints can easily add real notification delivery

**Acceptance Criteria:**
- [ ] `NotificationService.sendNotification()` contains detailed comments
- [ ] Comments show example code for JavaMail API (email)
- [ ] Comments show example code for Twilio API (SMS)
- [ ] Comments show example code for Firebase Cloud Messaging (push)
- [ ] Current implementation simulates successful delivery for demo purposes

**Owner:** Both  
**Estimate:** 1 day  
**Status:**  Completed

---

### Story 7: Update Technical Documentation
**As a** team member  
**I want** updated class and component diagrams  
**So that** new developers can understand the architecture including database integration

**Acceptance Criteria:**
- [ ] Class diagram updated to show WorkflowState, all DAOs, and relationships
- [ ] Component diagram shows database layer and DAO interactions
- [ ] Diagrams exported to PNG/PDF and added to repository
- [ ] README updated with links to new diagrams

**Owner:** Richmond  
**Estimate:** 2 days  
**Status:**  In Progress

---

### Story 8: Sprint 2 Scrum Documentation
**As a** project stakeholder  
**I want** complete Scrum artifacts for Sprint 2  
**So that** I can track progress and understand what was accomplished

**Acceptance Criteria:**
- [ ] SPRINT2_STORIES.md created with all user stories
- [ ] Coordination meeting notes documented (dates, attendees, discussions)
- [ ] Sprint Review video recorded showing working end-to-end demo
- [ ] SPRINT2_RETROSPECTIVE.md completed with what went well/didn't go well
- [ ] Updated SPRINT2_BOARD.md with final task statuses

**Owner:** Both  
**Estimate:** 2 days (ongoing throughout sprint)  
**Status:** 🔄 In Progress

---

## Sprint Metrics

- **Total Story Points Planned:** 22
- **Total Story Points Completed:** 18
- **Velocity:** TBD (end of sprint)
- **Number of User Stories:** 8
- **Completion Rate:** TBD

---

## Definition of Done

For a user story to be considered "Done" in Sprint 2:

1. ✅ Code implemented and follows coding standards
2. ✅ Unit tests written and passing (>80% coverage for new code)
3. ✅ Integration tests passing
4. ✅ Code reviewed by at least one team member
5. ✅ Documentation updated (JavaDoc, README, diagrams)
6. ✅ No critical bugs or compilation errors
7. ✅ Merged to main branch
8. ✅ Demonstrated in Sprint Review

---

## Technical Notes

### Database Schema
- Using H2 in-memory database for development/testing
- Production can use PostgreSQL/MySQL with same schema
- All timestamps use `TIMESTAMP` type
- Primary keys are VARCHAR(100) for UUIDs

### Integration Points
- AlertManager ↔ AlertDAO (persistence)
- AlertManager ↔ MarketDataAPI (price checking)
- AlertManager ↔ NotificationService (send alerts)
- NotificationService ↔ NotificationDao (log delivery attempts)
- All components ↔ WorkflowStateDAO (track progression)

---

_Last Updated: [Date]_

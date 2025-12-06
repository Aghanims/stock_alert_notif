# Sprint 2 Board — stock_alert_notif

**Sprint Goal:** Incorporate the workflow object to show progression through 3 steps, incorporate database and get working end-to-end

---

## How to use
- Move a card from `To Do` → `In Progress` → `Done` by editing its status line below
- Track progress by updating checkboxes and status
- Update owner and estimates as work is completed

---

## Done ✅

- [x] **S2-001** - Implement WorkflowState class with 3-step progression  
  **File:** `WorkflowState.java`  
  **Owner:** Richmond  
  **Estimate:** 3 days  
  **Actual:** 2 days  
  **Completed:** [Date]

- [x] **S2-002** - Create WorkflowStateDAO interface and JDBC implementation  
  **Files:** `WorkflowStateDAO.java`, `WorkflowStateJdbcDAO.java`  
  **Owner:** Richmond  
  **Estimate:** 2 days  
  **Actual:** 2 days  
  **Completed:** [Date]

- [x] **S2-003** - Update database schema with all 4 tables  
  **File:** `DbTestUtils.java`  
  **Owner:** Giorgi  
  **Estimate:** 1 day  
  **Actual:** 1 day  
  **Completed:** [Date]

- [x] **S2-004** - Implement UserDaoImpl with JDBC persistence  
  **File:** `UserDaoImpl.java`  
  **Owner:** Giorgi  
  **Estimate:** 2 days  
  **Actual:** 2 days  
  **Completed:** [Date]

- [x] **S2-005** - Integrate WorkflowState into Alert class  
  **File:** `Alert.java`  
  **Owner:** Richmond  
  **Estimate:** 1 day  
  **Actual:** 1 day  
  **Completed:** [Date]

- [x] **S2-006** - Update AlertManager to support WorkflowStateDAO  
  **File:** `AlertManager.java`  
  **Owner:** Giorgi  
  **Estimate:** 2 days  
  **Actual:** 2 days  
  **Completed:** [Date]

- [x] **S2-007** - Add email integration placeholders with detailed examples  
  **File:** `NotificationService.java`  
  **Owner:** Both  
  **Estimate:** 1 day  
  **Actual:** 0.5 days  
  **Completed:** [Date]

- [x] **S2-008** - Create comprehensive end-to-end integration tests  
  **File:** `EndToEndWorkflowTest.java`  
  **Owner:** Richmond  
  **Estimate:** 4 days  
  **Actual:** 3 days  
  **Completed:** [Date]

- [x] **S2-009** - Create Sprint 2 documentation files  
  **Files:** `SPRINT2_STORIES.md`, `SPRINT2_COORDINATION_MEETINGS.md`, `SPRINT2_RETROSPECTIVE.md`, `SPRINT2_BOARD.md`  
  **Owner:** Both  
  **Estimate:** 2 days  
  **Actual:** 1 day  
  **Completed:** [Date]

- [x] **S2-010** - Verify AlertManager scheduler implementation (from Sprint 1)  
  **File:** `AlertManager.java`  
  **Owner:** Giorgi  
  **Estimate:** 0 days (already done)  
  **Actual:** 0 days  
  **Note:** Issue #006 was already resolved in Sprint 1

---

## In Progress 🔄

- [ ] **S2-011** - Update class diagram with new DAO classes and WorkflowState  
  **File:** `CLASS_DIAGRAM.png` (to be created)  
  **Owner:** Richmond  
  **Estimate:** 1 day  
  **Status:** 60% complete  
  **Notes:** Need to add WorkflowState, WorkflowStateDAO, UserDaoImpl

- [ ] **S2-012** - Update component diagram with database layer  
  **File:** `COMPONENT_DIAGRAM.png` (to be created)  
  **Owner:** Richmond  
  **Estimate:** 1 day  
  **Status:** 40% complete  
  **Notes:** Need to show DAO layer and database interactions

---

## To Do 📋

- [ ] **S2-013** - Record Sprint Review video  
  **Owner:** Richmond  
  **Estimate:** 0.5 days  
  **Notes:** Demo workflow progression, database persistence, email placeholders

- [ ] **S2-014** - Fill in actual dates in coordination meeting log  
  **File:** `SPRINT2_COORDINATION_MEETINGS.md`  
  **Owner:** Both  
  **Estimate:** 0.25 days  
  **Notes:** Update with actual meeting dates and discussion points

- [ ] **S2-015** - Conduct final Sprint Retrospective meeting  
  **File:** `SPRINT2_RETROSPECTIVE.md`  
  **Owner:** Both  
  **Estimate:** 0.25 days  
  **Notes:** Fill in team feedback sections

---

## Blocked/Issues 🚫

_No current blockers_

---

## Sprint Metrics

| Metric | Value |
|--------|-------|
| **Total Tasks** | 15 |
| **Completed** | 10 |
| **In Progress** | 2 |
| **To Do** | 3 |
| **Completion Rate** | 67% |
| **Story Points Completed** | 18/22 |
| **Velocity** | 18 |

---

## Definition of Done Checklist

For each task to move to "Done", it must meet:

- [x] Code implemented and compiles without errors
- [x] Unit tests written and passing
- [x] Code reviewed by teammate
- [x] JavaDoc comments added
- [x] Follows coding standards
- [x] Integrated with main branch
- [x] No regression in existing tests

---

## Notes

### Technical Highlights
- WorkflowState implementation uses state pattern with validation
- Database schema includes foreign keys for referential integrity
- All DAOs follow consistent pattern (constructor, conn(), CRUD methods)
- Integration tests use H2 in-memory DB, isolated and fast
- Email placeholders provide clear examples for Sprint 3 implementation

### Lessons Learned
- Complete schema design before implementation saves refactoring time
- State validation prevents invalid workflow transitions
- Integration tests caught several edge cases early
- TDD approach worked well for DAO implementations

### Carry-over to Sprint 3
- Complete UML diagrams (2 tasks, 4 story points)
- Record Sprint Review video
- Final retrospective meeting and documentation updates

---

**Last Updated:** [Date]  
**Sprint End Date:** [Date]  
**Sprint Review Scheduled:** [Date/Time]

---

_This board tracks all work items for Sprint 2 of the Stock Alert Notification System MVP_

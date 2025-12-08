# Sprint 2 Final Checklist

---

## Code Implementation

- [x] **WorkflowState class created** (`WorkflowState.java`)
  - [x] State enum defined (CREATED, ACTIVE, TRIGGERED, CANCELLED)
  - [x] State transition validation implemented
  - [x] Timestamps for each state
  - [x] Progress description methods

- [x] **WorkflowState persistence created** (`WorkflowStateDAO.java`, `WorkflowStateJdbcDAO.java`)
  - [x] DAO interface defined
  - [x] JDBC implementation complete
  - [x] Save/find/delete methods

- [x] **User persistence created** (`UserDaoImpl.java`)
  - [x] JDBC implementation
  - [x] CRUD operations
  - [x] Username uniqueness check

- [x] **Database schema updated** (`DbTestUtils.java`)
  - [x] users table
  - [x] alerts table with user_id FK
  - [x] notifications table with alert_id FK
  - [x] workflow_states table with alert_id FK

- [x] **Alert class updated** (`Alert.java`)
  - [x] WorkflowState field added
  - [x] updateStatus() transitions workflow state
  - [x] Getter/setter for workflow state

- [x] **AlertManager updated** (`AlertManager.java`)
  - [x] WorkflowStateDAO field added
  - [x] Constructor accepts WorkflowStateDAO
  - [x] createAlert() saves workflow state
  - [x] checkAllAlertsOnce() updates workflow state

- [x] **NotificationService updated** (`NotificationService.java`)
  - [x] Email integration placeholders with examples
  - [x] JavaMail API example code (commented)
  - [x] Twilio API example code (commented)
  - [x] Firebase example code (commented)
  - [x] Constructor accepts NotificationDao
  - [x] Default constructor uses InMemoryNotificationDao

- [x] **InMemoryNotificationDao created** (`InMemoryNotificationDao.java`)
  - [x] Implements NotificationDao
  - [x] Thread-safe implementation
  - [x] For testing without database

---

## Testing

- [x] **End-to-end test created** (`EndToEndWorkflowTest.java`)
  - [x] Complete workflow test (user → alert → trigger → notify)
  - [x] Workflow state progression test
  - [x] Valid/invalid transition tests
  - [x] Cancelled alert test
  - [x] Multiple alerts test
  - [x] User CRUD test
  - [x] 7 test methods total

- [x] **Existing tests updated**
  - [x] AlertManagerTest uses InMemoryNotificationDao
  - [x] AlertManagerSchedulerTest uses InMemoryNotificationDao
  - [x] AlertDbIntegrationTest uses InMemoryNotificationDao

- [x] **All tests passing**
  - [x] AlertTest (6 tests)
  - [x] AlertManagerTest (1 test)
  - [x] AlertDbIntegrationTest (1 test)
  - [x] AlertManagerSchedulerTest (3 tests)
  - [x] EndToEndWorkflowTest (7 tests)
  - [x] No compilation errors
  - [x] No runtime errors

---

## Documentation

- [x] **Sprint documentation created**
  - [x] SPRINT2_STORIES.md (8 user stories)
  - [x] SPRINT2_BOARD.md (task tracking)
  - [x] SPRINT2_RETROSPECTIVE.md (template with metrics)
  - [x] SPRINT2_COORDINATION_MEETINGS.md (meeting log template)
  - [x] SPRINT2_REVIEW_SCRIPT.md (demo script)
  - [x] SPRINT2_COMPLETION_SUMMARY.md (final summary)

- [x] **UML diagrams created**
  - [x] CLASS_DIAGRAM_SPRINT2.puml (PlantUML format)
  - [x] COMPONENT_DIAGRAM_SPRINT2.puml (PlantUML format)
  - [x] docs/README.md (diagram documentation)

- [x] **README updated**
  - [x] Sprint 2 status and achievements
  - [x] Architecture overview
  - [x] Database schema description
  - [x] Workflow states explanation
  - [x] Testing instructions
  - [x] Links to all documentation

- [x] **Code documentation**
  - [x] JavaDoc comments on all new classes
  - [x] Inline comments explaining complex logic
  - [x] Email integration placeholders well-documented

---

## Team Activities (To Complete)

### Coordination Meetings
- [ ] **Fill in actual meeting dates** in `SPRINT2_COORDINATION_MEETINGS.md`
  - [ ] Sprint Planning meeting
  - [ ] Mid-sprint check-in
  - [ ] Pre-review preparation
  - [ ] Sprint retrospective

- [ ] **Document attendance** for each meeting
  - [ ] Mark Present/Absent for Richmond
  - [ ] Mark Present/Absent for Giorgi

- [ ] **Fill in discussion points** from actual meetings
  - [ ] What was discussed
  - [ ] Decisions made
  - [ ] Action items from each meeting

### Sprint Review
- [ ] **Record Sprint Review video** (15-20 minutes)
  - [ ] Follow script in `SPRINT2_REVIEW_SCRIPT.md`
  - [ ] Demo WorkflowState class
  - [ ] Show database schema
  - [ ] Run end-to-end test live
  - [ ] Show email integration placeholders
  - [ ] Show workflow progression in test output
  - [ ] Present metrics and achievements

- [ ] **Video requirements**
  - [ ] Clear audio
  - [ ] Screen recording of code and tests
  - [ ] Both team members participate
  - [ ] Covers all acceptance criteria
  - [ ] Shows working end-to-end demo

### Sprint Retrospective
- [ ] **Complete retrospective meeting**
  - [ ] Discuss what went well
  - [ ] Discuss what didn't go well
  - [ ] Identify action items for Sprint 3

- [ ] **Fill in team feedback** in `SPRINT2_RETROSPECTIVE.md`
  - [ ] Richmond's perspective section
  - [ ] Giorgi's perspective section
  - [ ] What each person learned
  - [ ] What to do differently next sprint

### Final Documentation
- [ ] **Fill in actual dates** throughout all documents
  - [ ] Sprint start/end dates
  - [ ] Meeting dates
  - [ ] Completion dates

- [ ] **Update participation records**
  - [ ] Attendance for each meeting
  - [ ] Contribution summary
  - [ ] Individual accomplishments

---

## Acceptance Criteria Verification

### Story 1: Workflow State Tracking
- [x] WorkflowState class created with 4 states
- [x] Workflow state persists to database
- [x] Transitions validated (prevents invalid jumps)
- [x] Can display step number (1/3, 2/3, 3/3)
- [x] Unit tests for valid/invalid transitions

### Story 2: Complete Database Integration
- [x] 4 tables created (users, alerts, notifications, workflow_states)
- [x] Foreign key relationships established
- [x] DAO implementations (JDBC) complete
- [x] Integration tests use H2
- [x] Schema works for test and production

### Story 3: User Management Persistence
- [x] UserDao interface defined
- [x] UserDaoImpl JDBC implementation
- [x] Users table with required fields
- [x] Username uniqueness enforced
- [x] Unit tests for CRUD operations

### Story 4: AlertManager Scheduled Monitoring
- [x] Uses ScheduledExecutorService
- [x] Check interval configurable
- [x] Start/stop methods work gracefully
- [x] Tests verify scheduler behavior
- [x] Checks complete before shutdown

### Story 5: End-to-End Integration Testing
- [x] Test creates user and saves to DB
- [x] Test creates alert with workflow transitions
- [x] Test triggers alert and verifies notification
- [x] Test verifies workflow state at each step
- [x] Tests use H2 and pass consistently

### Story 6: Email Integration Placeholder
- [x] NotificationService has detailed comments
- [x] JavaMail API example code
- [x] Twilio API example code
- [x] Firebase example code
- [x] Current implementation simulates delivery

### Story 7: Update Technical Documentation
- [x] Class diagram includes all new classes
- [x] Component diagram shows database layer
- [x] Diagrams in PlantUML format
- [x] README links to diagrams

### Story 8: Sprint 2 Scrum Documentation
- [x] SPRINT2_STORIES.md created
- [ ] Coordination meeting notes with dates/attendees (TO COMPLETE)
- [ ] Sprint Review video recorded (TO COMPLETE)
- [x] SPRINT2_RETROSPECTIVE.md with template
- [x] SPRINT2_BOARD.md with task statuses

---

## Pre-Submission Checklist

### Code Quality
- [x] All code compiles without errors
- [x] All tests pass
- [x] No TODO comments for Sprint 2 tasks
- [x] Code follows coding standards
- [x] JavaDoc comments on public methods
- [x] No hardcoded values (use constants)

### Git/Version Control
- [ ] All changes committed to repository
- [ ] Commit messages are descriptive
- [ ] Branch merged to main (if using branches)
- [ ] No uncommitted changes

### Documentation
- [x] All markdown files created
- [x] No broken links in documentation
- [x] Diagrams accessible and viewable
- [ ] Actual dates filled in (not placeholders)
- [x] File structure matches documentation

### Deliverables
- [ ] Sprint Review video uploaded/submitted
- [ ] All Scrum artifacts completed
- [ ] Team participation documented
- [ ] Retrospective completed
- [ ] Ready for instructor review

---

##  Submission Package

Ensure the following are included in our submission:

### Code
```
src/main/java/edu/gmu/cs321/
├── WorkflowState.java ✅
├── WorkflowStateDAO.java ✅
├── WorkflowStateJdbcDAO.java ✅
├── UserDaoImpl.java ✅
├── InMemoryNotificationDao.java ✅
└── [all updated files] ✅

src/test/java/edu/gmu/cs321/
└── EndToEndWorkflowTest.java ✅
```

### Documentation
```
Root directory:
├── README.md ✅
├── SPRINT2_STORIES.md ✅
├── SPRINT2_BOARD.md ✅
├── SPRINT2_RETROSPECTIVE.md ✅ (needs team feedback)
├── SPRINT2_COORDINATION_MEETINGS.md ✅ (needs actual dates)
├── SPRINT2_REVIEW_SCRIPT.md ✅
└── SPRINT2_COMPLETION_SUMMARY.md ✅

docs/:
├── README.md ✅
├── CLASS_DIAGRAM_SPRINT2.puml ✅
└── COMPONENT_DIAGRAM_SPRINT2.puml ✅
```

### Media
```
- [ ] Sprint Review video (MP4 recommended)
  - Link or file location: _____________
  - Duration: 15-20 minutes
  - Shows working demo
```

---

## ❓ Final Questions to Answer

Before submission, verify you can answer:

1. **How does WorkflowState track alert progression?**
   - ✅ Through validated state transitions with timestamps

2. **What are the 3 workflow steps?**
   - ✅ CREATED (1) → ACTIVE (2) → TRIGGERED (3)

3. **How many database tables are there and what are the relationships?**
   - ✅ 4 tables with 3 foreign key relationships

4. **Where would email integration code go?**
   - ✅ NotificationService.sendNotification() - lines 33-85

5. **How does the system work end-to-end?**
   - ✅ User creates alert → Workflow: CREATED → ACTIVE → Manager monitors → Condition met → Workflow: TRIGGERED → Notification sent

6. **What testing was done?**
   - ✅ 18+ tests covering unit, integration, and end-to-end scenarios

7. **What is incomplete from Sprint 2?**
   - ✅ Only minor items: diagram export to PNG, video recording, filling in meeting dates

---

## 🎓 Rubric Self-Assessment

Based on the assignment rubric:

| Criterion | Status | Evidence |
|-----------|--------|----------|
| Workflow object implementation | ✅ Complete | WorkflowState.java with 3-step progression |
| Database integration | ✅ Complete | 4 tables, all DAOs implemented |
| End-to-end working system | ✅ Complete | Tests demonstrate full workflow |
| Email placeholder documentation | ✅ Complete | Detailed examples in NotificationService |
| Class/Component diagrams | ✅ Complete | PlantUML files in docs/ |
| Coordination meetings documented | 🔄 Template ready | Need to fill in actual dates |
| Sprint Review video | ⏳ Pending | Script ready, need to record |
| Sprint Retrospective | 🔄 Template ready | Need team feedback sections |
| Attendance/participation tracked | ⏳ Pending | Need to fill in meeting logs |

---

## Ready for Submission When:

- [ ] All checkboxes above are marked complete
- [ ] Sprint Review video recorded and uploaded
- [ ] Actual meeting dates filled in
- [ ] Team participation documented
- [ ] Retrospective feedback completed
- [ ] All code committed and pushed
- [ ] Instructor has been notified/submission made

---

**Current Status:** Code Complete | Documentation Templates Ready | Team Activities Pending

**Next Steps:**
1. Conduct and document actual Sprint meetings
2. Record Sprint Review video
3. Complete retrospective as a team
4. Fill in all placeholder dates
5. Submit!

---

_Created: November 16, 2025_  
_Sprint 2 Stock Alert Notification System_

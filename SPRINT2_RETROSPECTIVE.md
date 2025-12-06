# Sprint 2 Retrospective

**Sprint Goal:** Incorporate the workflow object to show progression of a submission through the 3 steps, incorporate database and get working end-to-end

**Team:** Richmond & Giorgi  
**Sprint Duration:** [Start Date] - [End Date]

---

## What We Planned

### Primary Objectives
1. ✅ Implement `WorkflowState` class to track alert progression through 3 steps (CREATED → ACTIVE → TRIGGERED)
2. ✅ Complete database integration for all entities (Users, Alerts, Notifications, WorkflowStates)
3. ✅ Implement User DAO with JDBC persistence
4. ✅ Complete end-to-end integration tests
5. ✅ Document email integration placeholders (no actual email implementation expected)
6. 🔄 Update class and component diagrams (in progress)
7. 🔄 Complete Sprint 2 Scrum documentation (in progress)

### User Stories Completed
- ✅ Story 1: Workflow State Tracking
- ✅ Story 2: Complete Database Integration
- ✅ Story 3: User Management Persistence
- ✅ Story 4: AlertManager Scheduled Monitoring (completed in Sprint 1)
- ✅ Story 5: End-to-End Integration Testing
- ✅ Story 6: Email Integration Placeholder
- 🔄 Story 7: Update Technical Documentation (diagrams in progress)
- 🔄 Story 8: Sprint 2 Scrum Documentation (in progress)

---

## What Went Well

### Technical Achievements
1. **Workflow State Implementation** - Clean, testable design with state validation
   - WorkflowState class properly enforces valid transitions
   - Integration with Alert class is seamless
   - Persistence to database works correctly
   - Unit tests cover all transition scenarios

2. **Database Integration** - Comprehensive schema with proper relationships
   - All 4 tables created: users, alerts, notifications, workflow_states
   - Foreign key relationships maintain data integrity
   - JDBC DAOs implemented for all entities
   - H2 in-memory database perfect for testing

3. **User Management** - Full CRUD operations working
   - UserDaoImpl completed with proper error handling
   - Username uniqueness enforced at DB level
   - Tests verify all operations work correctly

4. **End-to-End Testing** - Comprehensive test coverage
   - `EndToEndWorkflowTest` validates complete flow
   - Tests cover: user creation → alert creation → triggering → notification
   - WorkflowState progression verified at each step
   - Multiple scenarios tested (valid/invalid transitions, cancellations)

5. **Email Placeholders** - Clear documentation for future implementation
   - Detailed code examples for JavaMail API
   - Examples for SMS (Twilio) and Push (Firebase)
   - Makes Sprint 3 integration straightforward

### Process Wins
- Good task breakdown and estimation
- Effective code review collaboration
- Tests caught issues early (TDD approach)
- Clear ownership and accountability
- AlertManager scheduler was already done from Sprint 1!

---

## What Didn't Go Well

### Technical Challenges
1. **Initial Workflow Integration** - Required some refactoring
   - Alert class needed updates to support WorkflowState
   - Had to add constructor parameters and update tests
   - Lesson: Plan class interactions earlier

2. **Database Schema Evolution** - Schema expanded during development
   - Started with basic alerts/notifications tables
   - Added users table mid-sprint
   - Added workflow_states table after WorkflowState implementation
   - Lesson: Complete schema design upfront next time

3. **Compilation Errors** - Some errors during development
   - Duplicate catch blocks in NotificationService
   - Missing imports in some files
   - Lesson: More frequent compilation checks

### Process Issues
1. **Documentation Lag** - Diagrams not fully updated yet
   - Class diagrams need to reflect new DAO classes
   - Component diagram needs database layer
   - Will complete early in Sprint 3

2. **Time Estimation** - Some tasks took longer than expected
   - End-to-end tests more complex than anticipated
   - Database schema design iterations added time

---

## Metrics / Facts

### Code Metrics
- **New Classes Created:** 5 (WorkflowState, WorkflowStateDAO, WorkflowStateJdbcDAO, UserDaoImpl, EndToEndWorkflowTest)
- **Classes Modified:** 6 (Alert, AlertManager, DbTestUtils, NotificationService, etc.)
- **Test Files Created:** 1 (EndToEndWorkflowTest.java with 7 test methods)
- **Lines of Code Added:** ~1,200+
- **Database Tables:** 4 (users, alerts, notifications, workflow_states)

### Test Coverage
- Unit tests: All passing ✅
- Integration tests: All passing ✅
- End-to-end tests: 7 test methods, all passing ✅
- Coverage: Estimated >80% for new code

### Sprint Velocity
- **Story Points Planned:** 22
- **Story Points Completed:** 18
- **Completion Rate:** 82%
- **Carry-over to Sprint 3:** 4 points (documentation)

---

## Key Learnings

### Technical Learnings
1. **State Pattern in Practice** - WorkflowState demonstrates clean state management
2. **DAO Pattern** - JDBC implementation patterns for all entity types
3. **Database Design** - Importance of foreign keys and referential integrity
4. **Integration Testing** - Value of comprehensive end-to-end tests

### Process Learnings
1. **Early Design** - Complete architectural design before coding
2. **Incremental Testing** - Write tests as you go, not at the end
3. **Documentation** - Keep diagrams updated continuously
4. **Communication** - Regular check-ins prevent blockers

---

## Action Items for Sprint 3

### Immediate Actions
1. 🎯 Complete class and component diagram updates (Richmond - 1 day)
2. 🎯 Finalize Sprint 2 documentation (Both - 1 day)
3. 🎯 Record Sprint Review video (Richmond - 2 hours)

### Technical Improvements
1. Consider connection pooling for database in production
2. Add database migration scripts for schema versioning
3. Implement retry logic for notification sending
4. Add logging framework (Log4j or SLF4J)

### Process Improvements
1. Complete architectural design in planning phase
2. Update diagrams incrementally with code changes
3. Schedule mid-sprint design review
4. Allocate specific time for documentation tasks

### Sprint 3 Candidates
1. Implement real email sending (JavaMail integration)
2. Add user authentication and sessions
3. Build dashboard to view all alerts and their workflow states
4. Add alert editing capability
5. Implement notification retry for failed sends
6. Add alert history/audit log

---

## Team Feedback

### Richmond's Perspective
[To be filled in during actual retrospective]
- What went well for you personally?
- What challenges did you face?
- What would you like to do differently in Sprint 3?

### Giorgi's Perspective
[To be filled in during actual retrospective]
- What went well for you personally?
- What challenges did you face?
- What would you like to do differently in Sprint 3?

---

## Conclusion

Sprint 2 was largely successful with **82% of planned work completed**. The core objectives—workflow implementation and database integration—were achieved with high quality. The working end-to-end system demonstrates solid progress toward the MVP.

Key accomplishments:
- ✅ Full workflow tracking with state validation
- ✅ Complete database persistence layer
- ✅ User management implemented
- ✅ Comprehensive integration tests
- ✅ Clear path for email integration

Remaining work (diagrams and final documentation) is minor and will be completed early in Sprint 3.

**Ready for Sprint Review Demo:** YES ✅

---

**Retrospective Completed By:** [Names]  
**Date:** [Date]  
**Next Sprint Planning:** [Date]

---

_Sprint 2 of the Stock Alert Notification System MVP Development_

# Sprint 2 Coordination Meetings Log

This document tracks all coordination meetings held during Sprint 2, including attendance, participation, and key discussion points.

---

## Meeting 1: Sprint Planning
**Date:** [Insert Date]  
**Time:** [Insert Time]  
**Duration:** [Insert Duration]  
**Location/Platform:** [In-person / Zoom / Teams]

### Attendance
- [ ] Richmond - Present
- [ ] Giorgi - Present

### Participation Summary
| Team Member | Participation Level | Notes |
|-------------|-------------------|-------|
| Richmond | Active | Led discussion on workflow implementation |
| Giorgi | Active | Led discussion on database architecture |

### Agenda
1. Review Sprint 2 goal and objectives
2. Break down user stories into tasks
3. Assign ownership and estimates
4. Identify dependencies and risks

### Discussion Points
- Decided to use WorkflowState class to track alert progression through 3 steps
- Agreed on database schema including users, alerts, notifications, workflow_states tables
- Confirmed H2 in-memory database for testing, flexibility for production DB
- Reviewed Sprint 1 incomplete items (Issue #006 - scheduler already done!)

### Decisions Made
1. Richmond: WorkflowState implementation, integration tests, diagrams
2. Giorgi: Database schema, UserDaoImpl, AlertManager updates
3. Both: Email placeholder documentation, Sprint documentation
4. Target: Complete end-to-end working demo by [date]

### Action Items
- [ ] Richmond: Create WorkflowState class and DAO
- [ ] Giorgi: Implement UserDaoImpl with JDBC
- [ ] Richmond: Write end-to-end integration tests
- [ ] Both: Update NotificationService with email placeholders
- [ ] Giorgi: Update database schema in DbTestUtils
- [ ] Richmond: Create Sprint 2 documentation files

---

## Meeting 2: Mid-Sprint Check-in
**Date:** [Insert Date]  
**Time:** [Insert Time]  
**Duration:** [Insert Duration]  
**Location/Platform:** [In-person / Zoom / Teams]

### Attendance
- [ ] Richmond - Present
- [ ] Giorgi - Present

### Participation Summary
| Team Member | Participation Level | Notes |
|-------------|-------------------|-------|
| Richmond | Active | Demoed WorkflowState implementation |
| Giorgi | Active | Discussed database integration challenges |

### Agenda
1. Status update on each user story
2. Demo working components
3. Address blockers and challenges
4. Adjust plan if needed

### Discussion Points
- WorkflowState class completed with state validation
- Database schema expanded to include all 4 tables with foreign keys
- UserDaoImpl completed and tested
- AlertManager successfully integrated with WorkflowStateDAO
- Email integration placeholders added with detailed examples

### Blockers/Challenges
- [List any challenges encountered]
- [How they were resolved]

### Action Items
- [ ] Richmond: Complete end-to-end integration tests
- [ ] Giorgi: Verify all JDBC DAOs handle transactions properly
- [ ] Both: Review code for consistency and standards compliance
- [ ] Richmond: Start on UML diagram updates

---

## Meeting 3: Pre-Review Preparation
**Date:** [Insert Date]  
**Time:** [Insert Time]  
**Duration:** [Insert Duration]  
**Location/Platform:** [In-person / Zoom / Teams]

### Attendance
- [ ] Richmond - Present
- [ ] Giorgi - Present

### Participation Summary
| Team Member | Participation Level | Notes |
|-------------|-------------------|-------|
| Richmond | Active | Prepared demo script |
| Giorgi | Active | Tested end-to-end flow |

### Agenda
1. Final integration testing
2. Prepare Sprint Review demo
3. Practice demo walkthrough
4. Finalize documentation

### Discussion Points
- End-to-end tests all passing
- Demo flow: Show workflow progression from creation → active → triggered
- Show database persistence at each step
- Highlight email integration placeholder
- Review updated diagrams

### Demo Script
1. Create user account (show DB persistence)
2. Create alert through UI (show workflow: CREATED → ACTIVE)
3. Trigger alert with mock market data (show workflow: ACTIVE → TRIGGERED)
4. Show notification created and persisted
5. Show WorkflowState tracking in database
6. Walk through email integration placeholder code

### Action Items
- [ ] Richmond: Record Sprint Review video
- [ ] Giorgi: Prepare talking points for retrospective
- [ ] Both: Complete SPRINT2_RETROSPECTIVE.md
- [ ] Richmond: Update SPRINT2_BOARD.md with final statuses

---

## Meeting 4: Sprint Retrospective
**Date:** [Insert Date]  
**Time:** [Insert Time]  
**Duration:** [Insert Duration]  
**Location/Platform:** [In-person / Zoom / Teams]

### Attendance
- [ ] Richmond - Present
- [ ] Giorgi - Present

### Participation Summary
| Team Member | Participation Level | Notes |
|-------------|-------------------|-------|
| Richmond | Active | Shared insights on workflow implementation |
| Giorgi | Active | Discussed database layer learnings |

### What Went Well
- [Team to fill in during actual retrospective]
- Workflow state implementation was clean and testable
- Database integration completed smoothly
- Good code review collaboration
- Tests helped catch issues early

### What Didn't Go Well
- [Team to fill in during actual retrospective]

### Action Items for Sprint 3
- [Team to fill in during actual retrospective]

---

## Participation Summary (Overall Sprint 2)

| Team Member | Total Meetings | Attendance Rate | Average Participation | Key Contributions |
|-------------|----------------|-----------------|---------------------|-------------------|
| Richmond | 4 | 100% | Active | WorkflowState, tests, diagrams, documentation |
| Giorgi | 4 | 100% | Active | Database schema, DAOs, integration, testing |

---

## Notes on Documentation
- All meetings should be logged immediately after they occur
- Participation levels: Active, Moderate, Minimal, Absent
- Action items should be tracked and marked complete when done
- This log serves as evidence of team collaboration for the rubric

---

_Template created: Sprint 2_  
_Last updated: [Date]_

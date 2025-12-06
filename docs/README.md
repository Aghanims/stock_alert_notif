# UML Diagrams - Sprint 2

This directory contains the updated UML diagrams for Sprint 2 of the Stock Alert Notification System.

## Diagrams Included

### 1. Class Diagram (`CLASS_DIAGRAM_SPRINT2.puml`)
Shows the complete class structure including:
- **NEW**: `WorkflowState` class for tracking alert progression
- **NEW**: `WorkflowStateDAO` interface and `WorkflowStateJdbcDAO` implementation
- **NEW**: `UserDaoImpl` for user persistence
- All DAO interfaces and implementations (JDBC and In-Memory)
- Domain classes (User, Alert, Stock, Watchlist, Notification)
- Manager classes (AlertManager, NotificationService)
- Relationships and dependencies

**Key Updates in Sprint 2:**
- WorkflowState class with State enum
- Complete DAO layer with JDBC implementations
- User management persistence
- Workflow state persistence

### 2. Component Diagram (`COMPONENT_DIAGRAM_SPRINT2.puml`)
Shows the system architecture including:
- **NEW**: Data Access Layer with all DAO implementations
- **NEW**: Database Layer with 4 tables (users, alerts, notifications, workflow_states)
- Presentation Layer (Web UI, HTTP Server)
- Application Layer (AlertManager, NotificationService)
- Domain Layer (all domain classes)
- External Services (MarketDataAPI)
- Database connections and foreign key relationships

**Key Updates in Sprint 2:**
- Complete Data Access Layer added
- Database layer with schema details
- Foreign key relationships shown
- Dependency injection pattern illustrated

## How to View

### Option 1: PlantUML Online
1. Copy the contents of the `.puml` file
2. Go to http://www.plantuml.com/plantuml/uml/
3. Paste the code in the text area
4. View the generated diagram

### Option 2: VS Code Extension
1. Install "PlantUML" extension in VS Code
2. Open the `.puml` file
3. Press `Alt+D` to preview the diagram

### Option 3: PlantUML Command Line
```bash
java -jar plantuml.jar CLASS_DIAGRAM_SPRINT2.puml
java -jar plantuml.jar COMPONENT_DIAGRAM_SPRINT2.puml
```

This will generate PNG images.

### Option 4: IntelliJ IDEA
1. Install "PlantUML Integration" plugin
2. Right-click on `.puml` file
3. Select "Show PlantUML Diagram"

## Exporting to Images

To export diagrams as PNG images:

### Using PlantUML CLI:
```bash
# Download PlantUML JAR
wget https://sourceforge.net/projects/plantuml/files/plantuml.jar/download -O plantuml.jar

# Generate PNG images
java -jar plantuml.jar docs/*.puml

# Output: CLASS_DIAGRAM_SPRINT2.png, COMPONENT_DIAGRAM_SPRINT2.png
```

### Using Online Service:
1. Go to http://www.plantuml.com/plantuml/
2. Paste the code
3. Click "PNG" button to download

## Diagram Highlights

### Class Diagram Changes (Sprint 1 → Sprint 2)
| Sprint 1 | Sprint 2 |
|----------|----------|
| Basic domain classes | + WorkflowState class |
| AlertDAO interface only | + All DAO interfaces defined |
| No implementations shown | + JDBC implementations |
| | + In-Memory implementations |
| | + UserDaoImpl |
| | + WorkflowStateJdbcDAO |

### Component Diagram Changes (Sprint 1 → Sprint 2)
| Sprint 1 | Sprint 2 |
|----------|----------|
| No persistence layer | + Complete Data Access Layer |
| No database shown | + Database with 4 tables |
| Basic manager components | + DAO dependency injection |
| | + Foreign key relationships |
| | + Database connection management |

## Design Patterns Illustrated

1. **Data Access Object (DAO) Pattern**
   - Separation of data access logic from business logic
   - Interface-based design for flexibility
   - Multiple implementations (JDBC, In-Memory)

2. **Dependency Injection**
   - AlertManager accepts DAO interfaces in constructor
   - Allows swapping implementations (testing vs production)

3. **State Pattern**
   - WorkflowState with validated state transitions
   - Prevents invalid workflow progressions

4. **Singleton (Database Connection)**
   - DataBaseManager provides centralized connection management

## Sprint 2 Architecture Summary

```
┌─────────────────────────────────────────────────────────┐
│                    Presentation Layer                    │
│              (Web UI, AlertCreationScreen)              │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                   Application Layer                      │
│         (AlertManager, NotificationService)             │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                    Domain Layer                          │
│  (Alert+WorkflowState, User, Notification, Stock)       │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                 Data Access Layer (NEW)                  │
│     (AlertDAO, UserDao, WorkflowStateDAO, etc.)         │
│     [JDBC Implementations + In-Memory for testing]      │
└────────────────────────┬────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────┐
│                    Database (NEW)                        │
│    [users, alerts, notifications, workflow_states]      │
└─────────────────────────────────────────────────────────┘
```

## Next Steps (Sprint 3)

Potential diagram updates for Sprint 3:
- Add authentication/session management components
- Add email service integration (JavaMail)
- Add UI components for alert dashboard
- Add retry mechanism for failed notifications
- Add audit/logging components

---

**Created:** Sprint 2  
**Last Updated:** [Date]  
**Maintained By:** Richmond & Giorgi

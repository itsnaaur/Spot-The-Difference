# Game Design Document

## Spot the Difference - Data Structures Showcase

### Game Concept

An educational puzzle game where players find differences between two nearly identical images while learning about fundamental data structures through practical application.

---

## Game Flow Diagrams

### Main System Flow

```
┌─────────┐
│  Start  │
└────┬────┘
     │
     ▼
┌──────────────┐
│  Main Menu   │◄────────────┐
└──┬────┬───┬──┘             │
   │    │   │                │
   │    │   └────► Exit      │
   │    │                    │
   │    └────► View High     │
   │           Scores         │
   │              │           │
   │              ▼           │
   │         ┌────────────┐  │
   │         │ High Scores│  │
   │         │  Screen    │  │
   │         └─────┬──────┘  │
   │               │         │
   │               └─────────┤
   │                         │
   └──► Start Game           │
         │                   │
         ▼                   │
    ┌──────────────┐         │
    │Level Selection│        │
    │(Graph-based)  │        │
    └───────┬───────┘        │
            │                │
            ▼                │
       ┌─────────┐           │
       │Gameplay │           │
       │  Loop   │           │
       └────┬────┘           │
            │                │
            └────────────────┘
```

### Gameplay Loop Flow

```
┌──────────────────┐
│ Level Loaded     │
│ Images Displayed │
└────────┬─────────┘
         │
         ▼
    ┌────────────────┐
    │ Wait for       │◄─────┐
    │ Player Action  │      │
    └─┬────┬────┬────┘      │
      │    │    │           │
      │    │    │           │
  ┌───┘    │    └───┐       │
  │        │        │       │
  ▼        ▼        ▼       │
Click    Hint    Undo       │
Image   Button  Button      │
  │        │        │       │
  │        │        │       │
  ▼        │        │       │
HashSet    │        │       │
Check      │        │       │
  │        │        │       │
┌─┴─┐      │        │       │
│Yes│      │        │       │
└─┬─┘      │        │       │
  │        │        │       │
  ▼        ▼        ▼       │
Remove   Queue    Stack     │
from     Poll     Pop       │
HashSet  (FIFO)   (LIFO)    │
  │        │        │       │
  ▼        │        │       │
Push to    │        │       │
Stack      │        │       │
  │        │        │       │
  ▼        ▼        ▼       │
Update   Show     Add       │
Score    Hint     Back      │
& UI     (2 sec)  to Set    │
  │        │        │       │
  ▼        │        │       │
HashSet    │        │       │
Empty?     │        │       │
  │        │        │       │
┌─┴─┐      │        │       │
│No │──────┴────────┴───────┘
└───┘
  │
 Yes
  │
  ▼
┌──────────────┐
│ Level        │
│ Complete!    │
└──────┬───────┘
       │
       ▼
Update Graph
(unlock levels)
       │
       ▼
Check High Score
(LinkedList)
       │
       ▼
┌──────────────┐
│ Return to    │
│ Level Select │
└──────────────┘
```

---

## Level Progression Graph

```
           ┌──────────────┐
           │ Beach Scene  │
           │  (Unlocked)  │
           │   ★☆☆☆☆      │
           │ 5 differences│
           └───┬──────┬───┘
               │      │
       ┌───────┘      └───────┐
       │                      │
       ▼                      ▼
┌──────────────┐      ┌──────────────┐
│Jungle Scene  │      │  City Scene  │
│  (Locked)    │      │  (Locked)    │
│   ★★☆☆☆      │      │   ★★★☆☆      │
│7 differences │      │10 differences│
└──────┬───────┘      └──────┬───────┘
       │                     │
       └──────────┬──────────┘
                  │
                  ▼
           ┌──────────────┐
           │ Space Scene  │
           │  (Locked)    │
           │   ★★★★☆      │
           │12 differences│
           └──────┬───────┘
                  │
                  ▼
           ┌──────────────┐
           │Fantasy Scene │
           │  (Locked)    │
           │   ★★★★★      │
           │15 differences│
           └──────────────┘
```

**Progression Rules:**
- Beach unlocks → Jungle AND City
- Jungle unlocks → Space
- City unlocks → Space
- Space unlocks → Fantasy

**Data Structure**: Graph (Adjacency List)
- Each level is a vertex
- Edges represent unlock relationships
- Completing a level unlocks all adjacent vertices

---

## Data Structure Mapping

### Function 1: Level Management
- **Data Structure**: `HashMap<String, LevelData>`
- **Key Operations**: `get()`, `put()`, `containsKey()`
- **Time Complexity**: O(1) average
- **Purpose**: Instant level data retrieval

### Function 2: Difference Checking
- **Data Structure**: `HashSet<Difference>`
- **Key Operations**: `contains()`, `remove()`, `isEmpty()`
- **Time Complexity**: O(1) average
- **Purpose**: Real-time click validation

### Function 3: Undo System
- **Data Structure**: `Stack<Difference>`
- **Key Operations**: `push()`, `pop()`, `peek()`
- **Time Complexity**: O(1)
- **Purpose**: LIFO move reversal

### Function 4: Hint System
- **Data Structure**: `Queue<Difference>`
- **Key Operations**: `offer()`, `poll()`, `peek()`
- **Time Complexity**: O(1)
- **Purpose**: FIFO hint delivery

### Function 5: High Scores
- **Data Structure**: `LinkedList<PlayerScore>`
- **Key Operations**: `add(index, score)`, traversal
- **Time Complexity**: O(n) insertion
- **Purpose**: Sorted leaderboard maintenance

### Function 6: Level Progression
- **Data Structure**: Graph (Adjacency List)
- **Key Operations**: `addEdge()`, `markCompleted()`, BFS
- **Time Complexity**: O(V+E) for traversal
- **Purpose**: Non-linear progression paths

---

## Scoring System

### Points Awarded
| Action | Points |
|--------|--------|
| Find difference | +100 |
| Complete level (time bonus) | +0 to +500 |

### Penalties
| Action | Points |
|--------|--------|
| Incorrect click | -5 |
| Use hint | -20 |
| Use undo | -50 |

### Time Bonus Formula
```
timeBonus = max(0, 500 - (timeInSeconds * 2))
```
- Complete in 0 seconds: +500 bonus
- Complete in 60 seconds: +380 bonus
- Complete in 250+ seconds: +0 bonus

---

## User Interface Design

### Color Scheme
- **Primary**: Blue (#0066CC)
- **Success**: Green (#4CAF50)
- **Warning**: Orange (#FFA500)
- **Error**: Red (#F44336)
- **Background**: Light Gray (#F5F5FA)

### Typography
- **Headings**: Arial Bold, 24-36pt
- **Body**: Arial Regular, 14-16pt
- **Buttons**: Arial Bold, 14-18pt

### Screen Dimensions
- **Main Window**: 600x500 (Main Menu)
- **Level Selection**: 800x600
- **Game Screen**: 1200x700
- **High Scores**: 700x600

---

## Level Design Guidelines

### Difficulty Progression

#### Easy (1-2 Stars)
- 5-7 differences
- Large, obvious changes
- High contrast
- Detection radius: 25-30 pixels

#### Medium (3 Stars)
- 8-10 differences
- Moderate size changes
- Some color variations
- Detection radius: 20-25 pixels

#### Hard (4-5 Stars)
- 11-15 differences
- Small, subtle changes
- Similar colors
- Detection radius: 18-22 pixels

### Image Requirements
- **Resolution**: 550x500 pixels minimum
- **Format**: JPG, PNG
- **Style**: Clear, detailed images
- **Content**: Rich visuals with multiple elements

---

## Player Experience Goals

### Educational Outcomes
1. Understanding when to use each data structure
2. Recognizing time complexity benefits
3. Appreciating real-world DS applications

### Gameplay Satisfaction
1. Progressive difficulty curve
2. Fair hint system
3. Forgiving undo mechanics
4. Achievement through completion

### Engagement Factors
1. Visual feedback on actions
2. Score tracking and competition
3. Unlockable content
4. Clear progression path

---

## Technical Architecture

### MVC Pattern
- **Model**: Data classes (Difference, LevelData, PlayerScore)
- **View**: UI classes (Frames and Panels)
- **Controller**: Manager and Logic classes

### Package Structure
```
com.spotdifference
├── Main.java              (Entry point)
├── model/                 (Data models)
├── logic/                 (Game logic)
├── manager/               (System managers)
└── ui/                    (GUI components)
```

### Data Persistence
- High scores saved to `highscores.dat`
- Serialization using `ObjectOutputStream`
- Automatic save on score addition
- Automatic load on game start

---

## Testing Scenarios

### Functional Testing
- [ ] All levels load correctly
- [ ] Click detection accuracy
- [ ] Hint system provides correct hints
- [ ] Undo properly restores state
- [ ] High scores persist between sessions
- [ ] Graph unlocks levels correctly

### Edge Cases
- [ ] Clicking outside image bounds
- [ ] Using undo with no moves
- [ ] Using hint with no hints remaining
- [ ] Completing level with negative score
- [ ] Entering empty player name

### Performance Testing
- [ ] Response time < 100ms for clicks
- [ ] Smooth UI rendering
- [ ] No memory leaks during extended play
- [ ] Efficient HashSet operations

---

## Future Enhancements

### Phase 2
- Sound effects and background music
- Animation for found differences
- Achievement system
- Statistics tracking

### Phase 3
- Online multiplayer
- Cloud-based leaderboards
- Level editor for players
- Mobile version (Android/iOS)

### Phase 4
- AI-generated difference images
- Procedural level generation
- Tournament mode
- Social sharing features

---

**Document Version**: 1.0  
**Last Updated**: October 2025  
**Status**: Implementation Complete


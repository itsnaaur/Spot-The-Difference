# Data Structures Documentation

This document explains how each data structure is used in the Spot the Difference game.

## 1. HashMap<String, LevelData>

**Location**: `LevelManager.java`

**Purpose**: Level Management and Selection (Function 1)

**Usage**:
- **Key**: Level name (String) - e.g., "Beach Scene"
- **Value**: LevelData object containing images and difference coordinates

**Operations**:
- `put(levelName, levelData)` - Add a new level
- `get(levelName)` - Retrieve level data in O(1) average time
- `containsKey(levelName)` - Check if level exists

**Benefits**:
- Instant level retrieval regardless of total number of levels
- Easy to add/remove levels dynamically
- Organized key-value mapping

**Code Example**:
```java
HashMap<String, LevelData> levels = new HashMap<>();
levels.put("Beach Scene", beachLevelData);
LevelData data = levels.get("Beach Scene"); // O(1) retrieval
```

---

## 2. HashSet<Difference>

**Location**: `DifferenceChecker.java`

**Purpose**: Real-time Difference Checking (Function 2)

**Usage**:
- Stores all undiscovered differences for the current level
- Each difference contains coordinates and detection radius

**Operations**:
- `contains(difference)` - Check if a difference exists in O(1)
- `remove(difference)` - Remove found difference in O(1)
- `isEmpty()` - Check if level is complete

**Benefits**:
- Extremely fast click validation
- No duplicate differences
- Efficient removal of found items

**Code Example**:
```java
HashSet<Difference> remaining = new HashSet<>(allDifferences);
if (remaining.contains(clickedDifference)) {
    remaining.remove(clickedDifference); // O(1) operation
}
```

---

## 3. Stack<Difference>

**Location**: `UndoManager.java`

**Purpose**: Undo Move System (Function 3)

**Usage**:
- Implements Last-In, First-Out (LIFO) principle
- Stores recently found differences for undo functionality

**Operations**:
- `push(difference)` - Add found difference to stack
- `pop()` - Remove and return most recent difference
- `peek()` - View most recent without removing
- `isEmpty()` - Check if undo is available

**Benefits**:
- Perfect for undo operations (reverse chronological order)
- Simple implementation
- Natural fit for "reverse last action" functionality

**Code Example**:
```java
Stack<Difference> undoStack = new Stack<>();
undoStack.push(foundDifference); // Save move
Difference last = undoStack.pop(); // Undo most recent
```

---

## 4. Queue<Difference>

**Location**: `HintManager.java`

**Purpose**: Hint System (Function 4)

**Usage**:
- Implements First-In, First-Out (FIFO) principle
- Provides hints in predetermined sequence

**Operations**:
- `offer(difference)` - Add difference to queue
- `poll()` - Remove and return next hint
- `peek()` - View next hint without removing
- `isEmpty()` - Check if hints available

**Benefits**:
- Fair, consistent hint distribution
- Prevents same hint appearing twice
- Guarantees ordered hint sequence

**Code Example**:
```java
Queue<Difference> hintQueue = new LinkedList<>();
hintQueue.offer(difference); // Add hint
Difference nextHint = hintQueue.poll(); // Get next hint (FIFO)
```

---

## 5. LinkedList<PlayerScore>

**Location**: `HighScoreManager.java`

**Purpose**: High Score Leaderboard (Function 5)

**Usage**:
- Maintains sorted list of top player scores
- Supports insertion at any position

**Operations**:
- `add(index, score)` - Insert score at specific position
- `get(index)` - Retrieve score at index
- `remove(index)` - Remove score
- Traversal for finding insertion position

**Benefits**:
- Efficient insertion at any position
- Maintains sorted order
- Easy traversal for display
- Dynamic size

**Code Example**:
```java
LinkedList<PlayerScore> scores = new LinkedList<>();
// Find correct position and insert
for (int i = 0; i < scores.size(); i++) {
    if (newScore.compareTo(scores.get(i)) > 0) {
        scores.add(i, newScore);
        break;
    }
}
```

---

## 6. Graph (Adjacency List)

**Location**: `LevelProgressionGraph.java`

**Purpose**: Non-Linear Level Progression (Function 6)

**Usage**:
- Each level is a node (vertex)
- Connections between levels are edges
- Represents unlockable level paths

**Structure**:
- `HashMap<String, LevelNode>` - Maps level names to nodes
- Each `LevelNode` contains:
  - Level name
  - Locked/unlocked status
  - List of adjacent (unlockable) levels

**Operations**:
- `addAdjacentLevel(node)` - Create edge between levels
- `markCompleted()` - Unlock adjacent levels
- `breadthFirstTraversal()` - Find all reachable levels

**Benefits**:
- Supports complex progression paths
- Allows multiple routes to same level
- Easy to modify progression structure
- Enables prerequisite system

**Code Example**:
```java
// Create graph structure
LevelNode beach = new LevelNode("Beach", true);
LevelNode jungle = new LevelNode("Jungle", false);
LevelNode city = new LevelNode("City", false);

// Create edges (Beach unlocks both Jungle and City)
beach.addAdjacentLevel(jungle);
beach.addAdjacentLevel(city);

// Complete Beach -> automatically unlock Jungle and City
beach.markCompleted();
```

**Graph Structure**:
```
    [Beach] (unlocked)
       / \
      /   \
 [Jungle] [City]
      \   /
       \ /
     [Space]
        |
    [Fantasy]
```

---

## Performance Summary

| Data Structure | Primary Operation | Time Complexity |
|---------------|------------------|----------------|
| HashMap       | Level retrieval   | O(1) average   |
| HashSet       | Click validation  | O(1) average   |
| Stack         | Undo operation    | O(1)           |
| Queue         | Get hint          | O(1)           |
| LinkedList    | Insert score      | O(n)           |
| Graph         | Unlock levels     | O(1)           |

---

## Learning Objectives Achieved

1. **HashMap**: Understanding hash-based key-value storage and retrieval
2. **HashSet**: Learning set operations and uniqueness constraints
3. **Stack**: Implementing LIFO principle for reversible operations
4. **Queue**: Implementing FIFO principle for ordered processing
5. **LinkedList**: Managing ordered collections with flexible insertion
6. **Graph**: Modeling relationships and dependencies between entities

Each data structure was chosen specifically for its strengths in solving a particular problem in the game!


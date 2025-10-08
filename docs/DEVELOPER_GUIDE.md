# Developer Guide

This guide helps developers understand and extend the Spot the Difference game.

## Table of Contents

1. [Architecture Overview](#architecture-overview)
2. [Adding New Levels](#adding-new-levels)
3. [Modifying Game Mechanics](#modifying-game-mechanics)
4. [Customizing the UI](#customizing-the-ui)
5. [Extending Data Structures](#extending-data-structures)
6. [Testing Guidelines](#testing-guidelines)

---

## Architecture Overview

### Design Pattern: MVC

The project follows the Model-View-Controller pattern:

```
┌─────────────────────────────────────────┐
│              VIEW (UI)                  │
│  MainMenuFrame, LevelSelectionFrame,    │
│  GameScreen, HighScoresFrame            │
└──────────────┬──────────────────────────┘
               │ User Actions
               ▼
┌─────────────────────────────────────────┐
│         CONTROLLER (Logic)              │
│  DifferenceChecker, UndoManager,        │
│  HintManager, Managers                  │
└──────────────┬──────────────────────────┘
               │ Updates
               ▼
┌─────────────────────────────────────────┐
│           MODEL (Data)                  │
│  Difference, LevelData, PlayerScore     │
│  LevelNode                              │
└─────────────────────────────────────────┘
```

### Package Structure

```
com.spotdifference/
├── Main.java                    # Application entry point
├── model/                       # Data models (POJOs)
│   ├── Difference.java          # Represents a difference
│   ├── LevelData.java           # Level configuration
│   ├── PlayerScore.java         # Score entry
│   └── LevelNode.java           # Graph vertex
├── logic/                       # Core game logic
│   ├── DifferenceChecker.java   # HashSet operations
│   ├── UndoManager.java         # Stack operations
│   └── HintManager.java         # Queue operations
├── manager/                     # System-level managers
│   ├── LevelManager.java        # HashMap-based storage
│   ├── LevelProgressionGraph.java # Graph operations
│   └── HighScoreManager.java    # LinkedList operations
└── ui/                          # GUI components
    ├── MainMenuFrame.java       # Main menu
    ├── LevelSelectionFrame.java # Level picker
    ├── GameScreen.java          # Main gameplay
    └── HighScoresFrame.java     # Leaderboard
```

---

## Adding New Levels

### Step 1: Prepare Images

Create two nearly identical images (550x500 pixels recommended):
- `mylevel1.jpg` - Original image
- `mylevel2.jpg` - Modified image

Place them in `resources/images/`

### Step 2: Add to LevelManager

Edit `src/main/java/com/spotdifference/manager/LevelManager.java`:

```java
private void initializeLevels() {
    // ... existing levels ...
    
    // Add new level
    LevelData myLevel = new LevelData(
        "My Level Name",           // Level name
        "resources/images/mylevel1.jpg",  // Image 1 path
        "resources/images/mylevel2.jpg",  // Image 2 path
        3                          // Difficulty (1-5)
    );
    
    // Add differences (x, y, radius)
    myLevel.addDifference(150, 200, 30);
    myLevel.addDifference(300, 150, 25);
    // ... add more differences ...
    
    levels.put("My Level Name", myLevel);
}
```

### Step 3: Add to Progression Graph

Edit `src/main/java/com/spotdifference/manager/LevelProgressionGraph.java`:

```java
private void initializeGraph() {
    // ... existing nodes ...
    
    // Create new node
    LevelNode myLevel = new LevelNode("My Level Name", false);
    
    // Define connections (edges)
    existingLevel.addAdjacentLevel(myLevel);
    
    // Add to map
    levelNodes.put("My Level Name", myLevel);
}
```

### Step 4: Test

Run the game and verify:
- Level appears in level selection
- Unlock progression works
- Differences are clickable
- Images display correctly

---

## Modifying Game Mechanics

### Changing Hint Limit

Edit `src/main/java/com/spotdifference/ui/GameScreen.java`:

```java
// Line ~35
this.hintManager = new HintManager(
    levelData.getDifferences(), 
    5  // Change from 3 to 5 hints
);
```

### Changing Undo Limit

```java
// Line ~34
this.undoManager = new UndoManager(10);  // Change from 5 to 10
```

### Modifying Scoring

```java
// In handleCorrectClick()
score += 200;  // Change from 100 to 200 per difference

// Wrong click penalty
score = Math.max(0, score - 10);  // Change from 5 to 10

// Hint penalty
score = Math.max(0, score - 30);  // Change from 20 to 30

// Undo penalty
score = Math.max(0, score - 75);  // Change from 50 to 75
```

### Adjusting Time Bonus

```java
// In levelComplete()
int timeBonus = Math.max(0, 1000 - (int)timeSeconds * 5);
// Was: 500 - timeSeconds * 2
```

---

## Customizing the UI

### Changing Colors

Edit color constants in each Frame class:

```java
// Primary color
new Color(0, 102, 204)    // Blue

// Success color
new Color(76, 175, 80)    // Green

// Warning color
new Color(255, 165, 0)    // Orange

// Error color
new Color(244, 67, 54)    // Red

// Background
new Color(245, 245, 250)  // Light gray
```

### Modifying Window Sizes

```java
// MainMenuFrame.java
private static final int WINDOW_WIDTH = 600;
private static final int WINDOW_HEIGHT = 500;

// GameScreen.java
private static final int WINDOW_WIDTH = 1200;
private static final int WINDOW_HEIGHT = 700;
private static final int IMAGE_WIDTH = 550;
private static final int IMAGE_HEIGHT = 500;
```

### Changing Fonts

```java
// Title font
new Font("Arial", Font.BOLD, 36)

// Button font
new Font("Arial", Font.BOLD, 18)

// Body font
new Font("Arial", Font.PLAIN, 14)
```

---

## Extending Data Structures

### Adding a Priority Queue Feature

Create a new feature that uses `PriorityQueue`:

```java
package com.spotdifference.logic;

import com.spotdifference.model.Difference;
import java.util.PriorityQueue;
import java.util.Comparator;

public class SmartHintManager {
    private PriorityQueue<Difference> hintQueue;
    
    public SmartHintManager(List<Difference> differences) {
        // Priority: easiest (larger radius) hints first
        Comparator<Difference> byDifficulty = 
            (d1, d2) -> Integer.compare(d2.getRadius(), d1.getRadius());
        
        this.hintQueue = new PriorityQueue<>(byDifficulty);
        this.hintQueue.addAll(differences);
    }
    
    public Difference getEasiestHint() {
        return hintQueue.poll();
    }
}
```

### Adding a TreeMap Feature

Implement level difficulty-based access:

```java
package com.spotdifference.manager;

import java.util.TreeMap;
import com.spotdifference.model.LevelData;

public class DifficultyBasedLevelManager {
    private TreeMap<Integer, List<LevelData>> levelsByDifficulty;
    
    public DifficultyBasedLevelManager() {
        this.levelsByDifficulty = new TreeMap<>();
    }
    
    public void addLevel(LevelData level) {
        int difficulty = level.getDifficulty();
        levelsByDifficulty
            .computeIfAbsent(difficulty, k -> new ArrayList<>())
            .add(level);
    }
    
    public List<LevelData> getLevelsByDifficulty(int difficulty) {
        return levelsByDifficulty.get(difficulty);
    }
    
    public Map<Integer, List<LevelData>> getAllLevelsSorted() {
        return new TreeMap<>(levelsByDifficulty);
    }
}
```

---

## Testing Guidelines

### Manual Testing Checklist

**Level Loading:**
- [ ] All levels load without errors
- [ ] Images display correctly
- [ ] Difference markers appear on clicks

**Game Mechanics:**
- [ ] Correct clicks register properly
- [ ] Wrong clicks deduct points
- [ ] Hints show correct locations
- [ ] Undo restores previous state
- [ ] Level completion triggers properly

**UI Navigation:**
- [ ] Main menu buttons work
- [ ] Level selection shows all levels
- [ ] Back buttons return to previous screen
- [ ] High scores display correctly

**Data Persistence:**
- [ ] High scores save between sessions
- [ ] Level completion status persists
- [ ] Graph unlocks remain after restart

### Unit Test Example

Create `src/test/java/com/spotdifference/logic/DifferenceCheckerTest.java`:

```java
package com.spotdifference.logic;

import com.spotdifference.model.Difference;
import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Point;
import java.util.Arrays;

public class DifferenceCheckerTest {
    
    @Test
    public void testCorrectClick() {
        Difference diff = new Difference(100, 100, 30);
        DifferenceChecker checker = new DifferenceChecker(
            Arrays.asList(diff)
        );
        
        // Click within radius
        Point click = new Point(105, 105);
        Difference found = checker.checkClick(click);
        
        assertNotNull("Should find difference", found);
        assertEquals(0, checker.getRemainingCount());
        assertTrue(checker.isLevelComplete());
    }
    
    @Test
    public void testIncorrectClick() {
        Difference diff = new Difference(100, 100, 30);
        DifferenceChecker checker = new DifferenceChecker(
            Arrays.asList(diff)
        );
        
        // Click outside radius
        Point click = new Point(200, 200);
        Difference found = checker.checkClick(click);
        
        assertNull("Should not find difference", found);
        assertEquals(1, checker.getRemainingCount());
        assertFalse(checker.isLevelComplete());
    }
}
```

Run tests:
```bash
mvn test
```

---

## Code Style Guidelines

### Naming Conventions

- **Classes**: PascalCase (`DifferenceChecker`)
- **Methods**: camelCase (`checkClick()`)
- **Variables**: camelCase (`remainingDifferences`)
- **Constants**: UPPER_SNAKE_CASE (`MAX_HINTS`)
- **Packages**: lowercase (`com.spotdifference.logic`)

### Documentation

Always include Javadoc for public classes and methods:

```java
/**
 * Checks if a click point matches any remaining difference.
 * Uses HashSet for O(1) average-time validation.
 * 
 * @param clickPoint The point where the player clicked
 * @return The matched Difference object, or null if no match
 */
public Difference checkClick(Point clickPoint) {
    // implementation
}
```

### Best Practices

1. **Single Responsibility**: Each class should do one thing
2. **Immutability**: Return copies of collections, not originals
3. **Null Safety**: Always check for null before using objects
4. **Error Handling**: Use try-catch for I/O operations
5. **Resource Management**: Close streams and timers properly

---

## Performance Optimization

### Current Complexities

| Operation | Data Structure | Time Complexity |
|-----------|---------------|----------------|
| Load level | HashMap | O(1) |
| Check click | HashSet | O(1) average |
| Get hint | Queue | O(1) |
| Undo move | Stack | O(1) |
| Add score | LinkedList | O(n) |
| Unlock level | Graph | O(1) |

### Optimization Tips

1. **Image Loading**: Pre-load images instead of loading on-demand
2. **Click Detection**: Use spatial indexing for many differences
3. **UI Updates**: Batch updates to reduce repaints
4. **Serialization**: Use more efficient formats (JSON, Protocol Buffers)

---

## Debugging Tips

### Enable Debug Logging

Add at the start of `Main.java`:

```java
public static void main(String[] args) {
    System.setProperty("debug", "true");
    // ... rest of code
}
```

Then in your code:

```java
if (System.getProperty("debug") != null) {
    System.out.println("Click detected at: " + clickPoint);
}
```

### Common Issues

**Issue**: Click not registering  
**Fix**: Check difference radius and coordinates

**Issue**: Level not unlocking  
**Fix**: Verify graph edges in `LevelProgressionGraph`

**Issue**: High scores not saving  
**Fix**: Check file permissions for `highscores.dat`

---

## Contributing

When contributing code:

1. Create a feature branch
2. Write clean, documented code
3. Test thoroughly
4. Update relevant documentation
5. Submit a pull request

---

## Resources

- **Java Swing Tutorial**: [Oracle Docs](https://docs.oracle.com/javase/tutorial/uiswing/)
- **Data Structures**: [GeeksforGeeks](https://www.geeksforgeeks.org/data-structures/)
- **Maven Guide**: [Apache Maven](https://maven.apache.org/guides/)

---

**Happy coding!** 🚀


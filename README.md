# 🎮 Spot the Difference Game

An interactive "Spot the Difference" game built in Java with a graphical user interface, designed to demonstrate the practical application and efficiency of fundamental data structures.

## 📋 Project Overview

This educational game showcases **six fundamental data structures** through engaging gameplay:

- **HashMap** - Level Management and Selection
- **HashSet** - Real-time Difference Checking
- **Stack** - Undo Move System
- **Queue** - Hint System
- **LinkedList** - High Score Leaderboard
- **Graph** - Non-Linear Level Progression

Players find differences between two nearly identical images while the game demonstrates how each data structure contributes to smooth, efficient gameplay.

## 🎯 Features

### Core Gameplay
- **5 Levels** with increasing difficulty (Beach → Jungle → City → Space → Fantasy)
- **5-15 differences** per level depending on difficulty
- **Real-time click validation** using HashSet (O(1) performance)
- **Score system** with time bonuses and penalties

### Game Mechanics
- **💡 Hint System** - Queue-based FIFO hint delivery (max 3 per level)
- **↶ Undo System** - Stack-based LIFO move reversal (max 5 per level)
- **🏆 High Score Leaderboard** - LinkedList-based persistent scoring
- **🗺️ Graph Progression** - Non-linear level unlocking system

### User Interface
- Clean, modern GUI built with Java Swing
- Visual difference markers
- Real-time score and timer display
- Pause/Resume functionality
- Level completion tracking

## 🏗️ Project Structure

```
Spot-The-Difference/
├── src/main/java/com/spotdifference/
│   ├── Main.java                          # Application entry point
│   ├── model/                             # Data models
│   │   ├── Difference.java                # Difference representation
│   │   ├── LevelData.java                 # Level configuration
│   │   ├── PlayerScore.java               # Score entry
│   │   └── LevelNode.java                 # Graph node for progression
│   ├── logic/                             # Core game logic
│   │   ├── DifferenceChecker.java         # HashSet-based validation
│   │   ├── UndoManager.java               # Stack-based undo system
│   │   └── HintManager.java               # Queue-based hint system
│   ├── manager/                           # System managers
│   │   ├── LevelManager.java              # HashMap-based level storage
│   │   ├── LevelProgressionGraph.java     # Graph-based progression
│   │   └── HighScoreManager.java          # LinkedList-based leaderboard
│   └── ui/                                # GUI components
│       ├── MainMenuFrame.java             # Main menu screen
│       ├── LevelSelectionFrame.java       # Level selection screen
│       ├── GameScreen.java                # Main gameplay screen
│       └── HighScoresFrame.java           # Leaderboard display
├── resources/images/                      # Game images
├── docs/                                  # Documentation
│   └── DATA_STRUCTURES.md                 # Detailed DS explanation
├── pom.xml                                # Maven configuration
└── README.md                              # This file
```

## 🚀 Getting Started

> **📖 For detailed run instructions, see [HOW_TO_RUN.md](HOW_TO_RUN.md)**

### Prerequisites

- **Java Development Kit (JDK) 11 or higher**
- **Apache Maven 3.6+** (for building)
- **Git** (for cloning the repository)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/Spot-The-Difference.git
   cd Spot-The-Difference
   ```

2. **Build the project with Maven**
   ```bash
   mvn clean package
   ```

3. **Run the game**
   ```bash
   java -jar target/SpotTheDifference.jar
   ```

   **Alternative (using Maven):**
   ```bash
   mvn exec:java -Dexec.mainClass="com.spotdifference.Main"
   ```

### Running from IDE

1. Import the project as a Maven project
2. Navigate to `src/main/java/com/spotdifference/Main.java`
3. Run the `main()` method

## 🎮 How to Play

### Main Menu
- **Start Game** - Opens level selection
- **High Scores** - View the leaderboard
- **Exit** - Close the application

### Level Selection
- Choose from available levels
- Locked levels unlock as you complete prerequisites
- Difficulty indicated by star rating (1-5 stars)

### Gameplay
1. **Find Differences**: Click on differences in either image
2. **Use Hints**: Click "💡 Hint" button (limited to 3 per level)
3. **Undo Moves**: Click "↶ Undo" to reverse last find (limited to 5)
4. **Complete Level**: Find all differences to unlock new levels

### Scoring System
- **+100 points** per difference found
- **-5 points** for incorrect clicks
- **-20 points** per hint used
- **-50 points** per undo used
- **Time bonus** on completion (faster = more points)

## 📊 Data Structures Explained

### 1. HashMap - Level Management
**File**: `LevelManager.java`

Stores all level data with level name as key for O(1) retrieval.

```java
HashMap<String, LevelData> levels;
LevelData level = levels.get("Beach Scene"); // Instant access
```

### 2. HashSet - Difference Checking
**File**: `DifferenceChecker.java`

Validates clicks against remaining differences in O(1) average time.

```java
HashSet<Difference> remainingDifferences;
Difference found = checkClick(clickPoint); // Fast validation
```

### 3. Stack - Undo System
**File**: `UndoManager.java`

Implements LIFO principle for reversing recent moves.

```java
Stack<Difference> undoStack;
undoStack.push(found);      // Save move
Difference last = undoStack.pop(); // Undo (LIFO)
```

### 4. Queue - Hint System
**File**: `HintManager.java`

Implements FIFO principle for ordered hint delivery.

```java
Queue<Difference> hintQueue;
Difference hint = hintQueue.poll(); // Get next hint (FIFO)
```

### 5. LinkedList - High Scores
**File**: `HighScoreManager.java`

Maintains sorted leaderboard with flexible insertion.

```java
LinkedList<PlayerScore> highScores;
// Insert at correct position to maintain sorted order
highScores.add(position, newScore);
```

### 6. Graph - Level Progression
**File**: `LevelProgressionGraph.java`

Models level relationships and unlocking dependencies.

```java
// Beach unlocks Jungle and City (graph edges)
beach.addAdjacentLevel(jungle);
beach.addAdjacentLevel(city);
beach.markCompleted(); // Unlocks adjacent levels
```

For detailed explanations, see [DATA_STRUCTURES.md](docs/DATA_STRUCTURES.md).

## 🖼️ Adding Custom Images

The game currently uses placeholder images. To add your own "Spot the Difference" images:

1. Create image pairs (550x500 pixels recommended)
2. Place them in `resources/images/` directory:
   - `beach1.jpg` / `beach2.jpg`
   - `jungle1.jpg` / `jungle2.jpg`
   - `city1.jpg` / `city2.jpg`
   - `space1.jpg` / `space2.jpg`
   - `fantasy1.jpg` / `fantasy2.jpg`

3. Update difference coordinates in `LevelManager.java`:
   ```java
   beach.addDifference(x, y, radius);
   ```

## 🎓 Educational Value

This project demonstrates:

- **Data Structure Selection** - Choosing the right DS for each problem
- **Time Complexity** - Understanding O(1), O(n) operations
- **Object-Oriented Design** - Separation of concerns, encapsulation
- **GUI Programming** - Java Swing components and event handling
- **File I/O** - Saving/loading high scores
- **Graph Theory** - Adjacency lists, BFS traversal

## 👥 User Roles

### Player
- Select and play levels
- Find differences
- Use hints and undo features
- View and submit high scores

### Developer/Administrator
- Add new levels
- Configure difficulty settings
- Design level progression graph
- Define difference coordinates

## 🔄 Game Flow

```
Start
  ↓
Main Menu → [Exit]
  ↓
Level Selection (Graph-based)
  ↓
Gameplay Loop
  ├─→ Click Image (HashSet check)
  ├─→ Use Hint (Queue poll)
  ├─→ Undo Move (Stack pop)
  └─→ Level Complete?
       ↓
     [Yes]
       ↓
  Update Graph (unlock levels)
       ↓
  Check High Score (LinkedList insert)
       ↓
  Return to Level Selection
```

## 🛠️ Technologies Used

- **Language**: Java 11
- **GUI Framework**: Java Swing
- **Build Tool**: Apache Maven
- **Version Control**: Git

## 📝 System Requirements

- **OS**: Windows, macOS, or Linux
- **RAM**: 512 MB minimum
- **Disk Space**: 50 MB
- **Display**: 1280x720 minimum resolution

## 🤝 Contributing

This is an educational project. Contributions are welcome!

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is created for educational purposes. Feel free to use it for learning!

## 🎯 Future Enhancements

- [ ] Timer-based challenges
- [ ] Multiplayer mode
- [ ] Online leaderboards
- [ ] More levels and themes
- [ ] Sound effects and music
- [ ] Difficulty customization
- [ ] Achievement system
- [ ] Mobile version

## 📧 Contact

For questions or feedback about this educational project, please open an issue on GitHub.

---

**Made with ❤️ to demonstrate Data Structures in action!**

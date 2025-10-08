# Quick Start Guide

Get the Spot the Difference game running in 5 minutes!

## Prerequisites Check

Before starting, ensure you have:

```bash
# Check Java version (need 11+)
java -version

# Check Maven (need 3.6+)
mvn -version

# Check Git
git --version
```

If you don't have these installed:
- **Java**: Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Maven**: Download from [Apache Maven](https://maven.apache.org/download.cgi)
- **Git**: Download from [git-scm.com](https://git-scm.com/)

---

## Installation (3 Steps)

### Step 1: Clone the Repository
```bash
git clone https://github.com/yourusername/Spot-The-Difference.git
cd Spot-The-Difference
```

### Step 2: Build the Project
```bash
mvn clean package
```

This will:
- Compile all Java files
- Run tests (if any)
- Create an executable JAR file

### Step 3: Run the Game
```bash
java -jar target/SpotTheDifference.jar
```

**That's it! The game should now be running.**

---

## Alternative: Run from IDE

### IntelliJ IDEA
1. File → Open → Select project folder
2. Wait for Maven import to complete
3. Find `Main.java` in `src/main/java/com/spotdifference/`
4. Right-click → Run 'Main.main()'

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select project folder
3. Wait for import
4. Find `Main.java`
5. Right-click → Run As → Java Application

### VS Code
1. File → Open Folder → Select project
2. Install "Java Extension Pack" if prompted
3. Open `Main.java`
4. Click "Run" above the `main()` method

---

## Troubleshooting

### "Java version not supported"
**Problem**: Your Java version is too old  
**Solution**: Install Java 11 or higher

```bash
# Check version
java -version

# Should show version 11 or higher
```

### "mvn: command not found"
**Problem**: Maven not installed or not in PATH  
**Solution**: 
- Install Maven from [maven.apache.org](https://maven.apache.org/)
- Add to PATH environment variable

### "Cannot find or load main class"
**Problem**: JAR not built correctly  
**Solution**: 
```bash
mvn clean package
```

### Game window doesn't appear
**Problem**: Display or GUI issues  
**Solution**:
- Make sure you're not running on a headless server
- Check display resolution (minimum 1280x720)
- Try running with `-Djava.awt.headless=false` flag

---

## Quick Play Guide

### 1. Main Menu
When the game starts, you'll see three buttons:
- **Start Game** → Play levels
- **High Scores** → View leaderboard
- **Exit** → Close game

### 2. Level Selection
- Click on any **unlocked** level to play
- Complete levels to unlock new ones
- First level (Beach Scene) is always unlocked

### 3. Gameplay
- **Goal**: Find all differences between the two images
- **Click** on differences in either image
- **Hint** (💡): Shows you one difference (limited to 3)
- **Undo** (↶): Reverses your last find (limited to 5)
- **Pause** (⏸): Pause the game

### 4. Scoring
- **+100** per difference found
- **-5** per wrong click
- **-20** per hint used
- **-50** per undo used
- **+0 to +500** time bonus on completion

---

## Project Structure Quick Reference

```
Spot-The-Difference/
├── src/main/java/           # Java source code
│   └── com/spotdifference/
│       ├── Main.java        # START HERE
│       ├── model/           # Data classes
│       ├── logic/           # Game logic
│       ├── manager/         # Managers
│       └── ui/              # GUI
├── resources/               # Images & resources
├── docs/                    # Documentation
├── pom.xml                  # Maven config
└── README.md                # Full documentation
```

---

## Data Structures at a Glance

| Structure | Where Used | Why |
|-----------|------------|-----|
| **HashMap** | Level loading | O(1) level lookup |
| **HashSet** | Click checking | O(1) validation |
| **Stack** | Undo system | LIFO reversals |
| **Queue** | Hint system | FIFO delivery |
| **LinkedList** | High scores | Sorted insertion |
| **Graph** | Level progression | Unlock paths |

---

## Next Steps

1. **Play the game** - Complete all 5 levels!
2. **Read the code** - Start with `Main.java`
3. **Understand data structures** - See `docs/DATA_STRUCTURES.md`
4. **Add custom images** - See "Adding Custom Images" in README
5. **Modify levels** - Edit `LevelManager.java`

---

## Useful Commands

```bash
# Build without tests
mvn clean package -DskipTests

# Run with Maven
mvn exec:java -Dexec.mainClass="com.spotdifference.Main"

# Clean build artifacts
mvn clean

# View dependency tree
mvn dependency:tree

# Generate Javadoc
mvn javadoc:javadoc
```

---

## Getting Help

- **Full documentation**: See [README.md](README.md)
- **Data structures explained**: See [docs/DATA_STRUCTURES.md](docs/DATA_STRUCTURES.md)
- **Game design**: See [docs/GAME_DESIGN.md](docs/GAME_DESIGN.md)
- **Issues**: Check GitHub Issues
- **Code**: Read the source - it's well-commented!

---

## Development Mode

Want to modify the code?

1. **Make changes** to Java files
2. **Rebuild**: `mvn clean package`
3. **Run**: `java -jar target/SpotTheDifference.jar`
4. **Repeat**

Or use your IDE's built-in run feature for faster iteration.

---

**Happy gaming and learning!** 🎮📚

For detailed information, see the [full README](README.md).


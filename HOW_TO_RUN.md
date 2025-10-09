# 🎮 How to Run the Spot the Difference Game

## Quick Start (For Team Members)

### ✅ Prerequisites
You need these installed on your computer:
1. **Java 11 or higher** (Java 21 recommended)
2. **Maven 3.6+**

---

## 🚀 Method 1: Easy Way (Windows)

Just double-click or run:
```powershell
run.bat
```

That's it! The game will build and launch automatically.

---

## 🚀 Method 2: Easy Way (Mac/Linux)

In terminal:
```bash
chmod +x run.sh
./run.sh
```

Done! Game will start.

---

## 🚀 Method 3: Manual Steps

### Step 1: Open Terminal/Command Prompt

**Windows**: Press `Win + R`, type `cmd`, press Enter  
**Mac**: Press `Cmd + Space`, type `terminal`, press Enter  
**Linux**: Press `Ctrl + Alt + T`

### Step 2: Navigate to Project Folder

```bash
cd "path/to/Spot-The-Difference"
```

Example:
```bash
cd "C:\Users\acer\Documents\project room\Spot-The-Difference"
```

### Step 3: Build the Project

```bash
mvn clean package
```

Wait for it to say **"BUILD SUCCESS"**

### Step 4: Run the Game

```bash
java -jar target/SpotTheDifference.jar
```

🎉 **The game window should appear!**

---

## 🛠️ Method 4: Run from IDE

### IntelliJ IDEA
1. Open the project folder in IntelliJ
2. Wait for Maven to import (bottom right corner)
3. Find `Main.java` in `src/main/java/com/spotdifference/`
4. Right-click on `Main.java` → **Run 'Main.main()'**

### Eclipse
1. File → Import → Maven → Existing Maven Projects
2. Select the project folder
3. Wait for import to complete
4. Find `Main.java`
5. Right-click → **Run As → Java Application**

### VS Code
1. File → Open Folder → Select project
2. Install "Java Extension Pack" if prompted
3. Open `Main.java`
4. Click **"Run"** button above the `main()` method

---

## ❓ Troubleshooting

### "Java is not recognized"
**Problem**: Java not installed or not in PATH  
**Solution**: 
1. Download Java from https://www.oracle.com/java/technologies/downloads/
2. Install it
3. Restart your terminal/command prompt

**Check if Java is installed:**
```bash
java -version
```
Should show version 11 or higher.

### "mvn is not recognized"
**Problem**: Maven not installed  
**Solution**:
1. Download Maven from https://maven.apache.org/download.cgi
2. Install it
3. Add to PATH
4. Restart terminal

**Check if Maven is installed:**
```bash
mvn -version
```

### "Cannot find or load main class"
**Problem**: Project not built properly  
**Solution**:
```bash
mvn clean package
```
Then try running again.

### "UnsupportedClassVersionError"
**Problem**: Java version too old  
**Solution**: 
- Update to Java 11 or higher
- Or use the Java that Maven is using

### Game window doesn't appear
**Problem**: Display issues  
**Solution**:
- Make sure you're not on a headless server
- Check display resolution (minimum 1280x720)
- Try restarting computer

---

## 📋 System Requirements

- **OS**: Windows 10/11, macOS 10.14+, Linux (any recent distro)
- **Java**: Version 11 or higher (21 recommended)
- **Maven**: Version 3.6 or higher
- **RAM**: 512 MB minimum
- **Disk Space**: 50 MB
- **Display**: 1280x720 minimum resolution

---

## 🎮 How to Play

Once the game starts:

1. **Main Menu** appears with 3 options:
   - **Start Game** - Play levels
   - **High Scores** - View leaderboard  
   - **Exit** - Close game

2. **Click "Start Game"**
   - Select an unlocked level
   - Beach Scene is unlocked by default

3. **Gameplay**:
   - Find differences by clicking on them (in either image)
   - Use **Hint** button (💡) if stuck (max 3 per level)
   - Use **Undo** button (↶) to reverse last find (max 5)
   - **Pause** button (⏸) to pause

4. **Scoring**:
   - +100 points per difference found
   - -5 points for wrong clicks
   - -20 points per hint used
   - -50 points per undo used
   - Time bonus when complete (faster = more points)

5. **Complete the level** to unlock new levels!

---

## 🏗️ For Developers

### Build Only (No Run)
```bash
mvn clean compile
```

### Build with Tests
```bash
mvn clean test
```

### Create JAR file
```bash
mvn clean package
```
JAR will be in `target/SpotTheDifference.jar`

### Clean Build Files
```bash
mvn clean
```

### Run Without Building JAR
```bash
mvn exec:java -Dexec.mainClass="com.spotdifference.Main"
```

---

## 📁 Project Structure

```
Spot-The-Difference/
├── src/main/java/          # Java source code
│   └── com/spotdifference/
│       ├── Main.java        # Run this!
│       ├── model/
│       ├── logic/
│       ├── manager/
│       └── ui/
├── resources/               # Images and resources
├── target/                  # Compiled files (auto-generated)
│   └── SpotTheDifference.jar
├── pom.xml                  # Maven configuration
├── run.bat                  # Windows launcher
└── run.sh                   # Mac/Linux launcher
```

---

## 🔗 Quick Reference Commands

| Task | Command |
|------|---------|
| Build project | `mvn clean package` |
| Run game | `java -jar target/SpotTheDifference.jar` |
| Build & Run (Windows) | `run.bat` |
| Build & Run (Mac/Linux) | `./run.sh` |
| Check Java version | `java -version` |
| Check Maven version | `mvn -version` |

---

## 📚 More Help

- **Full Documentation**: See [README.md](README.md)
- **Quick Start Guide**: See [QUICKSTART.md](QUICKSTART.md)
- **Adding Custom Images**: See [docs/HOW_TO_ADD_IMAGES.md](docs/HOW_TO_ADD_IMAGES.md)
- **Developer Guide**: See [docs/DEVELOPER_GUIDE.md](docs/DEVELOPER_GUIDE.md)

---

## 💬 Common Questions

**Q: Do I need to install anything else?**  
A: Just Java and Maven. Everything else is included.

**Q: Can I run it on Mac/Linux?**  
A: Yes! Use `./run.sh` or the manual steps.

**Q: Where are the images?**  
A: The game uses placeholder images. See [docs/HOW_TO_ADD_IMAGES.md](docs/HOW_TO_ADD_IMAGES.md) to add real images.

**Q: How do I share this with others?**  
A: Just send them the entire project folder. They can run it with the steps above.

**Q: Can I make an executable?**  
A: Yes! The JAR file (`target/SpotTheDifference.jar`) is executable. Just double-click it (if Java is installed).

---

## ✅ Checklist for First-Time Setup

- [ ] Install Java 11+ 
- [ ] Install Maven 3.6+
- [ ] Clone/download the project
- [ ] Open terminal in project folder
- [ ] Run `mvn clean package`
- [ ] Run `java -jar target/SpotTheDifference.jar`
- [ ] See the game window appear!
- [ ] Click "Start Game" and play!

---

**Need help?** Ask your team lead or check the documentation files!

**Have fun playing! 🎮**


# How to Add Real Images to Your Game

This guide shows you exactly how to add your own images and mark differences.

---

## 📸 Step 1: Prepare Your Images

### Option A: Download Free Images
1. Google: **"spot the difference images printable"**
2. Download a pair of images
3. Make sure they're the **same size** (ideally 550x500 pixels)

### Option B: Create Your Own
1. Find or take a photo
2. Open in **Paint**, **Photoshop**, or **GIMP**
3. Make a copy of the image
4. In the copy, make small changes:
   - Remove or add small objects
   - Change colors
   - Move items slightly
   - Alter shapes
5. Save both versions

**Example:**
- `beach1.jpg` - Original
- `beach2.jpg` - Modified (with 5-10 differences)

---

## 📁 Step 2: Add Images to Project

1. **Copy your images** into the `resources/images/` folder
   ```
   resources/images/
   ├── beach1.jpg
   ├── beach2.jpg
   ├── jungle1.jpg
   └── jungle2.jpg
   ```

2. **Name them clearly**:
   - First image: `levelname1.jpg` (original)
   - Second image: `levelname2.jpg` (with differences)

---

## 🎯 Step 3: Find Difference Coordinates

### Use the Coordinate Finder Tool!

I created a special tool to help you find coordinates easily.

#### How to Run the Tool:

**Option 1: From Command Line**
```bash
# Compile the tool
javac -d target/classes src/main/java/com/spotdifference/tools/CoordinateFinder.java

# Run it
java -cp target/classes com.spotdifference.tools.CoordinateFinder
```

**Option 2: From Your IDE**
1. Open `src/main/java/com/spotdifference/tools/CoordinateFinder.java`
2. Right-click in the file
3. Select **"Run As" → "Java Application"**

#### Using the Tool:

1. **Click "Load Images"** button
2. **Select your first image** (original)
3. **Select your second image** (with differences)
4. **Click on each difference** in the LEFT image
   - You'll see a red circle appear
   - A number shows which difference it is
   - The same spot highlights on the RIGHT image
5. **Made a mistake?** Click "Undo Last"
6. **Done?** Click "Print Code"
7. **Copy the generated code** from the dialog box

**Example Output:**
```java
level.addDifference(150, 200, 30);  // Difference 1
level.addDifference(300, 150, 25);  // Difference 2
level.addDifference(450, 300, 30);  // Difference 3
```

---

## 🔧 Step 4: Update LevelManager.java

1. **Open** `src/main/java/com/spotdifference/manager/LevelManager.java`

2. **Find** the `initializeLevels()` method

3. **Add or modify** a level:

```java
private void initializeLevels() {
    // Create new level
    LevelData myLevel = new LevelData(
        "My Custom Level",              // Level name
        "resources/images/beach1.jpg",  // First image path
        "resources/images/beach2.jpg",  // Second image path
        2                               // Difficulty (1-5)
    );
    
    // Paste the coordinates from the Coordinate Finder Tool here!
    myLevel.addDifference(150, 200, 30);  // Difference 1
    myLevel.addDifference(300, 150, 25);  // Difference 2
    myLevel.addDifference(450, 300, 30);  // Difference 3
    myLevel.addDifference(200, 400, 25);  // Difference 4
    myLevel.addDifference(500, 250, 30);  // Difference 5
    
    // Add to HashMap
    levels.put("My Custom Level", myLevel);
}
```

**Understanding the Parameters:**
- `addDifference(x, y, radius)`
  - **x**: Horizontal position (pixels from left)
  - **y**: Vertical position (pixels from top)
  - **radius**: Click detection area (20-35 recommended)
    - Smaller radius = harder to click
    - Larger radius = easier to click

---

## 🗺️ Step 5: Add to Level Progression (Optional)

If you want your level in the progression graph:

1. **Open** `src/main/java/com/spotdifference/manager/LevelProgressionGraph.java`

2. **Find** `initializeGraph()` method

3. **Add your level**:

```java
private void initializeGraph() {
    // ... existing levels ...
    
    // Create node for your level
    LevelNode myLevel = new LevelNode("My Custom Level", false);
    
    // Connect it to existing levels
    beach.addAdjacentLevel(myLevel);  // Beach unlocks your level
    
    // Add to the map
    levelNodes.put("My Custom Level", myLevel);
}
```

---

## 🚀 Step 6: Test Your Level

1. **Rebuild the project**:
   ```bash
   mvn clean package
   ```

2. **Run the game**:
   ```bash
   java -jar target/SpotTheDifference.jar
   ```

3. **Test**:
   - Navigate to your level
   - Try clicking on differences
   - Make sure all work correctly
   - Adjust radius if clicks don't register well

---

## 💡 Pro Tips

### Finding Good Coordinates

1. **Use the Coordinate Finder Tool** - most accurate!
2. **Alternative**: Open image in Paint
   - Move cursor over difference
   - Look at bottom-left corner for coordinates
   - Write them down

### Choosing Radius

- **Large differences** (obvious): radius 25-35
- **Medium differences**: radius 20-25
- **Small differences** (hard): radius 15-20

### Image Quality

- **Higher resolution** = better visual quality
- **Recommended size**: 550x500 pixels
- **Supported formats**: JPG, PNG, GIF, BMP

### Common Mistakes

❌ **Wrong path**: `images/beach1.jpg` → ✅ `resources/images/beach1.jpg`
❌ **Wrong order**: Image2 first → ✅ Image1 (original) first
❌ **Typo in name**: "Beach Scene" in manager vs "Beach" in graph → ✅ Use exact same name
❌ **Coordinates off**: Not clicking exact spot → ✅ Use Coordinate Finder Tool

---

## 📝 Example: Complete Custom Level

Here's a complete example from start to finish:

### 1. Images Ready
```
resources/images/
├── sunset1.jpg  (original)
└── sunset2.jpg  (5 differences)
```

### 2. Run Coordinate Finder
- Click on all 5 differences
- Get coordinates

### 3. Update LevelManager.java
```java
LevelData sunset = new LevelData(
    "Sunset Beach",
    "resources/images/sunset1.jpg",
    "resources/images/sunset2.jpg",
    3  // Medium difficulty
);

// From Coordinate Finder Tool:
sunset.addDifference(125, 180, 30);
sunset.addDifference(340, 220, 25);
sunset.addDifference(210, 390, 28);
sunset.addDifference(480, 160, 30);
sunset.addDifference(290, 310, 25);

levels.put("Sunset Beach", sunset);
```

### 4. Update LevelProgressionGraph.java (optional)
```java
LevelNode sunset = new LevelNode("Sunset Beach", false);
city.addAdjacentLevel(sunset);  // Unlocked after City
levelNodes.put("Sunset Beach", sunset);
```

### 5. Rebuild & Test
```bash
mvn clean package
java -jar target/SpotTheDifference.jar
```

---

## 🎯 Quick Reference

| Task | Tool/File |
|------|-----------|
| Get images | Google, create your own |
| Store images | `resources/images/` |
| Find coordinates | `CoordinateFinder.java` |
| Add level data | `LevelManager.java` |
| Add to graph | `LevelProgressionGraph.java` |
| Test | Run the game! |

---

## ❓ Troubleshooting

**Problem**: Images don't show up  
**Fix**: Check file path is `resources/images/filename.jpg`

**Problem**: Clicks don't register  
**Fix**: 
- Verify coordinates are correct
- Increase radius (try 30-35)
- Use Coordinate Finder Tool

**Problem**: Images are distorted  
**Fix**: Make both images same size before adding

**Problem**: Can't find Coordinate Finder  
**Fix**: Make sure you compiled it first

---

**Need more help?** Check the main README.md or DEVELOPER_GUIDE.md!

Happy level creating! 🎮✨


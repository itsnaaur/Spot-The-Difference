# 🎮 Game Levels & Progression Guide

## 📊 **Complete Level Overview**

Your game has **5 levels** with increasing difficulty. Here's everything about them:

---

## 🏖️ **Level 1: Beach Scene**

**Difficulty:** ★☆☆☆☆ (Easy)  
**Status:** 🔓 **UNLOCKED** (available from start)  
**Total Differences:** **5**  
**Click Radius:** 25-30 pixels (easier to click)

**What makes it easy:**
- Only 5 differences to find
- Larger click radius (30 pixels) = easier to hit
- First level - introduces the game mechanics

**Images:**
- `resources/images/beach1.jpg` (original)
- `resources/images/beach2.jpg` (with differences)

**Unlocks when completed:**
- ✅ **Jungle Scene**
- ✅ **City Scene**

---

## 🌴 **Level 2: Jungle Scene**

**Difficulty:** ★★☆☆☆ (Medium)  
**Status:** 🔒 **LOCKED** (unlocked after Beach Scene)  
**Total Differences:** **7** (+2 more than Beach)  
**Click Radius:** 25-30 pixels

**What makes it medium:**
- 7 differences to find (40% more than Beach)
- Still has reasonable click radius
- Good progression step

**Images:**
- `resources/images/jungle1.jpg` (original)
- `resources/images/jungle2.jpg` (with differences)

**Unlocks when completed:**
- ✅ **Space Scene**

---

## 🏙️ **Level 3: City Scene**

**Difficulty:** ★★★☆☆ (Hard)  
**Status:** 🔒 **LOCKED** (unlocked after Beach Scene)  
**Total Differences:** **10** (+5 more than Beach)  
**Click Radius:** 20-25 pixels (harder to click!)

**What makes it hard:**
- 10 differences to find (double Beach Scene!)
- Smaller click radius (20 pixels) = more precise clicks needed
- More challenging progression

**Images:**
- `resources/images/city1.jpg` (original)
- `resources/images/city2.jpg` (with differences)

**Unlocks when completed:**
- ✅ **Space Scene**

---

## 🚀 **Level 4: Space Scene**

**Difficulty:** ★★★★☆ (Expert)  
**Status:** 🔒 **LOCKED** (unlocked after Jungle OR City)  
**Total Differences:** **12** (+7 more than Beach)  
**Click Radius:** 20-25 pixels

**What makes it expert:**
- 12 differences to find (nearly triple Beach!)
- Smaller click radius = precision required
- Requires completing at least one medium/hard level first

**Images:**
- `resources/images/space1.jpg` (original)
- `resources/images/space2.jpg` (with differences)

**Unlocks when completed:**
- ✅ **Fantasy Scene**

---

## 🧙 **Level 5: Fantasy Scene**

**Difficulty:** ★★★★★ (Master)  
**Status:** 🔒 **LOCKED** (unlocked after Space Scene)  
**Total Differences:** **15** (THREE TIMES Beach!)  
**Click Radius:** 18-22 pixels (smallest/hardest!)

**What makes it master:**
- 15 differences to find (the most in the game!)
- Smallest click radius (18 pixels) = very precise clicks needed
- Final challenge - ultimate test of skill
- Requires completing entire progression chain

**Images:**
- `resources/images/fantasy1.jpg` (original)
- `resources/images/fantasy2.jpg` (with differences)

**Unlocks when completed:**
- 🏆 **Nothing - You've beaten the game!**

---

## 🗺️ **Level Progression Map**

Here's how the levels connect (uses **Graph** data structure):

```
                    ┌─────────────────┐
                    │  🏖️ Beach Scene  │
                    │    ★☆☆☆☆         │
                    │  5 differences   │
                    │   [UNLOCKED]     │
                    └────────┬─────────┘
                             │
                 ┌───────────┴───────────┐
                 │                       │
                 ▼                       ▼
         ┌──────────────┐        ┌──────────────┐
         │🌴 Jungle Scene│        │🏙️ City Scene  │
         │   ★★☆☆☆       │        │   ★★★☆☆       │
         │ 7 differences │        │ 10 differences│
         │   [LOCKED]    │        │   [LOCKED]    │
         └───────┬───────┘        └───────┬───────┘
                 │                        │
                 └───────────┬────────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │ 🚀 Space Scene    │
                    │    ★★★★☆          │
                    │  12 differences   │
                    │    [LOCKED]       │
                    └─────────┬─────────┘
                              │
                              ▼
                    ┌──────────────────┐
                    │ 🧙 Fantasy Scene  │
                    │    ★★★★★          │
                    │  15 differences   │
                    │    [LOCKED]       │
                    └──────────────────┘
```

---

## 🎯 **Key Differences Between Levels**

| Level | Difficulty | Stars | Differences | Radius | Challenge Level |
|-------|-----------|-------|-------------|--------|----------------|
| Beach | Easy | ★☆☆☆☆ | 5 | 25-30px | Beginner |
| Jungle | Medium | ★★☆☆☆ | 7 | 25-30px | Intermediate |
| City | Hard | ★★★☆☆ | 10 | 20-25px | Advanced |
| Space | Expert | ★★★★☆ | 12 | 20-25px | Expert |
| Fantasy | Master | ★★★★★ | 15 | 18-22px | Master |

### **What Changes:**

1. **Number of Differences** ⬆️
   - Beach: 5 → Jungle: 7 → City: 10 → Space: 12 → Fantasy: 15
   - Each level adds more differences to find

2. **Click Radius** ⬇️
   - Easy levels: 30 pixels (bigger target, easier to click)
   - Medium levels: 25 pixels
   - Hard+ levels: 18-22 pixels (smaller target, must click precisely)

3. **Unlock Requirements** 🔐
   - Beach: Always unlocked
   - Jungle & City: Complete Beach
   - Space: Complete Jungle OR City (multiple paths!)
   - Fantasy: Complete Space (complete the chain)

---

## 🔓 **Progression Flow**

### **Scenario 1: Linear Path**
```
Beach → Jungle → Space → Fantasy ✅
```

### **Scenario 2: Alternative Path**
```
Beach → City → Space → Fantasy ✅
```

### **Scenario 3: Complete Path (Recommended)**
```
Beach → Jungle ↘
              Space → Fantasy ✅
Beach → City  ↗
```

**Note:** You can do BOTH Jungle and City before Space for more practice!

---

## 💡 **Why This Progression System?**

### **Uses Graph Data Structure**
- Each level is a **vertex (node)**
- Connections are **edges**
- This allows **non-linear progression** (multiple paths)

### **Educational Benefits:**
- Demonstrates **graph theory** concepts
- Shows **adjacency list** implementation
- Practices **graph traversal** algorithms

### **Gameplay Benefits:**
- ✅ Player choice (Jungle vs City first)
- ✅ Gradual difficulty increase
- ✅ Sense of progression and achievement
- ✅ Can't skip to hard levels without practice

---

## 🎮 **How Unlocking Works**

### **Data Structure: Graph**

When you **complete a level**:

1. **Mark level as complete** (in LevelNode)
2. **Find all adjacent levels** (connected in graph)
3. **Unlock all adjacent levels** automatically
4. **Save progress** (persists between sessions)

### **Code Example:**
```java
// When Beach Scene is completed:
beach.markCompleted();

// Automatically unlocks:
- jungle.unlock();  // Jungle is now accessible
- city.unlock();    // City is now accessible
```

---

## 📈 **Difficulty Progression Breakdown**

### **Easy → Medium (Beach → Jungle)**
- +2 differences (40% increase)
- Same click radius
- **Focus:** More targets to find

### **Medium → Hard (Jungle → City)**
- +3 differences (43% increase)
- -5 pixel radius (20% smaller target)
- **Focus:** More targets + precision

### **Hard → Expert (City → Space)**
- +2 differences (20% increase)
- Same radius
- **Focus:** High target count

### **Expert → Master (Space → Fantasy)**
- +3 differences (25% increase)
- -2 pixel radius (10% smaller target)
- **Focus:** Maximum challenge!

---

## 🏆 **Completion Rewards**

### **Per Level:**
- ✅ Score based on performance
- ✅ Time bonus (faster = more points)
- ✅ Unlock next level(s)
- ✅ Achievement satisfaction

### **Complete All Levels:**
- 🎊 Beat the entire game!
- 📊 Compare scores on leaderboard
- 💪 Master of Spot the Difference!

---

## 🎯 **Tips for Each Level**

### **Beach Scene** 🏖️
- Perfect for learning the game mechanics
- Try to complete without hints to get better score
- Practice clicking accuracy

### **Jungle Scene** 🌴
- Use hints if stuck (you get 3 per level)
- Take your time - no rush!
- Remember: undo available if you need it

### **City Scene** 🏙️
- More differences = more challenge
- Smaller targets require precision
- Strategic hint usage recommended

### **Space Scene** 🚀
- 12 differences = lots to find
- Scan systematically (top to bottom, left to right)
- Use hints sparingly for tough spots

### **Fantasy Scene** 🧙
- Final boss of the game!
- 15 differences = maximum challenge
- Smallest targets = laser precision needed
- Take breaks if needed - level stays saved!

---

## 📊 **Summary Table**

| Feature | Beach | Jungle | City | Space | Fantasy |
|---------|-------|--------|------|-------|---------|
| **Unlock Status** | ✅ Start | 🔒 After Beach | 🔒 After Beach | 🔒 After Jungle/City | 🔒 After Space |
| **Differences** | 5 | 7 | 10 | 12 | 15 |
| **Avg Radius** | 27.5px | 27.5px | 22.5px | 22.5px | 20px |
| **Difficulty Rating** | 1/5 | 2/5 | 3/5 | 4/5 | 5/5 |
| **Est. Time** | 1-2 min | 2-3 min | 3-5 min | 4-6 min | 5-8 min |
| **Max Hints** | 3 | 3 | 3 | 3 | 3 |
| **Max Undos** | 5 | 5 | 5 | 5 | 5 |

---

## 🔧 **Technical Implementation**

### **Data Structures Used:**

1. **HashMap** - Stores all level data
   ```java
   levels.put("Beach Scene", beachData);
   LevelData level = levels.get("Beach Scene"); // O(1)
   ```

2. **Graph** - Manages progression
   ```java
   beach.addAdjacentLevel(jungle);  // Create edge
   beach.markCompleted();            // Unlock adjacent
   ```

3. **HashSet** - Tracks remaining differences
   ```java
   HashSet<Difference> remaining = new HashSet<>(differences);
   ```

---

## ❓ **FAQ**

**Q: Can I skip levels?**  
A: No, you must follow the progression graph. But you can choose between Jungle or City!

**Q: What happens if I quit mid-level?**  
A: Progress is lost, but level remains unlocked.

**Q: Can I replay completed levels?**  
A: Yes! Try to beat your high score!

**Q: Do hints/undos carry between levels?**  
A: No, you get fresh 3 hints and 5 undos per level.

**Q: How is the score calculated?**  
A: +100 per difference, -5 per wrong click, -20 per hint, -50 per undo, +time bonus

**Q: What if I can't find all differences?**  
A: Use hints! That's what they're for. Max 3 per level.

---

**Ready to start? Launch the game and begin with Beach Scene!** 🎮🏖️


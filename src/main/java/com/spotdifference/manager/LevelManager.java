package com.spotdifference.manager;

import java.util.HashMap;
import java.util.Set;

import com.spotdifference.model.LevelData;

/**
 * Function 1: Level Management and Selection
 * Uses HashMap<String, LevelData> for O(1) average-time level retrieval.
 * The level's name serves as the key, and LevelData object as the value.
 */
public class LevelManager {
    private HashMap<String, LevelData> levels;
    
    public LevelManager() {
        // HashMap: level name -> LevelData (fast O(1) lookup)
        this.levels = new HashMap<>();
        initializeLevels();
    }
    
    /**
     * Initializes all game levels with their data.
     * In a real application, this could load from files or a database.
     */
    private void initializeLevels() {
        // Level 1: Beach Scene (Easy)
        LevelData beach = new LevelData("Beach Scene", 
            "images/beach1.png", 
            "images/beach2.png", 
            1);
        // Coordinates found using Coordinate Finder Tool
        beach.addDifference(275, 356, 20);  // Difference 1 - Easy difficulty
        beach.addDifference(324, 445, 20);  // Difference 2
        beach.addDifference(55, 297, 20);   // Difference 3
        beach.addDifference(485, 312, 20);  // Difference 4
        beach.addDifference(85, 403, 20);   // Difference 5
        levels.put("Beach Scene", beach);
        
        // Level 2: Jungle Scene (Medium)
        LevelData jungle = new LevelData("Jungle Scene", 
            "images/jungle1.png", 
            "images/jungle2.png", 
            2);
        // Coordinates found using Coordinate Finder Tool
        jungle.addDifference(540, 299, 18);  // Difference 1 - Medium difficulty
        jungle.addDifference(250, 171, 18);  // Difference 2
        jungle.addDifference(413, 215, 18);  // Difference 3
        jungle.addDifference(474, 474, 18);  // Difference 4
        jungle.addDifference(83, 181, 18);   // Difference 5
        jungle.addDifference(55, 96, 18);    // Difference 6
        jungle.addDifference(445, 87, 18);   // Difference 7
        levels.put("Jungle Scene", jungle);
        
        // Level 3: City Scene (Hard) - 10 differences
        LevelData city = new LevelData("City Scene", 
            "images/city1.png", 
            "images/city2.png", 
            3);
        // Coordinates found using Coordinate Finder Tool
        // All 15px for Hard difficulty (smaller targets!)
        city.addDifference(78, 446, 15);    // Difference 1
        city.addDifference(510, 458, 15);   // Difference 2
        city.addDifference(83, 364, 15);    // Difference 3
        city.addDifference(34, 375, 15);    // Difference 4
        city.addDifference(42, 35, 15);     // Difference 5
        city.addDifference(122, 23, 15);    // Difference 6
        city.addDifference(498, 124, 15);   // Difference 7
        city.addDifference(293, 128, 15);   // Difference 8
        city.addDifference(418, 218, 15);   // Difference 9
        city.addDifference(516, 174, 15);   // Difference 10
        levels.put("City Scene", city);
        
        // Level 4: Space Scene (Expert) - 12 differences
        LevelData space = new LevelData("Space Scene", 
            "images/space1.png", 
            "images/space2.png", 
            4);
        // Coordinates found using Coordinate Finder Tool
        // All 12px for Expert difficulty (very small targets!)
        space.addDifference(538, 292, 12);   // Difference 1
        space.addDifference(419, 412, 12);   // Difference 2
        space.addDifference(76, 311, 12);    // Difference 3
        space.addDifference(61, 149, 12);    // Difference 4
        space.addDifference(17, 457, 12);    // Difference 5
        space.addDifference(353, 60, 12);    // Difference 6
        space.addDifference(526, 73, 12);    // Difference 7
        space.addDifference(474, 29, 12);    // Difference 8
        space.addDifference(541, 191, 12);   // Difference 9
        space.addDifference(40, 58, 12);     // Difference 10
        space.addDifference(102, 80, 12);    // Difference 11
        space.addDifference(103, 379, 12);   // Difference 12
        levels.put("Space Scene", space);
        
        // Level 5: Fantasy Scene (Master) - 15 differences
        LevelData fantasy = new LevelData("Fantasy Scene", 
            "images/fantasy1.png", 
            "images/fantasy2.png", 
            5);
        // Coordinates found using Coordinate Finder Tool
        // All 10px for Master difficulty (EXTREME precision required!)
        fantasy.addDifference(403, 339, 10);  // Difference 1 - EXTREME!
        fantasy.addDifference(520, 259, 10);  // Difference 2
        fantasy.addDifference(496, 164, 10);  // Difference 3
        fantasy.addDifference(497, 119, 10);  // Difference 4
        fantasy.addDifference(385, 21, 10);   // Difference 5
        fantasy.addDifference(330, 82, 10);   // Difference 6
        fantasy.addDifference(332, 209, 10);  // Difference 7
        fantasy.addDifference(179, 175, 10);  // Difference 8
        fantasy.addDifference(91, 165, 10);   // Difference 9
        fantasy.addDifference(60, 96, 10);    // Difference 10
        fantasy.addDifference(49, 156, 10);   // Difference 11
        fantasy.addDifference(61, 353, 10);   // Difference 12
        fantasy.addDifference(77, 390, 10);   // Difference 13
        fantasy.addDifference(186, 419, 10);  // Difference 14
        fantasy.addDifference(238, 227, 10);  // Difference 15
        levels.put("Fantasy Scene", fantasy);
    }
    
    /**
     * Retrieves a level by name.
     * Demonstrates HashMap's O(1) average-time get() operation.
     * 
     * @param levelName The name of the level to retrieve
     * @return The LevelData object, or null if not found
     */
    public LevelData getLevel(String levelName) {
        return levels.get(levelName);
    }
    
    /**
     * Adds a new level to the manager
     */
    public void addLevel(String levelName, LevelData levelData) {
        levels.put(levelName, levelData);
    }
    
    /**
     * Checks if a level exists
     */
    public boolean hasLevel(String levelName) {
        return levels.containsKey(levelName);
    }
    
    /**
     * Gets all available level names
     */
    public Set<String> getAllLevelNames() {
        return levels.keySet();
    }
    
    /**
     * Gets the total number of levels
     */
    public int getLevelCount() {
        return levels.size();
    }
}


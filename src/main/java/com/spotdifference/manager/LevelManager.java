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
        beach.addDifference(275, 356, 30);  // Difference 1
        beach.addDifference(324, 445, 30);  // Difference 2
        beach.addDifference(55, 297, 30);   // Difference 3
        beach.addDifference(485, 312, 30);  // Difference 4
        beach.addDifference(85, 403, 30);   // Difference 5
        levels.put("Beach Scene", beach);
        
        // Level 2: Jungle Scene (Medium)
        LevelData jungle = new LevelData("Jungle Scene", 
            "images/jungle1.png", 
            "images/jungle2.png", 
            2);
        // Coordinates found using Coordinate Finder Tool
        jungle.addDifference(540, 299, 30);  // Difference 1
        jungle.addDifference(250, 171, 30);  // Difference 2
        jungle.addDifference(413, 215, 30);  // Difference 3
        jungle.addDifference(474, 474, 30);  // Difference 4
        jungle.addDifference(83, 181, 30);   // Difference 5
        jungle.addDifference(55, 96, 30);    // Difference 6
        jungle.addDifference(445, 87, 30);   // Difference 7
        levels.put("Jungle Scene", jungle);
        
        // Level 3: City Scene (Hard)
        LevelData city = new LevelData("City Scene", 
            "resources/images/city1.jpg", 
            "resources/images/city2.jpg", 
            3);
        city.addDifference(200, 150, 20);
        city.addDifference(350, 250, 25);
        city.addDifference(180, 380, 20);
        city.addDifference(450, 200, 25);
        city.addDifference(280, 320, 20);
        city.addDifference(400, 420, 25);
        city.addDifference(150, 280, 20);
        city.addDifference(500, 350, 25);
        city.addDifference(250, 180, 20);
        city.addDifference(380, 450, 25);
        levels.put("City Scene", city);
        
        // Level 4: Space Scene (Expert)
        LevelData space = new LevelData("Space Scene", 
            "resources/images/space1.jpg", 
            "resources/images/space2.jpg", 
            4);
        space.addDifference(190, 210, 20);
        space.addDifference(330, 160, 25);
        space.addDifference(220, 370, 20);
        space.addDifference(470, 240, 25);
        space.addDifference(160, 420, 20);
        space.addDifference(390, 330, 25);
        space.addDifference(280, 190, 20);
        space.addDifference(420, 450, 25);
        space.addDifference(240, 280, 20);
        space.addDifference(500, 380, 25);
        space.addDifference(170, 340, 20);
        space.addDifference(450, 170, 25);
        levels.put("Space Scene", space);
        
        // Level 5: Fantasy Scene (Master)
        LevelData fantasy = new LevelData("Fantasy Scene", 
            "resources/images/fantasy1.jpg", 
            "resources/images/fantasy2.jpg", 
            5);
        fantasy.addDifference(210, 190, 18);
        fantasy.addDifference(340, 240, 22);
        fantasy.addDifference(180, 360, 18);
        fantasy.addDifference(460, 210, 22);
        fantasy.addDifference(270, 310, 18);
        fantasy.addDifference(410, 410, 22);
        fantasy.addDifference(160, 270, 18);
        fantasy.addDifference(490, 340, 22);
        fantasy.addDifference(240, 170, 18);
        fantasy.addDifference(370, 440, 22);
        fantasy.addDifference(200, 420, 18);
        fantasy.addDifference(440, 180, 22);
        fantasy.addDifference(320, 290, 18);
        fantasy.addDifference(480, 250, 22);
        fantasy.addDifference(150, 380, 18);
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


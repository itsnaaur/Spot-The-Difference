package com.spotdifference.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Stores all data for a single game level.
 * Used as the value in the HashMap for level management.
 */
public class LevelData implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String levelName;
    private String image1Path;
    private String image2Path;
    private List<Difference> differences;
    private int difficulty; // 1-5 difficulty rating
    
    public LevelData(String levelName, String image1Path, String image2Path, int difficulty) {
        this.levelName = levelName;
        this.image1Path = image1Path;
        this.image2Path = image2Path;
        this.difficulty = difficulty;
        this.differences = new ArrayList<>();
    }
    
    public void addDifference(Difference difference) {
        differences.add(difference);
    }
    
    public void addDifference(int x, int y, int radius) {
        differences.add(new Difference(x, y, radius));
    }
    
    public String getLevelName() {
        return levelName;
    }
    
    public String getImage1Path() {
        return image1Path;
    }
    
    public String getImage2Path() {
        return image2Path;
    }
    
    public List<Difference> getDifferences() {
        return new ArrayList<>(differences); // Return copy for safety
    }
    
    public int getDifficulty() {
        return difficulty;
    }
    
    public int getTotalDifferences() {
        return differences.size();
    }
    
    @Override
    public String toString() {
        return levelName + " (Difficulty: " + difficulty + ", Differences: " + differences.size() + ")";
    }
}


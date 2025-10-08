package com.spotdifference.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the level progression graph.
 * Each node contains a level and connections to adjacent levels.
 */
public class LevelNode {
    private String levelName;
    private List<LevelNode> adjacentLevels;
    private boolean unlocked;
    private boolean completed;
    
    public LevelNode(String levelName, boolean unlocked) {
        this.levelName = levelName;
        this.unlocked = unlocked;
        this.completed = false;
        this.adjacentLevels = new ArrayList<>();
    }
    
    public void addAdjacentLevel(LevelNode node) {
        if (!adjacentLevels.contains(node)) {
            adjacentLevels.add(node);
        }
    }
    
    public void unlock() {
        this.unlocked = true;
    }
    
    public void markCompleted() {
        this.completed = true;
        // Unlock all adjacent levels
        for (LevelNode adjacent : adjacentLevels) {
            adjacent.unlock();
        }
    }
    
    public String getLevelName() {
        return levelName;
    }
    
    public List<LevelNode> getAdjacentLevels() {
        return new ArrayList<>(adjacentLevels);
    }
    
    public boolean isUnlocked() {
        return unlocked;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        LevelNode that = (LevelNode) obj;
        return levelName.equals(that.levelName);
    }
    
    @Override
    public int hashCode() {
        return levelName.hashCode();
    }
    
    @Override
    public String toString() {
        return levelName + (unlocked ? " [UNLOCKED]" : " [LOCKED]") + (completed ? " [COMPLETED]" : "");
    }
}


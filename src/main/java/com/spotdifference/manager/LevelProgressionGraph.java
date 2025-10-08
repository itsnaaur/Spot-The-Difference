package com.spotdifference.manager;

import com.spotdifference.model.LevelNode;
import java.util.*;

/**
 * Function 6: Non-Linear Level Progression Map
 * Uses Graph data structure where each level is a node (vertex) 
 * and pathways between them are edges.
 * Completing a level unlocks access to adjacent levels.
 */
public class LevelProgressionGraph {
    private HashMap<String, LevelNode> levelNodes;
    
    public LevelProgressionGraph() {
        this.levelNodes = new HashMap<>();
        initializeGraph();
    }
    
    /**
     * Initializes the level progression graph structure.
     * Creates nodes and establishes edges (connections) between levels.
     */
    private void initializeGraph() {
        // Create nodes for each level
        LevelNode beach = new LevelNode("Beach Scene", true); // First level unlocked
        LevelNode jungle = new LevelNode("Jungle Scene", false);
        LevelNode city = new LevelNode("City Scene", false);
        LevelNode space = new LevelNode("Space Scene", false);
        LevelNode fantasy = new LevelNode("Fantasy Scene", false);
        
        // Define graph edges (level progression paths)
        // Beach unlocks Jungle and City
        beach.addAdjacentLevel(jungle);
        beach.addAdjacentLevel(city);
        
        // Jungle unlocks Space
        jungle.addAdjacentLevel(space);
        
        // City unlocks Space
        city.addAdjacentLevel(space);
        
        // Space unlocks Fantasy
        space.addAdjacentLevel(fantasy);
        
        // Add all nodes to the map
        levelNodes.put("Beach Scene", beach);
        levelNodes.put("Jungle Scene", jungle);
        levelNodes.put("City Scene", city);
        levelNodes.put("Space Scene", space);
        levelNodes.put("Fantasy Scene", fantasy);
    }
    
    /**
     * Marks a level as completed and unlocks adjacent levels.
     * Demonstrates graph traversal and edge relationships.
     * 
     * @param levelName The name of the completed level
     */
    public void completeLevel(String levelName) {
        LevelNode node = levelNodes.get(levelName);
        if (node != null) {
            node.markCompleted();
        }
    }
    
    /**
     * Checks if a level is unlocked and playable
     */
    public boolean isLevelUnlocked(String levelName) {
        LevelNode node = levelNodes.get(levelName);
        return node != null && node.isUnlocked();
    }
    
    /**
     * Checks if a level has been completed
     */
    public boolean isLevelCompleted(String levelName) {
        LevelNode node = levelNodes.get(levelName);
        return node != null && node.isCompleted();
    }
    
    /**
     * Gets all levels that are unlocked
     */
    public List<String> getUnlockedLevels() {
        List<String> unlockedLevels = new ArrayList<>();
        for (LevelNode node : levelNodes.values()) {
            if (node.isUnlocked()) {
                unlockedLevels.add(node.getLevelName());
            }
        }
        return unlockedLevels;
    }
    
    /**
     * Gets all levels adjacent to (unlockable from) a given level
     */
    public List<String> getAdjacentLevels(String levelName) {
        LevelNode node = levelNodes.get(levelName);
        if (node == null) return new ArrayList<>();
        
        List<String> adjacentNames = new ArrayList<>();
        for (LevelNode adjacent : node.getAdjacentLevels()) {
            adjacentNames.add(adjacent.getLevelName());
        }
        return adjacentNames;
    }
    
    /**
     * Gets all level names in the graph
     */
    public Set<String> getAllLevels() {
        return levelNodes.keySet();
    }
    
    /**
     * Performs a breadth-first traversal of the graph from a starting level
     * Returns levels in the order they can be reached
     */
    public List<String> breadthFirstTraversal(String startLevel) {
        List<String> result = new ArrayList<>();
        Queue<LevelNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        
        LevelNode startNode = levelNodes.get(startLevel);
        if (startNode == null) return result;
        
        queue.offer(startNode);
        visited.add(startLevel);
        
        while (!queue.isEmpty()) {
            LevelNode current = queue.poll();
            result.add(current.getLevelName());
            
            for (LevelNode adjacent : current.getAdjacentLevels()) {
                if (!visited.contains(adjacent.getLevelName())) {
                    visited.add(adjacent.getLevelName());
                    queue.offer(adjacent);
                }
            }
        }
        
        return result;
    }
    
    /**
     * Resets the graph to initial state (only first level unlocked)
     */
    public void reset() {
        for (LevelNode node : levelNodes.values()) {
            if (!node.getLevelName().equals("Beach Scene")) {
                // Reset all except the first level
                levelNodes.put(node.getLevelName(), 
                    new LevelNode(node.getLevelName(), false));
            }
        }
        initializeGraph();
    }
}


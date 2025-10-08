package com.spotdifference.manager;

import com.spotdifference.model.PlayerScore;
import java.io.*;
import java.util.LinkedList;

/**
 * Function 5: High Score Leaderboard
 * Uses LinkedList<PlayerScore> to maintain a persistent, sorted list of top scores.
 * Demonstrates list traversal and insertion to maintain sorted order.
 */
public class HighScoreManager {
    private LinkedList<PlayerScore> highScores;
    private static final int MAX_SCORES = 10;
    private static final String SAVE_FILE = "highscores.dat";
    
    public HighScoreManager() {
        this.highScores = new LinkedList<>();
        loadScores();
    }
    
    /**
     * Adds a new score to the leaderboard.
     * Demonstrates LinkedList traversal and insertion to maintain sorted order.
     * 
     * @param playerScore The score to add
     * @return true if the score was added (made it to top 10), false otherwise
     */
    public boolean addScore(PlayerScore playerScore) {
        // Find the correct position to insert (maintain sorted order)
        int insertPosition = 0;
        for (PlayerScore score : highScores) {
            if (playerScore.compareTo(score) < 0) {
                // Current score is higher, keep looking
                insertPosition++;
            } else {
                // Found the right position
                break;
            }
        }
        
        // Check if score qualifies for the leaderboard
        if (insertPosition < MAX_SCORES) {
            highScores.add(insertPosition, playerScore);
            
            // Remove excess scores if list exceeds maximum
            while (highScores.size() > MAX_SCORES) {
                highScores.removeLast();
            }
            
            saveScores();
            return true;
        }
        
        // If list isn't full yet, add the score
        if (highScores.size() < MAX_SCORES) {
            highScores.add(playerScore);
            saveScores();
            return true;
        }
        
        return false;
    }
    
    /**
     * Gets all high scores
     */
    public LinkedList<PlayerScore> getHighScores() {
        return new LinkedList<>(highScores);
    }
    
    /**
     * Gets high scores for a specific level
     */
    public LinkedList<PlayerScore> getHighScoresForLevel(String levelName) {
        LinkedList<PlayerScore> levelScores = new LinkedList<>();
        for (PlayerScore score : highScores) {
            if (score.getLevelName().equals(levelName)) {
                levelScores.add(score);
            }
        }
        return levelScores;
    }
    
    /**
     * Checks if a score would make it to the leaderboard
     */
    public boolean isHighScore(int score) {
        if (highScores.size() < MAX_SCORES) {
            return true;
        }
        return score > highScores.getLast().getScore();
    }
    
    /**
     * Gets the minimum score needed to make the leaderboard
     */
    public int getMinimumScore() {
        if (highScores.isEmpty()) {
            return 0;
        }
        if (highScores.size() < MAX_SCORES) {
            return 0;
        }
        return highScores.getLast().getScore();
    }
    
    /**
     * Clears all high scores
     */
    public void clearScores() {
        highScores.clear();
        saveScores();
    }
    
    /**
     * Saves high scores to file
     */
    private void saveScores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(highScores);
        } catch (IOException e) {
            System.err.println("Error saving high scores: " + e.getMessage());
        }
    }
    
    /**
     * Loads high scores from file
     */
    @SuppressWarnings("unchecked")
    private void loadScores() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(
                    new FileInputStream(SAVE_FILE))) {
                highScores = (LinkedList<PlayerScore>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading high scores: " + e.getMessage());
                highScores = new LinkedList<>();
            }
        }
    }
}


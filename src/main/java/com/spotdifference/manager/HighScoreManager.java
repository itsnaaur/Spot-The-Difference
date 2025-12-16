package com.spotdifference.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import com.spotdifference.model.PlayerScore;

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
        // Find the correct position to insert (maintain sorted order - highest scores first)
        // We want descending order: [100, 90, 80, 70, ...]
        int insertPosition = 0;
        for (PlayerScore score : highScores) {
            // compareTo returns negative if playerScore should come BEFORE score (i.e., playerScore is higher)
            // compareTo returns positive if playerScore should come AFTER score (i.e., playerScore is lower)
            if (playerScore.compareTo(score) > 0) {
                // playerScore is lower than current score, so it should go after this position
                insertPosition++;
            } else {
                // playerScore is higher or equal to current score, insert here
                break;
            }
        }
        
        // Check if score qualifies for the leaderboard
        if (insertPosition < MAX_SCORES || highScores.size() < MAX_SCORES) {
            // Insert at the correct position to maintain descending order (highest first)
            highScores.add(insertPosition, playerScore);
            
            // Remove excess scores if list exceeds maximum
            while (highScores.size() > MAX_SCORES) {
                highScores.removeLast();
            }
            
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
     * Checks if a score is a NEW HIGH SCORE (beats the highest score in the leaderboard)
     * This is different from qualifying for the leaderboard - this checks if it's the new #1
     */
    public boolean isHighScore(int score) {
        if (highScores.isEmpty()) {
            return true; // First score is always a high score
        }
        // Only show "NEW HIGH SCORE!" if it beats the highest (first) score
        int highestScore = highScores.getFirst().getScore();
        return score > highestScore;
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
                // Ensure scores are sorted in descending order (highest first)
                // This fixes any existing files that might have been saved with incorrect order
                highScores.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
                // Keep only top MAX_SCORES
                while (highScores.size() > MAX_SCORES) {
                    highScores.removeLast();
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading high scores: " + e.getMessage());
                highScores = new LinkedList<>();
            }
        }
    }
}


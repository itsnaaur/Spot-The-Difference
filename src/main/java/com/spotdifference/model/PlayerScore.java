package com.spotdifference.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a player's score entry for the leaderboard.
 * Used in the LinkedList-based high score system.
 */
public class PlayerScore implements Serializable, Comparable<PlayerScore> {
    private static final long serialVersionUID = 1L;
    
    private String playerName;
    private int score;
    private String levelName;
    private LocalDateTime timestamp;
    
    public PlayerScore(String playerName, int score, String levelName) {
        this.playerName = playerName;
        this.score = score;
        this.levelName = levelName;
        this.timestamp = LocalDateTime.now();
    }
    
    public String getPlayerName() {
        return playerName;
    }
    
    public int getScore() {
        return score;
    }
    
    public String getLevelName() {
        return levelName;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.format(formatter);
    }
    
    @Override
    public int compareTo(PlayerScore other) {
        // Higher scores come first (descending order)
        return Integer.compare(other.score, this.score);
    }
    
    @Override
    public String toString() {
        return playerName + " - " + score + " points (" + levelName + ")";
    }
}


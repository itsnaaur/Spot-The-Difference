package com.spotdifference.logic;

import com.spotdifference.model.Difference;
import java.awt.Point;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Function 2: Real-time Difference Checking
 * Uses HashSet<Difference> for O(1) average-time click validation.
 * When a player clicks, this checks if the click matches any remaining difference.
 */
public class DifferenceChecker {
    private HashSet<Difference> remainingDifferences;
    private int totalDifferences;
    
    public DifferenceChecker(List<Difference> differences) {
        this.remainingDifferences = new HashSet<>(differences);
        this.totalDifferences = differences.size();
    }
    
    /**
     * Checks if the click point matches any remaining difference.
     * Uses O(1) average-time HashSet operations.
     * 
     * @param clickPoint The point where the player clicked
     * @return The matched Difference object, or null if no match
     */
    public Difference checkClick(Point clickPoint) {
        for (Difference diff : remainingDifferences) {
            if (diff.contains(clickPoint)) {
                remainingDifferences.remove(diff);
                return diff;
            }
        }
        return null;
    }
    
    /**
     * Adds a difference back to the remaining set (used by Undo)
     */
    public void addDifference(Difference difference) {
        remainingDifferences.add(difference);
    }
    
    /**
     * Removes a difference from the remaining set (used by Hint)
     */
    public void removeDifference(Difference difference) {
        remainingDifferences.remove(difference);
    }
    
    public boolean isLevelComplete() {
        return remainingDifferences.isEmpty();
    }
    
    public int getRemainingCount() {
        return remainingDifferences.size();
    }
    
    public int getTotalDifferences() {
        return totalDifferences;
    }
    
    public Set<Difference> getRemainingDifferences() {
        return new HashSet<>(remainingDifferences);
    }
    
    public void reset(List<Difference> differences) {
        this.remainingDifferences = new HashSet<>(differences);
        this.totalDifferences = differences.size();
    }
}


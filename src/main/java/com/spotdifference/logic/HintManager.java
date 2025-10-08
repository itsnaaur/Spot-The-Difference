package com.spotdifference.logic;

import com.spotdifference.model.Difference;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Function 4: Hint System
 * Uses Queue<Difference> following FIFO (First-In, First-Out) principle.
 * Reveals differences in a consistent, predetermined sequence.
 */
public class HintManager {
    private Queue<Difference> hintQueue;
    private int maxHints;
    private int hintsUsed;
    
    public HintManager(List<Difference> differences, int maxHints) {
        this.hintQueue = new LinkedList<>(differences);
        this.maxHints = maxHints;
        this.hintsUsed = 0;
    }
    
    /**
     * Dequeues and returns the next hint.
     * Demonstrates Queue's poll() operation (FIFO).
     * 
     * @return The next difference to hint, or null if no hints available
     */
    public Difference getNextHint() {
        if (maxHints > 0 && hintsUsed >= maxHints) {
            return null; // Hint limit reached
        }
        
        if (!hintQueue.isEmpty()) {
            hintsUsed++;
            return hintQueue.poll();
        }
        return null;
    }
    
    /**
     * Peeks at the next hint without removing it
     */
    public Difference peekNextHint() {
        return hintQueue.peek();
    }
    
    /**
     * Removes a specific difference from the hint queue
     * (called when player finds a difference before using its hint)
     */
    public void removeDifference(Difference difference) {
        hintQueue.remove(difference);
    }
    
    public boolean hasHintsAvailable() {
        if (maxHints > 0 && hintsUsed >= maxHints) {
            return false;
        }
        return !hintQueue.isEmpty();
    }
    
    public int getHintsRemaining() {
        if (maxHints <= 0) {
            return hintQueue.size(); // Unlimited hints
        }
        return Math.min(maxHints - hintsUsed, hintQueue.size());
    }
    
    public int getHintsUsed() {
        return hintsUsed;
    }
    
    public void clear() {
        hintQueue.clear();
        hintsUsed = 0;
    }
}


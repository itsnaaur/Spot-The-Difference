package com.spotdifference.logic;

import com.spotdifference.model.Difference;
import java.util.Stack;

/**
 * Function 3: Undo Move System
 * Uses Stack<Difference> following LIFO (Last-In, First-Out) principle.
 * Allows players to reverse their most recent successful find.
 */
public class UndoManager {
    private Stack<Difference> undoStack;
    private int maxUndos;
    
    public UndoManager(int maxUndos) {
        this.undoStack = new Stack<>();
        this.maxUndos = maxUndos;
    }
    
    /**
     * Pushes a found difference onto the stack.
     * Demonstrates Stack's push() operation.
     * 
     * @param difference The difference that was just found
     */
    public void pushMove(Difference difference) {
        if (undoStack.size() >= maxUndos && maxUndos > 0) {
            // Remove oldest element if limit reached
            undoStack.remove(0);
        }
        undoStack.push(difference);
    }
    
    /**
     * Pops and returns the most recent move.
     * Demonstrates Stack's pop() operation (LIFO).
     * 
     * @return The most recent difference, or null if stack is empty
     */
    public Difference popMove() {
        if (!undoStack.isEmpty()) {
            return undoStack.pop();
        }
        return null;
    }
    
    /**
     * Peeks at the most recent move without removing it
     */
    public Difference peekMove() {
        if (!undoStack.isEmpty()) {
            return undoStack.peek();
        }
        return null;
    }
    
    public boolean canUndo() {
        return !undoStack.isEmpty();
    }
    
    public int getUndoCount() {
        return undoStack.size();
    }
    
    public void clear() {
        undoStack.clear();
    }
}


package com.spotdifference.model;

import java.awt.Point;
import java.io.Serializable;

/**
 * Represents a single difference in the game.
 * Contains the location and radius for click detection.
 */
public class Difference implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Point location;
    private int radius; // Click detection radius in pixels
    
    public Difference(int x, int y, int radius) {
        this.location = new Point(x, y);
        this.radius = radius;
    }
    
    public Difference(Point location, int radius) {
        this.location = new Point(location);
        this.radius = radius;
    }
    
    public Point getLocation() {
        return new Point(location); // Return copy to prevent modification
    }
    
    public int getRadius() {
        return radius;
    }
    
    /**
     * Checks if a click point is within this difference's detection area
     */
    public boolean contains(Point clickPoint) {
        double distance = location.distance(clickPoint);
        return distance <= radius;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Difference that = (Difference) obj;
        return location.equals(that.location);
    }
    
    @Override
    public int hashCode() {
        return location.hashCode();
    }
    
    @Override
    public String toString() {
        return "Difference{x=" + location.x + ", y=" + location.y + ", radius=" + radius + "}";
    }
}


package com.spotdifference.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 * Centralized UI Theme and Constants
 * Provides consistent styling across all UI components
 */
public class UITheme {
    
    // ========== COLOR PALETTE ==========
    
    // Primary Colors
    public static final Color PRIMARY_BLUE = new Color(37, 99, 235);        // #2563EB
    public static final Color PRIMARY_BLUE_DARK = new Color(29, 78, 216);   // #1D4ED8
    public static final Color PRIMARY_BLUE_LIGHT = new Color(59, 130, 246); // #3B82F6
    
    // Success Colors
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);       // #22C55E
    public static final Color SUCCESS_GREEN_DARK = new Color(22, 163, 74);  // #16A34A
    public static final Color SUCCESS_GREEN_LIGHT = new Color(74, 222, 128); // #4ADE80
    
    // Warning Colors
    public static final Color WARNING_YELLOW = new Color(234, 179, 8);      // #EAB308
    public static final Color WARNING_YELLOW_DARK = new Color(202, 138, 4); // #CA8A04
    public static final Color WARNING_YELLOW_LIGHT = new Color(253, 224, 71); // #FDE047
    
    // Error/Danger Colors
    public static final Color DANGER_RED = new Color(239, 68, 68);          // #EF4444
    public static final Color DANGER_RED_DARK = new Color(220, 38, 38);     // #DC2626
    public static final Color DANGER_RED_LIGHT = new Color(248, 113, 113);  // #F87171
    
    // Accent Colors
    public static final Color ACCENT_PURPLE = new Color(168, 85, 247);      // #A855F7
    public static final Color ACCENT_PURPLE_DARK = new Color(147, 51, 234); // #9333EA
    
    // Neutral Colors
    public static final Color GRAY_50 = new Color(248, 250, 252);           // #F8FAFC
    public static final Color GRAY_100 = new Color(241, 245, 249);          // #F1F5F9
    public static final Color GRAY_200 = new Color(226, 232, 240);          // #E2E8F0
    public static final Color GRAY_400 = new Color(148, 163, 184);          // #94A3B8
    public static final Color GRAY_500 = new Color(100, 116, 139);          // #64748B
    public static final Color GRAY_700 = new Color(51, 65, 85);             // #334155
    public static final Color GRAY_900 = new Color(15, 23, 42);             // #0F172A
    
    // Background Colors
    public static final Color BG_MAIN = new Color(250, 250, 252);           // Light background
    public static final Color BG_CARD = Color.WHITE;                        // Card background
    public static final Color BG_PANEL = new Color(245, 245, 250);          // Panel background
    
    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);         // #0F172A
    public static final Color TEXT_SECONDARY = new Color(100, 116, 139);    // #64748B
    public static final Color TEXT_DISABLED = new Color(148, 163, 184);     // #94A3B8
    
    // ========== FONTS ==========
    
    public static final String FONT_FAMILY = "Segoe UI";
    
    public static Font getTitleFont(float size) {
        return new Font(FONT_FAMILY, Font.BOLD, (int)size);
    }
    
    public static Font getHeadingFont(float size) {
        return new Font(FONT_FAMILY, Font.BOLD, (int)size);
    }
    
    public static Font getBodyFont(float size) {
        return new Font(FONT_FAMILY, Font.PLAIN, (int)size);
    }
    
    public static Font getButtonFont(float size) {
        return new Font(FONT_FAMILY, Font.BOLD, (int)size);
    }
    
    // ========== SHADOWS & ELEVATION ==========
    
    /**
     * Draws a soft shadow for elevation effect
     */
    public static void drawShadow(Graphics2D g2d, int x, int y, int width, int height, int elevation) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Draw multiple shadow layers for depth
        for (int i = elevation; i > 0; i--) {
            float alpha = 0.05f / (elevation - i + 1);
            g2d.setColor(new Color(0, 0, 0, alpha));
            int offset = i;
            g2d.fillRoundRect(x + offset, y + offset, width, height, 12, 12);
        }
    }
    
    /**
     * Creates a rounded rectangle border with modern styling
     */
    public static javax.swing.border.Border createModernCardBorder() {
        return javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(GRAY_200, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ),
            javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2)
        );
    }
    
    /**
     * Creates a gradient paint for backgrounds
     */
    public static java.awt.GradientPaint createGradient(int width, int height, Color start, Color end) {
        return new java.awt.GradientPaint(0, 0, start, 0, height, end);
    }
    
    // ========== BUTTON STYLES ==========
    
    public static void styleModernButton(javax.swing.JButton button, Color bgColor, int height) {
        button.setFont(getButtonFont(14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        // Modern rounded corners with padding
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 24, 12, 24));
        button.setPreferredSize(new java.awt.Dimension(
            button.getPreferredSize().width,
            height
        ));
        
        // Smooth hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(lightenColor(bgColor, 0.15f));
                button.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                    javax.swing.BorderFactory.createLineBorder(darkenColor(bgColor, 0.1f), 2, true),
                    javax.swing.BorderFactory.createEmptyBorder(10, 22, 10, 22)
                ));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 24, 12, 24));
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(darkenColor(bgColor, 0.15f));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    // ========== UTILITY METHODS ==========
    
    /**
     * Lightens a color by a percentage
     */
    public static Color lightenColor(Color color, float factor) {
        int r = Math.min(255, (int)(color.getRed() + (255 - color.getRed()) * factor));
        int g = Math.min(255, (int)(color.getGreen() + (255 - color.getGreen()) * factor));
        int b = Math.min(255, (int)(color.getBlue() + (255 - color.getBlue()) * factor));
        return new Color(r, g, b);
    }
    
    /**
     * Darkens a color by a percentage
     */
    public static Color darkenColor(Color color, float factor) {
        int r = Math.max(0, (int)(color.getRed() * (1 - factor)));
        int g = Math.max(0, (int)(color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int)(color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }
    
    /**
     * Creates a semi-transparent overlay color
     */
    public static Color withAlpha(Color color, float alpha) {
        return new Color(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            alpha
        );
    }
}

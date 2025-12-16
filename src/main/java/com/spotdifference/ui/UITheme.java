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
    
    // ========== COLOR PALETTE (DARK GAMING THEME) ==========
    
    // Primary Colors
    public static final Color PRIMARY_BLUE = new Color(59, 130, 246);        // #3B82F6
    public static final Color PRIMARY_BLUE_DARK = new Color(30, 64, 175);    // #1E40AF
    public static final Color PRIMARY_BLUE_LIGHT = new Color(96, 165, 250); // #60A5FA
    
    // Success Colors
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);        // #22C55E
    public static final Color SUCCESS_GREEN_DARK = new Color(22, 163, 74);   // #16A34A
    public static final Color SUCCESS_GREEN_LIGHT = new Color(52, 211, 153); // #34D399
    
    // Warning Colors
    public static final Color WARNING_YELLOW = new Color(234, 179, 8);       // #EAB308
    public static final Color WARNING_YELLOW_DARK = new Color(202, 138, 4);  // #CA8A04
    public static final Color WARNING_YELLOW_LIGHT = new Color(250, 204, 21);// #FACC15
    
    // Error/Danger Colors
    public static final Color DANGER_RED = new Color(239, 68, 68);           // #EF4444
    public static final Color DANGER_RED_DARK = new Color(220, 38, 38);      // #DC2626
    public static final Color DANGER_RED_LIGHT = new Color(248, 113, 113);   // #F87171
    
    // Accent Colors
    public static final Color ACCENT_PURPLE = new Color(147, 51, 234);       // #9333EA
    public static final Color ACCENT_PURPLE_DARK = new Color(126, 34, 206);  // #7E22CE
    
    // Neutral Colors (dark background)
    public static final Color GRAY_50 = new Color(15, 23, 42);               // #0F172A
    public static final Color GRAY_100 = new Color(17, 24, 39);              // #111827
    public static final Color GRAY_200 = new Color(31, 41, 55);              // #1F2937
    public static final Color GRAY_400 = new Color(148, 163, 184);           // #94A3B8
    public static final Color GRAY_500 = new Color(100, 116, 139);           // #64748B
    public static final Color GRAY_700 = new Color(55, 65, 81);              // #374151
    public static final Color GRAY_900 = new Color(15, 23, 42);              // #0F172A
    
    // Background Colors
    public static final Color BG_MAIN = new Color(15, 23, 42);               // main window bg
    public static final Color BG_CARD = new Color(17, 24, 39);               // card panels
    public static final Color BG_PANEL = new Color(15, 23, 42);              // large panels
    
    // Text Colors
    public static final Color TEXT_PRIMARY = new Color(248, 250, 252);       // near-white
    public static final Color TEXT_SECONDARY = new Color(148, 163, 184);     // muted gray
    public static final Color TEXT_DISABLED = new Color(75, 85, 99);         // dark gray
    
    // ========== FONTS ==========
    
    public static final String FONT_FAMILY = "Segoe UI";
    
    public static Font getTitleFont(float size) {
        return new Font(FONT_FAMILY, Font.BOLD, (int) size);
    }
    
    public static Font getHeadingFont(float size) {
        return new Font(FONT_FAMILY, Font.BOLD, (int) size);
    }
    
    public static Font getBodyFont(float size) {
        return new Font(FONT_FAMILY, Font.PLAIN, (int) size);
    }
    
    public static Font getButtonFont(float size) {
        return new Font(FONT_FAMILY, Font.BOLD, (int) size);
    }
    
    // ========== SHADOWS & ELEVATION ==========
    
    /**
     * Draws a soft shadow for elevation effect
     */
    public static void drawShadow(Graphics2D g2d, int x, int y, int width, int height, int elevation) {
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        for (int i = elevation; i > 0; i--) {
            float alpha = 0.05f / (elevation - i + 1);
            g2d.setColor(new Color(0, 0, 0, alpha));
            int offset = i;
            g2d.fillRoundRect(x + offset, y + offset, width, height, 12, 12);
        }
    }
    
    public static javax.swing.border.Border createModernCardBorder() {
        return javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createLineBorder(GRAY_200, 1, true),
                javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20)
            ),
            javax.swing.BorderFactory.createEmptyBorder(2, 2, 2, 2)
        );
    }
    
    public static java.awt.GradientPaint createGradient(int width, int height, Color start, Color end) {
        return new java.awt.GradientPaint(0, 0, start, 0, height, end);
    }
    
    // ========== BUTTON STYLES ==========
    
    public static void styleModernButton(javax.swing.JButton button, Color bgColor, int height) {
        // Base visual style
        button.setFont(getButtonFont(14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        // Slightly pill-shaped with consistent height
        button.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 20, 8, 20));
        button.setPreferredSize(new java.awt.Dimension(
            button.getPreferredSize().width,
            height
        ));
        
        // Subtle hover/press states (less aggressive so it looks cleaner)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(lightenColor(bgColor, 0.08f));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(darkenColor(bgColor, 0.14f));
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    // ========== UTILITY METHODS ==========
    
    public static Color lightenColor(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() + (255 - color.getRed()) * factor));
        int g = Math.min(255, (int) (color.getGreen() + (255 - color.getGreen()) * factor));
        int b = Math.min(255, (int) (color.getBlue() + (255 - color.getBlue()) * factor));
        return new Color(r, g, b);
    }
    
    public static Color darkenColor(Color color, float factor) {
        int r = Math.max(0, (int) (color.getRed() * (1 - factor)));
        int g = Math.max(0, (int) (color.getGreen() * (1 - factor)));
        int b = Math.max(0, (int) (color.getBlue() * (1 - factor)));
        return new Color(r, g, b);
    }
    
    public static Color withAlpha(Color color, float alpha) {
        return new Color(
            color.getRed() / 255f,
            color.getGreen() / 255f,
            color.getBlue() / 255f,
            alpha
        );
    }
}

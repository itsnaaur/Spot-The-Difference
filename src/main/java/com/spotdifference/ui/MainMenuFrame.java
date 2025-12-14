package com.spotdifference.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * Main Menu GUI - The application's central hub
 * Provides navigation to Start Game, View High Scores, or Exit
 */
public class MainMenuFrame extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;
    
    public MainMenuFrame() {
        initializeFrame();
        createComponents();
    }
    
    private void initializeFrame() {
        setTitle("Spot the Difference");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }
    
    private void createComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Create gradient background
                GradientPaint gradient = UITheme.createGradient(
                    getWidth(), getHeight(),
                    UITheme.GRAY_50,
                    UITheme.BG_PANEL
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(80, 100, 80, 100));
        
        // Title with enhanced styling and shadow effect
        JLabel titleLabel = new JLabel("Spot the Difference") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                
                // Draw text shadow
                g2d.setColor(new Color(0, 0, 0, 20));
                g2d.setFont(getFont());
                g2d.drawString(getText(), 3, getHeight() - 3);
                
                // Draw main text
                super.paintComponent(g);
                g2d.dispose();
            }
        };
        titleLabel.setFont(UITheme.getTitleFont(48));
        titleLabel.setForeground(UITheme.PRIMARY_BLUE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle with modern font
        JLabel subtitleLabel = new JLabel("Data Structures Showcase Game");
        subtitleLabel.setFont(UITheme.getBodyFont(18));
        subtitleLabel.setForeground(UITheme.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Add decorative line
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(200, 2));
        separator.setBackground(UITheme.PRIMARY_BLUE_LIGHT);
        separator.setForeground(UITheme.PRIMARY_BLUE_LIGHT);
        separator.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Modern buttons with enhanced styling
        JButton startButton = createStyledButton("▶ Start Game", UITheme.SUCCESS_GREEN, 60);
        JButton highScoresButton = createStyledButton("★ High Scores", UITheme.PRIMARY_BLUE, 60);
        JButton exitButton = createStyledButton("✕ Exit", UITheme.DANGER_RED, 60);
        
        // Button actions
        startButton.addActionListener(e -> openLevelSelection());
        highScoresButton.addActionListener(e -> openHighScores());
        exitButton.addActionListener(e -> System.exit(0));
        
        // Add components with spacing
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));
        mainPanel.add(separator);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));
        mainPanel.add(startButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        mainPanel.add(highScoresButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        mainPanel.add(exitButton);
        mainPanel.add(Box.createVerticalGlue());
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Color bgColor, int height) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                UITheme.drawShadow(g2d, 2, 2, getWidth() - 4, getHeight() - 4, 3);
                
                // Draw rounded rectangle background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Draw border
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.setColor(UITheme.darkenColor(getBackground(), 0.1f));
                g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 12, 12);
                
                // Draw text
                g2d.setColor(getForeground());
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        
        UITheme.styleModernButton(button, bgColor, height);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(360, height));
        
        // Enhanced hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(UITheme.lightenColor(bgColor, 0.15f));
                button.repaint();
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.repaint();
            }
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(UITheme.darkenColor(bgColor, 0.15f));
                button.repaint();
            }
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.repaint();
            }
        });
        
        return button;
    }
    
    private void openLevelSelection() {
        LevelSelectionFrame levelSelection = new LevelSelectionFrame(this);
        levelSelection.setVisible(true);
        this.setVisible(false);
    }
    
    private void openHighScores() {
        HighScoresFrame highScores = new HighScoresFrame(this);
        highScores.setVisible(true);
        this.setVisible(false);
    }
}


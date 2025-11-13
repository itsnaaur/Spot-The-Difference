package com.spotdifference.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Main Menu GUI - The application's central hub
 * Provides navigation to Start Game, View High Scores, or Exit
 */
public class MainMenuFrame extends JFrame {
    private static final int WINDOW_WIDTH = 600;
    private static final int WINDOW_HEIGHT = 500;
    
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
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(250, 250, 252));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(60, 80, 60, 80));
        
        // Title with modern styling
        JLabel titleLabel = new JLabel("Spot the Difference");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(new Color(37, 99, 235));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle with modern font
        JLabel subtitleLabel = new JLabel("Data Structures Showcase Game");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(100, 116, 139));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Modern buttons with text icons
        JButton startButton = createStyledButton("▶ Start Game", new Color(34, 197, 94));
        JButton highScoresButton = createStyledButton("★ High Scores", new Color(59, 130, 246));
        JButton exitButton = createStyledButton("✕ Exit", new Color(239, 68, 68));
        
        // Button actions
        startButton.addActionListener(e -> openLevelSelection());
        highScoresButton.addActionListener(e -> openHighScores());
        exitButton.addActionListener(e -> System.exit(0));
        
        // Add components with spacing
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 60)));
        mainPanel.add(startButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(highScoresButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(exitButton);
        
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(320, 55));
        button.setPreferredSize(new Dimension(320, 55));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        
        // Modern rounded corners
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createEmptyBorder(5, 5, 5, 5),
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bgColor.darker(), 0, true),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
            )
        ));
        
        // Smooth hover effect with shadow
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 0, 0, 30), 2, true),
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bgColor, 0, true),
                        BorderFactory.createEmptyBorder(12, 25, 12, 25)
                    )
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
                button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createEmptyBorder(5, 5, 5, 5),
                    BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(bgColor.darker(), 0, true),
                        BorderFactory.createEmptyBorder(12, 25, 12, 25)
                    )
                ));
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


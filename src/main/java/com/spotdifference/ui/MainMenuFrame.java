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
        mainPanel.setBackground(new Color(240, 248, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 80, 50, 80));
        
        // Title
        JLabel titleLabel = new JLabel("Spot the Difference");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        titleLabel.setForeground(new Color(0, 102, 204));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Data Structures Showcase Game");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 16));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Buttons
        JButton startButton = createStyledButton("Start Game", new Color(76, 175, 80));
        JButton highScoresButton = createStyledButton("High Scores", new Color(33, 150, 243));
        JButton exitButton = createStyledButton("Exit", new Color(244, 67, 54));
        
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
        
        // Add info panel at bottom
        JPanel infoPanel = createInfoPanel();
        
        add(mainPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.SOUTH);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(300, 50));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(230, 230, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel infoLabel = new JLabel("<html><center>" +
            "Data Structures Used: HashMap • HashSet • Stack • Queue • LinkedList • Graph" +
            "</center></html>");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        infoLabel.setForeground(new Color(80, 80, 80));
        
        panel.add(infoLabel);
        return panel;
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


package com.spotdifference.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.spotdifference.manager.LevelManager;
import com.spotdifference.manager.LevelProgressionGraph;
import com.spotdifference.model.LevelData;

/**
 * Level Selection GUI - Displays available levels with Graph-based progression
 * Shows locked/unlocked status based on level completion
 */
public class LevelSelectionFrame extends JFrame {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    private JFrame parentFrame;
    private LevelManager levelManager;
    private LevelProgressionGraph progressionGraph;
    
    public LevelSelectionFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.levelManager = new LevelManager();
        this.progressionGraph = new LevelProgressionGraph();
        
        initializeFrame();
        createComponents();
    }
    
    private void initializeFrame() {
        setTitle("Select Level");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                returnToMainMenu();
            }
        });
    }
    
    private void createComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Level grid panel
        JPanel levelsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        levelsPanel.setBackground(new Color(245, 245, 250));
        levelsPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        
        // Get all levels and create buttons
        List<String> unlockedLevels = progressionGraph.getUnlockedLevels();
        
        for (String levelName : levelManager.getAllLevelNames()) {
            LevelData levelData = levelManager.getLevel(levelName);
            boolean unlocked = unlockedLevels.contains(levelName);
            boolean completed = progressionGraph.isLevelCompleted(levelName);
            
            JPanel levelCard = createLevelCard(levelName, levelData, unlocked, completed);
            levelsPanel.add(levelCard);
        }
        
        // Scroll pane for levels
        JScrollPane scrollPane = new JScrollPane(levelsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Bottom panel with back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 230, 250));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JButton backButton = new JButton("← Back to Main Menu");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(new Color(120, 120, 120));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> returnToMainMenu());
        
        bottomPanel.add(backButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createLevelCard(String levelName, LevelData levelData, boolean unlocked, boolean completed) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Level name
        JLabel nameLabel = new JLabel(levelName);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Level info
        StringBuilder difficultyStars = new StringBuilder();
        for (int i = 0; i < levelData.getDifficulty(); i++) {
            difficultyStars.append("★");
        }
        for (int i = 0; i < 5 - levelData.getDifficulty(); i++) {
            difficultyStars.append("☆");
        }
        JLabel difficultyLabel = new JLabel(difficultyStars.toString());
        difficultyLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        difficultyLabel.setForeground(new Color(255, 165, 0));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel differencesLabel = new JLabel(levelData.getTotalDifferences() + " differences");
        differencesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        differencesLabel.setForeground(Color.GRAY);
        differencesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Status label
        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("Arial", Font.BOLD, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        if (completed) {
            statusLabel.setText("✓ COMPLETED");
            statusLabel.setForeground(new Color(76, 175, 80));
        } else if (unlocked) {
            statusLabel.setText("UNLOCKED");
            statusLabel.setForeground(new Color(33, 150, 243));
        } else {
            statusLabel.setText("🔒 LOCKED");
            statusLabel.setForeground(new Color(200, 0, 0));
        }
        
        // Play button
        JButton playButton = new JButton(unlocked ? "Play" : "Locked");
        playButton.setFont(new Font("Arial", Font.BOLD, 14));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setMaximumSize(new Dimension(150, 35));
        playButton.setEnabled(unlocked);
        playButton.setCursor(unlocked ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR));
        
        if (unlocked) {
            playButton.setBackground(new Color(76, 175, 80));
            playButton.setForeground(Color.WHITE);
            playButton.setFocusPainted(false);
            playButton.addActionListener(e -> startLevel(levelName));
        } else {
            playButton.setBackground(new Color(180, 180, 180));
            playButton.setForeground(Color.WHITE);
        }
        
        // Add components
        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(difficultyLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(differencesLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(playButton);
        
        return card;
    }
    
    private void startLevel(String levelName) {
        LevelData levelData = levelManager.getLevel(levelName);
        if (levelData != null) {
            GameScreen gameScreen = new GameScreen(this, levelName, levelData, progressionGraph);
            gameScreen.setVisible(true);
            this.setVisible(false);
        }
    }
    
    private void returnToMainMenu() {
        parentFrame.setVisible(true);
        dispose();
    }
    
    /**
     * Refreshes the level selection screen (called after completing a level)
     */
    public void refresh() {
        getContentPane().removeAll();
        createComponents();
        revalidate();
        repaint();
    }
}


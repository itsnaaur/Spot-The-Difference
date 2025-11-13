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
        mainPanel.setBackground(new Color(248, 250, 252));
        
        // Modern Header with gradient effect
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(37, 99, 235));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Modern level grid panel
        JPanel levelsPanel = new JPanel(new GridLayout(0, 2, 25, 25));
        levelsPanel.setBackground(new Color(248, 250, 252));
        levelsPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
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
        
        // Modern bottom panel with back button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(241, 245, 249));
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(226, 232, 240)),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JButton backButton = new JButton("← Back to Main Menu");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(100, 116, 139));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
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
        
        // Modern card with shadow effect
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1, true),
                BorderFactory.createLineBorder(new Color(241, 245, 249), 3, true)
            ),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Level name with modern font
        JLabel nameLabel = new JLabel(levelName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Level difficulty rating - using text instead of stars
        String[] difficultyText = {"Easy", "Medium", "Hard", "Expert", "Master"};
        String difficultyRating = "Difficulty: " + difficultyText[levelData.getDifficulty() - 1];
        JLabel difficultyLabel = new JLabel(difficultyRating);
        difficultyLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        difficultyLabel.setForeground(new Color(234, 179, 8));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel differencesLabel = new JLabel(levelData.getTotalDifferences() + " differences to find");
        differencesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        differencesLabel.setForeground(new Color(100, 116, 139));
        differencesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Modern status label with badge styling
        JLabel statusLabel = new JLabel();
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 11));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setOpaque(true);
        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 12, 4, 12));
        
        if (completed) {
            statusLabel.setText("✓ COMPLETED");
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(new Color(34, 197, 94));
        } else if (unlocked) {
            statusLabel.setText("READY TO PLAY");
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(new Color(59, 130, 246));
        } else {
            statusLabel.setText("LOCKED");
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(new Color(148, 163, 184));
        }
        
        // Modern play button
        JButton playButton = new JButton(unlocked ? "▶ Play Level" : "Locked");
        playButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setMaximumSize(new Dimension(180, 42));
        playButton.setPreferredSize(new Dimension(180, 42));
        playButton.setEnabled(unlocked);
        playButton.setCursor(unlocked ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR));
        playButton.setFocusPainted(false);
        playButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        if (unlocked) {
            playButton.setBackground(new Color(34, 197, 94));
            playButton.setForeground(Color.WHITE);
            playButton.addActionListener(e -> startLevel(levelName));
            
            // Hover effect for unlocked button
            playButton.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    playButton.setBackground(new Color(22, 163, 74));
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    playButton.setBackground(new Color(34, 197, 94));
                }
            });
        } else {
            playButton.setBackground(new Color(203, 213, 225));
            playButton.setForeground(new Color(100, 116, 139));
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


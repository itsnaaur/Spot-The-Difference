package com.spotdifference.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
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
    
    private final JFrame parentFrame;
    private final LevelManager levelManager;
    private final LevelProgressionGraph progressionGraph;
    
    public LevelSelectionFrame(JFrame parentFrame) {
        // Graph drives locked/unlocked state; HashMap holds level data for cards
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
        mainPanel.setBackground(UITheme.GRAY_50);
        
        // Modern Header with gradient effect
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = UITheme.createGradient(
                    getWidth(), getHeight(),
                    UITheme.PRIMARY_BLUE,
                    UITheme.PRIMARY_BLUE_DARK
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(35, 30, 35, 30));
        
        JLabel titleLabel = new JLabel("Choose Your Level");
        titleLabel.setFont(UITheme.getTitleFont(36));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Modern level grid panel
        JPanel levelsPanel = new JPanel(new GridLayout(0, 2, 30, 30));
        levelsPanel.setBackground(UITheme.GRAY_50);
        levelsPanel.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));
        
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
        bottomPanel.setBackground(UITheme.GRAY_100);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.GRAY_200),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JButton backButton = new JButton("← Back to Main Menu");
        UITheme.styleModernButton(backButton, UITheme.GRAY_500, 45);
        backButton.addActionListener(e -> returnToMainMenu());
        
        bottomPanel.add(backButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createLevelCard(String levelName, LevelData levelData, boolean unlocked, boolean completed) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw shadow
                UITheme.drawShadow(g2d, 2, 2, getWidth() - 4, getHeight() - 4, 4);
                
                // Draw card background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                
                // Draw border
                g2d.setStroke(new BasicStroke(1.5f));
                Color borderColor = unlocked ? (completed ? UITheme.SUCCESS_GREEN : UITheme.PRIMARY_BLUE_LIGHT) : UITheme.GRAY_200;
                g2d.setColor(borderColor);
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 16, 16);
                
                g2d.dispose();
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(UITheme.BG_CARD);
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        
        // Level name with modern font
        JLabel nameLabel = new JLabel(levelName);
        nameLabel.setFont(UITheme.getHeadingFont(22));
        nameLabel.setForeground(UITheme.TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Level difficulty rating - using text instead of stars
        String[] difficultyText = {"Easy", "Medium", "Hard", "Expert", "Master"};
        Color[] difficultyColors = {
            UITheme.SUCCESS_GREEN,
            UITheme.WARNING_YELLOW,
            new Color(255, 152, 0),
            new Color(255, 87, 34),
            UITheme.DANGER_RED
        };
        String difficultyRating = difficultyText[levelData.getDifficulty() - 1];
        JLabel difficultyLabel = new JLabel("Difficulty: " + difficultyRating);
        difficultyLabel.setFont(UITheme.getButtonFont(13));
        difficultyLabel.setForeground(difficultyColors[levelData.getDifficulty() - 1]);
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel differencesLabel = new JLabel(levelData.getTotalDifferences() + " differences to find");
        differencesLabel.setFont(UITheme.getBodyFont(12));
        differencesLabel.setForeground(UITheme.TEXT_SECONDARY);
        differencesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        // Modern status label with badge styling
        JLabel statusLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw rounded badge background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                
                // Draw text
                g2d.setColor(getForeground());
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        statusLabel.setFont(UITheme.getButtonFont(10));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setOpaque(false);
        statusLabel.setPreferredSize(new Dimension(120, 28));
        
        if (completed) {
            statusLabel.setText("✓ COMPLETED");
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(UITheme.SUCCESS_GREEN);
        } else if (unlocked) {
            statusLabel.setText("READY TO PLAY");
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(UITheme.PRIMARY_BLUE);
        } else {
            statusLabel.setText("🔒 LOCKED");
            statusLabel.setForeground(Color.WHITE);
            statusLabel.setBackground(UITheme.GRAY_400);
        }
        
        // Modern play button
        JButton playButton = new JButton(unlocked ? "▶ Play Level" : "🔒 Locked") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                // Draw button background
                g2d.setColor(getBackground());
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                
                // Draw text
                g2d.setColor(getForeground());
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        playButton.setFont(UITheme.getButtonFont(13));
        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        playButton.setMaximumSize(new Dimension(200, 46));
        playButton.setPreferredSize(new Dimension(200, 46));
        playButton.setEnabled(unlocked);
        playButton.setCursor(unlocked ? new Cursor(Cursor.HAND_CURSOR) : new Cursor(Cursor.DEFAULT_CURSOR));
        playButton.setFocusPainted(false);
        playButton.setBorderPainted(false);
        playButton.setOpaque(false);
        
        if (unlocked) {
            playButton.setBackground(UITheme.SUCCESS_GREEN);
            playButton.setForeground(Color.WHITE);
            playButton.addActionListener(e -> startLevel(levelName));
            
            // Enhanced hover effect
            playButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    playButton.setBackground(UITheme.SUCCESS_GREEN_DARK);
                    playButton.repaint();
                }
                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    playButton.setBackground(UITheme.SUCCESS_GREEN);
                    playButton.repaint();
                }
                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    playButton.setBackground(UITheme.darkenColor(UITheme.SUCCESS_GREEN, 0.2f));
                    playButton.repaint();
                }
            });
        } else {
            playButton.setBackground(UITheme.GRAY_200);
            playButton.setForeground(UITheme.TEXT_DISABLED);
        }
        
        // Add components with better spacing
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(nameLabel);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(difficultyLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(differencesLabel);
        card.add(Box.createRigidArea(new Dimension(0, 15)));
        card.add(statusLabel);
        card.add(Box.createRigidArea(new Dimension(0, 18)));
        card.add(playButton);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        
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


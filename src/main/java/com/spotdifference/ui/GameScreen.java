package com.spotdifference.ui;

import com.spotdifference.logic.DifferenceChecker;
import com.spotdifference.logic.HintManager;
import com.spotdifference.logic.UndoManager;
import com.spotdifference.manager.HighScoreManager;
import com.spotdifference.manager.LevelProgressionGraph;
import com.spotdifference.model.Difference;
import com.spotdifference.model.LevelData;
import com.spotdifference.model.PlayerScore;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Main Game Screen - The core gameplay interface
 * Integrates all data structures for the complete gaming experience
 */
public class GameScreen extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    private static final int WINDOW_HEIGHT = 700;
    private static final int IMAGE_WIDTH = 550;
    private static final int IMAGE_HEIGHT = 500;
    
    private LevelSelectionFrame parentFrame;
    private String levelName;
    private LevelData levelData;
    private LevelProgressionGraph progressionGraph;
    
    // Data structure managers
    private DifferenceChecker differenceChecker;
    private UndoManager undoManager;
    private HintManager hintManager;
    private HighScoreManager highScoreManager;
    
    // Game state
    private int score;
    private int clicks;
    private long startTime;
    private List<Point> foundMarkers;
    private Timer hintTimer;
    private Difference currentHint;
    
    // UI components
    private ImagePanel leftImagePanel;
    private ImagePanel rightImagePanel;
    private JLabel scoreLabel;
    private JLabel differencesLabel;
    private JLabel timeLabel;
    private JLabel clicksLabel;
    private JButton hintButton;
    private JButton undoButton;
    private Timer gameTimer;
    
    public GameScreen(LevelSelectionFrame parentFrame, String levelName, 
                     LevelData levelData, LevelProgressionGraph progressionGraph) {
        this.parentFrame = parentFrame;
        this.levelName = levelName;
        this.levelData = levelData;
        this.progressionGraph = progressionGraph;
        this.highScoreManager = new HighScoreManager();
        
        initializeGame();
        initializeFrame();
        createComponents();
        startGameTimer();
    }
    
    private void initializeGame() {
        this.differenceChecker = new DifferenceChecker(levelData.getDifferences());
        this.undoManager = new UndoManager(5); // Max 5 undos
        this.hintManager = new HintManager(levelData.getDifferences(), 3); // Max 3 hints
        this.score = 0;
        this.clicks = 0;
        this.startTime = System.currentTimeMillis();
        this.foundMarkers = new ArrayList<>();
    }
    
    private void initializeFrame() {
        setTitle("Playing: " + levelName);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                pauseAndConfirmExit();
            }
        });
    }
    
    private void createComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 245, 250));
        
        // Top panel with level info and controls
        JPanel topPanel = createTopPanel();
        
        // Center panel with images
        JPanel centerPanel = createCenterPanel();
        
        // Bottom panel with stats
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(63, 81, 181));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Left: Level name
        JLabel levelLabel = new JLabel(levelName);
        levelLabel.setFont(new Font("Arial", Font.BOLD, 24));
        levelLabel.setForeground(Color.WHITE);
        
        // Center: Action buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        hintButton = createGameButton("💡 Hint (" + hintManager.getHintsRemaining() + ")", 
                                      new Color(255, 165, 0));
        hintButton.addActionListener(e -> useHint());
        
        undoButton = createGameButton("↶ Undo", new Color(156, 39, 176));
        undoButton.addActionListener(e -> undoLastMove());
        undoButton.setEnabled(false);
        
        JButton pauseButton = createGameButton("⏸ Pause", new Color(96, 125, 139));
        pauseButton.addActionListener(e -> pauseGame());
        
        buttonPanel.add(hintButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(pauseButton);
        
        // Right: Differences remaining
        differencesLabel = new JLabel(differenceChecker.getRemainingCount() + " left");
        differencesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        differencesLabel.setForeground(Color.YELLOW);
        
        panel.add(levelLabel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(differencesLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panel.setBackground(new Color(245, 245, 250));
        
        // Left image
        leftImagePanel = new ImagePanel(levelData.getImage1Path(), true);
        leftImagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        leftImagePanel.addMouseListener(new ImageClickListener());
        
        // Right image
        rightImagePanel = new ImagePanel(levelData.getImage2Path(), false);
        rightImagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        rightImagePanel.addMouseListener(new ImageClickListener());
        
        panel.add(leftImagePanel);
        panel.add(rightImagePanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        panel.setBackground(new Color(230, 230, 250));
        
        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        timeLabel = new JLabel("Time: 00:00");
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        clicksLabel = new JLabel("Clicks: 0");
        clicksLabel.setFont(new Font("Arial", Font.BOLD, 16));
        
        panel.add(scoreLabel);
        panel.add(timeLabel);
        panel.add(clicksLabel);
        
        return panel;
    }
    
    private JButton createGameButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
    
    private void startGameTimer() {
        gameTimer = new Timer(1000, e -> updateTimer());
        gameTimer.start();
    }
    
    private void updateTimer() {
        long elapsed = (System.currentTimeMillis() - startTime) / 1000;
        long minutes = elapsed / 60;
        long seconds = elapsed % 60;
        timeLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }
    
    /**
     * Handles image clicks - demonstrates HashSet's O(1) checking
     */
    private class ImageClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            clicks++;
            clicksLabel.setText("Clicks: " + clicks);
            Point clickPoint = e.getPoint();
            
            // Check if click matches a difference (HashSet operation)
            Difference foundDiff = differenceChecker.checkClick(clickPoint);
            
            if (foundDiff != null) {
                // Correct click!
                handleCorrectClick(foundDiff);
            } else {
                // Wrong click - penalty
                score = Math.max(0, score - 5);
                updateScore();
            }
        }
    }
    
    private void handleCorrectClick(Difference difference) {
        // Add to undo stack (Stack push operation)
        undoManager.pushMove(difference);
        undoButton.setEnabled(true);
        
        // Remove from hint queue if present
        hintManager.removeDifference(difference);
        
        // Add visual marker
        foundMarkers.add(difference.getLocation());
        leftImagePanel.addMarker(difference.getLocation());
        rightImagePanel.addMarker(difference.getLocation());
        
        // Update score
        score += 100;
        updateScore();
        
        // Update UI
        differencesLabel.setText(differenceChecker.getRemainingCount() + " left");
        
        // Check if level complete
        if (differenceChecker.isLevelComplete()) {
            levelComplete();
        }
    }
    
    /**
     * Uses hint - demonstrates Queue's FIFO dequeue operation
     */
    private void useHint() {
        if (!hintManager.hasHintsAvailable()) {
            JOptionPane.showMessageDialog(this, "No hints remaining!", 
                "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        // Get next hint from queue (Queue poll operation)
        currentHint = hintManager.getNextHint();
        if (currentHint != null) {
            // Show hint temporarily
            leftImagePanel.showHint(currentHint.getLocation());
            rightImagePanel.showHint(currentHint.getLocation());
            
            // Update button
            hintButton.setText("💡 Hint (" + hintManager.getHintsRemaining() + ")");
            if (!hintManager.hasHintsAvailable()) {
                hintButton.setEnabled(false);
            }
            
            // Remove hint after 2 seconds
            if (hintTimer != null) {
                hintTimer.stop();
            }
            hintTimer = new Timer(2000, e -> {
                leftImagePanel.clearHint();
                rightImagePanel.clearHint();
                hintTimer.stop();
            });
            hintTimer.start();
            
            // Small score penalty
            score = Math.max(0, score - 20);
            updateScore();
        }
    }
    
    /**
     * Undoes last move - demonstrates Stack's LIFO pop operation
     */
    private void undoLastMove() {
        // Pop from undo stack (Stack pop operation)
        Difference lastDiff = undoManager.popMove();
        
        if (lastDiff != null) {
            // Add back to difference checker
            differenceChecker.addDifference(lastDiff);
            
            // Remove visual marker
            foundMarkers.remove(lastDiff.getLocation());
            leftImagePanel.removeMarker(lastDiff.getLocation());
            rightImagePanel.removeMarker(lastDiff.getLocation());
            
            // Update UI
            differencesLabel.setText(differenceChecker.getRemainingCount() + " left");
            
            // Score penalty for undo
            score = Math.max(0, score - 50);
            updateScore();
        }
        
        if (!undoManager.canUndo()) {
            undoButton.setEnabled(false);
        }
    }
    
    private void updateScore() {
        scoreLabel.setText("Score: " + score);
    }
    
    private void pauseGame() {
        gameTimer.stop();
        int response = JOptionPane.showConfirmDialog(
            this,
            "Game Paused\n\nResume playing?",
            "Pause",
            JOptionPane.YES_NO_OPTION
        );
        
        if (response == JOptionPane.YES_OPTION) {
            gameTimer.start();
        } else {
            returnToLevelSelection();
        }
    }
    
    private void pauseAndConfirmExit() {
        gameTimer.stop();
        int response = JOptionPane.showConfirmDialog(
            this,
            "Exit current game?",
            "Confirm Exit",
            JOptionPane.YES_NO_OPTION
        );
        
        if (response == JOptionPane.YES_OPTION) {
            returnToLevelSelection();
        } else {
            gameTimer.start();
        }
    }
    
    private void levelComplete() {
        gameTimer.stop();
        
        // Calculate time bonus
        long timeSeconds = (System.currentTimeMillis() - startTime) / 1000;
        int timeBonus = Math.max(0, 500 - (int)timeSeconds * 2);
        score += timeBonus;
        
        // Mark level as completed in graph
        progressionGraph.completeLevel(levelName);
        
        // Show completion dialog
        String message = String.format(
            "🎉 Level Complete! 🎉\n\n" +
            "Final Score: %d\n" +
            "Time: %d seconds\n" +
            "Time Bonus: +%d\n\n" +
            "New levels may have been unlocked!",
            score, timeSeconds, timeBonus
        );
        
        // Check for high score
        boolean isHighScore = highScoreManager.isHighScore(score);
        if (isHighScore) {
            String playerName = JOptionPane.showInputDialog(
                this,
                message + "\n\nYou achieved a HIGH SCORE!\nEnter your name:",
                "Level Complete",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            if (playerName != null && !playerName.trim().isEmpty()) {
                PlayerScore playerScore = new PlayerScore(playerName.trim(), score, levelName);
                highScoreManager.addScore(playerScore);
            }
        } else {
            JOptionPane.showMessageDialog(this, message, "Level Complete", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        returnToLevelSelection();
    }
    
    private void returnToLevelSelection() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (hintTimer != null) {
            hintTimer.stop();
        }
        parentFrame.refresh(); // Refresh to show newly unlocked levels
        parentFrame.setVisible(true);
        dispose();
    }
    
    /**
     * Custom panel for displaying game images with markers
     */
    private class ImagePanel extends JPanel {
        private BufferedImage image;
        private List<Point> markers;
        private Point hintPoint;
        private boolean isLeftImage;
        
        public ImagePanel(String imagePath, boolean isLeftImage) {
            this.isLeftImage = isLeftImage;
            this.markers = new ArrayList<>();
            this.image = loadImage(imagePath);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        }
        
        private BufferedImage loadImage(String imagePath) {
            try {
                // Try to load the image from the classpath
                java.io.InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
                if (inputStream != null) {
                    BufferedImage img = ImageIO.read(inputStream);
                    inputStream.close();
                    if (img != null) {
                        return img;
                    }
                }
                System.out.println("Could not load image: " + imagePath);
            } catch (Exception e) {
                System.err.println("Error loading image " + imagePath + ": " + e.getMessage());
            }
            // Fall back to placeholder if image loading fails
            return createPlaceholderImage();
        }
        
        private BufferedImage createPlaceholderImage() {
            BufferedImage img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, 
                                                 BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            
            // Gradient background
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(100, 150, 200),
                IMAGE_WIDTH, IMAGE_HEIGHT, new Color(200, 150, 100)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
            
            // Add some shapes
            g2d.setColor(new Color(255, 255, 255, 100));
            for (int i = 0; i < 10; i++) {
                int x = (int)(Math.random() * IMAGE_WIDTH);
                int y = (int)(Math.random() * IMAGE_HEIGHT);
                int size = (int)(Math.random() * 100) + 50;
                g2d.fillOval(x, y, size, size);
            }
            
            // Text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 30));
            String text = isLeftImage ? "Left Image" : "Right Image";
            FontMetrics fm = g2d.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g2d.drawString(text, (IMAGE_WIDTH - textWidth) / 2, IMAGE_HEIGHT / 2);
            
            g2d.dispose();
            return img;
        }
        
        public void addMarker(Point point) {
            markers.add(point);
            repaint();
        }
        
        public void removeMarker(Point point) {
            markers.remove(point);
            repaint();
        }
        
        public void showHint(Point point) {
            this.hintPoint = point;
            repaint();
        }
        
        public void clearHint() {
            this.hintPoint = null;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            // Draw image
            if (image != null) {
                g2d.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, this);
            }
            
            // Draw found markers
            g2d.setColor(new Color(0, 255, 0, 150));
            g2d.setStroke(new BasicStroke(3));
            for (Point marker : markers) {
                int x = marker.x;
                int y = marker.y;
                g2d.drawOval(x - 20, y - 20, 40, 40);
                g2d.drawLine(x - 15, y, x + 15, y);
                g2d.drawLine(x, y - 15, x, y + 15);
            }
            
            // Draw hint
            if (hintPoint != null) {
                g2d.setColor(new Color(255, 255, 0, 200));
                g2d.setStroke(new BasicStroke(4));
                int x = hintPoint.x;
                int y = hintPoint.y;
                g2d.drawOval(x - 25, y - 25, 50, 50);
                g2d.drawOval(x - 30, y - 30, 60, 60);
            }
        }
    }
}


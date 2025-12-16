package com.spotdifference.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import com.spotdifference.logic.DifferenceChecker;
import com.spotdifference.logic.HintManager;
import com.spotdifference.logic.UndoManager;
import com.spotdifference.manager.HighScoreManager;
import com.spotdifference.manager.LevelProgressionGraph;
import com.spotdifference.model.Difference;
import com.spotdifference.model.LevelData;
import com.spotdifference.model.PlayerScore;

/**
 * Main Game Screen - The core gameplay interface
 * Integrates all data structures for the complete gaming experience
 */
public class GameScreen extends JFrame {
    private static final int WINDOW_WIDTH = 1200;
    // Taller game window so full-size images + HUD fit comfortably
    private static final int WINDOW_HEIGHT = 820;
    private static final int IMAGE_WIDTH = 550;
    // Original image height (1:1 with your image coordinates for accurate clicks)
    private static final int IMAGE_HEIGHT = 500;
    
    private final LevelSelectionFrame parentFrame;
    private final String levelName;
    private final LevelData levelData;
    private final LevelProgressionGraph progressionGraph;
    
    // Data structure managers
    private DifferenceChecker differenceChecker;
    private UndoManager undoManager;
    private HintManager hintManager;
    private final HighScoreManager highScoreManager;
    
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
        
        // Wire up all data-structure-driven systems for this run
        initializeGame();
        initializeFrame();
        createComponents();
        startGameTimer();
    }
    
    private void initializeGame() {
        // HashSet: remaining differences (O(1) contains/removal)
        this.differenceChecker = new DifferenceChecker(levelData.getDifferences());
        // Stack: undo history (LIFO)
        this.undoManager = new UndoManager(5); // Max 5 undos
        // Queue: hint order (FIFO)
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
        mainPanel.setBackground(UITheme.BG_PANEL);
        
        JPanel topPanel = createTopPanel();
        JPanel centerPanel = createCenterPanel();
        JPanel bottomPanel = createBottomPanel();
        
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createTopPanel() {
        JPanel panel = new JPanel() {
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
        panel.setLayout(new BorderLayout());
        panel.setOpaque(false);
        panel.setBorder(BorderFactory.createEmptyBorder(22, 30, 22, 30));
        
        JLabel levelLabel = new JLabel("Playing: " + levelName);
        levelLabel.setFont(UITheme.getHeadingFont(22));
        levelLabel.setForeground(Color.WHITE);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        buttonPanel.setOpaque(false);
        
        hintButton = createGameButton("Hint (" + hintManager.getHintsRemaining() + ")", 
                                      UITheme.WARNING_YELLOW);
        hintButton.addActionListener(e -> useHint());
        
        undoButton = createGameButton("Undo", UITheme.ACCENT_PURPLE);
        undoButton.addActionListener(e -> undoLastMove());
        undoButton.setEnabled(false);
        
        JButton pauseButton = createGameButton("Pause", UITheme.GRAY_500);
        pauseButton.addActionListener(e -> pauseGame());
        
        buttonPanel.add(hintButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(pauseButton);
        
        differencesLabel = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                g2d.setColor(UITheme.WARNING_YELLOW_LIGHT);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                
                g2d.setStroke(new BasicStroke(2f));
                g2d.setColor(UITheme.WARNING_YELLOW);
                g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 20, 20);
                
                g2d.setColor(UITheme.TEXT_PRIMARY);
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        differencesLabel.setText(differenceChecker.getRemainingCount() + " remaining");
        differencesLabel.setFont(UITheme.getButtonFont(16));
        differencesLabel.setOpaque(false);
        differencesLabel.setPreferredSize(new Dimension(140, 36));
        
        panel.add(levelLabel, BorderLayout.WEST);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(differencesLabel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createCenterPanel() {
        // Strict 1x2 grid so both images are always visible side by side
        JPanel panel = new JPanel(new java.awt.GridLayout(1, 2, 30, 0));
        panel.setBackground(UITheme.BG_PANEL);
        // Slightly more top padding, modest bottom padding so full 550x500
        // images sit cleanly above the stats bar without feeling cramped.
        panel.setBorder(BorderFactory.createEmptyBorder(40, 25, 20, 25));
        
        leftImagePanel = new ImagePanel(levelData.getImage1Path(), true);
        leftImagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        leftImagePanel.addMouseListener(new ImageClickListener());
        
        rightImagePanel = new ImagePanel(levelData.getImage2Path(), false);
        rightImagePanel.setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        rightImagePanel.addMouseListener(new ImageClickListener());
        
        panel.add(leftImagePanel);
        panel.add(rightImagePanel);
        
        return panel;
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 60, 18));
        panel.setBackground(UITheme.GRAY_100);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.GRAY_200),
            BorderFactory.createEmptyBorder(16, 20, 16, 20)
        ));
        
        scoreLabel = createStatLabel("Score: 0", UITheme.PRIMARY_BLUE_LIGHT);
        timeLabel = createStatLabel("Time: 00:00", UITheme.TEXT_SECONDARY);
        clicksLabel = createStatLabel("Clicks: 0", UITheme.TEXT_SECONDARY);
        
        panel.add(scoreLabel);
        panel.add(timeLabel);
        panel.add(clicksLabel);
        
        return panel;
    }
    
    private JLabel createStatLabel(String text, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(UITheme.getHeadingFont(17));
        label.setForeground(color);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 1, true),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        return label;
    }
    
    private JButton createGameButton(String text, Color bgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                if (isEnabled()) {
                    UITheme.drawShadow(g2d, 2, 2, getWidth() - 4, getHeight() - 4, 2);
                    g2d.setColor(getBackground());
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                    g2d.setStroke(new BasicStroke(1.5f));
                    g2d.setColor(UITheme.darkenColor(getBackground(), 0.1f));
                    g2d.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 10, 10);
                } else {
                    g2d.setColor(UITheme.GRAY_200);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                }
                
                g2d.setColor(isEnabled() ? getForeground() : UITheme.TEXT_DISABLED);
                FontMetrics fm = g2d.getFontMetrics(getFont());
                int textX = (getWidth() - fm.stringWidth(getText())) / 2;
                int textY = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
                g2d.drawString(getText(), textX, textY);
                
                g2d.dispose();
            }
        };
        
        UITheme.styleModernButton(button, bgColor, 38);
        button.setPreferredSize(new Dimension(button.getPreferredSize().width, 38));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(UITheme.lightenColor(bgColor, 0.15f));
                    button.repaint();
                }
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                button.setBackground(bgColor);
                    button.repaint();
                }
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (button.isEnabled()) {
                    button.setBackground(UITheme.darkenColor(bgColor, 0.15f));
                    button.repaint();
                }
            }
        });
        
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
    
    private class ImageClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            clicks++;
            clicksLabel.setText("Clicks: " + clicks);
            Point clickPoint = e.getPoint();
            
            Difference foundDiff = differenceChecker.checkClick(clickPoint);
            
            if (foundDiff != null) {
                handleCorrectClick(foundDiff);
            } else {
                score = Math.max(0, score - 5);
                updateScore();
            }
        }
    }
    
    private void handleCorrectClick(Difference difference) {
        undoManager.pushMove(difference);
        undoButton.setEnabled(true);
        
        hintManager.removeDifference(difference);
        
        foundMarkers.add(difference.getLocation());
        leftImagePanel.addMarker(difference.getLocation());
        rightImagePanel.addMarker(difference.getLocation());
        
        score += 100;
        updateScore();
        
        differencesLabel.setText(differenceChecker.getRemainingCount() + " remaining");
        
        if (differenceChecker.isLevelComplete()) {
            levelComplete();
        }
    }
    
    private void useHint() {
        if (!hintManager.hasHintsAvailable()) {
            JOptionPane.showMessageDialog(this, "No hints remaining!", 
                "Hint", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        currentHint = hintManager.getNextHint();
        if (currentHint != null) {
            leftImagePanel.showHint(currentHint.getLocation());
            rightImagePanel.showHint(currentHint.getLocation());
            
            hintButton.setText("Hint (" + hintManager.getHintsRemaining() + ")");
            if (!hintManager.hasHintsAvailable()) {
                hintButton.setEnabled(false);
            }
            
            if (hintTimer != null) {
                hintTimer.stop();
            }
            hintTimer = new Timer(2000, e -> {
                leftImagePanel.clearHint();
                rightImagePanel.clearHint();
                hintTimer.stop();
            });
            hintTimer.start();
            
            score = Math.max(0, score - 20);
            updateScore();
        }
    }
    
    private void undoLastMove() {
        Difference lastDiff = undoManager.popMove();
        
        if (lastDiff != null) {
            differenceChecker.addDifference(lastDiff);
            
            foundMarkers.remove(lastDiff.getLocation());
            leftImagePanel.removeMarker(lastDiff.getLocation());
            rightImagePanel.removeMarker(lastDiff.getLocation());
            
            differencesLabel.setText(differenceChecker.getRemainingCount() + " remaining");
            
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
        
        long timeSeconds = (System.currentTimeMillis() - startTime) / 1000;
        int timeBonus = Math.max(0, 500 - (int) timeSeconds * 2);
        score += timeBonus;
        
        progressionGraph.completeLevel(levelName);
        
        JDialog completionDialog = createCompletionDialog(timeSeconds, timeBonus);
        completionDialog.setVisible(true);
        
        returnToLevelSelection();
    }
    
    private JDialog createCompletionDialog(long timeSeconds, int timeBonus) {
        JDialog dialog = new JDialog(this, "Level Complete!", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = UITheme.createGradient(
                    getWidth(), getHeight(),
                    UITheme.SUCCESS_GREEN_LIGHT,
                    UITheme.SUCCESS_GREEN
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        JLabel titleLabel = new JLabel("Level Complete!");
        titleLabel.setFont(UITheme.getTitleFont(32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setOpaque(false);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JLabel finalScoreLabel = createStatRow("Final Score:", String.valueOf(score));
        JLabel timeDisplayLabel = createStatRow("Time:", String.format("%d seconds", timeSeconds));
        JLabel bonusLabel = createStatRow("Time Bonus:", "+" + timeBonus);
        
        statsPanel.add(finalScoreLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(timeDisplayLabel);
        statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        statsPanel.add(bonusLabel);
        
        boolean isHighScore = highScoreManager.isHighScore(score);
        
        if (isHighScore) {
            JLabel highScoreLabel = new JLabel("NEW HIGH SCORE!");
            highScoreLabel.setFont(UITheme.getHeadingFont(20));
            highScoreLabel.setForeground(UITheme.WARNING_YELLOW_LIGHT);
            highScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            statsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            statsPanel.add(highScoreLabel);
            
            JTextField nameField = new JTextField(15);
            nameField.setFont(UITheme.getBodyFont(14));
            nameField.setMaximumSize(new Dimension(250, 35));
            nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
            
            statsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
            statsPanel.add(new JLabel("Enter your name:"));
            statsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
            statsPanel.add(nameField);
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.setOpaque(false);
            
            // Create action to submit score (used by both button and Enter key)
            Runnable submitAction = () -> {
                String playerName = nameField.getText().trim();
                if (!playerName.isEmpty()) {
                    PlayerScore playerScore = new PlayerScore(playerName, score, levelName);
                    highScoreManager.addScore(playerScore);
                    dialog.dispose();
                }
            };
            
            JButton submitButton = new JButton("Submit Score");
            UITheme.styleModernButton(submitButton, UITheme.PRIMARY_BLUE, 40);
            submitButton.addActionListener(e -> submitAction.run());
            
            // Add Enter key support to the text field
            nameField.addActionListener(e -> submitAction.run());
            
            // Set submit button as default (highlighted, responds to Enter)
            dialog.getRootPane().setDefaultButton(submitButton);
            
            JButton skipButton = new JButton("Skip");
            UITheme.styleModernButton(skipButton, UITheme.GRAY_500, 40);
            skipButton.addActionListener(e -> dialog.dispose());
            
            buttonPanel.add(submitButton);
            buttonPanel.add(Box.createRigidArea(new Dimension(15, 0)));
            buttonPanel.add(skipButton);
            statsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            statsPanel.add(buttonPanel);
            
            // Request focus on the text field when dialog is shown
            nameField.requestFocusInWindow();
        } else {
            JLabel unlockLabel = new JLabel("New levels may have been unlocked!");
            unlockLabel.setFont(UITheme.getBodyFont(14));
            unlockLabel.setForeground(Color.WHITE);
            unlockLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            statsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            statsPanel.add(unlockLabel);
            
            JButton okButton = new JButton("Continue");
            UITheme.styleModernButton(okButton, UITheme.PRIMARY_BLUE, 40);
            okButton.setAlignmentX(Component.CENTER_ALIGNMENT);
            okButton.addActionListener(e -> dialog.dispose());
            statsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
            statsPanel.add(okButton);
        }
        
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(statsPanel);
        
        dialog.add(mainPanel);
        return dialog;
    }
    
    private JLabel createStatRow(String label, String value) {
        JLabel rowLabel = new JLabel(label + "  " + value);
        rowLabel.setFont(UITheme.getHeadingFont(16));
        rowLabel.setForeground(Color.WHITE);
        rowLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return rowLabel;
    }
    
    private void returnToLevelSelection() {
        if (gameTimer != null) {
            gameTimer.stop();
        }
        if (hintTimer != null) {
            hintTimer.stop();
        }
        parentFrame.refresh();
        parentFrame.setVisible(true);
        dispose();
    }
    
    private class ImagePanel extends JPanel {
        private final BufferedImage image;
        private final List<Point> markers;
        private Point hintPoint;
        private final boolean isLeftImage;
        
        public ImagePanel(String imagePath, boolean isLeftImage) {
            this.isLeftImage = isLeftImage;
            this.markers = new ArrayList<>();
            this.image = loadImage(imagePath);
            // Let the parent background show through so we don't get any
            // unwanted white behind or beside the image.
            setOpaque(false);
            setPreferredSize(new Dimension(IMAGE_WIDTH, IMAGE_HEIGHT));
        }
        
        private BufferedImage loadImage(String imagePath) {
            try {
                java.io.InputStream inputStream = getClass().getClassLoader().getResourceAsStream(imagePath);
                if (inputStream != null) {
                    try {
                    BufferedImage img = ImageIO.read(inputStream);
                    if (img != null) {
                        return img;
                        }
                    } finally {
                        inputStream.close();
                    }
                }
                System.out.println("Could not load image: " + imagePath);
            } catch (java.io.IOException e) {
                System.err.println("Error loading image " + imagePath + ": " + e.getMessage());
            }
            return createPlaceholderImage();
        }
        
        private BufferedImage createPlaceholderImage() {
            BufferedImage img = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, 
                                                 BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = img.createGraphics();
            
            GradientPaint gradient = new GradientPaint(
                0, 0, new Color(30, 64, 175),
                IMAGE_WIDTH, IMAGE_HEIGHT, new Color(15, 23, 42)
            );
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
            
            g2d.setColor(new Color(255, 255, 255, 60));
            for (int i = 0; i < 10; i++) {
                int x = (int) (Math.random() * IMAGE_WIDTH);
                int y = (int) (Math.random() * IMAGE_HEIGHT);
                int size = (int) (Math.random() * 100) + 50;
                g2d.fillOval(x, y, size, size);
            }
            
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
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            
            // Soft shadow only, no extra white frame so the image sits flush
            UITheme.drawShadow(g2d, 4, 4, IMAGE_WIDTH - 8, IMAGE_HEIGHT - 8, 4);
            
            if (image != null) {
                g2d.drawImage(image, 0, 0, IMAGE_WIDTH, IMAGE_HEIGHT, this);
            }
            
            for (Point marker : markers) {
                int x = marker.x;
                int y = marker.y;
                int radius = 25;
                
                g2d.setColor(UITheme.withAlpha(UITheme.SUCCESS_GREEN, 0.3f));
                g2d.fillOval(x - radius - 5, y - radius - 5, (radius + 5) * 2, (radius + 5) * 2);
                
                g2d.setColor(UITheme.withAlpha(UITheme.SUCCESS_GREEN, 0.6f));
                g2d.setStroke(new BasicStroke(3f));
                g2d.drawOval(x - radius, y - radius, radius * 2, radius * 2);
                
                g2d.setColor(UITheme.SUCCESS_GREEN);
                g2d.fillOval(x - 12, y - 12, 24, 24);
                
                g2d.setColor(Color.WHITE);
                g2d.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(x - 7, y, x - 2, y + 5);
                g2d.drawLine(x - 2, y + 5, x + 7, y - 5);
            }
            
            if (hintPoint != null) {
                int x = hintPoint.x;
                int y = hintPoint.y;
                long time = System.currentTimeMillis() % 1000;
                float pulse = (float) (0.5 + 0.5 * Math.sin(time * 0.01));
                
                int outerRadius = (int) (35 + pulse * 10);
                g2d.setColor(UITheme.withAlpha(UITheme.WARNING_YELLOW, 0.4f * pulse));
                g2d.fillOval(x - outerRadius, y - outerRadius, outerRadius * 2, outerRadius * 2);
                
                g2d.setColor(UITheme.WARNING_YELLOW);
                g2d.setStroke(new BasicStroke(4f));
                g2d.drawOval(x - 30, y - 30, 60, 60);
                
                g2d.setColor(UITheme.WARNING_YELLOW_LIGHT);
                g2d.fillOval(x - 15, y - 15, 30, 30);
            }
            
            g2d.dispose();
        }
    }
}

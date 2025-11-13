package com.spotdifference.ui;

import com.spotdifference.manager.HighScoreManager;
import com.spotdifference.model.PlayerScore;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedList;

/**
 * High Scores GUI - Displays the leaderboard using LinkedList
 * Shows top player scores in sorted order
 */
public class HighScoresFrame extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;
    
    private JFrame parentFrame;
    private HighScoreManager highScoreManager;
    
    public HighScoresFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.highScoreManager = new HighScoreManager();
        
        initializeFrame();
        createComponents();
    }
    
    private void initializeFrame() {
        setTitle("High Scores");
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
        
        // Modern Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(234, 179, 8));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 30, 20));
        
        JLabel titleLabel = new JLabel("High Scores Leaderboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Table for scores
        JPanel tablePanel = createScoresTable();
        
        // Modern bottom panel
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
        
        JButton clearButton = new JButton("Clear All Scores");
        clearButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        clearButton.setBackground(new Color(239, 68, 68));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        clearButton.setBorder(BorderFactory.createEmptyBorder(12, 24, 12, 24));
        clearButton.addActionListener(e -> clearScores());
        
        bottomPanel.add(backButton);
        bottomPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        bottomPanel.add(clearButton);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel createScoresTable() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        // Column names
        String[] columnNames = {"Rank", "Player", "Score", "Level", "Date"};
        
        // Get scores from LinkedList
        LinkedList<PlayerScore> scores = highScoreManager.getHighScores();
        
        // Create table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Populate table
        if (scores.isEmpty()) {
            // Show empty message
            JLabel emptyLabel = new JLabel("No high scores yet. Start playing!");
            emptyLabel.setFont(new Font("Arial", Font.ITALIC, 18));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(emptyLabel, BorderLayout.CENTER);
            return panel;
        }
        
        int rank = 1;
        for (PlayerScore score : scores) {
            Object[] row = {
                "#" + rank,
                score.getPlayerName(),
                score.getScore() + " pts",
                score.getLevelName(),
                score.getFormattedTimestamp()
            };
            model.addRow(row);
            rank++;
        }
        
        // Create modern table
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(40);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(37, 99, 235));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(219, 234, 254));
        table.setGridColor(new Color(226, 232, 240));
        
        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(140);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Info label
        JLabel infoLabel = new JLabel("Data Structure: LinkedList - Demonstrates sorted insertion and traversal");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoLabel.setForeground(Color.GRAY);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(infoLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void clearScores() {
        int response = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to clear all high scores?",
            "Clear Scores",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (response == JOptionPane.YES_OPTION) {
            highScoreManager.clearScores();
            getContentPane().removeAll();
            createComponents();
            revalidate();
            repaint();
        }
    }
    
    private void returnToMainMenu() {
        parentFrame.setVisible(true);
        dispose();
    }
}


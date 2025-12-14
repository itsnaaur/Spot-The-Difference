package com.spotdifference.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.spotdifference.manager.HighScoreManager;
import com.spotdifference.model.PlayerScore;

/**
 * High Scores GUI - Displays the leaderboard using LinkedList
 * Shows top player scores in sorted order
 */
public class HighScoresFrame extends JFrame {
    private static final int WINDOW_WIDTH = 700;
    private static final int WINDOW_HEIGHT = 600;
    
    private final JFrame parentFrame;
    private final HighScoreManager highScoreManager;
    
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
        mainPanel.setBackground(UITheme.GRAY_50);
        
        // Modern Header with gradient
        JPanel headerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                
                GradientPaint gradient = UITheme.createGradient(
                    getWidth(), getHeight(),
                    UITheme.WARNING_YELLOW,
                    UITheme.WARNING_YELLOW_DARK
                );
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(35, 30, 35, 30));
        
        JLabel titleLabel = new JLabel("★ High Scores Leaderboard");
        titleLabel.setFont(UITheme.getTitleFont(32));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        // Table for scores
        JPanel tablePanel = createScoresTable();
        
        // Modern bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(UITheme.GRAY_100);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UITheme.GRAY_200),
            BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));
        
        JButton backButton = new JButton("← Back to Main Menu");
        UITheme.styleModernButton(backButton, UITheme.GRAY_500, 45);
        backButton.addActionListener(e -> returnToMainMenu());
        
        JButton clearButton = new JButton("Clear All Scores");
        UITheme.styleModernButton(clearButton, UITheme.DANGER_RED, 45);
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
        panel.setBackground(UITheme.BG_CARD);
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        
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
            // Show empty message with better styling
            JLabel emptyLabel = new JLabel("No high scores yet. Start playing! 🎮");
            emptyLabel.setFont(UITheme.getHeadingFont(20));
            emptyLabel.setForeground(UITheme.TEXT_SECONDARY);
            emptyLabel.setHorizontalAlignment(SwingConstants.CENTER);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(60, 0, 60, 0));
            panel.add(emptyLabel, BorderLayout.CENTER);
            return panel;
        }
        
        int rank = 1;
        for (PlayerScore score : scores) {
            Object[] row = {
                rank == 1 ? "🥇" : rank == 2 ? "🥈" : rank == 3 ? "🥉" : "#" + rank,
                score.getPlayerName(),
                score.getScore() + " pts",
                score.getLevelName(),
                score.getFormattedTimestamp()
            };
            model.addRow(row);
            rank++;
        }
        
        // Create modern table
        JTable table = new JTable(model) {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                
                // Alternate row colors
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? UITheme.BG_CARD : UITheme.GRAY_50);
                }
                
                // Highlight top 3
                switch (row) {
                    case 0:
                        c.setBackground(UITheme.withAlpha(UITheme.WARNING_YELLOW_LIGHT, 0.3f));
                        break;
                    case 1:
                        c.setBackground(UITheme.withAlpha(UITheme.GRAY_200, 0.5f));
                        break;
                    case 2:
                        c.setBackground(UITheme.withAlpha(new Color(205, 127, 50), 0.3f));
                        break;
                    default:
                        break;
                }
                
                return c;
            }
        };
        
        table.setFont(UITheme.getBodyFont(14));
        table.setRowHeight(45);
        table.getTableHeader().setFont(UITheme.getButtonFont(14));
        table.getTableHeader().setBackground(UITheme.PRIMARY_BLUE);
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setPreferredSize(new Dimension(table.getTableHeader().getWidth(), 45));
        table.setSelectionBackground(UITheme.withAlpha(UITheme.PRIMARY_BLUE_LIGHT, 0.3f));
        table.setSelectionForeground(UITheme.TEXT_PRIMARY);
        table.setGridColor(UITheme.GRAY_200);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(0, 1));
        
        // Custom header renderer
        table.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setFont(UITheme.getButtonFont(14));
                c.setForeground(Color.WHITE);
                c.setBackground(UITheme.PRIMARY_BLUE);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                return c;
            }
        });
        
        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                ((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
                c.setFont(UITheme.getBodyFont(14));
                return c;
            }
        };
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(70);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(110);
        table.getColumnModel().getColumn(3).setPreferredWidth(160);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UITheme.GRAY_200, 2),
            BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));
        scrollPane.getViewport().setBackground(UITheme.BG_CARD);
        scrollPane.setBackground(UITheme.BG_CARD);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
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


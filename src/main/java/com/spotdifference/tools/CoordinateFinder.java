package com.spotdifference.tools;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * COORDINATE FINDER TOOL
 * 
 * This tool helps you find the exact coordinates of differences in your images.
 * 
 * HOW TO USE:
 * 1. Run this tool (right-click → Run As Java Application)
 * 2. Click "Load Images" and select your two images
 * 3. Click on each difference location on the LEFT image
 * 4. You'll see a circle appear
 * 5. Copy the coordinates from the console
 * 6. Use these coordinates in LevelManager.java
 */
public class CoordinateFinder extends JFrame {
    
    private BufferedImage image1;
    private BufferedImage image2;
    private List<Point> clickedPoints = new ArrayList<>();
    private ImagePanel leftPanel;
    private ImagePanel rightPanel;
    private int defaultRadius = 30; // Default click radius
    
    public CoordinateFinder() {
        setTitle("Coordinate Finder Tool - Spot the Difference");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createUI();
        setLocationRelativeTo(null);
    }
    
    private void createUI() {
        // Top Panel - Instructions and Controls
        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(63, 81, 181));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel titleLabel = new JLabel("Coordinate Finder Tool");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JLabel instructionLabel = new JLabel("Click on differences in the LEFT image");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        instructionLabel.setForeground(Color.YELLOW);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);
        
        JButton loadButton = new JButton("📁 Load Images");
        loadButton.setFont(new Font("Arial", Font.BOLD, 14));
        loadButton.addActionListener(e -> loadImages());
        
        JButton clearButton = new JButton("🗑️ Clear All");
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(e -> clearPoints());
        
        JButton printButton = new JButton("📋 Print Code");
        printButton.setFont(new Font("Arial", Font.BOLD, 14));
        printButton.addActionListener(e -> printCode());
        
        JButton undoButton = new JButton("↶ Undo Last");
        undoButton.setFont(new Font("Arial", Font.BOLD, 14));
        undoButton.addActionListener(e -> undoLast());
        
        buttonPanel.add(loadButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(undoButton);
        buttonPanel.add(printButton);
        
        topPanel.add(titleLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(instructionLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(buttonPanel);
        
        // Center Panel - Images
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        centerPanel.setBackground(new Color(240, 240, 240));
        
        leftPanel = new ImagePanel(true);
        rightPanel = new ImagePanel(false);
        
        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);
        
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        
        // Bottom Panel - Info
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(230, 230, 250));
        JLabel infoLabel = new JLabel("Total Differences Marked: 0 | Radius: " + defaultRadius + " pixels");
        infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        bottomPanel.add(infoLabel);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadImages() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select FIRST Image (Original)");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image files", "jpg", "jpeg", "png", "gif", "bmp"));
        
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            try {
                image1 = ImageIO.read(chooser.getSelectedFile());
                
                chooser.setDialogTitle("Select SECOND Image (With Differences)");
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    image2 = ImageIO.read(chooser.getSelectedFile());
                    
                    leftPanel.setImage(image1);
                    rightPanel.setImage(image2);
                    
                    JOptionPane.showMessageDialog(this,
                        "Images loaded successfully!\n\n" +
                        "Now click on each difference location in the LEFT image.\n" +
                        "The same spot will be highlighted on the RIGHT image.",
                        "Ready!",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Error loading images: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void clearPoints() {
        clickedPoints.clear();
        leftPanel.repaint();
        rightPanel.repaint();
        updateInfo();
        System.out.println("All points cleared!");
    }
    
    private void undoLast() {
        if (!clickedPoints.isEmpty()) {
            clickedPoints.remove(clickedPoints.size() - 1);
            leftPanel.repaint();
            rightPanel.repaint();
            updateInfo();
            System.out.println("Last point removed. Total: " + clickedPoints.size());
        }
    }
    
    private void printCode() {
        if (clickedPoints.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "No differences marked yet!\nClick on the differences first.",
                "No Data",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        System.out.println("\n========================================");
        System.out.println("COPY THIS CODE TO LevelManager.java:");
        System.out.println("========================================\n");
        
        for (int i = 0; i < clickedPoints.size(); i++) {
            Point p = clickedPoints.get(i);
            System.out.println(String.format("level.addDifference(%d, %d, %d);  // Difference %d",
                p.x, p.y, defaultRadius, i + 1));
        }
        
        System.out.println("\n========================================");
        System.out.println("Total Differences: " + clickedPoints.size());
        System.out.println("========================================\n");
        
        // Also show in dialog
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < clickedPoints.size(); i++) {
            Point p = clickedPoints.get(i);
            code.append(String.format("level.addDifference(%d, %d, %d);\n",
                p.x, p.y, defaultRadius));
        }
        
        JTextArea textArea = new JTextArea(code.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this,
            scrollPane,
            "Code Generated - Copy This!",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateInfo() {
        // Update bottom label
        Component[] components = ((JPanel)getContentPane().getComponent(2)).getComponents();
        if (components.length > 0 && components[0] instanceof JLabel) {
            ((JLabel)components[0]).setText(
                "Total Differences Marked: " + clickedPoints.size() + 
                " | Radius: " + defaultRadius + " pixels");
        }
    }
    
    private class ImagePanel extends JPanel {
        private BufferedImage image;
        private boolean isLeftPanel;
        
        public ImagePanel(boolean isLeftPanel) {
            this.isLeftPanel = isLeftPanel;
            setPreferredSize(new Dimension(550, 500));
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                isLeftPanel ? "Image 1 (Click Here!)" : "Image 2 (Preview)"
            ));
            
            if (isLeftPanel) {
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (image != null) {
                            Point p = e.getPoint();
                            clickedPoints.add(p);
                            repaint();
                            rightPanel.repaint();
                            updateInfo();
                            
                            System.out.println(String.format(
                                "Point %d: x=%d, y=%d (Radius: %d)",
                                clickedPoints.size(), p.x, p.y, defaultRadius));
                        }
                    }
                });
            }
        }
        
        public void setImage(BufferedImage img) {
            this.image = img;
            repaint();
        }
        
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                               RenderingHints.VALUE_ANTIALIAS_ON);
            
            if (image != null) {
                // Draw image scaled to fit
                g2d.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Show placeholder
                g2d.setColor(Color.LIGHT_GRAY);
                String text = isLeftPanel ? "Load images to start" : "Preview appears here";
                FontMetrics fm = g2d.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(text)) / 2;
                int y = getHeight() / 2;
                g2d.drawString(text, x, y);
            }
            
            // Draw marked points
            if (image != null) {
                g2d.setStroke(new BasicStroke(3));
                for (int i = 0; i < clickedPoints.size(); i++) {
                    Point p = clickedPoints.get(i);
                    
                    // Draw circle
                    g2d.setColor(new Color(255, 0, 0, 180));
                    g2d.drawOval(p.x - defaultRadius, p.y - defaultRadius,
                               defaultRadius * 2, defaultRadius * 2);
                    
                    // Draw crosshair
                    g2d.setColor(new Color(0, 255, 0, 200));
                    g2d.drawLine(p.x - 15, p.y, p.x + 15, p.y);
                    g2d.drawLine(p.x, p.y - 15, p.x, p.y + 15);
                    
                    // Draw number
                    g2d.setColor(Color.WHITE);
                    g2d.fillOval(p.x - 12, p.y - 25, 24, 24);
                    g2d.setColor(Color.RED);
                    g2d.setFont(new Font("Arial", Font.BOLD, 14));
                    String num = String.valueOf(i + 1);
                    FontMetrics fm = g2d.getFontMetrics();
                    int numX = p.x - fm.stringWidth(num) / 2;
                    g2d.drawString(num, numX, p.y - 8);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CoordinateFinder tool = new CoordinateFinder();
            tool.setVisible(true);
            
            System.out.println("========================================");
            System.out.println("COORDINATE FINDER TOOL");
            System.out.println("========================================");
            System.out.println("1. Click 'Load Images' button");
            System.out.println("2. Select your two images");
            System.out.println("3. Click on each difference in LEFT image");
            System.out.println("4. Click 'Print Code' to get coordinates");
            System.out.println("5. Copy the code to LevelManager.java");
            System.out.println("========================================\n");
        });
    }
}


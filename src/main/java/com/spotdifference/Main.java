package com.spotdifference;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.spotdifference.ui.MainMenuFrame;

/**
 * Main entry point for the Spot the Difference game.
 * Initializes the application and displays the main menu.
 */
public class Main {
    public static void main(String[] args) {
        // Set modern FlatLaf Look and Feel (if available)
        try {
            Class<?> flatLafClass = Class.forName("com.formdev.flatlaf.FlatIntelliJLaf");
            Object flatLafInstance = flatLafClass.getDeclaredConstructor().newInstance();
            UIManager.setLookAndFeel((javax.swing.LookAndFeel) flatLafInstance);
            // Enable modern window decorations
            System.setProperty("flatlaf.useWindowDecorations", "true");
            // Enable macOS-style rounded corners on Windows
            System.setProperty("flatlaf.menuBarEmbedded", "false");
        } catch (Exception e) {
            // FlatLaf not available - use system default Look and Feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                // If that fails too, just use default
                System.err.println("Using default Look and Feel.");
            }
        }
        
        // Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainMenuFrame mainMenu = new MainMenuFrame();
            mainMenu.setVisible(true);
        });
    }
}


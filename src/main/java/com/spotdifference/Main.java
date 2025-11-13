package com.spotdifference;

import com.spotdifference.ui.MainMenuFrame;
import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Main entry point for the Spot the Difference game.
 * Initializes the application and displays the main menu.
 */
public class Main {
    public static void main(String[] args) {
        // Set modern FlatLaf Look and Feel
        try {
            UIManager.setLookAndFeel(new FlatIntelliJLaf());
            // Enable modern window decorations
            System.setProperty("flatlaf.useWindowDecorations", "true");
            // Enable macOS-style rounded corners on Windows
            System.setProperty("flatlaf.menuBarEmbedded", "false");
        } catch (Exception e) {
            System.err.println("Failed to initialize FlatLaf. Using default Look and Feel.");
        }
        
        // Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainMenuFrame mainMenu = new MainMenuFrame();
            mainMenu.setVisible(true);
        });
    }
}


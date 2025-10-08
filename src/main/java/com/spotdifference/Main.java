package com.spotdifference;

import com.spotdifference.ui.MainMenuFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Spot the Difference game.
 * Initializes the application and displays the main menu.
 */
public class Main {
    public static void main(String[] args) {
        // Ensure GUI is created on the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            MainMenuFrame mainMenu = new MainMenuFrame();
            mainMenu.setVisible(true);
        });
    }
}


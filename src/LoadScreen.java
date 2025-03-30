package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class LoadScreen extends JLayeredPane {
    private Font customFont;
    private static final String SAVE_DIR = "saves/";
    private static final String BUTTON_IMG = "resources/load_file_clicked.png";
    private static final int BUTTON_WIDTH = 580;
    private static final int BUTTON_HEIGHT = 100;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public LoadScreen(Font customFont, JPanel mainPanel, CardLayout cardLayout) {
        this.customFont = customFont;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        setPreferredSize(new Dimension(1080, 750));

        // ======== DO NOT CHANGE BACKGROUND =========
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        ImageIcon fileLoad = new ImageIcon("resources/save_load_screen.png");
        Image scaledLoad = fileLoad.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledLoadIcon = new ImageIcon(scaledLoad);
        JLabel loadLabel = new JLabel(scaledLoadIcon);
        loadLabel.setBounds(0, 0, 1080, 750);
        add(loadLabel, Integer.valueOf(1));

        JLabel loadText = new JLabel("LOAD");
        loadText.setForeground(Color.WHITE);
        loadText.setFont(customFont.deriveFont(24f));
        loadText.setBounds(800, 70, 192, 64);
        loadText.setVerticalAlignment(SwingConstants.CENTER);
        loadText.setHorizontalAlignment(SwingConstants.CENTER);
        add(loadText, Integer.valueOf(2));

        // ======== BACKGROUND UNCHANGED =========

        JButton deleteButton = MainScreen.buttonCreate(
                540, 70, 192, 64, // Adjust position as needed
                "resources/button.png",
                "resources/button_clicked.png",
                ""
        );

        deleteButton.addActionListener(e -> promptDeleteSave());
        add(deleteButton, Integer.valueOf(2)); // Ensure it appears on top

        JLabel deleteText = new JLabel("DELETE SAVE");
        deleteText.setFont(customFont);
        deleteText.setForeground(Color.WHITE);
        deleteText.setBounds(540,70,192,64);
        deleteText.setHorizontalAlignment(SwingConstants.CENTER);
        deleteText.setVerticalAlignment(SwingConstants.CENTER);
        add(deleteText, Integer.valueOf(3));

        // Back Button (Returns to MainScreen)
        JButton homeButton = MainScreen.buttonCreate(20, 20, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        JLabel homeText = new JLabel("< HOME");
        homeText.setFont(customFont);
        homeText.setForeground(Color.BLACK);
        homeText.setBounds(20,20,192,64);
        homeText.setHorizontalAlignment(SwingConstants.CENTER);
        homeText.setVerticalAlignment(SwingConstants.CENTER);
        add(homeText, Integer.valueOf(2));
        add(homeButton, Integer.valueOf(2));

        // Load the save files and create buttons
        File[] saveFiles = getSaveFiles();

        for (int i = 0; i < Math.min(3, saveFiles.length); i++) {
            final String filePath = saveFiles[i].getAbsolutePath(); // Make final for inner class

            GameData gameData = GameDataManager.loadGame(filePath);
            if (gameData != null) {
                String petName = gameData.getPet().getName();
                int petHealth = gameData.getPet().getHealth();
                int playerCoins = gameData.getInventory().getPlayerCoins();

                //Load and scale images
                ImageIcon defaultIcon = scaleImageIcon(BUTTON_IMG, 798 ,  138 );

                // Declare final variable to avoid scope issue
                final JButton saveButton = new JButton(defaultIcon);

                // Set button size and position
                saveButton.setBounds(141, 174 + (i * 170), 798, 138);
                saveButton.setBorderPainted(false);
                saveButton.setContentAreaFilled(false);
                saveButton.setFocusPainted(false);

                // Set text inside button
                saveButton.setText("<html><center>" +
                        "<b>" + petName + "</b><br>" +
                        "Health: " + petHealth + "<br>" +
                        "Coins: " + playerCoins +
                        "</center></html>");
                saveButton.setFont(customFont);
                saveButton.setHorizontalTextPosition(JButton.CENTER);
                saveButton.setVerticalTextPosition(JButton.CENTER);

                // Change button appearance when clicked
                saveButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // 🚫 Check parental playtime restrictions before loading
                        if (!MainScreen.getParentalControl().isPlayAllowedNow()) {
                            JOptionPane.showMessageDialog(
                                    LoadScreen.this,
                                    "⏰ Playtime is currently restricted.\nPlease try again during allowed hours.",
                                    "Playtime Restricted",
                                    JOptionPane.WARNING_MESSAGE
                            );
                            return;
                        }

                        // ✅ Proceed to load the game if allowed
                        GameData loadedGame = GameDataManager.loadGame(filePath);
                        if (loadedGame != null) {
                            switchToInGameScreen(loadedGame, filePath);
                        }
                    }
                });



                add(saveButton, Integer.valueOf(2));
            }
        }
    }

    // Helper function to scale ImageIcons
    private ImageIcon scaleImageIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    // Get save files from the directory
    private File[] getSaveFiles() {
        File dir = new File(SAVE_DIR);
        if (dir.exists() && dir.isDirectory()) {
            return dir.listFiles((dir1, name) -> name.endsWith(".json"));
        }
        return new File[0];
    }

    // Switch to InGameScreen using MainScreen's managed instance
//    private void switchToInGameScreen(GameData gameData) {
//        MainScreen.showInGameScreen(gameData);
//    }

    private void switchToInGameScreen(GameData gameData, String filePath) {
        InGameScreen inGameScreen = new InGameScreen(customFont, cardLayout, mainPanel, gameData, filePath);
        mainPanel.add(inGameScreen, "InGameScreen");
        cardLayout.show(mainPanel, "InGameScreen");
    }



    private void promptDeleteSave() {
        File[] saveFiles = getSaveFiles();

        if (saveFiles.length == 0) {
            JOptionPane.showMessageDialog(this, "No save files found.", "Delete Save", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Create an array of file names
        String[] saveNames = new String[saveFiles.length];
        for (int i = 0; i < saveFiles.length; i++) {
            saveNames[i] = saveFiles[i].getName();
        }

        // Ask user to select a file
        String selectedFile = (String) JOptionPane.showInputDialog(
                this,
                "Select a save file to delete:",
                "Delete Save",
                JOptionPane.QUESTION_MESSAGE,
                null,
                saveNames,
                saveNames[0]
        );

        // If user canceled, do nothing
        if (selectedFile == null) return;

        // Confirm before deleting
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + selectedFile + "?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            File fileToDelete = new File(SAVE_DIR + selectedFile);
            if (fileToDelete.delete()) {
                JOptionPane.showMessageDialog(this, "Save file deleted successfully.", "Delete Save", JOptionPane.INFORMATION_MESSAGE);
                refreshLoadScreen();
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete save file.", "Delete Save", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void refreshLoadScreen() {
        // Remove the current LoadScreen from mainPanel
        mainPanel.remove(this);

        // Create a new instance of LoadScreen
        LoadScreen newLoadScreen = new LoadScreen(customFont, mainPanel, cardLayout);

        // Add the new LoadScreen to the mainPanel
        mainPanel.add(newLoadScreen, "LoadScreen");

        // Switch to the updated LoadScreen
        cardLayout.show(mainPanel, "LoadScreen");

        // Repaint and revalidate to reflect changes
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}


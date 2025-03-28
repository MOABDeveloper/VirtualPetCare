package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;


public class LoadScreen extends JLayeredPane {
    private Font customFont;
    private static final String SAVE_DIR = "saves/";
    private static final String BUTTON_IMG = "resources/load_file.png";
    private static final String BUTTON_IMG_CLICKED = "resources/load_file_clicked.png";
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
        // ======== BACKGROUND UNCHANGED =========

        // Back Button (Returns to MainScreen)
        JButton homeButton = MainScreen.buttonCreate(
                20, 10, 192, 64,
                "resources/white_button.png",
                "resources/white_button_clicked.png",
                "Home"
        );
        add(homeButton, Integer.valueOf(2));

        // Load the save files and create buttons
        File[] saveFiles = getSaveFiles();

        for (int i = 0; i < Math.min(2, saveFiles.length); i++) {
            final String filePath = saveFiles[i].getAbsolutePath(); // Make final for inner class

            GameData gameData = GameDataManager.loadGame(filePath);
            if (gameData != null) {
                String petName = gameData.getPet().getName();
                int petHealth = gameData.getPet().getHealth();
                int playerCoins = gameData.getInventory().getPlayerCoins();

                //Load and scale images
                ImageIcon defaultIcon = scaleImageIcon(BUTTON_IMG, 798 ,  138 );
                ImageIcon clickedIcon = scaleImageIcon(BUTTON_IMG_CLICKED, 798 ,  138);


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
                    public void mousePressed(MouseEvent e) {
                        saveButton.setIcon(clickedIcon);
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        saveButton.setIcon(defaultIcon);
                    }

                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Load game data
                        GameData loadedGame = GameDataManager.loadGame(filePath);
                        if (loadedGame != null) {
                            // Switch to InGameScreen
                            switchToInGameScreen(loadedGame);
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

    // Switch to InGameScreen
    private void switchToInGameScreen(GameData gameData) {
        InGameScreen inGameScreen = new InGameScreen(customFont, cardLayout, mainPanel, gameData);
        mainPanel.add(inGameScreen, "InGameScreen");
        cardLayout.show(mainPanel, "InGameScreen");
    }
}







//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.io.File;
//import src.GameData;
//import src.GameDataManager;
////import InGameScreen; // Ensure this is imported
//
//public class LoadScreen extends JLayeredPane {
//    private Font customFont;
//    private static final String SAVE_DIR = "saves/";
//    private static final String BUTTON_IMG = "resources/load_file.png";
//    private static final String BUTTON_IMG_CLICKED = "resources/load_file_clicked.png";
//    private static final int BUTTON_WIDTH = 580;
//    private static final int BUTTON_HEIGHT = 100;
//
//    private JPanel mainPanel;
//    private CardLayout cardLayout;
//
//    public LoadScreen(Font customFont, JPanel mainPanel, CardLayout cardLayout) {
//        this.customFont = customFont;
//        this.mainPanel = mainPanel;
//        this.cardLayout = cardLayout;
//        setPreferredSize(new Dimension(1080, 750));
//
//        // ======== DO NOT CHANGE BACKGROUND =========
//        ImageIcon background = new ImageIcon("resources/grid.png");
//        JLabel backgroundLabel = new JLabel(background);
//        backgroundLabel.setBounds(0, 0, 1080, 750);
//        add(backgroundLabel, Integer.valueOf(0));
//
//        ImageIcon fileLoad = new ImageIcon("resources/save_load_screen.png");
//        Image scaledLoad = fileLoad.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
//        ImageIcon scaledLoadIcon = new ImageIcon(scaledLoad);
//        JLabel loadLabel = new JLabel(scaledLoadIcon);
//        loadLabel.setBounds(0, 0, 1080, 750);
//        add(loadLabel, Integer.valueOf(1));
//        // ======== BACKGROUND UNCHANGED =========
//
//        // Load the save files and create buttons
//        File[] saveFiles = getSaveFiles();
//
//        for (int i = 0; i < Math.min(2, saveFiles.length); i++) {
//            final String filePath = saveFiles[i].getAbsolutePath(); // Make final for inner class
//
//            GameData gameData = GameDataManager.loadGame(filePath);
//            if (gameData != null) {
//                String petName = gameData.getPet().getName();
//                int petHealth = gameData.getPet().getHealth();
//                int playerCoins = gameData.getInventory().getPlayerCoins();
//
//                //Load and scale images
//                ImageIcon defaultIcon = scaleImageIcon(BUTTON_IMG, 798 ,  138 );
//                ImageIcon clickedIcon = scaleImageIcon(BUTTON_IMG_CLICKED, 798 ,  138);
//
//
//                // Declare final variable to avoid scope issue
//                final JButton saveButton = new JButton(defaultIcon);
//
//                // Set button size and position
//                saveButton.setBounds(141, 174 + (i * 170), 798, 138);
//                saveButton.setBorderPainted(false);
//                saveButton.setContentAreaFilled(false);
//                saveButton.setFocusPainted(false);
//
//                // Set text inside button
//                saveButton.setText("<html><center>" +
//                        "<b>" + petName + "</b><br>" +
//                        "Health: " + petHealth + "<br>" +
//                        "Coins: " + playerCoins +
//                        "</center></html>");
//                saveButton.setFont(customFont);
//                saveButton.setHorizontalTextPosition(JButton.CENTER);
//                saveButton.setVerticalTextPosition(JButton.CENTER);
//
//                // Change button appearance when clicked
//                saveButton.addMouseListener(new MouseAdapter() {
//                    @Override
//                    public void mousePressed(MouseEvent e) {
//                        saveButton.setIcon(clickedIcon);
//                    }
//
//                    @Override
//                    public void mouseReleased(MouseEvent e) {
//                        saveButton.setIcon(defaultIcon);
//                    }
//
//                    @Override
//                    public void mouseClicked(MouseEvent e) {
//                        // Load game data
//                        GameData loadedGame = GameDataManager.loadGame(filePath);
//                        if (loadedGame != null) {
//                            // Switch to InGameScreen
//                            switchToInGameScreen(loadedGame);
//                        }
//                    }
//                });
//
//                add(saveButton, Integer.valueOf(2));
//            }
//        }
//    }
//
//    // Helper function to scale ImageIcons
//    private ImageIcon scaleImageIcon(String path, int width, int height) {
//        ImageIcon icon = new ImageIcon(path);
//        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        return new ImageIcon(img);
//    }
//
//    // Get save files from the directory
//    private File[] getSaveFiles() {
//        File dir = new File(SAVE_DIR);
//        if (dir.exists() && dir.isDirectory()) {
//            return dir.listFiles((dir1, name) -> name.endsWith(".json"));
//        }
//        return new File[0];
//    }
//
//    // Switch to InGameScreen
//    private void switchToInGameScreen(GameData gameData) {
//        InGameScreen inGameScreen = new InGameScren(customFont, cardLayout, mainPanel, gameData);
//        mainPanel.add(inGameScreen, "InGameScreen");
//        cardLayout.show(mainPanel, "InGameScreen");
//    }
//}

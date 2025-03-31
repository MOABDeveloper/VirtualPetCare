package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Objects;


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

        JButton deleteButton = MainScreen.buttonCreate(540, 70, 192, 64, "resources/button.png", "resources/button_clicked.png", "");

        deleteButton.addActionListener(e -> promptDeleteSave());
        add(deleteButton, Integer.valueOf(2));

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
            final String filePath = saveFiles[i].getAbsolutePath();

            GameData gameData = GameDataManager.loadGame(filePath);
            if (gameData != null) {
                String petName = gameData.getPet().getName();
                int petHealth = gameData.getPet().getHealth();
                int playerCoins = gameData.getInventory().getPlayerCoins();

                // Load and scale button image
                ImageIcon defaultIcon = scaleImageIcon(BUTTON_IMG, 798, 138);

                // Choose pet icon
                String petIconLocation = "";
                if (Objects.equals(gameData.getPet().getPetType(), "PetOption1")){
                    petIconLocation = "resources/PetOption1Icon.PNG";
                } else if (Objects.equals(gameData.getPet().getPetType(), "PetOption2")){
                    petIconLocation = "resources/PetOption2Icon.PNG";
                } else {
                    petIconLocation = "resources/PetOption3Icon.PNG";
                }

                // Add pet image icon
                ImageIcon petIcon = scaleImageIcon(petIconLocation, 220, 200);
                JLabel petIconLabel = new JLabel(petIcon);
                petIconLabel.setBounds(100, 135 + (i * 170), 200, 200);
                add(petIconLabel, Integer.valueOf(3));

                // Save button with background image only
                final JButton saveButton = new JButton(defaultIcon);
                saveButton.setBounds(141, 174 + (i * 170), 798, 138);
                saveButton.setBorderPainted(false);
                saveButton.setContentAreaFilled(false);
                saveButton.setFocusPainted(false);

                // Mouse click to load save
                saveButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!MainScreen.getParentalControl().isPlayAllowedNow()) {
                            JOptionPane.showMessageDialog(LoadScreen.this, "Playtime is currently restricted.\nPlease try again during allowed hours.", "Playtime Restricted", JOptionPane.WARNING_MESSAGE);
                            return;
                        }

                        GameData loadedGame = GameDataManager.loadGame(filePath);
                        if (loadedGame != null) {
                            switchToInGameScreen(loadedGame, filePath);
                        }
                    }
                });

                add(saveButton, Integer.valueOf(2));

                // Panel to display text info using real font
                JPanel labelPanel = new JPanel(new GridBagLayout()); // This centers content
                labelPanel.setOpaque(false);
                labelPanel.setBounds(saveButton.getBounds()); // Same size as button

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = GridBagConstraints.RELATIVE;
                gbc.anchor = GridBagConstraints.CENTER;
                gbc.insets = new Insets(2, 0, 2, 0); // optional padding between labels

                JLabel nameLabel = new JLabel(petName);
                nameLabel.setFont(customFont.deriveFont(Font.BOLD, 20f));
                nameLabel.setForeground(Color.decode("#7392B2"));

                JLabel healthLabel = new JLabel("Health: " + petHealth);
                healthLabel.setFont(customFont.deriveFont(16f));
                healthLabel.setForeground(Color.BLACK);

                JLabel coinsLabel = new JLabel("Coins: " + playerCoins);
                coinsLabel.setFont(customFont.deriveFont(16f));
                coinsLabel.setForeground(Color.BLACK);

                labelPanel.add(nameLabel, gbc);
                labelPanel.add(healthLabel, gbc);
                labelPanel.add(coinsLabel, gbc);

                add(labelPanel, Integer.valueOf(3));
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
        String selectedFile = (String) JOptionPane.showInputDialog(this, "Select a save file to delete:", "Delete Save", JOptionPane.QUESTION_MESSAGE, null, saveNames, saveNames[0]);

        // If user canceled, do nothing
        if (selectedFile == null) return;

        // Confirm before deleting
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete " + selectedFile + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);

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


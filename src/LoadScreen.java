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
                        MusicPlayer.playSoundEffect("resources/button_clicked.wav");
                        if (!MainScreen.getParentalControl().isPlayAllowedNow()) {
                            showStyledDialog("Playtime Restricted",
                                    "Playtime is currently restricted.\nPlease try again during allowed hours.");
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
            showStyledDialog("Delete Save", "No save files found.");
            return;
        }

        // Create an array of file names
        String[] saveNames = new String[saveFiles.length];
        for (int i = 0; i < saveFiles.length; i++) {
            saveNames[i] = saveFiles[i].getName();
        }

        // Create a custom panel for the input dialog
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 240));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<font size=4 color='#2E86C1'><b>Select a save file to delete:</b></font></div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        JComboBox<String> saveComboBox = new JComboBox<>(saveNames);
        saveComboBox.setFont(customFont.deriveFont(14f));
        panel.add(saveComboBox, BorderLayout.SOUTH);

        // Create the dialog
        int option = JOptionPane.showOptionDialog(
                this,
                panel,
                "Delete Save",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                new Object[]{"Select", "Cancel"},
                "Select"
        );

        if (option != JOptionPane.OK_OPTION) return;

        String selectedFile = (String) saveComboBox.getSelectedItem();
        if (selectedFile == null) return;

        // Create confirmation dialog
        JPanel confirmPanel = new JPanel(new BorderLayout(10, 10));
        confirmPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        confirmPanel.setBackground(new Color(240, 240, 240));

        JLabel confirmLabel = new JLabel("<html><div style='text-align: center;'>"
                + "<font size=4 color='#2E86C1'><b>Confirm Delete</b></font><br>"
                + "<font size=3 color='#5D6D7E'>Are you sure you want to delete " + selectedFile + "?</font></div></html>");
        confirmLabel.setHorizontalAlignment(SwingConstants.CENTER);
        confirmPanel.add(confirmLabel, BorderLayout.CENTER);

        JButton yesButton = new JButton("Yes");
        yesButton.setFont(customFont.deriveFont(Font.BOLD, 14f));
        yesButton.setBackground(new Color(52, 152, 219));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JButton noButton = new JButton("No");
        noButton.setFont(customFont.deriveFont(Font.BOLD, 14f));
        noButton.setBackground(new Color(231, 76, 60));
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        confirmPanel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog confirmDialog = new JDialog((Frame)null, "Confirm Delete", true);
        confirmDialog.setContentPane(confirmPanel);
        confirmDialog.setSize(350, 200);
        confirmDialog.setLocationRelativeTo(this);
        confirmDialog.setResizable(false);

        final boolean[] confirmed = {false};
        yesButton.addActionListener(e -> {
            confirmed[0] = true;
            confirmDialog.dispose();
        });
        noButton.addActionListener(e -> confirmDialog.dispose());

        confirmDialog.setVisible(true);

        if (confirmed[0]) {
            File fileToDelete = new File(SAVE_DIR + selectedFile);
            if (fileToDelete.delete()) {
                showStyledDialog("Delete Save", "Save file deleted successfully.");
                refreshLoadScreen();
            } else {
                showStyledDialog("Delete Save", "Failed to delete save file.");
            }
        }
    }

    private void showStyledDialog(String title, String message) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setBackground(new Color(240, 240, 240));

        JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>" + "<font size=4 color='#2E86C1'><b>" + title + "</b></font><br>" + "<font size=3 color='#5D6D7E'>" + message + "</font></div></html>");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(messageLabel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        okButton.setFont(customFont.deriveFont(Font.BOLD, 14f));
        okButton.setBackground(new Color(52, 152, 219));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        okButton.addActionListener(e -> {
            Window window = SwingUtilities.getWindowAncestor(panel);
            if (window != null) {
                window.dispose();
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(240, 240, 240));
        buttonPanel.add(okButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JDialog dialog = new JDialog((Frame)null, title, true);
        dialog.setContentPane(panel);
        dialog.setSize(350, 200);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
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

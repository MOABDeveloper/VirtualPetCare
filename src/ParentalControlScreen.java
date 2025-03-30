package src;

import javax.swing.*;
import java.awt.*;


public class ParentalControlScreen extends JLayeredPane {
    private Font customFont;
    private ParentalControl parentalControl;

    private JLabel totalPlayValue;
    private JLabel avgPlayValue;


    public ParentalControlScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel, ParentalControl parentalControl) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));
        this.parentalControl = parentalControl;

        // Add grid background
        ImageIcon gridBackground = new ImageIcon("resources/grid.png");
        Image scaledGrid = gridBackground.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        JLabel gridLabel = new JLabel(new ImageIcon(scaledGrid));
        gridLabel.setBounds(0, 0, 1080, 750);
        add(gridLabel, Integer.valueOf(0));

        // Add parental control window on top of grid
        ImageIcon windowBg = new ImageIcon("resources/parentalcontrol_background.png");
        Image scaledWindowBg = windowBg.getImage().getScaledInstance(1011, 600, Image.SCALE_SMOOTH); // Adjust size as needed
        JLabel windowLabel = new JLabel(new ImageIcon(scaledWindowBg));
        windowLabel.setBounds(0, 60, 1080, 750); // Centered-ish, tweak this if needed
        add(windowLabel, Integer.valueOf(1));


        // Back button
        JButton homeButton = MainScreen.buttonCreate(20, 20, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        add(homeButton, Integer.valueOf(1));
        JLabel homeText = new JLabel("< HOME");
        homeText.setFont(customFont);
        homeText.setForeground(Color.BLACK);
        homeText.setBounds(20,20,192,64);
        homeText.setHorizontalAlignment(SwingConstants.CENTER);
        homeText.setVerticalAlignment(SwingConstants.CENTER);
        add(homeText, Integer.valueOf(4));

        // Labels
        JLabel totalPlayLabel = createLabel("TOTAL PLAY TIME:", 240, 245, 300, 40);
        totalPlayValue = createLabel(formatMillis(parentalControl.getTotalPlayTime()), 250, 328, 400, 40);
        totalPlayValue.setFont(customFont.deriveFont(40f));

        JLabel avgPlayLabel = createLabel("AVERAGE PLAY TIME", 220, 419, 300, 40);
        avgPlayValue = createLabel(formatMillis(parentalControl.getAveragePlayTime()), 250, 498, 500, 40);
        avgPlayValue.setFont(customFont.deriveFont(40f));

        add(totalPlayLabel, Integer.valueOf(2));
        add(totalPlayValue, Integer.valueOf(2));
        add(avgPlayLabel, Integer.valueOf(2));
        add(avgPlayValue, Integer.valueOf(2));

        updateStatLabels();

        // Buttons
        JButton resetStatsButton = MainScreen.buttonCreate(620,190, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "");
        resetStatsButton.setText("RESET STATS");
        resetStatsButton.setFont(customFont.deriveFont(13f));
        resetStatsButton.setForeground(Color.decode("#7392B2"));
        resetStatsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        resetStatsButton.setVerticalTextPosition(SwingConstants.CENTER);

        JButton setPlayTimeButton = MainScreen.buttonCreate(620, 320, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "");
        setPlayTimeButton.setText("SET PLAY TIME");
        setPlayTimeButton.setFont(customFont.deriveFont(12f));
        setPlayTimeButton.setForeground(Color.decode("#7392B2"));
        setPlayTimeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        setPlayTimeButton.setVerticalTextPosition(SwingConstants.CENTER);

        JButton revivePetButton = MainScreen.buttonCreate(620, 440, 192, 62, "resources/white_button.png", "resources/white_button_clicked.png", "");
        revivePetButton.setText("REVIVE PET");
        revivePetButton.setFont(customFont.deriveFont(13f));
        revivePetButton.setForeground(Color.decode("#7392B2"));
        revivePetButton.setHorizontalTextPosition(SwingConstants.CENTER);
        revivePetButton.setVerticalTextPosition(SwingConstants.CENTER);


        add(resetStatsButton, Integer.valueOf(2));
        add(setPlayTimeButton, Integer.valueOf(2));
        add(revivePetButton, Integer.valueOf(2));

        // RESET STATS button Logic
        resetStatsButton.addActionListener(e -> {
            parentalControl.resetStats();
            GameDataManager.saveParentalControlSettings(parentalControl);

            updateStatLabels();

            JOptionPane.showMessageDialog(this, "Play time statistics have been reset.");
        });



        setPlayTimeButton.addActionListener(e -> {
            try {
                String startStr = JOptionPane.showInputDialog(this, "Enter allowed start hour (0–23):");
                String endStr = JOptionPane.showInputDialog(this, "Enter allowed end hour (0–23):");

                int startHour = Integer.parseInt(startStr);
                int endHour = Integer.parseInt(endStr);

                parentalControl.setPlayTimeWindow(startHour, endHour);
                parentalControl.setLimitationsEnabled(true);

                // ✅ Save it
                GameDataManager.saveParentalControlSettings(parentalControl);

                JOptionPane.showMessageDialog(this,
                        "Play time window set from " + startHour + ":00 to " + endHour + ":00.",
                        "Play Time Set",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid hours (0–23).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        JButton resetPlayTimeButton = MainScreen.buttonCreate(620, 560, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "");
        resetPlayTimeButton.setText("RESET PLAY TIME");
        resetPlayTimeButton.setFont(customFont.deriveFont(11f));
        resetPlayTimeButton.setForeground(Color.decode("#7392B2"));
        resetPlayTimeButton.setHorizontalTextPosition(SwingConstants.CENTER);
        resetPlayTimeButton.setVerticalTextPosition(SwingConstants.CENTER);

        // SET PLAY TIME button → shows dialog to get start/end hour and enables limit
        resetPlayTimeButton.addActionListener(e -> {
            parentalControl.resetPlayTimeRestrictions();

            // 🔥 Save updated settings
            GameDataManager.saveParentalControlSettings(parentalControl);

            JOptionPane.showMessageDialog(this,
                    "Play time restrictions have been reset.\nPlay is now allowed at any time.",
                    "Reset Successful",
                    JOptionPane.INFORMATION_MESSAGE);
        });


        add(resetPlayTimeButton, Integer.valueOf(2));


        revivePetButton.addActionListener(e -> {
            // Step 1: Let the user select a save file
            String saveFile = selectSaveFile();
            if (saveFile == null) {
                JOptionPane.showMessageDialog(null, "No save file selected.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Step 2: Load the pet from the save file
            GameData gameData = GameDataManager.loadGame(saveFile);
            if (gameData == null || gameData.getPet() == null) {
                JOptionPane.showMessageDialog(null, "Failed to load pet from save file.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Pet pet = gameData.getPet();

            System.out.println("Pet loaded from save file: " + pet.getName());
            System.out.println("Pet health: " + pet.getHealth());
            System.out.println("Pet isDead: " + pet.isDead());  


            // Step 3: Try reviving the pet using parental controls
            boolean revived = parentalControl.revivePet(pet);

            // Step 4: Show the result to the user
            if (revived) {
                JOptionPane.showMessageDialog(null, "Pet successfully revived!", "Success", JOptionPane.INFORMATION_MESSAGE);
                GameDataManager.saveGame(saveFile, pet, gameData.getInventory(), gameData.getTotalPlayTime()); // Save updated pet state
            } else {
                JOptionPane.showMessageDialog(null, "The pet is not dead and doesn't need revival.", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(customFont);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        return label;
    }

    private String selectSaveFile() {
        JFileChooser fileChooser = new JFileChooser("saves/");
        fileChooser.setDialogTitle("Select Save File");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null; // No file selected
    }

    private String formatMillis(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;
        return minutes + "m " + remainingSeconds + "s";
    }

    public void updateStatLabels() {
        totalPlayValue.setText(formatMillis(parentalControl.getTotalPlayTime()));
        avgPlayValue.setText(formatMillis(parentalControl.getAveragePlayTime()));
    }





}

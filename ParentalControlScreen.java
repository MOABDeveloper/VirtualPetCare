import src.ParentalControl;

import javax.swing.*;
import java.awt.*;
import src.GameData;
import src.GameDataManager;
import src.Pet;


public class ParentalControlScreen extends JLayeredPane {
    private Font customFont;

    private ParentalControl parentalControl;


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
        Image scaledWindowBg = windowBg.getImage().getScaledInstance(1011, 720, Image.SCALE_SMOOTH); // Adjust size as needed
        JLabel windowLabel = new JLabel(new ImageIcon(scaledWindowBg));
        windowLabel.setBounds(0, 0, 1080, 750); // Centered-ish, tweak this if needed
        add(windowLabel, Integer.valueOf(1));


        // Back button
        JButton backButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        add(backButton, Integer.valueOf(1));

        // Labels
        JLabel totalPlayLabel = createLabel("TOTAL PLAY TIME:", 240, 245, 300, 40);
        JLabel totalPlayValue = createLabel(String.valueOf(parentalControl.getTotalPlayTime()), 300, 328, 150, 40);
        totalPlayValue.setFont(customFont.deriveFont(40f));

        JLabel avgPlayLabel = createLabel("AVERAGE PLAY TIME", 220, 419, 300, 40);
        JLabel avgPlayValue = createLabel(String.valueOf(parentalControl.getAveragePlayTime()), 300, 498, 500, 40);
        avgPlayValue.setFont(customFont.deriveFont(40f));

        add(totalPlayLabel, Integer.valueOf(2));
        add(totalPlayValue, Integer.valueOf(2));
        add(avgPlayLabel, Integer.valueOf(2));
        add(avgPlayValue, Integer.valueOf(2));

        // Buttons
        JButton resetStatsButton = createButton("RESET STATS", 600, 200, 200, 50);
        JButton setPlayTimeButton = createButton("SET PLAY TIME", 600, 270, 200, 50);
        JButton revivePetButton = createButton("REVIVE PET", 600, 340, 200, 50);

        add(resetStatsButton, Integer.valueOf(2));
        add(setPlayTimeButton, Integer.valueOf(2));
        add(revivePetButton, Integer.valueOf(2));

        // RESET STATS button Logic
        resetStatsButton.addActionListener(e -> {
            parentalControl.resetStats();
            totalPlayValue.setText(String.valueOf(parentalControl.getTotalPlayTime()));
            avgPlayValue.setText(String.valueOf(parentalControl.getAveragePlayTime()));
            JOptionPane.showMessageDialog(this, "Play time statistics have been reset.");
        });

        // SET PLAY TIME button → shows dialog to get start/end hour and enables limit
        setPlayTimeButton.addActionListener(e -> {
            try {
                String startStr = JOptionPane.showInputDialog(this, "Enter allowed start hour (0–23):");
                String endStr = JOptionPane.showInputDialog(this, "Enter allowed end hour (0–23):");

                int startHour = Integer.parseInt(startStr);
                int endHour = Integer.parseInt(endStr);

                parentalControl.setPlayTimeWindow(startHour, endHour);
                parentalControl.setLimitationsEnabled(true);

                JOptionPane.showMessageDialog(this, "Play time window set from " + startHour + ":00 to " + endHour + ":00.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid hours (0–23).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
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
            System.out.println("Pet isDead: " + pet.isDead());  // <-- Check if it's actually true


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

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(customFont);
        button.setBounds(x, y, width, height);
        return button;
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

}

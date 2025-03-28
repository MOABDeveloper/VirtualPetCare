package src;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

public class InGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;  // Remove static
    private JPanel mainPanel;

    private JProgressBar HealthProgressBar;
    private JProgressBar SleepProgressBar;
    private JProgressBar HappinessProgressBar;
    private JProgressBar FullnessProgressBar;

    private Pet pet;
    private static String health_red = "#A94337";

    private Timer statDecayTimer;

    private GameData gameData;




    public InGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel, GameData gameData) {

        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));
        setBackground();
        this.pet = gameData.getPet();
        this.gameData = gameData;


        // Create and position progress bar for health
        HealthProgressBar = new JProgressBar(JProgressBar.VERTICAL, 0,pet.getMaxHealth());
        HealthProgressBar.setBounds(26, 81, 25, 135);
        HealthProgressBar.setValue(pet.getHealth()); // Initial value
        HealthProgressBar.setStringPainted(false); // Show percentage text

        // Paint the progress bar colors
        updateProgressBarColor(HealthProgressBar,pet.getHealth());

        //Set Background Color
        HealthProgressBar.setBackground(Color.decode("#f9e6c6"));
        HealthProgressBar.repaint();

        HealthProgressBar.revalidate();

        //Place bar below the graphics
        add(HealthProgressBar, Integer.valueOf(1));
        //Finished Health Progress bar

        // Create and position progress bar for Sleep
        SleepProgressBar = new JProgressBar(JProgressBar.VERTICAL, 0,pet.getMaxSleep());
        SleepProgressBar.setBounds(101, 81, 25, 135);
        SleepProgressBar.setValue(pet.getSleep()); // Initial value
        SleepProgressBar.setStringPainted(false); // Show percentage text

        // Paint the progress bar colors
        updateProgressBarColor(SleepProgressBar,pet.getSleep());

        //Set Background Color
        SleepProgressBar.setBackground(Color.decode("#f9e6c6"));
        SleepProgressBar.repaint();

        SleepProgressBar.revalidate();

        //Place bar below the graphics
        add(SleepProgressBar, Integer.valueOf(1));
        //Finished sleep progress bar

        // Create and position progress bar for health
        FullnessProgressBar = new JProgressBar(JProgressBar.VERTICAL, 0,pet.getMaxFullness());
        FullnessProgressBar.setBounds(26, 299, 25, 135);
        FullnessProgressBar.setValue(pet.getFullness()); // Initial value
        FullnessProgressBar.setStringPainted(false); // Show percentage text

        // Paint the progress bar colors
        updateProgressBarColor(FullnessProgressBar,pet.getFullness());

        //Set Background Color
        FullnessProgressBar.setBackground(Color.decode("#f9e6c6"));
        FullnessProgressBar.repaint();

        FullnessProgressBar.revalidate();

        //Place bar below the graphics
        add(FullnessProgressBar, Integer.valueOf(1));
        //Finished Health Progress bar


        // Create and position progress bar for health
        HappinessProgressBar = new JProgressBar(JProgressBar.VERTICAL, 0,pet.getMaxHappiness());
        HappinessProgressBar.setBounds(101, 299, 25, 135);
        HappinessProgressBar.setValue(pet.getHappiness()); // Initial value
        HappinessProgressBar.setStringPainted(false); // Show percentage text

        // Paint the progress bar colors
        updateProgressBarColor(HappinessProgressBar,pet.getHappiness());

        //Set Background Color
        HappinessProgressBar.setBackground(Color.decode("#f9e6c6"));
        HappinessProgressBar.repaint();

        HappinessProgressBar.revalidate();

        //Place bar below the graphics
        add(HappinessProgressBar, Integer.valueOf(1));
        //Finished Health Progress bar


        healthBars();
        commandButtons();
        spriteGifs();

        JButton backButton = MainScreen.buttonCreate(800,70, 70,70, "resources/home_button.png", "resources/home_button_clicked.png", "");
        backButton.setBounds(990, 15, 64, 64);

        ImageIcon homeIcon = new ImageIcon("resources/home_icon.png");
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setBounds(990, 15, 64, 64);
        add(homeIconLabel,Integer.valueOf(3));

//        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
//        add(backButton, Integer.valueOf(2));

        backButton.addActionListener(e -> {
            stopDecayTimer();
            cardLayout.show(mainPanel, "Home");
        });

        add(backButton, Integer.valueOf(2));

        // Decay every 5 seconds (5000 ms)
        statDecayTimer = new Timer(500, e -> {
            pet.applyDecline();

            // Update progress bar or any UI components
            HealthProgressBar.setMaximum(pet.getMaxHealth());
            HealthProgressBar.setValue(pet.getHealth());
            updateProgressBarColor(HealthProgressBar, pet.getHealth());

            // Update progress bar or any UI components
            SleepProgressBar.setMaximum(pet.getMaxSleep());
            SleepProgressBar.setValue(pet.getSleep());
            updateProgressBarColor(SleepProgressBar, pet.getSleep());

            // Update progress bar or any UI components
            FullnessProgressBar.setMaximum(pet.getMaxFullness());
            FullnessProgressBar.setValue(pet.getFullness());
            updateProgressBarColor(FullnessProgressBar, pet.getFullness());

            // Update progress bar or any UI components
            HappinessProgressBar.setMaximum(pet.getMaxHappiness());
            HappinessProgressBar.setValue(pet.getHappiness());
            updateProgressBarColor(HappinessProgressBar, pet.getHappiness());


            // Optional: print stats to console for debugging
            pet.printStats();

            // TODO: Update other UI elements like fullness, happiness, etc.
            // TODO: Show warnings (e.g. red flash if stat < 25%)
            // TODO: Save to file periodically if desired
        });

// Start the timer
        statDecayTimer.start();


    }

    public static void updateProgressBarColor(JProgressBar progressBar, int health) {
        // Change color based on health percentage
        if (health <= 10) {
            progressBar.setForeground(Color.decode("#A94337"));
        } else if (health <= 20) {
            progressBar.setForeground(Color.decode("#B54F32"));
        } else if (health <= 30) {
            progressBar.setForeground(Color.decode("#C05C2E"));
        } else if (health <= 40) {
            progressBar.setForeground(Color.decode("#CB6829"));
        } else if (health <= 50) {
            progressBar.setForeground(Color.decode("#D67524"));
        } else if (health <= 60) {
            progressBar.setForeground(Color.decode("#E1821F"));
        } else if (health <= 70) {
            progressBar.setForeground(Color.decode("#EC8E1A"));
        } else if (health <= 80) {
            progressBar.setForeground(Color.decode("#F79B15"));
        } else if (health <= 90) {
            progressBar.setForeground(Color.decode("#83B52B"));
        } else {
            progressBar.setForeground(Color.decode("#37A943"));
        }

        progressBar.repaint();
    }

    private void setBackground() {
        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // actual file thing
        ImageIcon inGameWindow = new ImageIcon("resources/ingamescreen.png");
        Image scaledWindow = inGameWindow.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledWindowIcon = new ImageIcon(scaledWindow);

        // create label
        JLabel windowLabel = new JLabel(scaledWindowIcon);
        windowLabel.setBounds(-13, -10, 1080, 750);

        // add above grid
        add(windowLabel, Integer.valueOf(1));

        // save icon
        JButton saveButton = MainScreen.buttonCreate(17, 15, 50, 50, "resources/save.png", "resources/save_clicked.png", "Save");
        add(saveButton, Integer.valueOf(2));

        // attach save action
        saveButton.addActionListener(e -> {
            // Save the game
            GameDataManager.saveGame(
                    "saves/" + pet.getName() + ".json",
                    gameData.getPet(),
                    gameData.getInventory(),
                    gameData.getTotalPlayTime()
            );

            // Create custom components
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
            panel.setBackground(new Color(240, 240, 240)); // Light gray background

            // Custom message with HTML formatting
            JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>"
                    + "<font size=4 color='#2E86C1'><b>Game Saved!</b></font><br>"
                    + "<font size=3 color='#5D6D7E'>Your progress is safe</font></div></html>");
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            panel.add(messageLabel, BorderLayout.CENTER);

            // Customize the button
            UIManager.put("OptionPane.okButtonText", "Got it!");
            UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.BOLD, 14));
            UIManager.put("Button.background", new Color(52, 152, 219));
            UIManager.put("Button.foreground", Color.WHITE);

            // Create the option pane
            JOptionPane optionPane = new JOptionPane(
                    panel,
                    JOptionPane.PLAIN_MESSAGE,
                    JOptionPane.DEFAULT_OPTION,
                    null, // No extra icon since we have our own
                    new Object[]{"Got it!"},
                    "Got it!"
            );

            // Create and position the dialog
            JDialog dialog = optionPane.createDialog("Save Successful");
            dialog.setSize(350, 200); // Adjusted size
            dialog.setLocation(
                    (1080 - dialog.getWidth()) / 2, // Center horizontally
                    50 // Fixed position from top
            );
            dialog.setVisible(true);
        });
//        // attach save action
//        saveButton.addActionListener(e -> {
//            GameDataManager.saveGame(
//                    "saves/" + pet.getName() + ".json",
//                    gameData.getPet(),
//                    gameData.getInventory(),
//                    gameData.getTotalPlayTime()
//            );
//            JOptionPane.showMessageDialog(this, "✅ Game saved!", "Save", JOptionPane.INFORMATION_MESSAGE);
//        });

        // stop music button
        JButton musicToggle = MainScreen.buttonCreate(90, 15, 50, 50, "resources/save.png", "resources/save_clicked.png", "");
        ImageIcon musicIcon = new ImageIcon("resources/Speaker-Crossed.png");
        JLabel musicLabel = new JLabel(musicIcon);

// Position the icon centered on the button (adjust these values as needed)
        int iconX = 90 + (50 - 26)/2;  // button x + (button width - icon width)/2
        int iconY = 15 + (50 - 28)/2;  // button y + (button height - icon height)/2
        musicLabel.setBounds(iconX, iconY, 26, 28);

        add(musicLabel, Integer.valueOf(3));
        add(musicToggle, Integer.valueOf(2));


        musicToggle.addActionListener(e -> {
            MusicPlayer.toggleBackgroundMusic();
        });

    }

    private void healthBars(){
        ImageIcon healthBar = new ImageIcon("resources/health_bar.png");
        JLabel healthBarLabel = new JLabel(healthBar);
        healthBarLabel.setBounds(-145, -30, 350, 350);
        add(healthBarLabel, Integer.valueOf(2));

        ImageIcon sleepBar = new ImageIcon("resources/sleep_bar.png");
        JLabel sleepBarLabel = new JLabel(sleepBar);
        sleepBarLabel.setBounds(-70, -30,350, 350);
        add(sleepBarLabel, Integer.valueOf(2));

        ImageIcon hungerBar = new ImageIcon("resources/hunger_bar.png");
        JLabel hungerBarLabel = new JLabel(hungerBar);
        hungerBarLabel.setBounds(-145, 190, 350, 350);
        add(hungerBarLabel, Integer.valueOf(2));

        ImageIcon happiness_bar = new ImageIcon("resources/happiness_bar.png");
        JLabel happinessBarLabel = new JLabel(happiness_bar);
        happinessBarLabel.setBounds(-70, 190, 350, 350);
        add(happinessBarLabel, Integer.valueOf(2));
    }

    private void commandButtons() {
        JButton shopButton = MainScreen.buttonCreate(30, 550, 128, 128, "resources/command_button.png", "resources/command_button_clicked.png", "Shop");
        shopButton.removeActionListener(shopButton.getActionListeners()[0]);
        shopButton.addActionListener(e -> {
            for (Component comp : mainPanel.getComponents()) {
                if (comp instanceof StoreScreen) {
                    ((StoreScreen)comp).resetToFirstPage(); // Reset to first page
                    break;
                }
            }
            cardLayout.show(mainPanel, "Shop"); // Then show the shop
        });

        add(shopButton, Integer.valueOf(2));

        JButton feedButton = MainScreen.buttonCreate(210,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", ""); // this needs to have a function to open the food inventory
        add(feedButton, Integer.valueOf(2));

        JButton playButton = MainScreen.buttonCreate(390,550, 128,128, "resources/command_button.png", "resources/command_button_clicked.png", ""); // this needs to have a function to open the toy inventory
        add(playButton, Integer.valueOf(2));

        JButton giveGift = MainScreen.buttonCreate(560, 550, 128,128, "resources/command_button.png", "resources/command_button_clicked.png", ""); // function, give a gift (the outfits)
        add(giveGift, Integer.valueOf(2));

        JButton exerciseButton = MainScreen.buttonCreate(730,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", ""); // this should change the sprite into the exercise gif
        add(exerciseButton, Integer.valueOf(2));

        JButton vetButton = MainScreen.buttonCreate(900,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        vetButton.addActionListener(e -> {
            playSound("resources/heal_sound.wav"); // this takes like 5 seconds before the sound starts. i think its a good idea so we can move the sprite off screen by then and make it seem like it went to the vet
        });
        add(vetButton, Integer.valueOf(2));
    }

    private void spriteGifs() {
        ImageIcon gifIcon = new ImageIcon("resources/sprite1_idle.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(300, 30, 622, 632);
        add(gifLabel, Integer.valueOf(3));

        ImageIcon swissRoll = new ImageIcon("resources/swissroll.gif");
        JLabel swissRollLabel = new JLabel(swissRoll);
        swissRollLabel.setBounds(280, 100, 142, 102);
        add(swissRollLabel, Integer.valueOf(3));
    }

    public void stopDecayTimer() {
        if (statDecayTimer != null) {
            statDecayTimer.stop();
        }
    }

    // testing to see if sound works
    private void playSound(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFilePath).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

}
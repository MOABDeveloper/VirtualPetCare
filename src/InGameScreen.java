package src;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

public class InGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JProgressBar HealthProgressBar;
    private JProgressBar SleepProgressBar;
    private JProgressBar HappinessProgressBar;
    private JProgressBar FullnessProgressBar;
    private Pet pet;
    private Timer statDecayTimer;
    private GameData gameData;
    private String saveFilePath;
    private JLabel gifLabel;
    private long sessionStartTime;
    private JButton feedButton;
    private JButton playButton;
    private JButton sleepButton;
    private JButton giveGiftButton;
    private JButton exerciseButton;
    private JButton vetButton;
    private JButton shopButton;
    private String base;
    private String state;
    private String currentSpritePath = "";
    private JLabel coinLabel;



    public InGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel, GameData gameData, String saveFilePath) {
        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        this.pet = gameData.getPet();
        this.gameData = gameData;
        this.saveFilePath = saveFilePath;
        this.sessionStartTime = System.currentTimeMillis();

        mainPanel.add(this, "InGame");
        setPreferredSize(new Dimension(1080, 750));

        initializePetSprite();
        setBackground();
        createProgressBars();
        healthBars();
        commandButtons();
        createBackButton();
        startStatDecayTimer();
        displayCoins();
        new KeyboardShortcuts(this, mainPanel, cardLayout, customFont, gameData).setupKeyBindings();
    }

    private void createProgressBars() {
        HealthProgressBar = createBar(26, 81, pet.getMaxHealth(), pet.getHealth());
        SleepProgressBar = createBar(101, 81, pet.getMaxSleep(), pet.getSleep());
        FullnessProgressBar = createBar(26, 299, pet.getMaxFullness(), pet.getFullness());
        HappinessProgressBar = createBar(101, 299, pet.getMaxHappiness(), pet.getHappiness());
    }

    private JProgressBar createBar(int x, int y, int max, int value) {
        JProgressBar bar = new JProgressBar(JProgressBar.VERTICAL, 0, max);
        bar.setBounds(x, y, 25, 135);
        bar.setValue(value);
        bar.setStringPainted(false);
        updateProgressBarColor(bar, value);
        bar.setBackground(Color.decode("#f9e6c6"));
        add(bar, Integer.valueOf(1));
        return bar;
    }

    private void initializePetSprite() {
        String type = pet.getPetType();
        String outfitPrefix = "";
        String base = "";

        if (pet.isWearingOutfit()) {
            outfitPrefix = "Outfit_";
        }

        if (type.equals("PetOption1")) {
            base = "PetOne";
        } else if (type.equals("PetOption2")) {
            base = "PetTwo";
        } else if (type.equals("PetOption3")) {
            base = "PetThree";
        }

        spriteGifs("resources/" + base + outfitPrefix + "Idle.gif");
    }



    private void createBackButton() {
        JButton backButton = MainScreen.buttonCreate(800, 70, 70, 70, "resources/home_button.png", "resources/home_button_clicked.png", "");
        backButton.setBounds(990, 15, 64, 64);

        ImageIcon homeIcon = new ImageIcon("resources/home_icon.png");
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setBounds(990, 15, 64, 64);
        add(homeIconLabel, Integer.valueOf(3));
        add(backButton, Integer.valueOf(2));

        backButton.addActionListener(e -> {
            stopDecayTimer();
            long sessionDuration = System.currentTimeMillis() - sessionStartTime;
            MainScreen.getParentalControl().updateAfterSession(sessionDuration);
            GameDataManager.saveParentalControlSettings(MainScreen.getParentalControl());
            cardLayout.show(mainPanel, "Home");
        });
    }

    private void startStatDecayTimer() {
        statDecayTimer = new Timer(250, e -> {
            pet.applyDecline();
            updateBar(HealthProgressBar, pet.getMaxHealth(), pet.getHealth());
            updateBar(SleepProgressBar, pet.getMaxSleep(), pet.getSleep());
            updateBar(FullnessProgressBar, pet.getMaxFullness(), pet.getFullness());
            updateBar(HappinessProgressBar, pet.getMaxHappiness(), pet.getHappiness());
            pet.printStats();
            updatePetState();
        });
        SwingUtilities.invokeLater(statDecayTimer::start);
    }

    private void updateBar(JProgressBar bar, int max, int value) {
        bar.setMaximum(max);
        bar.setValue(value);
        updateProgressBarColor(bar, value);
    }

    private void updatePetState() {
        String type = pet.getPetType();

        if (type.equals("PetOption1")) {
            base = "PetOne";
        } else if (type.equals("PetOption2")) {
            base = "PetTwo";
        } else if (type.equals("PetOption3")) {
            base = "PetThree";
        } else {
            base = "Pet";
        }

        if (pet.isDead()) {
            state = "Dead";
            setButtonsEnabled(false);
        } else if (pet.isSleeping()) {
            state = "Sleep";
            setButtonsEnabled(false);
        } else if (pet.isAngry()) {
            state = "Angry";
            feedButton.setEnabled(false);
            //sleepButton.setEnabled(false);
            vetButton.setEnabled(false);
        } else if (pet.isHungry()) {
            state = "Hungry";
            setButtonsEnabled(true);
        } else {
            state = "Idle";
            setButtonsEnabled(true);
        }

        updateSprite(pet);
    }


    private void setButtonsEnabled(boolean enabled) {
        feedButton.setEnabled(enabled);
        playButton.setEnabled(enabled);
        sleepButton.setEnabled(enabled);
        giveGiftButton.setEnabled(enabled);
        vetButton.setEnabled(enabled);
        exerciseButton.setEnabled(enabled);
        shopButton.setEnabled(enabled);
    }


    private void updateGif(String gifPath, int duration) {
        System.out.println("Updating GIF to: " + gifPath);

        // Ensure the old GIF is removed properly
        if (gifLabel != null) {
            remove(gifLabel);
            gifLabel = null; // Reset to avoid referencing a removed label
        }

        // Load new GIF and update the global gifLabel
        ImageIcon newGif = new ImageIcon(gifPath);
        gifLabel = new JLabel(newGif);
        gifLabel.setBounds(300, 30, 622, 632);
        add(gifLabel, Integer.valueOf(3));

        revalidate();
        repaint();

        // Reset back to idle animation after a delay
        Timer revertTimer = new Timer(duration, e -> {
            if (gifLabel != null) {
                remove(gifLabel);
                gifLabel = null;
            }

            // Restore the correct idle animation based on pet type
            String petType = pet.getPetType();
            if (petType.equals("PetOption1")) {
                if (pet.isWearingOutfit()) {
                    spriteGifs("resources/PetOneOutfit_Idle.gif");
                } else {
                    spriteGifs("resources/PetOne_Idle.gif");
                }
            }
            else if (petType.equals("PetOption2")) {
                if(pet.isWearingOutfit())
                {
                    spriteGifs("resources/PetTwoOutfit_Idle.gif");
                }
                else
                {
                    spriteGifs("resources/PetTwo_Idle.gif");
                }
            }
            else if (petType.equals("PetOption3")) {
                if(pet.isWearingOutfit())
                {
                    spriteGifs("resources/PetThreeOutfit_Idle.gif");
                }
                else
                {
                    spriteGifs("resources/PetThree_Idle.gif");
                }
            }
            revalidate();
            repaint();
        });

        revertTimer.setRepeats(false);
        revertTimer.start();
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
            panel.setBackground(new Color(240, 240, 240));

            // Custom message
            JLabel messageLabel = new JLabel("<html><div style='text-align: center;'>"
                    + "<font size=4 color='#2E86C1'><b>Game Saved!</b></font><br>"
                    + "<font size=3 color='#5D6D7E'>Your progress is safe</font></div></html>");
            messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            panel.add(messageLabel, BorderLayout.CENTER);

            // Create a custom button without focus painting
            JButton okButton = new JButton("Got it!");
            okButton.setFont(new Font("Arial", Font.BOLD, 14));
            okButton.setBackground(new Color(52, 152, 219));
            okButton.setForeground(Color.WHITE);
            okButton.setFocusPainted(false);  // This removes the focus border
            okButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

            okButton.addActionListener(ev -> {
                Window window = SwingUtilities.getWindowAncestor(panel);
                if (window != null) {
                    window.dispose();
                }
            });

            // Create button panel
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setBackground(new Color(240, 240, 240));
            buttonPanel.add(okButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);

            // Create the dialog
            JDialog dialog = new JDialog((Frame)null, "Save Successful", true);
            dialog.setContentPane(panel);
            dialog.setSize(350, 200);
            dialog.setLocationRelativeTo(this);
            dialog.setResizable(false);
            dialog.setVisible(true);
        });

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
        shopButton = MainScreen.buttonCreate(30, 550, 128, 128, "resources/command_button.png", "resources/command_button_clicked.png", "Shop");
        // Ensure existing StoreScreen is refreshed before switching
        shopButton.addActionListener(e -> {
            Component[] components = mainPanel.getComponents();
            for (int i = 0; i < components.length; i++) {
                if (components[i] instanceof StoreScreen) {
                    ((StoreScreen) components[i]).resetToFirstPage();  // Ensure store is refreshed
                    cardLayout.show(mainPanel, "Shop");  // ✅ Now switch to the Store
                    return;
                }
            }

        // If StoreScreen is missing, create and add it
            Store store = new Store();
            StoreScreen storeScreen = new StoreScreen(customFont, cardLayout, mainPanel, store, gameData);
            mainPanel.add(storeScreen, "Shop");
            cardLayout.show(mainPanel, "Shop");
        });


        ImageIcon shopIcon = new ImageIcon("resources/store_icon.png");
        JLabel shopIconLabel = new JLabel(shopIcon);
        shopIconLabel.setBounds(30 + (128 - 44)/2, 545 + (128 - 38)/2, 44, 38);
        add(shopIconLabel, Integer.valueOf(3));
        add(shopButton, Integer.valueOf(2));
        JLabel shopTextLabel = new JLabel("SHOP");
        shopTextLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        shopTextLabel.setForeground(Color.BLACK);
        shopTextLabel.setBounds(30, 530 + 128 + 5, 128, 20);
        shopTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(shopTextLabel, Integer.valueOf(3));


        // Feed Button with Inventory Popup
        feedButton = MainScreen.buttonCreate(210,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        feedButton.addActionListener(e -> showInventoryPopup(feedButton, "Feed"));
        ImageIcon feedIcon = new ImageIcon("resources/feed_icon.png");
        JLabel feedIconLabel = new JLabel(feedIcon);
        feedIconLabel.setBounds(210 + (128 - 39)/2, 545 + (128 - 38)/2, 39, 38);
        add(feedIconLabel, Integer.valueOf(3));
        add(feedButton, Integer.valueOf(2));
        JLabel feedTextLabel = new JLabel("FEED");
        feedTextLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        feedTextLabel.setForeground(Color.BLACK);
        feedTextLabel.setBounds(210, 530 + 128 + 5, 128, 20);
        feedTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(feedTextLabel, Integer.valueOf(3));

        // Play Button with Inventory Popup
        playButton = MainScreen.buttonCreate(390,550, 128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        playButton.addActionListener(e -> showInventoryPopup(playButton, "Play"));
        ImageIcon playIcon = new ImageIcon("resources/play_icon.png");
        JLabel playIconLabel = new JLabel(playIcon);
        playIconLabel.setBounds(390 + (128 - 47)/2, 545 + (128 - 58)/2, 47, 58);
        add(playIconLabel, Integer.valueOf(3));
        add(playButton, Integer.valueOf(2));
        JLabel playTextLabel = new JLabel("PLAY");
        playTextLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        playTextLabel.setForeground(Color.BLACK);
        playTextLabel.setBounds(390, 530 + 128 + 5, 128, 20);
        playTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(playTextLabel, Integer.valueOf(3));

        // Gift Button without Inventory Popup
        giveGiftButton = MainScreen.buttonCreate(560, 550, 128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        ImageIcon giftIcon = new ImageIcon("resources/gift_icon.png");
        JLabel giftIconLabel = new JLabel(giftIcon);
        giftIconLabel.setBounds(560 + (128 - 50)/2, 545 + (128 - 47)/2, 50, 47);
        add(giftIconLabel, Integer.valueOf(3));
        add(giveGiftButton, Integer.valueOf(2));
        giveGiftButton.addActionListener(e -> {
            PlayerInventory inventory = gameData.getInventory();
            Pet pet = gameData.getPet();

            // 1. Toggle outfit
            toggleOutfit(pet, inventory);

            // 2. Update sprite
            updateSprite(pet);

            // 3. If wearing outfit, increase happiness and update coins
            if (pet.isWearingOutfit()) {
                pet.setHappiness(pet.getMaxHappiness());
                // Add coin reward for wearing outfit
                inventory.setPlayerCoins(inventory.getPlayerCoins() + 100);
                refreshCoinDisplay();
            }
        });
        JLabel giftTextLabel = new JLabel("GIFT");
        giftTextLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        giftTextLabel.setForeground(Color.BLACK);
        giftTextLabel.setBounds(560, 530 + 128 + 5, 128, 20);
        giftTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(giftTextLabel, Integer.valueOf(3));


        exerciseButton = MainScreen.buttonCreate(730,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        add(exerciseButton, Integer.valueOf(2));
        exerciseButton.addActionListener(e -> {
            PlayerInventory inventory = gameData.getInventory(); // Get PlayerInventory
            inventory.exercisePet(pet);
            playSound("resources/exercise_sound.wav");
            HealthProgressBar.setValue(pet.getHealth());
            FullnessProgressBar.setValue(pet.getFullness());
            SleepProgressBar.setValue(pet.getSleep());
            refreshCoinDisplay(); // Add this to update coin display
            updateGif(getGifPath("Exercising"),1500);
        });


        ImageIcon exerciseIcon = new ImageIcon("resources/exercise_icon.png");
        JLabel exerciseIconLabel = new JLabel(exerciseIcon);
        exerciseIconLabel.setBounds(730 + (128 - 50)/2, 545 + (128 - 47)/2, 50, 47);
        add(exerciseIconLabel, Integer.valueOf(3));
        add(exerciseButton, Integer.valueOf(2));
        JLabel exerciseTextLabel = new JLabel("EXERCISE");
        exerciseTextLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        exerciseTextLabel.setForeground(Color.BLACK);
        exerciseTextLabel.setBounds(730, 530 + 128 + 5, 128, 20);
        exerciseTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(exerciseTextLabel, Integer.valueOf(3));

        vetButton = MainScreen.buttonCreate(900,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        vetButton = MainScreen.buttonCreate(900,550,128,128, "resources/command_button.png", "resources/command_button_clicked.png", "");
        vetButton.addActionListener(e -> {

            playSound("resources/heal_sound.wav");

            PlayerInventory inventory = gameData.getInventory(); // Get PlayerInventory
            int currentTime = (int) (System.currentTimeMillis() / 1000); // Current time in seconds

            if (inventory.takePetToVet(pet, currentTime)) {
                HealthProgressBar.setValue(pet.getHealth());
                System.out.println(pet.getName() + " went to the vet and gained health!");
                refreshCoinDisplay(); // Add this to update coin display
            } else {
                int remainingCooldown = pet.getVetCooldownDuration() - (currentTime - pet.getLastVetVisitTime());
                remainingCooldown = Math.max(remainingCooldown, 0); // Ensure it doesn't go negative

                JOptionPane.showMessageDialog(
                        this,
                        pet.getName() + " must wait " + remainingCooldown + " seconds before visiting the vet again.",
                        "Vet Cooldown",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        ImageIcon vetIcon = new ImageIcon("resources/vet_icon.png");
        JLabel vetIconLabel = new JLabel(vetIcon);
        vetIconLabel.setBounds(900 + (128 - 47)/2, 545 + (128 - 47)/2, 47, 44);
        add(vetIconLabel, Integer.valueOf(3));
        add(vetButton, Integer.valueOf(2));
        JLabel vetTextLabel = new JLabel("VET");
        vetTextLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        vetTextLabel.setForeground(Color.BLACK);
        vetTextLabel.setBounds(900, 530 + 128 + 5, 128, 20);
        vetTextLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(vetTextLabel, Integer.valueOf(3));

        sleepButton = MainScreen.buttonCreate(150, 500, 56,47, "resources/sleep_button.png", "resources/sleep_button.png", "");
        add(sleepButton, Integer.valueOf(2));
        sleepButton.addActionListener(e -> {
            pet.setSleeping(true);
        });
    }

    private void toggleOutfit(Pet pet, PlayerInventory inventory) {
        if (pet.isWearingOutfit()) {
            System.out.println("Unequipping current outfit: " + pet.getCurrentOutfit());
            inventory.unequipOutfit(pet);
        } else {
            // Try to equip the outfit the player owns for this pet
            String petType = pet.getPetType();
            String matchingOutfit = "";

            if (petType.equals("PetOption1")) matchingOutfit = "outfit1";
            else if (petType.equals("PetOption2")) matchingOutfit = "outfit2";
            else if (petType.equals("PetOption3")) matchingOutfit = "outfit3";

            if (inventory.ownsOutfit(matchingOutfit)) {
                System.out.println("Equipping outfit: " + matchingOutfit);
                inventory.equipOutfit(matchingOutfit, pet);
            } else {
                playSound("resources/error_button_sound.wav");
                System.out.println("No outfit available to equip.");
            }
        }
    }


    private void showInventoryPopup(JButton sourceButton, String inventoryType) {
        // inventory popup
        ImageIcon originalIcon = new ImageIcon("resources/inventory_popup.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(originalIcon.getIconWidth()/2, originalIcon.getIconHeight()/2, Image.SCALE_SMOOTH);
        ImageIcon popupIcon = new ImageIcon(scaledImage);
        JLabel popupLabel = new JLabel(popupIcon);

        // Original close button implementation
        JButton closeButton = MainScreen.buttonCreate(380, 60, 100, 100, "resources/save.png", "resources/save_clicked.png", "");

        // X label on top of close button
        JLabel xLabel = new JLabel("X");
        xLabel.setFont(customFont.deriveFont(Font.BOLD, 19f));
        xLabel.setForeground(Color.BLACK);
        xLabel.setBounds(423, 100, 20, 20);
        xLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // inventory JLayeredPane
        JLayeredPane inventoryPane = new JLayeredPane();
        inventoryPane.setPreferredSize(new Dimension(popupIcon.getIconWidth(), popupIcon.getIconHeight()));

        // add components to inventoryPane - background first
        popupLabel.setBounds(0, 30, popupIcon.getIconWidth(), popupIcon.getIconHeight());
        inventoryPane.add(popupLabel, JLayeredPane.DEFAULT_LAYER); // Bottom layer
        inventoryPane.add(closeButton, JLayeredPane.MODAL_LAYER);
        inventoryPane.add(xLabel, JLayeredPane.POPUP_LAYER);

        // Create inventory grid squares
        ImageIcon squareIcon = new ImageIcon("resources/inventory_item_square_1.png");
        int startX = 100;
        int startY = 140;
        int squareWidth = 90;
        int squareHeight = 90;
        int horizontalGap = 20;
        int verticalGap = 15;

        JLabel[] squares = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            int row = i / 3;
            int col = i % 3;
            squares[i] = new JLabel(squareIcon);
            squares[i].setBounds(
                    startX + col * (squareWidth + horizontalGap),
                    startY + row * (squareHeight + verticalGap),
                    squareWidth,
                    squareHeight
            );
            inventoryPane.add(squares[i], JLayeredPane.PALETTE_LAYER);
        }

        // Position the popup
        int popupX = sourceButton.getX() + (sourceButton.getWidth() - popupIcon.getIconWidth())/2;
        int popupY = sourceButton.getY() - popupIcon.getIconHeight() + 110;
        popupX = Math.max(0, Math.min(popupX, getWidth() - popupIcon.getIconWidth()));
        popupY = Math.max(0, Math.min(popupY, getHeight() - popupIcon.getIconHeight()));
        inventoryPane.setBounds(popupX, popupY, popupIcon.getIconWidth(), popupIcon.getIconHeight());
        add(inventoryPane, JLayeredPane.POPUP_LAYER);

        // Create a label for the item GIF that will appear beside the pet
        JLabel itemGifLabel = new JLabel();
        itemGifLabel.setBounds(330, 200, 405, 393); // Position beside the pet
        add(itemGifLabel, Integer.valueOf(4)); // Higher layer than pet

        // Handle food items
        if (inventoryType.equals("Feed")) {
            PlayerInventory inventory = gameData.getInventory();
            int itemIndex = 0;

            for (Food food : inventory.getFoodInventory().keySet()) {
                int quantity = inventory.getFoodCount(food);
                if (quantity <= 0 || itemIndex >= 6) continue;

                JLabel square = squares[itemIndex];

                // Load and scale food icon to 40x40
                String iconPath = "resources/food_" + food.getName().replace(" ", "_").toLowerCase() + ".png";
                ImageIcon originalFoodIcon = new ImageIcon(iconPath);
                Image scaledFoodImage = originalFoodIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

                // Create food button with scaled icon
                JButton foodButton = new JButton(new ImageIcon(scaledFoodImage));
                foodButton.setBounds(
                        square.getX() + (square.getWidth() - 40)/2,
                        square.getY() + (square.getHeight() - 40)/2,
                        40,
                        40
                );
                foodButton.setContentAreaFilled(false);
                foodButton.setBorderPainted(false);
                foodButton.setFocusPainted(false);

                // Quantity label
                JLabel quantityLabel = new JLabel("x" + quantity);
                quantityLabel.setFont(customFont.deriveFont(12f));
                quantityLabel.setForeground(Color.BLACK);
                quantityLabel.setBounds(square.getX() + square.getWidth() - 25, square.getY() + square.getHeight() - 20, 25, 15);

                inventoryPane.add(foodButton, JLayeredPane.MODAL_LAYER);
                inventoryPane.add(quantityLabel, JLayeredPane.MODAL_LAYER);

                foodButton.addActionListener(e -> {
                    itemGifLabel.setBounds(330, 200, 405, 393); // Food position
                    // Show the food GIF beside the pet
                    String foodGifPath = "resources/food_" + food.getName().replace(" ", "_").toLowerCase() + ".gif";
                    ImageIcon foodGif = new ImageIcon(foodGifPath);
                    itemGifLabel.setIcon(foodGif);

                    boolean fed = inventory.feedPet(pet, food);
                    if (fed) {
                        FullnessProgressBar.setValue(pet.getFullness());
                        playSound("resources/eating_sound.wav");
                        updateGif(getGifPath("Eating"),1500);
                        refreshCoinDisplay(); // Add this to update coin display

                        // Remove the food GIF after 1.5 seconds
                        Timer gifTimer = new Timer(1500, ev -> {
                            itemGifLabel.setIcon(null);
                        });
                        gifTimer.setRepeats(false);
                        gifTimer.start();

                        remove(inventoryPane);
                        revalidate();
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, "Out of " + food.getName() + "!", "No Food", JOptionPane.WARNING_MESSAGE);
                    }
                });
                itemIndex++;
            }
        }

        // Handle toy items
        if (inventoryType.equals("Play")) {
            PlayerInventory inventory = gameData.getInventory();
            int itemIndex = 0;

            for (Toys toy : inventory.getToyInventory().keySet()) {
                int quantity = inventory.getToyCount(toy);
                if (quantity <= 0 || itemIndex >= 6) continue;

                JLabel square = squares[itemIndex];

                // Load and scale toy icon to 40x40
                String iconPath = "resources/toy_" + toy.getName().replace(" ", "_").toLowerCase() + ".png";
                ImageIcon originalToyIcon = new ImageIcon(iconPath);
                Image scaledToyImage = originalToyIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);

                // Create toy button with scaled icon
                JButton toyButton = new JButton(new ImageIcon(scaledToyImage));
                toyButton.setBounds(square.getX() + (square.getWidth() - 40)/2, square.getY() + (square.getHeight() - 40)/2, 40, 40);
                toyButton.setContentAreaFilled(false);
                toyButton.setBorderPainted(false);
                toyButton.setFocusPainted(false);

                // Quantity label
                JLabel quantityLabel = new JLabel("x" + quantity);
                quantityLabel.setFont(customFont.deriveFont(12f));
                quantityLabel.setForeground(Color.BLACK);
                quantityLabel.setBounds(
                        square.getX() + square.getWidth() - 25,
                        square.getY() + square.getHeight() - 20,
                        25,
                        15
                );

                inventoryPane.add(toyButton, JLayeredPane.MODAL_LAYER);
                inventoryPane.add(quantityLabel, JLayeredPane.MODAL_LAYER);

                // In the toy button action listener (play action)
                toyButton.addActionListener(e -> {
                    itemGifLabel.setBounds(430, 350, 100, 100);

                    // Show the toy GIF
                    String toyPngPath = "resources/toy_" + toy.getName().replace(" ", "_").toLowerCase() + ".png";
                    ImageIcon originalToyPNG = new ImageIcon(toyPngPath);
                    Image scaledToyPNG = originalToyPNG.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                    itemGifLabel.setIcon(new ImageIcon(scaledToyPNG));

                    if (inventory.hasToy(toy)) {
                        pet.increaseHappiness(25);
                        HappinessProgressBar.setValue(pet.getHappiness());
                        playSound("resources/play_sound.wav");
                        updateGif(getGifPath("Playing"),1500);

                        // Add coin reward for playing
                        inventory.setPlayerCoins(inventory.getPlayerCoins() + 500);
                        refreshCoinDisplay(); // Update the coin display

                        // Remove the toy GIF after 1.5 seconds
                        Timer gifTimer = new Timer(1500, ev -> {
                            itemGifLabel.setIcon(null);
                        });
                        gifTimer.setRepeats(false);
                        gifTimer.start();

                        remove(inventoryPane);
                        revalidate();
                        repaint();
                    } else {
                        JOptionPane.showMessageDialog(this, "You don't have " + toy.getName() + "!", "No Toy", JOptionPane.WARNING_MESSAGE);
                    }
                });
                itemIndex++;
            }
        }

        // Original close button action
        closeButton.addActionListener(e -> {
            remove(inventoryPane);
            itemGifLabel.setIcon(null); // Clear any displayed GIF
            revalidate();
            repaint();
        });

        revalidate();
        repaint();
    }

    private void updateSprite(Pet pet) {
        String outfit = pet.getCurrentOutfit();
        String spritePath;

        if (outfit == null || outfit.isEmpty()) {
            spritePath = "resources/" + base + "_"+ state + ".gif";
        } else {
            spritePath = "resources/" + base + "Outfit_" + state + ".gif";
        }

        // Only update if sprite has changed
        if (spritePath.equals(currentSpritePath)) return;

        // Remove old label
        if (gifLabel != null) remove(gifLabel);

        ImageIcon gifIcon = new ImageIcon(spritePath);
        gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(300, 30, 622, 632);
        add(gifLabel, Integer.valueOf(3));
        currentSpritePath = spritePath;

        revalidate();
        repaint();
    }

    private String getGifPath(String action) {
        String petType = pet.getPetType();
        String baseName = "";

        // Determine the base name for the pet type
        if (petType.equals("PetOption1")) {
            baseName = "PetOne";
        } else if (petType.equals("PetOption2")) {
            baseName = "PetTwo";
        } else {
            baseName = "PetThree";
        }

        // Append outfit status and action
        if (pet.isWearingOutfit()) {
            return "resources/" + baseName + "Outfit_" + action + ".gif";
        } else {
            return "resources/" + baseName + "_" + action + ".gif";
        }
    }


    private void spriteGifs(String spriteFilePath) {
        if (gifLabel != null) {
            remove(gifLabel); // Remove existing GIF before adding a new one
        }

        ImageIcon gifIcon = new ImageIcon(spriteFilePath);
        gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(300, 30, 622, 632);
        add(gifLabel, Integer.valueOf(3));

        revalidate();
        repaint();
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

    public void displayCoins(){
        ImageIcon originalCoinIcon = new ImageIcon("resources/coins_display.png");
        Image scaledCoinImage = originalCoinIcon.getImage().getScaledInstance(200, 46, Image.SCALE_SMOOTH);
        ImageIcon scaledCoinIcon = new ImageIcon(scaledCoinImage);

        // create the label with scaled image
        JLabel coinDisplayLabel = new JLabel(scaledCoinIcon);
        coinDisplayLabel.setBounds(820, 460, 200, 46);

        int coins = gameData.getInventory().getPlayerCoins();

        coinLabel = new JLabel(String.valueOf(coins));
        coinLabel.setFont(customFont.deriveFont(Font.BOLD, 17f));
        coinLabel.setForeground(Color.BLACK);

        coinLabel.setBounds(890, 470, 100, 30);

        add(coinLabel, Integer.valueOf(4));
        add(coinDisplayLabel, Integer.valueOf(3));
        revalidate();
        repaint();
    }

    public void refreshCoinDisplay() {
        int coins = gameData.getInventory().getPlayerCoins();
        coinLabel.setText(String.valueOf(coins));
        revalidate();
        repaint();
    }
}
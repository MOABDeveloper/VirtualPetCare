package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class StoreScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;
    private CardLayout shopCardLayout;
    private JPanel shopPanel;
    private List<JButton> allButtons = new ArrayList<>();
    private Store store;
    private PlayerInventory playerInventory;
    private GameData gameData;
    private JButton nextButton;
    private JButton prevButton;
    private JLabel coinLabel;

    public StoreScreen(Font customFont, CardLayout mainCardLayout, JPanel mainPanel, Store store, GameData gameData) {
        this.customFont = customFont;
        this.mainCardLayout = mainCardLayout;
        this.mainPanel = mainPanel;
        this.store = store;
        this.gameData = gameData;

        if (this.playerInventory == null) {
            System.out.println("ERROR: PlayerInventory is NULL in StoreScreen!");
        } else {
            System.out.println("Player Coins at Store Start: " + this.playerInventory.getPlayerCoins());
        }
        for (Component comp : mainPanel.getComponents()) {
            System.out.println("Component: " + comp.getClass().getName());
        }

        setPreferredSize(new Dimension(1080, 750));

        setupBackground();
        updateCoinDisplay();

        this.shopCardLayout = new CardLayout();
        this.shopPanel = new JPanel(shopCardLayout);
        shopPanel.setOpaque(false);
        shopPanel.setBounds(0, 0, 1080, 750);
        add(shopPanel, Integer.valueOf(2));

        // Create navigation buttons
        nextButton = createNavButton("resources/next_page.png", 700, 600);
        nextButton.addActionListener(e -> {
            shopCardLayout.next(shopPanel);
            updateNavButtons();
        });
        add(nextButton, Integer.valueOf(3));

        prevButton = createNavButton("resources/prev_page.png", 300, 600);
        prevButton.addActionListener(e -> {
            shopCardLayout.previous(shopPanel);
            updateNavButtons();
        });
        prevButton.setVisible(false); // Hide initially
        add(prevButton, Integer.valueOf(3));

        populateStorePages();

        JButton homeButton = MainScreen.buttonCreate(800, 50, 192, 64,
                "resources/home_button.png", "resources/home_button_clicked.png", "InGame");
        homeButton.addActionListener(e -> {
            resetToFirstPage(); // Ensure shop resets

            // Directly switch to InGameScreen
            mainCardLayout.show(mainPanel, "InGameScreen");
        });


        add(homeButton, Integer.valueOf(3));
        allButtons.add(homeButton);
    }

    private void updateNavButtons() {
        // Get current page name
        Component[] components = shopPanel.getComponents();
        for (Component comp : components) {
            if (comp.isVisible()) {
                String pageName = shopPanel.getComponentZOrder(comp) == 0 ? "Page 1" :
                        shopPanel.getComponentZOrder(comp) == 1 ? "Page 2" : "Page 3";

                // Update button visibility based on current page
                if (pageName.equals("Page 1")) {
                    nextButton.setVisible(true);
                    prevButton.setVisible(false);
                } else if (pageName.equals("Page 2")) {
                    nextButton.setVisible(true);
                    prevButton.setVisible(true);
                } else if (pageName.equals("Page 3")) {
                    nextButton.setVisible(false);
                    prevButton.setVisible(true);
                }
                break;
            }
        }
    }

    private JButton createNavButton(String imagePath, int x, int y) {
        JButton button = new JButton(new ImageIcon(imagePath));
        button.setBounds(x, y, 64, 64);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        return button;
    }

    public void resetToFirstPage() {
        if (shopCardLayout != null && shopPanel != null) {
            shopCardLayout.show(shopPanel, "Page 1");
            updateNavButtons();
        }
    }

    private void setupBackground() {
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        ImageIcon shopBG = new ImageIcon("resources/shop_bg.png");
        JLabel bgLabel = new JLabel(shopBG);
        bgLabel.setBounds(0, -15, 1080, 750);
        add(bgLabel, Integer.valueOf(1));

        JLabel shopText = new JLabel("SHOP");
        shopText.setFont(customFont.deriveFont(Font.BOLD, 18f));
        shopText.setForeground(Color.BLACK);
        shopText.setBounds(500, 70, 200, 30);
        add(shopText, Integer.valueOf(3)); // Higher layer to appear above background

    }

    private void populateStorePages() {
        // Create three pages
        createPage1();
        createPage2();
        createPage3();
    }

    private void createPage1() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));
        page.setOpaque(false);

        int x = 200, y = 130;
        int itemsPerRow = 3;
        int count = 0;

        // Add first 6 food items
        List<String> foodNames = new ArrayList<>(store.getAllFood().keySet());
        for (int i = 0; i < Math.min(6, foodNames.size()); i++) {
            String foodName = foodNames.get(i);
            Food food = store.getFood(foodName);
            JPanel itemPanel = createShopItem(food.getName(), food.getPrice(), x, y);
            page.add(itemPanel, Integer.valueOf(2));

            x += 250;
            count++;
            if (count % itemsPerRow == 0) {
                x = 200;
                y += 240;
            }
        }
        shopPanel.add(page, "Page 1");
    }

    private void createPage2() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));
        page.setOpaque(false);

        int x = 200, y = 130;
        int itemsPerRow = 3;
        int count = 0;

        // Add all 5 toys
        List<String> toyNames = new ArrayList<>(store.getAllToys().keySet());
        for (int i = 0; i < Math.min(6, toyNames.size()); i++) {
            String toyName = toyNames.get(i);
            Toys toy = store.getToy(toyName);
            JPanel itemPanel = createShopItem(toy.getName(), toy.getPrice(), x, y);
            page.add(itemPanel, Integer.valueOf(2));

            x += 250;
            count++;
            if (count % itemsPerRow == 0) {
                x = 200;
                y += 240;
            }
        }

        shopPanel.add(page, "Page 2");
    }

    private void createPage3() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));
        page.setOpaque(false);

        int x = 200, y = 130;
        int itemsPerRow = 3;
        int count = 0;

        // Add all gifts (outfits)
        List<String> giftNames = new ArrayList<>(store.getAllGifts().keySet());
        for (int i = 0; i < giftNames.size(); i++) {
            String giftName = giftNames.get(i);
            Gifts gift = store.getGift(giftName);
            JPanel itemPanel = createShopItem(gift.getName(), gift.getPrice(), x, y);
            page.add(itemPanel, Integer.valueOf(2));

            x += 250;
            count++;
            if (count % itemsPerRow == 0) {
                x = 200;
                y += 240;
            }
        }

        shopPanel.add(page, "Page 3");
    }

    private JPanel createShopItem(String itemName, int price, int x, int y) {
        // Display dimensions
        final int ICON_WIDTH = 70;
        final int ICON_HEIGHT = 70;
        final double VERTICAL_POSITION_RATIO = 0.3; // 30% from top

        JPanel panel = new JPanel();
        panel.setLayout(new OverlayLayout(panel));
        panel.setBounds(x, y, 165, 221);
        panel.setOpaque(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(165, 221));
        layeredPane.setOpaque(false);

        // Base background image
        ImageIcon baseIcon = loadAndScaleImage("resources/item_image.png", 165, 221);
        JButton imageButton = new JButton(baseIcon);
        imageButton.setBounds(0, 0, 165, 221);
        imageButton.setContentAreaFilled(false);
        imageButton.setBorderPainted(false);
        imageButton.setFocusPainted(false);
        imageButton.addActionListener(e -> showPopup(itemName, price, this.gameData.getPet()));

        // Determine item type and load appropriate image
        String imagePath = getItemImagePath(itemName);
        if (imagePath != null) {
            ImageIcon itemIcon = loadAndScaleImage(imagePath, ICON_WIDTH, ICON_HEIGHT);
            if (itemIcon != null) {
                JLabel itemImageLabel = new JLabel(itemIcon);
                int iconX = (165 - ICON_WIDTH) / 2;
                int iconY = (int)((221 - ICON_HEIGHT) * VERTICAL_POSITION_RATIO);
                itemImageLabel.setBounds(iconX, iconY, ICON_WIDTH, ICON_HEIGHT);
                layeredPane.add(itemImageLabel, Integer.valueOf(1));
            }
        }

        // Price label
        JLabel priceLabel = new JLabel(String.valueOf(price), SwingConstants.CENTER);
        priceLabel.setBounds(5, 175, 165, 30);
        priceLabel.setFont(customFont.deriveFont(Font.BOLD, 16f));
        priceLabel.setForeground(Color.BLACK);
        priceLabel.setOpaque(false);

        // Layer components
        layeredPane.add(imageButton, Integer.valueOf(0));
        layeredPane.add(priceLabel, Integer.valueOf(2));
        panel.add(layeredPane);
        allButtons.add(imageButton);

        return panel;
    }

    private String getItemImagePath(String itemName) {
        // Check food items first
        if (store.hasFood(itemName)) {
            return "resources/food_" + itemName.toLowerCase().replace(" ", "_") + ".png";
        }
        // Then check toys
        else if (store.hasToys(itemName)) {
            return "resources/toy_" + itemName.toLowerCase().replace(" ", "_") + ".png";
        }
        // Then check gifts (outfits)
        else if (store.hasGift(itemName)) {
            // Special case for outfit images
            if (itemName.equals("outfit1") || itemName.equals("outfit2") || itemName.equals("outfit3")) {
                return "resources/" + itemName + ".png";
            }
            return "resources/gift_" + itemName.toLowerCase().replace(" ", "_") + ".png";
        }
        return null;
    }

    private ImageIcon loadAndScaleImage(String path, int width, int height) {
        try {
            ImageIcon originalIcon = new ImageIcon(path);
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(scaledImage);
        } catch (Exception e) {
            System.out.println("Error loading image: " + path);
            return null;
        }
    }


    private boolean attemptPurchase(String itemName, int price) {
        PlayerInventory inventory = gameData.getInventory();
        System.out.println("Player coins before purchase: " + inventory.getPlayerCoins());

        if (store.hasFood(itemName)) {
            return store.buyFood(itemName, inventory, 1);
        } else if (store.hasToys(itemName)) {
            return store.buyToy(itemName, inventory, 1);
        } else if (store.hasGift(itemName)) {
            return store.buyGift(itemName, inventory, 1);
        }

        return false;
    }

    private void setAllButtonsEnabled(boolean enabled) {
        for (JButton button : allButtons) {
            button.setEnabled(enabled);
        }
        // Also handle navigation buttons separately since they might not be in allButtons
        nextButton.setEnabled(enabled);
        prevButton.setEnabled(enabled);
    }

    private void showPopup(String itemName, int price, Pet pet) {
        // Disable all buttons first
        setAllButtonsEnabled(false);

        JLayeredPane popup = new JLayeredPane();
        popup.setBounds(372, 86, 336, 579);
        popup.setOpaque(false);

        // Background image
        JLabel popupImage = new JLabel(new ImageIcon("resources/store_popup.png"));
        popupImage.setBounds(0, 0, 336, 579);
        popup.add(popupImage, Integer.valueOf(0));

        // Load and display item image
        String imagePath = getItemImagePath(itemName);
        ImageIcon itemIcon = loadAndScaleImage(imagePath, 130, 130);
        JLabel itemImageLabel = new JLabel(itemIcon);
        itemImageLabel.setBounds(100, 60, 130, 130);
        popup.add(itemImageLabel, Integer.valueOf(1));

        // Item name
        JLabel nameLabel = new JLabel(itemName, SwingConstants.CENTER);
        nameLabel.setBounds(0, 240, 336, 30);
        nameLabel.setFont(customFont.deriveFont(Font.BOLD, 18f));
        popup.add(nameLabel, Integer.valueOf(1));

        // Initialize description and stats
        String description = "";
        String stats = "";

        if (store.hasFood(itemName)) {
            Food food = store.getFood(itemName);
            description = food.getDescription().isEmpty() ? "A tasty treat!" : food.getDescription();
            stats = "Fullness: +" + food.getFullness();
        }
        else if (store.hasToys(itemName)) {
            Toys toy = store.getToy(itemName);
            description = toy.getDescription().isEmpty() ? "Fun to play with!" : toy.getDescription();
            stats = "Happiness: +20"; // Assuming fixed happiness increase for toys
        }
        else if (store.hasGift(itemName)) {
            description = "A special outfit for your pet!";
            stats = "Unique appearance";
        }

        // Description with word wrap
        JTextArea descArea = new JTextArea(description);
        descArea.setBounds(40, 360, 275, 70);
        descArea.setFont(customFont.deriveFont(Font.PLAIN, 15f));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setOpaque(false);
        popup.add(descArea, Integer.valueOf(1));

        // Stats
        JLabel statsLabel = new JLabel(stats, SwingConstants.CENTER);
        statsLabel.setBounds(0, 480, 336, 30);
        statsLabel.setFont(customFont.deriveFont(Font.BOLD, 14f));
        popup.add(statsLabel, Integer.valueOf(1));


        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(90, 540, 80, 30);
        buyButton.addActionListener(e -> {
            System.out.println("Buy button clicked, Outfit clicked is: "+ itemName);

            // Check if the item is an outfit
            if (isOutfit(itemName)) {
                String allowedOutfit = getAllowedOutfit(pet);

                System.out.println("You are trying to buy" + itemName);
                System.out.println("Pet can only wear" + allowedOutfit);

                // If the pet is not allowed to wear this outfit, show an error message
                if (!itemName.equals(allowedOutfit)) {
                    System.out.println("Should have failed");
                    JOptionPane.showMessageDialog(this,
                            "This pet cannot wear " + itemName + "! It can only wear " + allowedOutfit,
                            "Purchase Failed",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Stop the purchase
                }
            }

            // Process the purchase
            boolean purchaseSuccess = attemptPurchase(itemName, price);
            if (purchaseSuccess) {
                updateCoinDisplay();
                if (isOutfit(itemName)) {
                    pet.setOutfit(itemName); // Equip the outfit
                    JOptionPane.showMessageDialog(this,
                            "You bought " + itemName + "! Your pet is now wearing it!",
                            "Purchase Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                            "You bought " + itemName + "!",
                            "Purchase Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "Not enough coins!",
                        "Purchase Failed",
                        JOptionPane.ERROR_MESSAGE);
            }

            remove(popup);
            setAllButtonsEnabled(true); // Re-enable buttons
            revalidate();
            repaint();
        });

        popup.add(buyButton, Integer.valueOf(2));


        // Close Button
        JButton closeButton = new JButton("Close");
        closeButton.setBounds(190, 540, 80, 30);
        closeButton.addActionListener(e -> {
            remove(popup);
            setAllButtonsEnabled(true);
            revalidate();
            repaint();
        });
        popup.add(closeButton, Integer.valueOf(1));

        add(popup, Integer.valueOf(5));
        revalidate();
        repaint();
    }

    // Check if the item is an outfit
    private boolean isOutfit(String itemName) {
        return itemName.equals("outfit1") || itemName.equals("outfit2") || itemName.equals("outfit3");
    }

    // Get the allowed outfit for the pet
    private String getAllowedOutfit(Pet pet) {
        switch (pet.getPetType()) {
            case "PetOption1": return "outfit1";
            case "PetOption2": return "outfit2";
            case "PetOption3": return "outfit3";
            default: return "None"; // In case of an unknown pet type
        }
    }

    private void updateCoinDisplay() {
        if (coinLabel != null) {
            remove(coinLabel); // Remove existing label if it exists
        }

        int coins = gameData.getInventory().getPlayerCoins();
        coinLabel = new JLabel("Coins: " + coins);
        coinLabel.setFont(customFont.deriveFont(Font.BOLD, 18f));
        coinLabel.setForeground(Color.BLACK);
        coinLabel.setBounds(20, 20, 200, 30);
        add(coinLabel, Integer.valueOf(3)); // Higher layer to appear above background
    }
}
//package src;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//import java.util.ArrayList;
//import java.util.List;
//
//public class StoreScreen extends JLayeredPane {
//    private Font customFont;
//    private CardLayout mainCardLayout;
//    private JPanel mainPanel;
//    private CardLayout shopCardLayout;
//    private JPanel shopPanel;
//    private List<JButton> allButtons = new ArrayList<>(); // Track to disable and enable buttons
//    private Store store; // Store instance
//    private PlayerInventory playerInventory;
//
//
//    private GameData gameData; // 👈 Add this line
//
//    public StoreScreen(Font customFont, CardLayout mainCardLayout, JPanel mainPanel, Store store, GameData gameData) {
//        this.customFont = customFont;
//        this.mainCardLayout = mainCardLayout;
//        this.mainPanel = mainPanel;
//        this.store = store;
//        this.gameData = gameData; // ✅ Save the real game data
//
//
//        if (this.playerInventory == null) {
//            System.out.println("ERROR: PlayerInventory is NULL in StoreScreen!");
//        } else {
//            System.out.println("Player Coins at Store Start: " + this.playerInventory.getPlayerCoins());
//        }
//
//        setPreferredSize(new Dimension(1080, 750));
//
//        setupBackground();
//
//        this.shopCardLayout = new CardLayout();
//        this.shopPanel = new JPanel(shopCardLayout);
//        shopPanel.setOpaque(false);
//        shopPanel.setBounds(0, 0, 1080, 750);
//        add(shopPanel, Integer.valueOf(2));
//
//        populateStore(); // Load items dynamically
//
//        JButton homeButton = MainScreen.buttonCreate(800, 50, 192, 64, "resources/home_button.png", "resources/home_button_clicked.png", "InGame");
//        homeButton.addActionListener(e -> {
//            resetToFirstPage(); // Now this method exists
//            mainCardLayout.show(mainPanel, "InGame");
//        });
//        add(homeButton, Integer.valueOf(3));
//        allButtons.add(homeButton);
//
////        JButton homeButton = MainScreen.buttonCreate(800, 50, 192, 64, "resources/home_button.png", "resources/home_button_clicked.png", "InGame");
////        homeButton.addActionListener(e -> {
////            resetToFirstPage();
////            mainCardLayout.show(mainPanel, "InGame");
////        });
////        add(homeButton, Integer.valueOf(3));
////        allButtons.add(homeButton);
//    }
//    public void resetToFirstPage() {
//        if (shopCardLayout != null && shopPanel != null) {
//            shopCardLayout.show(shopPanel, "Page 1"); // Switch back to first shop page
//        }
//    }
//
//
//    private void setupBackground() {
//        ImageIcon background = new ImageIcon("resources/grid.png");
//        JLabel backgroundLabel = new JLabel(background);
//        backgroundLabel.setBounds(0, 0, 1080, 750);
//        add(backgroundLabel, Integer.valueOf(0));
//
//        ImageIcon shopBG = new ImageIcon("resources/shop_bg.png");
//        JLabel bgLabel = new JLabel(shopBG);
//        bgLabel.setBounds(0, -15, 1080, 750);
//        add(bgLabel, Integer.valueOf(1));
//    }
//
//    private void populateStore() {
//        JLayeredPane page = new JLayeredPane();
//        page.setPreferredSize(new Dimension(1080, 750));
//        page.setOpaque(false);
//
//        int x = 200, y = 130;  // Adjust starting position
//        int itemsPerRow = 3;
//        int count = 0;
//
//        // Load Food Items Dynamically
//        for (String foodName : store.getAllFood().keySet()) {
//            Food food = store.getFood(foodName);
//            JPanel itemPanel = createShopItem("resources/item_image.png", food.getName(), food.getPrice(), x, y);
//            page.add(itemPanel, Integer.valueOf(2));
//
//            x += 250;  // Adjust spacing between items
//            count++;
//            if (count % itemsPerRow == 0) {
//                x = 200;
//                y += 130;  // Move to next row after 3 items
//            }
//        }
//
//        shopPanel.add(page, "Page 1");
//        shopCardLayout.show(shopPanel, "Page 1");
//    }
//
//
//    private JPanel createShopItem(String imagePath, String itemName, int price, int x, int y) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Stack Image & Text
//        panel.setBounds(x, y, 165, 221); // Ensure enough space
//        panel.setOpaque(false);
//
//        // Load and Scale Image
//        ImageIcon icon = new ImageIcon(imagePath);
//        Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
//        ImageIcon resizedIcon = new ImageIcon(scaledImage);
//
//        // Make Image a Clickable Button
//        JButton imageButton = new JButton(resizedIcon);
//        imageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        imageButton.setOpaque(false);
//        imageButton.setContentAreaFilled(false);
//        imageButton.setBorderPainted(false);
//        imageButton.setFocusPainted(false);
//        imageButton.setPreferredSize(new Dimension(120, 120));
//
//        // Add Click Event for Popup
//        imageButton.addActionListener(e -> showPopup(itemName, price));
//
//        // Item Name & Price
//        JLabel textLabel = new JLabel("<html><center>" + itemName + "<br>Price: " + price + "</center></html>");
//        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        textLabel.setFont(new Font("Arial", Font.BOLD, 12));
//
//        // Add Components
//        panel.add(imageButton); // Clickable Image Button
//        panel.add(textLabel);   // Show Name & Price
//
//        allButtons.add(imageButton); // Track interactive buttons
//
//        return panel;
//    }
//
//
//
//
//
////    private JPanel createShopItem(String imagePath, String itemName, int price, int x, int y) {
////        JPanel panel = new JPanel();
////        panel.setLayout(new BorderLayout());
////        panel.setBounds(x, y, 150, 180);
////        panel.setOpaque(false);
////
////        // Item Image
////        ImageIcon icon = new ImageIcon(imagePath);
////        JLabel imageLabel = new JLabel(icon);
////        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
////
////        // Item Name & Price
////        JLabel textLabel = new JLabel("<html><center>" + itemName + "<br>Price: " + price + "</center></html>");
////        textLabel.setHorizontalAlignment(SwingConstants.CENTER);
////        textLabel.setFont(new Font("Arial", Font.BOLD, 12));
////
////        // Invisible Button for Interaction
////        JButton button = new JButton();
////        button.setOpaque(false);
////        button.setContentAreaFilled(false);
////        button.setBorderPainted(false);
////        button.setFocusPainted(false);
////        button.setPreferredSize(new Dimension(150, 180));
////
////        // Click Event for Popup
////        button.addActionListener(e -> showPopup(itemName, price));
////
////        panel.add(imageLabel, BorderLayout.CENTER);
////        panel.add(textLabel, BorderLayout.SOUTH);
////        panel.add(button, BorderLayout.CENTER); // Overlay button
////
////        allButtons.add(button); // Track interactive buttons
////
////        return panel;
////    }
//    private void showPopup(String itemName, int price) {
//        JLayeredPane popup = new JLayeredPane();
//        popup.setBounds(372, 86, 336, 579);
//        popup.setOpaque(false);
//
//        JLabel popupImage = new JLabel(new ImageIcon("resources/store_popup.png"));
//        popupImage.setBounds(0, 0, 336, 579);
//        popup.add(popupImage, Integer.valueOf(1));
//
//        JLabel textLabel = new JLabel("<html><center>" + itemName + "<br>Price: " + price + "</center></html>");
//        textLabel.setBounds(50, 200, 236, 50);
//        popup.add(textLabel, Integer.valueOf(2));
//
//        JButton buyButton = new JButton("Buy");
//        buyButton.setBounds(90, 540, 80, 30);
//        buyButton.addActionListener(e -> {
//            boolean purchaseSuccess = attemptPurchase(itemName, price);
//            if (purchaseSuccess) {
//                JOptionPane.showMessageDialog(this, "You bought " + itemName + "!", "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);
//            } else {
//                JOptionPane.showMessageDialog(this, "Not enough coins!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
//            }
//            remove(popup);
//            revalidate();
//            repaint();
//        });
//        popup.add(buyButton, Integer.valueOf(2));
//
//        JButton closeButton = new JButton("Close");
//        closeButton.setBounds(190, 540, 80, 30);
//        closeButton.addActionListener(e -> {
//            remove(popup);
//            revalidate();
//            repaint();
//        });
//        popup.add(closeButton, Integer.valueOf(2));
//
//        add(popup, Integer.valueOf(5));
//        revalidate();
//        repaint();
//    }
//
//    private boolean attemptPurchase(String itemName, int price) {
//        PlayerInventory inventory = gameData.getInventory(); // ✅ get actual player inventory
//
//        // Print for debugging
//        System.out.println("Player coins before purchase: " + inventory.getPlayerCoins());
//
//        // Try to buy from all categories (safe because only one will succeed)
//        if (store.hasFood(itemName)) {
//            return store.buyFood(itemName, inventory, 1);
//        } else if (store.hasToys(itemName)) {
//            return store.buyToy(itemName, inventory, 1);
//        } else if (store.hasGift(itemName)) {
//            return store.buyGift(itemName, inventory, 1);
//        }
//
//        return false; // Not found in store
//    }
//
//
//
//
////    private void showPopup(String itemName, int price) {
////        for (JButton button : allButtons) {
////            button.setEnabled(false);
////        }
////
////        JLayeredPane popup = new JLayeredPane();
////        popup.setBounds(372, 86, 336, 579);
////        popup.setOpaque(false);
////
////        JLabel popupImage = new JLabel(new ImageIcon("resources/store_popup.png"));
////        popupImage.setBounds(0, 0, 336, 579);
////        popup.add(popupImage, Integer.valueOf(1));
////
////        JLabel textLabel = new JLabel("<html><center>" + itemName + "<br>Price: " + price + "</center></html>");
////        textLabel.setBounds(50, 200, 236, 50);
////        popup.add(textLabel, Integer.valueOf(2));
////
////        JButton closeButton = new JButton("Close");
////        closeButton.setBounds(128, 540, 80, 30);
////        closeButton.addActionListener(e -> {
////            remove(popup);
////            for (JButton button : allButtons) {
////                button.setEnabled(true);
////            }
////            revalidate();
////            repaint();
////        });
////        popup.add(closeButton, Integer.valueOf(2));
////
////        add(popup, Integer.valueOf(5));
////        revalidate();
////        repaint();
////    }
////
////    public void resetToFirstPage() {
////        shopCardLayout.show(shopPanel, "Page 1");
////    }
//}

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

        this.shopCardLayout = new CardLayout();
        this.shopPanel = new JPanel(shopCardLayout);
        shopPanel.setOpaque(false);
        shopPanel.setBounds(0, 0, 1080, 750);
        add(shopPanel, Integer.valueOf(2));

        // Create navigation buttons
        nextButton = createNavButton("resources/next_page.png", 900, 350);
        nextButton.addActionListener(e -> {
            shopCardLayout.next(shopPanel);
            updateNavButtons();
        });
        add(nextButton, Integer.valueOf(3));

        prevButton = createNavButton("resources/prev_page.png", 100, 350);
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
    }

    private void populateStorePages() {
        // Create three pages
        createPage1();
        createPage2();
        createPage3();
    }

//    private void createPage1() {
//        JLayeredPane page = new JLayeredPane();
//        page.setPreferredSize(new Dimension(1080, 750));
//        page.setOpaque(false);
//
//        int x = 200, y = 130;
//        int itemsPerRow = 3;
//        int count = 0;
//
//        // Add first 3 food items
//        List<String> foodNames = new ArrayList<>(store.getAllFood().keySet());
//        for (int i = 0; i < Math.min(3, foodNames.size()); i++) {
//            String foodName = foodNames.get(i);
//            Food food = store.getFood(foodName);
//            JPanel itemPanel = createShopItem("resources/item_image.png", food.getName(), food.getPrice(), x, y);
//            page.add(itemPanel, Integer.valueOf(2));
//
//            x += 250;
//            count++;
//            if (count % itemsPerRow == 0) {
//                x = 200;
//                y += 130;
//            }
//        }
//
//        shopPanel.add(page, "Page 1");
//    }
//
//    private void createPage2() {
//        JLayeredPane page = new JLayeredPane();
//        page.setPreferredSize(new Dimension(1080, 750));
//        page.setOpaque(false);
//
//        int x = 200, y = 130;  // Changed from 150 to 200
//        int itemsPerRow = 3;
//        int count = 0;
//
//        // Rest of the method remains the same...
//        List<String> foodNames = new ArrayList<>(store.getAllFood().keySet());
//        for (int i = 3; i < Math.min(9, foodNames.size()); i++) {
//            String foodName = foodNames.get(i);
//            Food food = store.getFood(foodName);
//            JPanel itemPanel = createShopItem("resources/item_image.png", food.getName(), food.getPrice(), x, y);
//            page.add(itemPanel, Integer.valueOf(2));
//
//            x += 250;
//            count++;
//            if (count % itemsPerRow == 0) {
//                x = 200;  // Also changed this reset value to 200
//                y += 240;
//            }
//        }
//
//        shopPanel.add(page, "Page 2");
//    }
//
//    private void createPage3() {
//        JLayeredPane page = new JLayeredPane();
//        page.setPreferredSize(new Dimension(1080, 750));
//        page.setOpaque(false);
//
//        int x = 150, y = 130;
//        int itemsPerRow = 3;
//        int count = 0;
//
//        // Add next 6 items (or all remaining if less than 6)
//        List<String> foodNames = new ArrayList<>(store.getAllFood().keySet());
//        for (int i = 9; i < Math.min(15, foodNames.size()); i++) {
//            String foodName = foodNames.get(i);
//            Food food = store.getFood(foodName);
//            JPanel itemPanel = createShopItem("resources/item_image.png", food.getName(), food.getPrice(), x, y);
//            page.add(itemPanel, Integer.valueOf(2));
//
//            x += 250;
//            count++;
//            if (count % itemsPerRow == 0) {
//                x = 150;
//                y += 130;
//            }
//        }
//
//        shopPanel.add(page, "Page 3");
//    }
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
        JPanel itemPanel = createShopItem("resources/item_image.png", food.getName(), food.getPrice(), x, y);
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
            JPanel itemPanel = createShopItem("resources/item_image.png", toy.getName(), toy.getPrice(), x, y);
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

        // Add all gifts
        List<String> giftNames = new ArrayList<>(store.getAllGifts().keySet());
        for (int i = 0; i < giftNames.size(); i++) {
            String giftName = giftNames.get(i);
            Gifts gift = store.getGift(giftName);
            JPanel itemPanel = createShopItem("resources/item_image.png", gift.getName(), gift.getPrice(), x, y);
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

    private JPanel createShopItem(String imagePath, String itemName, int price, int x, int y) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBounds(x, y, 165, 221); // Ensure correct size
        panel.setOpaque(false);

        // Load image and scale to 165x221
        ImageIcon icon = new ImageIcon(imagePath);
        Image image = icon.getImage().getScaledInstance(165, 221, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(image);

        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Clickable Button with Proper Image
        JButton imageButton = new JButton();
        imageButton.setIcon(resizedIcon);
        imageButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageButton.setOpaque(false);
        imageButton.setContentAreaFilled(false);
        imageButton.setBorderPainted(false);
        imageButton.setFocusPainted(false);
        imageButton.setPreferredSize(new Dimension(165, 221));

        // Add Click Event for Popup
        imageButton.addActionListener(e -> showPopup(itemName, price));

        // Item Name & Price
        JLabel textLabel = new JLabel("<html><center>" + itemName + "<br>Price: " + price + "</center></html>");
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textLabel.setFont(new Font("Arial", Font.BOLD, 12));

        // Add Components
        panel.add(imageButton);
        panel.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing
        panel.add(textLabel);

        allButtons.add(imageButton);

        return panel;
    }


    private void showPopup(String itemName, int price) {
        // Disable all buttons first
        setAllButtonsEnabled(false);

        JLayeredPane popup = new JLayeredPane();
        popup.setBounds(372, 86, 336, 579);
        popup.setOpaque(false);

        JLabel popupImage = new JLabel(new ImageIcon("resources/store_popup.png"));
        popupImage.setBounds(0, 0, 336, 579);
        popup.add(popupImage, Integer.valueOf(1));

        JLabel textLabel = new JLabel("<html><center>" + itemName + "<br>Price: " + price + "</center></html>");
        textLabel.setBounds(50, 200, 236, 50);
        popup.add(textLabel, Integer.valueOf(2));

        JButton buyButton = new JButton("Buy");
        buyButton.setBounds(90, 540, 80, 30);
        buyButton.addActionListener(e -> {
            boolean purchaseSuccess = attemptPurchase(itemName, price);
            if (purchaseSuccess) {
                JOptionPane.showMessageDialog(this, "You bought " + itemName + "!", "Purchase Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Not enough coins!", "Purchase Failed", JOptionPane.ERROR_MESSAGE);
            }
            remove(popup);
            setAllButtonsEnabled(true); // Re-enable buttons
            revalidate();
            repaint();
        });
        popup.add(buyButton, Integer.valueOf(2));

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(190, 540, 80, 30);
        closeButton.addActionListener(e -> {
            remove(popup);
            setAllButtonsEnabled(true); // Re-enable buttons
            revalidate();
            repaint();
        });
        popup.add(closeButton, Integer.valueOf(2));

        add(popup, Integer.valueOf(5));
        revalidate();
        repaint();
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
}
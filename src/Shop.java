package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shop extends JLayeredPane {
    private Font customFont;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;
    private CardLayout shopCardLayout;
    private JPanel shopPanel;

    public Shop(Font customFont, CardLayout mainCardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.mainCardLayout = mainCardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        setupBackground();

        this.shopCardLayout = new CardLayout();
        this.shopPanel = new JPanel(shopCardLayout);
        shopPanel.setOpaque(false); // Make transparent
        shopPanel.setBounds(0, 0, 1080, 750);
        add(shopPanel, Integer.valueOf(2)); // Content above background

        JLayeredPane page1 = createShopPage1();
        JLayeredPane page2 = createShopPage2();

        shopPanel.add(page1, "Page 1");
        shopPanel.add(page2, "Page 2");

        shopCardLayout.show(shopPanel, "Page 1");

        JButton homeButton = MainScreen.buttonCreate(800, 50, 192, 64, "resources/home_button.png", "resources/home_button_clicked.png", "InGame");
        homeButton.addActionListener(e -> {
            resetToFirstPage();
            mainCardLayout.show(mainPanel, "InGame");
        });
        add(homeButton, Integer.valueOf(3));
    }

    private void setupBackground() {
        // background grid
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // shop bg
        ImageIcon shopBG = new ImageIcon("resources/shop_bg.png");
        JLabel bgLabel = new JLabel(shopBG);
        bgLabel.setBounds(0, -15, 1080, 750);
        add(bgLabel, Integer.valueOf(1));
    }

    private JLayeredPane createShopPage1() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));
        page.setOpaque(false);

        JButton item1 = createShopItem("resources/item_image.png", 200, 130);
        page.add(item1, Integer.valueOf(2));

        JButton item2 = createShopItem("resources/item_image.png", 460,130);
        page.add(item2, Integer.valueOf(2));

        JButton item3 = createShopItem("resources/item_image.png", 720, 130);
        page.add(item3, Integer.valueOf(2));

        JButton item4 = createShopItem("resources/item_image.png", 200, 370);
        page.add(item4, Integer.valueOf(2));

        JButton item5 = createShopItem("resources/item_image.png", 460, 370);
        page.add(item5, Integer.valueOf(2));

        JButton item6 = createShopItem("resources/item_image.png", 720, 370);
        page.add(item6, Integer.valueOf(2));

        JButton nextPage = MainScreen.buttonCreate(700, 620, 28, 28, "resources/next_page.png", "resources/next_page.png", "");
        nextPage.addActionListener(e -> shopCardLayout.show(shopPanel, "Page 2"));
        page.add(nextPage, Integer.valueOf(2));

        return page;
    }

    private JLayeredPane createShopPage2() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));

        // Different items for page 2
        JButton item7 = createShopItem("resources/item_image2.png", 200, 130);
        page.add(item7, Integer.valueOf(1));

        JButton item8 = createShopItem("resources/item_image2.png", 460,130);
        page.add(item8, Integer.valueOf(1));

        JButton item9 = createShopItem("resources/item_image2.png", 720, 130);
        page.add(item9, Integer.valueOf(1));

        JButton prevPage = MainScreen.buttonCreate(100, 650, 28, 28, "resources/prev_page.png", "resources/prev_page.png", "");
        prevPage.addActionListener(e -> shopCardLayout.show(shopPanel, "Page 1"));
        page.add(prevPage, Integer.valueOf(1));

        JButton homeButton = MainScreen.buttonCreate(800, 50, 192, 64, "resources/home_button.png", "resources/home_button_clicked.png", "InGame");
        homeButton.addActionListener(e -> mainCardLayout.show(mainPanel, "InGame"));
        page.add(homeButton, Integer.valueOf(1));

        return page;
    }

    private JButton createShopItem(String imagePath, int x, int y) {
        ImageIcon icon = new ImageIcon(imagePath);
        JButton button = new JButton(icon);
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        return button;
    }

    public void resetToFirstPage() {
        shopCardLayout.show(shopPanel, "Page 1");
    }

}
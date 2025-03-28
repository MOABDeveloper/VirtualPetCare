package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Shop extends JLayeredPane {
    private Font customFont;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;
    private CardLayout shopCardLayout;
    private JPanel shopPanel;
    private List<JButton> allButtons = new ArrayList<>(); // track to disable and enable buttons

    public Shop(Font customFont, CardLayout mainCardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.mainCardLayout = mainCardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        setupBackground();

        this.shopCardLayout = new CardLayout();
        this.shopPanel = new JPanel(shopCardLayout);
        shopPanel.setOpaque(false);
        shopPanel.setBounds(0, 0, 1080, 750);
        add(shopPanel, Integer.valueOf(2));

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
        allButtons.add(homeButton); // Add to buttons list
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

    private JLayeredPane createShopPage1() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));
        page.setOpaque(false);

        JButton item1 = createShopItem("resources/item_image.png", 200, 130);
        item1.addActionListener(e -> showPopup("resources/store_popup.png"));
        page.add(item1, Integer.valueOf(2));
        allButtons.add(item1);

        JButton item2 = createShopItem("resources/item_image.png", 460, 130);
        item2.addActionListener(e -> showPopup("resources/store_popup.png"));
        page.add(item2, Integer.valueOf(2));
        allButtons.add(item2);

        JButton item3 = createShopItem("resources/item_image.png", 720, 130);
        page.add(item3, Integer.valueOf(2));
        allButtons.add(item3);

        JButton item4 = createShopItem("resources/item_image.png", 200, 370);
        page.add(item4, Integer.valueOf(2));
        allButtons.add(item4);

        JButton item5 = createShopItem("resources/item_image.png", 460, 370);
        page.add(item5, Integer.valueOf(2));
        allButtons.add(item5);

        JButton item6 = createShopItem("resources/item_image.png", 720, 370);
        page.add(item6, Integer.valueOf(2));
        allButtons.add(item6);

        JButton nextPage = MainScreen.buttonCreate(700, 620, 28, 28, "resources/next_page.png", "resources/next_page.png", "");
        nextPage.addActionListener(e -> shopCardLayout.show(shopPanel, "Page 2"));
        page.add(nextPage, Integer.valueOf(2));
        allButtons.add(nextPage);

        return page;
    }

    private void showPopup(String imagePath) {
        for (JButton button : allButtons) {
            button.setEnabled(false);
        }

        JLayeredPane popup = new JLayeredPane();
        popup.setBounds(372, 86, 336, 579);
        popup.setOpaque(false);

        JLabel popupImage = new JLabel(new ImageIcon(imagePath));
        popupImage.setBounds(0, 0, 336, 579);
        popup.add(popupImage, Integer.valueOf(1));

        JButton closeButton = new JButton("Close");
        closeButton.setBounds(128, 540, 80, 30);
        closeButton.addActionListener(e -> {
            remove(popup);
            for (JButton button : allButtons) {
                button.setEnabled(true);
            }
            revalidate();
            repaint();
        });
        popup.add(closeButton, Integer.valueOf(2));

        add(popup, Integer.valueOf(5));
        revalidate();
        repaint();
    }

    private JLayeredPane createShopPage2() {
        JLayeredPane page = new JLayeredPane();
        page.setPreferredSize(new Dimension(1080, 750));

        JButton item7 = createShopItem("resources/item_image.png", 200, 130);
        page.add(item7, Integer.valueOf(1));
        allButtons.add(item7);

        JButton item8 = createShopItem("resources/item_image.png", 460, 130);
        page.add(item8, Integer.valueOf(1));
        allButtons.add(item8);

        JButton item9 = createShopItem("resources/item_image.png", 720, 130);
        page.add(item9, Integer.valueOf(1));
        allButtons.add(item9);

        JButton prevPage = MainScreen.buttonCreate(100, 650, 28, 28, "resources/prev_page.png", "resources/prev_page.png", "");
        prevPage.addActionListener(e -> shopCardLayout.show(shopPanel, "Page 1"));
        page.add(prevPage, Integer.valueOf(1));
        allButtons.add(prevPage);

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
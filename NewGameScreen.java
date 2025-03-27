import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout petCardLayout;  // For pet selection navigation
    private JPanel petPanel;           // For pet selection screens
    private CardLayout mainCardLayout; // For main navigation
    private JPanel mainPanel;          // For main screens

    public NewGameScreen(Font customFont, CardLayout mainCardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.mainCardLayout = mainCardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        // Set up internal pet selection navigation
        this.petCardLayout = new CardLayout();
        this.petPanel = new JPanel(petCardLayout);
        petPanel.setBounds(0, 0, 1080, 750);
        add(petPanel, Integer.valueOf(0));

        // Create the pet screens
        JLayeredPane firstPet = firstPet();
        JLayeredPane secondPet = secondPet();
        JLayeredPane thirdPet = thirdPet();

        petPanel.add(firstPet, "First Pet");
        petPanel.add(secondPet, "Second Pet");
        petPanel.add(thirdPet, "Third Pet");

        petCardLayout.show(petPanel, "First Pet");

        // Home button (uses main navigation)
        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        homeButton.addActionListener(e -> mainCardLayout.show(mainPanel, "Home"));
        add(homeButton, Integer.valueOf(3));

        setVisible(true);
    }

    private JLayeredPane backgroundScreen(String idCardPet, JLayeredPane screenSource) {
        ImageIcon background = new ImageIcon("resources/new_game.png");
        Image scaledBG = background.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        JLabel backgroundLabel = new JLabel(new ImageIcon(scaledBG));
        backgroundLabel.setBounds(0, 0, 1080, 750);
        screenSource.add(backgroundLabel, Integer.valueOf(0));

        ImageIcon idCard = new ImageIcon(idCardPet);
        int width = idCard.getIconWidth() - 1000;
        int height = idCard.getIconHeight() - 700;
        Image scaledID = idCard.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel idCardLabel = new JLabel(new ImageIcon(scaledID));
        idCardLabel.setBounds(35, 30, width, height);
        screenSource.add(idCardLabel, Integer.valueOf(4));

        return screenSource;
    }

    private JLayeredPane arrowButtons(JLayeredPane screenSource, int petNum) {
        ImageIcon rightArrowImage = new ImageIcon("resources/right_arrow.png");
        ImageIcon leftArrowImage = new ImageIcon("resources/left_arrow.png");
        JLabel rightArrowLabel = new JLabel(rightArrowImage);
        JLabel leftArrowLabel = new JLabel(leftArrowImage);
        rightArrowLabel.setBounds(868, 365, 32, 32);
        leftArrowLabel.setBounds(160, 365, 32, 32);

        if (petNum == 1) {
            JButton rightButton = createPetNavigationButton(850, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Second Pet");
            screenSource.add(rightButton, Integer.valueOf(4));
            screenSource.add(rightArrowLabel, Integer.valueOf(5));
        }
        else if (petNum == 2) {
            JButton rightButton = createPetNavigationButton(850, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Third Pet");
            JButton leftButton = createPetNavigationButton(145, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "First Pet");
            screenSource.add(rightButton, Integer.valueOf(4));
            screenSource.add(leftButton, Integer.valueOf(4));
            screenSource.add(rightArrowLabel, Integer.valueOf(5));
            screenSource.add(leftArrowLabel, Integer.valueOf(5));
        }
        else {
            JButton leftButton = createPetNavigationButton(145, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Second Pet");
            screenSource.add(leftButton, Integer.valueOf(4));
            screenSource.add(leftArrowLabel, Integer.valueOf(5));
        }
        return screenSource;
    }

    private JButton createPetNavigationButton(int x, int y, int width, int height, String defaultImg, String pressedImg, String cardName) {
        JButton button = MainScreen.buttonCreate(x, y, width, height, defaultImg, pressedImg, cardName);
        button.addActionListener(e -> petCardLayout.show(petPanel, cardName));
        return button;
    }

    private JLabel[] displayPopup(JLayeredPane sourceScreen) {
        // Overlay
        ImageIcon overlayIcon = new ImageIcon("resources/opacity.png");
        JLabel overlayLabel = new JLabel(overlayIcon);
        overlayLabel.setBounds(0, 0, 1080, 750);
        overlayLabel.setVisible(false);
        sourceScreen.add(overlayLabel, Integer.valueOf(5));

        // Popup
        ImageIcon popupIcon = new ImageIcon("resources/password_popup.png");
        int width = popupIcon.getIconWidth() - 1000;
        int height = popupIcon.getIconHeight() - 664;
        Image scaledPopup = popupIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        JLabel popUpLabel = new JLabel(new ImageIcon(scaledPopup));
        popUpLabel.setBounds(20, 66, width, height);
        popUpLabel.setVisible(false);
        sourceScreen.add(popUpLabel, Integer.valueOf(6));

        return new JLabel[]{overlayLabel, popUpLabel};
    }


    private JLayeredPane firstPet() {
        JLayeredPane firstPet = new JLayeredPane();
        firstPet.setPreferredSize(new Dimension(1080, 750));
        arrowButtons(firstPet, 1);
        backgroundScreen("resources/id_card_sprite1.png", firstPet);
        JLabel[] popupElements = displayPopup(firstPet); // Store overlay and popup labels

        // Choose button
        JButton chooseButton = MainScreen.buttonCreate(445, 620, 192, 64, "resources/button.png", "resources/button_clicked.png", "Choose");
        chooseButton.addActionListener(e -> showPopup(firstPet, popupElements[0], popupElements[1]));
        firstPet.add(chooseButton, Integer.valueOf(4));

        return firstPet;
    }

    private JLayeredPane secondPet() {
        JLayeredPane secondPet = new JLayeredPane();
        secondPet.setPreferredSize(new Dimension(1080, 750));
        arrowButtons(secondPet, 2);
        backgroundScreen("resources/id_card_sprite2.png", secondPet);
        JLabel[] popupElements = displayPopup(secondPet); // Store overlay and popup labels

        // Choose button
        JButton chooseButton = MainScreen.buttonCreate(445, 620, 192, 64, "resources/button.png", "resources/button_clicked.png", "Choose");
        chooseButton.addActionListener(e -> showPopup(secondPet, popupElements[0], popupElements[1]));
        secondPet.add(chooseButton, Integer.valueOf(4));

        return secondPet;
    }

    private JLayeredPane thirdPet() {
        JLayeredPane thirdPet = new JLayeredPane();
        thirdPet.setPreferredSize(new Dimension(1080, 750));
        arrowButtons(thirdPet, 3);
        backgroundScreen("resources/id_card_sprite3.png", thirdPet);
        JLabel[] popupElements = displayPopup(thirdPet); // Store overlay and popup labels

        // Choose button
        JButton chooseButton = MainScreen.buttonCreate(445, 620, 192, 64, "resources/button.png", "resources/button_clicked.png", "Choose");
        chooseButton.addActionListener(e -> showPopup(thirdPet, popupElements[0], popupElements[1]));
        thirdPet.add(chooseButton, Integer.valueOf(4));

        return thirdPet;
    }

    private void showPopup(JLayeredPane parentPane, JLabel overlayLabel, JLabel popUpLabel) {
        // Make popup visible
        overlayLabel.setVisible(true);
        popUpLabel.setVisible(true);

        // Back Button (closes popup)
        JButton backButton = MainScreen.buttonCreate(250, 440, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", ""); // Empty text
        backButton.setText("BACK"); // Set text explicitly
        backButton.setFont(customFont);
        backButton.setForeground(Color.decode("#7392B2"));
        backButton.setHorizontalTextPosition(SwingConstants.CENTER);
        backButton.setVerticalTextPosition(SwingConstants.CENTER);

        // Enter Button (navigates to InGame screen)
        JButton enterButton = MainScreen.buttonCreate(600, 440, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", ""); // Empty text
        enterButton.setText("ENTER"); // Set text explicitly
        enterButton.setFont(customFont);
        enterButton.setForeground(Color.decode("#7392B2"));
        enterButton.setHorizontalTextPosition(SwingConstants.CENTER);
        enterButton.setVerticalTextPosition(SwingConstants.CENTER);

        // Add action listeners
        backButton.addActionListener(e -> {
            hidePopup(parentPane, overlayLabel, popUpLabel, backButton, enterButton);
        });

        enterButton.addActionListener(e -> {
            hidePopup(parentPane, overlayLabel, popUpLabel, backButton, enterButton);
            mainCardLayout.show(mainPanel, "InGame");
        });

        // Add buttons to parent pane
        parentPane.add(backButton, Integer.valueOf(7));
        parentPane.add(enterButton, Integer.valueOf(7));
    }

    private void hidePopup(JLayeredPane parentPane, JLabel overlayLabel, JLabel popUpLabel, JButton backButton, JButton doneButton) {
        // Hide overlay and popup
        overlayLabel.setVisible(false);
        popUpLabel.setVisible(false);

        // Remove buttons
        parentPane.remove(backButton);
        parentPane.remove(doneButton);

        // Refresh UI
        parentPane.revalidate();
        parentPane.repaint();
    }
}
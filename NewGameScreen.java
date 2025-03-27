import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JLabel popUpLabel;
    private JButton backButton;
    private JButton doneButton;
    private JLabel overlayLabel;
    private JLabel nameLabel;

    public NewGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 0, 1080, 750);
        add(mainPanel, Integer.valueOf(0));

        // Create the pet screens
        JLayeredPane firstPet = firstPet();
        JLayeredPane secondPet = secondPet();
        JLayeredPane thirdPet = thirdPet();

        mainPanel.add(firstPet, "First Pet");
        mainPanel.add(secondPet, "Second Pet");
        mainPanel.add(thirdPet, "Third Pet");

        cardLayout.show(mainPanel, "First Pet");

        // home button (stays visible on all screens)
        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
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
            JButton rightButton = TutorialScreen.createButtonWithCardLayout(850, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Second Pet", cardLayout, mainPanel);
            screenSource.add(rightButton, Integer.valueOf(4));
            screenSource.add(rightArrowLabel, Integer.valueOf(5));
        }
        else if (petNum == 2) {
            JButton rightButton = TutorialScreen.createButtonWithCardLayout(850, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Third Pet", cardLayout, mainPanel);
            JButton leftButton = TutorialScreen.createButtonWithCardLayout(145, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "First Pet", cardLayout, mainPanel);
            screenSource.add(rightButton, Integer.valueOf(4));
            screenSource.add(leftButton, Integer.valueOf(4));
            screenSource.add(rightArrowLabel, Integer.valueOf(5));
            screenSource.add(leftArrowLabel, Integer.valueOf(5));
        }
        else {
            JButton leftButton = TutorialScreen.createButtonWithCardLayout(145, 350, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Second Pet", cardLayout, mainPanel);
            screenSource.add(leftButton, Integer.valueOf(4));
            screenSource.add(leftArrowLabel, Integer.valueOf(5));
        }
        return screenSource;
    }

    private void displayPopup(JLayeredPane sourceScreen){
        // overlay
        ImageIcon overlayIcon = new ImageIcon("resources/opacity.png");
        overlayLabel = new JLabel(overlayIcon);
        overlayLabel.setBounds(0, 0, 1080, 750);
        overlayLabel.setVisible(false);
        sourceScreen.add(overlayLabel, Integer.valueOf(5)); // Higher layer

        // popup
        ImageIcon popupIcon = new ImageIcon("resources/password_popup.png");
        int width = popupIcon.getIconWidth() - 1000;
        int height = popupIcon.getIconHeight() - 664;
        Image scaledPopup = popupIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        popUpLabel = new JLabel(new ImageIcon(scaledPopup));
        popUpLabel.setBounds(20, 66, width, height);
        popUpLabel.setVisible(false);
        sourceScreen.add(popUpLabel, Integer.valueOf(6)); // Even higher
    }

    private JLayeredPane firstPet() {
        JLayeredPane firstPet = new JLayeredPane();
        firstPet.setPreferredSize(new Dimension(1080, 750));
        arrowButtons(firstPet, 1);

        // normal components
        backgroundScreen("resources/id_card.png", firstPet);

        displayPopup(firstPet);

        // button
        JButton chooseButton = MainScreen.buttonCreate(445, 620, 192, 64, "resources/button.png", "resources/button_clicked.png", "Choose");
        chooseButton.addActionListener(e -> showPopup(firstPet));
        firstPet.add(chooseButton, Integer.valueOf(4));

        return firstPet;
    }

    private JLayeredPane secondPet() {
        JLayeredPane secondPet = new JLayeredPane();
        secondPet.setPreferredSize(new Dimension(1080, 750));
        arrowButtons(secondPet, 2);
        return backgroundScreen("resources/id_card.png", secondPet);
    }

    private JLayeredPane thirdPet() {
        JLayeredPane thirdPet = new JLayeredPane();
        thirdPet.setPreferredSize(new Dimension(1080, 750));
        arrowButtons(thirdPet, 3);
        return backgroundScreen("resources/id_card.png", thirdPet);
    }

    private void showPopup(JLayeredPane parentPane) {
        // Make popup visible
        overlayLabel.setVisible(true);
        popUpLabel.setVisible(true);

        // Back Button (closes popup)
        backButton = MainScreen.buttonCreate(250, 440, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "First Pet");
        backButton.setFont(customFont);
        backButton.setForeground(Color.decode("#7392B2"));
        backButton.addActionListener(e -> hidePopup(parentPane));
        parentPane.add(backButton, Integer.valueOf(7));

        // Done Button (closes popup and navigates to InGameScreen)
        doneButton = MainScreen.buttonCreate(600, 440, 192, 64,
                "resources/white_button.png", "resources/white_button_clicked.png", "InGame");
        doneButton.setText("DONE");  // Set button text if needed
        doneButton.setFont(customFont);
        doneButton.setForeground(Color.decode("#7392B2"));
        doneButton.setHorizontalTextPosition(SwingConstants.CENTER);
        doneButton.setVerticalTextPosition(SwingConstants.CENTER);
        doneButton.addActionListener(e -> {
            hidePopup(parentPane); // Close the popup
            cardLayout.show(mainPanel, "InGame"); // Navigate using CardLayout
        });
        parentPane.add(doneButton, Integer.valueOf(7));
    }

    private void hidePopup(JLayeredPane parentPane) {
        overlayLabel.setVisible(false);
        popUpLabel.setVisible(false);
        parentPane.remove(backButton);
        parentPane.remove(doneButton);
        parentPane.repaint();
    }
}
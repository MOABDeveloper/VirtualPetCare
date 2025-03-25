import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class NewGameScreen extends JLayeredPane {
    private Font customFont;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    // normal constructor
    public NewGameScreen(Font customFont) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));


        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 0, 1080, 750);
        add(mainPanel, Integer.valueOf(0));


        // create the layers
        JLayeredPane firstPet = firstPet();
//        JLayeredPane secondPet = secondPet();
//        JLayeredPane thirdPet = thirdPet();

        mainPanel.add(firstPet, "First Pet");
//        mainPanel.add(secondPet, "Second Pet");
//        mainPanel.add(thirdPet, "Third Pet");

        cardLayout.show(mainPanel, "First Pet");

        // home button
        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/button.png", "resources/button_clicked.png", "Home");
        add(homeButton, Integer.valueOf(3));

        setVisible(true);
    }

    // sets the background and the cardid
    private JLayeredPane backgroundScreen(String idCardPet, JLayeredPane screenSource) {
        ImageIcon background = new ImageIcon("resources/new_game.png");
        Image scaledBG = background.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledIconBG = new ImageIcon(scaledBG);
        JLabel backgroundLabel = new JLabel(scaledIconBG);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        screenSource.add(backgroundLabel, Integer.valueOf(0));


        ImageIcon idCard = new ImageIcon(idCardPet);
        int width = idCard.getIconWidth() - 1000;
        int height = idCard.getIconHeight() - 700;
        Image scaledID = idCard.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon idCardIcon = new ImageIcon(scaledID);

        JLabel idCardLabel = new JLabel(idCardIcon);
        idCardLabel.setBounds(35, 70, width, height);
        screenSource.add(idCardLabel, Integer.valueOf(4));

        return screenSource;
    }

    // first pet screen
    private JLayeredPane firstPet() {
        JLayeredPane firstPet = new JLayeredPane();
        firstPet.setPreferredSize(new Dimension(1080, 750));

        JButton rightButton = TutorialScreen.createButtonWithCardLayout(700, 250, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Second Pet");
        firstPet.add(rightButton, Integer.valueOf(4));

        ImageIcon rightArrowImage = new ImageIcon("resources/right_arrow.png");
        JLabel rightArrowLabel = new JLabel(rightArrowImage);
        rightArrowLabel.setBounds(880, 250, 32, 32);
        firstPet.add(rightArrowLabel, Integer.valueOf(5));

        return backgroundScreen("resources/id_card.png", firstPet);
    }
}
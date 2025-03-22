import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public TutorialScreen(Font customFont) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));

        // set up, create new screen
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 0, 1080, 750); // Set bounds for the mainPanel
        add(mainPanel, Integer.valueOf(1)); // Add mainPanel to the layered pane

        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/button.png", "resources/button_clicked.png", "Home");
        add(homeButton, Integer.valueOf(2));

        // creating screens
        JLayeredPane tutorialScreen = tutorialScreen();
        JLayeredPane feedingScreen = feedingScreen();

        // add screens into panel
        mainPanel.add(tutorialScreen, "Tutorial");
        mainPanel.add(feedingScreen, "Feeding");

        // Show the initial screen
        cardLayout.show(mainPanel, "Tutorial");

        setVisible(true);
    }

    private JLayeredPane tutorialScreen() {
        JLayeredPane tutorialScreen = new JLayeredPane();
        tutorialScreen.setPreferredSize(new Dimension(1080, 750));

        // Background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        tutorialScreen.add(backgroundLabel, Integer.valueOf(0));

        // Tutorial screen content
        ImageIcon tutorialImage = new ImageIcon("resources/tut.png");
        JLabel tutorialLabel = new JLabel(tutorialImage);
        tutorialLabel.setBounds(95, 70, 860, 645);
        tutorialScreen.add(tutorialLabel, Integer.valueOf(2));

        ImageIcon textBoxImage = new ImageIcon("resources/text_box.png");
        JLabel textBoxLabel = new JLabel(textBoxImage);
        textBoxLabel.setBounds(105, 100, 860, 540);
        tutorialScreen.add(textBoxLabel, Integer.valueOf(1));

        // button to switch to the feeding screen
        JButton rightArrow = createButtonWithCardLayout(800, 250, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Feeding");
        tutorialScreen.add(rightArrow, Integer.valueOf(1));

        ImageIcon rightArrowImage = new ImageIcon("resources/right_arrow.png");
        JLabel rightArrowLabel = new JLabel(rightArrowImage);
        rightArrowLabel.setBounds(817, 265, 32, 32);
        tutorialScreen.add(rightArrowLabel, Integer.valueOf(2));

        return tutorialScreen;
    }

    private JLayeredPane feedingScreen() {
        JLayeredPane feedingScreen = new JLayeredPane();
        feedingScreen.setPreferredSize(new Dimension(1080, 750));

        // Background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        feedingScreen.add(backgroundLabel, Integer.valueOf(0));

        ImageIcon tutorialImage = new ImageIcon("resources/tut.png");
        JLabel tutorialLabel = new JLabel(tutorialImage);
        tutorialLabel.setBounds(95, 70, 860, 645);
        feedingScreen.add(tutorialLabel, Integer.valueOf(2));

        ImageIcon textBoxImage = new ImageIcon("resources/text_box.png");
        JLabel textBoxLabel = new JLabel(textBoxImage);
        textBoxLabel.setBounds(105, 100, 860, 540);
        feedingScreen.add(textBoxLabel, Integer.valueOf(1));

        // Button to switch to the next screen
        JButton rightArrow = createButtonWithCardLayout(800, 250, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Giving Gift");
        feedingScreen.add(rightArrow, Integer.valueOf(1));

        ImageIcon rightArrowImage = new ImageIcon("resources/right_arrow.png");
        JLabel rightArrowLabel = new JLabel(rightArrowImage);
        rightArrowLabel.setBounds(817, 265, 32, 32);
        feedingScreen.add(rightArrowLabel, Integer.valueOf(2));

        // Button to switch back to the tutorial screen
        JButton leftArrow = createButtonWithCardLayout(20, 250, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Tutorial");
        feedingScreen.add(leftArrow, Integer.valueOf(1));

        ImageIcon leftArrowImage = new ImageIcon("resources/left_arrow.png");
        JLabel leftArrowLabel = new JLabel(leftArrowImage);
        leftArrowLabel.setBounds(17, 265, 32, 32);
        feedingScreen.add(leftArrowLabel, Integer.valueOf(2));

        return feedingScreen;
    }

    // Wrapper method to create buttons with CardLayout functionality
    private JButton createButtonWithCardLayout(int x, int y, int width, int height, String defaultImageSource, String pressedImageSource, String location) {
        // Call the existing buttonCreate function
        JButton button = MainScreen.buttonCreate(x, y, width, height, defaultImageSource, pressedImageSource, location);

        // Add ActionListener to handle screen switching
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, location); // Switch to the specified screen
            }
        });

        return button;
    }
}


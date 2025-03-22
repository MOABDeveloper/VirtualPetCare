import javax.swing.*;
import java.awt.*;

public class TutorialScreen extends JLayeredPane {
    private Font customFont;

    public TutorialScreen(Font customFont) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));

        // Background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("< HOME");
        label.setFont(customFont);
        label.setForeground(Color.WHITE);
        label.setBounds(60, 15, 500, 50);
        add(label, Integer.valueOf(1));

        ImageIcon tutorialImage = new ImageIcon("resources/tut.png");
        JLabel tutorialLabel = new JLabel(tutorialImage);
        tutorialLabel.setBounds(95, 70, 860, 645);
        add(tutorialLabel, Integer.valueOf(2));

        ImageIcon textBoxImage = new ImageIcon("resources/text_box.png");
        JLabel textBoxLabel = new JLabel(textBoxImage);
        textBoxLabel.setBounds(105, 100, 860, 540);
        add(textBoxLabel, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/button.png", "resources/button_clicked.png", "Home");
        add(homeButton, Integer.valueOf(1));

        JButton rightArrow = MainScreen.buttonCreate(800, 250, 64, 64, "resources/arrow_button.png", "resources/arrow_button_click.png", "Tutorial");
        add(rightArrow, Integer.valueOf(1));

        ImageIcon rightArrowImage = new ImageIcon("resources/right_arrow.png");
        JLabel rightArrowLabel = new JLabel(rightArrowImage);
        rightArrowLabel.setBounds(817, 265, 32, 32);
        add(rightArrowLabel, Integer.valueOf(2));
    }
}
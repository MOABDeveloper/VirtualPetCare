import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewGameScreen extends JLayeredPane {
    private Font customFont;

    public NewGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));

        // Load background image
        ImageIcon background = new ImageIcon("resources/character_selection.png");

        // Check if image loaded properly
        if (background.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Error: Image not found or couldn't be loaded");
            // Fallback - paint background red so you know there's an issue
            setBackground(Color.RED);
        }

        // Scale the image
        Image scaledBackground = background.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledBackground);

        // Create background label
        JLabel backgroundLabel = new JLabel(scaledIcon);
        backgroundLabel.setBounds(0, 0, 1080, 750);

        // Add background to the lowest layer (THIS is the JLayeredPane)
        add(backgroundLabel, Integer.valueOf(0));

        // Add other components
         JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        add(homeButton, Integer.valueOf(1));}
    //private JLayeredPane firstPet() {

    //}
}
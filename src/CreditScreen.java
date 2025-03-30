package src;
import javax.swing.*;
import java.awt.*;

// credit scrren
public class CreditScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;  // Changed from static
    private JPanel mainPanel;      // Changed from static

    public CreditScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        ImageIcon creditImage = new ImageIcon("resources/credit_screen.png");

        // Scale down the image if needed (same scaling code as before)
        JLabel creditLabel = new JLabel(creditImage);
        creditLabel.setBounds(0, 0, creditImage.getIconWidth(), creditImage.getIconHeight());
        add(creditLabel, Integer.valueOf(1));

        // Game screen content
        JLabel titleLabel = new JLabel("Credits - Group 19");
        titleLabel.setFont(customFont.deriveFont(28f));
        titleLabel.setForeground(Color.decode("#987e78"));
        titleLabel.setBounds(315, 30, 1000, 50);
        add(titleLabel, Integer.valueOf(2));

        JLabel subLabel = new JLabel("\"Virtual Pet\" is a project created for COMPSCI 2212 Winter 2025 at Western University");
        subLabel.setFont(customFont.deriveFont(12f));
        subLabel.setForeground(Color.decode("#d09b62"));
        subLabel.setBounds(70, 70, 1000, 50);
        add(subLabel, Integer.valueOf(4));

        // Fixed Home Button - uses the MainScreen.buttonCreate method
        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64,
                "resources/white_button.png",
                "resources/white_button_clicked.png",
                "Home");  // Changed to "Home" to match your main screen's name

        homeButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        add(homeButton, Integer.valueOf(4));
    }
}
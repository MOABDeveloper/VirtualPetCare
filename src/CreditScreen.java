package src;
import javax.swing.*;
import java.awt.*;

public class CreditScreen extends JLayeredPane {
    private Font customFont;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    public CreditScreen(Font customFont) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("Welcome to the credit screen!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        add(label, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton backButton = new JButton("HOME");
        backButton.setBounds(800, 70, 200, 50);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        add(backButton, Integer.valueOf(2));

        add(backButton, Integer.valueOf(1));
    }
}

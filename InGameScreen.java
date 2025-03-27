import javax.swing.*;
import java.awt.*;

public class InGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;  // Remove static
    private JPanel mainPanel;      // Remove static

    public InGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        add(backButton, Integer.valueOf(1));
    }
}
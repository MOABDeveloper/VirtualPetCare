import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ParentalControlScreen extends JLayeredPane {
    private Font customFont;
    public ParentalControlScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        ImageIcon password = new ImageIcon("resources/password_popup.png");
        JLabel passwordLabel = new JLabel(password);
        passwordLabel.setBounds(0, 0, 980, 650);
        add(passwordLabel, Integer.valueOf(2));
        /*JLabel label = new JLabel("Welcome to the parental control screen!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        add(label, Integer.valueOf(1)); */

        // Button to switch back to the home screen
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
            }
        });
        add(backButton, Integer.valueOf(1));
    }
}

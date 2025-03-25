//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//public class ParentalControlScreen extends JLayeredPane {
//    private Font customFont;
//    public ParentalControlScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
//        this.customFont = customFont;
//        setPreferredSize(new Dimension(1080, 750));
//
//        // background
//        ImageIcon background = new ImageIcon("resources/grid.png");
//        JLabel backgroundLabel = new JLabel(background);
//        backgroundLabel.setBounds(0, 0, 1080, 750);
//        add(backgroundLabel, Integer.valueOf(0));
//
//        // Game screen content
//        ImageIcon password = new ImageIcon("resources/password_popup.png");
//        int width = password.getIconWidth() -1000;
//        int height = password.getIconHeight() -700;
//        Image scaledPassword = password.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
//        ImageIcon passwordIcon = new ImageIcon(scaledPassword);
//
//        JLabel passwordLabel = new JLabel(passwordIcon);
//        passwordLabel.setBounds(50, 50, width, height);
//        add(passwordLabel, Integer.valueOf(1));
//
//        // Button to switch back to the home screen
//        JButton backButton = new JButton("Back to Home");
//        backButton.setBounds(200, 300, 200, 50);
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
//            }
//        });
//        add(backButton, Integer.valueOf(1));
//    }
//}

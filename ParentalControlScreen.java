import src.ParentalControl;

import javax.swing.*;
import java.awt.*;


public class ParentalControlScreen extends JLayeredPane {
    private Font customFont;

    public ParentalControlScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));


        // Add grid background
        ImageIcon gridBackground = new ImageIcon("resources/grid.png");
        Image scaledGrid = gridBackground.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        JLabel gridLabel = new JLabel(new ImageIcon(scaledGrid));
        gridLabel.setBounds(0, 0, 1080, 750);
        add(gridLabel, Integer.valueOf(0));

        // Add parental control window on top of grid
        ImageIcon windowBg = new ImageIcon("resources/parentalcontrol_background.png");
        Image scaledWindowBg = windowBg.getImage().getScaledInstance(1011, 720, Image.SCALE_SMOOTH); // Adjust size as needed
        JLabel windowLabel = new JLabel(new ImageIcon(scaledWindowBg));
        windowLabel.setBounds(0, 0, 1080, 750); // Centered-ish, tweak this if needed
        add(windowLabel, Integer.valueOf(1));


        // Back button
        JButton backButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        add(backButton, Integer.valueOf(1));

        // Labels
        JLabel totalPlayLabel = createLabel("TOTAL PLAY TIME:", 240, 245, 300, 40);
        JLabel totalPlayValue = createLabel("10 hrs", 300, 328, 150, 40);
        totalPlayValue.setFont(customFont.deriveFont(40f));
        JLabel avgPlayLabel = createLabel("AVERAGE PLAY TIME:", 220, 419, 300, 40);
        JLabel avgPlayValue = createLabel("0.4 hrs", 300, 498, 500, 40);

        add(totalPlayLabel, Integer.valueOf(2));
        add(totalPlayValue, Integer.valueOf(2));
        add(avgPlayLabel, Integer.valueOf(2));
        add(avgPlayValue, Integer.valueOf(2));

        // Buttons
        JButton resetStatsButton = createButton("RESET STATS", 600, 200, 200, 50);
        JButton setPlayTimeButton = createButton("SET PLAY TIME", 600, 270, 200, 50);
        JButton revivePetButton = createButton("REVIVE PET", 600, 340, 200, 50);

        add(resetStatsButton, Integer.valueOf(2));
        add(setPlayTimeButton, Integer.valueOf(2));
        add(revivePetButton, Integer.valueOf(2));

        setVisible(true);
    }

    private JLabel createLabel(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(customFont);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);
        return label;
    }

    private JButton createButton(String text, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(customFont);
        button.setBounds(x, y, width, height);
        return button;
    }
}

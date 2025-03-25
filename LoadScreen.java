import javax.swing.*;
import java.awt.*;

public class LoadScreen extends JLayeredPane {
    private Font customFont;
    private static CardLayout cardLayoutLoad;
    private static JPanel mainPanelLoad;

    public LoadScreen(Font customFont) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));


        // Game screen content
        JLabel loadText = new JLabel("LOAD");
        loadText.setFont(customFont);
        loadText.setForeground(Color.WHITE);
        Font resizedFont = customFont.deriveFont(Font.PLAIN, 30);
        loadText.setFont(resizedFont);
        loadText.setBounds(835, 78, 500, 50);
        add(loadText, Integer.valueOf(2));

        JButton backButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        add(backButton, Integer.valueOf(1));

        setVisible(true);
    }

    private JLayeredPane emptySaves(){
        JLayeredPane emptySaveScreen = new JLayeredPane();
        emptySaveScreen.setBounds(0, 0, 1080, 750);

        // load image
        ImageIcon loadImage = new ImageIcon("resources/save_load_screen.png");
        Image scaledLoad = loadImage.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledLoadIcon = new ImageIcon(scaledLoad);

        // Create background loadText
        JLabel loadLabel = new JLabel(scaledLoadIcon);
        loadLabel.setBounds(0, 0, 1080, 750);

        // Add background to the lowest layer
        emptySaveScreen.add(loadLabel, Integer.valueOf(2));

        return emptySaveScreen;
    }
}




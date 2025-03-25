import javax.swing.*;
import java.awt.*;

public class LoadScreen extends JLayeredPane {
    private Font customFont;
    private static CardLayout cardLayout;
    private static JPanel mainPanel;

    public LoadScreen(Font customFont) {
        this.customFont = customFont;
        setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // actual file thing
        ImageIcon fileLoad = new ImageIcon("resources/save_load_screen.png");
        Image scaledLoad = fileLoad.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledLoadIcon = new ImageIcon(scaledLoad);

        // create label
        JLabel loadLabel = new JLabel(scaledLoadIcon);
        loadLabel.setBounds(0, 0, 1080, 750);

        // add above grid
        add(loadLabel, Integer.valueOf(1));

        // back button:
        JButton homeButton = MainScreen.buttonCreate(20, 10, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        add(homeButton, Integer.valueOf(2));

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBounds(0, 0, 1080, 750);
        add(mainPanel, Integer.valueOf(0));

    }
}
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class InGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;  // Remove static
    private JPanel mainPanel;      // Remove static

    public InGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel) {
        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));

        setBackground();
        healthBars();
        commandButtons();
        spriteGifs();

        JButton backButton = MainScreen.buttonCreate(800,70, 70,70, "resources/home_button.png", "resources/home_button_clicked.png", "");
        backButton.setBounds(990, 15, 64, 64);
        ImageIcon homeIcon = new ImageIcon("resources/home_icon.png");
        JLabel homeIconLabel = new JLabel(homeIcon);
        homeIconLabel.setBounds(990, 15, 64, 64);
        add(homeIconLabel,Integer.valueOf(3));
        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Home"));
        add(backButton, Integer.valueOf(2));
    }

    private void setBackground() {
        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        add(backgroundLabel, Integer.valueOf(0));

        // actual file thing
        ImageIcon inGameWindow = new ImageIcon("resources/ingamescreen_dark.png");
        Image scaledWindow = inGameWindow.getImage().getScaledInstance(1080, 750, Image.SCALE_SMOOTH);
        ImageIcon scaledWindowIcon = new ImageIcon(scaledWindow);

        // create label
        JLabel windowLabel = new JLabel(scaledWindowIcon);
        windowLabel.setBounds(-13, -10, 1080, 750);

        // add above grid
        add(windowLabel, Integer.valueOf(1));

        // save icon
        JButton saveButton = MainScreen.buttonCreate(17, 15, 50, 50, "resources/save_load_1.png", "resources/save_load_clicked_1.png", "Save");
        add(saveButton, Integer.valueOf(2));

        // load button
        JButton loadButton = MainScreen.buttonCreate(90, 15, 50, 50, "resources/save_load_1.png", "resources/save_load_clicked_1.png", "Load");
        add(loadButton, Integer.valueOf(2));
    }

    private void healthBars(){
        ImageIcon healthBar = new ImageIcon("resources/health_bar.png");
        JLabel healthBarLabel = new JLabel(healthBar);
        healthBarLabel.setBounds(-145, -30, 350, 350);
        add(healthBarLabel, Integer.valueOf(2));

        ImageIcon sleepBar = new ImageIcon("resources/sleep_bar.png");
        JLabel sleepBarLabel = new JLabel(sleepBar);
        sleepBarLabel.setBounds(-70, -30,350, 350);
        add(sleepBarLabel, Integer.valueOf(2));

        ImageIcon hungerBar = new ImageIcon("resources/hunger_bar.png");
        JLabel hungerBarLabel = new JLabel(hungerBar);
        hungerBarLabel.setBounds(-145, 190, 350, 350);
        add(hungerBarLabel, Integer.valueOf(2));

        ImageIcon happiness_bar = new ImageIcon("resources/happiness_bar.png");
        JLabel happinessBarLabel = new JLabel(happiness_bar);
        happinessBarLabel.setBounds(-70, 190, 350, 350);
        add(happinessBarLabel, Integer.valueOf(2));
    }

    private void commandButtons(){
        JButton shopButton = MainScreen.buttonCreate(70, 500,128,128, "resources/command_button.png", "resources/command_button_clicked.png", "InGame");
        add(shopButton, Integer.valueOf(2));
    }

    private void spriteGifs() {
        ImageIcon gifIcon = new ImageIcon("resources/sprite1_idle.gif");
        JLabel gifLabel = new JLabel(gifIcon);
        gifLabel.setBounds(300, 30, 622, 632);
        add(gifLabel, Integer.valueOf(3));

    }
}
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import src.GameData;
import src.Pet;


import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class InGameScreen extends JLayeredPane {
    private Font customFont;
    private CardLayout cardLayout;  // Remove static
    private JPanel mainPanel;
    // define variable for the height of the bar
    private JProgressBar progressBar;
    private Pet pet;
    private static String health_red = "#A94337";

    private Timer statDecayTimer;


    public InGameScreen(Font customFont, CardLayout cardLayout, JPanel mainPanel, GameData gameData) {
        this.customFont = customFont;
        this.cardLayout = cardLayout;
        this.mainPanel = mainPanel;
        setPreferredSize(new Dimension(1080, 750));
        setBackground();
        this.pet = gameData.getPet();


        // Create and position progress bar
        progressBar = new JProgressBar(JProgressBar.VERTICAL, 0,pet.getMaxHealth()); //min,max
        progressBar.setBounds(26, 81, 25, 135); // (x, y, width, height)
        progressBar.setValue(pet.getHealth()); // Initial value
        progressBar.setStringPainted(false); // Show percentage text

        // Paint the progress bar colors
        updateProgressBarColor(progressBar,pet.getHealth());

        //Set Background Color
        progressBar.setBackground(Color.decode("#f9e6c6"));
        progressBar.repaint();

        progressBar.revalidate();

        add(progressBar, Integer.valueOf(1)); // Add above other elements



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

    public static void updateProgressBarColor(JProgressBar progressBar, int health) {
        // Change color based on health percentage
        if (health <= 10) {
            progressBar.setForeground(Color.decode("#A94337"));
        } else if (health <= 20) {
            progressBar.setForeground(Color.decode("#B54F32"));
        } else if (health <= 30) {
            progressBar.setForeground(Color.decode("#C05C2E"));
        } else if (health <= 40) {
            progressBar.setForeground(Color.decode("#CB6829"));
        } else if (health <= 50) {
            progressBar.setForeground(Color.decode("#D67524"));
        } else if (health <= 60) {
            progressBar.setForeground(Color.decode("#E1821F"));
        } else if (health <= 70) {
            progressBar.setForeground(Color.decode("#EC8E1A"));
        } else if (health <= 80) {
            progressBar.setForeground(Color.decode("#F79B15"));
        } else if (health <= 90) {
            progressBar.setForeground(Color.decode("#83B52B"));
        } else {
            progressBar.setForeground(Color.decode("#37A943"));
        }

        progressBar.repaint();
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
        ImageIcon loadIcon = new ImageIcon("resources/load_button.png");
        JLabel loadIconLabel = new JLabel(loadIcon);
        loadIconLabel.setBounds(102, 27, 28, 24);
        add(loadIconLabel,Integer.valueOf(3));
        add(loadButton, Integer.valueOf(2));

        // command buttons:
        JButton playButton = MainScreen.buttonCreate(70,70, 116, 75, "resources/command_button_scaled.png", "resources/play_button_clicked.png", "");
        add(playButton, Integer.valueOf(3));
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

    public void stopDecayTimer() {
        if (statDecayTimer != null) {
            statDecayTimer.stop();
        }
    }

}
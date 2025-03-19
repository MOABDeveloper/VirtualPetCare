import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    MyFrame() {
        // actual screen
        this.setTitle("Virtual Pet");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1080, 750);

        // cardLayout to switch screens
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // create screens
        JPanel homeScreen = createMainScreen();
        JLayeredPane tutorialScreen = createTutorialScreen();
        JLayeredPane newGameScreen = createNewGameScreen();

        // add screens to the main panel
        mainPanel.add(homeScreen, "Home");
        mainPanel.add(tutorialScreen, "Tutorial");
        mainPanel.add(newGameScreen, "New Game");
//        mainPanel.add(newGameScreen, "Load");
//        mainPanel.add(newGameScreen, "Credits");
//        mainPanel.add(newGameScreen, "Parental Controls");

        // add main panel to the frame
        this.add(mainPanel);

        // show the home screen by default
        cardLayout.show(mainPanel, "Home");

        // image icon
        ImageIcon iconImage = new ImageIcon("resources/Purple.png");
        this.setIconImage(iconImage.getImage());

        for (Component c : mainPanel.getComponents()) {
            System.out.println("Existing panel: " + c.getClass().getName());
        }

        this.setVisible(true);
    }

    // Displays button based on characteristics
    private JButton buttonCreate(int x, int y, int width, int height, String imageSource, String location) {
        ImageIcon buttonImage = new ImageIcon(imageSource);
        JButton buttonLabel = new JButton(buttonImage);
        buttonLabel.setBounds(x, y, width, height);

        // make it look like a button
        buttonLabel.setBorderPainted(false);
        buttonLabel.setContentAreaFilled(false);
        buttonLabel.setFocusPainted(false);

        buttonLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Clicked button: " + location);
                cardLayout.show(mainPanel, location);
            }
        });

        return (JButton) buttonLabel;
    }

    private JPanel createMainScreen() {
        // layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // windows + icon image
        ImageIcon windowsImage = new ImageIcon("resources/windowsion.png");
        int width = windowsImage.getIconWidth() - 200;
        int height = windowsImage.getIconHeight() - 200;
        Image scaledWindow = windowsImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon windowIcon = new ImageIcon(scaledWindow);

        JLabel windowsLabel = new JLabel(windowIcon);

        windowsLabel.setBounds(70, 35, width, height);

        layeredPane.add(windowsLabel, Integer.valueOf(1));


        // buttons
        JButton tutorialButton = buttonCreate(-200, -11, 1080, 750, "resources/button.png", "Tutorial");
        layeredPane.add(tutorialButton, Integer.valueOf(2));

        JButton newGameButton = buttonCreate(-200, 80, 1080, 750, "resources/Default.png", "New Game");
        layeredPane.add(newGameButton, Integer.valueOf(2));

        JButton loadButton = buttonCreate(-10, 80, 1080, 750, "resources/button.png", "Load");
        layeredPane.add(loadButton, Integer.valueOf(2));

        JButton creditButton = buttonCreate(-10, -10, 1080, 750, "resources/button.png", "Credits");
        layeredPane.add(creditButton, Integer.valueOf(2));

//        JButton parentalButton = buttonCreate(-10, 35, 1080, 750, "resources/button.png");
//        parentalButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                cardLayout.show(mainPanel,"Parental Controls");
//            }
//        });
//        layeredPane.add(parentalButton, Integer.valueOf(2));

        // wrap the layered pane in a JPanel
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(layeredPane, BorderLayout.CENTER);
        return homePanel;
    }

    private JLayeredPane createNewGameScreen(){
        JLayeredPane newGameScreen = new JLayeredPane();
        newGameScreen.setPreferredSize(new Dimension(1080, 750));

        // background:
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        newGameScreen.add(backgroundLabel, Integer.valueOf(0));

        JLabel label = new JLabel("Choose a pet!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        newGameScreen.add(label, Integer.valueOf(1));

        newGameScreen.setVisible(true);
        return newGameScreen;
    }

    private JLayeredPane createTutorialScreen() {
        // layered pane
        JLayeredPane layered = new JLayeredPane();
        layered.setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        layered.add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("Welcome to the Tutorial!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        layered.add(label, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
            }
        });
        layered.add(backButton, Integer.valueOf(1));

        return layered;
    }

}
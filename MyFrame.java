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
        JLayeredPane homeScreen = createMainScreen();
        JLayeredPane tutorialScreen = createTutorialScreen();
        JLayeredPane newGameScreen = createNewGameScreen();
        JLayeredPane loadScreen = createLoadScreen();
        JLayeredPane creditScreen = createCreditScreen();
        JLayeredPane parentalControl = createParentalControlScreen();

        // add screens to the main panel
        mainPanel.add(homeScreen, "Home");
        mainPanel.add(tutorialScreen, "Tutorial");
        mainPanel.add(newGameScreen, "New Game");
        mainPanel.add(loadScreen, "Load");
        mainPanel.add(creditScreen, "Credit");
        mainPanel.add(parentalControl, "Parental Control");


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
    private JButton buttonCreate(int x, int y, int width, int height, String defaultImageSource, String pressedImageSource, String location) {
        ImageIcon defaultImage = new ImageIcon(defaultImageSource);
        ImageIcon pressedImage = new ImageIcon(pressedImageSource);

        JButton buttonLabel = new JButton(defaultImage);
        buttonLabel.setBounds(x, y, width, height);

        // make it look like a button
        buttonLabel.setBorderPainted(false);
        buttonLabel.setContentAreaFilled(false);
        buttonLabel.setFocusPainted(false);

        // Add MouseListener for press/release effect
        buttonLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Change to the "pressed" image when mouse is pressed
                buttonLabel.setIcon(pressedImage);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Revert to the default image when mouse is released
                buttonLabel.setIcon(defaultImage);
            }
        });

        // Add ActionListener for button functionality
        buttonLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, location);
            }
        });

        return buttonLabel;
    }

    private JLayeredPane createMainScreen() {
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
        JButton tutorialButton = buttonCreate(240, 430, 192, 64, "resources/button.png", "resources/button_clicked.png", "Tutorial");
        layeredPane.add(tutorialButton, Integer.valueOf(2));

        JButton newGameButton = buttonCreate(240, 340, 192, 64, "resources/button.png", "resources/button_clicked.png", "New Game");
        layeredPane.add(newGameButton, Integer.valueOf(2));

        JButton loadButton = buttonCreate(450, 340, 192, 64, "resources/button.png", "resources/button_clicked.png", "Load");
        layeredPane.add(loadButton, Integer.valueOf(2));

        JButton creditButton = buttonCreate(450, 430, 192, 64, "resources/button.png", "resources/button_clicked.png", "Credit");
        layeredPane.add(creditButton, Integer.valueOf(2));

        JButton parentalControlButton = buttonCreate(850, 620, 192, 64, "resources/button.png", "resources/button_clicked.png", "Parental Control");
        layeredPane.add(parentalControlButton, Integer.valueOf(2));

        return layeredPane;
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

    private JLayeredPane createNewGameScreen() {
        // layered pane
        JLayeredPane newgame = new JLayeredPane();
        newgame.setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        newgame.add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("Welcome to the New Game Screen!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        newgame.add(label, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
            }
        });
        newgame.add(backButton, Integer.valueOf(1));

        return newgame;
    }

    private JLayeredPane createLoadScreen() {
        // layered pane
        JLayeredPane loadgame = new JLayeredPane();
        loadgame.setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        loadgame.add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("Welcome to the load game screen!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        loadgame.add(label, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
            }
        });
        loadgame.add(backButton, Integer.valueOf(1));

        return loadgame;
    }

    private JLayeredPane createCreditScreen(){
        // layered pane
        JLayeredPane loadgame = new JLayeredPane();
        loadgame.setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        loadgame.add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("Welcome to the credit screen!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        loadgame.add(label, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
            }
        });
        loadgame.add(backButton, Integer.valueOf(1));

        return loadgame;
    }

    private JLayeredPane createParentalControlScreen(){
        // layered pane
        JLayeredPane loadgame = new JLayeredPane();
        loadgame.setPreferredSize(new Dimension(1080, 750));

        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        loadgame.add(backgroundLabel, Integer.valueOf(0));

        // Game screen content
        JLabel label = new JLabel("Welcome to the parental control screen!");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(200, 200, 500, 50);
        loadgame.add(label, Integer.valueOf(1));

        // Button to switch back to the home screen
        JButton backButton = new JButton("Back to Home");
        backButton.setBounds(200, 300, 200, 50);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Home"); // Switch back to the home screen
            }
        });
        loadgame.add(backButton, Integer.valueOf(1));

        return loadgame;
    }

}

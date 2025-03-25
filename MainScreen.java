import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;

public class MainScreen extends JFrame {
    private static CardLayout cardLayout;
    private static JPanel mainPanel;
    private static Font customFont;
    private static JLabel passwordLabel;
    private static JLabel overlayLabel;
    private static TutorialScreen tutorialScreen; // Store the TutorialScreen instance


    MainScreen() {
        // Load custom font
        try {
            // loading font from resources folder
            InputStream fontStream = getClass().getResourceAsStream("/resources/Early GameBoy.ttf");
            if (fontStream == null) {
                throw new IOException("Font file not found!");
            }
            customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream).deriveFont(16f); // def size
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
            // if font fails to load, go back to default
            customFont = new Font("Arial", Font.PLAIN, 24);
        }
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
        tutorialScreen = new TutorialScreen(customFont);
        JLayeredPane newGameScreen = new NewGameScreen(customFont, cardLayout, mainPanel);
        JLayeredPane loadScreen = new LoadScreen(customFont, cardLayout, mainPanel);
        JLayeredPane creditScreen = new CreditScreen(customFont, cardLayout, mainPanel);

        // add screens to the main panel
        mainPanel.add(homeScreen, "Home");
        mainPanel.add(tutorialScreen, "Tutorial");
        mainPanel.add(newGameScreen, "New Game");
        mainPanel.add(loadScreen, "Load");
        mainPanel.add(creditScreen, "Credit");


        // add main panel to the frame
        this.add(mainPanel);

        // show the home screen by default
        cardLayout.show(mainPanel, "Home");

        // image icon
        ImageIcon iconImage = new ImageIcon("resources/Purple.png");
        this.setIconImage(iconImage.getImage());


        this.setVisible(true);
    }

    // Displays button based on characteristics
    static JButton buttonCreate(int x, int y, int width, int height, String defaultImageSource, String pressedImageSource, String location) {
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

        // ActionListener for button functionality
        buttonLabel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, location);
            }
        });

        return buttonLabel;
    }

    private static JLabel buttonText(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(customFont);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);

        return label;

    }

    public static JLayeredPane createMainScreen() {
        // layered pane
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(1080, 750));


        // background
        ImageIcon background = new ImageIcon("resources/grid.png");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setBounds(0, 0, 1080, 750);
        layeredPane.add(backgroundLabel, Integer.valueOf(0));

        // windows + icon image
        ImageIcon windowsImage = new ImageIcon("resources/windowsicon.png");
        int width = windowsImage.getIconWidth() + 100;
        int height = windowsImage.getIconHeight() + 100;
        Image scaledWindow = windowsImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        ImageIcon windowIcon = new ImageIcon(scaledWindow);

        JLabel windowsLabel = new JLabel(windowIcon);
        windowsLabel.setBounds(-66, -60, width, height);
        layeredPane.add(windowsLabel, Integer.valueOf(1));

        ImageIcon overlayImage = new ImageIcon("resources/opacity.png"); // Replace with your image path
        overlayLabel = new JLabel(overlayImage);
        overlayLabel.setBounds(0, 0, 1080, 750); // Adjust bounds as needed
        overlayLabel.setVisible(false);
        layeredPane.add(overlayLabel, Integer.valueOf(2));

        // buttons
        JButton tutorialButton = buttonCreate(240, 430, 192, 64, "resources/button.png", "resources/button_clicked.png", "Tutorial");
        tutorialButton.addActionListener(e -> tutorialScreen.resetToGiveGift());
        cardLayout.show(mainPanel, "Tutorial");
        JLabel tutorialLabel = buttonText("Tutorial", 270, 430, 192, 64);
        layeredPane.add(tutorialLabel, Integer.valueOf(2));
        layeredPane.add(tutorialButton, Integer.valueOf(2));

        JButton newGameButton = buttonCreate(240, 340, 192, 64, "resources/button.png", "resources/button_clicked.png", "New Game");
        JLabel newGameLabel = buttonText("New Game", 275, 340, 192, 64);
        layeredPane.add(newGameLabel, Integer.valueOf(2));
        layeredPane.add(newGameButton, Integer.valueOf(2));


        JButton loadButton = buttonCreate(450, 340, 192, 64, "resources/button.png", "resources/button_clicked.png", "Load");
        JLabel loadLabel = buttonText("Load", 510, 340, 192, 64);
        layeredPane.add(loadLabel, Integer.valueOf(2));
        layeredPane.add(loadButton, Integer.valueOf(2));


        JButton creditButton = buttonCreate(450, 430, 192, 64, "resources/button.png", "resources/button_clicked.png", "Credit");
        JLabel creditLabel = buttonText("Credits", 490, 430, 192, 64);
        layeredPane.add(creditLabel, Integer.valueOf(2));
        layeredPane.add(creditButton, Integer.valueOf(2));

        JButton parentalControlButton = buttonCreate(850, 620, 192, 64, "resources/button.png", "resources/button_clicked.png", "");
        parentalControlButton.addActionListener(e -> showPasswordPopup(layeredPane));
        layeredPane.add(parentalControlButton, Integer.valueOf(2));


        ImageIcon passwordIcon = new ImageIcon("resources/password_popup.png"); // actual pop-up
        int desiredWidth = 1000;
        int desiredHeight = 664;

        Image scaledPassword = passwordIcon.getImage().getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        passwordLabel = new JLabel(new ImageIcon(scaledPassword));
        passwordLabel.setBounds(20, 66, desiredWidth, desiredHeight);
        passwordLabel.setVisible(false);
        layeredPane.add(passwordLabel, Integer.valueOf(3)); // move above everything else

        return layeredPane;
    }

    private static void showPasswordPopup(JLayeredPane parentPane) {
        passwordLabel.setVisible(true);
        overlayLabel.setVisible(true);


        // Create "Back" button (only visible when popup is visible)
        JButton backButton = buttonCreate(250,  400, 192, 64, "resources/button_parental_control.png", "resources/button_clicked_parental_controls.png", "Home");
        backButton.setText("Back");
        backButton.setFont(customFont);
        backButton.setForeground(Color.decode("#7392B2"));
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setVerticalTextPosition(JButton.CENTER);
        backButton.setVisible(true);

        // Create "Done" button
        JButton doneButton = buttonCreate(600, 400, 192, 64, "resources/button_parental_control.png", "resources/button_clicked_parental_controls.png", "");
        doneButton.setText("ENTER");
        doneButton.setFont(customFont);
        doneButton.setForeground(Color.decode("#7392B2"));
        doneButton.setHorizontalTextPosition(JButton.CENTER);
        doneButton.setVerticalTextPosition(JButton.CENTER);
        doneButton.setVisible(true);

        // Add action listeners
        backButton.addActionListener(e -> {
            passwordLabel.setVisible(false);
            overlayLabel.setVisible(false); // Hide overlay when closing popup
            parentPane.remove(backButton);
            parentPane.remove(doneButton);
            parentPane.repaint();
        });

        doneButton.addActionListener(e -> {
            passwordLabel.setVisible(false);
            overlayLabel.setVisible(false); // Hide overlay when closing popup
            parentPane.remove(backButton);
            parentPane.remove(doneButton);
            parentPane.repaint();
            // Add your logic for when "Done" is clicked
        });

        // Add buttons to parentPane (on a high layer)
        parentPane.add(backButton, Integer.valueOf(4));
        parentPane.add(doneButton, Integer.valueOf(4));
        parentPane.repaint();
    }
}

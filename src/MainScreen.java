package src;

import javax.sound.sampled.*;
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
    private static ParentalControl parentalControl;
    private static Clip buttonClickSound;

    private static InGameScreen inGameScreen;
    private static PlayerInventory playerInventory;

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
        try {
            InputStream soundStream = getClass().getResourceAsStream("/resources/button_clicked.wav");
            if (soundStream != null) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundStream);
                buttonClickSound = AudioSystem.getClip();
                buttonClickSound.open(audioInputStream);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
            System.out.println("Could not load button click sound");
        }

//        MusicPlayer.playBackgroundMusic("resources/verdanturf.wav");
//        MusicPlayer.setVolume(0.05f);
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
        JLayeredPane loadScreen = new LoadScreen(customFont, mainPanel, cardLayout);
        JLayeredPane creditScreen = new CreditScreen(customFont, cardLayout, mainPanel);
        //InGameScreen inGameScreen = new InGameScreen(customFont, cardLayout, mainPanel,);
        Store store = new Store(); // Create Store instance



        // add screens to the main panel
        mainPanel.add(homeScreen, "Home");
        mainPanel.add(tutorialScreen, "Tutorial");
        mainPanel.add(newGameScreen, "New Game");
//      mainPanel.add(loadScreen, "Load");
        mainPanel.add(creditScreen, "Credit");
        //mainPanel.add(inGameScreen, "InGame");

        //ATTEMPTED TO ADD MOHAMMED-KAM
        parentalControl = GameDataManager.loadParentalControlSettings();
        JLayeredPane parentalControlScreen = new ParentalControlScreen(customFont, cardLayout, mainPanel,parentalControl);
        mainPanel.add(parentalControlScreen, "ParentalControlScreen");

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
    public static JButton buttonCreate(int x, int y, int width, int height, String defaultImageSource, String pressedImageSource, String location) {
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
                playButtonClickSound(); // Moved this to be the first thing that happens

                if (location.equals("Load")) {
                    // Remove old LoadScreen if it exists (optional but clean)
                    for (Component comp : mainPanel.getComponents()) {
                        if (comp instanceof LoadScreen) {
                            mainPanel.remove(comp);
                            break;
                        }
                    }

                    // Rebuild LoadScreen with updated data
                    JLayeredPane refreshedLoadScreen = new LoadScreen(customFont, mainPanel, cardLayout);
                    mainPanel.add(refreshedLoadScreen, "Load");
                }

                cardLayout.show(mainPanel, location);
            }
        });

        return buttonLabel;
    }

    private static void playButtonClickSound() {
        if (buttonClickSound != null) {
            buttonClickSound.setFramePosition(0); // Rewind to the beginning
            buttonClickSound.start();
        }
    }

    private static JLabel buttonText(String text, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(customFont);
        label.setForeground(Color.WHITE);
        label.setBounds(x, y, width, height);

        return label;

    }

    private static JLayeredPane createMainScreen() {
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

        //The main art on the screen
        ImageIcon main_art = new ImageIcon("resources/MainScreenImg.png");
        JLabel artLabel = new JLabel(main_art);
        artLabel.setBounds(512, 127, main_art.getIconWidth(), main_art.getIconHeight()); // Set proper x,y coordinates
        layeredPane.add(artLabel, Integer.valueOf(2));


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

        // Password input field
        JTextField passwordField = new JTextField();

        //password text box
        passwordField.setBounds(210, 335, 650, 40);
        parentPane.add(passwordField, Integer.valueOf(4));
        passwordField.requestFocusInWindow();

        // "Back" button
        JButton backButton = buttonCreate(250, 400, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "Home");
        backButton.setText("Back");
        backButton.setFont(customFont);
        backButton.setForeground(Color.decode("#7392B2"));
        backButton.setHorizontalTextPosition(JButton.CENTER);
        backButton.setVerticalTextPosition(JButton.CENTER);
        backButton.setVisible(true);

        // "Enter" button
        JButton doneButton = buttonCreate(600, 400, 192, 64, "resources/white_button.png", "resources/white_button_clicked.png", "");
        doneButton.setText("ENTER");
        doneButton.setFont(customFont);
        doneButton.setForeground(Color.decode("#7392B2"));
        doneButton.setHorizontalTextPosition(JButton.CENTER);
        doneButton.setVerticalTextPosition(JButton.CENTER);
        doneButton.setVisible(true);

        // Add action listeners
        backButton.addActionListener(e -> {
            passwordLabel.setVisible(false);
            overlayLabel.setVisible(false);
            parentPane.remove(backButton);
            parentPane.remove(doneButton);
            parentPane.remove(passwordField);
            parentPane.repaint();
        });

        doneButton.addActionListener(e -> {
            String enteredPassword = passwordField.getText();

            if (parentalControl.authenticate(enteredPassword)) { // Replace with your secure password
                cardLayout.show(mainPanel, "ParentalControlScreen");
            } else {
                JOptionPane.showMessageDialog(parentPane, "Incorrect password!", "Access Denied", JOptionPane.ERROR_MESSAGE);
            }

            passwordLabel.setVisible(false);
            overlayLabel.setVisible(false);
            parentPane.remove(backButton);
            parentPane.remove(doneButton);
            parentPane.remove(passwordField);
            parentPane.repaint();
        });

        parentPane.add(backButton, Integer.valueOf(4));
        parentPane.add(doneButton, Integer.valueOf(4));
        parentPane.repaint();
    }

    public static void showInGameScreen(GameData gameData, String saveFilePath) {
        // Remove old instance if it exists
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof InGameScreen) {
                mainPanel.remove(comp);
                break;
            }
        }

        // Create new InGameScreen and add it
        inGameScreen = new InGameScreen(customFont, cardLayout, mainPanel, gameData, saveFilePath);
        mainPanel.add(inGameScreen, "InGame");

        // Ensure StoreScreen is properly initialized
        Store store = new Store();
        JLayeredPane shopScreen = new StoreScreen(customFont, cardLayout, mainPanel, store, gameData);

        // Remove existing shop screen if needed
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof StoreScreen) {
                mainPanel.remove(comp);
                break;
            }
        }

        mainPanel.add(shopScreen, "Shop");

        // Switch to InGame screen
        cardLayout.show(mainPanel, "InGame");
    }

    public static ParentalControl getParentalControl() {
        return parentalControl;
    }




//    public static void showInGameScreen(GameData gameData) {
//        if (inGameScreen != null) {
//            inGameScreen.stopDecayTimer();
//            mainPanel.remove(inGameScreen);
//        }
//
//        inGameScreen = new InGameScreen(customFont, cardLayout, mainPanel, gameData);
//        mainPanel.add(inGameScreen, "InGame");
//
//        // ✅ Add or replace the store screen with correct gameData
//        Store store = new Store();
//        JLayeredPane shopScreen = new StoreScreen(customFont, cardLayout, mainPanel, store, gameData);
//
//        // Remove existing shop screen if needed (cleaner)
//        for (Component comp : mainPanel.getComponents()) {
//            if (comp instanceof StoreScreen) {
//                mainPanel.remove(comp);
//                break;
//            }
//        }
//
//        mainPanel.add(shopScreen, "Shop");
//
//        cardLayout.show(mainPanel, "InGame");
//    }



}
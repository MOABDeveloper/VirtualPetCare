package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class KeyboardShortcuts {
    private final InGameScreen inGameScreen;
    private final JPanel mainPanel;
    private final CardLayout cardLayout;
    private final Font customFont;
    private final GameData gameData;

    public KeyboardShortcuts(InGameScreen inGameScreen, JPanel mainPanel, CardLayout cardLayout, Font customFont, GameData gameData) {
        this.inGameScreen = inGameScreen;
        this.mainPanel = mainPanel;
        this.cardLayout = cardLayout;
        this.customFont = customFont;
        this.gameData = gameData;
    }

    public void setupKeyBindings() {
        InputMap userInput = inGameScreen.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = inGameScreen.getActionMap();


        // Play - P key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "playWithPet");
        actionMap.put("playWithPet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(390, 550);
            }
        });

        // Sleep - Z key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "Sleep");
        actionMap.put("Sleep", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(150, 500);
            }
        });

        // Gift - G key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_G, 0), "giveGift");
        actionMap.put("giveGift", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(560, 550);
            }
        });

        // Feed - F key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_F, 0), "feedPet");
        actionMap.put("feedPet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(210, 550);
            }
        });

        // Exercise - E key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, 0), "exercisePet");
        actionMap.put("exercisePet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(730, 550);
            }
        });

        // Vet - V key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0), "visitVet");
        actionMap.put("visitVet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(900, 550);
            }
        });

        // Sleep - Z key
        userInput.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0), "sleepPet");
        actionMap.put("sleepPet", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clickButtonAtPosition(150, 500);
            }
        });
    }


    private void clickButtonAtPosition(int x, int y) {
        Component[] components = inGameScreen.getComponents();
        for (int i = 0; i < components.length; i++) {
            Component comp = components[i];
            if (comp instanceof JButton) {
                JButton button = (JButton) comp;
                if (button.getX() == x && button.getY() == y) {
                    button.doClick();
                    return;
                }
            }
        }
    }
}
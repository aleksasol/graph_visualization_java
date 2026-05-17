package gui;

import javax.swing.*;
import java.awt.*;

public class ToolbarPanel extends JPanel {
    private CardLayout cardLayout;
    private JPanel cards;

    private JButton loadInputFileButton;
    private JButton loadOutputFileButton;

    private JComboBox<String> algorythmSelector;
    private JButton runCButton;

    public ToolbarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.YELLOW);

        String[] modes = {"Tryb Pasywny", "Tryb Wsadowy"};
        JComboBox<String> modeSelector = new JComboBox<>(modes);
        add(modeSelector, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        cards.setBackground(Color.RED);

        cards.add(createPassivePanel(), "PASSIVE");
        cards.add(createActivePanel(), "ACTIVE");

        add(cards, BorderLayout.CENTER);

        modeSelector.addActionListener(e -> {
            if (modeSelector.getSelectedIndex() == 0) {
                cardLayout.show(cards, "PASSIVE");
            } else {
                cardLayout.show(cards, "ACTIVE");
            }
        });
    }

    private JPanel createPassivePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLUE);

        loadInputFileButton = new JButton("Wczytaj krawędzie");
        loadOutputFileButton = new JButton("Wczytaj współrzędne");

        loadInputFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadOutputFileButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(loadInputFileButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(loadOutputFileButton);

        return panel;
    }

    private JPanel createActivePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.BLACK);

        String[] algos = {"Fruchterman-Reingold", "Tutte"};
        algorythmSelector = new JComboBox<>(algos);
        runCButton = new JButton("Przelicz (C)");

        algorythmSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        runCButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Wybierz algorytm:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(algorythmSelector);
        panel.add(Box.createVerticalStrut(15));
        panel.add(runCButton);

        return panel;
    }

    public JButton getLoadInputFileButton() {
        return loadInputFileButton;
    }

    public JButton getLoadOutputFileButton() {
        return loadOutputFileButton;
    }

    public JButton getRunCButton() {
        return runCButton;
    }

    public JComboBox<String> getAlgoSelector() {
        return algorythmSelector;
    }
}
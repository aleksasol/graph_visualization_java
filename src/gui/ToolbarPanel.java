package gui;

import javax.swing.*;
import java.awt.*;

public class ToolbarPanel extends JPanel {
    private JCheckBox showNodesNamesCheckBox;
    private JCheckBox showEdgesWeightsCheckBox;

    private CardLayout cardLayout;
    private JPanel cards;

    private JButton loadInputFileButton;
    private JButton loadOutputFileButton;

    private JComboBox<String> algorythmSelector;
    private JButton runCButton;

    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 35;
    private static final Color BACKGROUND_COLOR = Color.WHITE;
    private static final Color BUTTON_COLOR = new Color(240, 240, 240);

    public ToolbarPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(BACKGROUND_COLOR);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(BACKGROUND_COLOR);

        showNodesNamesCheckBox = new JCheckBox("Pokaż nazwy węzłów");
        showEdgesWeightsCheckBox = new JCheckBox("Pokaż wagi krawędzi");
        loadInputFileButton = new JButton("Wczytaj krawędzie");

        showNodesNamesCheckBox.setBackground(BACKGROUND_COLOR);
        showEdgesWeightsCheckBox.setBackground(BACKGROUND_COLOR);
        styleButton(loadInputFileButton);

        JPanel checkboxShowNodesNamesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        checkboxShowNodesNamesPanel.setBackground(BACKGROUND_COLOR);
        checkboxShowNodesNamesPanel.add(showNodesNamesCheckBox);

        JPanel checkboxShowEdgesWeightsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        checkboxShowEdgesWeightsPanel.setBackground(BACKGROUND_COLOR);
        checkboxShowEdgesWeightsPanel.add(showEdgesWeightsCheckBox);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(loadInputFileButton);

        String[] modes = {"Tryb Pasywny", "Tryb Wsadowy"};
        JComboBox<String> modeSelector = new JComboBox<>(modes);
        modeSelector.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));

        JPanel modePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        modePanel.setBackground(BACKGROUND_COLOR);
        modePanel.add(new JLabel("Tryb:"));

        JPanel modeSelectorPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        modeSelectorPanel.setBackground(BACKGROUND_COLOR);
        modeSelectorPanel.add(modeSelector);

        topPanel.add(checkboxShowNodesNamesPanel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(checkboxShowEdgesWeightsPanel);
        topPanel.add(Box.createVerticalStrut(15));
        topPanel.add(buttonPanel);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(modePanel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(modeSelectorPanel);
        topPanel.add(Box.createVerticalGlue());

        add(topPanel, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);
        cards.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        cards.setBackground(BACKGROUND_COLOR);

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

    private void styleButton(JButton button) {
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        button.setBackground(BUTTON_COLOR);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private JPanel createPassivePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);

        loadOutputFileButton = new JButton("Wczytaj współrzędne");
        styleButton(loadOutputFileButton);

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setBackground(BACKGROUND_COLOR);
        buttonWrapper.add(loadOutputFileButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonWrapper);
        panel.add(Box.createVerticalGlue());

        return panel;
    }

    private JPanel createActivePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BACKGROUND_COLOR);

        String[] algos = {"Fruchterman-Reingold", "Tutte"};
        algorythmSelector = new JComboBox<>(algos);
        algorythmSelector.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        algorythmSelector.setAlignmentX(Component.CENTER_ALIGNMENT);

        runCButton = new JButton("Przelicz (C)");
        styleButton(runCButton);

        JLabel label = new JLabel("Wybierz algorytm:");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setForeground(Color.BLACK);

        JPanel labelWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        labelWrapper.setBackground(BACKGROUND_COLOR);
        labelWrapper.add(label);

        JPanel comboBoxWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        comboBoxWrapper.setBackground(BACKGROUND_COLOR);
        comboBoxWrapper.add(algorythmSelector);

        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setBackground(BACKGROUND_COLOR);
        buttonWrapper.add(runCButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(labelWrapper);
        panel.add(Box.createVerticalStrut(8));
        panel.add(comboBoxWrapper);
        panel.add(Box.createVerticalStrut(20));
        panel.add(buttonWrapper);
        panel.add(Box.createVerticalGlue());

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

    public JCheckBox getShowNodesNamesCheckBox(){
        return showNodesNamesCheckBox;
    }

    public JCheckBox getShowEdgesWeightsCheckBox(){
        return showEdgesWeightsCheckBox;
    }
}
package gui;

import javax.swing.*;
import java.awt.Color;

public class ToolbarPanel extends JPanel {
    public ToolbarPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton loadButton = new JButton("Wczytaj graf");
        JButton runButton = new JButton("Przelicz (C)");
        JButton saveImageButton = new JButton("Save image");
        JButton saveResultsButton = new JButton("Save results");
        JCheckBox showNamesCheckBox = new JCheckBox("Show names");

        add(loadButton);
        add(runButton);
        add(saveImageButton);
        add(saveResultsButton);
        add(showNamesCheckBox);
    }
}
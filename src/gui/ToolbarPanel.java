package gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import java.awt.Color;

public class ToolbarPanel extends JPanel {
    public ToolbarPanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.LIGHT_GRAY);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton loadButton = new JButton("Wczytaj graf");
        JButton runButton = new JButton("Przelicz (C)");

        add(loadButton);
        add(runButton);
    }
}
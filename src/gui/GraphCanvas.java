package gui;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class GraphCanvas extends JPanel {
    public GraphCanvas() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillOval(100, 100, 50, 50);

        g.setColor(Color.BLACK);
        g.drawLine(125, 125, 300, 300);
    }
}
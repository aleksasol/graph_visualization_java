package gui;

import model.Graph;
import model.Node;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class GraphCanvas extends JPanel {

    private Graph graph;
    private double scale = 0.1;
    private double offsetX = 0, offsetY = 0;

    public GraphCanvas() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = new AffineTransform();
        at.translate(offsetX, offsetY);
        at.scale(scale, scale);
        graphics2D.transform(at);

        if (graph == null){
            return;
        }

        for (Node n : graph.getNodes()) {
            int radius = 10;
            graphics2D.setColor(Color.BLUE);
            graphics2D.fillOval((int) n.getX() - radius, (int) n.getY() - radius, radius * 2, radius * 2);
        }
    }

    public void setGraph(Graph targetGraph){
        this.graph = targetGraph;
        this.repaint();
    }
}
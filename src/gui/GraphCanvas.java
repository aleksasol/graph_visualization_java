package gui;

import model.Edge;
import model.Graph;
import model.Node;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;

public class GraphCanvas extends JPanel {
    private Graph graph;

    public GraphCanvas() {
        setBackground(Color.WHITE);
    }
    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (graph == null) return;
        g.setColor(Color.BLACK);
        for (Edge edge : graph.getEdges()) {
            int x1 = (int) edge.getSource().getX();
            int y1 = (int) edge.getSource().getY();
            int x2 = (int) edge.getTarget().getX();
            int y2 = (int) edge.getTarget().getY();
            g.drawLine(x1, y1, x2, y2);
        }
        g.setColor(Color.RED);
        int radius = 15;

        for (Node node : graph.getNodes()) {
            int x = (int) node.getX();
            int y = (int) node.getY();
            g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
            g.setColor(Color.BLACK);
            g.drawString(node.getName(), x - 5, y - radius - 5);
            g.setColor(Color.RED);
        }
    }
}
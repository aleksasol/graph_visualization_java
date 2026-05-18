package gui;

import model.Edge;
import model.Graph;
import model.Node;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.AffineTransform;

public class GraphCanvas extends JPanel {
    private double scale = 1;
    private double offsetX = 0, offsetY = 0;

    private boolean showNodesNames = false;
    private boolean showEdgeWeights = false;

    private Color nodeColor = Color.RED;
    private Color edgeColor = Color.BLACK;

    private Graph graph;

    public GraphCanvas() {
        setBackground(Color.LIGHT_GRAY);
        initMouseListeners();
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }

    public void setNodeColor(Color color){
        nodeColor = color;
        repaint();
    }

    public void setEdgeColor(Color color){
        edgeColor = color;
        repaint();
    }

    private void initMouseListeners() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            private int lastMouseX;
            private int lastMouseY;

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                lastMouseX = e.getX();
                lastMouseY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                int deltaX = e.getX() - lastMouseX;
                int deltaY = e.getY() - lastMouseY;

                offsetX += deltaX;
                offsetY += deltaY;

                lastMouseX = e.getX();
                lastMouseY = e.getY();

                repaint();
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                super.mouseWheelMoved(e);
                double zoomFactor = 1.1;
                double oldScale = scale;

                if (e.getPreciseWheelRotation() < 0) {
                    scale *= zoomFactor;
                } else {
                    scale /= zoomFactor;
                }

                double mouseX = e.getX();
                double mouseY = e.getY();

                offsetX = mouseX - (mouseX - offsetX) * (scale / oldScale);
                offsetY = mouseY - (mouseY - offsetY) * (scale / oldScale);

                repaint();
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
        addMouseWheelListener(mouseHandler);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        AffineTransform at = new AffineTransform();
        at.translate(offsetX, offsetY);
        at.scale(scale, scale);
        graphics2D.transform(at);

        if (graph == null) {
            return;
        }

        for (Edge edge : graph.getEdges()) {
            graphics2D.setColor(edgeColor);
            if (edge.getSource() != null && edge.getTarget() != null) {
                int x1 = (int) edge.getSource().getX();
                int y1 = (int) edge.getSource().getY();
                int x2 = (int) edge.getTarget().getX();
                int y2 = (int) edge.getTarget().getY();
                graphics2D.drawLine(x1, y1, x2, y2);
                if (showEdgeWeights){
                    graphics2D.setColor(Color.BLACK);
                    graphics2D.drawString(edge.getStringWeight(), (x1 + x2) / 2 - 5, (y1 + y2) / 2 - 5);
                }

            }
        }

        int radius = 15;
        for (Node node : graph.getNodes()) {
            int x = (int) node.getX();
            int y = (int) node.getY();

            graphics2D.setColor(nodeColor);
            graphics2D.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            if (showNodesNames){
                graphics2D.setColor(Color.BLACK);
                graphics2D.drawString(node.getName(), x - 5, y - radius - 5);
            }
        }
    }

    public void setShowNodesNames(boolean value){
        showNodesNames = value;
        repaint();
    }

    public void setShowEdgeWeights(boolean value){
        showEdgeWeights = value;
        repaint();
    }
}
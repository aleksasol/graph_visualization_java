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
import java.awt.geom.Point2D;

public class GraphCanvas extends JPanel {
    private double scale = 1;
    private double offsetX = 0, offsetY = 0;

    private boolean showNodesNames = false;
    private boolean showEdgeWeights = false;

    private Color nodeColor = Color.RED;
    private Color edgeColor = Color.BLACK;

    private Graph graph;
    private Node draggedNode = null;
    private int dragRadius = 15;

    public GraphCanvas() {
        setBackground(Color.LIGHT_GRAY);
        initMouseListeners();
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        repaint();
    }

    public void setNodeColor(Color color){
        if (color == null) {
            System.err.println("Kolor węzła nie może być null");
            return;
        }
        nodeColor = color;
        repaint();
    }

    public void setEdgeColor(Color color){
        if (color == null) {
            System.err.println("Kolor krawędzi nie może być null");
            return;
        }
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

                Point2D graphPoint = toGraphCoords(e.getX(), e.getY());
                draggedNode = findNodeAt(graphPoint.getX(), graphPoint.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (draggedNode != null) {
                    Point2D graphPoint = toGraphCoords(e.getX(), e.getY());
                    draggedNode.setX(graphPoint.getX());
                    draggedNode.setY(graphPoint.getY());
                    repaint();
                    return;
                }

                int deltaX = e.getX() - lastMouseX;
                int deltaY = e.getY() - lastMouseY;

                offsetX += deltaX;
                offsetY += deltaY;

                lastMouseX = e.getX();
                lastMouseY = e.getY();

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                draggedNode = null;
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

    private Point2D toGraphCoords(int mouseX, int mouseY) {
        double graphX = (mouseX - offsetX) / scale;
        double graphY = (mouseY - offsetY) / scale;
        return new Point2D.Double(graphX, graphY);
    }

    private Node findNodeAt(double x, double y) {
        if (graph == null || graph.getNodes() == null) {
            return null;
        }
        double radiusSq = (double) dragRadius * dragRadius;
        for (Node node : graph.getNodes()) {
            if (node == null) {
                continue;
            }
            double dx = node.getX() - x;
            double dy = node.getY() - y;
            if (dx * dx + dy * dy <= radiusSq) {
                return node;
            }
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        try {
            Graphics2D graphics2D = (Graphics2D) g;
            graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            AffineTransform at = new AffineTransform();
            at.translate(offsetX, offsetY);
            at.scale(scale, scale);
            graphics2D.transform(at);

            if (graph == null) {
                return;
            }

            if (graph.getEdges() != null) {
                for (Edge edge : graph.getEdges()) {
                    if (edge == null) {
                        System.err.println("Krawędź jest null");
                        continue;
                    }

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
            }

            int radius = 15;
            if (graph.getNodes() != null) {
                for (Node node : graph.getNodes()) {
                    if (node == null) {
                        System.err.println("Węzeł jest null");
                        continue;
                    }

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
        } catch (Exception ex) {
            System.err.println("Błąd podczas rysowania grafu: " + ex.getMessage());
            ex.printStackTrace();
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
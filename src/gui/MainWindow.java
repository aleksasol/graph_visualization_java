package gui;

import utils.GraphReader;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainWindow extends JFrame {
    private GraphCanvas canvas;
    private ToolbarPanel toolbar;

    public MainWindow() throws Exception {
        setTitle("Wizualizacja Grafów Planarnych");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        canvas = new GraphCanvas();
        toolbar = new ToolbarPanel();

        GraphReader reader = new GraphReader("grid_graph.txt", "output.txt");
        reader.readGraph();
        canvas.setGraph(reader.getGraph());
        add(toolbar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
    }
}
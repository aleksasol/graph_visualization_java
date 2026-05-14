package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainWindow extends JFrame {
    private GraphCanvas canvas;
    private ToolbarPanel toolbar;

    public MainWindow() {
        setTitle("Wizualizacja Grafów Planarnych");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        canvas = new GraphCanvas();
        toolbar = new ToolbarPanel();

        add(toolbar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);
    }
}
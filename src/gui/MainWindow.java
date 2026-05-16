package gui;

import integration.IntegrationManager;
import model.Graph;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import java.awt.BorderLayout;
import java.io.File;

public class MainWindow extends JFrame {
    private GraphCanvas canvas;
    private ToolbarPanel toolbar;
    private String selectedFilePath = null;

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

        toolbar.getLoadButton().addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);

            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                selectedFilePath = selectedFile.getAbsolutePath(); // Zapisujemy ścieżkę
                JOptionPane.showMessageDialog(this, "Wybrano plik:\n" + selectedFilePath);
            }
        });

        toolbar.getRunButton().addActionListener(e -> {
            if (selectedFilePath == null) {
                JOptionPane.showMessageDialog(this, "wczytaj plik z grafem używając przycisku wyżej!", "Błąd", JOptionPane.WARNING_MESSAGE);
                return; // Przerywamy, jeśli nie wybrano pliku
            }

            JOptionPane.showMessageDialog(this, "obliczenia do języka C dla pliku:\n" + selectedFilePath);

            IntegrationManager manager = new IntegrationManager();
            Graph calculatedGraph = manager.runCProgram(selectedFilePath);
            canvas.setGraph(calculatedGraph);
        });
    }

    public GraphCanvas getCanvas() {
        return canvas;
    }
}
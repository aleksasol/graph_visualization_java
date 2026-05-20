package gui;

import integration.IntegrationManager;
import model.Graph;
import utils.GraphReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;

public class MainWindow extends JFrame {
    private GraphCanvas canvas;
    private ToolbarPanel toolbar;

    private String inputFilePath = null;
    private String outputFilePath = null;

    public MainWindow() {
        setTitle("Wizualizacja Grafów Planarnych");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        canvas = new GraphCanvas();
        toolbar = new ToolbarPanel();

        add(toolbar, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);

        initListeners();
    }

    public void initListeners() {
        toolbar.getShowNodesNamesCheckBox().addItemListener(e -> {
            try {
                canvas.setShowNodesNames(e.getStateChange() == ItemEvent.SELECTED);
            } catch (Exception ex) {
                System.err.println("Błąd przy zmianie wyświetlania nazw węzłów: " + ex.getMessage());
            }
        });

        toolbar.getShowEdgesWeightsCheckBox().addItemListener(e -> {
            try {
                canvas.setShowEdgeWeights(e.getStateChange() == ItemEvent.SELECTED);
            } catch (Exception ex) {
                System.err.println("Błąd przy zmianie wyświetlania wag krawędzi: " + ex.getMessage());
            }
        });

        toolbar.getLoadInputFileButton().addActionListener(_ -> {
            try {
                inputFilePath = selectFileViaDialog("Wybierz plik z krawędziami");
                if (inputFilePath != null) {
                    toolbar.getLoadInputFileButton().setBackground(Color.GREEN);
                    checkAndRender();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd przy wczytywaniu pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        toolbar.getLoadOutputFileButton().addActionListener(_ -> {
            try {
                outputFilePath = selectFileViaDialog("Wybierz plik ze współrzędnymi");
                if (outputFilePath != null) {
                    toolbar.getLoadOutputFileButton().setBackground(Color.GREEN);
                    checkAndRender();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd przy wczytywaniu pliku: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        toolbar.getChooseNodeColorButton().addActionListener(_ -> {
            try {
                Color nodeSelectedColor = JColorChooser.showDialog(
                        this,
                        "Wybierz kolor",
                        toolbar.getNodeColorIndicator().getBackground()
                );
                if (nodeSelectedColor != null){
                    toolbar.getNodeColorIndicator().setBackground(nodeSelectedColor);
                    canvas.setNodeColor(nodeSelectedColor);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd przy wyborze koloru: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        toolbar.getChooseEdgeColorButton().addActionListener(_ -> {
            try {
                Color edgeSelectedColor = JColorChooser.showDialog(
                        this,
                        "Wybierz kolor",
                        toolbar.getEdgeColorIndicator().getBackground()
                );
                if (edgeSelectedColor != null){
                    toolbar.getEdgeColorIndicator().setBackground(edgeSelectedColor);
                    canvas.setEdgeColor(edgeSelectedColor);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd przy wyborze koloru: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        });

        toolbar.getRunCButton().addActionListener(_ -> {
            try {
                if (inputFilePath == null || inputFilePath.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Najpierw wczytaj plik z krawędziami!", "Błąd", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                toolbar.getRunCButton().setEnabled(false);
                toolbar.getAlgoSelector().setEnabled(false);

                String outputFilePath = "engine/results.txt";
                String selectedAlgo = (String) toolbar.getAlgoSelector().getSelectedItem();
                
                if (selectedAlgo == null) {
                    throw new IllegalArgumentException("Nie wybrano algorytmu");
                }

                IntegrationManager worker = new IntegrationManager(inputFilePath, outputFilePath, selectedAlgo, () -> {
                    toolbar.getRunCButton().setEnabled(true);
                    toolbar.getAlgoSelector().setEnabled(true);
                }){
                    @Override
                    protected void done(){
                        super.done();
                        try {
                            if (!isCancelled()) {
                                Graph calculatedGraph = get();
                                if (calculatedGraph != null) {
                                    canvas.setGraph(calculatedGraph);
                                }
                            }
                        } catch (Exception exception) {
                            System.err.println("Błąd podczas ustawiania grafu na canvas: " + exception.getMessage());
                            exception.printStackTrace();
                        }
                    }
                };
                worker.execute();
                inputFilePath = null;
                toolbar.styleButton(toolbar.getLoadInputFileButton());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Błąd: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                toolbar.getRunCButton().setEnabled(true);
                toolbar.getAlgoSelector().setEnabled(true);
            }
        });
    }

    private String selectFileViaDialog(String dialogTitle) {
        try {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            fileChooser.setDialogTitle(dialogTitle);
            int userSelection = fileChooser.showOpenDialog(this);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                return fileChooser.getSelectedFile().getAbsolutePath();
            }
        } catch (Exception ex) {
            System.err.println("Błąd przy otwieraniu okna dialogowego: " + ex.getMessage());
        }
        return null;
    }

    private void checkAndRender() {
        if (inputFilePath != null && outputFilePath != null) {
            try {
                if (inputFilePath.isEmpty() || outputFilePath.isEmpty()) {
                    throw new IllegalArgumentException("Ścieżki do plików nie mogą być puste");
                }

                GraphReader reader = new GraphReader(inputFilePath, outputFilePath);
                reader.readGraph();
                
                Graph graph = reader.getGraph();
                if (graph == null) {
                    throw new RuntimeException("Graf nie mógł być załadowany");
                }

                canvas.setGraph(graph);
                inputFilePath = null;
                outputFilePath = null;
                toolbar.styleButton(toolbar.getLoadOutputFileButton());
                toolbar.styleButton(toolbar.getLoadInputFileButton());
            } catch (Exception exception) {
                System.err.println("Błąd przy wczytywaniu grafu: " + exception.getMessage());
                exception.printStackTrace();
                JOptionPane.showMessageDialog(this, "Błąd: " + exception.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
                inputFilePath = null;
                outputFilePath = null;
                toolbar.styleButton(toolbar.getLoadOutputFileButton());
                toolbar.styleButton(toolbar.getLoadInputFileButton());
            }
        }
    }

    public GraphCanvas getCanvas() {
        return canvas;
    }
}
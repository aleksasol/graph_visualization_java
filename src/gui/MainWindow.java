package gui;

import integration.IntegrationManager;
import model.Graph;
import utils.GraphReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;

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
            if (e.getStateChange() == ItemEvent.SELECTED){
                canvas.setShowNodesNames(true);
            }else{
                canvas.setShowNodesNames(false);
            }
            canvas.repaint();
        });

        toolbar.getShowEdgesWeightsCheckBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED){
                canvas.setShowEdgeWeights(true);
            }else{
                canvas.setShowEdgeWeights(false);
            }
            canvas.repaint();
        });

        toolbar.getLoadInputFileButton().addActionListener(_ -> {
            inputFilePath = selectFileViaDialog("Wybierz plik z krawędziami");
            if (inputFilePath != null) {
                toolbar.getLoadInputFileButton().setBackground(Color.GREEN);
                checkAndRender();
            }
        });

        toolbar.getLoadOutputFileButton().addActionListener(_ -> {
            outputFilePath = selectFileViaDialog("Wybierz plik ze współrzędnymi");
            if (outputFilePath != null) {
                toolbar.getLoadOutputFileButton().setBackground(Color.GREEN);
                checkAndRender();
            }
        });

        toolbar.getChooseNodeColorButton().addActionListener(_ -> {
            Color nodeSelectedColor = JColorChooser.showDialog(
                    this,
                    "Wybierz color",
                    toolbar.getNodeColorIndicator().getBackground()
            );
            if (nodeSelectedColor != null){
                toolbar.getNodeColorIndicator().setBackground(nodeSelectedColor);
                canvas.setNodeColor(nodeSelectedColor);
            }
        });

        toolbar.getRunCButton().addActionListener(_ -> {
            if (inputFilePath == null) {
                JOptionPane.showMessageDialog(this, "Najpierw wczytaj plik z krawędziami!", "Błąd", JOptionPane.WARNING_MESSAGE);
                return;
            }

            toolbar.getRunCButton().setEnabled(false);
            toolbar.getAlgoSelector().setEnabled(false);

            String outputFilePath = "engine/wynik.txt";
            String selectedAlgo = (String) toolbar.getAlgoSelector().getSelectedItem();
            System.out.println(selectedAlgo);

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
                            canvas.setGraph(calculatedGraph);
                        }
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }
            };
            worker.execute();
            inputFilePath = null;
            toolbar.getLoadInputFileButton().setBackground(Color.WHITE);
        });
    }

    private String selectFileViaDialog(String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
        fileChooser.setDialogTitle(dialogTitle);
        int userSelection = fileChooser.showOpenDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private void checkAndRender() {
        if (inputFilePath != null && outputFilePath != null) {
            try {
                GraphReader reader = new GraphReader(inputFilePath, outputFilePath);
                reader.readGraph();
                canvas.setGraph(reader.getGraph());
                inputFilePath = null;
                outputFilePath = null;
                toolbar.getLoadOutputFileButton().setBackground(Color.WHITE);
                toolbar.getLoadInputFileButton().setBackground(Color.WHITE);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(this, "Błąd: " + exception.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public GraphCanvas getCanvas() {
        return canvas;
    }
}
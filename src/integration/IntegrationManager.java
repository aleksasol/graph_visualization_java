package integration;

import model.Graph;
import utils.GraphReader;

import javax.swing.*;
import java.io.File;
import java.util.HashMap;

public class IntegrationManager extends SwingWorker<Graph, Void> {

    private String inputFilePath;
    private String outputFilePath;
    private String algorithm;
    private Runnable onComplete;

    HashMap<String, String> algorithms = new HashMap<String, String>();

    public IntegrationManager(String inputFilePath, String outputFilePath, String algorithm, Runnable onComplete) {
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        this.algorithm = algorithm;
        this.onComplete = onComplete;

        algorithms.put("Fruchterman-Reingold", "FR");
        algorithms.put("Tutte", "TU");
    }

    @Override
    protected Graph doInBackground() throws Exception {
        System.out.println("Uruchamiam program w C... Algorytm: " + algorithm);

        File rootDir = new File(System.getProperty("user.dir"));
        File engineDir = new File(rootDir, "engine");
        File exeFile = new File(engineDir, "graphProcessor.exe");

        File inputFile = new File(inputFilePath);
        File outputFile = new File("engine", "results.txt");

        ProcessBuilder builder = new ProcessBuilder(exeFile.getAbsolutePath(),
                "-i", inputFile.getAbsolutePath(),
                "-o", outputFile.getAbsolutePath(),
                "-algo", algorithms.get(algorithm)
        );

        builder.directory(engineDir);
        builder.inheritIO();

        Process process = builder.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("Proces C zakończył się błędem. Kod wyjścia: " + exitCode);
        }

        System.out.println("Program w C zakończył pracę!");

        GraphReader reader = new GraphReader(inputFile.toString(), outputFile.toString());
        reader.readGraph();

        return reader.getGraph();
    }

    @Override
    protected void done() {
        try {
            Graph resultGraph = get();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Błąd podczas obliczeń: " + e.getMessage(),
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (onComplete != null) {
                onComplete.run();
            }
        }
    }
}
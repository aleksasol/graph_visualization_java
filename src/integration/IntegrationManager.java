package integration;

import model.Graph;
import utils.GraphReader;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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

        algorithms.put("Fruchterman-Reingold", "1");
        algorithms.put("Tutte", "2");
    }

    @Override
    protected Graph doInBackground() throws Exception {
        System.out.println("Uruchamiam program w C... Algorytm: " + algorithm);

        if (inputFilePath == null || inputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Ścieżka do pliku wejściowego nie jest ustawiona");
        }
        if (outputFilePath == null || outputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Ścieżka do pliku wyjściowego nie jest ustawiona");
        }
        if (algorithm == null || algorithm.isEmpty()) {
            throw new IllegalArgumentException("Algorytm nie został wybrany");
        }

        String algoCode = algorithms.get(algorithm);
        if (algoCode == null) {
            throw new IllegalArgumentException("Nieznany algorytm: " + algorithm);
        }

        File rootDir = new File(System.getProperty("user.dir"));
        File engineDir = new File(rootDir, "engine");
        File exeFile = new File(engineDir, "graphProcessor.exe");

        if (!exeFile.exists()) {
            throw new FileNotFoundException("Program graphProcessor.exe nie znaleziony: " + exeFile.getAbsolutePath());
        }

        File inputFile = new File(inputFilePath);
        if (!inputFile.exists()) {
            throw new FileNotFoundException("Plik wejściowy nie znaleziony: " + inputFilePath);
        }

        File outputFile = new File(outputFilePath);

        try {
            ProcessBuilder builder = new ProcessBuilder(exeFile.getAbsolutePath(),
                    "-i", inputFile.getAbsolutePath(),
                    "-o", outputFile.getAbsolutePath(),
                    "-algorithm", algoCode
            );

            builder.directory(engineDir);
            builder.inheritIO();

            Process process = builder.start();
            int exitCode = process.waitFor();

            if (exitCode != 0 && exitCode != 1) {
                throw new RuntimeException("Proces C zakończył się błędem. Kod wyjścia: " + exitCode);
            }

            System.out.println("Program w C zakończył pracę!");

            GraphReader reader = new GraphReader(inputFile.toString(), outputFile.toString());
            reader.readGraph();

            return reader.getGraph();
        } catch (IOException e) {
            throw new IOException("Błąd podczas uruchamiania procesu: " + e.getMessage(), e);
        }
    }

    @Override
    protected void done() {
        try {
            Graph resultGraph = get();
            if (resultGraph == null) {
                JOptionPane.showMessageDialog(null,
                        "Błąd: Graf nie został prawidłowo obczony",
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
            System.err.println("Błąd podczas obliczeń: " + errorMessage);
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Błąd podczas obliczeń: " + errorMessage,
                    "Błąd",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            if (onComplete != null) {
                onComplete.run();
            }
        }
    }
}
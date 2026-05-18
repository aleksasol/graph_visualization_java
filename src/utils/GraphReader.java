package utils;

import model.Edge;
import model.Graph;
import model.Node;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphReader {
    private Graph graph = new Graph();
    private String inputFilePath = null;
    private String outputFilePath = null;

    public GraphReader(String sourceFilePath, String targetFilePath) {
        this.outputFilePath = targetFilePath;
        this.inputFilePath = sourceFilePath;
    }

    private void readEdges(String path) throws Exception {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Ścieżka do pliku krawędzi nie może być pusta");
        }

        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Plik z krawędziami nie znaleziony: " + path);
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (currentLine.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] result = currentLine.split(" ");
                    if (result.length < 4) {
                        throw new IllegalArgumentException("Nieprawidłowy format linii: " + currentLine);
                    }

                    String edge_name = result[0];
                    String source_node_name = result[1];
                    String target_node_name = result[2];
                    Double weight = Double.parseDouble(result[3]);

                    Node source_node = graph.findNodeByName(source_node_name);
                    Node target_node = graph.findNodeByName(target_node_name);

                    if (source_node == null) {
                        throw new IllegalArgumentException("Węzeł źródłowy nie znaleziony: " + source_node_name);
                    }
                    if (target_node == null) {
                        throw new IllegalArgumentException("Węzeł docelowy nie znaleziony: " + target_node_name);
                    }

                    Edge newEdge = new Edge(edge_name, source_node, target_node, weight);
                    graph.addEdge(newEdge);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Błąd parsowania liczby w linii: " + currentLine, e);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    private void readNodes(String path) throws Exception {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Ścieżka do pliku węzłów nie może być pusta");
        }

        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException("Plik ze współrzędnymi nie znaleziony: " + path);
        }

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                if (currentLine.trim().isEmpty()) {
                    continue;
                }

                try {
                    String[] result = currentLine.split(" ");
                    if (result.length < 3) {
                        throw new IllegalArgumentException("Nieprawidłowy format linii: " + currentLine);
                    }

                    String name = result[0];
                    Double x = Double.parseDouble(result[1]);
                    Double y = Double.parseDouble(result[2]);

                    Node newNode = new Node(name, x, y);
                    graph.addNode(newNode);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Błąd parsowania liczby w linii: " + currentLine, e);
                }
            }
        } catch (FileNotFoundException e) {
            throw e;
        }
    }

    public void readGraph() throws Exception {
        if (inputFilePath == null || inputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Ścieżka do pliku wejściowego nie jest ustawiona");
        }
        if (outputFilePath == null || outputFilePath.isEmpty()) {
            throw new IllegalArgumentException("Ścieżka do pliku wyjściowego nie jest ustawiona");
        }

        readNodes(outputFilePath);
        readEdges(inputFilePath);
    }

    public Graph getGraph() {
        return graph;
    }
}

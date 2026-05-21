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
    private double coordinateScale = 6.0;

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
                    String[] result = currentLine.split("\\s+");
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
                    String[] result = currentLine.split("\\s+");
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

    private void applyCoordinateScale() {
        if (graph.getNodes() == null || graph.getNodes().isEmpty()) {
            return;
        }

        double minX = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;

        for (Node node : graph.getNodes()) {
            if (node == null) {
                continue;
            }
            minX = Math.min(minX, node.getX());
            maxX = Math.max(maxX, node.getX());
            minY = Math.min(minY, node.getY());
            maxY = Math.max(maxY, node.getY());
        }

        if (minX == Double.POSITIVE_INFINITY || minY == Double.POSITIVE_INFINITY) {
            return;
        }

        double centerX = (minX + maxX) / 2.0;
        double centerY = (minY + maxY) / 2.0;

        for (Node node : graph.getNodes()) {
            if (node == null) {
                continue;
            }
            double scaledX = centerX + (node.getX() - centerX) * coordinateScale;
            double scaledY = centerY + (node.getY() - centerY) * coordinateScale;
            node.setX(scaledX);
            node.setY(scaledY);
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
        applyCoordinateScale();
        readEdges(inputFilePath);
    }

    public Graph getGraph() {
        return graph;
    }
}

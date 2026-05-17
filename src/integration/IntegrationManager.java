package integration;

import model.Edge;
import model.Graph;
import model.Node;

import java.io.File;
import java.util.Locale;
import java.util.Scanner;

public class IntegrationManager {

    public Graph runCProgram(String inputFilePath) {
        Graph resultGraph = new Graph();
        String outputFileName = "wynik.txt";

        try {
            ProcessBuilder builder = new ProcessBuilder("./graphProcessor", "-i", inputFilePath, "-o", outputFileName);
            builder.directory(new File("engine"));
            builder.inheritIO();

            System.out.println("Uruchamiam program w C...");
            Process process = builder.start();
            process.waitFor();
            System.out.println("Program w C zakończył pracę!");

            File outputFile = new File("engine/" + outputFileName);
            if (outputFile.exists()) {
                Scanner scanner = new Scanner(outputFile);
                scanner.useLocale(Locale.US);

                while (scanner.hasNextInt()) {
                    int id = scanner.nextInt();
                    double x = scanner.nextDouble();
                    double y = scanner.nextDouble();

                    resultGraph.addNode(new Node(String.valueOf(id), x * 7, y * 7));
                }
                scanner.close();
                System.out.println("Wczytano wierzchołki!");
            }
            File inputFile = new File(inputFilePath);
            if (inputFile.exists()) {
                Scanner edgeScanner = new Scanner(inputFile);
                edgeScanner.useLocale(Locale.US);

                while (edgeScanner.hasNext()) {
                    String edgeName = edgeScanner.next(); // np. "k1"
                    String n1Name = String.valueOf(edgeScanner.nextInt()); // "1"
                    String n2Name = String.valueOf(edgeScanner.nextInt()); // "2"
                    double weight = edgeScanner.nextDouble(); // "1.0"

                    Node sourceNode = null;
                    Node targetNode = null;

                    for (Node node : resultGraph.getNodes()) {
                        if (node.getName().equals(n1Name)) sourceNode = node;
                        if (node.getName().equals(n2Name)) targetNode = node;
                    }

                    if (sourceNode != null && targetNode != null) {
                        resultGraph.addEdge(new Edge(edgeName, sourceNode, targetNode, weight));
                    }
                }
                edgeScanner.close();
                System.out.println("Wczytano krawędzie!");
            }

        } catch (Exception e) {
            System.out.println("Błąd: " + e.getMessage());
        }

        return resultGraph;
    }
}
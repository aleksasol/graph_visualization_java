package utils;

import model.Edge;
import model.Graph;
import model.Node;

import java.io.File;
import java.util.Scanner;

public class GraphReader {
    private Graph graph = new Graph();
    private String inputFilePath = null;
    private String outputFilePath = null;

    public GraphReader(String sourceFilePath, String targetFilePath){
        this.outputFilePath = targetFilePath;
        this.inputFilePath = sourceFilePath;
    }

    private void readEdges(String path) throws Exception{
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        while (scanner.hasNextLine()){
            String currentLine = scanner.nextLine();
            String [] result = currentLine.split(" ");

            String edge_name = result[0];
            String source_node_name = result[1];
            String target_node_name = result[2];
            Double weight = Double.parseDouble(result[3]);

            Node source_node = graph.findNodeByName(source_node_name);
            Node target_node = graph.findNodeByName(target_node_name);

            Edge newEdge = new Edge(edge_name, source_node, target_node, weight);

            graph.addEdge(newEdge);
        }

        scanner.close();
    }

    private void readNodes(String path) throws Exception {
        File file = new File(path);
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()){
            String currentLine = scanner.nextLine();
            String [] result = currentLine.split(" ");

            String name = result[0];
            Double x = Double.parseDouble(result[1]);
            Double y = Double.parseDouble(result[2]);

            Node newNode = new Node(name, x, y);

            graph.addNode(newNode);
        }

        scanner.close();
    }

    public void readGraph() throws Exception {
        readNodes(outputFilePath);
        readEdges(inputFilePath);
    }

    public Graph getGraph() {
        return graph;
    }
}

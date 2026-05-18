package model;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void addNode(Node node) {
        if (node == null) {
            System.err.println("Nie można dodać null węzła do grafu");
            return;
        }
        nodes.add(node);
    }

    public void addEdge(Edge edge) {
        if (edge == null) {
            System.err.println("Nie można dodać null krawędzi do grafu");
            return;
        }
        edges.add(edge);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public Node findNodeByName(String nodeName) {
        if (nodeName == null || nodeName.isEmpty()) {
            return null;
        }
        for (Node node : nodes) {
            if (node != null && node.getName() != null && node.getName().equals(nodeName)) {
                return node;
            }
        }
        return null;
    }
}
import gui.MainWindow;
import model.Edge;
import model.Graph;
import model.Node;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = new MainWindow();
            window.setVisible(true);

            Graph testGraph = new Graph();
            Node n1 = new Node("A", 200, 200);
            Node n2 = new Node("B", 400, 150);
            Node n3 = new Node("C", 300, 350);

            testGraph.addNode(n1);
            testGraph.addNode(n2);
            testGraph.addNode(n3);
            testGraph.addEdge(new Edge(n1, n2, 1.0));
            testGraph.addEdge(new Edge(n2, n3, 1.0));
            testGraph.addEdge(new Edge(n3, n1, 1.0));
            window.getCanvas().setGraph(testGraph);
        });
    }
}
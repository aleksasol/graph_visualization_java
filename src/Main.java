import gui.MainWindow;
import model.Edge;
import model.Graph;
import model.Node;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainWindow window = null;
            try {
                window = new MainWindow();
                if (window == null) {
                    throw new RuntimeException("Nie udało się stworzyć okna aplikacji");
                }
                window.setVisible(true);
            } catch (Exception e) {
                System.err.println("Błąd przy inicjalizacji okna: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }

            try {
                Graph testGraph = new Graph();
                Node n1 = new Node("A", 200, 200);
                Node n2 = new Node("B", 400, 150);
                Node n3 = new Node("C", 300, 350);

                if (n1 == null || n2 == null || n3 == null) {
                    throw new RuntimeException("Nie udało się stworzyć węzłów");
                }

                testGraph.addNode(n1);
                testGraph.addNode(n2);
                testGraph.addNode(n3);
                testGraph.addEdge(new Edge("AB", n1, n2, 1.0));
                testGraph.addEdge(new Edge("BC", n2, n3, 1.0));
                testGraph.addEdge(new Edge("AC", n3, n1, 1.0));

                if (window != null && window.getCanvas() != null) {
                    window.getCanvas().setGraph(testGraph);
                }
            } catch (Exception e) {
                System.err.println("Błąd przy tworzeniu grafu testowego: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}
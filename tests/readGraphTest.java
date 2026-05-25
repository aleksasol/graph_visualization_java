import model.Graph;
import model.Node;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import utils.GraphReader;

import java.nio.file.Path;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

class GraphReaderTest {

    @TempDir
    Path tempDir;

    @Test
    void readGraph_parsesNodesAndEdges() throws Exception {
        Path nodes = tempDir.resolve("nodes.txt");
        Path edges = tempDir.resolve("edges.txt");

        Files.writeString(nodes,
                "A 0 0\n" +
                        "B 10 0\n" +
                        "C 0 10\n");

        Files.writeString(edges,
                "AB A B 1.0\n" +
                        "BC B C 2.0\n");

        GraphReader reader = new GraphReader(edges.toString(), nodes.toString());
        reader.readGraph();

        Graph g = reader.getGraph();
        assertNotNull(g);
        assertEquals(3, g.getNodes().size());
        assertEquals(2, g.getEdges().size());

        Node a = g.findNodeByName("A");
        assertNotNull(a);
    }

    @Test
    void readGraph_throwsIfMissingNodeInEdge() throws Exception {
        Path nodes = tempDir.resolve("nodes.txt");
        Path edges = tempDir.resolve("edges.txt");

        Files.writeString(nodes, "A 0 0\n");
        Files.writeString(edges, "AB A B 1.0\n");

        GraphReader reader = new GraphReader(edges.toString(), nodes.toString());
        Exception ex = assertThrows(IllegalArgumentException.class, reader::readGraph);
        assertTrue(ex.getMessage().contains("Węzeł docelowy"));
    }

    @Test
    void readGraph_throwsOnBadFormat() throws Exception {
        Path nodes = tempDir.resolve("nodes.txt");
        Path edges = tempDir.resolve("edges.txt");

        Files.writeString(nodes, "A 0\n"); // bad line
        Files.writeString(edges, "AB A B 1.0\n");

        GraphReader reader = new GraphReader(edges.toString(), nodes.toString());
        Exception ex = assertThrows(IllegalArgumentException.class, reader::readGraph);
        assertTrue(ex.getMessage().contains("Nieprawidłowy format linii"));
    }
}
package assignments.homework.hw2;

import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

public class GraphV1Test {

    @Test
    void testAddVertex() {
        GraphV1 g = new GraphV1();
        assertTrue(g.addVertex("A"), "should add new vertex A");
        assertFalse(g.addVertex("A"), "adding A again should return false");
        assertTrue(g.getVertices().contains("A"));
    }

    @Test
    void testRemoveNonexistentVertex() {
        GraphV1 g = new GraphV1();
        assertFalse(g.removeVertex("Z"), "removing missing vertex should return false");
        assertTrue(g.getVertices().isEmpty(), "no vertices should be present");
    }

    @Test
    void testRemoveVertexWithEdges() {
        GraphV1 g = new GraphV1();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        assertTrue(g.removeVertex("A"), "should remove A");
        assertFalse(g.getVertices().contains("A"));
        // B and C remain but lose their edge to A
        assertEquals(0, g.getDegree("B"), "B should have degree 0 after A is removed");
        assertEquals(0, g.getDegree("C"), "C should have degree 0 after A is removed");
        assertFalse(g.isAdjacent("B", "A"));
        assertFalse(g.isAdjacent("C", "A"));
    }

    @Test
    void testAddEdge() {
        GraphV1 g = new GraphV1();
        assertTrue(g.addEdge("A", "B"), "first addEdge(A,B) should return true");
        assertFalse(g.addEdge("A", "B"), "adding same edge again should return false");
        assertTrue(g.isAdjacent("A", "B"));
        assertTrue(g.isAdjacent("B", "A"));
        assertEquals(1, g.getDegree("A"));
        assertEquals(1, g.getDegree("B"));
    }

    @Test
    void testAddEdgeSelf() {
        GraphV1 g = new GraphV1();
        assertFalse(g.addEdge("X", "X"), "self-edge addEdge(X,X) should return false on second adjacency");
        assertTrue(g.isAdjacent("X", "X"), "vertex should be adjacent to itself after self-edge");
        assertEquals(1, g.getDegree("X"));
        Collection<String> edges = g.getEdges();
        assertEquals(1, edges.size(), "only one self-edge string should be present");
        assertTrue(edges.contains("X X"));
    }

    @Test
    void testRemoveEdge() {
        GraphV1 g = new GraphV1();
        g.addEdge("A", "B");
        assertTrue(g.removeEdge("A", "B"), "should remove existing edge");
        assertFalse(g.isAdjacent("A", "B"));
        assertFalse(g.removeEdge("A", "B"), "removing again should return false");
    }

    @Test
    void testRemoveEdgeWithMissingVertices() {
        GraphV1 g = new GraphV1();
        assertFalse(g.removeEdge("X", "Y"), "removing edge between non-existent vertices should return false");
        g.addVertex("X");
        assertFalse(g.removeEdge("X", "Y"), "removing edge when one vertex missing should return false");
    }

    @Test
    void testGetDegree() {
        GraphV1 g = new GraphV1();
        assertEquals(-1, g.getDegree("Z"), "degree of missing vertex should be -1");
        g.addVertex("Z");
        assertEquals(0, g.getDegree("Z"), "new vertex should have degree 0");
        g.addEdge("Z", "Y");
        assertEquals(1, g.getDegree("Z"));
    }

    @Test
    void testIsAdjacent() {
        GraphV1 g = new GraphV1();
        assertFalse(g.isAdjacent("X", "Y"), "missing vertices should not be adjacent");
        g.addEdge("X", "Y");
        assertTrue(g.isAdjacent("X", "Y"));
        assertTrue(g.isAdjacent("Y", "X"));
    }

    @Test
    void testGetVerticesImmutable() {
        GraphV1 g = new GraphV1();
        g.addVertex("A");
        Collection<String> verts = g.getVertices();
        verts.add("C");
        assertFalse(g.getVertices().contains("C"), "modifying returned vertices should not affect graph");
    }

    @Test
    void testGetEdgesImmutable() {
        GraphV1 g = new GraphV1();
        g.addEdge("A", "B");
        Collection<String> edges = g.getEdges();
        edges.clear();
        assertEquals(2, g.getEdges().size(), "modifying returned edges should not affect graph");
    }

    @Test
    void testTriangleGraphEdges() {
        GraphV1 g = new GraphV1();
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        g.addEdge("A", "C");
        Collection<String> edges = g.getEdges();
        assertEquals(6, edges.size(), "triangle should have 6 directed-edge entries");
        for (String e : new String[]{"A B", "B A", "B C", "C B", "A C", "C A"}) {
            assertTrue(edges.contains(e), "should contain edge " + e);
        }
    }

    @Test
    void testGetEdgesEmpty() {
        GraphV1 g = new GraphV1();
        assertTrue(g.getEdges().isEmpty(), "no edges in a new graph");
    }
}

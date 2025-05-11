package assignments.homework.hw2;

import java.util.*;

public class GraphV1 {

    private final Set<String> vertices;
    private final Map<String, Set<String>> edges;

    public GraphV1() {
        vertices = new HashSet<>();
        edges = new HashMap<>();
    }

    public boolean addVertex(String v) {
        if (vertices.add(v)) {
            edges.put(v, new HashSet<>());
            return true;
        }
        return false;
    }

    public boolean removeVertex(String v) {
        if (vertices.remove(v)) {
            for (String u : edges.remove(v)) {
                edges.get(u).remove(v);
            }
            return true;
        }
        return false;
    }

    public boolean addEdge(String vFrom, String vTo) {
        addVertex(vFrom);
        addVertex(vTo);
        return edges.get(vFrom).add(vTo) && edges.get(vTo).add(vFrom);
    }

    public boolean removeEdge(String v1, String v2) {
        return edges.containsKey(v1) && edges.containsKey(v2) && edges.get(v1).remove(v2) && edges.get(v2).remove(v1);
    }

    public int getDegree(String v) {
        return vertices.contains(v) ? edges.get(v).size() : -1;
    }

    public boolean isAdjacent(String v1, String v2) {
        return edges.containsKey(v1) && edges.get(v1).contains(v2);
    }

    public Collection<String> getVertices() {
        return new HashSet<String>(vertices);
    }

    public Collection<String> getEdges() {
        Set<String> edgeSet = new HashSet<>();
        for (String s1 : edges.keySet()) {
            for (String s2 : edges.get(s1)) {
                edgeSet.add(s1 + " " + s2);
            }
        }
        return edgeSet;
    }
}

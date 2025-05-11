package assignments.homework.hw3;

import java.util.*;

public class WeightedGraph {
    private final Map<String, Map<String, Integer>> matrix;
    Map<String, String> shortestPaths;

    public WeightedGraph() {
        matrix = new HashMap<>();
    }

    /**
     * PQ entry holding a vertex label and its current tentative distance.
     */
    private static record Node(String vertex, int weight) implements Comparable<Node> {
        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    /**
     * Add {@code v} if absent. @return {@code true} if the vertex was inserted.
     */
    public boolean addVertex(String v) {
        return matrix.putIfAbsent(v, new HashMap<>()) == null;
    }

    /**
     * Remove {@code v1} and all incident edges.
     */
    public boolean removeVertex(String v1) {
        if (matrix.containsKey(v1)) {
            for (String v2 : matrix.get(v1).keySet()) matrix.get(v2).remove(v1);
            matrix.remove(v1);
            return true;
        }
        return false;
    }

    /**
     * Add or update an undirected edge {@code v1-v2} with non-negative {@code weight}.
     */
    public boolean addEdge(String v1, String v2, int weight) {
        if (weight < 0) throw new IllegalArgumentException();
        addVertex(v1);
        addVertex(v2);
        matrix.get(v1).putIfAbsent(v2, weight);
        matrix.get(v2).putIfAbsent(v1, weight);
        return true;
    }

    /**
     * Remove edge {@code v1-v2}.
     */
    public boolean removeEdge(String v1, String v2) {
        return matrix.containsKey(v1) && matrix.get(v1).remove(v2) != null && matrix.get(v2).remove(v1) != null;
    }

    /**
     * Degree (neighbour count) of {@code v}, or -1 if absent.
     */
    public int getDegree(String v) {
        return matrix.containsKey(v) ? matrix.get(v).size() : -1;
    }

    /**
     * @return {@code true} if an edge exists between {@code v1} and {@code v2}.
     */
    public boolean isAdjacent(String v1, String v2) {
        return matrix.get(v1).containsKey(v2);
    }

    /**
     * Unmodifiable set of vertex labels.
     */
    public Collection<String> getVertices() {
        return Set.copyOf(matrix.keySet());
    }

    /**
     * All edges as "u->v weight:w" strings.
     */
    public Collection<String> getEdges() {
        Set<String> out = new HashSet<>();
        matrix.forEach((u, m) -> m.forEach((v, w) -> out.add(u + " -> " + v + " weight:" + w)));
        return out;
    }

    /**
     * Run Dijkstra from {@code source}; returns distance map (reachable vertices only).
     * Complexity: O((V+E) log V).
     */
    public Map<String, Integer> dijkstra(String source) {
        Map<String, Integer> dist = new HashMap<>();
        if (!matrix.containsKey(source)) return dist;

        shortestPaths = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();
        matrix.keySet().forEach(v -> dist.put(v, Integer.MAX_VALUE));
        dist.put(source, 0);
        shortestPaths.put(source, null);
        pq.add(new Node(source, 0));

        while (!pq.isEmpty()) {
            Node cur = pq.poll();
            String v1 = cur.vertex;
            int w1 = cur.weight;

            if (w1 != dist.get(v1)) continue; // skip stale queue entry

            for (String v2 : matrix.get(v1).keySet()) {
                int w2 = matrix.get(v1).get(v2) + w1;
                if (w2 < dist.get(v2)) {
                    dist.put(v2, w2);
                    shortestPaths.put(v2, v1);
                    pq.add(new Node(v2, w2));
                }
            }
        }
        return dist;
    }

    /**
     * Reconstruct path (source→{@code v}) after a call to {@link #dijkstra}.
     */
    private List<String> shortestPath(String v) {
        List<String> path = new LinkedList<>();
        if (shortestPaths.containsKey(v)) for (String at = v; at != null; at = shortestPaths.get(at)) path.addFirst(at);
        return path;
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph();
        graph.addEdge("A", "B", 4);
        graph.addEdge("A", "C", 1);
        graph.addEdge("C", "B", 2);
        graph.addEdge("B", "D", 5);
        graph.addEdge("C", "D", 8);
        graph.addEdge("D", "E", 6);

        Scanner sc = new Scanner(System.in);
        System.out.print("Source: ");
        String src = sc.next();
        System.out.print("Destination: ");
        String dst = sc.next();

        Map<String, Integer> distances = graph.dijkstra(src);
        if (!distances.containsKey(dst)) {
            System.out.printf("%s -> %s: unreachable%n", src, dst);
        } else {
            int d = distances.get(dst);
            List<String> path = graph.shortestPath(dst);
            System.out.printf("%s -> %s: distance=%d, path=%s%n", src, dst, d, path);
        }
    }
}
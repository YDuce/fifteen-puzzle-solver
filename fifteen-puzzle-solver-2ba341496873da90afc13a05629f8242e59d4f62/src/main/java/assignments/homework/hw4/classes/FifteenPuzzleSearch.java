package assignments.homework.hw4.classes;

import assignments.homework.hw4.interfaces.INode;
import assignments.homework.hw4.interfaces.IPuzzleSearch;

import java.util.*;

public class FifteenPuzzleSearch implements IPuzzleSearch {

    private static Node aStar(Node root) {
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(n -> n.getGValue() + n.getHValue()));
        Set<Node> closed = new HashSet<>();

        open.add(root);

        while (!open.isEmpty()) {
            Node cur = open.poll();

            if (closed.contains(cur)) continue;

            if (cur.isSolution()) return cur;

            closed.add(cur);

            for (INode n : cur.getNextNodes()) {
                Node node = (Node) n;
                if (!closed.contains(node)) open.add((node));
            }
        }
        return null;
    }

    private static Node scramble(Node root, int moves) {
        Random rnd = new Random();
        Node cur = root;
        for (int n = 0; n < moves; n++) {
            List<Node> options = new ArrayList<>();
            for (INode node : cur.getNextNodes()) {
                options.add((Node) node);
            }
            cur = options.get(rnd.nextInt(options.size()));
        }
        return new Node(cur.matrix);
    }

    private static boolean isSolvable(Node node) {
        byte[] board = node.matrix;
        int n = (int) Math.sqrt(board.length);

        int inversions = 0;
        for (int i = 0; i < board.length; i++) {
            byte vi = board[i];
            if (vi == 0) continue;
            for (int j = i + 1; j < board.length; j++) {
                byte vj = board[j];
                if (vj != 0 && vi > vj) inversions++;
            }
        }

        if (n % 2 == 1) {
            return inversions % 2 == 0;
        } else {
            int blankRow = node.blankIdx / n + 1;
            return (inversions + blankRow) % 2 == 0;
        }
    }

    @Override
    public void search(INode node) {
        Node root = (Node) node;
        Objects.requireNonNull(root, "start node is null");
        if (!isSolvable(root)) throw new IllegalStateException("Board state is unsolvable");

        Node solved = aStar(scramble(root, 100));
        if (solved == null) {
            System.out.println("No solution");
        } else {
            System.out.println("Solution:\n\n" + solved.toStringPath());
        }
    }
}

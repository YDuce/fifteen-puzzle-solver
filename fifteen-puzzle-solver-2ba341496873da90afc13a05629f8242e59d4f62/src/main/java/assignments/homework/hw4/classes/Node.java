package assignments.homework.hw4.classes;

import assignments.homework.hw4.interfaces.INode;

import java.util.*;

public class Node implements INode {

    public byte[] matrix;

    public Node parent;

    public int level;

    public int blankIdx;

    List<INode> nextNodes;

    public Node(byte[] matrix) {
        Objects.requireNonNull(matrix, "board is null");
        if (Math.sqrt(matrix.length) % 1.0 != 0.0) throw new IllegalArgumentException("Board is not N×N");
        this.matrix = matrix;
        parent = null;
        level = 0;
        nextNodes = new ArrayList<>();
        computeBlankIdx();
    }

    private Node(byte[] matrix, int blankIdx, Node parent) {
        Objects.requireNonNull(matrix, "board is null");
        if (Math.sqrt(matrix.length) % 1.0 != 0.0) throw new IllegalArgumentException("Board is not N×N");
        this.matrix = matrix;
        this.blankIdx = blankIdx;
        this.parent = parent;
        this.level = parent.level + 1;
        nextNodes = new ArrayList<>();
    }

    @Override
    public Iterable<INode> getNextNodes() {
        byte[] temp;
        int n = ((int) Math.sqrt(matrix.length));

        int upRowIdx = blankIdx - n;
        int downRowIdx = blankIdx + n;
        int upColIdx = blankIdx + 1;
        int downColIdx = blankIdx - 1;

        // if you can move up a row
        if (blankIdx >= n) {
            temp = Arrays.copyOf(matrix, matrix.length);
            temp[upRowIdx] = matrix[blankIdx];
            temp[blankIdx] = matrix[upRowIdx];
            nextNodes.add(new Node(temp, upRowIdx, this));
        }

        // if you can move down a row
        if (blankIdx < (matrix.length - n)) {
            temp = Arrays.copyOf(matrix, matrix.length);
            temp[downRowIdx] = matrix[blankIdx];
            temp[blankIdx] = matrix[downRowIdx];
            nextNodes.add(new Node(temp, downRowIdx, this));
        }

        // if you can move up a column
        if ((blankIdx + 1) % n != 0) {
            temp = Arrays.copyOf(matrix, matrix.length);
            temp[upColIdx] = matrix[blankIdx];
            temp[blankIdx] = matrix[upColIdx];
            nextNodes.add(new Node(temp, upColIdx, this));
        }

        // if you can move down a column
        if (blankIdx % n != 0) {
            temp = Arrays.copyOf(matrix, matrix.length);
            temp[downColIdx] = matrix[blankIdx];
            temp[blankIdx] = matrix[downColIdx];
            nextNodes.add(new Node(temp, downColIdx, this));
        }

        return nextNodes;
    }

    private void computeBlankIdx() {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i] == 0) blankIdx = i;
        }
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public INode getParent() {
        return parent;
    }

    @Override
    public boolean isSolution() {
        for (int i = 0; i < matrix.length - 1; i++) {
            if (matrix[i] != i + 1) return false;
        }
        return true;
    }

    @Override
    public double getHValue() {
        int manhattanDist = 0;
        int n = ((int) Math.sqrt(matrix.length));
        for (int i = 0; i < matrix.length; i++) {
            int val = matrix[i];
            if (val == 0) continue;
            int curRowIdx = i / n;
            int curColIdx = i % n;
            int targetRowIdx = (val - 1) / n;
            int targetColIdx = (val - 1) % n;
            manhattanDist += (Math.abs(curRowIdx - targetRowIdx) + Math.abs(curColIdx - targetColIdx));
        }
        return manhattanDist;
    }

    @Override
    public double getGValue() {
        return level;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(matrix);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Node n && Arrays.equals(this.matrix, n.matrix);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int n = (int) Math.sqrt(matrix.length);
        int v = String.valueOf(matrix.length - 1).length();
        for (int i = 0; i < matrix.length; i++) {
            sb.append(String.format("%0" + v + "d", matrix[i])).append(((i + 1) % n == 0) ? "\n" : " ");
        }
        return sb.toString();
    }

    public String toStringPath() {
        List<String> path = new ArrayList<>();
        for (Node cur = this; cur != null; cur = cur.parent) {
            path.add("Level: " + cur.getLevel() + "\n" + cur);
        }
        Collections.reverse(path);
        return String.join("\n\n", path);
    }

}

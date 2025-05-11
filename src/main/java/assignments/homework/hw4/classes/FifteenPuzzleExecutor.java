package assignments.homework.hw4.classes;

public class FifteenPuzzleExecutor {

    public static void main(String[] args) {
        Node node = new Node(new byte[]{3, 5, 12, 4, 6, 0, 2, 8, 1, 13, 7, 15, 10, 9, 11, 14});
        FifteenPuzzleSearch ps = new FifteenPuzzleSearch();
        ps.search(node);
    }

}

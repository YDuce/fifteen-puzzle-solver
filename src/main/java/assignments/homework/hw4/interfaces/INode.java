package assignments.homework.hw4.interfaces;

public interface INode {

    Iterable<INode> getNextNodes();

    int getLevel();

    INode getParent();

    boolean isSolution();

    double getHValue();

    double getGValue();
}

package ai.neat.graph;

public class Node {

    private int number;
    private NodeType type;

    public Node(int number, NodeType type) {

        this.number = number;
        this.type = type;
    }


    public enum NodeType {
        SENSOR, OUTPUT, HIDDEN;
    }
}

package ai.neat.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Node {

    private int number;
    private NodeType type;

    private List<Connection> outConnections;

    private Set<Node> outNodes;

    public Node(int number, NodeType type) {

        this.number = number;
        this.type = type;
        init();
    }

    private void init() {
        outConnections = new ArrayList<>();
    }

    public void addOutConnection(Connection c) {
        Node outNode = c.getOutNode();

        if (!outNodes.contains(outNode)) {
            // only add if the connection does not exist
            outConnections.add(c);
            outNodes.add(outNode);
        }

    }

    public void removeOutConnection(Connection c) {
        Node outNode = c.getOutNode();
        outNodes.remove(outNode);
        outConnections.remove(c);
    }

    public boolean containsOutNode(Node node) {
        return outNodes.contains(node);
    }


    public enum NodeType {
        SENSOR, OUTPUT, HIDDEN;
    }

    public Set<Node> getOutNodes() {
        return outNodes;
    }
}

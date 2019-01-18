package ai.neat.network;

import java.util.HashSet;
import java.util.Set;

public class Node {

    private int number;
    private NodeType type;


    private Set<Connection> inConnections;
    private Set<Connection> outConnections;

    private Set<Node> inNodes;
    private Set<Node> outNodes;

    public Node(int number, NodeType type) {

        this.number = number;
        this.type = type;
        init();
    }

    private void init() {
        inConnections = new HashSet<>();
        outConnections = new HashSet<>();

        inNodes = new HashSet<>();
        outNodes = new HashSet<>();
    }

    public void addOutConnection(Connection c) {
        Node outNode = c.getOutNode();

        if (!outNodes.contains(outNode)) {
            // only add if the connection does not exist
            outConnections.add(c);
            outNodes.add(outNode);
        }

    }

    public void addInConnection(Connection c) {
        Node inNode = c.getInNode();
        if (!inNodes.contains(inNode)) {
            // Only add if the connection does not exist
            inConnections.add(c);
            inNodes.add(inNode);
        }
    }

    public void removeOutConnection(Connection c) {
        Node outNode = c.getOutNode();
        outNodes.remove(outNode);
        outConnections.remove(c);
    }

    public void removeInConnection(Connection c) {
        Node inNode = c.getInNode();
        inNodes.remove(inNode);
        inConnections.remove(c);
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

    public Set<Node> getInNodes() {
        return inNodes;
    }

    public Set<Connection> getInConnections() {
        return inConnections;
    }

    public Set<Connection> getOutConnections() {
        return outConnections;
    }


    public void clear() {
        for (Connection c : inConnections) {
            c.getInNode().removeOutConnection(c);
        }
        for (Connection c : outConnections) {
            c.getOutNode().removeInConnection(c);

        }
    }

}


package ai.neat.network;

import java.util.*;

public class Node {

    private int number;
    private NodeType type;


    private Map<Integer, Connection> inConnections;
    private Map<Integer, Connection> outConnections;

    private Set<Node> inNodes;
    private Set<Node> outNodes;

    public Node(int number, NodeType type) {

        this.number = number;
        this.type = type;
        init();
    }

    private void init() {
        inConnections = new HashMap<>();
        outConnections = new HashMap<>();

        inNodes = new HashSet<>();
        outNodes = new HashSet<>();
    }

    public void addOutConnection(Connection c) {
        Node outNode = c.getOutNode();

        if (!outNodes.contains(outNode)) {
            // only add if the connection does not exist
            outConnections.put(c.getInnovationNumber(), c);
            outNodes.add(outNode);
        }

    }

    public void addInConnection(Connection c) {
        Node inNode = c.getInNode();
        if (!inNodes.contains(inNode)) {
            // Only add if the connection does not exist
            inConnections.put(c.getInnovationNumber(), c);
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

    public Collection<Connection> getInConnections() {
        return inConnections.values();
    }

    public Collection<Connection> getOutConnections() {
        return outConnections.values();
    }


    public void clear() {
        for (Connection c : inConnections.values()) {
            c.getInNode().removeOutConnection(c);
        }
        for (Connection c : outConnections.values()) {
            c.getOutNode().removeInConnection(c);

        }
    }

}


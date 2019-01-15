package ai.neat.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final int numInputNodes;
    private final int numOutputNodes;

    private static int nodeCounter = 1;

    private List<Node> nodes;
    private List<Connection> connections;


    public Graph(int numInputNodes, int numOutputNodes) {

        this.numInputNodes = numInputNodes;
        this.numOutputNodes = numOutputNodes;

        init();
    }

    private void init() {
        nodes = new ArrayList<>();
        connections = new ArrayList<>();

        // Initializing input nodes
        for (int i = 0; i < numInputNodes; i++, nodeCounter++) {
            nodes.add(new Node(nodeCounter, Node.NodeType.SENSOR));
        }

        // Initializing Output nodes
        for (int i = 0; i < numOutputNodes; i++, nodeCounter++) {
            nodes.add(new Node(nodeCounter, Node.NodeType.OUTPUT));
        }


    }

    private void mutateConnections() {
        for (Connection c : connections) {
            // TODO fill
        }
    }

    private void mutateNodes() {
        for (Node n : nodes) {
            // TODO fill, tbh I forgot how we mutate nodes ,if at all, so this
            // TODO method might not be necessary.
        }
    }
}

package ai.neat.graph;

import ai.neat.parameters.NeatParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Graph {

    private final int numInputNodes;
    private final int numOutputNodes;
    private NeatParameters parameters;

    private static int nodeCounter = 1;

    private List<Node> nodes;
    private List<Connection> connections;


    public Graph(int numInputNodes, int numOutputNodes, NeatParameters parameters) {

        this.numInputNodes = numInputNodes;
        this.numOutputNodes = numOutputNodes;
        this.parameters = parameters;

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

        double mutateRate = 0;
        double replaceRate = 0;

        double minValue, maxValue;

        Random r = new Random();

        for (Connection c : connections) {
            double weight = c.getWeight();

            double random = Math.random();
            if (random < mutateRate) {
                // Mutate this particular connection
                double newWeight = parameters.mutateWeight(weight);
                c.setWeight(newWeight);

            } else if (random < mutateRate + replaceRate) {
                // Replace this particular connection
                c.setWeight(parameters.generateWeight());
            }
        }
    }

    private void mutateNodes() {
        for (Node n : nodes) {
            // TODO fill, tbh I forgot how we mutate nodes ,if at all, so this
            // TODO method might not be necessary.
        }
    }
}

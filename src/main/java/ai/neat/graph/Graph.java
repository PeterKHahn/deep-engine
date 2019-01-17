package ai.neat.graph;

import ai.neat.parameters.NeatParameters;

import java.util.*;

public class Graph {

    // TODO add an innovation number mechanic

    private final int numInputNodes;
    private final int numOutputNodes;
    private NeatParameters parameters;

    private static int nodeCounter = 1;

    private List<Node> nodes;

    private List<Node> inputNodes;
    private List<Node> hiddenNodes;
    private List<Node> outputNodes;


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

    private int randInt(int range) {
        return (int) (Math.random() * range);
    }

    private Node retrieveInputConnectionNode() {

        // Retrieving nodes from input nodes or hidden nodes
        int size = inputNodes.size() + hiddenNodes.size();


        int index = randInt(size - 1);
        if (index < inputNodes.size()) {
            return inputNodes.get(index);
        } else {
            int newIndex = index - inputNodes.size();
            return hiddenNodes.get(newIndex);
        }

    }

    private Node retrieveOutputConnectionNode() {
        // Retrieving nodes from hidden nodes or output nodes
        int size = hiddenNodes.size() + outputNodes.size();
        int index = randInt(size);
        if (index < hiddenNodes.size()) {
            return hiddenNodes.get(index);
        } else {
            int newIndex = index - hiddenNodes.size();
            return outputNodes.get(newIndex);
        }
    }

    private void mutateConnections() {

        for (Connection c : connections) {
            parameters.changeConnection(c);
        }
    }


    private void mutateAddConnection() {
        double random = Math.random();
        if (random < parameters.connectionAddProbability) {
            Node connectionFrom = retrieveInputConnectionNode();
            Node connectionTo = retrieveOutputConnectionNode();

            // A few considerations here


            // Does the connection we are trying to make already exist?
            if (connectionFrom.containsOutNode(connectionTo)) {
                // If so, then we return immediately, as we cannot add this connection
                return;
            }


            // Does the connection we are trying to make create a cycle?
            if (accessible(connectionTo, connectionFrom)) {
                // Only add cycles in recurrent, otherwise return
                // TODO adjust this method for recurrent neural networks

                return;

            }


            // all else succeeding, we can now add the connection
            createAndAddNewConnection(connectionFrom, connectionTo);

        }

    }

    private void createAndAddNewConnection(Node from, Node to) {
        double randomWeight = 0;
        // TODO fix the innovation number
        Connection newConnection = new Connection(from, to, randomWeight, true, -1);
        connections.add(newConnection);
        from.addOutConnection(newConnection);
    }

    private void mutateDeleteConnection() {
        double random = Math.random();
        if (random < parameters.connectionDeleteProbability) {
            int index = randInt(connections.size() - 1);
            Connection c = connections.get(index);

            c.getInNode().removeOutConnection(c);
            connections.remove(index); // TODO this in linear time operation, find a faster way to do this or whatever


        }
    }

    private void mutateAddNode() {
        double random = Math.random();
        if (random < parameters.nodeAddProbability) {

        }


    }

    private void mutateDeleteNode() {
        double random = Math.random();
        if (random < parameters.connectionDeleteProbability) {

        }
    }

    private boolean accessible(Node start, Node end) {
        Set<Node> visited = new HashSet<>();
        Queue<Node> frontier = new LinkedList<>();

        visited.add(start);
        frontier.addAll(start.getOutNodes());


        while (frontier.isEmpty()) {

            Node n = frontier.poll();
            if (visited.contains(n)) {
                continue;
            }

            visited.add(n);

            if (end.equals(n)) {
                return true;
            }
            visited.addAll(n.getOutNodes());

        }

        return false;
    }
}

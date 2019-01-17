package ai.neat.graph;

import ai.neat.parameters.NeatParameters;
import math.Random;

import java.util.*;

public class Graph {


    private int innovationCounter;

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

        innovationCounter = 1;

        // Initializing input nodes
        for (int i = 0; i < numInputNodes; i++) {
            nodes.add(new Node(getNextNode(), Node.NodeType.SENSOR));
        }

        // Initializing Output nodes
        for (int i = 0; i < numOutputNodes; i++) {
            nodes.add(new Node(getNextNode(), Node.NodeType.OUTPUT));
        }


    }


    private Node retrieveInputConnectionNode() {

        // Retrieving nodes from input nodes or hidden nodes
        return Random.choice(inputNodes, hiddenNodes);


    }

    private Node retrieveOutputConnectionNode() {
        // Retrieving nodes from hidden nodes or output nodes

        return Random.choice(hiddenNodes, outputNodes);
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
        double randomWeight = parameters.generateWeight();
        createAndAddNewConnection(from, to, randomWeight);

    }

    private void createAndAddNewConnection(Node from, Node to, double weight) {
        Connection newConnection = new Connection(from, to, weight, true, getNextInnovation());
        connections.add(newConnection);
        from.addOutConnection(newConnection);
        to.addInConnection(newConnection);
    }


    private void mutateDeleteConnection() {
        double random = Math.random();
        if (random < parameters.connectionDeleteProbability) {
            int index = Random.randInt(connections.size() - 1);
            Connection c = Random.choiceRemove(connections); // TODO this in linear time operation, find a faster way to do this or whatever

            clearConnection(c);


        }
    }

    private void clearConnection(Connection c) {
        c.getInNode().removeOutConnection(c);
        c.getOutNode().removeInConnection(c);
    }

    private void mutateAddNode() {
        double random = Math.random();
        if (random < parameters.nodeAddProbability) {
            Connection c = Random.choice(connections);
            c.setEnabled(false);

            Node newNode = createNewNode(Node.NodeType.HIDDEN);

            createAndAddNewConnection(c.getInNode(), newNode, 1);
            createAndAddNewConnection(newNode, c.getOutNode());
            
        }


    }

    private void mutateDeleteNode() {
        double random = Math.random();
        if (random < parameters.connectionDeleteProbability) {
            if (hiddenNodes.isEmpty()) {
                return;
            } else {
                Node n = Random.choiceRemove(hiddenNodes);
                connections.removeAll(n.getInConnections());
                connections.removeAll(n.getOutConnections());

                n.clear();

            }


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

    private Node createNewNode(Node.NodeType type) {
        return new Node(getNextNode(), type);
    }

    private int peekNextInnovation() {
        return innovationCounter;
    }

    private int getNextInnovation() {
        int tmp = innovationCounter;
        innovationCounter++;
        return tmp;
    }

    private int peekNextNode() {
        return nodeCounter;
    }

    private int getNextNode() {
        int tmp = nodeCounter;
        nodeCounter++;
        return tmp;
    }


}

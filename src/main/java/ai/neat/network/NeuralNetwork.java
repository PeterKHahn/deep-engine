package ai.neat.network;

import ai.neat.parameters.NeatParameters;
import math.Random;

import java.util.*;

public class NeuralNetwork {


    private int innovationCounter;

    private final int numInputNodes;
    private final int numOutputNodes;
    private NeatParameters parameters;

    private static int nodeCounter = 1;

    private List<Node> nodes;

    private List<Node> inputNodes;
    private List<Node> hiddenNodes;
    private List<Node> outputNodes;


    private List<Connection> connections; // Invariant: Must be strictly increasing order of innovation


    public NeuralNetwork(int numInputNodes, int numOutputNodes, NeatParameters parameters) {

        this.numInputNodes = numInputNodes;
        this.numOutputNodes = numOutputNodes;
        this.parameters = parameters;

        init();
    }

    private void init() {
        nodes = new ArrayList<>();
        connections = new ArrayList<>();

        inputNodes = new ArrayList<>();
        hiddenNodes = new ArrayList<>();
        outputNodes = new ArrayList<>();

        innovationCounter = 1;

        // Initializing input nodes
        for (int i = 0; i < numInputNodes; i++) {
            createNewNode(Node.NodeType.SENSOR);
        }

        // Initializing Output nodes
        for (int i = 0; i < numOutputNodes; i++) {
            createNewNode(Node.NodeType.OUTPUT);
        }


    }


    public void mutateConnections() {

        for (Connection c : connections) {
            parameters.changeConnection(c);
        }
    }


    public void mutateAddConnection() {
        double random = Math.random();
        if (random < parameters.connectionAddProbability) {
            Node connectionFrom = Random.choice(inputNodes, hiddenNodes);
            Node connectionTo = Random.choice(hiddenNodes, outputNodes);

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


    public void mutateDeleteConnection() {
        double random = Math.random();
        if (random < parameters.connectionDeleteProbability) {
            Connection c = Random.choiceRemove(connections);
            // TODO this in linear time operation, find a faster way to do this or whatever

            c.getInNode().removeOutConnection(c);
            c.getOutNode().removeInConnection(c);


        }
    }


    public void mutateAddNode() {
        double random = Math.random();

        if (random < parameters.nodeAddProbability) {
            Connection c = Random.choice(connections);
            c.setEnabled(false);

            Node newNode = createNewNode(Node.NodeType.HIDDEN);

            createAndAddNewConnection(c.getInNode(), newNode, 1);
            createAndAddNewConnection(newNode, c.getOutNode(), c.getWeight());

            hiddenNodes.add(newNode);

        }


    }

    public void mutateDeleteNode() {
        double random = Math.random();
        if (random < parameters.connectionDeleteProbability) {
            if (hiddenNodes.isEmpty()) {
                return;
            } else {
                Node n = Random.choiceRemove(hiddenNodes);
                removeConnections(n.getInConnections());
                removeConnections(n.getOutConnections());


                n.clear();

            }


        }
    }

    private void removeConnections(List<Connection> toRemove) {

        Iterator<Connection> connectionsIter = this.connections.iterator();


        for (Connection connectionToRemove : toRemove) {

            int innovRemove = connectionToRemove.getInnovationNumber();

            int innov = -1;
            while (innov < innovRemove && connectionsIter.hasNext()) {

                Connection c = connectionsIter.next();
                innov = c.getInnovationNumber();
            }
            if (innov == innovRemove) {
                connectionsIter.remove();
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
        Node n = new Node(getNextNode(), type);
        switch (type) {
            case HIDDEN:
                hiddenNodes.add(n);
                nodes.add(n);
                break;
            case SENSOR:
                inputNodes.add(n);
                nodes.add(n);
                break;
            case OUTPUT:
                inputNodes.add(n);
                nodes.add(n);
                break;
        }
        return n;
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

    public List<Connection> getConnections() {
        return connections;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Input Nodes: \n");
        for (Node n : inputNodes) {
            sb.append(n);
        }
        sb.append("Hidden Nodes: \n");
        for (Node n : hiddenNodes) {
            sb.append(n);
        }
        sb.append("Output Nodes: \n");
        for (Node n : outputNodes) {
            sb.append(n);
        }

        return sb.toString();
    }


}

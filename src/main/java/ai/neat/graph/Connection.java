package ai.neat.graph;

public class Connection {

    private Node inNode;
    private Node outNode;

    private double weight;
    private boolean enabled;
    private int innovationNumber;

    public Connection(Node inNode, Node outNode, double weight, boolean enabled, int innovationNumber) {

        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.enabled = enabled;
        this.innovationNumber = innovationNumber;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public Node getInNode() {
        return inNode;
    }

    public Node getOutNode() {
        return outNode;
    }


    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}

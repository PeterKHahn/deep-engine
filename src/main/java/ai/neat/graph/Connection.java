package ai.neat.graph;

public class Connection {

    private int inNode;
    private int outNode;

    private double weight;
    private boolean enabled;
    private int innovationNumber;

    private Connection(int inNode, int outNode, double weight, boolean enabled, int innovationNumber) {

        this.inNode = inNode;
        this.outNode = outNode;
        this.weight = weight;
        this.enabled = enabled;
        this.innovationNumber = innovationNumber;
    }


}

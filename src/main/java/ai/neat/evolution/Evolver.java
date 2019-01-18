package ai.neat.evolution;

import ai.neat.network.NeuralNetwork;
import ai.neat.parameters.NeatParameters;

import java.util.List;

public class Evolver {


    private NeatParameters parameters;

    private List<NeuralNetwork> population;

    public Evolver(NeatParameters parameters) {
        this.parameters = parameters;

        init();
    }

    private void init() {

    }

    public void start() {
        // In this method, we hope to start with the evolution process.
        for (int i = 0; i < parameters.populationSize; i++) {
            // we are going to initialize the population to population size members
        }
    }


    private double findDelta(NeuralNetwork n1, NeuralNetwork n2) {
        // TODO fill
        return -1;
    }

}

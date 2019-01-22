package ai.neat.evolution;

import ai.neat.network.Connection;
import ai.neat.network.NeuralNetwork;
import ai.neat.parameters.NeatParameters;

import java.util.ArrayList;
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

    private double fitness(NeuralNetwork network) {
        // TODO fill
        return -1;
    }


    private BreedReport findChild(NeuralNetwork n1, NeuralNetwork n2, NeatParameters parameters) {
        // TODO deal with not only weights, but with bias and activation and aggregation


        double n1Fitness = fitness(n1);
        double n2Fitness = fitness(n2);


        List<Connection> connections1 = n1.getConnections();
        List<Connection> connections2 = n2.getConnections();

        List<Connection> newConnections = new ArrayList<>();

        int numExcess = 0;
        int numDisjoint = 0;
        int numMatching = 0;

        double totalWeight = 0;


        int n = Math.max(connections1.size(), connections2.size());

        int iter1 = 0;
        int iter2 = 0;

        while (iter1 < connections1.size() && iter2 < connections2.size()) {

            Connection c1 = connections1.get(iter1);
            Connection c2 = connections2.get(iter2);

            if (c1.getInnovationNumber() < c2.getInnovationNumber()) {
                if (n1Fitness > n2Fitness || (n1Fitness == n2Fitness && Math.random() > 0.5)) {
                    newConnections.add(c1);
                }
                numDisjoint++;
                iter1++;
            } else if (c1.getInnovationNumber() > c2.getInnovationNumber()) {
                if (n2Fitness > n1Fitness || (n2Fitness == n1Fitness && Math.random() > 0.5)) {
                    newConnections.add(c2);
                }
                numDisjoint++;
                iter2++;
            } else {
                if (Math.random() > 0.5) {
                    newConnections.add(c1);
                } else {
                    newConnections.add(c2);
                }
                totalWeight += Math.abs((c1.getWeight() - c2.getWeight()));

                numMatching++;
                iter1++;
                iter2++;
            }
        }

        while (iter1 < connections1.size()) {

            if (n1Fitness > n2Fitness || (n1Fitness == n2Fitness && Math.random() > 0.5)) {
                Connection c = connections1.get(iter1);

                newConnections.add(c);
            }
            numExcess++;
        }
        while (iter2 < connections2.size()) {
            if (n2Fitness > n1Fitness || (n1Fitness == n2Fitness && Math.random() > 0.5)) {
                Connection c = connections2.get(iter2);

                newConnections.add(c);
            }
            numExcess++;
        }

        double delta = (parameters.compatibilityDisjointCoefficient * numExcess / n)
                + (parameters.compatibilityDisjointCoefficient * numDisjoint / n)
                + (parameters.compatibilityWeightCoefficient * (totalWeight / numMatching));
        return new BreedReport(newConnections, delta);
    }

    private static class BreedReport {

        private final List<Connection> connections;
        private final double delta;

        private BreedReport(List<Connection> connections, double delta) {

            this.connections = connections;
            this.delta = delta;
        }
    }

}

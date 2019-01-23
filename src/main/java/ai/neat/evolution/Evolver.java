package ai.neat.evolution;

import ai.neat.network.Connection;
import ai.neat.network.NeuralNetwork;
import ai.neat.parameters.NeatParameters;

import java.util.*;

public class Evolver {


    private NeatParameters parameters;


    public Evolver(NeatParameters parameters) {
        this.parameters = parameters;

        init();
    }

    private void init() {

    }

    private List<Species> initialSpecies() {
        List<NeuralNetwork> initialPopulation = new ArrayList<>();

        for (int i = 0; i < parameters.populationSize; i++) {
            // we are going to initialize the population to population size members
            // Each neural net will have no connections, and only its input and output nodes
        }

        Species initial = new Species(initialPopulation);

        List<Species> res = new ArrayList<>();
        res.add(initial);
        return res;

    }

    public void start(int numGenerations) {

        List<Species> speciesList = initialSpecies();


        // In this method, we hope to start with the evolution process.


        double survivalThreshold = parameters.survivalThreshold; // The fraction of guys who will breed


        for (int i = 0; i < numGenerations; i++) { // For each generation, we are doing

            for (Species s : speciesList) {
                List<NeuralNetwork> population = s.getPopulation();

                Map<NeuralNetwork, Double> fitnessMap = new HashMap<>();

                for (NeuralNetwork network : population) {
                    network.mutate(); // Mutates each network to start


                    double fitness = fitness(network); // Evaluates the fitness of network

                }

                population.sort(Comparator.comparingDouble(fitnessMap::get).reversed()); // sort the population

                // For testing purposes
                if (population.size() > 0) {
                    System.out.println("Biggest: " + population.get(0));
                    System.out.println("Smallest: " + population.get(population.size() - 1));
                }

                int threshold = Math.max((int) (population.size() * survivalThreshold), 2);

                List<NeuralNetwork> fittest = new ArrayList<>();
                for (int k = 0; k < threshold; k++) { // creating the fittest population
                    fittest.add(population.get(k));
                }


                List<NeuralNetwork> spawn = new ArrayList<>();


            }


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

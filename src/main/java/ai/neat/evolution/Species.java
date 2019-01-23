package ai.neat.evolution;

import ai.neat.network.NeuralNetwork;

import java.util.List;

public class Species {

    private List<NeuralNetwork> population;

    public Species(List<NeuralNetwork> population) {

        this.population = population;
    }


    public List<NeuralNetwork> getPopulation() {
        return population;
    }

}

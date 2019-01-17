package ai.neat.parameters;

import ai.neat.network.Connection;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Set;

public class NeatParameters {


    // [NEAT] Section
    private FitnessCriterion fitnessCriterion;
    private double fitnessThreshold;
    private boolean noFitnessTermination;
    private int populationSize;
    private boolean resetOnExtinction;

    // [DefaultStagnation] Section
    private FitnessCriterion speciesFitnessFunction;
    private int maxStagnation;
    private int speciesElitism;

    // [DefaultReproduction] Section
    private int elitism;
    private double survivalThreshold;
    private int minSpeciesSize;

    // [DefaultGenome] Section
    private Activation activationDefault;
    private double activationMutationRate;
    private Set<Activation> activationOptions;

    private Aggregation aggregationDefault;
    private double aggregationMutateRate;
    private Set<Aggregation> aggregationOptions;

    private double biasInitMean;
    private double biasInitStdev;
    private Distribution biasInitType;
    private double biasMaxValue;
    private double biasMinValue;
    private double biasMutatePower;
    private double biasMutateRate;
    private double biasReplaceRate;

    private double compatibilityThreshold;
    private double compatibilityDisjointCoefficient;
    private double compatibilityWeightCoefficient;

    public double connectionAddProbability;
    public double connectionDeleteProbability;

    private boolean enabledDefault;
    private double enabledMutationRate;
    private double enabledRateToFalseAdd;
    private double enabledRateToTrueAdd;

    private boolean feedForward;

    private InitialConnection initialConnection;

    public double nodeAddProbability;
    public double nodeDeleteProbability;
    private int numHidden;
    private int numInputs;
    private int numOutputs;
    private double responseInitStdev;
    private Distribution responseInitType;
    private double responseMaxValue;
    private double responseMinValue;
    private double responseMutatePower;
    private double responseMutateRate;
    private double responseReplaceRate;

    private boolean singleStructuralMutation;
    private boolean structuralMutationSurer;

    private double weightInitMean;
    private double weightInitStdev;
    private Distribution weightInitType;
    private double weightMaxValue;
    private double weightMinValue;
    private double weightMutatePower;
    private double weightMutateRate;
    private double weightReplaceRate;


    private NormalDistribution weightMutationDistribution;
    private NormalDistribution weightInitDistribution;

    private void init() {
        weightMutationDistribution = new NormalDistribution(0, weightMutatePower);
        // TODO take into account that weightInitDistribution might not be normal.
        // TODO we are going to have to abstract this using the Distribution class we made
        weightInitDistribution = new NormalDistribution(weightInitMean, weightInitStdev);

    }

    /**
     * Controls the mutation of a given connection given the parameters defined in this
     * object.
     *
     * @param c the connection to be changed
     * @author Peter Hahn
     */
    public void changeConnection(Connection c) {
        double weight = c.getWeight();

        double random = Math.random();
        if (random < weightMutateRate) {
            // Mutate this particular connection
            double newWeight = mutateWeight(weight);
            c.setWeight(newWeight);

        } else if (random < weightMutateRate + weightReplaceRate) {
            // Replace this particular connection
            c.setWeight(generateWeight());
        }
    }


    private double weightMutationSample() {
        return weightMutationDistribution.sample();
    }

    public double mutateWeight(double weight) {
        double weightMutation = weightMutationSample();
        return clampWeight(weight + weightMutation);

    }

    public double generateWeight() {
        return clampWeight(weightInitDistribution.sample());
    }


    public double clampBias(double val) {
        if (val < biasMinValue) {
            return biasMaxValue;
        } else if (val > biasMaxValue) {
            return biasMaxValue;
        } else {
            return val;
        }
    }

    public double clampResponse(double val) {
        if (val < responseMinValue) {
            return responseMinValue;
        } else if (val > responseMaxValue) {
            return responseMaxValue;
        } else {
            return val;
        }
    }

    public double clampWeight(double val) {
        if (val < weightMinValue) {
            return weightMinValue;
        } else if (val > weightMaxValue) {
            return weightMaxValue;
        } else {
            return val;
        }
    }


}

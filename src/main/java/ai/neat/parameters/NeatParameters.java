package ai.neat.parameters;

import ai.neat.network.Connection;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.Set;

public class NeatParameters {


    // [NEAT] Section
    public final FitnessCriterion fitnessCriterion;
    public final double fitnessThreshold;
    public final boolean noFitnessTermination;
    public final int populationSize;
    public final boolean resetOnExtinction;

    // [DefaultStagnation] Section
    public final FitnessCriterion speciesFitnessFunction;
    public final int maxStagnation;
    public final int speciesElitism;

    // [DefaultReproduction] Section
    public final int elitism;
    public final double survivalThreshold;
    public final int minSpeciesSize;

    // [DefaultGenome] Section
    public final Activation activationDefault;
    public final double activationMutationRate;
    public final Set<Activation> activationOptions;

    public final Aggregation aggregationDefault;
    public final double aggregationMutateRate;
    public final Set<Aggregation> aggregationOptions;

    public final double biasInitMean;
    public final double biasInitStdev;
    public final Distribution biasInitType;
    public final double biasMaxValue;
    public final double biasMinValue;
    public final double biasMutatePower;
    public final double biasMutateRate;
    public final double biasReplaceRate;

    public final double compatibilityThreshold;
    public final double compatibilityDisjointCoefficient;
    public final double compatibilityWeightCoefficient;

    public final double connectionAddProbability;
    // public final double connectionDeleteProbability;

    public final boolean enabledDefault;
    public final double enabledMutationRate;
    public final double enabledRateToFalseAdd;
    public final double enabledRateToTrueAdd;

    public final boolean feedForward;

    public final InitialConnection initialConnection;

    public final double nodeAddProbability;
    // public final double nodeDeleteProbability;
    public final int numHidden;
    public final int numInputs;
    public final int numOutputs;

    public final double responseInitStdev;
    public final Distribution responseInitType;
    public final double responseMaxValue;
    public final double responseMinValue;
    public final double responseMutatePower;
    public final double responseMutateRate;
    public final double responseReplaceRate;

    public final boolean singleStructuralMutation;
    public final boolean structuralMutationSurer;

    public final double weightInitMean;
    public final double weightInitStdev;
    public final Distribution weightInitType;
    public final double weightMaxValue;
    public final double weightMinValue;
    public final double weightMutatePower;
    public final double weightMutateRate;
    public final double weightReplaceRate;


    private NormalDistribution weightMutationDistribution;
    private NormalDistribution weightInitDistribution;

    private NeatParameters(NeatParametersBuilder builder) {
        this.fitnessCriterion = builder.fitnessCriterion;
        this.fitnessThreshold = builder.fitnessThreshold;
        this.noFitnessTermination = builder.noFitnessTermination;
        this.populationSize = builder.populationSize;
        this.resetOnExtinction = builder.resetOnExtinction;

        this.speciesFitnessFunction = builder.speciesFitnessFunction;
        this.maxStagnation = builder.maxStagnation;
        this.speciesElitism = builder.speciesElitism;
        this.elitism = builder.elitism;
        this.survivalThreshold = builder.survivalThreshold;
        this.minSpeciesSize = builder.minSpeciesSize;
        this.activationDefault = builder.activationDefault;
        this.activationMutationRate = builder.activationMutationRate;
        this.activationOptions = builder.activationOptions;

        this.aggregationDefault = builder.aggregationDefault;
        this.aggregationMutateRate = builder.aggregationMutateRate;
        this.aggregationOptions = builder.aggregationOptions;

        this.biasInitMean = builder.biasInitMean;
        this.biasInitStdev = builder.biasInitStdev;
        this.biasInitType = builder.biasInitType;
        this.biasMaxValue = builder.biasMaxValue;
        this.biasMinValue = builder.biasMinValue;
        this.biasMutatePower = builder.biasMutatePower;
        this.biasMutateRate = builder.biasMutateRate;
        this.biasReplaceRate = builder.biasReplaceRate;

        this.compatibilityThreshold = builder.compatibilityThreshold;
        this.compatibilityDisjointCoefficient = builder.compatibilityDisjointCoefficient;
        this.compatibilityWeightCoefficient = builder.compatibilityWeightCoefficient;

        this.connectionAddProbability = builder.connectionAddProbability;
        // this.connectionDeleteProbability = builder.connectionDeleteProbability;

        this.enabledDefault = builder.enabledDefault;
        this.enabledMutationRate = builder.enabledMutationRate;
        this.enabledRateToFalseAdd = builder.enabledRateToFalseAdd;
        this.enabledRateToTrueAdd = builder.enabledRateToTrueAdd;

        this.feedForward = builder.feedForward;
        this.initialConnection = builder.initialConnection;

        this.nodeAddProbability = builder.nodeAddProbability;
        // this.nodeDeleteProbability = builder.nodeDeleteProbability;

        this.numHidden = builder.numHidden;
        this.numInputs = builder.numInputs;
        this.numOutputs = builder.numOutputs;


        this.responseInitStdev = builder.responseInitStdev;
        this.responseInitType = builder.responseInitType;
        this.responseMaxValue = builder.responseMaxValue;
        this.responseMinValue = builder.responseMinValue;
        this.responseMutatePower = builder.responseMutatePower;
        this.responseMutateRate = builder.responseMutateRate;
        this.responseReplaceRate = builder.responseReplaceRate;


        this.singleStructuralMutation = builder.singleStructuralMutation;
        this.structuralMutationSurer = builder.structuralMutationSurer;

        this.weightInitMean = builder.weightInitMean;
        this.weightInitStdev = builder.weightInitStdev;
        this.weightInitType = builder.weightInitType;
        this.weightMaxValue = builder.weightMaxValue;
        this.weightMinValue = builder.weightMinValue;
        this.weightMutatePower = builder.weightMutatePower;
        this.weightMutateRate = builder.weightMutateRate;
        this.weightReplaceRate = builder.weightReplaceRate;

        init();
    }

    public static NeatParametersBuilder builder() {
        return new NeatParametersBuilder();
    }

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

    public static class NeatParametersBuilder {

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

        private double connectionAddProbability;
        // private double connectionDeleteProbability;

        private boolean enabledDefault;
        private double enabledMutationRate;
        private double enabledRateToFalseAdd;
        private double enabledRateToTrueAdd;

        private boolean feedForward;

        private InitialConnection initialConnection;

        private double nodeAddProbability;
        // private double nodeDeleteProbability;
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

        private NeatParametersBuilder() {

        }

        public NeatParametersBuilder fitnessCriterion(FitnessCriterion criterion) {
            this.fitnessCriterion = criterion;
            return this;
        }

        public NeatParametersBuilder fitnessThreshold(double threshold) {
            this.fitnessThreshold = threshold;
            return this;
        }

        public NeatParametersBuilder noFitnessTermination(boolean noFitnessTermination) {
            this.noFitnessTermination = noFitnessTermination;
            return this;
        }

        public NeatParametersBuilder populationSize(int populationSize) {
            this.populationSize = populationSize;
            return this;
        }

        public NeatParametersBuilder resetOnExtinction(boolean resetOnExtinction) {
            this.resetOnExtinction = resetOnExtinction;
            return this;
        }

        public NeatParametersBuilder speciesFitnessFunction(FitnessCriterion criterion) {
            this.speciesFitnessFunction = criterion;
            return this;
        }

        public NeatParametersBuilder maxStagnation(int maxStagnation) {
            this.maxStagnation = maxStagnation;
            return this;
        }

        public NeatParametersBuilder speciesElitism(int speciesElitism) {
            this.speciesElitism = speciesElitism;
            return this;
        }

        public NeatParametersBuilder elitism(int elitism) {
            this.elitism = elitism;
            return this;
        }

        public NeatParametersBuilder survivalThreshold(double survivalThreshold) {
            this.survivalThreshold = survivalThreshold;
            return this;
        }

        public NeatParametersBuilder minSpeciesSize(int minSpeciesSize) {
            this.minSpeciesSize = minSpeciesSize;
            return this;
        }

        public NeatParametersBuilder activationDefault(Activation activation) {
            this.activationDefault = activation;
            return this;
        }

        public NeatParametersBuilder activationMutationRate(double activationMutationRate) {
            this.activationMutationRate = activationMutationRate;
            return this;
        }

        public NeatParametersBuilder activationOptions(Set<Activation> options) {
            this.activationOptions = options;
            return this; // TODO make this so you can add one at a time
        }

        public NeatParametersBuilder aggregationDefault(Aggregation aggregation) {
            this.aggregationDefault = aggregation;
            return this;
        }

        public NeatParametersBuilder aggregationMutationRate(double activationMutationRate) {
            this.aggregationMutateRate = aggregationMutateRate;
            return this;

        }

        public NeatParametersBuilder aggregationOptions(Set<Aggregation> aggregationOptions) {
            this.aggregationOptions = aggregationOptions;
            return this;
        }

        public NeatParametersBuilder biasInitMean(double biasInitMean) {
            this.biasInitMean = biasInitMean;
            return this;
        }

        public NeatParametersBuilder biasInitStdev(double biasInitStdev) {
            this.biasInitStdev = biasInitStdev;
            return this;
        }

        public NeatParametersBuilder biasInitType(Distribution biasInitType) {
            this.biasInitType = biasInitType;
            return this;
        }

        public NeatParametersBuilder biasMaxValue(double biasMaxValue) {
            this.biasMaxValue = biasMaxValue;
            return this;
        }

        public NeatParametersBuilder biasMinValue(double biasMinValue) {
            this.biasMinValue = biasMinValue;
            return this;
        }

        public NeatParametersBuilder biasMutatePower(double biasMutatePower) {
            this.biasMutatePower = biasMutatePower;
            return this;
        }

        public NeatParametersBuilder biasMutateRate(double biasMutateRate) {
            this.biasMutateRate = biasMutateRate;
            return this;
        }

        public NeatParametersBuilder biasReplaceRate(double biasReplaceRate) {
            this.biasReplaceRate = biasReplaceRate;
            return this;
        }

        public NeatParametersBuilder compatibilityThreshold(double compatibilityThreshold) {
            this.compatibilityThreshold = compatibilityThreshold;
            return this;
        }

        public NeatParametersBuilder compatibilityDisjointCoefficient(double compatibilityDisjointCoefficient) {
            this.compatibilityDisjointCoefficient = compatibilityDisjointCoefficient;
            return this;
        }

        public NeatParametersBuilder compatibilityWeightCoefficient(double compatibilityWeightCoefficient) {
            this.compatibilityWeightCoefficient = compatibilityWeightCoefficient;
            return this;
        }

        public NeatParametersBuilder connectionAddProbability(double connectionAddProbability) {
            this.connectionAddProbability = connectionAddProbability;
            return this;
        }

        /*public NeatParametersBuilder connectionDeleteProbability(double connectionDeleteProbability) {
            this.connectionDeleteProbability = connectionDeleteProbability;
            return this;
        }*/

        public NeatParametersBuilder enabledDefault(boolean enabledDefault) {
            this.enabledDefault = enabledDefault;
            return this;
        }

        public NeatParametersBuilder enabledMutationRate(double enabledMutationRate) {
            this.enabledMutationRate = enabledMutationRate;
            return this;
        }

        public NeatParametersBuilder enabledRateToFalseAdd(double enabledRateToFalseAdd) {
            this.enabledRateToFalseAdd = enabledRateToFalseAdd;
            return this;
        }

        public NeatParametersBuilder enabledRateToTrueAdd(double enabledRateToTrueAdd) {
            this.enabledRateToTrueAdd = enabledRateToTrueAdd;
            return this;
        }

        public NeatParametersBuilder feedForward(boolean feedForward) {
            this.feedForward = feedForward;
            return this;
        }

        public NeatParametersBuilder initialConnection(InitialConnection initialConnection) {
            this.initialConnection = initialConnection;
            return this;
        }

        public NeatParametersBuilder nodeAddProbability(double nodeAddProbability) {
            this.nodeAddProbability = nodeAddProbability;
            return this;
        }

        /*public NeatParametersBuilder nodeDeleteProbability(double nodeDeleteProbability) {
            this.nodeDeleteProbability = nodeDeleteProbability;
            return this;
        }*/

        public NeatParametersBuilder numHidden(int numHidden) {
            this.numHidden = numHidden;
            return this;
        }

        public NeatParametersBuilder numInputs(int numInputs) {
            this.numInputs = numInputs;
            return this;
        }

        public NeatParametersBuilder numOutputs(int numOutputs) {
            this.numOutputs = numOutputs;
            return this;
        }

        public NeatParametersBuilder responseInitStdev(double responseInitStdev) {
            this.responseInitStdev = responseInitStdev;
            return this;
        }

        public NeatParametersBuilder responseInitType(Distribution responseInitType) {
            this.responseInitType = responseInitType;
            return this;
        }

        public NeatParametersBuilder responseMaxValue(double responseMaxValue) {
            this.responseMaxValue = responseMaxValue;
            return this;
        }

        public NeatParametersBuilder responseMinValue(double responseMinValue) {
            this.responseMinValue = responseMinValue;
            return this;
        }

        public NeatParametersBuilder responseMutatePower(double responseMutatePower) {
            this.responseMutatePower = responseMutatePower;
            return this;
        }

        public NeatParametersBuilder responseMutateRate(double responseMutateRate) {
            this.responseMutateRate = responseMutateRate;
            return this;
        }

        public NeatParametersBuilder responseReplaceRate(double responseReplaceRate) {
            this.responseReplaceRate = responseReplaceRate;
            return this;
        }

        public NeatParametersBuilder weightInitMean(double weightInitMean) {
            this.weightInitMean = weightInitMean;
            return this;
        }

        public NeatParametersBuilder weightInitStedev(double weightInitStdev) {
            this.weightInitStdev = weightInitStdev;
            return this;
        }

        public NeatParametersBuilder weightInitType(Distribution weightInitType) {
            this.weightInitType = weightInitType;
            return this;
        }

        public NeatParametersBuilder weightMaxValue(double weightMaxValue) {
            this.weightMaxValue = weightMaxValue;
            return this;
        }

        public NeatParametersBuilder weightMinValue(double weightMinValue) {
            this.weightMinValue = weightMinValue;
            return this;
        }

        public NeatParametersBuilder weightMutatePower(double weightMutatePower) {
            this.weightMutatePower = weightMutatePower;
            return this;
        }

        public NeatParametersBuilder weightMutateRate(double weightMutateRate) {
            this.weightMutateRate = weightMutateRate;
            return this;
        }

        public NeatParametersBuilder weightReplaceRate(double weightReplaceWeight) {
            this.weightReplaceRate = weightReplaceWeight;
            return this;
        }


    }


}

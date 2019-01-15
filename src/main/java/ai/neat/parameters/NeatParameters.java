package ai.neat.parameters;

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

    private double connectionAddProbability;
    private double connectionDeleteProbability;

    private boolean enabledDefault;
    private double enabledMutationRate;
    private double enabledRateToFalseAdd;
    private double enabledRateToTrueAdd;

    private boolean feedForward;

    private InitialConnection initialConnection;

    private double nodeAddProb;
    private double nodeDeleteProb;
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

}

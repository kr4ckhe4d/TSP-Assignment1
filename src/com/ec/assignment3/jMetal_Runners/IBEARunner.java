package com.ec.assignment3.jMetal_Runners;

import com.ec.assignment3.Logger;
import com.ec.jMetal_Implementations.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.util.List;

public class IBEARunner extends AbstractAlgorithmRunner {
    public static String Algorithm_i;
    public static int numOfCities;
    public static boolean fixedGrid;
    public static int generations = 1000;

    public static void main(String[] args) throws Exception {
        // COMMANDLINE PARAMETERS
        numOfCities = Integer.valueOf(args[0]);
        Algorithm_i = args[2];
        fixedGrid = Boolean.valueOf(args[3]);

        // DECLARATIONS
        TSP_InstanceProblem problem;
        Algorithm<List<TSP_InstanceSolution>> algorithm;
        CrossoverOperator<TSP_InstanceSolution> crossover;
        MutationOperator<TSP_InstanceSolution> mutation;
        SelectionOperator<List<TSP_InstanceSolution>, TSP_InstanceSolution> selection;
        String ObjectivesFile = "FUN.tsv";
        Logger OL = new Logger("\t");

        // DEFINE PROBLEM
        problem = new TSP_InstanceProblem(numOfCities, Algorithm_i, fixedGrid)
                .setCitySpaceLimit(20);

        // DEFINE CROSSOVER CHARACTERISTICS
        double crossoverProbability = 0.9;
        crossover = new RandomSwapCrossover(crossoverProbability);

        // DEFINE MUTATION CHARACTERISTICS
        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        mutation = new RandomShiftMutator(mutationProbability, fixedGrid);

        // DEFINE SELECTION CHARACTERISTICS
        selection = new BinaryTournamentSelection<TSP_InstanceSolution>();

        // DEFINE IBEA AS THE RUNNING ALGORITHM
        algorithm = new G1_IBEABuilder<TSP_InstanceSolution>(problem, crossover, mutation)
                .setSelection(selection)
                .setMaxEvaluations(generations)
                .setPopulationSize(20)
                .build();

        // RUN ALGORITHM
        String fGridText = (fixedGrid) ? "fixed":"continuous";
        System.out.println("IBEA RUNNING {" + Algorithm_i + "} on " + fGridText + " [" + numOfCities + "] cities over " + generations + "generations");
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // OBTAIN RESULTS
        List<TSP_InstanceSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        System.out.println(algorithm.getResult());

        // LOG INFO
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        // OUTPUT RESULTS (PARETO FRONT)
        printFinalSolutionSet(population);

        String exName = (fixedGrid) ? "Ex1" : "Ex2";
        OL.setExperimentName(exName, numOfCities, "IBEA", Algorithm_i);
        OL.addPopulation(population);
        OL.readObjectivesFile(ObjectivesFile);
        OL.calcPareto();
        OL.writeCombinedResultsFile();
    }
}

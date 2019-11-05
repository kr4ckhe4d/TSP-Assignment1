package com.ec.assignment3.jMetal_Runners;

import com.ec.assignment3.OutputLogger;
import com.ec.jMetal_Implementations.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.TournamentSelection;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.util.List;

public class NSGAIIRunner extends AbstractAlgorithmRunner {
    public static String Algorithm_i;
    public static int numOfCities;
    public static boolean fixedGrid;
    public static int generations = 1000;

    public static void main(String[] args) throws Exception {
        // COMMANDLINE PARAMETERS
        numOfCities = Integer.valueOf(args[0]);
        Algorithm_i = args[2];
        fixedGrid = Boolean.valueOf(args[3]);
//        if(args.length > 4) {
//            generations = Integer.valueOf(args[4]);
//        }

        // DECLARATIONS
        TSP_InstanceProblem problem;
        Algorithm<List<TSP_InstanceSolution>> algorithm;
        CrossoverOperator<TSP_InstanceSolution> crossover;
        MutationOperator<TSP_InstanceSolution> mutation;
        SelectionOperator<List<TSP_InstanceSolution>, TSP_InstanceSolution> selection;
        String ObjectivesFile = "FUN.tsv";
        OutputLogger OL = new OutputLogger("\t");

        // DEFINE PROBLEM
        problem = new TSP_InstanceProblem(numOfCities, Algorithm_i, fixedGrid);
        problem.setCitySpaceLimit(20);

        // DEFINE CROSSOVER CHARACTERISTICS
        double crossoverProbability = 0.9;
        crossover = new RandomCrossover(crossoverProbability);

        // DEFINE MUTATION CHARACTERISTICS
        double mutationProbability = 1.0 / problem.getNumberOfVariables();
        mutation = new deviationMutator(mutationProbability, fixedGrid);

        // DEFINE SELECTION CHARACTERISTICS
        selection = new TournamentSelection<TSP_InstanceSolution>( numOfCities / 2 );

        // DEFINE NSGA_II AS THE RUNNING ALGORITHM
        algorithm = new NSGAIIBuilder<TSP_InstanceSolution>(problem, crossover, mutation)
                .setSelectionOperator(selection)
                .setMaxIterations(generations)
                .setPopulationSize(20)
                .build();

        // RUN ALGORITHM
        String fGridText = (fixedGrid) ? "fixed":"continuous";
        System.out.println("NSGAII RUNNING {" + Algorithm_i + "} on " + fGridText + " [" + numOfCities + "] cities over " + generations + "generations");
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(algorithm)
                .execute();

        // OBTAIN RESULTS
        List<TSP_InstanceSolution> population = algorithm.getResult();
        long computingTime = algorithmRunner.getComputingTime();

        // LOG INFO
        JMetalLogger.logger.info("Total execution time: " + computingTime + "ms");

        // OUTPUT RESULTS (PARETO FRONT)
        printFinalSolutionSet(population);

        String exName = (fixedGrid) ? "Ex1" : "Ex2";
        OL.setExperimentName(exName, numOfCities, "NSGAII", Algorithm_i);
        OL.addPopulation(population);
        OL.readObjectivesFile(ObjectivesFile);
        OL.calcPareto();
        OL.writeCombinedResultsFile();
    }
}

package com.ec.assignment3.jMetal_Runners;

import com.ec.jMetal_Implementations.*;
import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.algorithm.multiobjective.spea2.SPEA2Builder;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.util.AlgorithmRunner;
import org.uma.jmetal.util.JMetalLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MOEA_Runner {
    Random RNG = new Random(System.currentTimeMillis());
    public static String RunnerType;
    public static String Algorithm_i;
    public static int totalCities;

    //===== OUTERLOOP VARIABLES
    private static int outerPopulationSize = 20;
    private static int outerCities; //number of cells
    private static int outer_generations = 25;
    private static double outer_crossoverProbability = 0.9;
    private static double outer_mutationProbability = 0.9;
    private static TSP_InstanceProblem outer_problem;
    private static CrossoverOperator<TSP_InstanceSolution> outer_crossover;
    private static MutationOperator<TSP_InstanceSolution> outer_mutation;
    private static SelectionOperator<List<TSP_InstanceSolution>, TSP_InstanceSolution> outer_selection;
    private static Algorithm<List<TSP_InstanceSolution>> outer_algorithm;

    //===== INNERLOOP VARIABLES
    private static int innerPopulationSize = 20;
    private static int innerCities; //number of cities per cell
    private static int inner_generations = 40;
    private static double inner_crossoverProbability = 0.9;
    private static double inner_mutationProbability = 0.9;
    private static TSP_InstanceProblem inner_problem;
    private static CrossoverOperator<TSP_InstanceSolution> inner_crossover;
    private static MutationOperator<TSP_InstanceSolution> inner_mutation;
    private static SelectionOperator<List<TSP_InstanceSolution>, TSP_InstanceSolution> inner_selection;
    private static Algorithm<List<TSP_InstanceSolution>> inner_algorithm;

    public enum LoopType {
        INNER,
        OUTER,
        BOTH
    }

    //===== CONSTRUCTOR AND SETTERS
    public MOEA_Runner() {
    }

    public MOEA_Runner setRunnerType(String runner) {
        RunnerType = runner;
        return this;
    }

    public MOEA_Runner setAlgorithm_i(String alg) {
        Algorithm_i = alg;
        return this;
    }

    public MOEA_Runner setNumOfCities(int N) {
        totalCities = N;
        calculateOuterAndInnerCitySizes();
        return this;
    }

    public MOEA_Runner setGenerations(LoopType whichLoop, int G) {
        switch (whichLoop) {
            case INNER:
                inner_generations = G;
                break;
            case OUTER:
                outer_generations = G;
                break;
            case BOTH:
                inner_generations = G;
                outer_generations = G;
                break;
        }
        return this;
    }

    //===== GETTERS
    public String getAlgorithm_i() {
        return Algorithm_i;
    }

    public int getNumOfCities(LoopType whichLoop) {
        switch (whichLoop) {
            case INNER:
                return innerCities;
            case OUTER:
                return outerCities;
            default:
                return totalCities;
        }
    }

    public int getGenerations(LoopType whichLoop) {
        switch (whichLoop) {
            case INNER:
                return inner_generations;
            case OUTER:
                return outer_generations;
            default:
                return inner_generations + outer_generations;
        }
    }

    //===== RUNNERS
    private void calculateOuterAndInnerCitySizes() {
        double A = Math.floor(Math.sqrt(totalCities));
        double B;
        do {
            B = ((double)totalCities) / A;
            A--;
        } while(B%1 != 0.0);

        innerCities = (int) B;
        outerCities = (int) A+1;
    }

    //===== LOOPS
    public List<TSP_InstanceSolution> runOuterLoop() {
        long computingTime = 0;
        List<TSP_InstanceSolution> result = new ArrayList<>();

        System.out.println("CELLS WITH CITIES: \t" + outerCities + "\nCITIES PER CELL: \t" + innerCities);

        // SET PARAMS
        outer_problem =     new TSP_InstanceProblem_Mixed(outerCities, Algorithm_i, true, innerCities, this)
                .setCitySpaceLimit(20)
                .setInnerGenerations(inner_generations);
        outer_crossover =   new BinaryCrossover(outer_crossoverProbability);
        outer_mutationProbability = 1.0 / outer_problem.getNumberOfVariables();
        outer_mutation =    new binaryMutator(outer_mutationProbability);
        outer_selection =   new BinaryTournamentSelection<TSP_InstanceSolution>();

        switch(RunnerType) {
            case "NSGAII":
                outer_algorithm = new NSGAIIBuilder<TSP_InstanceSolution>(outer_problem, outer_crossover, outer_mutation)
                        .setSelectionOperator(outer_selection)
                        .setMaxIterations(outer_generations)
                        .setPopulationSize(outerPopulationSize)
                        .build();
                break;

            case "SPEA2":
                outer_algorithm = new SPEA2Builder<TSP_InstanceSolution>(outer_problem, outer_crossover, outer_mutation)
                        .setSelectionOperator(outer_selection)
                        .setMaxIterations(outer_generations)
                        .setPopulationSize(outerPopulationSize)
                        .build();
                break;

            case "IBEA":
                if(outer_generations < outerPopulationSize) {
                    outer_generations = outerPopulationSize + 1;    // for some strange reason, jMetal's IBEA encapsulation requires this invariant property to hold.
                }
                outer_algorithm = new G1_IBEABuilder<TSP_InstanceSolution>(outer_problem, outer_crossover, outer_mutation)
                        .setSelection(outer_selection)
                        .setMaxEvaluations(outer_generations)
                        .setPopulationSize(outerPopulationSize)
                        .build();
                break;
        }

        // RUN OUTER LOOP ( inner loop runs within evaluation routine )
        AlgorithmRunner algorithmRunner = new AlgorithmRunner.Executor(outer_algorithm)
                .execute();

        // OBTAIN RESULTS
        result = outer_algorithm.getResult();
        computingTime = algorithmRunner.getComputingTime();
        JMetalLogger.logger.info("Total PROGRAM execution time: " + computingTime + "ms");

        return result;
    }

    public List<TSP_InstanceSolution> runInnerLoop() {  // returns
        System.out.print("RUNNING INNER LOOP (" + innerCities + " Cities over " + inner_generations + " generations)..... ");
        long computingTime = 0;
        List<TSP_InstanceSolution> result = new ArrayList<>();

        inner_problem =     new TSP_InstanceProblem(innerCities, Algorithm_i, false)
                .setCitySpaceLimit(1); // within single cell grid
        inner_crossover =   new RandomCrossover(inner_crossoverProbability);
        inner_mutationProbability = 1.0 / inner_problem.getNumberOfVariables();
        inner_mutation =    new deviationMutator(inner_mutationProbability, false);
        inner_selection =   new BinaryTournamentSelection<TSP_InstanceSolution>();

        innerPopulationSize = 2*outerCities;    // Reproduction in NSGAii (jMetal) requires pair-wise population (even number)

        switch(RunnerType) {
            case "NSGAII":
                inner_algorithm = new NSGAIIBuilder<TSP_InstanceSolution>(inner_problem, inner_crossover, inner_mutation)
                        .setSelectionOperator(inner_selection)
                        .setMaxIterations(inner_generations)
                        .setPopulationSize(innerPopulationSize)
                        .build();
                break;

            case "SPEA2":
                inner_algorithm = new SPEA2Builder<TSP_InstanceSolution>(inner_problem, inner_crossover, inner_mutation)
                        .setSelectionOperator(inner_selection)
                        .setMaxIterations(inner_generations)
                        .setPopulationSize(innerPopulationSize)
                        .build();
                break;

            case "IBEA":
                if(inner_generations < innerPopulationSize) {
                    inner_generations = innerPopulationSize + 1;    // for some strange reason, jMetal's IBEA encapsulation requires this invariant property to hold.
                }
                inner_algorithm = new G1_IBEABuilder<TSP_InstanceSolution>(inner_problem, inner_crossover, inner_mutation)
                        .setSelection(inner_selection)
                        .setMaxEvaluations(inner_generations)
                        .setPopulationSize(innerPopulationSize)
                        .build();
                break;
        }

        // RUN INNER ALGORITHM
        AlgorithmRunner algorithmRunner;
        while(result.size() < innerPopulationSize) {
             algorithmRunner = new AlgorithmRunner.Executor(inner_algorithm)
                    .execute();

            List<TSP_InstanceSolution> bestSols = inner_algorithm.getResult();
            result.addAll(bestSols);
            computingTime += algorithmRunner.getComputingTime();
        }

        System.out.println("DONE:" + computingTime + "ms");
//        JMetalLogger.logger.info("Total Inner Loop execution time: " + computingTime + "ms");

        return result;
    }

}

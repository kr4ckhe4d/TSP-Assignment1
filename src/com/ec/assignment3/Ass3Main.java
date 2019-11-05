package com.ec.assignment3;

import com.ec.assignment3.jMetal_Runners.IBEARunner;
import com.ec.assignment3.jMetal_Runners.MOEA_Runner;
import com.ec.assignment3.jMetal_Runners.NSGAIIRunner;
import com.ec.assignment3.jMetal_Runners.SPEA2Runner;
import com.ec.jMetal_Implementations.TSP_InstanceSolution;
import org.uma.jmetal.runner.AbstractAlgorithmRunner;

import java.util.Arrays;
import java.util.List;

public class Ass3Main {
    public static int innerGenerations = 40;    //default   *NOTE: IBEA requires generations > 20
    public static int outerGenerations = 25;     //default  *NOTE: IBEA requires generations > 20

    public static void runMixed(String args[]) throws Exception {
        int numOfCities = Integer.valueOf(args[0]);
        String RunnerType = args[1];
        String Algorithm_i = args[2];

        MOEA_Runner TEST = new MOEA_Runner()
                .setRunnerType(RunnerType)
                .setAlgorithm_i(Algorithm_i)
                .setNumOfCities(numOfCities)
                .setGenerations(MOEA_Runner.LoopType.OUTER, outerGenerations)
                .setGenerations(MOEA_Runner.LoopType.INNER, innerGenerations);

        List<TSP_InstanceSolution> result = TEST.runOuterLoop();

        AbstractAlgorithmRunner.printFinalSolutionSet(result);
        System.out.println(result);

        Logger OL = new Logger(" \t");
        OL.setExperimentName("Ex3", numOfCities, RunnerType, Algorithm_i);
        OL.addPopulation(result);
        OL.readObjectivesFile("FUN.tsv");
        OL.calcPareto();
        OL.writeCombinedResultsFile();
        OL.printOutput();
    }

    public static void runSingular(String args[], boolean fixedGrid) throws Exception {
        String[] newArgs = Arrays.copyOf(args, args.length + 1);
        newArgs[args.length] = (fixedGrid) ? "true":"false";

        switch (newArgs[1]) {
            case "NSGAII":
                NSGAIIRunner.main(newArgs);
                break;

            case "SPEA2":
                SPEA2Runner.main(newArgs);
                break;

            case "IBEA":
                IBEARunner.main(newArgs);
                break;
        }
    }

    public static void main(String[] args) throws Exception {
        String[] newArgs = Arrays.copyOfRange(args, 1, args.length);

        switch (args[0]) {
            case "Ex2":
                System.out.println("Running Exercise 2 - Discrete evolutionary multi-objective algorithms for TSP instances");
                runSingular(newArgs, true);
                break;

            case "Ex3":
                System.out.println("Running Exercise 3 - Continuous evolutionary multi-objective algorithms for TSP instances");
                runSingular(newArgs, false);
                break;

            case "Ex4":
                System.out.println("Running Exercise 4 - Mixed evolutionary multi-objective algorithms for TSP instances");
                runMixed(newArgs);
                break;
        }
    }
}

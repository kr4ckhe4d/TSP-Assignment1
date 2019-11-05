package com.ec.EAs;

import com.ec.Objects.HamiltonCycle;
import com.ec.Objects.InstancePopulation;
import com.ec.TSP_Solvers.TSP_Solver;
import com.ec.TSP_Solvers.TSP_SolverFactory;

import java.util.Collections;
import java.util.Random;

public class PerformanceMeasure {
    private static Random RNG = new Random(System.currentTimeMillis());
    private static int populationSize = 10;
    private static int numOfCities = 100;
    private static int totalGenerations = 1000;
    private static int x_lim = 20;
    private static int y_lim = 20;
    private boolean debug_flag = false;

    InstancePopulation InstancePop = null;
//    HamiltonCycle bestInstance = null;

    public PerformanceMeasure(boolean debug) {
        InstancePop = new InstancePopulation(populationSize, numOfCities, x_lim, y_lim);
        debug_flag = debug;
    }

    public static InstancePopulation evolveInstance(InstancePopulation IP) {    // returns of population of 1.5x the original size
        InstancePopulation population = IP.clone();
        InstancePopulation nextGen = new InstancePopulation();
        Mutator mu = new Mutator();
        Crossover co = new Crossover();

        // perform Crossover
        InstancePopulation afterCO = new InstancePopulation();
        for(int i=0; i<population.size()/2; i++) {
            afterCO.add(
                    co.transplantCrossover(population.get(i), population.get(i + 5),
                            RNG.nextBoolean(), RNG.nextBoolean(), x_lim, y_lim)
            );
        }

        // perform Mutation
        for (HamiltonCycle inst : afterCO) {
            nextGen.add(mu.DisperseMutator(inst, 0.5).clone());
        }

        // add old parents as well
        for (HamiltonCycle inst : population) {
            nextGen.add(inst.clone());
        }
        return nextGen;
    }

    public HamiltonCycle runComparison_maximisePerformance(String algorithmA, String algorithmB, String measureType) {
        int thisGeneration = 1;
        InstancePopulation currentPopulation = InstancePop.clone();
        InstancePopulation bestPopulation = InstancePop.clone();
        HamiltonCycle bestInstance = null;
        double bestFitness = Double.NEGATIVE_INFINITY;

        TSP_Solver A = new TSP_SolverFactory().setAlgorithm(algorithmA);
        TSP_Solver B = new TSP_SolverFactory().setAlgorithm(algorithmB);

        // Begin loop
        while( thisGeneration <= totalGenerations ) {
            // Evolve Instance
            InstancePopulation evolvedInstance = evolveInstance(currentPopulation);

            // Measure Performance and Set as Fitness
            evalAndSetPerformanceDifference(evolvedInstance, A, B, measureType);
            // sort and trim
            InstancePopulation nextGen = evolvedInstance.clone();
            Collections.sort(nextGen);
            nextGen.trimFromBackToSize(populationSize);

            bestInstance = nextGen.get(0);
            double this_fitness = bestInstance.getAverageFitness();


            if(this_fitness > bestFitness) {
                bestFitness = this_fitness;
                // show output if result is better
                System.out.print("TAKEN \t{G_" + thisGeneration + "} \t");
                System.out.println("[" + this_fitness + "]");
            } else {
                if(debug_flag) {
                    System.out.print("\t\t{G_" + thisGeneration + "} \t");
                    System.out.println("[" + this_fitness + "]");
                }
            }

            currentPopulation = nextGen.clone();
            thisGeneration++;
        }

        return bestInstance;
    }

    /*
     * BELOW SECTION ONLY FOR double-type returns: calculation subroutines only
     */

    public void evalAndSetPerformanceDifference(InstancePopulation population, TSP_Solver A, TSP_Solver B, String measureType) {
//        InstancePopulation result = population.clone();
        for(HamiltonCycle instance : population) {
            if(instance.getAverageFitness() <= 0) {
                double fitness = compareAlgorithms(A, B, instance, measureType);
                instance.setAverageFitness(fitness);
            }
        }
    }

    public double compareAlgorithms(TSP_Solver A, TSP_Solver B, HamiltonCycle HC, String measureType) {   // compare 1-2   // type is either 'r' or 'p'
        double algorithmFitness = 0.0;
        double current_best_fitness_A = -1.0;
        double current_best_fitness_B = -1.0;

        for(int i=0; i<5; i++) {
            // randomly initialise a tour
            Collections.shuffle(HC);

            // run algorithm A and collect fitness of best average tour output
            double f_A = A.solve(HC).calculateCost();
            if ((f_A < current_best_fitness_A)||(current_best_fitness_A < 0)) {
                current_best_fitness_A = f_A;
            }

            // run algorithm B and collect fitness of best tour output
            // replace current best fitness with best value over 5 iterations
            double f_B = B.solve(HC).calculateCost();
            if ((f_B < current_best_fitness_B)||(current_best_fitness_B < 0)) {
                current_best_fitness_B = f_B;
            }
        }

        if(measureType.equals("minus") ) {  // either r_ij(minus) or p_ij(divide)
            algorithmFitness = current_best_fitness_A - current_best_fitness_B;
        } else if(measureType.equals("divide")) {
            algorithmFitness = current_best_fitness_A / current_best_fitness_B;
        }

        return algorithmFitness;
    }
}

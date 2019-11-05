package com.ec.EAs;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;

import java.util.Collections;

public class Algorithms {
    public static double distanceBetween(City A, City B) {
        double A_x = (double) A.getX();
        double A_y = (double) A.getY();
        double B_x = (double) B.getX();
        double B_y = (double) B.getY();

        return Math.sqrt(Math.pow(A_x-B_x, 2) + Math.pow(A_y-B_y, 2));
    }


    public static int twoOptLocalSearchGenerationsRan;
    private static HamiltonCycle reptitionCopy;
    private static int repetition_counter;
    private static final int repetition_limit = 100;
    private static boolean verbose = false;

    /*  This version of the twoOptLS is now obsolete. Use either the encapsulated class version in EAs package or the blackbox version in the TSP_Solvers package

    public static HamiltonCycle newTwoOptLS(HamiltonCycle HC) {
        twoOptLocalSearchGenerationsRan = 0;
        HamiltonCycle pi = HC.clone();
        while (true) {
            double best_improvement = 0.0;
            int best_current = 0;
            int best_other = 0;

            for (int i = 0; i < pi.size(); i++) {
                City x_i = pi.get(i);
                City x_i1 = pi.get((i+1) % pi.size());
                for(int j=i+2; j<pi.size(); j++) {
                    if(!((i==0) && (j==pi.size()-1))) {
                        City x_j = pi.get(j);
                        City x_j1 = pi.get((j+1) % pi.size());

                        double dist1 = distanceBetween(x_i, x_i1);
                        double dist2 = distanceBetween(x_j, x_j1);
                        double dist3 = distanceBetween(x_i, x_j);
                        double dist4 = distanceBetween(x_i1, x_j1);

                        double improvement = dist1 + dist2 - dist3 - dist4;

                        if(improvement > best_improvement) {
                            best_improvement = improvement;
                            best_current = i;
                            best_other = j;
                        }
                    }
                }
            }

            if(best_improvement > 0.0) {
                Collections.reverse(pi.subList(best_current+1, best_other+1));  // fromIndex is inclusive, toIndex is exclusive
            } else {
                break;
            }

            twoOptLocalSearchGenerationsRan++;
            if(verbose) {
                System.out.print("\t{G_" + twoOptLocalSearchGenerationsRan + "} \t");
                System.out.print("[" + pi.calculateCost() + "] \t");    pi.printCycle();
            }
        }
        return pi;
    }
     */


    public static HamiltonCycle onePlusOneEA(HamiltonCycle HC, String type, boolean verbose) {
        HamiltonCycle bestInstance = HC.clone();
        Mutator M = new Mutator();
        newTwoOptLS solver = null;

        int eaIterations = 0;
        int bestFitness = 0;
        while (eaIterations < 10000) {
            HamiltonCycle copy = bestInstance.clone();
            HamiltonCycle current = null;

            // PERFORM MUTATION OVER DIFFERENT INITIAL SOLUTIONS
            HamiltonCycle mutatedInstance = null;
            if(type.equals("transpose")) {
                mutatedInstance = M.TransposeMutator(copy, 0.5);
            } else if(type.equals("median")) {
                mutatedInstance = M.MedianMutator(copy, 0.5);
            } else if(type.equals("disperse")) {
                mutatedInstance = M.DisperseMutator(copy, 0.5);
            } else if(type.equals("hackney")) {
                mutatedInstance = M.HackneyMutator(copy, 0.2);
            }

            int avg_of_5 = 0;
            // loop over 5 times
            for (int i = 0; i < 5; i++) {
                Collections.shuffle(mutatedInstance);
                // EVALUATE 2-OPT ON MUTATED INSTANCE
                solver = new newTwoOptLS(mutatedInstance,false);
                solver.run();

                current = solver.getHC();
                avg_of_5 = ( avg_of_5 + solver.getFitness() ) / 2;
            }
            // Fitness value is the average generations of 5 runs
            int fitness = avg_of_5;

            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestInstance = current.clone();
                System.out.print("TAKEN \t");
                System.out.print("{G_" + eaIterations + "} \t[" + fitness + "] \t");
                copy.printCycle();
            } else {
                if(verbose) {
                    System.out.print("\t \t");
                    System.out.print("{G_" + eaIterations + "} \t[" + fitness + "] \t");
                    copy.printCycle();
                }
            }

            eaIterations++;
        }
        System.out.println("BEST FITNESS: \t" + bestFitness);
        return bestInstance;
    }
}

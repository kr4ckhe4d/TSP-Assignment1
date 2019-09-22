package com.ec;



import com.ec.beans.City;
import com.ec.beans.Solution;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: Li He 2019-09-22
 */
public class OnePlusOneEA {
    static Random ran = new Random(System.currentTimeMillis());

    public static double distanceBetween(City A, City B) {
        double A_x = (double) A.getX();
        double A_y = (double) A.getY();
        double B_x = (double) B.getX();
        double B_y = (double) B.getY();

        return Math.sqrt(Math.pow(A_x - B_x, 2) + Math.pow(A_y - B_y, 2));
    }

    private static boolean verbose = false;

    public static Solution onePlusOneEA(Solution sol, String type, boolean verbose) {
        Solution bestInstance = sol.copy();
//        Mutation mu = new Mutation();
        int eaIterations = 0;
        int bestFitness = 0;
        double bestDistance = 0;
        while (eaIterations < 10000) {
            Solution copy = bestInstance.copy();
            Solution current = null;

            Solution mutatedInstance = null;
            mutatedInstance = medianMutation(copy, 0.5);
            ExecutorService executor = Executors.newFixedThreadPool(5);
            TwoOptLocalSearch[] solvers = new TwoOptLocalSearch[5];
            for (int i = 0; i < 5; i++) {
                Collections.shuffle(mutatedInstance);
                solvers[i] = new TwoOptLocalSearch(mutatedInstance, false);
                executor.execute(solvers[i]);
            }
            executor.shutdown();
            while (!executor.isTerminated()) {
            }

            int fitness = 0;
            double smallCost=solvers[0].getSol().updateDist1();
            current=solvers[0].getSol();
            double temCost=0;
            for (int j = 1; j < 5; j++) {
                fitness += solvers[j].getFitnessValue();
                temCost=solvers[j].getSol().updateDist1();
                if(temCost<smallCost) {
                    current = solvers[j].getSol();
                    smallCost=temCost;
                }
            }
            fitness /= 5;

            if (fitness > bestFitness) {
                bestFitness = fitness;
                bestInstance = current.copy();
                bestDistance =smallCost;
                System.out.print("TAKEN \t");
                System.out.println("{G_" + eaIterations + "} \t" + fitness + " \t" + smallCost + " \t");
                copy.printXY();
            } else {
                if (verbose) {
                    System.out.print("\t \t");
                    System.out.println("{G_" + eaIterations + "} \t" + fitness + " \t" + smallCost + " \t");
                    copy.printXY();
                }
            }

            eaIterations++;
        }
        System.out.println("BEST FITNESS: \t" + bestFitness);
        System.out.println("BEST Cost: \t" + bestDistance);
        return bestInstance;
    }

    public static ArrayList<Integer> translateIndividual(Solution individual){
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < individual.size(); i++) {
            arrayList.add(individual.get(i).getId());
        }
        return arrayList;
    }

    public static Solution medianMutation(Solution sol, double pickRatio) {
        double medianX = 0.0;
        double medianY = 0.0;

        for (City c : sol) {
            medianX += c.getX();
            medianY += c.getY();
        }

        medianX = medianX / (double) sol.size();
        medianY = medianY / (double) sol.size();

        int numbertoTake = (int) Math.floor(pickRatio * sol.size());

        for (int i = 0; i <= numbertoTake; i++) {
            int t = ran.nextInt(sol.size());
            City temp = sol.get(t);

            double posX = Math.abs(medianX + temp.getX()) / 2.0;
            double posY = Math.abs(medianY + temp.getY()) / 2.0;

            City closerCity = new City(temp.getId(), false, posX, posY);

            if (!sol.existCityPosition(closerCity)) {
                temp.setY(posY);
            }
        }
//        sol.normaliseCitySpace();
        return sol;
    }
}

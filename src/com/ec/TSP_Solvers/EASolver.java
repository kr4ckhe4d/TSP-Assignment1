package com.ec.TSP_Solvers;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * This class solves TSP with the best evolutionary algorithm selected from
 * Assignment 1.
 *
 * Parent selector: elitism, 0.5 Crossover: pmx Mutation: inversion Offspring
 * selector: elitism,0.5 Population: 10 Generation: 1000
 *
 * @author choyukchow
 *
 */
public class EASolver implements TSP_Solver {

    private static int POPULATION = 10;
    private static int GENERATION = 1000;
    private static double PARENT_ELITE_RATIO = 0.5;
    private static double OFFSPRING_ELITE_RATIO = 0.5;

    private static Random RNG = new Random(System.currentTimeMillis());

    private ArrayList<HamiltonCycle> elitismSelect(
        ArrayList<HamiltonCycle> currGen, int howManyToSelect, double eliteRatio) {
        ArrayList<HamiltonCycle> currGenCopy = new ArrayList<HamiltonCycle>(currGen);
        ArrayList<HamiltonCycle> nextGen = new ArrayList<HamiltonCycle>();

        // Picks from front of sortedCurrGen, based on eliteRatio
        int eliteGroupSize = (int) (eliteRatio * howManyToSelect);

        for (int i = 0; i < eliteGroupSize; i++) {
            HamiltonCycle elite = currGenCopy.get(0);
            Double bestFitness = elite.calculateCost();
            for (int j = 0; j < currGenCopy.size(); j++) {
                Double currFitness = currGenCopy.get(j).calculateCost();
                if (currFitness < bestFitness) {
                    elite = currGenCopy.get(j);
                    bestFitness = currFitness;
                }
            }
            nextGen.add(elite);
            currGenCopy.remove(elite);
        }

        HamiltonCycle x;
        // fill up the remaining spots with random picking
        while (nextGen.size() < howManyToSelect) {
            int pos = RNG.nextInt(currGenCopy.size());
            x = currGenCopy.get(pos);
            nextGen.add(x);
            currGenCopy.remove(x);
        }

        return nextGen;
    }

    private ArrayList<HamiltonCycle> pmxCrossover(HamiltonCycle parent1, HamiltonCycle parent2) {
        Integer[] p1 = parent1.getTourOrder();
        Integer[] p2 = parent2.getTourOrder();

        int size = p1.length;

        ArrayList<HamiltonCycle> result = new ArrayList<>();
        Integer[] c1 = new Integer[size];
        Integer[] c2 = new Integer[size];

        Integer region_begin = RNG.nextInt(size - 1);
        Integer region_end = region_begin + RNG.nextInt(size - region_begin - 1) + 1;

        Integer[] subP1 = Arrays.copyOfRange(p1, region_begin, region_end);
        System.arraycopy(subP1, 0, c1, region_begin, subP1.length);

        Integer[] subP2 = Arrays.copyOfRange(p2, region_begin, region_end);
        System.arraycopy(subP2, 0, c2, region_begin, subP2.length);

        for (int i = region_begin; i < region_end; i++) {
            Integer num_to_insert = c2[i];

            // Do for this number only if number does not already exists in C1
            if (!Arrays.asList(c1).contains(num_to_insert)) {
                int k = Arrays.asList(p2).indexOf(c1[i]);   					// find (C1[i] in P2) ->position k
                while ((k >= region_begin) && (k < region_end)) {
                    k = Arrays.asList(p2).indexOf(c1[k]);   					// bounce k until it goes out of stage1 region
                }
                c1[k] = num_to_insert;                      					// put num_to_insert into C1[k]
            }
        }

        for (int i = region_begin; i < region_end; i++) {   					// for each position in crossover region
            Integer num_to_insert = c1[i];

            // Do for this number only if number does not already exists in C2
            if (!Arrays.asList(c2).contains(num_to_insert)) {
                int k = Arrays.asList(p1).indexOf(c2[i]);   					// find (C2[i] in P1) ->position k
                while ((k >= region_begin) && (k < region_end)) {
                    k = Arrays.asList(p1).indexOf(c2[k]);   					// bounce k until it goes out of stage1 region
                }
                c2[k] = num_to_insert;                      					// put num_to_insert into C2[k]
            }
        }

        for (int i = 0; i < size; i++) {
            if (c1[i] == null) {
                c1[i] = p2[i];
            }
            if (c2[i] == null) {
                c2[i] = p1[i];
            }
        }

        parent1.setTourOrder(c1);
        parent2.setTourOrder(c2);
        result.add(parent1);
        result.add(parent2);
        return result;
    }

    /**
     * Selects two points in the individual
     *
     * @param bound The limit in the index to be selected
     * @return int[] of points to be mutated.
     */
    private static int[] picktwo(int bound) {
        int[] result = new int[2];
        int index1 = RNG.nextInt(bound);
        int index2 = index1;
        // possibility to get into infinite loop is ignored
        // as a streak breaker will introduce artificial number
        while (index1 == index2) {
            index2 = RNG.nextInt(bound);
        }
        result[0] = index1;
        result[1] = index2;
        return result;
    }

    private void inversionMutate(HamiltonCycle individual) {
        int size = individual.size();
        int[] allele = picktwo(size);

        // calculate the length of the sequence to be swapped then halve it
        int length;
        int head = allele[0];
        int tail = allele[1];
        if (head < tail) {
            length = tail - head;
        } else {
            length = size - head + tail;
        }
        length++;
        length /= 2;

        for (int i = 0; i < length; i++) {
            City temp = individual.get((head + i) % size);
            individual.set((head + i) % size, individual.get((tail + size - i) % size));
            individual.set((tail + size - i) % size, temp);
        }
    }

    @Override
    public HamiltonCycle solve(HamiltonCycle HC) {
        int currentGeneration = 0;

        // Creates the initial population.
        ArrayList<HamiltonCycle> population = new ArrayList<HamiltonCycle>();
        for (int i = 0; i < POPULATION; i++) {
            Collections.shuffle(HC);
            population.add(HC);
        }

        while (currentGeneration < GENERATION) {
            ArrayList<HamiltonCycle> offsprings = new ArrayList<HamiltonCycle>();

            // Applies crossover for each pair of parents selected from the population.
            ArrayList<HamiltonCycle> parentTempPool = new ArrayList<HamiltonCycle>(population);
            for (int i = 0; i < POPULATION; i += 2) {
                ArrayList<HamiltonCycle> parents = elitismSelect(
                    parentTempPool, 2 /* howManyToSelect */, PARENT_ELITE_RATIO);
                for (HamiltonCycle p : parents) {
                    if (parentTempPool.contains(p)) {
                        parentTempPool.remove(p);
                    }
                }
                offsprings.addAll(pmxCrossover(parents.get(0), parents.get(1)));
            }

            // Applies mutation for offsprings
            for (HamiltonCycle offspring : offsprings) {
                inversionMutate(offspring);
            }

            offsprings.addAll(population);
            ArrayList<HamiltonCycle> nextPopulation = elitismSelect(
                offsprings, POPULATION, OFFSPRING_ELITE_RATIO);
            population = nextPopulation;
            currentGeneration++;
        }

        // Returns the best individual in the population as the solution.
        HamiltonCycle best = population.get(0);
        double bestFitness = best.calculateCost();
        for (HamiltonCycle individual : population) {
            double fitness = individual.calculateCost();
            if (fitness < bestFitness) {
                best = individual;
                bestFitness = fitness;
            }
        }
        return best;
    }

}

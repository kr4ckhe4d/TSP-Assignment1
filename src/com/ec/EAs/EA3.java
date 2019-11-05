/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.EAs;

import com.ec.Objects.*;
import com.ec.TSP_Solvers.EAConfig;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Population-based Evolutionary Algorithm 3. CO = Cluster; MU = Attract;
 *
 * @author Xiaogang
 */
public class EA3 implements EA {

    private EAConfig config;
    private HamiltonCycle best;
    private InstancePopulation finalPopulation;

    @Override
    public void report() {
        int i = 1;
        for(HamiltonCycle HC : finalPopulation) {
            HC.renumber();
//            System.out.println(HC.getAverageFitness());
            System.out.println("##############################################");
            System.out.println("NAME : EA3_" + i);
            System.out.println("COMMENT : Excercise 2 EA3 Instance " + i++);
            System.out.println("TYPE : TSP");
            System.out.println("DIMENSION : " + HC.size());
            System.out.println("EDGE_WEIGHT_TYPE : EUC_2D");
            System.out.println("NODE_COORD_SECTION");
            HC.printCityInfo();
            System.out.println("EOF");
        }
    }

    public EA3(EAConfig config) {
        this.config = config;
    }

    @Override
    public void start() {
        int loopCount = 0;
        InstancePopulation population = new InstancePopulation(config.maxPopulationSize, config.cityAmount, 20, 20);
        InstancePopulation nextGen;
        Mutator mu = new Mutator();
        Crossover co = new Crossover();
        Random RNG = new Random(System.currentTimeMillis());
        while (loopCount < config.maxGen) {
            loopCount++;

            if (config.debug) {
                System.out.println("loopCount = " + loopCount);
                System.out.println("populationSize = " + population.size());
            }
            nextGen = new InstancePopulation();

            // produce offsprings and add to next generation
            for (int i = 0; i < config.maxPopulationSize / 2; i++) {
                nextGen.add(co.transplantCrossover(population.get(i), population.get(i + 5), RNG.nextBoolean(), RNG.nextBoolean(), 20, 20));
            }

            if (config.debug) {
                System.out.println("nextGenSize after CO = " + nextGen.size());
            }

            // mutate current generation and add to next generation
            for (int i = 0; i < config.maxPopulationSize; i++) {
                nextGen.add(mu.DisperseMutator(population.get(i), 0.5));
            }

            if (config.debug) {
                System.out.println("nextGenSize after MU = " + nextGen.size());
            }

            // evaluate each individual using 2-opt, use multiple threads to accelerate
            for (int i = 0; i < nextGen.size(); i++) {
                ExecutorService executor = Executors.newFixedThreadPool(config.trails);
                newTwoOptLS[] tasks = new newTwoOptLS[config.trails];
//                HamiltonCycle instance = nextGen.get(i).clone();
                for (int j = 0; j < config.trails; j++) {
                    HamiltonCycle instance = nextGen.get(i).clone();
                    Collections.shuffle(instance, RNG);
                    tasks[j] = new newTwoOptLS(instance, false);
                    executor.execute(tasks[j]);
                }
                executor.shutdown();

                long time = System.currentTimeMillis();
                while (!executor.isTerminated()) {
                    // a debug feature
                    if (config.debug) {
                        if (System.currentTimeMillis() - time > 2000) {
                            tasks[0].getHC().printCityInfo();
                            for (int j = 0; j < config.trails; j++) {
                                tasks[j].getHC().printCycle();
                            }
                            System.out.println("EXIT");
                            System.exit(0);
                        }
                    }
                }

                // set Fitness to each individual
                double fitness = 0;
                for (int j = 0; j < config.trails; j++) {
                    fitness += tasks[j].getFitness();
                }
                fitness /= config.trails;
                nextGen.get(i).setAverageFitness(fitness);
            }

            // sort the individuals according to their fitness values
            Collections.sort(nextGen);

            if (config.debug) {
                System.out.println("nextGenSize = " + population.size());
            }

            // trim the population so only best ones are left
            while (nextGen.size() > config.maxPopulationSize) {
                nextGen.remove(nextGen.size() - 1);
            }

            population = nextGen.clone();
        }
        best = population.get(0).clone();
        best.renumber();
        finalPopulation = population;
    }

}

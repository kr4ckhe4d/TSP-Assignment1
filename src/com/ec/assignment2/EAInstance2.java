package com.ec.assignment2;

import com.ec.Individual;
import com.ec.Population;
import com.ec.TSPProblem;
import com.ec.beans.City;
import com.ec.operators.MutationNCrossover;
import com.ec.selection.FitnessProportional;

import java.util.ArrayList;
import java.util.List;

/**
 *  This algorithm utilises
 *  Selection : Tournament Selection
 *  Crossover: BOP Crossover
 *  Mutation: Random Linear Projection Mutation
 */
public class EAInstance2 {

    private static int POPULATION_SIZE = 10;
    private static int GENERATION_SIZE = 1000;

    public static void main(String args[]) {
        TSPProblem tsp = new TSPProblem("data/eil51.tsp.txt");
        System.out.println(tsp.getCities());
        //Setting and initialising Popuation
        Population population = new Population(POPULATION_SIZE, true);
        System.out.println(population.getFittest());
        System.out.println("Initial Distance: " + population.getFittest().getDistance());

        //Evolve to New Generation
        population = nextGeneration(population,tsp.getCities());
        for (int i = 0; i < GENERATION_SIZE; i++) {
            population = nextGeneration(population,tsp.getCities());
        }
        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + population.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(population.getFittest());

    }

    private static Population nextGeneration(Population population, List<City> cities){
        Population newPopulation = new Population(population.populationSize(), false);
        int elitismIndex = 0;
        boolean elitism = true;
        if (elitism) {
            newPopulation.saveIndividual(0, population.getFittest());
            elitismIndex = 1;
        }

        for (int i = elitismIndex; i < newPopulation.populationSize(); i++) {
            FitnessProportional fp = new FitnessProportional();

            Individual parent1 = fp.select(population);
            Individual parent2 = fp.select(population);
            //System.out.println("Parent 1: "+parent1);
            //System.out.println("Parent 2: "+parent2);

            //Converting to match Crossover input parameter
            ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<ArrayList<Integer>>();
            ArrayList<Integer> tempArrayList = new ArrayList<>();
            ArrayList<Integer> temp = new ArrayList<>();
            for (int j = 0; j < parent1.individualSize(); j++) {
                tempArrayList.add(parent1.getCity(j).getId());
            }
            arrayLists.add(tempArrayList);

            for (int j = 0; j < parent2.individualSize(); j++) {
                temp.add(parent2.getCity(j).getId());
            }
            arrayLists.add(temp);

            List<Individual> children = new ArrayList<>();
            children = MutationNCrossover.orderBasedLevel(parent1, parent2);
            newPopulation.saveIndividual(i,children.get(0));
            //System.out.println("Child 1: "+ children.get(2));
            //System.out.println("Child 2: "+ children.get(3));
            if(i < newPopulation.populationSize() - 1) {
                i++;
                newPopulation.saveIndividual(i, children.get(1));
            }
        }

        for (int i = elitismIndex; i < newPopulation.populationSize(); i++) {
            //newPopulation.saveIndividual(i,MutationNCrossover.displacementLevelInversion(newPopulation.getIndividual(i)));
            newPopulation.saveIndividual(i,MutationNCrossover.invidualLevelInversion(newPopulation.getIndividual(i)));
            //newPopulation.saveIndividual(i,MutationNCrossover.scrambleLevelInversion(newPopulation.getIndividual(i)));

        }

        return newPopulation;
    }


}


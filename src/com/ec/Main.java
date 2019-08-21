package com.ec;

import com.ec.beans.City;
import com.ec.selection.TournamentSelection;

import java.util.ArrayList;
import java.util.List;

import static com.ec.operators.Crossover.PMXCrossover;
import static com.ec.operators.Crossover.orderCrossover;
import static com.ec.operators.Mutation.insertMutation;
import static com.ec.operators.TranslateIndividual.translateChild;
import static com.ec.operators.TranslateIndividual.translateChildren;

public class Main {

    private static int POPULATION_SIZE = 20;
    private static int GENERATION_SIZE = 2000;


    public static void main(String args[]) {
        TSPProblem tsp = new TSPProblem("data/eil51.tsp.txt");
        System.out.println(tsp.getCities());
        //Setting Popuation
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
            TournamentSelection ts = new TournamentSelection(5);

            Individual parent1 = ts.select(population);
            Individual parent2 = ts.select(population);
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
            children = translateChildren(orderCrossover(arrayLists), cities);
            newPopulation.saveIndividual(i,children.get(2));
            //System.out.println("Child 1: "+ children.get(2));
            //System.out.println("Child 2: "+ children.get(3));
            if(i < newPopulation.populationSize() - 1) {
                i++;
                newPopulation.saveIndividual(i, children.get(3));
            }
        }

        for (int i = elitismIndex; i < newPopulation.populationSize(); i++) {
            ArrayList<Integer> tempArrayList = new ArrayList<>();
            for (int j = 0; j < newPopulation.getIndividual(i).individualSize(); j++) {
                tempArrayList.add(newPopulation.getIndividual(i).getCity(j).getId());
            }
            newPopulation.saveIndividual(i,translateChild(insertMutation(tempArrayList), cities));
        }

        return newPopulation;
    }

}

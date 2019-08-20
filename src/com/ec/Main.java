package com.ec;

import com.ec.selection.TournamentSelection;

import java.util.ArrayList;

import static com.ec.operators.Crossover.orderCrossover;
import static com.ec.operators.Mutation.insertMutation;
import static com.ec.operators.TranslateIndividual.translateChildren;

public class Main {

    public static void main(String args[]) {
        TSPProblem tsp = new TSPProblem("data/eil51.tsp.txt");
        System.out.println(tsp.getCities());
        //Setting Popuation
        Population population = new Population(52, true);
        //Setting Tournament
        TournamentSelection ts = new TournamentSelection(5);

        Individual parent1 = ts.select(population);
        Individual parent2 = ts.select(population);
        System.out.println("Parent 1");
        for (int i = 0; i < TSPProblem.getCitySize(); i++) {
            System.out.print(parent1.getCity(i).getId() + " ");
        }
        System.out.println();
        System.out.println("Parent 2");
        for (int i = 0; i < TSPProblem.getCitySize(); i++) {
            System.out.print(parent2.getCity(i).getId() + " ");
        }
        System.out.println();

//        double rate = 0.3; //Possibility to mutation

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

        insertMutation(arrayLists.get(0));

//        System.out.println(arrayLists);

        ArrayList<Individual> individuals = new ArrayList<>();

        individuals = translateChildren(orderCrossover(arrayLists), tsp.getCities());

        System.out.println("After the crossover, the children are ");
        for (int i = 0; i < individuals.size(); i++) {
            for (int j = 0; j < individuals.get(i).individualSize(); j++) {
                System.out.print(individuals.get(i).getCity(j).getId() + "  ");
            }
            System.out.println();
        }

        /**
         *
         * selection
         *
         * */

//        population.saveIndividual(population.populationSize() - 1,individuals.get(0));
//        population.saveIndividual(population.populationSize() + 0,individuals.get(1));
//
//        Individual individual = ts.select(population);
//
//        for (int i = 0; i < TSPProblem.getCitySize(); i++) {
//            System.out.print(individual.getCity(i).getId() + " ");
//        }
//        System.out.println();

    }

}

package com.ec;

import com.ec.selection.TournamentSelection;

public class Main {

    public static void main(String args[]){
        TSPProblem tsp = new TSPProblem("data/eil51.tsp.txt");
        System.out.println(tsp.getCities());
        //Setting Popuation
        Population population = new Population(50,true);
        //Setting Tournament
        TournamentSelection ts = new TournamentSelection(5);

        Individual parent1 = ts.select(population);
        Individual parent2 = ts.select(population);
        System.out.println("Parent 1");
        for(int i=0; i<TSPProblem.getCitySize(); i++){
            System.out.print(parent1.getCity(i).getId()+" ");
        }
        System.out.println();
        System.out.println("Parent 2");
        for(int i=0; i<TSPProblem.getCitySize(); i++){
            System.out.print(parent2.getCity(i).getId()+" ");
        }
        System.out.println();

    }

}

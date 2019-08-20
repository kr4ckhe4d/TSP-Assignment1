package com.ec.selection;

import com.ec.Individual;
import com.ec.Population;

public class TournamentSelection implements Selection{

    private int tournamentSize;

    public TournamentSelection(int tournamentSize){
        this.tournamentSize = tournamentSize;
    }

    @Override
    public Individual select(Population population) {
        // Setting new tournament population
        Population tournament = new Population(tournamentSize,false);

        //Get random individual for each tournament and save it
        for(int i=0; i<tournamentSize; i++){
            int randomId = (int) (Math.random()* population.populationSize());
            tournament.saveIndividual(i, population.getIndividual(randomId));
        }

        //Simple Printing
        System.out.println("Random Tournament");
        for(int i=0; i<tournamentSize; i++){
            System.out.println("Individual"+ i +" -- Fitness = "+ tournament.getIndividual(i).getFitness());
        }
        //Get the fittest individual from the tournament and return
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}

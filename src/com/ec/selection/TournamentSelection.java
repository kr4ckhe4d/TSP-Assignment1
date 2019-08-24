package com.ec.selection;

import com.ec.Individual;
import com.ec.Population;

public class TournamentSelection implements Selection{

    private int tournamentSize;

    /**
     * Constructor sets the size of tournament
     * @param tournamentSize
     */
    public TournamentSelection(int tournamentSize){
        this.tournamentSize = tournamentSize;
    }

    /**
     * Random Individual is added to the tournament population and
     * returns the fittest individual
     * @param population
     * @return individual
     */
    @Override
    public Individual select(Population population) {
        // Setting new tournament population
        Population tournament = new Population(tournamentSize,false);

        //Get random individual for each tournament and save it
        for(int i=0; i<tournamentSize; i++){
            int randomId = (int) (Math.random()* population.populationSize());
            tournament.saveIndividual(i, population.getIndividual(randomId));
        }

        //Get the fittest individual from the tournament and return
        Individual fittest = tournament.getFittest();
        return fittest;
    }
}

package com.ec.selection;

import com.ec.Individual;
import com.ec.Population;

public class FitnessProportional implements Selection {

    /**
     * Fitness Proportional algorithm based on roulette wheel
     * Returns the best individual based on their fitness value.
     * @param population
     * @return individual
     */
    @Override
    public Individual select(Population population) {
        int index = -1;
        int totalSum = 0;
        for(int i=0; i<population.populationSize(); i++){
            totalSum += population.getFittest().getFitness();
        }
        int random = (int) (Math.random() * totalSum);
        int partialSum = 0;
        for(int i=0; i<population.populationSize(); i++){
            partialSum += population.getFittest().getFitness();
            if(partialSum >= random){
                index = i;
                break;
            }
        }
        random = (int) (Math.random() * totalSum);
        if(index == -1){
            return population.getIndividual(random);
        }
        return population.getIndividual(index);
    }

}

package com.ec.selection;

import com.ec.Individual;
import com.ec.Population;

import java.util.concurrent.ThreadLocalRandom;

public class FitnessProportional implements Selection {

    @Override
    public Individual select(Population population) {
        int index = 0;
        int totalSum = 0;
        for(int i=0; i<population.populationSize(); i++){
            totalSum += population.getFittest().getFitness();
        }
        int random = ThreadLocalRandom.current().nextInt(0,totalSum);
        int partialSum = 0;
        for(int i=0; i<population.populationSize(); i++){
            partialSum += population.getFittest().getFitness();
            if(partialSum >= random){
                index = i;
                break;
            }
        }
        random = ThreadLocalRandom.current().nextInt(0,totalSum);
        return population.getIndividual(random);
    }

}

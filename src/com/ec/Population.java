package com.ec;

import java.util.ArrayList;
import java.util.List;

public class Population {
    //Stores population
    private List<Individual> individuals;

    // Construct a population
    public Population(int populationSize, boolean initialise){
        individuals = new ArrayList<>(populationSize);
        initialiseIndividual(populationSize);
        if(initialise){
            for(int i=0; i< populationSize; i++) {
                Individual newIndividual = new Individual();
                newIndividual.generateIndividual();
                saveIndividual(i,newIndividual);
            }
        }
    }

    //Sets individuals with null to prevent IndexOutOfBound Exception
    private void initialiseIndividual(int populationSize){
        for(int i=0; i<populationSize; i++){
            individuals.add(null);
        }
    }

    //Stores the individual to population
    public void saveIndividual(int index, Individual individual){
        individuals.set(index,individual);

    }

    //Get the individual from population
    public Individual getIndividual(int index){
        return individuals.get(index);
    }

    //Get the best fittest individual from population
    public Individual getFittest(){
        Individual fittest = individuals.get(0);

        for(int i=1; i<populationSize(); i++){
            if(fittest.getFitness() <= getIndividual(i).getFitness()){
                fittest = getIndividual(i);
            }
        }
        return fittest;
    }

    //Gets the population size
    public int populationSize(){
        return individuals.size();
    }


}

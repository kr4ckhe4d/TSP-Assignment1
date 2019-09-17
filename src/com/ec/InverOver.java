package com.ec;

import com.ec.beans.City;

import java.util.Random;

public class InverOver {

    private static final int POPULATION_SIZE = 50;
    private static final int GENERATION_SIZE = 2000;
    private Random rnd = new Random();

    public void algorithm(){

        Population population = new Population(POPULATION_SIZE, true);

        Individual bestIndividual = population.getFittest();
        Individual currIndividual = population.getFittest();

        double currCost;
        double record = -1.0;

        for (int i = 0; i < GENERATION_SIZE; i++) {
            for (int j = 0; j < POPULATION_SIZE; j++) {
                currIndividual = population.getIndividual(j);
                currIndividual = process(currIndividual, population);
                population.saveIndividual(j, currIndividual);
            }

            bestIndividual = population.getFittest();
            currCost = bestIndividual.getDistance();
            if(record > currCost || record == -1.0) {
                record = currCost;
                System.out.println((i+1) + ": ***Best Solution*** = "+ record);
            }
        }

        //System.out.println("Final Best = "+bestIndividual);
        //System.out.println("Final Best Distance = "+bestIndividual.getDistance());
    }

    private Individual process(Individual individual, Population population) {
        Individual newInd = individual;
        int size = individual.individualSize();

        int index = rnd.nextInt(size-1);

        boolean running = true;
        int nextIndex;
        double prob;
        Individual otherInd;
        City currCity, nextCity;

        while (running) {
            nextIndex = index;
            otherInd = newInd;
            prob = rnd.nextDouble();
            while (nextIndex == index) {
                if (prob <= 0.02) {
                    nextIndex = rnd.nextInt(size-1);
                }
                else {
                    while (otherInd == individual) {
                        otherInd = population.getIndividual(rnd.nextInt(size-1));
                    }
                    currCity = newInd.getCity(index);
                    nextCity = otherInd.getNextCity(currCity.getId());
                    nextIndex = newInd.getCityIndex(nextCity.getId());
                }
            }

            if ((index + 1) == nextIndex) {
                running = false;
            } else if (index == (newInd.individualSize() - 1) && nextIndex == 0) {
                running = false;
            }

            else {
                if (index == (size - 1)){
                    index = 0;
                } else {
                    index++;
                }
                inverseSubset(newInd, index, nextIndex);
            }

        }

        if (newInd.getFitness() < individual.getFitness()) {
            individual = newInd;
        }

        return individual;
    }

    private void inverseSubset(Individual individual, int posA, int posB) {
        int a = posA;
        int b = posB;

        while (a < individual.individualSize() && b >= 0) {
            swapCities(individual, a, b);
            a++;
            b--;
        }
        if (a == individual.individualSize()){
            a = 0;
        } else {
            b = individual.individualSize() - 1;
        }

        while (a < b) {
            swapCities(individual, a, b);
            a++;
            b--;
        }
    }

    private void swapCities(Individual individual, int posA, int posB) {
        City temp = individual.getCity(posA);
        individual.setCity(posA, individual.getCity(posB));
        individual.setCity(posB, temp);
    }

    public static void main(String[] args) {
        TSPProblem tsp = new TSPProblem("data/eil51.tsp.txt");
        System.out.println(tsp.getCities());
        InverOver io = new InverOver();
        io.algorithm();
    }

}

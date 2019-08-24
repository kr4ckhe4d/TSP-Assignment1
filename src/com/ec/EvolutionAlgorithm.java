package com.ec;

import com.ec.beans.City;
import com.ec.operators.Crossover;
import com.ec.operators.Mutation;
import com.ec.operators.TranslateIndividual;
import com.ec.selection.FitnessProportional;
import com.ec.selection.Selection;
import com.ec.selection.TournamentSelection;

import java.util.ArrayList;
import java.util.List;

public class EvolutionAlgorithm {

    public static final int TOURNAMENT_SELECTION = 1;
    public static final int FITNESS_SELECTION = 2;
    public static final int ORDER_CROSSOVER = 3;
    public static final int PMX_CROSSOVER = 4;
    public static final int CYCLE_CROSSOVER = 5;
    public static final int INSERT_MUTATION = 6;
    public static final int SWAP_MUTATION = 7;
    public static final int INVERSION_MUTATION = 8;

    private int selection = 0;
    private int crossover = 0;
    private int mutation = 0;
    private int GENERATION_SIZE = 0;
    private int POPULATION_SIZE = 0;
    private int elitismSize = 0;
    private boolean elitism = false;
    private int tournamentSize = 0;

    public void setSelection(int selection) throws Exception {
        if(selection == TOURNAMENT_SELECTION) {
            this.selection = selection;
        }
        else if(selection == FITNESS_SELECTION) {
            this.selection = selection;
        }
        else{
            throw new Exception("Invalid Selection");
        }
    }

    public void setCrossover(int crossover) throws Exception{
        if(crossover == ORDER_CROSSOVER){
            this.crossover = crossover;
        }
        else if(crossover == PMX_CROSSOVER){
            this.crossover = crossover;
        }
        else if(crossover == CYCLE_CROSSOVER){
            this.crossover = crossover;
        }
        else{
            throw new Exception("Invalid Crossover");
        }
    }

    public void setMutation(int mutation) throws Exception{
        if(mutation == INSERT_MUTATION){
            this.mutation = mutation;
        }
        else if(mutation == SWAP_MUTATION){
            this.mutation = mutation;
        }
        else if(mutation == INVERSION_MUTATION){
            this.mutation = mutation;
        }
        else{
            throw new Exception("Invalid Mutation");
        }
    }

    public void setElitism(int elitismIndex) {
        this.elitismSize = elitismIndex;
        this.elitism = true;
    }

    public void setGenerationSize(int generationSize) throws Exception{
        if(generationSize > 0) {
            this.GENERATION_SIZE = generationSize;
        }
        else {
            throw new Exception("Invalid Generation Size");
        }
    }

    public void setPopulationSize(int populationSize) throws Exception{
        if(populationSize > 0) {
            this.POPULATION_SIZE = populationSize;
        }
        else {
            throw new Exception("Invalid Population Size");
        }
    }

    public void setTournamentSize(int tournamentSize) throws Exception{
        if(tournamentSize > 0) {
            this.tournamentSize = tournamentSize;
        }
        else {
            throw new Exception("Invalid Tournament Size");
        }
    }

    public void run(List<City> cities) throws Exception{
        if(selection == 0){
            throw new Exception("Set Selection using setselection()");
        }
        if(crossover == 0){
            throw new Exception("Set Crossover using setCrossover()");
        }
        if(mutation == 0){
            throw new Exception("Set Mutation using setMutation()");
        }

        Population population = new Population(POPULATION_SIZE, true);
        population = nextGeneration(population, cities);
        for (int i = 0; i < GENERATION_SIZE; i++) {
            population = nextGeneration(population, cities);
        }
        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + population.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(population.getFittest());
    }


    private Population nextGeneration(Population population, List<City> cities) throws Exception{
        Population newPopulation = new Population(population.populationSize(), false);
        if(elitism){
            for (int i = 0; i < elitismSize; i++) {
                newPopulation.saveIndividual(i, population.getIndividual(i));
            }
        }

        Selection selectionAlg = null;
        if(selection == TOURNAMENT_SELECTION){
            if(tournamentSize == 0){
                throw new Exception("Set the tournament size using setTournamentSize()");
            }
            selectionAlg = new TournamentSelection(tournamentSize);
        }
        else if(selection == FITNESS_SELECTION) {
            selectionAlg = new FitnessProportional();
        }


        for (int i = elitismSize; i < population.populationSize(); i++) {
            int npi = i;
            Individual parent1 = selectionAlg.select(population);
            Individual parent2 = selectionAlg.select(population);

            ArrayList<ArrayList<Integer>> arrayLists = new ArrayList<>();
            arrayLists.add(TranslateIndividual.translateIndividual(parent1));
            arrayLists.add(TranslateIndividual.translateIndividual(parent2));

            List<Individual> children = new ArrayList<>();
            if(crossover == ORDER_CROSSOVER){
                children = TranslateIndividual.translateChildren(Crossover.orderCrossover(arrayLists),cities);
            }
            else if(crossover == PMX_CROSSOVER){
                children = TranslateIndividual.translateChildren(Crossover.PMXCrossover(arrayLists),cities);
            }
            else if(crossover == CYCLE_CROSSOVER){
                children = TranslateIndividual.translateChildren(Crossover.cycleCrossover(arrayLists),cities);
            }

            //Setting Children to new Population
            if(children.size() == 2){
                newPopulation.saveIndividual(i,children.get(0));
                if(i < newPopulation.populationSize() - 1) {
                    i++;
                    newPopulation.saveIndividual(i, children.get(1));
                }
            }
            else if(children.size() == 4){
                newPopulation.saveIndividual(i,children.get(2));
                if(i < newPopulation.populationSize() - 1) {
                    i++;
                    newPopulation.saveIndividual(i, children.get(3));
                }
            }

        }

        for (int i = elitismSize; i < newPopulation.populationSize(); i++) {
            ArrayList<Integer> tempArrayList = TranslateIndividual.translateIndividual(newPopulation.getIndividual(i));
            if(mutation == INSERT_MUTATION){
                newPopulation.saveIndividual(i,TranslateIndividual.translateChild(Mutation.insertMutation(tempArrayList),cities));
            }
            else if(mutation == SWAP_MUTATION){
                newPopulation.saveIndividual(i,TranslateIndividual.translateChild(Mutation.swapMutation(tempArrayList),cities));
            }
            else if(mutation == INVERSION_MUTATION){
                newPopulation.saveIndividual(i,TranslateIndividual.translateChild(Mutation.inversionMutation(tempArrayList),cities));
            }
        }
        
        return newPopulation;
    }

}

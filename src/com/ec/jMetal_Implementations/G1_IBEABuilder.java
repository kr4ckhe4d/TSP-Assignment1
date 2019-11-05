package com.ec.jMetal_Implementations;

import org.uma.jmetal.algorithm.multiobjective.ibea.IBEA;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.operator.SelectionOperator;
import org.uma.jmetal.operator.impl.selection.BinaryTournamentSelection;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.solution.Solution;
import org.uma.jmetal.util.AlgorithmBuilder;

import java.util.List;

public class G1_IBEABuilder<S extends Solution<?>> implements AlgorithmBuilder<IBEA<S>> {
    private Problem<S> problem;
    private int populationSize;
    private int archiveSize;
    private int maxEvaluations;
    private CrossoverOperator<S> crossover;
    private MutationOperator<S> mutation;
    private SelectionOperator<List<S>, S> selection;

    public G1_IBEABuilder(Problem<S> problem, CrossoverOperator<S> crossoverOperator, MutationOperator<S> mutationOperator) {
        this.problem = problem;
        this.populationSize = 100;
        this.archiveSize = 100;
        this.maxEvaluations = 25000;
        double crossoverProbability = 0.9D;
        double crossoverDistributionIndex = 20.0D;
        this.crossover = crossoverOperator;
        double mutationProbability = 1.0D / (double)problem.getNumberOfVariables();
        double mutationDistributionIndex = 20.0D;
        this.mutation = mutationOperator;
        this.selection = new BinaryTournamentSelection();
    }


    public int getPopulationSize() {
        return this.populationSize;
    }

    public int getArchiveSize() {
        return this.archiveSize;
    }

    public int getMaxEvaluations() {
        return this.maxEvaluations;
    }

    public CrossoverOperator<S> getCrossover() {
        return this.crossover;
    }

    public MutationOperator<S> getMutation() {
        return this.mutation;
    }

    public SelectionOperator<List<S>, S> getSelection() {
        return this.selection;
    }

    public G1_IBEABuilder<S> setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
        return this;
    }

    public G1_IBEABuilder<S> setArchiveSize(int archiveSize) {
        this.archiveSize = archiveSize;
        return this;
    }

    public G1_IBEABuilder<S> setMaxEvaluations(int maxEvaluations) {
        this.maxEvaluations = maxEvaluations;
        return this;
    }

    public G1_IBEABuilder<S> setCrossover(CrossoverOperator<S> crossover) {
        this.crossover = crossover;
        return this;
    }

    public G1_IBEABuilder<S> setMutation(MutationOperator<S> mutation) {
        this.mutation = mutation;
        return this;
    }

    public G1_IBEABuilder<S> setSelection(SelectionOperator<List<S>, S> selection) {
        this.selection = selection;
        return this;
    }

    public IBEA<S> build() {
        return new IBEA(this.problem, this.populationSize, this.archiveSize, this.maxEvaluations, this.selection, this.crossover, this.mutation);
    }
}

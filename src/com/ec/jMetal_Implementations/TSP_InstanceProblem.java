package com.ec.jMetal_Implementations;

import com.ec.assignment3.OutputLogger;
import com.ec.Objects.HamiltonCycle;
import com.ec.TSP_Solvers.TSP_Solver;
import com.ec.TSP_Solvers.TSP_SolverFactory;
import org.uma.jmetal.problem.Problem;
import org.uma.jmetal.util.solutionattribute.impl.Fitness;
import org.uma.jmetal.util.solutionattribute.impl.StrengthRawFitness;

import java.util.Collections;

public class TSP_InstanceProblem implements Problem<TSP_InstanceSolution> {
    TSP_Solver solver_I;
    TSP_Solver solver_J;
    TSP_Solver solver_K;

    OutputLogger OL;

    private int numberOfVariables;
    private int numberOfObjectives;
    private String name;

    private int inner_generations = 10;

    private static boolean fixedGrid;
    int citySpaceLimit;
    StrengthRawFitness<TSP_InstanceSolution> attr_SRF;
    Fitness<TSP_InstanceSolution> attr_SF;

    public TSP_InstanceProblem(int numOfCities, String A_i, boolean fixed) {
        fixedGrid = fixed;
        numberOfVariables = numOfCities*2;
        numberOfObjectives = 2;
        name = "Multi-Objective TSP_InstanceProblem (" + A_i + ")";

        //initialise solvers
        solver_I = new TSP_SolverFactory().setAlgorithm(A_i);
        switch (A_i) {
            case "2opt":
                solver_J = new TSP_SolverFactory().setAlgorithm("InverOver");
                solver_K = new TSP_SolverFactory().setAlgorithm("EA");
                break;

            case "InverOver":
                solver_J = new TSP_SolverFactory().setAlgorithm("2opt");
                solver_K = new TSP_SolverFactory().setAlgorithm("EA");
                break;

            case "EA":
                solver_J = new TSP_SolverFactory().setAlgorithm("2opt");
                solver_K = new TSP_SolverFactory().setAlgorithm("InverOver");
                break;
        }

        //initialise attributes
        attr_SRF = new StrengthRawFitness<TSP_InstanceSolution>();
        attr_SF = new Fitness<TSP_InstanceSolution>();
    }

    public TSP_InstanceProblem setCitySpaceLimit(int value) {  // assuming city space is square
        citySpaceLimit = value;
        return this;
    }

    @Override
    public int getNumberOfVariables() {
        return numberOfVariables;
    }

    @Override
    public int getNumberOfObjectives() {
        return numberOfObjectives;
    }

    @Override
    public int getNumberOfConstraints() {
        return 0;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void evaluate(TSP_InstanceSolution tspSolution) {
        HamiltonCycle HC = tspSolution.getHCObject();

        // obtain solution from all solvers
        double f_Ai = runSolver(solver_I, HC);
        double f_Aj= runSolver(solver_J, HC);
        double f_Ak = runSolver(solver_K, HC);

        // calculate fitnesses
        double p_ij = f_Aj - f_Ai;  //System.out.print(p_ij + " \t");
        double p_ik = f_Ak - f_Ai;  //System.out.println(p_ik);

        tspSolution.setObjective(0, -p_ij);
        tspSolution.setObjective(1, -p_ik);

        attr_SRF.setAttribute(tspSolution, p_ij+p_ik);
        attr_SF.setAttribute(tspSolution, p_ij+p_ik);
    }

    @Override
    public TSP_InstanceSolution createSolution() {
        return new TSP_InstanceSolution(getNumberOfVariables()/2, fixedGrid, citySpaceLimit);
    }

    public double runSolver(TSP_Solver fA, HamiltonCycle tour) {
        double minTourLength = Double.MAX_VALUE;
        for(int i=0; i<5; i++) {
            Collections.shuffle(tour);
            double tourLength = fA.solve(tour).calculateCost();
            if(tourLength < minTourLength) {
                minTourLength = tourLength;
            }
        }
        return minTourLength;
    }

    public TSP_InstanceProblem setInnerGenerations(int innerGenerations) {
        innerGenerations = innerGenerations;
        return this;
    }
}

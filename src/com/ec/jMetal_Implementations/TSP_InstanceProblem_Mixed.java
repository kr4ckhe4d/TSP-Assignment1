package com.ec.jMetal_Implementations;

import com.ec.assignment3.jMetal_Runners.MOEA_Runner;
import com.ec.assignment3.OutputLogger;
import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;
import com.ec.Objects.Instance_Ex3Format;
import com.ec.TSP_Solvers.TSP_Solver;
import com.ec.TSP_Solvers.TSP_SolverFactory;
import org.uma.jmetal.util.solutionattribute.impl.Fitness;
import org.uma.jmetal.util.solutionattribute.impl.StrengthRawFitness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TSP_InstanceProblem_Mixed extends TSP_InstanceProblem {
    TSP_Solver solver_I;
    TSP_Solver solver_J;
    TSP_Solver solver_K;

    OutputLogger OL;
    MOEA_Runner moea;
    static Random RNG;

    private int numberOfVariables;
    private int numberOfObjectives;
    private String name;
    private static String Algorithm_i;

    private int inner_generations = 10;
    private int innerCities;

    private static boolean fixedGrid;
    StrengthRawFitness<TSP_InstanceSolution> attr_SRF;
    Fitness<TSP_InstanceSolution> attr_SF;

    List<TSP_InstanceSolution> innerResult;
    int population_counter=0;

    public TSP_InstanceProblem_Mixed(int numOfCities, String A_i, boolean fixed, int innerLoopCities, MOEA_Runner moeaRunner) {
        super(numOfCities, A_i, fixed);
        moea = moeaRunner;
        Algorithm_i = A_i;
        RNG = new Random(System.currentTimeMillis());
        fixedGrid = fixed;
        numberOfVariables = numOfCities*2;
        numberOfObjectives = 2;
        innerCities = innerLoopCities;
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
        innerResult = new ArrayList<>();
    }

    public TSP_InstanceProblem_Mixed setInnerGenerations(int G) {
        inner_generations = G;
        return this;
    }

    @Override
    public void evaluate(TSP_InstanceSolution tspSolution) {    // input: fixed_grid TSP Solution of P cells
        boolean[] cells = tspSolution.getGridCellsList();
                // IF NO RESULT, CREATE AND RUN INNER LOOP
        if( (innerResult.size() < 1) || (population_counter >=10)){
            innerResult = moea.runInnerLoop();
            population_counter=0;
        } else {
            population_counter++;
        }

        // REBUILD INSTANCE USING CELLS AND INNER LOOP CITIES
        HamiltonCycle HC = rejoinCellsAndOffsets(cells, innerResult, 20);

        // obtain solution from all solvers
        double f_Ai = runSolver(solver_I, HC);
        double f_Aj= runSolver(solver_J, HC);
        double f_Ak = runSolver(solver_K, HC);

        // calculate fitnesses
        double p_ij = f_Aj - f_Ai;  //System.out.print(p_ij + " \t");
        double p_ik = f_Ak - f_Ai;  //System.out.println(p_ik);

        tspSolution.setConjoinedHC(HC);
        tspSolution.setObjective(0, -p_ij);
        tspSolution.setObjective(1, -p_ik);

        attr_SRF.setAttribute(tspSolution, p_ij+p_ik);
        attr_SF.setAttribute(tspSolution, p_ij+p_ik);
    }

    public static HamiltonCycle rejoinCellsAndOffsets(boolean[] cells, List<TSP_InstanceSolution> cityGroups, int spaceLimit) {
        // Convert cityGroups into HamiltonCycle
        ArrayList<Instance_Ex3Format> IE3_Objects = new ArrayList<>();
        for(TSP_InstanceSolution tsi : cityGroups) {
            IE3_Objects.add(tsi.getHCObject().getEx3FormatObject());
        }

        // for each cell, add all cities based on offset
        HamiltonCycle result = new HamiltonCycle();
        for(int idx=0; idx<cells.length; idx++) {
            if(cells[idx]) {  // only if true
                double X_major = idx / spaceLimit;
                double Y_major = idx % spaceLimit;

                // pick a random cityGroup and insert
                Instance_Ex3Format city_offsets = IE3_Objects.get(RNG.nextInt(IE3_Objects.size()));
                ArrayList<Double> xOffsets = city_offsets.getXOffsets();
                ArrayList<Double> yOffsets = city_offsets.getYOffsets();
                for(int i=0; i<xOffsets.size(); i++) {
                    double X_minor = xOffsets.get(i);
                    double Y_minor = yOffsets.get(i);

                    City C = new City()
                            .setX( X_major + X_minor )
                            .setY( Y_major + Y_minor );
                    result.add( C );
                }
            }
        }
        result.renumber();
        return result;
    }
}

package com.ec.assignment3;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;
import com.ec.jMetal_Implementations.TSP_InstanceSolution;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OutputLogger {
    private String outputPath = "OUTPUT.txt";
    private String experimentName;
    private String separator;

    ArrayList<Double> p_ij_ParetoFront;
    ArrayList<Double> p_ik_ParetoFront;

    ArrayList<Double> p_ij_OBJECTIVE;
    ArrayList<Double> p_ik_OBJECTIVE;

    List<TSP_InstanceSolution> population;

    public OutputLogger(String separatorType) {
        separator = separatorType;
        p_ij_ParetoFront = new ArrayList<>();
        p_ik_ParetoFront = new ArrayList<>();
        p_ij_OBJECTIVE = new ArrayList<>();
        p_ik_OBJECTIVE = new ArrayList<>();
    }

    public void setOutputPath(String path) {
        outputPath = path;
    }


    public void setExperimentName(String ExName, int Ncities, String RunnerType, String Algorithm_i) {
        experimentName = ExName;
        experimentName += "_" + String.valueOf(Ncities);
        experimentName += "_" + RunnerType;
        experimentName += "_" + Algorithm_i;
        outputPath = experimentName + ".txt";
    }

    public void addPopulation(List<TSP_InstanceSolution> input) {
        population = input;
    }

    public void addObjectivePoint(Double p_ij, Double p_ik) {
        p_ij_OBJECTIVE.add(p_ij);
        p_ik_OBJECTIVE.add(p_ik);
    }

    public void readObjectivesFile(String filepath) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(filepath));

        String row = br.readLine();
        while(row != null) {
            String[] dataArray = row.split(" ");
            Double p_ij = -Double.valueOf(dataArray[0]);
            Double p_ik = -Double.valueOf(dataArray[1]);
            addObjectivePoint(p_ij, p_ik);
            row = br.readLine();
        }

        br.close();
    }

    // writes combined results to output file
    public void writeCombinedResultsFile() throws IOException {
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(outputPath, false)));

        pw.println("######### Pareto Front");
        for(int i=0; i<p_ij_ParetoFront.size(); i++) {
            pw.println(p_ij_ParetoFront.get(i) + separator + p_ik_ParetoFront.get(i));
        }
        pw.println("\n\n######### All Objectives");
        for(int i=0; i<p_ij_OBJECTIVE.size(); i++) {
            pw.println(p_ij_OBJECTIVE.get(i) + separator + p_ik_OBJECTIVE.get(i));
        }
        pw.println("\n\n######### Population");
        for(TSP_InstanceSolution sol : population) {
            HamiltonCycle HC = sol.getConjoinedHC();
            HC.renumber();
            for(City C : HC) {
                String line = String.valueOf(C.getNUMBER()) + separator + String.valueOf(C.getX()) + separator + String.valueOf(C.getY());
                pw.println(line);
            }
            pw.println("=======================\n");
        }
        pw.close();
    }

    // writes combined results to console output
    public void printOutput() {
        System.out.println("######### Optimal");
        for(int i=0; i<p_ij_ParetoFront.size(); i++) {
            System.out.println(p_ij_ParetoFront.get(i) + separator + p_ik_ParetoFront.get(i));
        }
        System.out.println("\n\n######### All");
        for(int i=0; i<p_ij_OBJECTIVE.size(); i++) {
            System.out.println(p_ij_OBJECTIVE.get(i) + separator + p_ik_OBJECTIVE.get(i));
        }
        System.out.println("\n\n######### Population");
        for(TSP_InstanceSolution sol : population) {
            int i = 0;
            while(i<sol.getNumberOfVariables()) {
                String line = String.valueOf((i/2) + 1) + separator + String.valueOf(sol.getVariableValue(i++)) + separator + String.valueOf(sol.getVariableValue(i++));
                System.out.println(line);
            }
            System.out.println("=======================\n");
        }
    }

    // Marcus: my own version of Pareto Front calculator (because I think jMetal5's FUN.tsv has flaws)
    public void calcPareto() {
        ArrayList<Double> pareto_X = (ArrayList<Double>) p_ij_OBJECTIVE.clone();
        ArrayList<Double> pareto_Y = (ArrayList<Double>) p_ik_OBJECTIVE.clone();

        for(int i=0; i<pareto_X.size(); i++) {    // for each point
            Double curr_X = pareto_X.get(i);
            Double curr_Y = pareto_Y.get(i);

            for(int j=0; j<pareto_X.size(); j++) {
                Double temp_X = pareto_X.get(j);
                Double temp_Y = pareto_Y.get(j);

                if( (curr_X < temp_X) && (curr_Y < temp_Y) ) {
                    pareto_X.remove(curr_X);
                    pareto_Y.remove(curr_Y);
                    i--;
                    break;
                }
            }
        }

        p_ij_ParetoFront = (ArrayList<Double>) pareto_X.clone();
        p_ik_ParetoFront = (ArrayList<Double>) pareto_Y.clone();
    }

}

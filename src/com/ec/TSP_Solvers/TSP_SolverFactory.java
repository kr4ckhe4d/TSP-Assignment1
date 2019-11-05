package com.ec.TSP_Solvers;

public class TSP_SolverFactory{
    public TSP_Solver setAlgorithm(String type) {
        switch (type) {
            case "2opt":
                return new twoOptLocalSearch();
            case "EA":
            	return new EASolver();
            case "InverOver":
                return new InverOver();
        }
        return null;
    }
}

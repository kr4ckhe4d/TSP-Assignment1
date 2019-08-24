package com.ec;

public class SampleMain {

    public static void main(String[] args) throws Exception{
        TSPProblem tsp = new TSPProblem("data/eil51.tsp.txt");
        System.out.println(tsp.getCities());

        EvolutionAlgorithm ea = new EvolutionAlgorithm();
        ea.setPopulationSize(50);
        ea.setGenerationSize(2000);
        ea.setSelection(EvolutionAlgorithm.TOURNAMENT_SELECTION);
        ea.setTournamentSize(5);
        ea.setCrossover(EvolutionAlgorithm.CYCLE_CROSSOVER);
        ea.setElitism(1);
        ea.setMutation(EvolutionAlgorithm.INVERSION_MUTATION);

        ea.run(tsp.getCities());
        System.out.println("EA Worked");
    }

}

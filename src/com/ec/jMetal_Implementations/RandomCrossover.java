package com.ec.jMetal_Implementations;

import com.ec.Objects.HamiltonCycle;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomCrossover implements CrossoverOperator<TSP_InstanceSolution> {

    private double crossoverProbability;
    private Random RNG = new Random(System.currentTimeMillis());

    public RandomCrossover(double probability) {
        setCrossoverProbability(probability);
    }

    /* GETTERS */
    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    /* SETTERS */
    public void setCrossoverProbability(double probability) {
        this.crossoverProbability = probability ;
    }

	@Override
	public List<TSP_InstanceSolution> execute(List<TSP_InstanceSolution> parents) {
		if (null == parents) {
            throw new JMetalException("Null parameter") ;
        } else if (parents.size() != 2) {
            throw new JMetalException("There must be two parents instead of " + parents.size()) ;
        }

        return doCrossover(crossoverProbability, parents.get(0), parents.get(1)) ;
	}

	public List<TSP_InstanceSolution> doCrossover(double crossoverProbability2,
			TSP_InstanceSolution p1, TSP_InstanceSolution p2) {
		List<TSP_InstanceSolution> children = new ArrayList<>();
        children.add(p1.clone());
        children.add(p2.clone());

        if(crossoverProbability > RNG.nextDouble()) {
            HamiltonCycle parent1 = p1.getHCObject();
            HamiltonCycle parent2 = p2.getHCObject();

            // Does crossover operation with HC objects.
            ArrayList<HamiltonCycle> offsprings = randomCrossover(parent1, parent2);
            children.get(0).adoptPositions(offsprings.get(0).convertCityPos());
            children.get(1).adoptPositions(offsprings.get(1).convertCityPos());
        }

        // if below probability, returns copy of parents
        return children;
	}

	private ArrayList<HamiltonCycle> randomCrossover(HamiltonCycle parent1, HamiltonCycle parent2) {
		// Combines two parents.
		HamiltonCycle combinedCycle = new HamiltonCycle();
		combinedCycle.addAll(parent1);
		combinedCycle.addAll(parent2);

		// Removes duplicated cities from the combinedCycle.
		combinedCycle.removeDuplicates();

		// Shuffles comebinedCycle.
		Collections.shuffle(combinedCycle);
		HamiltonCycle offspring1 = new HamiltonCycle( combinedCycle.subList(0, parent1.size()) );
		Collections.shuffle(combinedCycle);
		HamiltonCycle offspring2 = new HamiltonCycle( combinedCycle.subList(0, parent1.size()) );

		ArrayList<HamiltonCycle> offsprings = new ArrayList<HamiltonCycle>();
		offsprings.add(offspring1);
		offsprings.add(offspring2);

		return offsprings;
	}

}

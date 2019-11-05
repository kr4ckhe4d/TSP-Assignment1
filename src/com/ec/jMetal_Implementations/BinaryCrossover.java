package com.ec.jMetal_Implementations;

import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BinaryCrossover implements CrossoverOperator<TSP_InstanceSolution> {

	private double crossoverProbability;
    private Random RNG = new Random(System.currentTimeMillis());

    public BinaryCrossover(double probability) {
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

        if(crossoverProbability2 > RNG.nextDouble()) {
            boolean[] parent1 = p1.getHCObject().getGridCellsList();
            boolean[] parent2 = p2.getHCObject().getGridCellsList();

            // Does crossover operation with HC objects.
            ArrayList<boolean[]> offsprings = binaryCrossover(parent1, parent2);
            children.get(0).adoptPositions(offsprings.get(0));
            children.get(1).adoptPositions(offsprings.get(1));
        }

        // if below probability, returns copy of parents
        return children;
	}

	private ArrayList<boolean[]> binaryCrossover(boolean[] parent1,
			boolean[] parent2) {
		boolean[] child1 = new boolean[parent1.length];
		boolean[] child2 = new boolean[parent1.length];
		int remainingCity1 = 0, remainingCity2 = 0;

		for (int i = 0; i < parent1.length; i++) {
			if (parent1[i] && parent2[i]) {
				child1[i] = true;
				child2[i] = true;
			} else if (parent1[i]) {
				remainingCity1 ++;
				remainingCity2 ++;
			}
		}

		for (int i = 0; i < parent1.length; i++) {
			if (parent1[i] ^ parent2[i]) {
				Boolean isChild1True = RNG.nextBoolean();
				if ((isChild1True && remainingCity1 > 0) ||
						(!isChild1True && remainingCity2 == 0)) {
					child1[i] = true;
					child2[i] = false;
					remainingCity1 --;
				} else {
					child2[i] = true;
					child1[i] = false;
					remainingCity2 --;
				}
			} else if (!parent1[i]) {
				child2[i] = false;
				child1[i] = false;
			}
		}

		ArrayList<boolean[]> result = new ArrayList<boolean[]>();
		result.add(child1);
		result.add(child2);
		return result;
	}

}

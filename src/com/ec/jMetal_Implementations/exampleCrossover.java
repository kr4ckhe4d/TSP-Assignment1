package com.ec.jMetal_Implementations;

import com.ec.Objects.HamiltonCycle;
import com.ec.operators.Crossover;
import org.uma.jmetal.operator.CrossoverOperator;
import org.uma.jmetal.util.JMetalException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class exampleCrossover implements CrossoverOperator<TSP_InstanceSolution> {
    HamiltonCycle Parent1;
    HamiltonCycle Parent2;

    private double crossoverProbability;
    private Random RNG;

    public exampleCrossover(double probability) {
        RNG = new Random(System.currentTimeMillis());
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

    public List<TSP_InstanceSolution> doCrossover(double probability, TSP_InstanceSolution p1, TSP_InstanceSolution p2) {
        List<TSP_InstanceSolution> Children = new ArrayList<>();
        Children.add(p1.clone());
        Children.add(p2.clone());
        if(probability > RNG.nextDouble()) {    // Only replace parents if pass probability
            Crossover COX = new Crossover();
            Parent1 = p1.getHCObject();
            Parent2 = p2.getHCObject();

            // do crossover operation with HC objects here
            ArrayList<HamiltonCycle> xOverResult = randomlySwapCrossover(Parent1, Parent2);
            Children.get(0).adoptPositions(xOverResult.get(0).convertCityPos());
            Children.get(1).adoptPositions(xOverResult.get(1).convertCityPos());

        }
        return Children;    // if below probability, returns copy of parents
    }

    public ArrayList<HamiltonCycle> randomlySwapCrossover(HamiltonCycle p1, HamiltonCycle p2) {
        ArrayList<HamiltonCycle> result = new ArrayList<>();
        HamiltonCycle child1 = new HamiltonCycle();
        HamiltonCycle child2 = new HamiltonCycle();

        for(int i=0; i<p1.size(); i++) {
            if(RNG.nextBoolean()) {
                child1.add(p1.get(i).clone());
                child2.add(p2.get(i).clone());
            } else {
                child2.add(p1.get(i).clone());
                child1.add(p2.get(i).clone());
            }
        }
        result.add(child1);
        result.add(child2);

        return result;
    }

}

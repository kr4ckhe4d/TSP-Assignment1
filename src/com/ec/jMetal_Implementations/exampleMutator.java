package com.ec.jMetal_Implementations;


import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;

import java.util.Random;

public class exampleMutator implements MutationOperator<TSP_InstanceSolution> {
    HamiltonCycle victimHC;
    HamiltonCycle resultHC;

    private double mutationProbability;
    private boolean fixedG;
    private Random RNG;

    public exampleMutator(double probability, boolean fixedGrid) {
        fixedG = fixedGrid;
        RNG = new Random(System.currentTimeMillis());
        setMutationProbability(probability);
    }

    /* GETTERS */
    public double getMutationProbability() {
        return mutationProbability;
    }

    /* SETTERS */
    public void setMutationProbability(double probability) {
        this.mutationProbability = probability ;
    }

    @Override
    public TSP_InstanceSolution execute(TSP_InstanceSolution input) {
        if(null == input) {
            throw new JMetalException("Null parameter");
        }

        return doMutation(mutationProbability, input);
    }

    public TSP_InstanceSolution doMutation(double probability, TSP_InstanceSolution input) {
        TSP_InstanceSolution output = input.clone();

        victimHC = input.getHCObject();
        if(probability > RNG.nextDouble()) {
            resultHC = randomShiftMutation(victimHC);
            output.adoptPositions(resultHC.convertCityPos());
        }

        return output;
    }

    public HamiltonCycle randomShiftMutation(HamiltonCycle p1) {
        HamiltonCycle result = p1.clone();

        do {
            for (City C : result) {
                double xmod = C.getX();
                double ymod = C.getY();
                for (City p : p1) {
                    if (RNG.nextBoolean()) {
                        if(fixedG) {
                            xmod += p.getX() - 0.5;
                            ymod += p.getY() - 0.5;
                        } else {
                            xmod += p.getX();
                            ymod += p.getY();
                        }
                    }
                }
                // normalise
                C.setX(xmod % 20);
                C.setY(ymod % 20);
            }
        } while (result.checkForDuplicates());

        return result;
    }

}

package com.ec.jMetal_Implementations;

import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;

import java.util.Random;


public class binaryMutator implements MutationOperator<TSP_InstanceSolution> {
    boolean[] victimHC;
    boolean[] resultHC;

    private double mutationProbability;
    private Random RNG;

    public binaryMutator(double probability) {
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
        TSP_InstanceSolution result = input.clone();
        return doMutation(mutationProbability, input);
    }

    public TSP_InstanceSolution doMutation(double probability, TSP_InstanceSolution input) {
        TSP_InstanceSolution output = input.clone();

        victimHC = input.getHCObject().getGridCellsList();
        if(probability > RNG.nextDouble()) {
            resultHC = flipMutation(victimHC);
            output.adoptPositions(resultHC);
        }
        return output;
    }

    public boolean[] flipMutation(boolean[] parent) {
        boolean[] result = parent;
        int gridInterval = 20;
        int strength = 0;

        for(int i=0; i<parent.length;i = i+gridInterval){
        	for(int j=i; j<i+gridInterval; j++){
        		if(result[j] == true){
        			strength++;
        		}
        	}
        	if(strength <= 5 && strength != 0){
        		int temp=strength*2;
        		boolean flag = true;
        		do{
            		for(int k=i; k<i+gridInterval; k++){
            			if(flag && result[k]){
            				result[k] = !result[k];
            				flag = !flag;
            				temp--;
            			}
            			else if(!flag && !result[k]){
            				result[k] = !result[k];
            				flag = !flag;
            				temp--;
            			}
            			if(temp == 0){
            				break;
            			}
            		}
        		}while(temp%2 != 0);
        	}
        }

        return result;
    }

}

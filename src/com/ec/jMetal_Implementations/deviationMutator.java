package com.ec.jMetal_Implementations;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;
import org.uma.jmetal.operator.MutationOperator;
import org.uma.jmetal.util.JMetalException;

import java.util.Random;

public class deviationMutator implements MutationOperator<TSP_InstanceSolution> {
	HamiltonCycle victimHC;
	HamiltonCycle resultHC;

	private double mutationProbability;
	private boolean fixedG;
	private Random RNG;

	public deviationMutator(double probability, boolean fixedGrid) {
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
			resultHC = sdevMutation(victimHC);
			output.adoptPositions(resultHC.convertCityPos());
		}

		return output;
	}

	public HamiltonCycle sdevMutation(HamiltonCycle p1) {
		HamiltonCycle result = p1.clone();
		double average = 0;
		double stdev = 0;

		do {
			for (City C : result) {
				average += C.getX();
			}
			average = average/result.size();

			//calculate variance
			for (City C : result){
				double diff = Math.abs(C.getX() - average);
				stdev += diff*diff;
			}

			//calculate stdev
			stdev = Math.pow(stdev/result.size(), 0.5);

			City temp = result.get(RNG.nextInt(result.size()));
			result.remove(temp);

			if(temp.getX() >= average){
				if(fixedG){
					temp.setX((Math.floor(temp.getX() - stdev)+0.5)%20);
				}
				else{
					temp.setX((temp.getX() - stdev)%20);
				}
			}
			else{
				if(fixedG){
					temp.setX((Math.floor(temp.getX() + stdev)+0.5)%20);
				}
				else{
					temp.setX((temp.getX() + stdev)%20);
				}
			}
			result.add(temp);

		} while (result.checkForDuplicates());

		return result;
	}

}

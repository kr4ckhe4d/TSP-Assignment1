package com.ec.EAs;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;

import java.util.Random;

public class Crossover {

	Random RNG;
	public Crossover() {
		RNG = new Random(System.currentTimeMillis());
	}

	/**
	 * RandomCrossover takes in two parents and generates the child by randomly choosing the city of
	 * the same index from one of the parents.
	 * @param parent1
	 * @param parent2
	 * @return a new child HamiltonCycle
	 */
	public HamiltonCycle randomCrossover(HamiltonCycle parent1, HamiltonCycle parent2) {
		HamiltonCycle child = new HamiltonCycle();

		for (int i = 0; i < parent1.size(); i++) {
			City city = RNG.nextBoolean() ? parent1.get(i) : parent2.get(i);
			child.add(city);
		}
		return child;
	}

	private boolean isTopLeftOrBottomRight(City city, int x_limit, int y_limit) {
		boolean isTopLeft = city.getX() < x_limit/2 && city.getY() >= y_limit/2;
		boolean isBottomRight = city.getX() >= x_limit/2 && city.getY() < y_limit/2;
		return isTopLeft || isBottomRight;
	}

	/**
	 * Transplant Crossover preserves the clusters of one of the parents in a particular part of the
	 * map: either top left and bottom right, or top right and bottom left.
	 * @param parent1
	 * @param parent2
	 * @param preserveParent1
	 * @param preserveTopLeft
	 * @param x_limit
	 * @param y_limit
	 * @return a new child HamiltonCycle
	 */
	public HamiltonCycle transplantCrossover(HamiltonCycle parent1, HamiltonCycle parent2,
			boolean preserveParent1, boolean preserveTopLeft, int x_limit, int y_limit) {
		HamiltonCycle child = new HamiltonCycle();

		for (int i = 0; i < parent1.size(); i++) {
			City city = preserveParent1 ? parent1.get(i) : parent2.get(i);

			if (preserveTopLeft && !isTopLeftOrBottomRight(city, x_limit, y_limit)
					|| !preserveTopLeft && isTopLeftOrBottomRight(city, x_limit, y_limit)) {
				city = null;
				while (city != null && child.containsCityPosition(city) || city == null) {
					city = preserveParent1 ?
							parent2.get(RNG.nextInt(parent1.size())) :
							parent1.get(RNG.nextInt(parent1.size()));
				}
			}
			child.add(city.clone());
		}

		// renumber cities in the tour (don't keep their old numbers)
		child.renumber();
		return child;
	}
}

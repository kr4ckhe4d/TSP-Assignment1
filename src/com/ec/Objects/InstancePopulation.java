package com.ec.Objects;

import java.util.ArrayList;

public class InstancePopulation extends ArrayList<HamiltonCycle> {

    public InstancePopulation() {   // blank population, for when creating a new population inside EAs
    }

    public InstancePopulation(int num_of_instances, int num_of_cities, int x_limit, int y_limit) {
        for (int i = 0; i < num_of_instances; i++) {
            this.add(new HamiltonCycle(num_of_cities, x_limit, y_limit));
        }
    }

    public void printPopulation(boolean debug) {
        for (HamiltonCycle HC : this) {
            if(debug) {
                System.out.print("[" + HC.getAverageFitness() + "]\t");
            }
            HC.printCycle();
        }
    }

    @Override
    public InstancePopulation clone() {
        InstancePopulation IP_clone = new InstancePopulation();
        for (HamiltonCycle HC : this) {
            IP_clone.add(HC.clone());
        }
        return IP_clone;
    }

    public boolean contains(HamiltonCycle findee) {
        for (HamiltonCycle HC : this) {
            if (HC.isIdenticalTo(findee)) {
                return true;
            }
        }
        return false;
    }

    public void setAllTourLengthsAsFitness() {
        for(HamiltonCycle HC : this) {
            HC.setTourLengthAsFitness();
        }
    }

    public void trimFromBackToSize(int size) {
        while(this.size() > size) {
            this.remove(this.size()-1); // remove last object
        }
    }
}

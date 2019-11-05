/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.EAs;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;

import java.util.Collections;

/**
 * Implemented by Marcus, integrated by Xiaogang Dong.
 *
 * @author Marcus
 */
public class newTwoOptLS extends Thread implements Algorithm {

    private HamiltonCycle HC;
    private boolean verbose;
    private int fitness;

    public newTwoOptLS(HamiltonCycle HC, boolean verbose) {
        this.HC = HC;
        this.verbose = verbose;
    }

    public HamiltonCycle getHC() {
        return this.HC;
    }

    public int getFitness() {
        return fitness;
    }

    @Override
    public void run() {
        HC = solve();
    }

    private HamiltonCycle solve() {
        fitness = 0;
        HamiltonCycle pi = HC.clone();
        while (true) {
            double best_improvement = 0.0;
            int best_current = 0;
            int best_other = 0;

            for (int i = 0; i < pi.size(); i++) {
                City x_i = pi.get(i);
                City x_i1 = pi.get((i + 1) % pi.size());
                for (int j = i + 2; j < pi.size(); j++) {
                    if (!((i == 0) && (j == pi.size() - 1))) {
                        City x_j = pi.get(j);
                        City x_j1 = pi.get((j + 1) % pi.size());

                        double dist1 = Algorithms.distanceBetween(x_i, x_i1);
                        double dist2 = Algorithms.distanceBetween(x_j, x_j1);
                        double dist3 = Algorithms.distanceBetween(x_i, x_j);
                        double dist4 = Algorithms.distanceBetween(x_i1, x_j1);

                        double improvement = dist1 + dist2 - (dist3 + dist4);

                        if (improvement > best_improvement) {
                            best_improvement = improvement;
                            best_current = i;
                            best_other = j;
                        }
                    }
                }
            }

            if (best_improvement > 0.0) {
                Collections.reverse(pi.subList(best_current + 1, best_other + 1));  // fromIndex is inclusive, toIndex is exclusive
            } else {
                break;
            }

            fitness++;
            if (verbose) {
                System.out.print("\t{G_" + fitness + "} \t");
                System.out.print("[" + pi.calculateCost() + "] \t");
                pi.printCycle();
            }
        }
        return pi;
    }
}

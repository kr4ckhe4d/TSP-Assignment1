/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec;


import com.ec.beans.City;
import com.ec.beans.Solution;

import java.util.Collections;

/**
 * author: Li He 2019-09-22
 */
public class TwoOptLocalSearch extends Thread{

    private Solution sol;
    private boolean verbose;
    private int fitnessValue;

    public TwoOptLocalSearch(Solution sol, boolean verbose) {
        this.sol = sol;
        this.verbose = verbose;
    }

    public Solution getSol() {
        return sol;
    }

    public void setSol(Solution sol) {
        this.sol = sol;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public int getFitnessValue() {
        return fitnessValue;
    }

    public void setFitnessValue(int fitnessValue) {
        this.fitnessValue = fitnessValue;
    }

    @Override
    public void run() {
        sol = opt_2();
    }

    private double compareValue(City c1,City c2,City c3,City c4){
        double dist1 = Solution.edgeDist(c1, c2);
        double dist2 = Solution.edgeDist(c3, c4);
        double dist3 = Solution.edgeDist(c1, c3);
        double dist4 = Solution.edgeDist(c2, c4);

        double improvement = dist1 + dist2 - (dist3 + dist4);
        return improvement;
    }
    private Solution opt_2() {
        fitnessValue = 0;
        Solution pi = sol.copy();
        double Opt2Totalcost=0;
        double enhancement = 0;
        while (true) {
            enhancement = 0.0;
            int bestPosition1 = 0;
            int bestPosition2 = 0;
            for (int i = 0; i < pi.size(); i++) {
                City c1 = pi.get(i);
                City c2= null;
                if((i+1)<pi.size())
                    c2 = pi.get((i + 1));
                else
                    c2 = pi.get((0));
                for (int j = i + 2; j < pi.size(); j++) {
                    if (!((i == 0) && (j == pi.size() - 1))) {
                        City c3 = pi.get(j);
                        City c4 = null;
                        if((j+1)<pi.size())
                            c4 = pi.get((j + 1));
                        else
                            c4 = pi.get((0));
                        double improvement = compareValue(c1,c2,c3,c4);
                        if (improvement > enhancement) {
                            enhancement = improvement;
                            bestPosition1 = i;
                            bestPosition2 = j;
                        }
                    }
                }
            }

            if (enhancement > 0.0) {
                Collections.reverse(pi.subList(bestPosition1 + 1, bestPosition2 + 1));
            } else {
                break;
            }
            fitnessValue++;
            if (verbose) {
                System.out.print("\t{I_" + fitnessValue + "} \t");
                System.out.println("[" + pi.updateDist1() + "] \t");

                pi.printXY();
            }
        }
        return pi;
    }
}

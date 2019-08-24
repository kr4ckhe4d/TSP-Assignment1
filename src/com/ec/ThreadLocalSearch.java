package com.ec;

import com.ec.beans.Solution;

import java.util.HashMap;

/**
 * author Li He
 * MultiThread for big dataset solving
 */
public class ThreadLocalSearch extends Thread {
    Solution param;
    String operator;
    String fn;
    static double minCost;
    static double totalCost;
    public LocalSearch ls = new LocalSearch();
    public ThreadLocalSearch(Solution param, String operator,String fn) {
        this.param = new Solution(param);
        this.operator = operator;
        this.fn = fn;
    }
    public static synchronized void setMinCost(double mCost){
        minCost=mCost;
    }

    public static synchronized void setTotalCost(double tCost){
        totalCost=tCost;
    }

    @Override
    public void run() {
        System.out.println(this.getId());
        for (int i = 0; i < 5; i++) {
            param.initialize();
            Long sTime = System.currentTimeMillis();
            Solution result =ls.run(param,operator);
            Long eTime = System.currentTimeMillis();
            setTotalCost(totalCost+result.getPathDist());
            if (minCost == 0)
                setMinCost(result.getPathDist());
//        minCost = result.getPathDist();
            if (result.getPathDist() < minCost) {
                setMinCost(result.getPathDist());
                minCost = result.getPathDist();
            }
            System.out.println("\t\t"+result.getPathDist()+"\t"+(eTime-sTime));
        }

    }
}

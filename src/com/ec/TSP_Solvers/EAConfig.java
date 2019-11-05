/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.TSP_Solvers;

/**
 * This class holds descriptive parameters of an EA run.
 *
 * @author Xiaogang
 */
public class EAConfig {

    public final boolean debug = false;
    public final int trails = 5;
    public final int maxPopulationSize = 10;
    public final int maxGen = 1000;
    public String mutator;
    public String crossover;
    public int cityAmount;
    public String EAname;

}

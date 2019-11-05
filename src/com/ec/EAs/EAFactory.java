/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.EAs;

import com.ec.TSP_Solvers.EAConfig;

/**
 *
 * @author Xiaogang
 */
public class EAFactory {

    public static EA getEA(EAConfig config) {
        switch (config.EAname) {
            case "1":
                return new EA1(config);
            case "2":
                return new EA2(config);
            case "3":
                return new EA3(config);
            default:
                return null;
        }
    }

}

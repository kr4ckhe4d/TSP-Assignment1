/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ec.EAs;

import com.ec.Objects.City;

/**
 * Please extend Thread when implementing this interface.
 *
 * @author Xiaogang
 */
public interface Algorithm {

    default public double distanceBetween(City A, City B) {
        double A_x = (double) A.getX();
        double A_y = (double) A.getY();
        double B_x = (double) B.getX();
        double B_y = (double) B.getY();

        return Math.sqrt(Math.pow(A_x - B_x, 2) + Math.pow(A_y - B_y, 2));
    }

}

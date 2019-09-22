package com.ec;


import com.ec.beans.Solution;

/**
 * author: Li He 2019-09-22
 */
public class OnePlusOneEARun {

    public static int X_Coor_range = 50;
    public static int Y_Coor_range = 50;

    public static void run1plus1EA(String cityAmount) {


        int num_of_cities = Integer.parseInt(cityAmount);

        Solution sol = new Solution(num_of_cities, X_Coor_range, Y_Coor_range);
        System.out.println("------------------ initial City Positions ------------------");
        sol.print();
        System.out.println("\n\n======================= \n\n");

        Solution best_of_EA = null;

            System.out.println("Medium Operator Version");
            System.out.println("------------------");
            best_of_EA = OnePlusOneEA.onePlusOneEA(sol, "median", false);
            System.out.println("------------------ FINAL City Positions ------------------");
            best_of_EA.print();


    }



    public static void main(String[] args) {
//        String[] arg = new String[2];
        run1plus1EA("500");
    }
}

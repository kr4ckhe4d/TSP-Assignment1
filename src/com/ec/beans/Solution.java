package com.ec.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Solution {
    private ArrayList<Integer> cityPath;  //The path of all cities
    public double cityX[];  //coordinate x of i city
    public double cityY[]; //coordinate y of i city

    int size;
//    private List<Double> cityDist;
    private double pathDist;

    /**
     * Initialize a path with another path
     * @param path
     */
    public Solution(Solution path)
    {
        this.size = path.size;
        //this.cityPath = path.cityPath;

        cityPath = new ArrayList<Integer>();

        for (Iterator iterator = path.cityPath.iterator(); iterator.hasNext();) {
            cityPath.add((Integer)iterator.next());
        }
        cityX = path.cityX;
        cityY = path.cityY;
        this.pathDist = updateDist();
    }

    public Solution(List<City> cityPath) {
        int i=0;
        this.cityPath = new ArrayList<Integer>();
        this.cityX = new double[cityPath.size()];
        this.cityY = new double[cityPath.size()];
        for (City city:cityPath) {
            this.cityPath.add(city.getId());
            this.cityX[i] = city.getCoor().getX();
            this.cityY[i] = city.getCoor().getY();
            i++;
        }
        size = cityPath.size();
        this.pathDist = updateDist();
        initialize();
    }

    public Solution(ArrayList<Integer> cityPath, double[] cityX, double[] cityY, int size) {
        this.cityPath = cityPath;
        this.cityX = cityX;
        this.cityY = cityY;
        this.size = size;
        this.pathDist = updateDist();
    }

    public ArrayList<Integer> getCityPath() {
        return cityPath;
    }

    public void setCityPath(ArrayList<Integer> cityPath) {
        this.cityPath = cityPath;
    }

    public double getPathDist() {
        return pathDist;
    }

    public void setPathDist(double pathDist) {
        this.pathDist = pathDist;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }





    /**
     * Initializes a solution/route with a random permutation
     */
    public void initialize() {
        // TODO Auto-generated method stub
        Collections.shuffle(cityPath);
        updateDist();
    }

    /**
     * Prints the path
     */

    public void print()
    {
        for (Iterator iterator = cityPath.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    /**
     * Updates the distance of all path.
     * @return
     */
    public double updateDist()
    {
        double totalDist = 0.0;
        int currentCity, nextCity;
        Iterator it = cityPath.iterator();
        currentCity = (int) it.next();
        int startedCity = currentCity;
        while (it.hasNext()) {
            nextCity = (int) it.next();
            totalDist += edgeDist(currentCity-1, nextCity-1);
            currentCity =nextCity;
        }
        // dist from last city to first city
        totalDist += edgeDist(startedCity-1, currentCity-1);
        this.pathDist = totalDist;
        return totalDist;
    }

    /**
     * Returns the distance between the node a and b
     * @param a
     * @param b
     * @return
     */
    public double edgeDist(int a, int b)
    {

        double dist = Math.sqrt( (cityX[a]-cityX[b])*(cityX[a]-cityX[b]) + (cityY[a]-cityY[b])*(cityY[a]-cityY[b]) );
        return dist;
    }


}

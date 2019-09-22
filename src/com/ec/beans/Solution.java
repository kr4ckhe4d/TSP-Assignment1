package com.ec.beans;

import java.util.*;

/**
 * author: Li He
 */
public class Solution  extends ArrayList<City> implements Comparable<Solution> {
    private ArrayList<Integer> cityPath;  //The path of all cities
    public double cityX[];  //coordinate x of i city
    public double cityY[]; //coordinate y of i city
    Random RNG;
    int size;
    //    private List<Double> cityDist;
    private double pathDist;

    public Solution(){
        cityPath = new ArrayList<Integer>();
    }

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
//        initialize();
    }

    public Solution(ArrayList<Integer> cityPath, double[] cityX, double[] cityY, int size) {
        this.cityPath = cityPath;
        this.cityX = cityX;
        this.cityY = cityY;
        this.size = size;
        this.pathDist = updateDist();
    }

    /**
     * random build a solution
     * @param cityAmount
     * @param x_maximum maximum coord_x
     * @param y_maximum maximum coord_y
     */
    public Solution(int cityAmount, int x_maximum, int y_maximum) {
        Integer cityId = 1;
        while (this.size() < cityAmount) {
            City temp = null;
            do {
                temp = new City(cityId, true, x_maximum, y_maximum);
            } while (this.existCityPosition(temp));
            this.add(temp);
            cityId++;
        }
        if (this.size() > cityAmount) {
            this.remove(this.size() - 1);
        }
        Collections.shuffle(this);
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
        Collections.shuffle(cityPath);
        updateDist();
    }

    /**
     * Prints the path
     */

    public void print()
    {
        for (Iterator<City> iterator = this.iterator(); iterator.hasNext();) {
            System.out.print(iterator.next().getId() + " ");
        }
        System.out.println();
    }

    public void printXY()
    {
        for (Iterator<City> iterator = this.iterator(); iterator.hasNext();) {
            City city = iterator.next();
            System.out.println(city.getX() + "\t"+city.getY());
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
     * Updates the distance for new of all path.
     * @return
     */
    public double updateDist1()
    {
        double result = edgeDist(this.get(0), this.get(this.size() - 1));

        // add the remaining distances
        for (int i = 0; i < this.size() - 1; i++) {
            result += edgeDist(this.get(i), this.get(i + 1));
        }
        this.pathDist = result;
        return result;
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

    /**
     * Returns the distance between the node a and b
     * @param a
     * @param b
     * @return
     */
    public static double edgeDist(City a, City b)
    {
        double ax = (double) a.getX();
        double ay = (double) a.getY();
        double bx = (double) b.getX();
        double by = (double) b.getY();
        return Math.sqrt( (ax-bx)*(ax-bx) + (ay-by)*(ay-by) );
    }


    public boolean existCityPosition(City city) {
        for (City C : this) {
            if ((city.getX() == C.getX()) && (city.getY() == C.getY())) {
                return true;
            }
        }
        return false;
    }
    public Solution copy() {
        Solution sol = new Solution();
        for (City C : this) {
            sol.add(C.clone());
        }
        sol.setPathDist(pathDist);
        return sol;
    }
    @Override
    public int compareTo(Solution sol) {
        if (this.pathDist == sol.pathDist) {
            return 0;
        } else if (this.pathDist > sol.pathDist) {
            return -1;
        } else {
            return 1;
        }
    }

    public void normaliseCitySpace() {  // normalise the city positions to fit in a [20,20]
        double maxPosX = 0.0;
        double maxPosY = 0.0;
        for (City C : this) {
            if (C.getX() > maxPosX) {
                maxPosX = C.getX();
            }
            if (C.getY() > maxPosY) {
                maxPosY = C.getY();
            }
        }
        double rescaleFactorX = 20.0 / maxPosX;
        double rescaleFactorY = 20.0 / maxPosY;

        for (City C : this) {
            C.setX(C.getX() * rescaleFactorX);
            C.setY(C.getY() * rescaleFactorY);
        }
    }

}

package com.ec.beans;

import java.util.Random;

public class City  implements Comparable<City>{
    private int id;
    private Coor coor;
    private double x;
    private double y;
    private static Random RNG = new Random(System.nanoTime());

    /**
     * Gets city id
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets city id
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets city coordinates
     * @return coor
     */
    public Coor getCoor() {
        return coor;
    }

    /**
     * Sets city coordinates
     * @param coor
     */
    public void setCoor(Coor coor) {
        this.coor = coor;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    /**
     * Calculate distance between two city
     * @param city
     * @return
     */
    public double distanceTo(City city){
        double distX = Math.abs(getCoor().getX() - city.getCoor().getX());
        double distY = Math.abs(getCoor().getY() - city.getCoor().getY());
        double distance = Math.sqrt((distX*distX)+(distY*distY));
        return distance;
    }

    @Override
    public String toString() {
        return id + " "+ coor.getX() + " "+ coor.getY();
    }

    public City() {
    }

    // CONSTRUCTOR FOR FIXED POSITION
    public City(int number, boolean randomPosition, double x, double y) {     // if randomPosition is TRUE, [x,y] are the upper limits for a randomly chosen position. Otherwise, [x,y] is the assigned position.
        id = number;
        if (randomPosition) {
            setX(RNG.nextDouble() * x);   // java random.nextDouble() returns continuous number in range [0.0 , 1.0]
            setY(RNG.nextDouble() * y);
        } else {
            setX(x);
            setY(y);
        }
    }

    @Override
    public City clone() {
        return new City(id, false, getX(), getY());
    }

    @Override
    public int compareTo(City other) {
        if (this.id < other.id) {
            return -1;
        } else if (this.id > other.id) {
            return 1;
        } else {
            return 0;
        }
    }

}

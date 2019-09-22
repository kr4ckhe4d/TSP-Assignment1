package com.ec.beans;

public class City {
    private int id;
    private Coor coor;

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
}

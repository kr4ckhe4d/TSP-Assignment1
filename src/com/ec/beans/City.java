package com.ec.beans;

public class City {
    private int id;
    private Coor coor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coor getCoor() {
        return coor;
    }

    public void setCoor(Coor coor) {
        this.coor = coor;
    }

    public double distanceTo(City city){
        double distX = Math.abs(getCoor().getX() - city.getCoor().getX());
        double distY = Math.abs(getCoor().getY() - city.getCoor().getY());
        double distance = Math.sqrt((distX*distX)+(distY*distY));
        return distance;
    }

    @Override
    public String toString() {
        return "{" + id + "}";
    }
}

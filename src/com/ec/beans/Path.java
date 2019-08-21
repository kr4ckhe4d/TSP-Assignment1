package com.ec.beans;

import java.util.ArrayList;
import java.util.List;

public class Path {
    private List cityPath;
    private List<Double> cityDist;
    private double pathDist;
    public Path() {
        this.cityPath = new ArrayList();
        this.cityDist = new ArrayList();
    }

    public List getCityPath() {
        return cityPath;
    }

    public void setCityPath(List cityPath) {
        this.cityPath = cityPath;
    }

    public List getCityDist() {
        return cityDist;
    }

    public void setCityDist(List<Double> cityDist) {
        this.cityDist = cityDist;
    }

    public double getPathDist() {
        return pathDist;
    }

    public void setPathDist(double pathDist) {
        this.pathDist = pathDist;
    }
}

package com.ec;

import com.ec.beans.City;
import com.ec.beans.Coor;
import com.ec.beans.Path;

import java.util.List;

public class LocalSearch {

    public List twoOpt(List<City> cityList){
        for (City tem: cityList
             ) {

        }
        return null;
    }

    /**
     * calculate the distance of 2 cities
     * @param fromCity
     * @param toCity
     * @return Double type distance
     */
    public Double cal2CitiesDist(Coor fromCity, Coor toCity){
        Double distX = Math.pow((fromCity.getX()-toCity.getX()),2);
        Double distY = Math.pow((fromCity.getY()-toCity.getY()),2);
        return Math.sqrt(distX+distY);
    }

    /**
     * calculate the distance of the path
     * @param path
     * @return
     */
    public Double calPathDist(Path path){
        List<Double> cityDist = path.getCityDist();
        Double pathDist = new Double(0);
        for (Double dist: cityDist
             ) {
            pathDist+=dist;
        }
        return pathDist;
    }

    /**
     * compare distance of two paths
     * @param path_one
     * @param path_two
     * @return true,  path_one > path_two
     *         false, path_one < path_two
     */
    public boolean compare_paths(Path path_one,Path path_two){
        if(path_one.getPathDist()>path_two.getPathDist())
            return true;
        else
            return false;
    }

    public Path jump(Path path){
        return null;
    }

    public Path exchange(Path path){
        return null;
    }

    public Path opt_2(Path path){
        return null;
    }

    public static void main(String[] args) {
	// write your code here
    }
}

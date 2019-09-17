package com.ec.beans;

import java.util.ArrayList;

public class DataInstance {
    double[][] euclideanDistances;
    int noOfCities;


    DataInstance(int numberOfCities) {
        euclideanDistances = new double[numberOfCities][numberOfCities];
        this.noOfCities = numberOfCities;
    }

    public ArrayList<City> generateCities(int min, int max) {
        ArrayList<City> cities = new ArrayList<City>();
        for (int i = 0; i < noOfCities; i++) {
            // int yValue = min + rand.nextInt(50);
            double xValue = min + Math.random() * (max - min);
            // generates y values
            double yValue = min + Math.random() * (max - min);
            City c = new City();
            Coor coor = new Coor();
            coor.setX(xValue);
            coor.setY(yValue);
            c.setId(i);
            c.setCoor(coor);

            cities.add(c);
        }
        return cities;
    }

    public void generateTSP(ArrayList<City> cities) {
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i; j < cities.size(); j++) {
                if (cities.get(i).getId() != cities.get(j).getId()) {
                    euclideanDistances[cities.get(i).getId()][cities.get(j).getId()] = calculateEuclideanDistance(cities.get(i), cities.get(j));
                    euclideanDistances[cities.get(j).getId()][cities.get(i).getId()] = euclideanDistances[cities.get(i).getId()][cities.get(j).getId()];
                } else {
                    euclideanDistances[cities.get(i).getId()][cities.get(j).getId()] = 0;
                }
            }
        }
    }

    public static void main(String[] args) {
        DataInstance di = new DataInstance(5);
        ArrayList<City> cities = di.generateCities(0, 50);

        di.generateTSP(cities);
        for (int i = 0; i < cities.size(); i++) {
            for (int j = 0; j < cities.size(); j++) {
                System.out.print(di.euclideanDistances[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public double calculateEuclideanDistance(City source, City dest) {
        double distance = 0.0;

        distance += Math.pow(source.getCoor().getX() - dest.getCoor().getX(), 2.0);
        distance += Math.pow(source.getCoor().getY() - dest.getCoor().getY(), 2.0);

        return Math.sqrt(distance);
    }
}

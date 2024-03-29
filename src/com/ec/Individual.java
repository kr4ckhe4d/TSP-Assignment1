package com.ec;

import com.ec.beans.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Individual {
    private List<City> individual = new ArrayList<>(); //Holds list of city path.
    private double fitness = 0;
    private int distance = 0;

    // Constructor that generate individual with null
    public Individual(){
        for(int i=0; i<TSPProblem.getCitySize(); i++){
            individual.add(null);
        }
    }

    public Individual(ArrayList individual){
        this.individual = individual;
    }

    //Creates a random individual
    public void generateIndividual(){
        for(int i= 0; i<TSPProblem.getCitySize(); i++){
            setCity(i,TSPProblem.getCity(i));
        }
        Collections.shuffle(individual);
    }

    //Gets a city from individual
    public City getCity(int position){
        return individual.get(position);
    }

    //Sets a city to an individual
    public void setCity(int position, City city){
        individual.set(position,city);
        //Resetting since individual is altered.
        fitness = 0;
        distance = 0;
    }

    public int getCityIndex(int cityId) {
        for (int i = 0; i < individual.size(); i++) {
            if (cityId == individual.get(i).getId()) {
                return i;
            }
        }
        return -1;
    }

    //Get Next City in the individual
    public City getNextCity(int position){
        int index = getCityIndex(position);
        if(index != -1) {
            if ((index + 1) >= individualSize()) {
                return individual.get(0);
            } else {
                return individual.get(index+1);
            }
        }
        return null;
    }

    //Gets individual fitness
    public double getFitness(){
        if(fitness == 0){
            fitness = 1/getDistance();
        }
        return fitness;
    }

    //Gets the total distance of individual's all city
    public double getDistance(){
        if(distance == 0){
            int individualDist = 0;
            for(int individualIndex=0; individualIndex < individualSize(); individualIndex++){
                City fromCity = getCity(individualIndex);
                City destCity;
                //Check we're not on our individual's last city, if we are set our individual's final
                // destination city to our starting city
                if(individualIndex+1 < individualSize()){
                    destCity = getCity(individualIndex+1);
                }
                else{
                    destCity = getCity(0);
                }

                individualDist += fromCity.distanceTo(destCity);
            }
            distance = individualDist;
        }
        return distance;
    }

    // returns individual size
    public int individualSize(){
        return individual.size();
    }

    //check individual contains city
    public boolean containsCity(City city){
        return individual.contains(city);
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < individualSize(); i++) {
            geneString += getCity(i)+"\n";
        }
        return geneString;
    }
}

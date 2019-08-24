package com.ec.operators;

import com.ec.Individual;
import com.ec.beans.City;

import java.util.ArrayList;
import java.util.List;

public class TranslateIndividual {
    public static ArrayList<Individual> translateChildren(ArrayList<ArrayList<Integer>> arrayLists, List<City> cities) {
        ArrayList<Individual> individuals = new ArrayList<>();
        for (int i = 0; i < arrayLists.size(); i++) {
            Individual individual = new Individual();
            City city = new City();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                for (int k = 0; k < cities.size(); k++) {
                    if (arrayLists.get(i).get(j) == cities.get(k).getId()) {
                        city = cities.get(k);
                        break;
                    }
                }
                individual.setCity(j, city);
            }
            individuals.add(individual);
        }
        return individuals;
    }

    public static Individual translateChild(ArrayList<Integer> arrayList, List<City> cities) {
        Individual individual = new Individual();
        for (int i = 0; i < arrayList.size(); i++) {
            City city = new City();
            for (int j = 0; j < cities.size(); j++) {
                if (arrayList.get(i) == cities.get(j).getId()) {
                    city = cities.get(j);
                    break;
                }
            }
            individual.setCity(i,city);
        }
        return individual;
    }

    public static  ArrayList<Integer> translateIndividual(Individual individual){
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < individual.individualSize(); i++) {
            arrayList.add(individual.getCity(i).getId());
        }
        return arrayList;
    }

}

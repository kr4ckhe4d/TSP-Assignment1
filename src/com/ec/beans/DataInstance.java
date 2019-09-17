package com.ec.beans;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataInstance {
    public ArrayList randXYPoint(int numberOfCities, int min, int max) throws IOException {
        String fileName = "src/com/ec/"+"fileName" + numberOfCities+".tsp.txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        ArrayList<String> cities = new ArrayList<String>();
        for (int i = 1; i <= numberOfCities; i++) {
            // int yValue = min + rand.nextInt(50);
            int xValue = (int) ((Integer) min + Math.random() * (max - min));
            // generates y values
            int yValue = (int) (min + Math.random() * (max - min));
            String city = String.valueOf(i) + " " + String.valueOf(xValue) + " " + String.valueOf(yValue) + "\n";
            writer.write(city);
            cities.add(city);
        }
        writer.close();
        return cities;
    }

}

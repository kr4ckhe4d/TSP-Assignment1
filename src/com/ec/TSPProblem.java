package com.ec;

import com.ec.beans.City;
import com.ec.beans.Coor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPProblem {

    private static List<City> cities;

    public TSPProblem(String filename){
        cities = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            //Skips First 6 lines of data
            for(int i=1; i<=6; i++){
                br.readLine();
            }

            //Read
            String line = br.readLine();
            while(!line.equals("EOF")){
                City city = new City();
                Coor coor = new Coor();
                String[] lineValue = line.split(" ");

                coor.setX(Integer.parseInt(lineValue[1]));
                coor.setY(Integer.parseInt(lineValue[2]));

                city.setId(Integer.parseInt(lineValue[0]));
                city.setCoor(coor);

                cities.add(city);
                line = br.readLine();
            }
            br.close();
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public List<City> getCities(){
        return cities;
    }

    public static int getCitySize(){
        return cities.size();
    }

    public static City getCity(int index){
        return cities.get(index);
    }

}

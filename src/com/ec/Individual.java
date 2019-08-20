package com.ec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Individual {
    private static class City{
        public String getName() {
            return name;
        }

        public City(String name, double latitude, double longitude) {
            this.name = name;
            this.latitude = latitude;
            this.longitude = longitude;
        }

        String name;
        double latitude;

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
    }
    public static void main(String[] args){
        List<City> cities= Arrays.asList(new City[]{
                new City("a", 1, 2),
                new City("a", 1, 2),
                new City("a", 1, 2),
                new City("a", 1, 2),
                new City("a", 1, 2)
        });
        System.out.println(permute(cities));
    }

    public double calculateDistance(City a, City b){
        double x1 = a.getLatitude(),
                x2=a.getLongitude(),
                y1=b.getLatitude(),
                y2=b.getLongitude();
        double dis;
        dis=Math.sqrt((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1));
        return dis;
    }

    private static void swap(List<City> nums, int i, int j){
        City temp = nums.get(i);
        nums.set(i,nums.get(j));
        nums.set(j,temp);
    }

    public static List<List<City>> permute(List<City> nums) {
        List<List<City>> result = new ArrayList<>();
        helper(0, nums, result);
        return result;
    }

    private static void helper(int start, List<City> nums, List<List<City>> result){
        if(start==nums.size()-1){
            ArrayList<City> list = new ArrayList<City>();
            for(City num: nums){
                list.add(num);
            }
            result.add(list);
            return;
        }

        for(int i=start; i<nums.size(); i++){
            swap(nums, i, start);
            helper(start+1, nums, result);
            swap(nums, i, start);
        }
    }

}

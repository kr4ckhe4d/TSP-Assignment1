package com.ec;

import com.ec.beans.City;
import com.ec.beans.Coor;
import com.ec.beans.Solution;

import java.io.File;
import java.io.InputStream;
import java.util.*;

/**
 * Author Li He
 *
 */
public class LocalSearch {
    private static Random random = new Random(System.currentTimeMillis());


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
     * compare distance of two paths
     * @param path_one
     * @param path_two
     * @return true,  path_one > path_two
     *         false, path_one < path_two
     */
    public boolean compare_paths(Solution path_one,Solution path_two){
        if(path_one.getPathDist()>path_two.getPathDist())
            return true;
        else
            return false;
    }

    public Solution jump(Solution path,int firstIndex, int secondIndex){
        Solution solution = new Solution (path);
        int n = solution.getSize();

        int element1 = solution.getCityPath().remove(firstIndex);
        solution.getCityPath().add(secondIndex,element1);
//        int element2 = solution.getCityPath().remove(ran2);
//        solution.getCityPath().add(ran1, element2);
        solution.updateDist();
        return solution;
    }

    public Solution exchange(Solution path,int firstIndex, int secondIndex){
        Solution solution = new Solution (path);
        int n = solution.getSize();
        int element1 = solution.getCityPath().remove(firstIndex);
        solution.getCityPath().add(secondIndex,element1);
        int element2 = solution.getCityPath().remove(secondIndex-1);
        solution.getCityPath().add(firstIndex, element2);
        solution.updateDist();
        return solution;
    }


    public Solution opt_2(Solution solution,int firstIndex, int secondIndex){
        int n = solution.getSize();
        Solution result = new Solution(solution);
        ArrayList<Integer> citypath = result.getCityPath();
        double cityX[] = result.cityX;  //coordinate x of i city
        double cityY[] = result.cityY; //coordinate y of i city

        int i=firstIndex, j = secondIndex;
        int tem=0;
        double tx,ty;
        while(i<secondIndex&&i<j){
            tem = citypath.get(i);
            citypath.set(i,citypath.get(j));
            citypath.set(j,tem);
            i++;
            j--;
        }
//        Solution result = new Solution(citypath, cityX,cityY,citypath.size());
        result.updateDist();
        return result;

    }

    /**
     * @Author：
     * @Description：get all files under the path
     * @Date：
     */
    public static List<String> getFiles(String path) {
        List<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();

        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                files.add(tempList[i].toString());
            }
        }
        return files;
    }



    public Solution run(Solution initSolution,String operation) {
            Solution bestSolution = new Solution(initSolution);
            double minDistance = bestSolution.getPathDist();
            double Opt2Totalcost=0;
            int enhancement = 0;
//            while (enhancement < 20) {
                for (int i = 0; i < bestSolution.getSize() - 1; i++) {
                    for (int k = i + 1; k < bestSolution.getSize(); k++) {
                        Solution postSolution;
                        if(operation.equals("jump")){
                            postSolution = jump(bestSolution,i,k);
                        }else if(operation.equals("exchange")){
                            postSolution = exchange(bestSolution,i,k);
                        }else{
                            postSolution = opt_2(bestSolution,i,k);
                        }
//                        Solution postSolution = opt_2(bestSolution,i,k);
                        //Opt2Totalcost+=postSolution.getPathDist();
                        double newDistance = postSolution.getPathDist();
                        if (newDistance < minDistance) {
//                            enhancement = 0;
                            minDistance = newDistance;
                            bestSolution = postSolution;
                        }
                    }
                }
//                enhancement++;
//            }
        return bestSolution;
    }




    public static void main(String[] args) {
        LocalSearch ls = new LocalSearch();

        List<String> fileNames = getFiles("/Users/jonny/IdeaProjects/TSP-Assignment1/src/resources");
        ArrayList<ArrayList> Log = new ArrayList<ArrayList>();
        ArrayList files = new ArrayList();
        Double Opt2Totalcost = new Double(0);
        Double Opt2Mincost = new Double(0);
        for (String fn: fileNames) {
            TSPProblem tsp = new TSPProblem(fn);
            List<City> cities = tsp.getCities();
            System.out.println(fn.substring(56,fn.length()));
            Solution bestSolution = new Solution(tsp.getCities());
            if (cities == null || cities.size() < 1) {
                System.out.println("cities can not be null");
                return;
            }
            Opt2Totalcost=0.0;
            for (int i = 0; i < 30; i++) {
                bestSolution.initialize();
                Solution result = ls.run(bestSolution,"jump");
                Opt2Totalcost+=result.getPathDist();
                if(i==0)
                    Opt2Mincost=result.getPathDist();
                if(Opt2Mincost>result.getPathDist()){
//                    bestSolution = result;
                    Opt2Mincost=result.getPathDist();
                }
//                System.out.println(result.getPathDist());
            }
            System.out.println("Jump:Mean Cost \t\t Minimum Cost");
            System.out.println(Opt2Totalcost/30+" \t "+Opt2Mincost);

            Opt2Totalcost=0.0;
            for (int i = 0; i < 30; i++) {
                bestSolution.initialize();
                Solution result = ls.run(bestSolution,"exchange");
                Opt2Totalcost+=result.getPathDist();
                if(i==0)
                    Opt2Mincost=result.getPathDist();
                if(Opt2Mincost>result.getPathDist()){
//                    bestSolution = result;
                    Opt2Mincost=result.getPathDist();
                }
//                System.out.println(result.getPathDist());
            }
            System.out.println("Exchange:Mean Cost \t Minimum Cost");
            System.out.println(Opt2Totalcost/30+" \t "+Opt2Mincost);

            Opt2Totalcost=0.0;
            for (int i = 0; i < 30; i++) {
                bestSolution.initialize();
                Solution result = ls.run(bestSolution,"2opt");
                Opt2Totalcost+=result.getPathDist();
                if(i==0)
                    Opt2Mincost=result.getPathDist();
                if(Opt2Mincost>result.getPathDist()){
//                    bestSolution = result;
                    Opt2Mincost=result.getPathDist();
                }
//                System.out.println(result.getPathDist());
            }
            System.out.println("2opt:Mean Cost \t\t Minimum Cost");
            System.out.println(Opt2Totalcost/30+" \t "+Opt2Mincost);


        }


//        String inputFileName = "/burma14.tsp";
//        InputStream inputStream = ls1.getClass().getResourceAsStream(inputFileName);
//        Scanner in = new Scanner(inputStream);
//
//        System.out.println(in.nextLine());
//        TSPProblem tsp = new TSPProblem("/Users/jonny/IdeaProjects/TSP-Assignment1/src/resources/eil51.tsp.txt");
//        List<City> cities = tsp.getCities();
//        if(cities==null||cities.size()<1){
//            System.out.println("cities can not be null");
//            return ;
//
//        }
//        Solution solution =new Solution(tsp.getCities());
        /*ArrayList<Integer> path = new ArrayList();
        path.add(5);
        path.add(1);
        path.add(3);
        path.add(2);
        path.add(4);
        path.add(6);

        double[] x = {1,2,3,4,5,6};
        double[] y = {1,2,3,4,5,6};
        Solution solution =new Solution(path,x,y,path.size());
        solution.updateDist();
        for (Integer tem: solution.getCityPath()
             ) {
            System.out.print(tem+"\t");
        }
        System.out.println();
        Solution result = ls.exchange(solution,1,5);
        for (Integer tem: result.getCityPath()
             ) {
            System.out.print(tem+"\t");
        }*/
//        System.out.println(result.getCityPath().toArray());
//
//        int ran1,ran2;
//        for (int i = 0; i < 10; i++) {
//            ran1 = random.nextInt(3);
//            System.out.println(ran1);
//        }



    }
}

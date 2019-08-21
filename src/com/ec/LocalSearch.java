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

    public Solution jump(Solution path){
        Solution solution = new Solution (path);
        int n = solution.getSize();
        int ran1,ran2;
        ran1 = random.nextInt(n);
        do {
            ran2  = random.nextInt(n);
        } while (ran1 == ran2);
        if(ran1>ran2){
            int tem =ran1;
            ran1= ran2;
            ran2= tem;
        }
        int element1 = solution.getCityPath().remove(ran1);
        solution.getCityPath().add(ran2,element1);
//        int element2 = solution.getCityPath().remove(ran2);
//        solution.getCityPath().add(ran1, element2);
        solution.updateDist();
        return solution;
    }

    public Solution exchange(Solution path){
        Solution solution = new Solution (path);
        int n = solution.getSize();
        int ran1,ran2;
        ran1 = random.nextInt(n);
        do {
            ran2  = random.nextInt(n);
        } while (ran1 == ran2);
        if(ran1>ran2){
            int tem =ran1;
            ran1= ran2;
            ran2= tem;
        }
        int element1 = solution.getCityPath().remove(ran1);
        solution.getCityPath().add(ran2,element1);
        int element2 = solution.getCityPath().remove(ran2-1);
        solution.getCityPath().add(ran1, element2);
        solution.updateDist();
        return solution;
    }


    public Solution opt_2(Solution solution){
//        Solution sol = new Solution (solution);
        int n = solution.getSize();
        int ran1,ran2;
        ran1 = random.nextInt(n);
        do {
            ran2  = random.nextInt(n);
        } while (ran1 == ran2);
        if(ran1>ran2){
            int tem =ran1;
            ran1= ran2;
            ran2= tem;
        }
//                System.out.println("n = "+n+"  ran = "+ran+"  ran2 = "+ran2);
        ArrayList<Integer> citypath = solution.getCityPath();
        double cityX[] = solution.cityX;  //coordinate x of i city
        double cityY[] = solution.cityY; //coordinate y of i city

        int i=ran1, j = ran2;
        int tem=0;
        double tx,ty;
        while(i<ran2&&i<j){
            tem = citypath.get(i);
            citypath.set(i,citypath.get(j));
            citypath.set(j,tem);
            tx=cityX[i];
            cityX[i]=cityX[j];
            cityX[j]=tx;
            ty=cityY[i];
            cityY[i]=cityY[j];
            cityY[j]=ty;
            i++;
            j--;
        }
        Solution result = new Solution(citypath, cityX,cityY,citypath.size());
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

    public void run(){
        List<String> fileNames = getFiles("/Users/jonny/IdeaProjects/TSP-Assignment1/src/resources");
        ArrayList<ArrayList> prints = new ArrayList<ArrayList>();
        for (String fn: fileNames
        ) {
            TSPProblem tsp = new TSPProblem(fn);
            List<City> cities = tsp.getCities();
            if(cities==null||cities.size()<1){
                System.out.println("cities can not be null");
                return ;
            }
            System.out.println(fn.substring(56,fn.length()));

            System.out.println();
            System.out.println("Jump");
            Solution preSolution =new Solution(tsp.getCities());
            Solution bestSolution = preSolution;

            Double jumpTotalcost = new Double(0);
            for (int i = 0; i < 30; i++) {
                Solution postSolution = jump(bestSolution);
                jumpTotalcost+=postSolution.getPathDist();
                System.out.println(postSolution.getPathDist());
                if(bestSolution.getPathDist()>postSolution.getPathDist()){
                    bestSolution = postSolution;
                }
            }
            ArrayList printLineJ = new ArrayList();
            printLineJ.add(fn.substring(56,fn.length()));
            printLineJ.add("Jump");
            printLineJ.add(jumpTotalcost/30+"");
            printLineJ.add(bestSolution.getPathDist()+"");
            prints.add(printLineJ);
            System.out.println("Average Cost \t\t Minimum Cost");
            System.out.println(jumpTotalcost/30+" \t "+bestSolution.getPathDist());
//            System.out.println("Average Cost = "+jumpTotalcost/30);
//            System.out.println("Minimum Cost = "+bestSolution.getPathDist());
            System.out.println();


            System.out.println("exchange");
            bestSolution = preSolution;
            Double exchangeTotalcost = new Double(0);
            for (int i = 0; i < 30; i++) {
                Solution postSolution = exchange(bestSolution);
                exchangeTotalcost+=postSolution.getPathDist();
                System.out.println(postSolution.getPathDist());
                if(bestSolution.getPathDist()>postSolution.getPathDist()){
                    bestSolution = postSolution;
                }
            }
            ArrayList printLineE = new ArrayList();
            printLineE.add("");
            printLineE.add("Exchange");
            printLineE.add(exchangeTotalcost/30+"");
            printLineE.add(bestSolution.getPathDist()+"");
            prints.add(printLineE);
            System.out.println("Average Cost \t\t Minimum Cost");
            System.out.println(exchangeTotalcost/30+" \t "+bestSolution.getPathDist());
//            System.out.println("Average Cost = "+exchangeTotalcost/30);
//            System.out.println("Minimum Cost = "+bestSolution.getPathDist());

            System.out.println();
            System.out.println("2-Opt");
            bestSolution = preSolution;
            Double Opt2Totalcost = new Double(0);
            for (int i = 0; i < 30; i++) {
                Solution postSolution = opt_2(bestSolution);
                Opt2Totalcost+=postSolution.getPathDist();
                System.out.println(postSolution.getPathDist());
                if(bestSolution.getPathDist()>postSolution.getPathDist()){
                    bestSolution = postSolution;
                }
            }
            ArrayList printLineO = new ArrayList();
            printLineO.add("");
            printLineO.add("2-Opt");
            printLineO.add(Opt2Totalcost/30+"");
            printLineO.add(bestSolution.getPathDist()+"");
            prints.add(printLineO);
            System.out.println("Average Cost \t\t Minimum Cost");
            System.out.println(Opt2Totalcost/30+" \t "+bestSolution.getPathDist());
//            System.out.println("Average Cost = "+Opt2Totalcost/30);
//            System.out.println("Minimum Cost = "+bestSolution.getPathDist());
        }
        System.out.println();
        for (ArrayList<String> list: prints
        ) {
            for (String str: list
            ) {
                System.out.print(str+"\t");
            }
            System.out.println();

        }
    }


    public static void main(String[] args) {
        // write your code here
        LocalSearch ls = new LocalSearch();
        ls.run();
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
//        ArrayList<Integer> path = new ArrayList();
//        path.add(5);
//        path.add(1);
//        path.add(3);
//        path.add(2);
//        path.add(4);
//        path.add(6);
//
//        double[] x = {1,2,3,4,5,6};
//        double[] y = {1,2,3,4,5,6};
//        Solution solution =new Solution(path,x,y,path.size());
//        solution.updateDist();
//
//        Solution result = ls.exchange(solution);
//        for (Integer tem: result.getCityPath()
//             ) {
//            System.out.println(tem);
//        }
//        System.out.println(result.getCityPath().toArray());

//        int ran1,ran2;
//        for (int i = 0; i < 10; i++) {
//            ran1 = random.nextInt(3);
//            System.out.println(ran1);
//        }



    }
}

package com.ec;

import com.ec.beans.City;
import com.ec.beans.Coor;
import com.ec.beans.Solution;

import java.io.File;
import java.util.*;

/**
 * Author Li He
 * Local search
 */
public class EA_1_1 {
    private static Random random = new Random(System.currentTimeMillis());
    public static LinkedHashMap<String,LinkedHashMap<String,String>> dataLog = new LinkedHashMap<String,LinkedHashMap<String,String>>();

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


    /**
     * RUN the Local search algorithm
     * @param initSolution init solution
     * @param operation  operation type："jump" , "exchange", "2opt"
     * @return
     */
    public Solution run(Solution initSolution,String operation) {
            Solution bestSolution = new Solution(initSolution);
            double minDistance = bestSolution.getPathDist();
            double Opt2Totalcost=0;
            int enhancement = 0;
//            while (enhancement < 10) {
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




    public static void main1(String[] args) {
        EA_1_1 ls = new EA_1_1();

        List<String> fileNames = getFiles(args[0]);
//        List<String> fileNames = getFiles("/Users/jonny/IdeaProjects/TSP-Assignment1/src/resources");
        ArrayList<ArrayList> Log = new ArrayList<ArrayList>();
        ArrayList files = new ArrayList();
        Double Opt2Totalcost = new Double(0);
        Double Opt2Mincost = new Double(0);
        Long sumTime = new Long(0);
        for (String fn: fileNames) {
            TSPProblem tsp = new TSPProblem(fn);
            List<City> cities = tsp.getCities();
            System.out.println(fn.substring(fn.lastIndexOf("/")+1,fn.length()));
            Solution bestSolution = new Solution(tsp.getCities());
            if (cities == null || cities.size() < 1) {
                System.out.println("cities can not be null");
                return;
            }

            for (int j = 0; j < 3; j++) {
                String operator = "jump";
                if(j==0){
                    operator="jump";
                }else if(j==1){
                    operator="exchange";
                }else
                    operator="2opt";
                Opt2Totalcost=0.0;
                Opt2Mincost=0.0;
                sumTime=0L;
                System.out.println(operator);
                if(bestSolution.getSize()<500) {
                    for (int i = 0; i < 30; i++) {
                        bestSolution.initialize();
                            Long sTime = System.currentTimeMillis();
                            Solution result = ls.run(bestSolution, operator);
                            Long eTime = System.currentTimeMillis();
                            Opt2Totalcost += result.getPathDist();
                        sumTime+=(eTime-sTime);
                            if (i == 0)
                                Opt2Mincost = result.getPathDist();
                            if (result.getPathDist() < Opt2Mincost) {
    //                    bestSolution = result;
                                Opt2Mincost = result.getPathDist();
                            }

                    System.out.println("\t"+result.getPathDist()+"\t"+(eTime-sTime));
                    }
                    LinkedHashMap<String,String> subLog= dataLog.get(fn.substring(fn.lastIndexOf("/")+1));
                    if (subLog==null||subLog.size()<1)
                        subLog = new LinkedHashMap<String,String>();
                    subLog.put(operator+":Mean Cost",Opt2Totalcost/30+"");
                    subLog.put(operator+":Mean Time",sumTime/30+"");
                    subLog.put(operator+":Min Cost",Opt2Mincost+"");
                    dataLog.put(fn.substring(fn.lastIndexOf("/")+1,fn.length()),subLog);
                }else{
                    ThreadLocalSearch threadLS1 = new ThreadLocalSearch(bestSolution,operator,fn.substring(fn.lastIndexOf("/")+1,fn.length()));
                    ThreadLocalSearch threadLS2 = new ThreadLocalSearch(bestSolution,operator,fn.substring(fn.lastIndexOf("/")+1,fn.length()));
                    ThreadLocalSearch threadLS3 = new ThreadLocalSearch(bestSolution,operator,fn.substring(fn.lastIndexOf("/")+1,fn.length()));
                    ThreadLocalSearch threadLS4 = new ThreadLocalSearch(bestSolution,operator,fn.substring(fn.lastIndexOf("/")+1,fn.length()));
                    ThreadLocalSearch threadLS5 = new ThreadLocalSearch(bestSolution,operator,fn.substring(fn.lastIndexOf("/")+1,fn.length()));
                    ThreadLocalSearch threadLS6 = new ThreadLocalSearch(bestSolution,operator,fn.substring(fn.lastIndexOf("/")+1,fn.length()));
                    threadLS1.start();
                    threadLS2.start();
                    threadLS3.start();
                    threadLS4.start();
                    threadLS5.start();
                    threadLS6.start();
                    try {
                        threadLS1.join();
                        threadLS2.join();
                        threadLS3.join();
                        threadLS4.join();
                        threadLS5.join();
                        threadLS6.join();
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }

//                System.out.println("Main thread is finished");
                LinkedHashMap<String,String> subLog= dataLog.get(fn.substring(fn.lastIndexOf("/")+1));
                if (subLog==null||subLog.size()<1)
                    subLog = new LinkedHashMap<String,String>();
                subLog.put(operator+":Mean Cost",ThreadLocalSearch.totalCost/30+"");
                subLog.put(operator+":Min Cost",ThreadLocalSearch.minCost+"");
                EA_1_1.dataLog.put(fn.substring(fn.lastIndexOf("/")+1,fn.length()),subLog);
            }

            }


//            System.out.println("Jump:Mean Cost \t\t Minimum Cost");
//            System.out.println(Opt2Totalcost/30+" \t "+Opt2Mincost);

            /*Opt2Totalcost=0.0;
            Opt2Mincost=0.0;
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
            Opt2Mincost=0.0;
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
            System.out.println(Opt2Totalcost/30+" \t "+Opt2Mincost);*/


        }
        for (Map.Entry<String, LinkedHashMap<String, String>> entry : dataLog.entrySet()) {
            String fname = entry.getKey();
            System.out.println(fname);
            LinkedHashMap<String,String> dlog = entry.getValue();
            for (String key : dlog.keySet()) {
                System.out.print(key+"\t"+dlog.get(key)+"\t");
            }
            System.out.println();
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

    public static void main(String[] args){
    EA_1_1 ls = new EA_1_1();

    List<String> fileNames = getFiles(args[0]);
    ArrayList<ArrayList> Log = new ArrayList<ArrayList>();
    ArrayList files = new ArrayList();
    Double Opt2Totalcost = new Double(0);
    Double Opt2Mincost = new Double(0);
    Long sumTime = new Long(0);
        for (String fn: fileNames) {
        TSPProblem tsp = new TSPProblem(fn);
        List<City> cities = tsp.getCities();
        System.out.println(fn.substring(fn.lastIndexOf("/")+1,fn.length()));
        Solution bestSolution = new Solution(tsp.getCities());
        if (cities == null || cities.size() < 1) {
            System.out.println("cities can not be null");
            return;
        }

        for (int j = 0; j < 3; j++) {
            String operator = "jump";
            if(j==0){
                operator="jump";
            }else if(j==1){
                operator="exchange";
            }else
                operator="2opt";
            Opt2Totalcost=0.0;
            Opt2Mincost=0.0;
            sumTime=0L;
            System.out.println(operator);
                for (int i = 0; i < 30; i++) {
                    bestSolution.initialize();
                    Long sTime = System.currentTimeMillis();
                    Solution result = ls.run(bestSolution, operator);
                    Long eTime = System.currentTimeMillis();
                    Opt2Totalcost += result.getPathDist();
                    sumTime+=(eTime-sTime);
                    if (i == 0)
                        Opt2Mincost = result.getPathDist();
                    if (result.getPathDist() < Opt2Mincost) {
                        //                    bestSolution = result;
                        Opt2Mincost = result.getPathDist();
                    }

                    System.out.println("\t"+result.getPathDist()+"\t"+(eTime-sTime));
                }
                LinkedHashMap<String,String> subLog= dataLog.get(fn.substring(fn.lastIndexOf("/")+1));
                if (subLog==null||subLog.size()<1)
                    subLog = new LinkedHashMap<String,String>();
                subLog.put(operator+":Mean Cost",Opt2Totalcost/30+"");
                subLog.put(operator+":Mean Time",sumTime/30+"");
                subLog.put(operator+":Min Cost",Opt2Mincost+"");
                dataLog.put(fn.substring(fn.lastIndexOf("/")+1,fn.length()),subLog);
            }


    }
        for (Map.Entry<String, LinkedHashMap<String, String>> entry : dataLog.entrySet()) {
        String fname = entry.getKey();
        System.out.println(fname);
        LinkedHashMap<String,String> dlog = entry.getValue();
        for (String key : dlog.keySet()) {
            System.out.print(key+"\t"+dlog.get(key)+"\t");
        }
        System.out.println();
    }



    }
}

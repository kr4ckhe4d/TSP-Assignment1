package com.ec.operators;

import java.util.ArrayList;

public class CrossoverNew {

    //Find a random position
    public static int findRandomPosition(int i) {
        int position = (int) (Math.random() * i);
        return position;
    }

    /**
     * Author: Jianqi Zeng
     * There are two crossover operators
     */
    //Order Based Crossover
    public static ArrayList<ArrayList<Integer>> orderBasedCrossover(ArrayList<Integer> fatherList, ArrayList<Integer> motherList) {
        ArrayList<ArrayList<Integer>> childrenList = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> child1 = generateChild(fatherList, motherList);
        ArrayList<Integer> child2 = generateChild(motherList, fatherList);

        childrenList.add(child1);
        childrenList.add(child2);

        System.out.println(childrenList);
        return childrenList;
    }

    public static ArrayList<Integer> generateChild(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {

        ArrayList<Integer> copyParent1 = parent1;
        ArrayList<Integer> copyParent2 = parent2;
        ArrayList<Integer> templist = new ArrayList<>();
        for (int i = 0; i < copyParent1.size(); i++) {
            int randomNumber = findRandomPosition(9);
            double possibility = randomNumber * (0.1);
            if (possibility > 0.5) {
                templist.add(copyParent1.get(i));
            }
        }

        for (int i = 0; i < copyParent2.size(); i++) {
            for (int j = 0; j < templist.size(); j++) {
                if (templist.get(j) == copyParent2.get(i)) {
                    copyParent2.set(i, -1);
                }
            }
        }

        int index = 0;
        for (int i = 0; i < parent1.size(); i++) {
            if (copyParent2.get(i) == -1) {
                copyParent2.set(i, templist.get(index));
                index = index + 1;
            }
        }

        return copyParent2;
    }

    //Position Based Crossover
    public static ArrayList<ArrayList<Integer>> positionBasedCrossover(ArrayList<Integer> fatherList, ArrayList<Integer> motherList) {
        ArrayList<ArrayList<Integer>> childrenList = new ArrayList<ArrayList<Integer>>();

        ArrayList<Integer> child1 = generatePositionChild(fatherList, motherList);
        ArrayList<Integer> child2 = generatePositionChild(motherList, fatherList);

        childrenList.add(child1);
        childrenList.add(child2);

        System.out.println(childrenList);

        return childrenList;
    }

    public static ArrayList<Integer> generatePositionChild(ArrayList<Integer> parent1, ArrayList<Integer> parent2) {
        ArrayList<Integer> resultList = new ArrayList<>();
        ArrayList<Integer> copyParent1 = parent1;
        ArrayList<Integer> copyParent2 = parent2;
        ArrayList<Integer> templist = new ArrayList<>();
        for (int i = 0; i < copyParent1.size(); i++) {
            int randomNumber = findRandomPosition(9);
            double possibility = randomNumber * (0.1);
            if (possibility > 0.5) {
                templist.add(copyParent1.get(i));
            } else {
                copyParent1.set(i, -1);
            }
        }

        for (int i = 0; i < copyParent2.size(); i++) {
            for (int j = 0; j < templist.size(); j++) {
                if(copyParent2.get(i) == templist.get(j)){
                    copyParent2.remove(i);
                    i = i - 1;
                }
            }
        }

        int index = 0;

        for (int i = 0; i < copyParent1.size(); i++) {
            if(copyParent1.get(i) == -1){
                copyParent1.set(i, copyParent2.get(index));
                index = index + 1;
            }
        }

        return resultList;
    }

}

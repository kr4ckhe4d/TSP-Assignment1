package com.ec.operators;

import com.ec.Individual;
import com.ec.beans.Coor;

import java.util.ArrayList;

public class MutationNCrossover {

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


    /**
     * Author: Jianqi Zeng
     * There are three mutation operators
     */
    //Inversion mutation (IM)
    public static Individual invidualLevelInversion(Individual individuals){
        ArrayList<Double> xArrayList = new ArrayList<>();
        ArrayList<Double> yArrayList = new ArrayList<>();
        for (int i = 0; i < individuals.individualSize(); i++) {
            xArrayList.add(individuals.getCity(i).getCoor().getX());
            yArrayList.add(individuals.getCity(i).getCoor().getY());
        }

        xArrayList = complexInversionMutation(xArrayList);
        yArrayList = complexInversionMutation(yArrayList);

        for (int i = 0; i < individuals.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(xArrayList.get(i));
            coor.setY(yArrayList.get(i));
            individuals.getCity(i).setCoor(coor);
        }

        return individuals;
    }    
    
    public static ArrayList complexInversionMutation(ArrayList<Double> arrayList) {
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Double> resultList = new ArrayList<>();
        resultList = arrayList;
        int flag = 0;

        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(resultList.size());
            }
        }

        if (randomPosition1 > randomPosition2) {
            int temp = randomPosition1;
            randomPosition1 = randomPosition2;
            randomPosition2 = temp;
        }

        ArrayList<Double> tempList = new ArrayList<>();
        for (int i = randomPosition1; i <= randomPosition2; i++) {
            tempList.add(resultList.get(i));
            resultList.set(i, -1.0);
        }

        int gap = randomPosition2 - randomPosition1;
        int index = tempList.size() - 1;

        System.out.println(tempList);

        for (int i = randomPosition2; i <= randomPosition2 + gap; i++) {
            resultList.add(i, tempList.get(index));
            index = index - 1;
        }

        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i) == -1) {
                resultList.remove(i);
                i = i - 1;
            }
        }

        System.out.println(resultList);

        return resultList;
    }

    //Displacement mutation (DM)
    public static Individual displacementLevelInversion(Individual individuals){
        ArrayList<Double> xArrayList = new ArrayList<>();
        ArrayList<Double> yArrayList = new ArrayList<>();
        for (int i = 0; i < individuals.individualSize(); i++) {
            xArrayList.add(individuals.getCity(i).getCoor().getX());
            yArrayList.add(individuals.getCity(i).getCoor().getY());
        }

        xArrayList = displacementMutation(xArrayList);
        yArrayList = displacementMutation(yArrayList);

        for (int i = 0; i < individuals.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(xArrayList.get(i));
            coor.setY(yArrayList.get(i));
            individuals.getCity(i).setCoor(coor);
        }

        return individuals;
    }

    public static ArrayList displacementMutation(ArrayList<Double> arrayList) {
        int flag = 0;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Double> resultList = new ArrayList<>();
        resultList = arrayList;

        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(resultList.size());
            }
        }
//        System.out.println(randomPosition1 + "  ******  " + randomPosition2);

        //Make sure the randomposition2 is bigger than randomposition1
        if (randomPosition1 > randomPosition2) {
            int temp = randomPosition1;
            randomPosition1 = randomPosition2;
            randomPosition2 = temp;
        }

        ArrayList<Double> tempList = new ArrayList<>();
        for (int i = randomPosition1; i <= randomPosition2; i++) {
            tempList.add(resultList.get(i));
            resultList.set(i, -1.0);
        }

        for (int i = 0; i < resultList.size(); i++) {
            if (resultList.get(i) == -1) {
                resultList.remove(i);
                i = i - 1;
            }
        }

        int randomPosition3 = findRandomPosition(resultList.size());

        for (int i = 0; i < tempList.size(); i++) {
            resultList.add(i + randomPosition3, tempList.get(i));
        }

        System.out.println(resultList);

        return resultList;
    }

    //Scramble mutation
    public static Individual scrambleLevelInversion(Individual individuals){
        ArrayList<Double> xArrayList = new ArrayList<>();
        ArrayList<Double> yArrayList = new ArrayList<>();
        for (int i = 0; i < individuals.individualSize(); i++) {
            xArrayList.add(individuals.getCity(i).getCoor().getX());
            yArrayList.add(individuals.getCity(i).getCoor().getY());
        }

        xArrayList = scrambleMutation(xArrayList);
        yArrayList = scrambleMutation(yArrayList);

        for (int i = 0; i < individuals.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(xArrayList.get(i));
            coor.setY(yArrayList.get(i));
            individuals.getCity(i).setCoor(coor);
        }

        return individuals;
    }

    public static ArrayList scrambleMutation(ArrayList<Double> arrayList) {
        int flag = 0;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Double> resultList = new ArrayList<>();
        resultList = arrayList;

        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(resultList.size());
            }
        }
//        System.out.println(randomPosition1 + "  ******  " + randomPosition2);

        //Make sure the randomposition2 is bigger than randomposition1
        if (randomPosition1 > randomPosition2) {
            int temp = randomPosition1;
            randomPosition1 = randomPosition2;
            randomPosition2 = temp;
        }

        int gap = randomPosition2 - randomPosition1;
        ArrayList<Double> tempList = new ArrayList<>();

        // Get the value between two positions
        for (int i = randomPosition1; i < randomPosition2; i++) {
            tempList.add(resultList.get(i));
            resultList.set(i, -1.0);
        }

        for (int i = 0; i < tempList.size(); i++) {
            int randomPosition3 = findRandomPosition(gap);
            if (resultList.get(randomPosition1 + randomPosition3) == -1) {
                resultList.set(randomPosition1 + randomPosition3, tempList.get(i));
            } else {
                i = i - 1;
            }
        }
        System.out.println(resultList);
        return resultList;

    }


}

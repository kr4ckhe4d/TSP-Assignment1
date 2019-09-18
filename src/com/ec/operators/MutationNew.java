package com.ec.operators;

import java.util.ArrayList;

public class MutationNew {

    /**
     * Author: Jianqi Zeng
     * There are three mutation operators
     */
    //Find a random position
    public static int findRandomPosition(int i) {
        int position = (int) (Math.random() * i);
        return position;
    }

    //Inversion mutation (IM)
    public static ArrayList complexInversionMutation(ArrayList<Integer> arrayList) {
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Integer> resultList = new ArrayList<>();
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

        ArrayList<Integer> tempList = new ArrayList<>();
        for (int i = randomPosition1; i <= randomPosition2; i++) {
            tempList.add(resultList.get(i));
            resultList.set(i, -1);
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
    public static ArrayList displacementMutation(ArrayList<Integer> arrayList) {
        int flag = 0;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Integer> resultList = new ArrayList<>();
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

        ArrayList<Integer> tempList = new ArrayList<>();
        for (int i = randomPosition1; i <= randomPosition2; i++) {
            tempList.add(resultList.get(i));
            resultList.set(i, -1);
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
    public static ArrayList scrambleMutation(ArrayList<Integer> arrayList) {
        int flag = 0;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Integer> resultList = new ArrayList<>();
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
        ArrayList<Integer> tempList = new ArrayList<>();

        // Get the value between two positions
        for (int i = randomPosition1; i < randomPosition2; i++) {
            tempList.add(resultList.get(i));
            resultList.set(i, -1);
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

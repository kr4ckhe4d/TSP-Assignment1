package com.ec.operators;

import java.util.ArrayList;

public class Mutation {

    /**
     * Author: Jianqi Zeng
     * There are operators about Mutation
     */

    //this is insert mutation operator
    public static ArrayList insertMutation(ArrayList<Integer> arrayList) {
        int flag = 0;
        int tempValue;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<Integer> result = arrayList;

        //they can not be in same position
        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(arrayList.size());
            }
        }

        tempValue = result.get(randomPosition2);
        result.remove(randomPosition2);
        result.add(randomPosition1, tempValue);

//        System.out.println("第一个位置" + randomPosition1);
//        System.out.println("第二个位置" + randomPosition2);
//        System.out.println(result);
        return result;
    }

    //this is swap mutation operator
    public static ArrayList swapMutation(ArrayList<Integer> arrayList) {
        ArrayList<Integer> result = arrayList;
        int flag = 0;
        int tempValue1;
        int tempValue2;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());

        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(arrayList.size());
            }
        }

        tempValue1 = result.get(randomPosition1);
        tempValue2 = result.get(randomPosition2);

        result.set(randomPosition1, tempValue2);
        result.set(randomPosition2, tempValue1);

//        System.out.println("第一个位置" + randomPosition1);
//        System.out.println("第二个位置" + randomPosition2);
//        System.out.println(result);

        return result;
    }

    //this is the inversion operator
    public static ArrayList inversionMutation(ArrayList<Integer> arrayList) {
        ArrayList<Integer> result = arrayList;

        int flag = 0;
        int i;
        int j;
        int tempValue1;
        int tempValue2;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());

        //不能两个是同一个位置的东西
        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(arrayList.size());
            }
        }

        if (randomPosition1 < randomPosition2) {
            i = randomPosition1;
            j = randomPosition2;
        } else {
            i = randomPosition2;
            j = randomPosition1;
        }

        while (flag == 1) {
//            System.out.println("i是" + i + "\tj是" + j);
            if (i < j) {
                tempValue1 = result.get(i);
                tempValue2 = result.get(j);

                result.set(i, tempValue2);
                result.set(j, tempValue1);

                i++;
                j--;
            } else {
                flag = 0;
            }
        }
//
//        System.out.println("第一个位置" + randomPosition1);
//        System.out.println("第二个位置" + randomPosition2);
//        System.out.println(result);

        return result;
    }

    //find the random position
    public static int findRandomPosition(int i) {
        int position = (int) (Math.random() * i);
        return position;
    }
}

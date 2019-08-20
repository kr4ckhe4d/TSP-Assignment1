package com.ec.operators;

import java.util.ArrayList;

public class Crossover {
    /**
     * Author: Jianqi Zeng
     * There are operators about Crossover
     */

    //Find a random position
    public static int findRandomPosition(int i) {
        int position = (int) (Math.random() * i);
        return position;
    }

    //this is order crossover
    public static ArrayList<ArrayList<Integer>> orderCrossover(ArrayList<ArrayList<Integer>> arrayLists) {
        ArrayList<ArrayList<Integer>> resultLists = arrayLists;
        int flag = 0;
        int tempValue;

        ArrayList<Integer> fatherList = createList(arrayLists.get(0));
        ArrayList<Integer> motherList = createList(arrayLists.get(1));

        ArrayList<Integer> child1;
        ArrayList<Integer> child2;

        int randomPosition1 = findRandomPosition(fatherList.size());
        int randomPosition2 = findRandomPosition(fatherList.size());

        //they can not be in the same position
        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
                if (randomPosition1 > randomPosition2) {
                    tempValue = randomPosition1;
                    randomPosition1 = randomPosition2;
                    randomPosition2 = tempValue;
                }
            } else {
                randomPosition2 = findRandomPosition(fatherList.size());
            }
        }

//        System.out.println("位置1 " + randomPosition1);
//        System.out.println("位置2 " + randomPosition2);

        child1 = switchOperator(randomPosition1, randomPosition2, fatherList, motherList);
        child2 = switchOperator(randomPosition1, randomPosition2, motherList, fatherList);

//        System.out.println(child1);
//        System.out.println(child2);

        return resultLists; //Return two children
    }

    //Order Crossover operator
    public static ArrayList<Integer> switchOperator(int position1, int position2, ArrayList<Integer> parent1List, ArrayList<Integer> parent2List) {
        //make sure the position and add the rest part of the parent list

        int takePosition = 0;

        ArrayList<Integer> arrayList = createList(parent1List);
        ArrayList<Integer> arrayList1 = createList(parent2List);
        ArrayList<Integer> tempList = new ArrayList<>();
        ArrayList<Integer> choiceList = new ArrayList<>();

        for (int i = position1; i <= position2; i++) {
            tempList.add(arrayList.get(i));
        }

        for (int i = 0; i < arrayList1.size(); i++) {
            if (compareNum(arrayList1.get(i), tempList)) {
                choiceList.add(arrayList1.get(i));
            }
        }

//        System.out.println("选中列 " + tempList);
//        System.out.println("被选列：" + choiceList);

        for (int i = position2 + 1; i != position1; i++) {
            if (i == parent1List.size()) {
                i = -1;
            } else {
                arrayList.set(i, choiceList.get(takePosition));
                takePosition++;
            }
        }

        return arrayList;
    }

    public static boolean compareNum(int i, ArrayList<Integer> arrayList) {
        int status = 0;

        for (int j = 0; j < arrayList.size(); j++) {
            if (i == arrayList.get(j)) {
                status = 1;
            }
        }

        if (status == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<Integer> createList(ArrayList<Integer> arrayList) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
            result.add(arrayList.get(i));
        }

        return result;
    }

    //PMX Crossover operator
    public static ArrayList<ArrayList<Integer>> PMXCrossover(ArrayList<ArrayList<Integer>> arrayLists) {
        ArrayList<ArrayList<Integer>> resultLists = arrayLists;
        int flag = 0;
        int tempValue;

        ArrayList<Integer> fatherList = createList(arrayLists.get(0));
        ArrayList<Integer> motherList = createList(arrayLists.get(1));

        ArrayList<Integer> child1;
        ArrayList<Integer> child2;

        int randomPosition1 = findRandomPosition(fatherList.size());
        int randomPosition2 = findRandomPosition(fatherList.size());

        //they can not be in same position
        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
                if (randomPosition1 > randomPosition2) {
                    tempValue = randomPosition1;
                    randomPosition1 = randomPosition2;
                    randomPosition2 = tempValue;
                }
            } else {
                randomPosition2 = findRandomPosition(fatherList.size());
            }
        }

        //get the chang list
        ArrayList<ArrayList<Integer>> changeList = buildChangeList(fatherList, motherList, randomPosition1, randomPosition2);
//        System.out.println(changeList);

        child1 = PMXOperator(randomPosition1, randomPosition2, fatherList, motherList, changeList);
//        System.out.println(child1);

        child2 = PMXOperator(randomPosition1, randomPosition2, motherList, fatherList, changeList);
//        System.out.println(child2);

        resultLists.add(child1);
        resultLists.add(child2);

        return resultLists;
    }

    public static ArrayList<ArrayList<Integer>> buildChangeList(ArrayList<Integer> parentList1, ArrayList<Integer> parentList2, int position1, int position2) {
        ArrayList<ArrayList<Integer>> pairList = new ArrayList<ArrayList<Integer>>();

        for (int i = position1; i <= position2; i++) {
            ArrayList<Integer> tempList = new ArrayList<>();
            tempList.add(parentList1.get(i));
            tempList.add(parentList2.get(i));
            pairList.add(tempList);
        }

        return pairList;
    }

    public static ArrayList<Integer> PMXOperator(int position1, int position2, ArrayList<Integer> arrayList, ArrayList<Integer> arrayList1, ArrayList<ArrayList<Integer>> changeList) {
        ArrayList<Integer> parentList1 = createList(arrayList);
        ArrayList<Integer> parentList2 = createList(arrayList1);
        ArrayList<ArrayList<Integer>> copyChangeList = createDoubleList(changeList);
        ArrayList<Integer> tempList = new ArrayList<>();

        for (int i = position1; i <= position2; i++) {
            tempList.add(parentList1.get(i));
        }

        for (int i = position1; i <= position2; i++) {
            parentList2.set(i, parentList1.get(i));
        }
//
//        System.out.println("Now It is " + parentList2);

        parentList2 = changeNum(position1, position2, parentList2, tempList, copyChangeList);

        return parentList2;
    }

    public static ArrayList<Integer> changeNum(int position1, int position2, ArrayList<Integer> parrntList, ArrayList<Integer> tempList, ArrayList<ArrayList<Integer>> changeList) {

//        System.out.println(changeList);
//        System.out.println(tempList);
        int flag = 0;
        int status = 0;

        for (int i = position2 + 1; i != position1; i++) {
            if (i == parrntList.size()) {
                i = -1;
            } else {
                if (!(compareNum(parrntList.get(i), tempList))) {
                    while (status == 0) {

                        if (changeList.get(flag).get(1) == parrntList.get(i)) {
                            parrntList.set(i, changeList.get(flag).get(0));
                        } else if (changeList.get(flag).get(0) == parrntList.get(i)) {
                            parrntList.set(i, changeList.get(flag).get(1));
                        }
                        if (compareNum(parrntList.get(i), tempList)) {
                            flag = 0;
                            break;
                        }
                        if (flag == changeList.size()) {
                            flag = -1;
                        }
                        flag++;
                        if (flag == changeList.size()) {
                            flag = 0;
                        }
                    }
                    status = 0;
                }
            }
        }

        return parrntList;
    }

    public static ArrayList<ArrayList<Integer>> createDoubleList(ArrayList<ArrayList<Integer>> arrayLists) {
        ArrayList<ArrayList<Integer>> resultLists = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < arrayLists.size(); i++) {
            ArrayList<Integer> tempList = new ArrayList<>();
            for (int j = 0; j < arrayLists.get(i).size(); j++) {
                tempList.add(arrayLists.get(i).get(j));
            }
            resultLists.add(tempList);
        }
        return resultLists;
    }

    //Cycle Crossover Operator
    public static ArrayList<ArrayList<Integer>> cycleCrossover(ArrayList<ArrayList<Integer>> arrayLists) {
        int flag = 1;
        int position = 0;
        ArrayList<Integer> fatherList = createList(arrayLists.get(0));
        ArrayList<Integer> motherList = createList(arrayLists.get(1));
        ArrayList<Integer> tempList = new ArrayList<>();

        ArrayList<Integer> child1List = createList(fatherList);
        ArrayList<Integer> child2List = createList(motherList);

        ArrayList<ArrayList<Integer>> resultLists = new ArrayList<ArrayList<Integer>>();
        tempList.add(fatherList.get(position));

        while (flag > 0) {
            if (flag == 1) {
                tempList.add(motherList.get(position));
            }

            if (checkList(tempList)) {
                tempList.remove(tempList.size() - 1);
                break;
            }

            for (int i = 0; i < fatherList.size(); i++) {
                if (tempList.get(tempList.size() - 1) == fatherList.get(i)) {
                    position = i;
                }
            }
        }

//        System.out.println("Key list : " + tempList);

        for (int i = 0; i < fatherList.size(); i++) {
            if (!(checkNum(fatherList.get(i), tempList))) {
                child1List.set(i, fatherList.get(i));
            }
        }


        for (int i = 0; i < motherList.size(); i++) {
            if (!(checkNum(motherList.get(i), tempList))) {
                child1List.set(i, motherList.get(i));
            }
        }

//        System.out.println(child1List);
//        System.out.println(child2List);

        resultLists.add(child1List);
        resultLists.add(child2List);

        return resultLists;
    }

    public static boolean checkNum(int i, ArrayList<Integer> arrayList) {
        for (int j = 0; j < arrayList.size(); j++) {
            if (i == arrayList.get(j)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkList(ArrayList<Integer> tempList) {
        for (int i = 0; i < tempList.size() - 1; i++) {
            if (tempList.get(tempList.size() - 1) == tempList.get(i)) {
                return true;
            }
        }

        return false;
    }

    //Edge recombination operator
    public static ArrayList<Integer> edgeRecombination(ArrayList<ArrayList<Integer>> arrayLists) {

        int status = 0;


        ArrayList<Integer> fatherList = createList(arrayLists.get(0));
        ArrayList<Integer> motherList = createList(arrayLists.get(1));
        ArrayList<Integer> tempList = new ArrayList<>();

        int randomPosition = findRandomPosition(fatherList.size()); // 生成随机数 便于第一个找位置

        ArrayList<Integer> childList = new ArrayList<>();

        ArrayList<Integer> leftList = new ArrayList<>();
        ArrayList<ArrayList<Integer>> rightList = new ArrayList<>();

        for (int i = 0; i < fatherList.size(); i++) {
            leftList.add(fatherList.get(i));
        }

        for (int i = 0; i < leftList.size(); i++) {
            rightList.add(findNightbourHoods(fatherList, motherList, leftList.get(i)));
        }

//        System.out.println("右边  " + rightList);
//        System.out.println("左边  " + leftList);

        //Random select
        randomPosition = 0;
        childList.add(leftList.get(randomPosition));
        removeFromList(rightList, leftList.get(randomPosition));

        while (status == 0) {

            tempList = createList(rightList.get(randomPosition));
            //First choose the common one
            //Second choose the shortest one
            //Otherwise random choose

            int commonPosition = common(tempList, leftList);
            int shortestPosition = shortest(tempList, rightList, leftList);

            if (commonPosition != -1) {
                //common return position
                childList.add(leftList.get(commonPosition));
                removeFromList(rightList, leftList.get(commonPosition));
                randomPosition = commonPosition;
            } else if (shortestPosition != -1) {
                //shortest return position
                childList.add(leftList.get(shortestPosition));
                removeFromList(rightList, leftList.get(shortestPosition));
                randomPosition = shortestPosition;
            } else {
                //random choose one of the number in the list
                childList.add(leftList.get(tempList.get(0)));
                removeFromList(rightList, leftList.get(tempList.get(0)));
                randomPosition = tempList.get(0);
            }

            //delete the common elements in the list
            //If all of them have been finished, the loop will be stop
            if (leftList.size() == childList.size()) {
                status = 1;
            }
        }

//        System.out.println(childList);

        return childList;
    }

    public static int shortest(ArrayList<Integer> tempList, ArrayList<ArrayList<Integer>> righList, ArrayList<Integer> leftList) {    //查找出现长度最短的链
        int length = leftList.size();
        int position = -1;
        for (int i = 0; i < tempList.size(); i++) {
            for (int j = 0; j < leftList.size(); j++) {
                if (leftList.get(j) == tempList.get(i)) {
                    position = j;
                }
            }
            if (realSize(righList.get(position)) < length) {
                length = realSize(righList.get(position));
            }
        }

        return position;
    }

    public static int realSize(ArrayList<Integer> oneRightList) {
        int length = oneRightList.size();

        for (int i = 0; i < oneRightList.size(); i++) {
            for (int j = i + 1; j < oneRightList.size(); j++) {
                if (oneRightList.get(i) == oneRightList.get(j)) {
                    length = length - 1;
                }
            }
        }

        return length;
    }

    public static int common(ArrayList<Integer> tempList, ArrayList<Integer> leftList) { //查找出现两次的元素 有的话 返回一个数 没有返回 负数

        int position = -1;
        int tempValue = -1;

        for (int i = 0; i < tempList.size(); i++) {
            for (int j = i; j < tempList.size(); j++) {
                if (tempList.get(i) == tempList.get(j)) {
                    tempValue = tempList.get(i);
                }
            }
        }

        if (tempValue != -1) {
            for (int i = 0; i < leftList.size(); i++) {
                if (tempValue == leftList.get(i)) {
                    position = i;
                }
            }
        }
        return position;
    }

    public static ArrayList<Integer> findNightbourHoods(ArrayList<Integer> fatherList, ArrayList<Integer> motherList, int value) {
        ArrayList<Integer> result = new ArrayList<>();

        for (int i = 0; i < fatherList.size(); i++) {
            if (value == fatherList.get(i)) {
                if (i == 0) {
                    result.add(fatherList.get(fatherList.size() - 1));
                    result.add(fatherList.get(i + 1));
                } else if (i == fatherList.size() - 1) {
                    result.add(fatherList.get(0));
                    result.add(fatherList.get(i - 1));
                } else {
                    result.add(fatherList.get(i + 1));
                    result.add(fatherList.get(i - 1));
                }
            }
        }

        for (int i = 0; i < motherList.size(); i++) {
            if (value == motherList.get(i)) {
                if (i == 0) {
                    result.add(motherList.get(motherList.size() - 1));
                    result.add(motherList.get(i + 1));
                } else if (i == motherList.size() - 1) {
                    result.add(motherList.get(0));
                    result.add(motherList.get(i - 1));
                } else {
                    result.add(motherList.get(i + 1));
                    result.add(motherList.get(i - 1));
                }
            }
        }

        return result;
    }

    public static void removeFromList(ArrayList<ArrayList<Integer>> rightList, int i) {
        //Delete the right side of list
        for (int j = 0; j < rightList.size(); j++) {
            for (int k = 0; k < rightList.get(j).size(); k++) {
                if (i == rightList.get(j).get(k)) {
                    rightList.get(j).remove(k);
                    k = 0;
                }
            }
//            System.out.println(rightList);
        }


    }
}

import com.sun.org.apache.xml.internal.utils.res.IntArrayWrapper;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<Integer> arrayList1 = new ArrayList<>();

//        arrayList.add(0);
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        arrayList.add(4);
        arrayList.add(5);
        arrayList.add(6);
        arrayList.add(7);
        arrayList.add(8);
        arrayList.add(9);

        arrayList1.add(9);
        arrayList1.add(3);
        arrayList1.add(7);
        arrayList1.add(8);
        arrayList1.add(2);
        arrayList1.add(6);
        arrayList1.add(5);
        arrayList1.add(1);
        arrayList1.add(4);
//        arrayList1.add(0);

        ArrayList<ArrayList<Integer>> parentList = new ArrayList<>();
        parentList.add(arrayList);
        parentList.add(arrayList1);

//        insertMutation(arrayList);
//        swapMutation(arrayList);
//        inversionMutation(arrayList);
//        orderCrossover(parentList);
//        PMXCrossover(parentList);
//        cycleCrossover(parentList);
        edgeRecombination(parentList);
    }

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

        //不能两个是同一个位置的东西
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

    //寻找一个随机的位置
    public static int findRandomPosition(int i) {
        int position = (int) (Math.random() * i);
        return position;
    }

    /**
     * Author: Jianqi Zeng
     * There are operators about Crossover
     */

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

        //不能两个是同一个位置的东西
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

        System.out.println(child1);
        System.out.println(child2);

        return resultLists; //最后会返回两个子代
    }

    //Order Crossover operator
    public static ArrayList<Integer> switchOperator(int position1, int position2, ArrayList<Integer> parent1List, ArrayList<Integer> parent2List) {
        //确定位置后，按照顺序给父亲链补上其他部分

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

        System.out.println("选中列 " + tempList);
        System.out.println("被选列：" + choiceList);

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

        //不能两个是同一个位置的东西
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
        System.out.println(child1);

        child2 = PMXOperator(randomPosition1, randomPosition2, motherList, fatherList, changeList);
        System.out.println(child2);

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

        System.out.println("Key list : " + tempList);

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

        System.out.println(child1List);
        System.out.println(child2List);

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

        //第一个随便选择
        randomPosition = 0;
        childList.add(leftList.get(randomPosition));
        removeFromList(rightList, leftList.get(randomPosition));

        while (status == 0) {

            tempList = createList(rightList.get(randomPosition));
            //首先选择有相同的
            //然后选择最短的
            //实在没有便随便选

            int commonPosition = common(tempList, leftList);
            int shortestPosition = shortest(tempList, rightList, leftList);

            if (commonPosition != -1) {
                //common 会返回position的位置
                childList.add(leftList.get(commonPosition));
                removeFromList(rightList, leftList.get(commonPosition));
                randomPosition = commonPosition;
            } else if (shortestPosition != -1) {
                //shortest 会返回一个position的位置
                childList.add(leftList.get(shortestPosition));
                removeFromList(rightList, leftList.get(shortestPosition));
                randomPosition = shortestPosition;
            } else {
                //从这个tempList的数字随机选择一个
                childList.add(leftList.get(tempList.get(0)));
                removeFromList(rightList, leftList.get(tempList.get(0)));
                randomPosition = tempList.get(0);
            }

            //选择完后要在右边的链都去掉该元素
            //如果全部选完 就跳过
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
                if(tempValue == leftList.get(i)){
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
        //消除右边的链了
        for (int j = 0; j < rightList.size(); j++) {
            for (int k = 0; k < rightList.get(j).size(); k++) {
                if (i == rightList.get(j).get(k)) {
                    rightList.get(j).remove(k);
                    k = 0;
                }
            }
//            System.out.println(rightList);
        }

//        //消除左边的链了
//        for (int j = 0; j < leftList.size(); j++) {
//            if (i == leftList.get(j)) {
//                leftList.remove(j);
//                j = j - 1;
//            }
//        }

    }
}

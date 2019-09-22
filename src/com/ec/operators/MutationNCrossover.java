package com.ec.operators;

import com.ec.Individual;
import com.ec.beans.Coor;

import java.util.ArrayList;
import java.util.Random;

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
    public static ArrayList orderBasedLevel(Individual individual1, Individual individual2) {
        ArrayList<Double> xfatherList = new ArrayList<>();
        ArrayList<Double> xmotherList = new ArrayList<>();

        ArrayList<Double> yfatherList = new ArrayList<>();
        ArrayList<Double> ymotherList = new ArrayList<>();

        for (int i = 0; i < individual1.individualSize(); i++) {
            xfatherList.add(individual1.getCity(i).getCoor().getX());
            yfatherList.add(individual1.getCity(i).getCoor().getY());
            xmotherList.add(individual2.getCity(i).getCoor().getX());
            ymotherList.add(individual2.getCity(i).getCoor().getY());
        }

        ArrayList<ArrayList<Double>> firstIndividual;
        ArrayList<ArrayList<Double>> secondIndividual;

        firstIndividual = orderBasedCrossover(xfatherList, xmotherList);
        secondIndividual = orderBasedCrossover(yfatherList, ymotherList);

        for (int i = 0; i < individual1.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(firstIndividual.get(0).get(i));
            coor.setY(secondIndividual.get(0).get(i));
            individual1.getCity(i).setCoor(coor);
        }

        for (int i = 0; i < individual2.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(firstIndividual.get(1).get(i));
            coor.setY(secondIndividual.get(1).get(i));
            individual2.getCity(i).setCoor(coor);
        }

        ArrayList<Individual> resultInvidual = new ArrayList<>();
        resultInvidual.add(individual1);
        resultInvidual.add(individual2);

        return resultInvidual;
    }

    public static ArrayList<ArrayList<Double>> orderBasedCrossover(ArrayList<Double> fatherList, ArrayList<Double> motherList) {
        ArrayList<ArrayList<Double>> childrenList = new ArrayList<ArrayList<Double>>();

        ArrayList<Double> child1 = generateChild(fatherList, motherList);
        ArrayList<Double> child2 = generateChild(motherList, fatherList);

        childrenList.add(child1);
        childrenList.add(child2);

        System.out.println(childrenList);
        return childrenList;
    }

    public static ArrayList<Double> generateChild(ArrayList<Double> parent1, ArrayList<Double> parent2) {

        ArrayList<Double> copyParent1 = parent1;
        ArrayList<Double> copyParent2 = parent2;
        ArrayList<Double> templist = new ArrayList<>();
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
                    copyParent2.set(i, -1.0);
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


//    public Individual BOPCrossover(Individual parentA, Individual parentB) {
//        double fitnessOfParentA = parentA.getFitness();
//        double fitnessOfParentB = parentB.getFitness();
//        int numberOfCities = parentA.individualSize();
//
//        int crossoverPoint = (int) Math.round(numberOfCities * 0.5);
//        if (fitnessOfParentA + fitnessOfParentB == 0) {
//            System.err.println("Parent A fitness + parent B fitness = 0. Splitting genes 50/50");
//        }
//        else {
//            Random r = new Random();
//            crossoverPoint = (int) Math.round((fitnessOfParentA / (fitnessOfParentA + fitnessOfParentB)) * numberOfCities);
//            crossoverPoint = (int) (crossoverPoint + r.nextGaussian()) % parentA.individualSize();
//            if(crossoverPoint < 0) {
//                crossoverPoint = 0;
//            }
//        }
//
//        ArrayList<Individual> childNodes = new ArrayList<>();
//        int index = 0;
//        while(index < numberOfCities) {
//            childNodes.add(index < crossoverPoint ? new Individual(
//                    parentA.getCity(index).getCoor().getX(),
//                    parentA.getCity(index).getCoor().getY())
//                    : new A1src.Node(index,
//                    parentB.getNodes().get(index).getX(),
//                    parentB.getNodes().get(index).getY()));
//            index++;
//        }
//
//        return new Individual(childNodes);
//    }

    //Position Based Crossover
    public static ArrayList positionBasedLevel(Individual individual1, Individual individual2) {
        ArrayList<Double> xfatherList = new ArrayList<>();
        ArrayList<Double> xmotherList = new ArrayList<>();

        ArrayList<Double> yfatherList = new ArrayList<>();
        ArrayList<Double> ymotherList = new ArrayList<>();

        for (int i = 0; i < individual1.individualSize(); i++) {
            xfatherList.add(individual1.getCity(i).getCoor().getX());
            yfatherList.add(individual1.getCity(i).getCoor().getY());
            xmotherList.add(individual2.getCity(i).getCoor().getX());
            ymotherList.add(individual2.getCity(i).getCoor().getY());
        }

        ArrayList<ArrayList<Double>> firstIndividual;
        ArrayList<ArrayList<Double>> secondIndividual;

        firstIndividual = positionBasedCrossover(xfatherList, xmotherList);
        secondIndividual = positionBasedCrossover(yfatherList, ymotherList);

        for (int i = 0; i < individual1.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(firstIndividual.get(0).get(i));
            coor.setY(secondIndividual.get(0).get(i));
            individual1.getCity(i).setCoor(coor);
        }

        for (int i = 0; i < individual2.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(firstIndividual.get(1).get(i));
            coor.setY(secondIndividual.get(1).get(i));
            individual2.getCity(i).setCoor(coor);
        }

        ArrayList<Individual> resultInvidual = new ArrayList<>();
        resultInvidual.add(individual1);
        resultInvidual.add(individual2);

        return resultInvidual;
    }

    public static ArrayList<ArrayList<Double>> positionBasedCrossover(ArrayList<Double> fatherList, ArrayList<Double> motherList) {
        ArrayList<ArrayList<Double>> childrenList = new ArrayList<ArrayList<Double>>();

        ArrayList<Double> child1 = generatePositionChild(fatherList, motherList);
        ArrayList<Double> child2 = generatePositionChild(motherList, fatherList);

        childrenList.add(child1);
        childrenList.add(child2);

        System.out.println(childrenList);

        return childrenList;
    }

    public static ArrayList<Double> generatePositionChild(ArrayList<Double> parent1, ArrayList<Double> parent2) {
        ArrayList<Double> resultList = new ArrayList<>();
        ArrayList<Double> copyParent1 = parent1;
        ArrayList<Double> copyParent2 = parent2;
        ArrayList<Double> templist = new ArrayList<>();
        for (int i = 0; i < copyParent1.size(); i++) {
            int randomNumber = findRandomPosition(9);
            double possibility = randomNumber * (0.1);
            if (possibility > 0.5) {
                templist.add(copyParent1.get(i));
            } else {
                copyParent1.set(i, -1.0);
            }
        }

        for (int i = 0; i < copyParent2.size(); i++) {
            for (int j = 0; j < templist.size(); j++) {
                if (copyParent2.get(i) == templist.get(j)) {
                    copyParent2.remove(i);
                    i = i - 1;
                }
            }
        }

        int index = 0;

        for (int i = 0; i < copyParent1.size(); i++) {
            if (copyParent1.get(i) == -1) {
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
    public static Individual invidualLevelInversion(Individual individuals) {
        ArrayList<Double> xArrayList = new ArrayList<>();
        ArrayList<Double> yArrayList = new ArrayList<>();
        for (int i = 0; i < individuals.individualSize(); i++) {
            xArrayList.add(individuals.getCity(i).getCoor().getX());
            yArrayList.add(individuals.getCity(i).getCoor().getY());
        }

//        xArrayList = complexInversionMutation(xArrayList);
//        yArrayList = complexInversionMutation(yArrayList);

        for (int i = 0; i < individuals.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(xArrayList.get(i));
            coor.setY(yArrayList.get(i));
            individuals.getCity(i).setCoor(coor);
        }

        return individuals;
    }

    public static ArrayList<ArrayList<Double>> complexInversionMutation(ArrayList<Double> arrayList, ArrayList<Double> arrayList1) {
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList.size());
        ArrayList<ArrayList<Double>> totalResultList = new ArrayList<ArrayList<Double>>();
        ArrayList<Double> resultListX = new ArrayList<>();
        ArrayList<Double> resultListY = new ArrayList<>();
        resultListX = arrayList;
        resultListY = arrayList1;

        int flag = 0;

        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(resultListX.size());
            }
        }

        if (randomPosition1 > randomPosition2) {
            int temp = randomPosition1;
            randomPosition1 = randomPosition2;
            randomPosition2 = temp;
        }


        double avgX = 0.00;
        double sumX = 0.00;

        double avgY = 0.00;
        double sumY = 0.00;

        ArrayList<Double> tempList = new ArrayList<>();
        for (int i = randomPosition1; i <= arrayList.size(); i++) {
            sumY = sumY + resultListY.get(i);
            sumX = sumX + resultListX.get(i);
        }
        avgX = sumX / (arrayList.size() - randomPosition1);
        avgY = sumY / (arrayList.size() - randomPosition1);

        double randomTempValue = new Random().nextInt(6);
        for (int i = randomPosition1; i <= randomPosition2; i++) {
//            tempValue++;
            resultListX.set(i, avgX + randomTempValue);
            resultListY.set(i, avgY + randomTempValue);
        }

        totalResultList.add(resultListX);
        totalResultList.add(resultListY);



        System.out.println(resultListX);

        return totalResultList;
    }

    //Displacement mutation (DM)
    public static Individual displacementLevelInversion(Individual individuals) {
        ArrayList<Double> xArrayList = new ArrayList<>();
        ArrayList<Double> yArrayList = new ArrayList<>();
        for (int i = 0; i < individuals.individualSize(); i++) {
            xArrayList.add(individuals.getCity(i).getCoor().getX());
            yArrayList.add(individuals.getCity(i).getCoor().getY());
        }

        ArrayList<ArrayList<Double>> totalResult = new ArrayList<ArrayList<Double>>();

        totalResult = displacementMutation(xArrayList, yArrayList);

        xArrayList = totalResult.get(0);
        yArrayList = totalResult.get(1);

        for (int i = 0; i < individuals.individualSize(); i++) {
            Coor coor = new Coor();
            coor.setX(xArrayList.get(i));
            coor.setY(yArrayList.get(i));
            individuals.getCity(i).setCoor(coor);
        }

        return individuals;
    }

    public static ArrayList<ArrayList<Double>> displacementMutation(ArrayList<Double> arrayList, ArrayList<Double> arrayList1) {
        int flag = 0;
        int randomPosition1 = findRandomPosition(arrayList.size());
        int randomPosition2 = findRandomPosition(arrayList1.size());
        ArrayList<Double> resultListX = new ArrayList<>();
        ArrayList<Double> resultListY = new ArrayList<>();
        ArrayList<ArrayList<Double>> totalResultList = new ArrayList<ArrayList<Double>>();
        resultListX = arrayList;
        resultListY = arrayList1;

        while (flag == 0) {
            if (randomPosition1 != randomPosition2) {
                flag = 1;
            } else {
                randomPosition2 = findRandomPosition(resultListX.size());
            }
        }
//        System.out.println(randomPosition1 + "  ******  " + randomPosition2);

        //Make sure the randomposition2 is bigger than randomposition1
        if (randomPosition1 > randomPosition2) {
            int temp = randomPosition1;
            randomPosition1 = randomPosition2;
            randomPosition2 = temp;
        }

        double avgX = 0.00;
        double sumX = 0.00;

        double avgY = 0.00;
        double sumY = 0.00;

        ArrayList<Double> tempList = new ArrayList<>();
        for (int i = randomPosition1; i < arrayList.size(); i++) {
            sumY = sumY + resultListY.get(i);
            sumX = sumX + resultListX.get(i);
        }
        avgX = sumX / (arrayList.size() - randomPosition1 + 1);
        avgY = sumY / (arrayList.size() - randomPosition1 + 1);

        double randomTempValueX = randInt(0,6) * 1.0;
        double randomTempValueY = randInt(0,6) * 1.0;
        for (int i = randomPosition1; i <= randomPosition2; i++) {
//            tempValue++;
            resultListX.set(i, avgX + randomTempValueX);
            resultListY.set(i, avgY + randomTempValueY);
        }

        totalResultList.add(resultListX);
        totalResultList.add(resultListY);

        return totalResultList;
    }

    //Scramble mutation
    public static Individual scrambleLevelInversion(Individual individuals) {
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


    public static int randInt(int min, int max) {
        Random random = new Random();

        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }

}

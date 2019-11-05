package com.ec.TSP_Solvers;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class InverOver implements TSP_Solver{

  private static double p = 0.02; // hard-coded for use in assignment 2

  public InverOver() {
  }

  public void setP(double value) {
    p = value;
  }

  public Random randomGenerator = new Random(System.nanoTime());

  @Override
  public HamiltonCycle solve(HamiltonCycle hc) {
    HamiltonCycle result = null;

    // RANDOM INITIALISATION OF POPULATION P
    ArrayList<HamiltonCycle> population = new ArrayList<>();
    int counter = 0;
    while(counter != 10){
      HamiltonCycle temp = hc.clone();
      Collections.shuffle(temp);
      population.add(temp);
      counter++;
    }

    int repeatLimit = 10;
    int repeatCounter = 0;
    int generations = 0;

    while(repeatCounter < repeatLimit) {
      for (int i = 0; i < population.size(); i++) {
        HamiltonCycle Si = population.get(i).clone();
        HamiltonCycle Sp = Si.clone();

        City c = Sp.get(randomGenerator.nextInt(Sp.size()));
        City cprime;

        while (true) { //REPEAT
          if (randomGenerator.nextDouble() <= p) {
            // select from c' from remaining cities in S'
            do {
              cprime = Sp.get(randomGenerator.nextInt(Sp.size()));
            } while (cprime.compareTo(c) == 0);
          } else {

            // Select a random individual from P
            HamiltonCycle randomIndividual = new HamiltonCycle();
            do {
              randomIndividual = population.get(randomGenerator.nextInt(population.size()));
            } while(Si.isIdenticalTo(randomIndividual));

            // Assign c' to the next city from c within randomIndividual
            cprime = randomIndividual.get((randomIndividual.indexOf(c) + 1) % randomIndividual.size());
          }

          // if c' in S' is adjacent to c
          if (checkAdjacent(Sp, c.getNUMBER(), cprime.getNUMBER())) {
            break;  // exit from REPEAT loop
          }

          // inverse section
          invertRegion(Sp, c.getNUMBER(), cprime.getNUMBER());
          c = cprime;
        }

        if (Sp.calculateCost() <= Si.calculateCost()) {
          population.set(i, Sp.clone()); // replace Si in the population with this "better" Sp
        }
      }

      // find best individual in population after conducting inver-over on all individuals
      HamiltonCycle currentBest = null;
      double currentFitness = -1;
      for(HamiltonCycle temp : population) {
        double thisCost = temp.calculateCost();
        if( (thisCost < currentFitness) || (currentFitness == -1) ) {
          currentFitness = thisCost;
          currentBest = temp;
        }
      }

      // CHECK REPEAT of BEST INDIVIDUAL IN POPULATION (termination condition)
      if(currentBest.isIdenticalTo(result)) {
        repeatCounter++;
      } else {
        result = currentBest;
        repeatCounter = 0;
      }

      if(repeatCounter >= repeatLimit) {
        break;
      }
//      System.out.print("{G_" + generations++ + "} \t[" + result.calculateCost() + "] \t"); result.printCycle();
    }
    return result;
  }

  public static void invertRegion(HamiltonCycle HC, int C_number, int Cprime_number) {  // from C to C', excludes C, includes C'

    // find index of city containing C_number
    int posA = (HC.getPosByCityNumber(C_number)+1) % HC.size();
    int posB = HC.getPosByCityNumber(Cprime_number);

    List<City> temp = null;
    if(posA < posB) {
      temp = HC.subList(posA, posB);
    } else {
      temp = HC.subList(posB, posA);
    }
    Collections.reverse(temp);
  }

  public static boolean checkAdjacent(HamiltonCycle Arr, int cityA_number, int cityB_number) {
    int posA = Arr.getPosByCityNumber(cityA_number);
    int posB = Arr.getPosByCityNumber(cityB_number);

    if( (posA + 1) % Arr.size() == posB ) {
      return true;
    }
    if( (posB + 1) % Arr.size() == posA ) {
      return true;
    }
    return false;
  }
}

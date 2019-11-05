package com.ec.EAs;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;

import java.util.Random;

public class Mutator{

  Random RNG;
  public Mutator(){
    RNG = new Random(System.currentTimeMillis());
  }

  public HamiltonCycle TransposeMutator(HamiltonCycle hc , double pickRatio){

    int numbertoTake = (int) Math.floor(pickRatio * hc.size());
    for(int i = 0; i <= numbertoTake; i++){
      City temp = hc.get(RNG.nextInt(hc.size()));
      hc.remove(temp);
      double tempX = temp.getX();
      temp.setX(temp.getY());
      temp.setY(tempX);
      hc.add(temp);
    }
    hc.normaliseCitySpace();
    return hc;
  }

  public HamiltonCycle MedianMutator(HamiltonCycle hc, double pickRatio){
    double medianX = 0.0;
    double medianY = 0.0;

    for(City c : hc){
      medianX += c.getX();
      medianY += c.getY();
    }

    medianX = medianX / (double)hc.size();
    medianY = medianY / (double)hc.size();

    int numbertoTake = (int) Math.floor(pickRatio * hc.size());

    for(int i = 0 ; i <= numbertoTake; i++){
      int t = RNG.nextInt(hc.size());
      City temp = hc.get(t);

      double posX = Math.abs(medianX + temp.getX())/2.0 ;
      double posY = Math.abs(medianY + temp.getY())/2.0;

      City closerCity = new City(temp.getNUMBER(),false, posX, posY);

      if(!hc.containsCityPosition(closerCity)) {
        temp.setY(posY);
      }
    }
    hc.normaliseCitySpace();
    return hc;
  }

  public HamiltonCycle DisperseMutator(HamiltonCycle hc, double pickRatio){

    int numbertoTake = (int) Math.floor(pickRatio * hc.size());

    for(int i = 0; i <= numbertoTake; i++){

      City temp1 = hc.get(RNG.nextInt(hc.size()));
      City temp2 = hc.get(RNG.nextInt(hc.size()));

      hc.remove(temp1);

      double newX = Math.abs(temp1.getX() - temp2.getX());
      double newY = Math.abs(temp1.getY() - temp2.getY());
      temp1.setX(temp2.getX()+newX);
      temp1.setY(temp2.getY()+newY);

      hc.add(temp1);
    }
    hc.normaliseCitySpace();
    return hc;

  }


  /*
   * Picks a random *small* subset (0.02 - 0.05) of the Instance and
   *  Calculates both the local median and global median
   *  moves the points far away from the main group such that the distance of the local median from the global median is 10 times as large
   */
  private double gaussianWeight(double d, double h) {
    return Math.exp(-Math.pow(d,2) / Math.pow(h,2));
  }

  public HamiltonCycle HackneyMutator(HamiltonCycle hc, double pickRatio) {
    int numbertoTake = (int) Math.floor(pickRatio * hc.size());

    // pick a hotspot from one X value and one Y value in the Point Set (
    double hotspotX = hc.get(RNG.nextInt(hc.size())).getX();
    double hotspotY = hc.get(RNG.nextInt(hc.size())).getY();

    // Move every point closer or further from the hotspot, based on weighted distance
    double h = 150;   // gaussian parameter
    for(City C : hc) {
      double cX = C.getX();
      double cY = C.getY();
      double diffX = cX - hotspotX;
      double diffY = cY - hotspotY;
      double d = Math.sqrt(Math.pow(cX - hotspotX, 2) + Math.pow(cY - hotspotY, 2));
      double weightTheta = gaussianWeight(d, h);

      //      if(RNG.nextBoolean()) { //flip coin
      //        weightTheta = -weightTheta; // moving inner or outer
      //      }

      C.setX(cX + (diffX * weightTheta));
      C.setY(cY + (diffY * weightTheta));
    }

    hc.normaliseCitySpace();
    return hc;
  }

}

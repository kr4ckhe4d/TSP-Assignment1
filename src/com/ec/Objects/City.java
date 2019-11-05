package com.ec.Objects;

import java.util.Random;

public class City implements Comparable<City> {
  private double x;
  private double y;
  private int NUMBER=-1; //default

  // CONSTRUCTOR FOR FIXED POSITION
  public City(int number, boolean randomPosition, double x, double y) {     // if randomPosition is TRUE, [x,y] are the upper limits for a randomly chosen position. Otherwise, [x,y] is the assigned position.
    NUMBER = number;
    if(randomPosition) {
      Random RNG = new Random(System.nanoTime());
      setX(RNG.nextDouble() * x);   // java random.nextDouble() returns continuous number in range [0.0 , 1.0]
      setY(RNG.nextDouble() * y);
    } else {
      setX(x);
      setY(y);
    }
  }

    public City() {}

    @Override
    public City clone() {
        return new City(NUMBER, false, getX(), getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getNUMBER() {
        return NUMBER;
    }

    public City setX(double x) {
        this.x = x;
        return this;
    }

    public City setY(double y) {
        this.y = y;
        return this;
    }

    public City setNUMBER(int NUM) {
        this.NUMBER = NUM;
        return this;
    }

    public int compareTo(City other) {
        if (this.NUMBER < other.NUMBER) {
            return -1;
        } else if (this.NUMBER > other.NUMBER) {
            return 1;
        } else {
            return 0;
        }
    }
}

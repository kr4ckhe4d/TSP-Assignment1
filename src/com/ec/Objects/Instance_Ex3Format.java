package com.ec.Objects;
import java.util.ArrayList;

public class Instance_Ex3Format {
    private int Xsize;
    private int Ysize;

    private ArrayList<Integer> gridIndices;
    private ArrayList<Double> xOffsets;
    private ArrayList<Double> yOffsets;

    public Instance_Ex3Format() {
        gridIndices = new ArrayList<>();
        xOffsets = new ArrayList<>();
        yOffsets = new ArrayList<>();
    }

    public Instance_Ex3Format(HamiltonCycle HC) {
        this();

        int index;
        double xoffset;
        double yoffset;

        for(City C : HC) {
            double posX = C.getX();
            double posY = C.getY();
            index = (int) ((20 * Math.floor(posY)) + Math.floor(posX));
            xoffset = (posX % 1);
            yoffset = (posY % 1);

            gridIndices.add(index);
            xOffsets.add(xoffset);
            yOffsets.add(yoffset);
        }
    }


    public Instance_Ex3Format(ArrayList<Integer> indices, ArrayList<Double> offset_x, ArrayList<Double> offset_y) {
        setGridIndices(indices);
        setXOffsets(offset_x);
        setYOffsets(offset_y);
    }

    public ArrayList<Integer> getGridIndices() {
        return gridIndices;
    }

    public ArrayList<Double> getXOffsets() {
        return xOffsets;
    }

    public ArrayList<Double> getYOffsets() {
        return yOffsets;
    }

    public Instance_Ex3Format clone() {
        return new Instance_Ex3Format(gridIndices, xOffsets, yOffsets);
    }


    public void setGridIndices(ArrayList<Integer> input) {
        gridIndices = input;
    }

    public void setGridIndices(boolean[] B) {
        gridIndices = new ArrayList<>();
        for(int i=0; i<B.length; i++) {
            if(B[i]) {
                gridIndices.add(i);
            }
        }
    }

    public void setXOffsets(ArrayList<Double> input) {
        xOffsets = input;
    }

    public void setYOffsets(ArrayList<Double> input) {
        yOffsets = input;
    }

    /* == OUTPUT ROUTINES == */

    public String asString(String separator) {
        String text = "";

        for(Integer index : gridIndices) {
            text += String.valueOf(index) + separator;
        }

        for(int i=0; i<xOffsets.size(); i++) {
            text += String.valueOf(xOffsets.get(i)) + separator;
            text += String.valueOf(yOffsets.get(i)) + separator;
        }
        return text;
    }

}

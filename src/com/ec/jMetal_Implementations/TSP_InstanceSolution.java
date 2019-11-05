package com.ec.jMetal_Implementations;

import com.ec.Objects.City;
import com.ec.Objects.HamiltonCycle;
import com.ec.Objects.Instance_Ex3Format;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.solution.Solution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TSP_InstanceSolution implements DoubleSolution {
    List<Double> positions;
    double objectives[];
    ArrayList<Object> attributes;
    ArrayList<Object> attr_values;
    int citySpaceLimit = 1;

    HamiltonCycle conjoinedHC;

    public TSP_InstanceSolution() {
        positions = new ArrayList<Double>();
        objectives = new double[2];
        attributes = new ArrayList<>();
        attr_values = new ArrayList<>();
    }

    public TSP_InstanceSolution(int N, boolean fixedGrid, int limit) {
        citySpaceLimit = limit;
        HamiltonCycle HC = new HamiltonCycle(N, fixedGrid, citySpaceLimit);
        positions = new ArrayList<Double>(HC.convertCityPos());
        objectives = new double[2];
        attributes = new ArrayList<>();
        attr_values = new ArrayList<>();
    }

    public TSP_InstanceSolution(List<Double> pos, double obj_copy[], int limit) {
        adoptPositions(pos);
        objectives = obj_copy.clone();
        attributes = new ArrayList<>();
        attr_values = new ArrayList<>();
        citySpaceLimit = limit;
    }

    public HamiltonCycle getHCObject() {
        int N = positions.size()/2;
        HamiltonCycle HC = new HamiltonCycle(N, false, citySpaceLimit);
        int i=0;
        for(City C : HC) {
            C.setX(getVariableValue(2*i));
            C.setY(getVariableValue((2*i) +1 ));
            i++;
        }
        HC.renumber();
        return HC;
    }

    public boolean[] getGridCellsList() {
        int N = positions.size()/2;
        boolean[] result = new boolean[citySpaceLimit * citySpaceLimit];
        Arrays.fill(result, Boolean.FALSE);
        for(int i=0; i<positions.size(); i+=2) {
            int idx = (int) (citySpaceLimit*Math.floor(getVariableValue(i+1)) + Math.floor(getVariableValue(i)));
            result[idx] = true;
        }
        return result;
    }

    public int getInnerCitiesNumber() {
        int N = positions.size() / 2;
        double A = Math.floor(Math.sqrt(N));
        double B;
        do {
            B = ((double)N) / A;
            A--;
        } while(B%1 != 0.0);

        return (int) B;
    }

    public Instance_Ex3Format getEx3Format() {
        return getConjoinedHC().getEx3FormatObject();
    }

    public void adoptPositions(List<Double> pos) {
        positions = new ArrayList<Double>(pos);
    }

    public void adoptPositions(boolean[] B) {
        positions = new ArrayList<>();
        for(int i=0; i<B.length; i++) {
            if(B[i]) {
                int posX = i/citySpaceLimit;
                int posY = i%citySpaceLimit;
                positions.add((double) posX);
                positions.add((double) posY);
            }
        }
    }

    public void setConjoinedHC(HamiltonCycle HC) {
        conjoinedHC = HC.clone();
    }

    public HamiltonCycle getConjoinedHC() {
        if(conjoinedHC == null) {
            return getHCObject();
        } else {
            return conjoinedHC;
        }
    }

    @Override
    public Double getLowerBound(int i) {
        return null;
    }

    @Override
    public Double getUpperBound(int i) {
        return null;
    }

    @Override
    public void setObjective(int i, double v) {
        objectives[i] = v;
    }

    @Override
    public double getObjective(int i) {
        return objectives[i];
    }

    @Override
    public Double getVariableValue(int i) { // return x or y value of city C, where 2(C.x) = i or 2(C.y)+1 = i
        return positions.get(i);
    }

    @Override
    public void setVariableValue(int i, Double posValue) {
        positions.set(i, posValue);
    }

    @Override
    public String getVariableValueString(int i) {
        return null;
    }

    @Override
    public int getNumberOfVariables() {
        return positions.size();
    }

    @Override
    public int getNumberOfObjectives() {
        return objectives.length;
    }

    public List<Double> getPositions(){
    	return positions;
    }

    @Override
    public Solution<Double> copy() {    // please don't use
        return new TSP_InstanceSolution(positions,objectives,citySpaceLimit);
    }

    @Override
    public TSP_InstanceSolution clone() {   // use this instead
        return new TSP_InstanceSolution(positions,objectives,citySpaceLimit);
    }

    @Override
    public void setAttribute(Object o, Object o_val) {
        if(attributes.contains(o)) {
            int i = attributes.indexOf(o);
            attr_values.set(i, o_val);
        } else {    // first time setting
            attributes.add(o);
            attr_values.add(o_val);
        }
    }

    @Override
    public Object getAttribute(Object o) {
        int pos = attributes.indexOf(o);
        if(pos == -1) {
            setAttribute(o, 0.0);
            return 0.0;
        } else {
            return attr_values.get(pos);
        }
    }
}

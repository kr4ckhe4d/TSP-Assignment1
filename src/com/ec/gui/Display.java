package com.ec.gui;

import com.ec.Individual;

import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    private Individual individual;

    public Display(Individual individual){
        this.individual = individual;
        initUI();
    }

    private void initUI(){
        setTitle("TSP Problem");
        setSize(900, 900);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void paint(Graphics g){
        draw(g);
    }

    private void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g.create();
        Graphics2D g2d1 = (Graphics2D)g.create();
        for (int i = 0; i < individual.individualSize()-1; i++) {
            g2d.drawLine(individual.getCity(i).getCoor().getX().intValue()*10,individual.getCity(i).getCoor().getY().intValue()*10,individual.getCity(i).getCoor().getX().intValue()*10,individual.getCity(i).getCoor().getY().intValue()*10);
            g2d.setStroke(new BasicStroke(3));
            g2d1.drawLine(individual.getCity(i).getCoor().getX().intValue()*10,individual.getCity(i).getCoor().getY().intValue()*10,individual.getCity(i+1).getCoor().getX().intValue()*10,individual.getCity(i+1).getCoor().getY().intValue()*10);

        }
        g2d1.drawLine(individual.getCity(individual.individualSize()-1).getCoor().getX().intValue()*10,individual.getCity(individual.individualSize()-1).getCoor().getY().intValue()*10,individual.getCity(0).getCoor().getX().intValue()*10,individual.getCity(0).getCoor().getY().intValue()*10);

    }



}

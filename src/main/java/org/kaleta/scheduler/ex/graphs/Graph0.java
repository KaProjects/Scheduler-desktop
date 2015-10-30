package org.kaleta.scheduler.ex.graphs;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 28.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class Graph0 extends JComponent {
    private int numberOfDays;
    private Map<Integer,Integer> incomes;
    private Map<Integer,Integer> costs;
    private int maxValue;

    public Graph0(){
        numberOfDays = 0;
        incomes = null;
        costs = null;
        maxValue = 0;

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.white);
        g.fillRect(0,0,30*numberOfDays+93,647);
        g.setColor(Color.black);
        g.drawLine(70, 25, 70, 625+5);/*y axis*/
        g.drawLine(70,625,70+30*numberOfDays,625);/*x axis*/
        /*axes values*/
        for (int d=1;d<=numberOfDays;d++) {
            String day = String.valueOf(d);
            g.drawString(day,75+30*d - g.getFontMetrics().stringWidth(day),643);
            g.drawLine(70+30*d,620,70+30*d,630);
        }
        for (int v=0;v<=10;v++) {
            String value = String.valueOf(Math.round(v*maxValue/10));
            g.drawString(value,60 - g.getFontMetrics().stringWidth(value),628-60*v);
            g.drawLine(65,625-60*v,75,625-60*v);
        }
        /*costs & incomes functions*/
        int lastIncomesYPoint = 0;
        int lastCostsYPoint = 0;
        for (int d=1;d<=numberOfDays;d++){
            int newIncomesYPoint = Math.round(incomes.get(d)*600f/maxValue);
            int newCostsYPoint = Math.round(costs.get(d)*600f/maxValue);

            g.setColor(Color.green);
            g.drawLine(70+30*(d-1),625 - lastIncomesYPoint,70+30*d,625 - newIncomesYPoint);
            g.setColor(Color.red);
            g.drawLine(70+30*(d-1),625 - lastCostsYPoint,70+30*d,625 - newCostsYPoint);

            lastIncomesYPoint = newIncomesYPoint;
            lastCostsYPoint = newCostsYPoint;
        }
    }

    public void setData(int numberOfDays, List<Item> items){
        Map<Integer,Integer> incomes = new HashMap<Integer, Integer>();
        Map<Integer,Integer> costs = new HashMap<Integer, Integer>();
        for (int i=1;i<=numberOfDays;i++){
            incomes.put(i,0);
            costs.put(i,0);
        }
        for (Item item : items){
            if (item.getIncome()){
                int value = incomes.get(item.getDay());
                value += Integer.valueOf(String.valueOf(item.getAmount()));
                incomes.put(item.getDay(),value);
            }
            if (!item.getIncome()){
                int value = costs.get(item.getDay());
                value += Integer.valueOf(String.valueOf(item.getAmount()));
                costs.put(item.getDay(),value);
            }
        }
        int incomesValue=0;
        int costsValue=0;
        for (int i=1;i<=numberOfDays;i++) {
            incomesValue += incomes.get(i);
            incomes.put(i, incomesValue);
            costsValue += costs.get(i);
            costs.put(i, costsValue);
        }
        int maxValue =0;
        if (incomesValue >= costsValue) {
            maxValue = incomesValue;
        } else {
            maxValue = costsValue;
        }
        int temp = maxValue;
        while (temp >=0){
            temp -= 1000;
        }
        maxValue += -temp;

        this.numberOfDays = numberOfDays;
        this.incomes = incomes;
        this.costs = costs;
        this.maxValue = maxValue;

    }







}

package org.kaleta.scheduler.ex.graphs;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 31.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class Graph1 extends JComponent {
    private int numberOfDays;
    private Map<Integer,Integer> balance;
    private int boundaryValue;

    public Graph1(){
        numberOfDays = 0;
        balance = null;
        boundaryValue = 0;
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
            if (v <= 5){
                if (v == 5) {
                    g.drawLine(65,625-60*v,70+30*numberOfDays,625-60*v);
                    g.drawString("0",60 - g.getFontMetrics().stringWidth("0"),628-60*v);
                } else {
                    g.drawLine(65,625-60*v,75,625-60*v);
                    String value = String.valueOf(Math.round((5-v)*(-boundaryValue)/5));
                    g.drawString(value,60 - g.getFontMetrics().stringWidth(value),628-60*v);
                }
            } else {
                g.drawLine(65,625-60*v,75,625-60*v);
                String value = String.valueOf(Math.round((v-5)* boundaryValue /5));
                g.drawString(value,60 - g.getFontMetrics().stringWidth(value),628-60*v);
            }
        }
        /*balance functions*/
        int lastBalanceYPoint = 300;
        for (int d=1;d<=numberOfDays;d++){
            int newBalanceYPoint = Math.round(300+(balance.get(d))*300f/(boundaryValue));



            g.setColor(Color.blue);
            g.drawLine(70+30*(d-1),625 - lastBalanceYPoint,70+30*d,625 - newBalanceYPoint);
            lastBalanceYPoint = newBalanceYPoint;
        }
    }

    public void setData(int numberOfDays, java.util.List<Item> items){
        Map<Integer,Integer> balance = new HashMap<Integer, Integer>();
        for (int i=1;i<=numberOfDays;i++){
            balance.put(i,0);
        }
        for (Item item : items){
            if (item.getIncome()){
                int value = balance.get(item.getDay());
                value += Integer.valueOf(String.valueOf(item.getAmount()));
                balance.put(item.getDay(),value);
            }
            if (!item.getIncome()){
                int value = balance.get(item.getDay());
                value -= Integer.valueOf(String.valueOf(item.getAmount()));
                balance.put(item.getDay(),value);
            }
        }
        int balanceValue=0;
        for (int i=1;i<=numberOfDays;i++) {
            balanceValue += balance.get(i);
            balance.put(i, balanceValue);
        }
        int maxValue =0;
        int minValue =0;
        for (int d=1;d<=numberOfDays;d++){
            Integer value = balance.get(d);
            if (value > maxValue){
                maxValue = value;
            }
            if ( value < minValue){
                minValue = value;
            }
        }
        int tempMax = maxValue;
        while (tempMax >= 0) {
            tempMax -= 1000;
        }
        maxValue += -tempMax;
        int tempMin = minValue;
        while (tempMin <= 0) {
            tempMin += 1000;
        }
        minValue -= tempMin;
        int edgeValue = 0;
        if (maxValue >= (-minValue)) {
            edgeValue = maxValue;
        } else {
            edgeValue = -minValue;
        }
        this.numberOfDays = numberOfDays;
        this.balance = balance;
        this.boundaryValue = edgeValue;
    }
}

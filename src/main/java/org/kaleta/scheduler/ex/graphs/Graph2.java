package org.kaleta.scheduler.ex.graphs;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.*;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Stanislav Kaleta
 * Date: 30.7.2014
 */
@Deprecated
public class Graph2 extends JComponent {
    private int numberOfDays;
    private Map<Integer,Integer> costs;
    private Map<Integer,Item> first;
    private Map<Integer,Item> second;
    private Map<Integer,Item> third;
    private int maxValueTotal;
    private int maxValueFirstThree;
    private List<String> costTypes;

    public Graph2(){
        numberOfDays = 0;
        costs = null;
        first = null;
        second = null;
        third = null;
        maxValueTotal = 0;
        maxValueFirstThree = 0;
        costTypes = new ArrayList<>();
        getItemTypes();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // not implemented
    }

    public void setData(int numberOfDays, java.util.List<Item> items){
        Map<Integer,Integer> costs = new HashMap<Integer, Integer>();
        for (int i=1;i<=numberOfDays;i++){
            costs.put(i,0);
        }
        for (Item item : items){
            if (!item.getIncome()){
                int value = costs.get(item.getDay());
                value += Integer.valueOf(String.valueOf(item.getAmount()));
                costs.put(item.getDay(),value);
            }
        }
        int costsValue=0;
        for (int i=1;i<=numberOfDays;i++) {
            costsValue += costs.get(i);
            costs.put(i, costsValue);
        }
        int maxValueTotal = costsValue;
        while (costsValue >=0){
            costsValue -= 1000;
        }
        maxValueTotal += -costsValue;

        Map<Integer,Map<String,Integer>> costs2 = new HashMap<Integer, Map<String, Integer>>();
        for (int d = 1; d <= numberOfDays; d++){
            Map<String,Integer> map = new HashMap<String, Integer>();
            for (String str : costTypes){
                map.put(str,0);
            }
            costs2.put(d,map);
        }
        for (int d = 1; d <= numberOfDays; d++) {
            for (String  type : costTypes) {
                for (Item item : items) {
                    if (type.equals(item.getType()) && d == item.getDay()){
                        Map<String,Integer> map = costs2.get(d);
                        Integer value = map.get(type);
                        value += Integer.valueOf(String.valueOf(item.getAmount()));
                        map.put(type,value);
                        costs2.put(d,map);
                    }
                }
            }
        }
//        for (Integer i : costs2.keySet()){
//            System.out.print("day "+String.format("%02d",i)+": ");
//            Map<String,Integer> map = costs2.get(i);
//            for (String str : map.keySet()){
//                Integer value = map.get(str);
//                System.out.print(str+": "+value+" ");
//            }
//            System.out.println();
//        }

        Map<Integer,Item> first = new HashMap<Integer, Item>();
        Map<Integer,Item> second = new HashMap<Integer, Item>();
        Map<Integer,Item> third = new HashMap<Integer, Item>();
        for (int d = 1; d <= numberOfDays; d++) {
            first.put(d, new Item());
            second.put(d, new Item());
            third.put(d, new Item());
        }
        for (Integer day : costs2.keySet()) {
            Map<String, Integer> dayCosts = costs2.get(day);
            for (int i = 1; i <= 3; i++) {
                Item item = new Item();
                item.setAmount(BigDecimal.valueOf(0));
                for (String type : dayCosts.keySet()) {
                    if (dayCosts.get(type) > Integer.valueOf(String.valueOf(item.getAmount()))) {
                        item.setAmount(BigDecimal.valueOf(dayCosts.get(type)));
                        item.setType(type);
                    }
                }
                dayCosts.remove(item.getType());
                if (i == 1) {
                    first.put(day, item);
                }
                if (i == 2) {
                    second.put(day, item);
                }
                if (i == 3) {
                    third.put(day, item);
                }
            }
        }
//        for (int d = 1; d <= numberOfDays; d++) {
//            System.out.print("day "+String.format("%02d",d)+": ");
//            Item item = first.get(d);
//            System.out.print("1st:"+item.getType()+":"+item.getAmount()+" ");
//            item = second.get(d);
//            System.out.print("2nd:"+item.getType()+":"+item.getAmount()+" ");
//            item = third.get(d);
//            System.out.print("3rd:"+item.getType()+":"+item.getAmount()+" ");
//            System.out.println();
//        }
        int maxValueFirstThree = 0;
        for (int d = 1; d <= numberOfDays; d++) {
            if (Integer.valueOf(String.valueOf(first.get(d).getAmount())) > maxValueFirstThree){
                maxValueFirstThree = Integer.valueOf(String.valueOf(first.get(d).getAmount()));
            }
            if (Integer.valueOf(String.valueOf(second.get(d).getAmount())) > maxValueFirstThree){
                maxValueFirstThree = Integer.valueOf(String.valueOf(second.get(d).getAmount()));
            }
            if (Integer.valueOf(String.valueOf(third.get(d).getAmount())) > maxValueFirstThree){
                maxValueFirstThree = Integer.valueOf(String.valueOf(third.get(d).getAmount()));
            }
        }
        int temp = maxValueFirstThree;
        while (temp >=0) {
            temp = temp - 1000;
        }
         maxValueFirstThree += -temp;

        this.numberOfDays = numberOfDays;
        this.costs = costs;
        this.first = first;
        this.second = second;
        this.third = third;
        this.maxValueTotal = maxValueTotal;
        this.maxValueFirstThree = maxValueFirstThree;
    }

    private void getItemTypes() {
        try {
            StringBuilder sb = new StringBuilder(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
            sb.append("resources/");
            File resourcesDir = new File(sb.toString());
            if (!resourcesDir.exists()){
                resourcesDir.mkdir();
                System.out.println("dir created: "+sb.toString());
            }
            sb.append("ItemTypes.txt");
            File resourcesFile = new File(sb.toString());
            if (!resourcesFile.exists()) {
                resourcesFile.createNewFile();
                System.out.println("file created: "+sb.toString());
            }
            BufferedReader br = new BufferedReader(new FileReader(resourcesFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split(";");
                if (splittedLine[0].equals("-")) {
                    costTypes.add(splittedLine[1]);
                }

            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }
}

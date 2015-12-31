package org.kaleta.scheduler.ex.graphs;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Stanislav Kaleta
 * Date: 31.7.2014
 */
@Deprecated
public class Graph3 extends JComponent {
    private Map<String,Integer> totalCostsForTypes;
    private Map<String,Float> dailyCostsForTypes;
    private Integer totalCosts;
    private Float dailyCosts;

    public Graph3(){
        totalCostsForTypes = new HashMap<String, Integer>();
        dailyCostsForTypes = new HashMap<String, Float>();
        totalCosts = 0;
        dailyCosts = 0f;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, 405, 405);
        List<Point2D> endPoints = new ArrayList<Point2D>();
        final int colorIterator = 360/totalCostsForTypes.size();
        double pieProgress = 0;
        int colorProgress = 0;
        for (String type : totalCostsForTypes.keySet()){
            double angle = ((float)totalCostsForTypes.get(type)/totalCosts)*360;
            g2d.setColor(Color.getHSBColor(colorProgress/360f,0.75f,1f));
            colorProgress += colorIterator;
            Arc2D section = new Arc2D.Double(5,5,395,395,pieProgress,angle, Arc2D.PIE);
            g2d.fill(section);
            pieProgress += angle;
            endPoints.add(section.getEndPoint());
        }
        for (Point2D p : endPoints){
            g2d.setColor(Color.black);
            g2d.drawLine(203, 203, (int) p.getX(), (int) p.getY());
            g2d.drawLine(203,203,(int)p.getX()+1,(int)p.getY());
            g2d.drawLine(203,203,(int)p.getX()-1,(int)p.getY());
            g2d.drawLine(203,203,(int)p.getX(),(int)p.getY()+1);
            g2d.drawLine(203,203,(int)p.getX(),(int)p.getY()-1);
        }
        g.setFont(new Font("times new roman",1,20));
        g.drawString("TOTAL COSTS",550 - g.getFontMetrics().stringWidth("TOTAL "),20);
        g.drawString("DAILY",680,20);

        g.drawString("TOTAL:",550 - g.getFontMetrics().stringWidth("TOTAL:"),40);
        g.drawString(String.valueOf(totalCosts),620 - g.getFontMetrics().stringWidth(String.valueOf(totalCosts)),40);
        g.drawString(String.format("%,2.2f",dailyCosts),730 - g.getFontMetrics().stringWidth(String.format("%,2.2f",dailyCosts)),40);
        int yLine = 60;
        for (String type : totalCostsForTypes.keySet()){
            g.drawString(type+":",550 - g.getFontMetrics().stringWidth(type+":"),yLine);
            g.drawString(String.valueOf(totalCostsForTypes.get(type)),620 - g.getFontMetrics().stringWidth(String.valueOf(totalCostsForTypes.get(type))),yLine);
            g.drawString(String.format("%,2.2f",dailyCostsForTypes.get(type)),730 - g.getFontMetrics().stringWidth(String.format("%,2.2f",dailyCostsForTypes.get(type))),yLine);
            yLine += 20;
        }


    }

    public void setData(int numberOfDays, java.util.List<Item> items) {
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
                    totalCostsForTypes.put(splittedLine[1], 0);
                    dailyCostsForTypes.put(splittedLine[1], 0f);
                }
            }
        } catch (IOException ex){
            ex.printStackTrace();
        }
        for (Item item : items){
            if (totalCostsForTypes.containsKey(item.getType()) && dailyCostsForTypes.containsKey(item.getType())) {
                Integer valueTotalForType = totalCostsForTypes.get(item.getType());
                valueTotalForType += Integer.valueOf(String.valueOf(item.getAmount()));
                totalCostsForTypes.put(item.getType(), valueTotalForType);

                Float valueDailyForType = dailyCostsForTypes.get(item.getType());
                valueDailyForType += (float)Integer.valueOf(String.valueOf(item.getAmount()))/numberOfDays;
                dailyCostsForTypes.put(item.getType(), valueDailyForType);

                totalCosts += Integer.valueOf(String.valueOf(item.getAmount()));
                dailyCosts += (float)Integer.valueOf(String.valueOf(item.getAmount()))/numberOfDays;
            }
        }
    }
}

package org.kaleta.scheduler.frontend.month;

import org.kaleta.scheduler.frontend.GuiModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class MonthPanel extends JPanel{

    public MonthPanel(){
        initComponents();

    }

    private void initComponents() {
        this.setLayout(new GridBagLayout());

        for(int y=1;y<=6;y++){
          for(int x =1;x<=7;x++){
              GridBagConstraints c = new GridBagConstraints();
              c.gridx = x;
              c.gridy = y;
              c.gridwidth = 1;
              c.gridheight = 1;
              c.weightx = 0;
              c.weighty = 0;

              DayPreviewPanel dayPreviewPanel = new DayPreviewPanel(new Point(x,y));
              this.add(dayPreviewPanel,c);
          }
        }



    }
}

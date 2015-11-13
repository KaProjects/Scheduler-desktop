package org.kaleta.scheduler.frontend.dialog;

import org.kaleta.scheduler.backend.entity.Month;

import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Set;

/**
 * Created by Stanislav Kaleta on 24.08.2015.
 */
public class PublicHolidaysDialog extends JDialog {
    private boolean result;


    public PublicHolidaysDialog(){
        result = false;
        this.setTitle("Public Free Days");
        this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        initComponents();
        this.setModal(true);
        this.pack();
    }

    private void initComponents() {
        JPanel panelContent = new JPanel();
        panelContent.setLayout(new GridBagLayout());

        for(int y=0;y<=6;y++){
            for(int x =0;x<=7;x++){
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = x;
                c.gridy = y;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.weightx = 0;
                c.weighty = 0;

                if (x==0 && y==0){
                    this.add(new JLabel("week\\day"),c);
                }

                if (x==0 && y!=0){
                    this.add(new JLabel(String.valueOf(y)),c);
                }

                if (x!=0 && y==0){
                    this.add(new JLabel(String.valueOf(y)),c);
                }

                if (x!=0 && y!=0){
                    JCheckBox checkBoxDay = new JCheckBox();


                    this.add(checkBoxDay,c);
                }


            }
        }

    }

    public void setUpDefault(int numberOfDays, int startingDay){

    }

    public void setUpFrom(Month month){

    }

    public Set<Integer> getPublicHolidays(){

        return null;
    }
}

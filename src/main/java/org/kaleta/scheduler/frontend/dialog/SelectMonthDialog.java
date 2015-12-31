package org.kaleta.scheduler.frontend.dialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Stanislav Kaleta on 26.08.2015.
 *
 * Dialog which provides selecting month.
 */
public class SelectMonthDialog extends JDialog {
    private boolean result;

    private JList<String> monthNames;

    public SelectMonthDialog(){
        result = false;
        this.setTitle("Select Month");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        this.setModal(true);
    }

    private void initComponents() {
        monthNames = new JList<>(new DefaultListModel<>());

        JButton buttonSelect = new JButton("Select");
        buttonSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = true;
                SelectMonthDialog.this.dispose();
            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = false;
                SelectMonthDialog.this.dispose();
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addComponent(monthNames)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonSelect, 25, 25, 25)
                        .addComponent(buttonCancel, 25, 25, 25))
                .addGap(10));

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(monthNames,165,165,165)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonSelect, 80, 80, 80)
                                .addGap(5)
                                .addComponent(buttonCancel, 80, 80, 80)))
                .addGap(10));

    }

    public void setMonthNames(String[] monthNames, int actualySelectedMonthIndex){
        this.monthNames.setModel(new AbstractListModel<String>() {
            @Override
            public int getSize() {
                return monthNames.length;
            }

            @Override
            public String getElementAt(int i) {
                return monthNames[i];
            }
        });

        this.monthNames.setSelectedIndex(actualySelectedMonthIndex);
        this.pack();
    }

    public int getSelectedMonthIndex(){
        return monthNames.getSelectedIndex();
    }

    public boolean getResult(){
        return result;
    }
}

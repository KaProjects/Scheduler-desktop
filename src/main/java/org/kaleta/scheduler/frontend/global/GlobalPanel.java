package org.kaleta.scheduler.frontend.global;

import org.kaleta.scheduler.frontend.GuiModel;
import org.kaleta.scheduler.frontend.common.ComponentAction;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class GlobalPanel extends JPanel{
    private GuiModel model;

    private JLabel labelBack;
    private JLabel labelMonth;
    private JLabel labelNext;

    public GlobalPanel(){
        /*TODO this to new panel monthControls */
        //U+25C0 (Black left-pointing triangle ◀)
        //U+25B6 (Black right-pointing triangle ▶)

        labelBack = new JLabel("◀");
        labelBack.setFont(new Font(labelBack.getFont().getName(),Font.PLAIN,20));
        labelBack.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map<Integer, Integer> orders = model.getMonthsOrder();

                int monthId = model.getSelectedMonthId();

                Integer order = orders.get(monthId);

                for (Integer key : orders.keySet()) {
                    if (orders.get(key).equals(order - 1)) {
                        model.selectMonth(key);
                    }
                }


            }
        });
        labelBack.setVisible(false);
        this.add(labelBack);
        labelMonth = new JLabel("-");
        labelMonth.setFont(new Font(labelMonth.getFont().getName(),Font.PLAIN, 20));
        labelMonth.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                SelectMonthDialog dialog = new SelectMonthDialog();

                Map<Integer, Integer> orders = model.getMonthsOrder();
                String[] monthNames = new String[orders.size()];
                Integer[] monthIds = new Integer[orders.size()];
                int actualySelectedMonthIndex = -1;
                int index = 0;
                for (Integer key : orders.keySet()){
                    monthNames[index] = model.getMonthName(key);
                    monthIds[index] = key;

                    if(key == model.getSelectedMonthId()){
                        actualySelectedMonthIndex = index;
                    }

                    index++;
                }

                dialog.setMonthNames(monthNames, actualySelectedMonthIndex);
                dialog.setLocationRelativeTo(labelMonth);
                dialog.setLocation(dialog.getX(),dialog.getY() + dialog.getHeight()/2);
                dialog.setVisible(true);

                if (dialog.getResult()){
                    Integer selectedMonthId = monthIds[dialog.getSelectedMonthIndex()];
                    model.selectMonth(selectedMonthId);
                }
            }
        });
        this.add(labelMonth);
        labelNext = new JLabel("▶");
        labelNext.setFont(new Font(labelNext.getFont().getName(),Font.PLAIN,20));
        labelNext.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map<Integer, Integer> orders = model.getMonthsOrder();

                int monthId = model.getSelectedMonthId();

                Integer order = orders.get(monthId);

                for (Integer key : orders.keySet()) {
                    if (orders.get(key).equals(order + 1)) {
                        model.selectMonth(key);
                    }
                }


            }
        });
        labelNext.setVisible(false);
        this.add(labelNext);


        this.getActionMap().put(GuiModel.MONTH_CHANGED, new ComponentAction() {
            @Override
            protected void actionPerformed(GuiModel guiModel) {
                if (model == null) {
                    model = guiModel;
                }

                Map<Integer, Integer> orders = model.getMonthsOrder();

                int monthId = model.getSelectedMonthId();

                Integer order = orders.get(monthId);

                labelBack.setVisible(false);
                labelNext.setVisible(false);
                for (Integer key : orders.keySet()) {
                    if (orders.get(key) > order) {
                        labelNext.setVisible(true);
                    }
                    if (orders.get(key) < order) {
                        labelBack.setVisible(true);
                    }
                }

                labelMonth.setText(model.getMonthName(monthId));


            }
        });
    }
}

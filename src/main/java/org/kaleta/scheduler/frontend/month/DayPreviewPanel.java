package org.kaleta.scheduler.frontend.month;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.common.ComponentAction;
import org.kaleta.scheduler.frontend.GuiModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class DayPreviewPanel extends JPanel{
    private GuiModel model;
    private int dayNumber;

    private JLabel labelDayNumber;
    private JLabel labelTaskItems;
    private Dimension size;

    public DayPreviewPanel(Point position){
        model = null;
        dayNumber = -1;
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);

        this.add(new JLabel("week " + position.y + " day " + position.x));
        labelDayNumber  = new JLabel("-");
        this.add(labelDayNumber);
        labelTaskItems = new JLabel("-");
        this.add(labelTaskItems);

        size = new Dimension(100,80);

        this.getActionMap().put(GuiModel.MONTH_CHANGED, new ComponentAction() {
            @Override
            protected void actionPerformed(GuiModel guiModel) {
                if (model == null) {
                    model = guiModel;
                }

                Day day = model.getDayAt(position);
                dayNumber = day.getDayNumber();
                if (dayNumber == -1) {
                    for (Component component : DayPreviewPanel.this.getComponents()){
                        component.setVisible(false);
                    }
                    DayPreviewPanel.this.setEnabled(false);
                    DayPreviewPanel.this.setBackground(Color.WHITE);
                    return;
                } else {
                    for (Component component : DayPreviewPanel.this.getComponents()){
                        component.setVisible(true);
                    }
                    DayPreviewPanel.this.setEnabled(true);
                    DayPreviewPanel.this.setBackground(Color.LIGHT_GRAY);
                }

                labelDayNumber.setText(String.valueOf(day.getDayNumber()));

                labelTaskItems.setText("T=" + day.getTasks().size() + " I=" + day.getItems().size());

                if (day.isPublicFreeDay()) {
                    DayPreviewPanel.this.setBorder(BorderFactory.createLineBorder(Color.GREEN));
                }
            }
        });

        this.getActionMap().put(GuiModel.DAY_CHANGED, new ComponentAction() {
            @Override
            protected void actionPerformed(GuiModel guiModel) {
                if (model == null) {
                    model = guiModel;
                }

                if (model.getSelectedDayNumber() == dayNumber) {
                    DayPreviewPanel.this.setBackground(Color.ORANGE);
                } else {
                    if (DayPreviewPanel.this.isEnabled()){
                        DayPreviewPanel.this.setBackground(Color.LIGHT_GRAY);
                    }

                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (DayPreviewPanel.this.isEnabled()){
                 model.selectDay(dayNumber);
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }
}

package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class DayPreviewPanelMonthChanged extends ConfigurationAction {
    private JComponent target;
    private Point position;
    private JLabel labelDayNumber;
    private JProgressBar barIncome;
    private JProgressBar barExpense;
    private String currency;

    public DayPreviewPanelMonthChanged(JComponent target, Point position, JLabel labelDayNumber, JProgressBar barIncome, JProgressBar barExpense){
        super((Configurable) target);
        this.target = target;
        this.position = position;
        this.labelDayNumber = labelDayNumber;
        this.barIncome = barIncome;
        this.barExpense = barExpense;
        currency = Service.configService().getSettings().getCurrency();
    }

    @Override
    protected void actionPerformed() {
        Day day = Service.dayService().getDayAt(position,getConfiguration().getSelectedMonthId());
        int dayNumber = day.getDayNumber();
        if (dayNumber == -1) {
            for (Component component : target.getComponents()){
                component.setVisible(false);
            }
            target.setEnabled(false);
            target.setBackground(Color.WHITE);
            return;
        } else {
            for (Component component : target.getComponents()){
                component.setVisible(true);
            }
            target.setEnabled(true);
            target.setBackground(Color.LIGHT_GRAY);
        }

        labelDayNumber.setText(String.valueOf(day.getDayNumber()));

        if (day.isPublicFreeDay()) {
            target.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        }

        int dailyMaxIncome = Service.itemService().getMaxDailyIncome(getConfiguration().getSelectedMonthId());
        int dailyMaxExpense = Service.itemService().getMaxDailyExpense(getConfiguration().getSelectedMonthId());
        int dayIncome = 0;
        int dayExpense = 0;
        for (Item item : day.getItems()){
            if (item.getIncome()){
                dayIncome += item.getAmount().intValue();
            } else {
                dayExpense += item.getAmount().intValue();
            }
        }
        barIncome.getModel().setMaximum(dailyMaxIncome);
        barIncome.getModel().setValue(dayIncome);
        barIncome.setToolTipText("<html>Income: " + dayIncome + "<br>Daily max.: " + dailyMaxIncome+"</html>");
        barIncome.setString(dayIncome + " " + currency);
        barExpense.getModel().setMaximum(dailyMaxExpense);
        barExpense.getModel().setValue(dayExpense);
        barExpense.setToolTipText("<html>Expense: " + dayExpense + "<br>Daily max.: " + dailyMaxExpense+"</html>");
        barExpense.setString(dayExpense + " " + currency);
    }
}

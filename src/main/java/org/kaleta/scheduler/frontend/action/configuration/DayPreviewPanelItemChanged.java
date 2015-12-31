package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Point;

/**
 * Created by Stanislav Kaleta on 22.12.2015.
 */
public class DayPreviewPanelItemChanged extends ConfigurationAction {
    private Point position;
    private JProgressBar barIncome;
    private JProgressBar barExpense;
    private String currency;

    public DayPreviewPanelItemChanged(Configurable configurable, Point position, JProgressBar barIncome, JProgressBar barExpense) {
        super(configurable);
        this.position = position;
        this.barIncome = barIncome;
        this.barExpense = barExpense;
        currency = Service.configService().getSettings().getCurrency();
    }

    @Override
    protected void actionPerformed() {
        Day day = Service.dayService().getDayAt(position,getConfiguration().getSelectedMonthId());

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

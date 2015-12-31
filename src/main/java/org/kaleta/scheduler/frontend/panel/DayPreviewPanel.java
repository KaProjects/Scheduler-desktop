package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.InitConfigurableAction;
import org.kaleta.scheduler.frontend.action.configuration.DayPreviewPanelDayChanged;
import org.kaleta.scheduler.frontend.action.configuration.DayPreviewPanelItemChanged;
import org.kaleta.scheduler.frontend.action.configuration.DayPreviewPanelMonthChanged;
import org.kaleta.scheduler.frontend.action.mouse.DayPreviewPanelClicked;
import org.kaleta.scheduler.frontend.common.ColorConstants;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class DayPreviewPanel extends JPanel implements Configurable {
    private Configuration configuration;

    public DayPreviewPanel(Point position){
        initComponents(position);
    }

    private void initComponents(Point position){
        this.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridBagLayout());

        JLabel labelDayNumber  = new JLabel("-");
        labelDayNumber.setFont(new Font(labelDayNumber.getName(), Font.BOLD, 25));

        JProgressBar barIncome = new JProgressBar(0,100);
        barIncome.setForeground(ColorConstants.INCOME_GREEN);
        barIncome.setStringPainted(true);
        barIncome.setString("");

        JProgressBar barExpense = new JProgressBar(0,100);
        barExpense.setForeground(ColorConstants.EXPENSE_RED);
        barExpense.setStringPainted(true);
        barExpense.setString("");

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.anchor = GridBagConstraints.PAGE_START;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(labelDayNumber,c);

        c.weighty = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.PAGE_END;
        this.add(barExpense,c);

        c.weighty = 0;
        c.gridy = 3;
        this.add(barIncome,c);

        this.getActionMap().put(Configuration.INIT_CONFIG, new InitConfigurableAction(this));
        this.getActionMap().put(Configuration.MONTH_CHANGED, new DayPreviewPanelMonthChanged(this, position, labelDayNumber, barIncome, barExpense));
        this.getActionMap().put(Configuration.DAY_CHANGED, new DayPreviewPanelDayChanged(this, position));
        this.getActionMap().put(Configuration.ITEM_CHANGED, new DayPreviewPanelItemChanged(this, position, barIncome, barExpense));
        this.addMouseListener(new DayPreviewPanelClicked(this, position));
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}
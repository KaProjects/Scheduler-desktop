package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.InitConfigurableAction;
import org.kaleta.scheduler.frontend.action.configuration.DayPreviewPanelDayChanged;
import org.kaleta.scheduler.frontend.action.configuration.DayPreviewPanelMonthChanged;
import org.kaleta.scheduler.frontend.action.mouse.DayPreviewPanelClicked;

import javax.swing.*;
import java.awt.Color;
import java.awt.Point;

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
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(Color.BLACK);

        // TODO update
        this.add(new JLabel("week " + position.y + " day " + position.x));
        // TODO update
        JLabel labelDayNumber  = new JLabel("-");
        this.add(labelDayNumber);
        // TODO update
        JLabel labelTaskItems = new JLabel("-");
        this.add(labelTaskItems);

        this.getActionMap().put(Configuration.INIT_CONFIG, new InitConfigurableAction(this));
        this.getActionMap().put(Configuration.MONTH_CHANGED, new DayPreviewPanelMonthChanged(this, position, labelDayNumber, labelTaskItems));
        this.getActionMap().put(Configuration.DAY_CHANGED, new DayPreviewPanelDayChanged(this, position));
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

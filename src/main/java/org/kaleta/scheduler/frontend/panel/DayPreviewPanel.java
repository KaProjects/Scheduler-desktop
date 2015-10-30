package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.CommonInitConfigurableAction;
import org.kaleta.scheduler.frontend.action.DayPreviewPanelDayChangedAction;
import org.kaleta.scheduler.frontend.action.DayPreviewPanelMonthChangedAction;
import org.kaleta.scheduler.frontend.action.DayPreviewPanelClicked;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class DayPreviewPanel extends JPanel implements Configurable {
    private Configuration configuration;

    private Dimension size;

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

        size = new Dimension(100,80);

        this.getActionMap().put(Configuration.INIT_CONFIG, new CommonInitConfigurableAction(this));
        this.getActionMap().put(Configuration.MONTH_CHANGED, new DayPreviewPanelMonthChangedAction(this, position, labelDayNumber, labelTaskItems));
        this.getActionMap().put(Configuration.DAY_CHANGED, new DayPreviewPanelDayChangedAction(this, position));
        this.addMouseListener(new DayPreviewPanelClicked(this, position));
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

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}

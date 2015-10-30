package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.CommonInitConfigurableAction;
import org.kaleta.scheduler.frontend.action.GlobalPanelMonthArrowClicked;
import org.kaleta.scheduler.frontend.action.GlobalPanelMonthChangedAction;
import org.kaleta.scheduler.frontend.action.GlobalPanelMonthNameClicked;

import javax.swing.*;
import java.awt.*;

/**
 * TODO month handling maybe to dif. panel
 *
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class GlobalPanel extends JPanel implements Configurable{
    private Configuration configuration;

    public GlobalPanel(){
        initComponents();
    }

    private void initComponents() {
        //U+25C0 (Black left-pointing triangle ◀)
        //U+25B6 (Black right-pointing triangle ▶)

        JLabel labelBack = new JLabel("◀");
        labelBack.setFont(new Font(labelBack.getFont().getName(), Font.PLAIN, 20));
        labelBack.addMouseListener(new GlobalPanelMonthArrowClicked(this, false));
        labelBack.setVisible(false);
        this.add(labelBack);

        JLabel labelMonth = new JLabel("-");
        labelMonth.setFont(new Font(labelMonth.getFont().getName(), Font.PLAIN, 20));
        labelMonth.addMouseListener(new GlobalPanelMonthNameClicked(this, labelMonth));
        this.add(labelMonth);

        JLabel labelNext = new JLabel("▶");
        labelNext.setFont(new Font(labelNext.getFont().getName(),Font.PLAIN,20));
        labelNext.addMouseListener(new GlobalPanelMonthArrowClicked(this, true));
        labelNext.setVisible(false);
        this.add(labelNext);

        this.getActionMap().put(Configuration.INIT_CONFIG, new CommonInitConfigurableAction(this));
        this.getActionMap().put(Configuration.MONTH_CHANGED, new GlobalPanelMonthChangedAction(this,labelBack,labelMonth,labelNext));
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

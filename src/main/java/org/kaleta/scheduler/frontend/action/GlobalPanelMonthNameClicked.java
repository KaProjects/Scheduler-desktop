package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;
import org.kaleta.scheduler.service.MonthService;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * TODO documentation about actionPerformed
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class GlobalPanelMonthNameClicked extends MouseAdapter{
    private Configurable configurable;
    private JComponent invoker;

    public GlobalPanelMonthNameClicked(Configurable configurable, JComponent invoker){
        this.configurable = configurable;
        this.invoker = invoker;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new SwingWorkerHandler() {
            @Override
            protected void runInBackground() {
                SelectMonthDialog dialog = new SelectMonthDialog();

                Service service = new Service();
                Map<Integer, Integer> orders = service.getMonthService().getMonthsOrder();
                String[] monthNames = new String[orders.size()];
                Integer[] monthIds = new Integer[orders.size()];
                int actuallySelectedMonthIndex = -1;
                int index = 0;
                for (Integer key : orders.keySet()) {
                    monthNames[index] = service.getMonthService().getMonthName(key);
                    monthIds[index] = key;

                    if (key == configurable.getConfiguration().getSelectedMonthId()) {
                        actuallySelectedMonthIndex = index;
                    }
                    index++;
                }

                dialog.setMonthNames(monthNames, actuallySelectedMonthIndex);
                dialog.setLocationRelativeTo(invoker);
                dialog.setLocation(dialog.getX(), dialog.getY() + dialog.getHeight() / 2);
                dialog.setVisible(true);

                if (dialog.getResult()) {
                    Integer selectedMonthId = monthIds[dialog.getSelectedMonthIndex()];
                    configurable.getConfiguration().selectMonth(selectedMonthId);
                }
            }
        }.execute();
    }
}

package org.kaleta.scheduler.frontend;

import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.feature.analytics.action.ShowBalanceGraph;
import org.kaleta.scheduler.feature.analytics.action.ShowStructureGraph;
import org.kaleta.scheduler.feature.analytics.dep.itemsLine.ShowItemsLineGraph;
import org.kaleta.scheduler.feature.exporting.ExportItemTypesAction;
import org.kaleta.scheduler.feature.importing.ImportCollectedDataAction;
import org.kaleta.scheduler.feature.importing.ImportOldDataAction;
import org.kaleta.scheduler.frontend.action.menu.*;
import org.kaleta.scheduler.frontend.common.MenuItemWrapper;
import org.kaleta.scheduler.frontend.panel.AccountingPanel;
import org.kaleta.scheduler.frontend.panel.DayPreviewPanel;
import org.kaleta.scheduler.frontend.panel.GlobalPanel;
import org.kaleta.scheduler.frontend.panel.SchedulePanel;
import org.kaleta.scheduler.service.Service;
import org.kaleta.scheduler.service.ServiceFailureException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 *
 * Main frame for this app. Inits major components and handles app. wide configuration.
 */
public class AppFrame extends JFrame implements Configuration {
    private int selectedDay;
    private int selectedMonthId;

    public AppFrame(){
        selectedDay = -1;
        selectedMonthId = -1;
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        initComponents();
        update(Configuration.INIT_CONFIG);
        applySettings();

        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        int centerPosX = (screenSize.width - frameSize.width) / 2;
        int centerPosY = (screenSize.height - frameSize.height) / 2;
        //this.setLocation(centerPosX, centerPosY); // TODO revert before release !!!
    }

    private void initComponents() {
        JTabbedPane dayTabbedPane = new JTabbedPane();
        dayTabbedPane.addTab("Schedule", new SchedulePanel());
        dayTabbedPane.addTab("Accounting", new AccountingPanel());
        /*select Acc. tab, cuz Sch. is not even implemented.*/dayTabbedPane.setSelectedIndex(1);

        GlobalPanel globalPanel = new GlobalPanel();

        JPanel monthPanel = new JPanel();
        monthPanel.setLayout(new GridBagLayout());

        for(int y=0;y<=6;y++) {
            for (int x = 1; x <= 7; x++) {
                GridBagConstraints c = new GridBagConstraints();
                c.gridx = x;
                c.gridy = y;
                c.gridwidth = 1;
                c.gridheight = 1;
                c.weightx = 1;
                c.weighty = 1;
                c.fill = GridBagConstraints.BOTH;

                if (y > 0) {
                    DayPreviewPanel dayPreviewPanel = new DayPreviewPanel(new Point(x, y));
                    monthPanel.add(dayPreviewPanel, c);
                } else {
                    DateFormatSymbols symbols = new SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).getDateFormatSymbols();
                    String[] dayNames = symbols.getWeekdays();
                    JLabel label = new JLabel(dayNames[(x) % 7 + 1]);
                    label.setFont(new Font(label.getFont().getName(), Font.BOLD + Font.ITALIC, label.getFont().getSize() + 2));
                    JPanel panel = new JPanel();
                    panel.setBorder(BorderFactory.createLineBorder(Color.RED));
                    panel.setBackground(Color.WHITE);
                    panel.add(label);
                    c.weightx = 0;
                    c.weighty = 0;
                    monthPanel.add(panel, c);
                }
            }
        }

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(globalPanel)
                        .addComponent(monthPanel))
                .addComponent(dayTabbedPane, 230, 330, Short.MAX_VALUE));

        layout.setVerticalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(globalPanel, 50, 50, 50)
                        .addComponent(monthPanel))
                .addComponent(dayTabbedPane));

        initMenuBar();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        menuBar.add(fileMenu);

        JMenu importMenu = new JMenu("Import");
        importMenu.add(new MenuItemWrapper(new ImportOldDataAction(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK),
                "Import whole month from version 1.x"));
        importMenu.add(new MenuItemWrapper(new ImportCollectedDataAction(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK),
                "Import items collected in android application to existing month."));
        fileMenu.add(importMenu);

        JMenu exportMenu = new JMenu("Export");
        exportMenu.add(new MenuItemWrapper(new ExportItemTypesAction(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK),
                "Export item types to android application."));
        fileMenu.add(exportMenu);

        fileMenu.add(new JSeparator());
        fileMenu.add(new MenuItemWrapper(new OpenSettingsDialog(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.SHIFT_MASK + InputEvent.CTRL_MASK)));
        fileMenu.add(new JSeparator());
        fileMenu.add(new MenuItemWrapper(new PerformExit(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0)));


        JMenu monthMenu = new JMenu("Month");
        monthMenu.setMnemonic(KeyEvent.VK_M);
        menuBar.add(monthMenu);
        monthMenu.add(new MenuItemWrapper(new OpenCreateMonthDialog(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK)));
        monthMenu.add(new MenuItemWrapper(new OpenSelectMonthDialog(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK)));
        monthMenu.add(new MenuItemWrapper(new OpenEditMonthDialog(this),
                KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK),
                "Not implemented yet!"));

        JMenu analyticsMenu = new JMenu("Analytics");
        analyticsMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(analyticsMenu);
        analyticsMenu.add(new MenuItemWrapper(new ShowItemsLineGraph(this), "Deprecated"));
        analyticsMenu.add(new MenuItemWrapper(new ShowBalanceGraph(this)));
        analyticsMenu.add(new MenuItemWrapper(new ShowStructureGraph(this)));

        JMenu helpMenu = new JMenu("Help");
        helpMenu.setMnemonic(KeyEvent.VK_H);
        menuBar.add(helpMenu);
        helpMenu.add(new MenuItemWrapper(new OpenDocumentationDialog(this),
                "Not implemented yet!"));
        helpMenu.add(new JSeparator());
        helpMenu.add(new MenuItemWrapper(new OpenAboutDialog(this)));
    }

    private void updateComponent(JComponent component,int command) {
        Action action = component.getActionMap().get(command);
        if (action != null){
            ActionEvent actionEvent = new ActionEvent(this,0,null);
            action.actionPerformed(actionEvent);
        }

        for (Component child : component.getComponents()) {
            try {
                updateComponent((JComponent) child, command);
            } catch (ClassCastException ex){
                // continue
            }
        }
    }

    @Override
    public void update(int command) {
        for (Component component : this.getComponents()) {
            updateComponent((JComponent) component, command);
        }
    }

    @Override
    public void applySettings(){
        Settings settings = Service.configService().getSettings();

        this.setTitle(Initializer.NAME + "-" + Initializer.VERSION
                + " - user: " + settings.getUserName());

        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (settings.getUiSchemeSelected().equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    SwingUtilities.updateComponentTreeUI(this);
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException
                | UnsupportedLookAndFeelException | IllegalAccessException e) {
            throw new ServiceFailureException(e);
        }

        int settingsSelectedMonthId = settings.getLastMonthSelectedId();
        if (settingsSelectedMonthId != selectedMonthId){
            selectedMonthId = settingsSelectedMonthId;
            update(Configuration.MONTH_CHANGED);
        }

        int settingsSelectedDay = settings.getLastDaySelected();
        if (settingsSelectedDay != selectedDay){
            selectedDay = settingsSelectedDay;
            update(Configuration.DAY_CHANGED);
        }
    }

    @Override
    public int getSelectedMonthId() {
        return selectedMonthId;
    }

    @Override
    public void selectMonth(int monthId) {
        selectedMonthId = monthId;

        Service.configService().monthChanged(monthId);

        update(Configuration.MONTH_CHANGED);

        selectDay(1);
    }

    @Override
    public int getSelectedDayNumber() {
        return selectedDay;
    }

    @Override
    public void selectDay(int dayNumber) {
        selectedDay = dayNumber;

        Service.configService().dayChanged(dayNumber);

        update(Configuration.DAY_CHANGED);
    }
}

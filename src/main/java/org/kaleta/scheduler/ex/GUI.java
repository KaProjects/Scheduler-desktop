package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.manager.ex.MonthManagerEx;
import org.kaleta.scheduler.backend.manager.ex.MonthManagerExImpl;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 10.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class GUI extends javax.swing.JFrame{
    private final  java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("l18n/L18nStrings");
    private final String SOURCE = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
    private final String VERSION = "1.1.1";
    private final String NAME = "Scheduler";
    private JPanel panelTop;
    private PanelWeek panelWeek;
    private JMenuBar menuBar;
    private JMenu menuFile;
    private JMenuItem menuFileItemNewMonth;
    private JMenuItem menuFileItemSelectMonth;
    private JMenuItem menuFileItemImportData;
    private FrameNewMonth frameNewMonth;
    private FrameSelectMonth frameSelectMonth;
    private JMenu menuEdit;
    private JMenu menuStats;
    private JMenuItem menuStatsItemAccounting;
    private JMenu menuHelp;
    private JMenuItem menuHelpItemAbout;
    private JLabel labelMonthName;
    private JSpinner spinnerWeek;
    private JButton buttonAddTask;
    private JButton buttonCreateMonth;
    private JButton buttonSelectMonth;
    private JButton bAcc1;
    private JButton bAcc2;
    private JButton bAcc3;
    private JButton bAcc4;
    private JButton bAcc5;
    private JButton bAcc6;
    private JButton bAcc7;
    private JButton bAccAdd;
    private MonthManagerEx manager;
    private Month workingMonth;
    private FrameAccounting frameAccounting;
    private FrameStats frameStats;
    private FrameImportData frameImportData;
    private JButton buttonImportData;

    public GUI() {
        try {
            initResources();
            initComponents();
            applySettings();   /*own object(thread) for settings*/
        } catch (IOException ex){
            /*TODO log*/
        }

    }
    private void  initResources() throws IOException {
        File databaseDir = new File(SOURCE+"database/");
        if (!databaseDir.exists()){
            databaseDir.mkdir();
            System.out.println("dir created: "+SOURCE+"database/"); /*TODO log*/
        }
        File resourcesDir = new File(SOURCE+"resources/");
        if (!resourcesDir.exists()){
            resourcesDir.mkdir();
            System.out.println("dir created: "+SOURCE+"resources/"); /*TODO log*/
        }
        // init resource ItemTypes if needed
        File resourceItemTypesFile = new File(SOURCE+"resources/ItemTypes.txt");
        if (!resourceItemTypesFile.exists()) {
            resourceItemTypesFile.createNewFile();
            System.out.println("file created: "+SOURCE+"resources/ItemTypes.txt"); /*TODO log*/
        }
        // init resource settings if needed
        File resourceSettingsFile = new File(SOURCE+"resources/Settings.txt");
        if (!resourceSettingsFile.exists()){
            resourceSettingsFile.createNewFile();
            System.out.println("file created: "+SOURCE+"resources/settings.txt"); /*TODO log*/
            PrintWriter writer = new PrintWriter(resourceSettingsFile, "UTF-8");
            writer.println("lastmonth:null:0");
            writer.close();
        }
    }
    private void applySettings() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(SOURCE+"resources/Settings.txt"));
        String line = "";
        while ((line = br.readLine()) != null){
            String[] strings = line.split(":");
            if (strings[0].equals("lastmonth")){
                if (!strings[1].equals("null")){
                    frameSelectMonth.setSelectedMonth(strings[1]);
                    selectChosenMonthActionPerformed(null,Integer.parseInt(strings[2]));/*TODO oddelit tento apply settings od swingworkru v selec...ActionP... vlakna, tuna vsetko nacitat aj za cenu spomalenia pri nacitani app*/
                }                                                                       /*TODO ActionP... bez druheho parametru*/
                                                                                       /*TODO nefunguju nazvy s mekcenami,dlznami atd (zistit,utf8?)*/
            }
            // other settings if tadada
        }
        br.close();
    }
    private void initComponents() {
        panelTop = new JPanel();
        bAcc1 = new JButton();
        bAcc2 = new JButton();
        bAcc3 = new JButton();
        bAcc4 = new JButton();
        bAcc5 = new JButton();
        bAcc6 = new JButton();
        bAcc7 = new JButton();
        bAccAdd = new JButton();
        panelWeek = new PanelWeek(new JButton[]{bAcc1,bAcc2,bAcc3,bAcc4,bAcc5,bAcc6,bAcc7});
        menuBar = new JMenuBar();
        menuFile = new JMenu();
        menuFileItemNewMonth = new JMenuItem();
        menuFileItemSelectMonth = new JMenuItem();
        menuFileItemImportData = new JMenuItem();
        buttonCreateMonth = new JButton();
        buttonSelectMonth = new JButton();
        frameNewMonth = new FrameNewMonth(buttonCreateMonth);
        frameSelectMonth = new FrameSelectMonth(buttonSelectMonth);
        menuEdit = new JMenu();
        menuStats = new JMenu();
        menuStatsItemAccounting = new JMenuItem();
        labelMonthName = new JLabel();
        spinnerWeek = new JSpinner();
        buttonAddTask = new JButton();
        manager = new MonthManagerExImpl(SOURCE+"database/");
        workingMonth = null;
        frameAccounting = new FrameAccounting(bAccAdd);
        frameStats = new FrameStats();
        buttonImportData = new JButton();
        frameImportData = new FrameImportData(buttonImportData,bundle.getString("NOFILESELECTED"),bundle.getString("SELECTFILE"));
        menuHelp = new JMenu();
        menuHelpItemAbout = new JMenuItem();


        /* "main frame" GroupLayout */    /*TODO doladit gapy*/
        GroupLayout mainFrameLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(mainFrameLayout);
        mainFrameLayout.setHorizontalGroup(
            mainFrameLayout.createParallelGroup()
                .addGroup(mainFrameLayout.createSequentialGroup()
                    .addComponent(panelTop,700,GroupLayout.DEFAULT_SIZE,Short.MAX_VALUE))  /*TODO doladit*/
                .addGroup(mainFrameLayout.createSequentialGroup()
                        .addComponent(panelWeek))
        );
        mainFrameLayout.setVerticalGroup(
            mainFrameLayout.createSequentialGroup()
                .addGroup(mainFrameLayout.createParallelGroup()
                    .addComponent(panelTop,GroupLayout.PREFERRED_SIZE,50,GroupLayout.PREFERRED_SIZE))    /*TODO doladit*/
                .addGroup(mainFrameLayout.createParallelGroup()
                        .addComponent(panelWeek))
        );
        /* "main frame" settings */
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(950,500);   /*TODO doladit w,h size*/
        //setMinimumSize(new java.awt.Dimension(950, 500));   /*TODO doladit w,h size*/
        setJMenuBar(menuBar);
        /* "main frame menu" settings */
        menuFile.setText(bundle.getString("FILE"));
        menuFileItemNewMonth.setText(bundle.getString("NEWMONTH"));
        menuFileItemSelectMonth.setText(bundle.getString("SELECTMONTH"));
        menuFileItemImportData.setText(bundle.getString("IMPORTDATA"));
        menuEdit.setText(bundle.getString("EDIT"));
        menuStats.setText(bundle.getString("STATS"));
        menuStatsItemAccounting.setText(bundle.getString("ACCOUNTING"));
        menuHelp.setText(bundle.getString("HELP"));
        menuHelpItemAbout.setText(bundle.getString("ABOUT"));

        menuBar.add(menuFile);
        menuBar.add(menuEdit);
        menuBar.add(menuStats);
        menuBar.add(menuHelp);
        menuFile.add(menuFileItemNewMonth);
        menuFile.add(menuFileItemSelectMonth);
        menuFile.add(new JSeparator());
        menuFile.add(menuFileItemImportData);
        menuFileItemImportData.setEnabled(false);
        menuStats.setEnabled(false);
        menuStats.add(menuStatsItemAccounting);
        menuHelp.add(menuHelpItemAbout);

        menuFileItemNewMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newMonthActionPerformed(e);
            }
        });
        menuFileItemSelectMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectMonthActionPerformed(e);
            }
        });
        menuStatsItemAccounting.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statsAccountingActionPerformed(e);
            }
        });
        menuFileItemImportData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importDataActionPerformed(e);
            }
        });
        menuHelpItemAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAboutActionPerformed(e);
            }
        });

        /* "panelTop" GroupLayout */   /*TODO doladit gapy*/
        GroupLayout panelTopLayout = new GroupLayout(panelTop);
        panelTop.setLayout(panelTopLayout);
        panelTopLayout.setHorizontalGroup(
            panelTopLayout.createSequentialGroup()
                .addGroup(panelTopLayout.createParallelGroup()
                    .addComponent(labelMonthName,GroupLayout.PREFERRED_SIZE,150,GroupLayout.PREFERRED_SIZE)   /*TODO doladit preferred size*/
                    .addComponent(spinnerWeek,GroupLayout.PREFERRED_SIZE,150,GroupLayout.PREFERRED_SIZE))     /*TODO doladit preferred size*/
                .addComponent(buttonAddTask)                                                                  /*TODO doladit preferred size*/


        );
        panelTopLayout.setVerticalGroup(
            panelTopLayout.createParallelGroup()
                .addGroup(panelTopLayout.createSequentialGroup()
                    .addComponent(labelMonthName)
                    .addComponent(spinnerWeek))
                .addComponent(buttonAddTask, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)  /*TODO doladit preferred size*/
        );
        /* "panelTop" settings */
        labelMonthName.setText(bundle.getString("LABELMONTHNONAME"));
        labelMonthName.setBackground(Color.white);
//        labelMonthName.setBorder(BorderFactory.createLineBorder(Color.black,1));
        spinnerWeek.setEnabled(false);
        spinnerWeek.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                spinnerWeekActionPerformed(e);
            }
        });
        buttonAddTask.setText(bundle.getString("ADDTASK"));

        /* "buttonCreateMonth" settings */
        buttonCreateMonth.setText(bundle.getString("CREATE"));
        buttonCreateMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createNewMonthActionPerformed(e);
            }
        });
        /* "buttonSelectMonth" settings */
        buttonSelectMonth.setText(bundle.getString("SELECT"));
        buttonSelectMonth.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectChosenMonthActionPerformed(e,1);
            }
        });
        /* "buttonImportData" settings*/
        buttonImportData.setText(bundle.getString("IMPORT"));
        buttonImportData.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importChosenDataActionPerformed(e);
            }
        });

        /* "buttonAccountings" settings*/
        bAcc1.setText(bundle.getString("ACCOUNTING"));
        bAcc1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,1);
            }
        });
        bAcc2.setText(bundle.getString("ACCOUNTING"));
        bAcc2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,2);
            }
        });
        bAcc3.setText(bundle.getString("ACCOUNTING"));
        bAcc3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,3);
            }
        });
        bAcc4.setText(bundle.getString("ACCOUNTING"));
        bAcc4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,4);
            }
        });
        bAcc5.setText(bundle.getString("ACCOUNTING"));
        bAcc5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,5);
            }
        });
        bAcc6.setText(bundle.getString("ACCOUNTING"));
        bAcc6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,6);
            }
        });
        bAcc7.setText(bundle.getString("ACCOUNTING"));
        bAcc7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAccountingActionPerformed(e,7);
            }
        });
        bAccAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemActionPerformed(e);
            }
        });
        /*"buttonAddTask" settings*/
        buttonAddTask.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTaskActionPerformed(e);
            }
        });
    }

    /* "action performed" methods */
    private void newMonthActionPerformed(ActionEvent e){
        frameNewMonth.setVisible(true);
    }
    private void createNewMonthActionPerformed(ActionEvent e){
        frameNewMonth.setVisible(false);
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                Month monthToCreate = frameNewMonth.getNewMonth();
                manager.createMonth(monthToCreate);
                frameNewMonth.cleanTextFields();
                return null;
            }
            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ignore) {}
                catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Error creating month: " + e.getMessage()); /*TODO log*/
                }
            }
        };
        worker.execute();
    }
    private void selectMonthActionPerformed(ActionEvent e){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                List<Month> tempList = manager.retrieveAllMonths();
                String[] monthNames = new String[tempList.size()];
                for (int i = 0; i < tempList.size(); i++) {
                    monthNames[i] = tempList.get(i).getName();
                }
                frameSelectMonth.setData(monthNames);
                frameSelectMonth.setVisible(true);
                return null;
            }

            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ignore) {
                } catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Error loading months: " + e.getMessage()); /*TODO log*/
                }
            }
        };
        worker.execute();
    }
    private void selectChosenMonthActionPerformed(ActionEvent e, final int chosenWeek){
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                workingMonth = manager.retrieveMonth(frameSelectMonth.getSelectedMonth());
                labelMonthName.setText(workingMonth.getName());
                panelWeek.setNewLayout(workingMonth);
                panelWeek.setWorkingWeek(1);
                spinnerWeek.setModel(new SpinnerListModel(panelWeek.getSpinnerValues()));
                ((JSpinner.DefaultEditor)spinnerWeek.getEditor()).getTextField().setEditable(false);
                spinnerWeek.setEnabled(true);
                menuStats.setEnabled(true);
                menuFileItemImportData.setEnabled(true);
                frameSelectMonth.setVisible(false);
                //save config
                File settingsFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"resources/Settings.txt");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(settingsFile));
                    StringBuilder sb = new StringBuilder();
                    String line = "";
                    while ((line = br.readLine()) != null){
                        if (line.split(":")[0].equals("lastmonth")){
                            sb.append("lastmonth:"+workingMonth.getName()+":1\n");
                        } else {
                            sb.append(line+"\n");
                        }
                    }
                    br.close();
                    PrintWriter writer = new PrintWriter(settingsFile, "UTF-8");
                    writer.println(sb.toString());
                    writer.close();
                } catch (IOException ex) {
                            /*TODO log*/
                }
                spinnerWeek.setValue(String.valueOf(chosenWeek));
                return null;
            }

            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ignore) {
                } catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Error selecting month: " + e.getMessage()); /*TODO log*/
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    private void spinnerWeekActionPerformed(ChangeEvent e){
        panelWeek.setWorkingWeek(Integer.parseInt(spinnerWeek.getValue().toString()));

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {


                File settingsFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "resources/Settings.txt");
                try {
                    BufferedReader br = new BufferedReader(new FileReader(settingsFile));
                    StringBuilder sb = new StringBuilder();
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        if (line.split(":")[0].equals("lastmonth")) {
                            sb.append("lastmonth:" + workingMonth.getName() + ":"+spinnerWeek.getValue().toString()+"\n");
                        } else {
                            sb.append(line + "\n");
                        }
                    }
                    br.close();
                    PrintWriter writer = new PrintWriter(settingsFile, "UTF-8");
                    writer.println(sb.toString());
                    writer.close();
                } catch (IOException ex) {
                            /*TODO log*/
                }
                return null;
            }

            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ignore) {
                } catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Error saving settings: " + e.getMessage()); /*TODO log*/
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
    }
    private void buttonAccountingActionPerformed(ActionEvent e, final int dayInWeek){
        List<Item> itemsForDay = new ArrayList<Item>();
        int dayNumber = panelWeek.getDayNumber(Integer.parseInt(spinnerWeek.getValue().toString()),dayInWeek);
        for (Item item : workingMonth.getItems()){
            if (item.getDay() == dayNumber){
                itemsForDay.add(item);
            }
        }
        frameAccounting.setDayName(dayNumber,dayInWeek,workingMonth.getName());
        frameAccounting.setTableModel(new TableModelAccounting(itemsForDay));
        frameAccounting.setWorkingDay(dayNumber);
        frameAccounting.setVisible(true);

    }
    private void addItemActionPerformed(ActionEvent e){
        Item item = frameAccounting.getCreatedItem();
        int maxId = 0;
        for (Item i : workingMonth.getItems())
        {
            if (i.getId() > maxId) {
                maxId = i.getId();
            }
        }
        item.setId(maxId + 1);
        final Item newItem = item;
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                workingMonth.getItems().add(newItem);
                manager.updateMonth(workingMonth);
                return null;
            }

            @Override
            public void done() {
                try {
                    List<Item> itemsForDay = new ArrayList<Item>();
                    for (Item i : workingMonth.getItems()){
                        if (i.getDay() == newItem.getDay()){
                            itemsForDay.add(i);
                        }
                    }
                    frameAccounting.setTableModel(new TableModelAccounting(itemsForDay));
                    get();
                } catch (InterruptedException ignore) {
                } catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Error creating item: " + e.getMessage()); /*TODO log*/
                }
            }
        };
        worker.execute();
    }
    private void statsAccountingActionPerformed(ActionEvent e){
        frameStats.setData(workingMonth);
        frameStats.setVisible(true);
    }
    private void importDataActionPerformed(ActionEvent e){
        frameImportData.setVisible(true);
    }
    private void importChosenDataActionPerformed(ActionEvent e){
        final List<Item> newItems = frameImportData.getImportedData();

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            public Void doInBackground() {
                int maxId = 0;
                for (Item i : workingMonth.getItems())
                {
                    if (i.getId() > maxId) {
                        maxId = i.getId();
                    }
                }
                for (Item item : newItems){
                    maxId++;
                    item.setId(maxId);
                }
                workingMonth.getItems().addAll(newItems);
                manager.updateMonth(workingMonth);
                frameImportData.setVisible(false);
                return null;
            }

            @Override
            public void done() {
                try {
                    get();
                } catch (InterruptedException ignore) {
                } catch (java.util.concurrent.ExecutionException e) {
                    System.err.println("Error creating imported items: " + e.getMessage()); /*TODO log*/
                }
            }
        };
        worker.execute();
    }

    private void showAboutActionPerformed(ActionEvent e) {
        StringBuilder sb = new StringBuilder();
        sb.append("name: "+NAME+"\n");
        sb.append("version: "+VERSION+"\n\n");
        sb.append("developer: Stanislav Kaleta \n");
        sb.append("contact: kstanleykale@gmail.com \n\n");
        sb.append("testers: Stanislav Kaleta, Ludmila Florekova \n\n");
        sb.append("\u00a9 2014 Stanislav Kaleta All rights reserved");

         JOptionPane.showMessageDialog(this, sb.toString(),"About",JOptionPane.PLAIN_MESSAGE);
    }
    private void addTaskActionPerformed(ActionEvent e){
        /*TODO*/

    }


    public static void main(String args[]){
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
}

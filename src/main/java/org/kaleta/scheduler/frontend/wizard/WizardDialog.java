package org.kaleta.scheduler.frontend.wizard;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by Stanislav Kaleta on 28.07.2015.
 */
public class WizardDialog extends JDialog{
    private boolean result;
    private WizardContentPanel contentPanel;
    private JList<String> wizardStepsList;
    private JButton buttonBack;
    private JButton buttonNext;
    private JButton buttonSave;

    public WizardDialog() {
        result = false;
        initComponents();
        this.setTitle("Initial Settings");
        this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitConfirmationDialog();
            }
        });
        this.setModal(true);
        this.pack();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension dialogSize = this.getSize();
        int centerPosX = (screenSize.width - dialogSize.width) / 2;
        int centerPosY = (screenSize.height - dialogSize.height) / 2;
        this.setLocation(centerPosX, centerPosY);
        wizardStepsList.setSelectedIndex(0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!WizardDialog.this.getResult()) {
                    WizardDialog.this.wizardStepsList.repaint();
                    boolean everythingFilled = true;
                    for (int i = 0; i < wizardStepsList.getModel().getSize(); i++) {
                        everythingFilled = contentPanel.isPanelFilled(i) && everythingFilled;
                    }
                    WizardDialog.this.buttonSave.setEnabled(everythingFilled);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // just continue
                    }
                }
            }
        }, "Wizard-validity-checker").start();
    }

    private void initComponents() {
        contentPanel = new WizardContentPanel();

        wizardStepsList = new JList<>(new AbstractListModel<String>() {
            private final String[] steps =
                    new String[]{"User Preferences", "Item Types", "Task Types", "Global Task Types"};

            @Override
            public int getSize() {
                return steps.length;
            }

            @Override
            public String getElementAt(int index) {
                return steps[index];
            }
        });
        wizardStepsList.setCellRenderer(new WizardListCellRenderer(contentPanel));
        wizardStepsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        wizardStepsList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = wizardStepsList.getSelectedIndex();
                contentPanel.setPanelVisible(selectedIndex);
                if (selectedIndex == 0) {
                    buttonBack.setEnabled(false);
                } else {
                    buttonBack.setEnabled(true);
                }
                if (selectedIndex == wizardStepsList.getModel().getSize() - 1) {
                    buttonNext.setEnabled(false);
                } else {
                    buttonNext.setEnabled(true);
                }
            }
        });

        JButton buttonExit = new JButton("Exit");
        buttonExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitConfirmationDialog();
            }
        });

        buttonSave = new JButton("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean everythingFilled = true;
                for(int i=0;i<wizardStepsList.getModel().getSize();i++){
                    everythingFilled = contentPanel.isPanelFilled(i) && everythingFilled;
                }
                if (everythingFilled){
                    result = true;
                    WizardDialog.this.dispose();
                } else {
                    JOptionPane.showMessageDialog(WizardDialog.this,"One or more cards are not fully filled!",
                            "Save Warning",JOptionPane.WARNING_MESSAGE);
                }

            }
        });
        buttonSave.setEnabled(false);

        buttonBack = new JButton("Back");
        buttonBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wizardStepsList.setSelectedIndex(wizardStepsList.getSelectedIndex() - 1);
            }
        });

        buttonNext = new JButton("Next");
        buttonNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                wizardStepsList.setSelectedIndex(wizardStepsList.getSelectedIndex() + 1);
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(wizardStepsList)
                        .addGap(5)
                        .addComponent(contentPanel)
                        .addGap(10))
                .addGroup(layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(buttonExit, 90, 90, 90)
                        .addGap(5)
                        .addComponent(buttonSave, 90, 90, 90)
                        .addGap(5, 200, Short.MAX_VALUE)
                        .addComponent(buttonBack, 90, 90, 90)
                        .addGap(5)
                        .addComponent(buttonNext, 90, 90, 90)
                        .addGap(10)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(wizardStepsList, 100, 400, Short.MAX_VALUE)
                        .addComponent(contentPanel))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonExit, 30, 30, 30)
                        .addComponent(buttonSave, 30, 30, 30)
                        .addComponent(buttonBack,30,30,30)
                        .addComponent(buttonNext,30,30,30))
                .addGap(10));
    }

    private void exitConfirmationDialog(){
        String ObjButtons[] = {"Yes","No"};
        int PromptResult = JOptionPane.showOptionDialog(WizardDialog.this,
                "Are you sure you want to exit? Application will close!", "Exit Initial Settings",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                ObjButtons,ObjButtons[1]);
        if(PromptResult==0){
            result = false;
            this.dispose();
        }
    }

    public boolean getResult(){
        return result;
    }

    public WizardContentPanel getContentPanel(){
        return contentPanel;
    }
}

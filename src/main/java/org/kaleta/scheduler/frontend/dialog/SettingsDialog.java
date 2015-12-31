package org.kaleta.scheduler.frontend.dialog;

import org.kaleta.scheduler.frontend.wizard.WizardContentPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Stanislav Kaleta on 24.08.2015.
 *
 * Dialog which provides updating settings.
 */
public class SettingsDialog extends JDialog {
    private boolean result;
    private WizardContentPanel contentPanel;
    private JList<String> settingsElementList;

    public SettingsDialog(){
        result = false;
        this.setTitle("Settings");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents();
        this.setModal(true);
        this.pack();
        settingsElementList.setSelectedIndex(0);
    }

    private void initComponents() {
        contentPanel = new WizardContentPanel();

        settingsElementList = new JList<>(new AbstractListModel<String>() {
            private final String[] steps =
                    new String[]{" User Preferences ", " Item Types ", " Task Types ", " Global Task Types "};

            @Override
            public int getSize() {
                return steps.length;
            }

            @Override
            public String getElementAt(int index) {
                return steps[index];
            }
        });
        settingsElementList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        settingsElementList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedIndex = settingsElementList.getSelectedIndex();
                contentPanel.setPanelVisible(selectedIndex);
            }
        });

        JButton buttonSave = new JButton("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = true;
                SettingsDialog.this.dispose();
            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = false;
                SettingsDialog.this.dispose();
            }
        });

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addGap(10)
                        .addComponent(settingsElementList)
                        .addGap(5)
                        .addComponent(contentPanel)
                        .addGap(10))
                .addGroup(layout.createSequentialGroup()
                        .addGap(10, 200, Short.MAX_VALUE)
                        .addComponent(buttonSave, 90, 90, 90)
                        .addGap(5)
                        .addComponent(buttonCancel, 90, 90, 90)
                        .addGap(10)));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(settingsElementList, 100, 200, Short.MAX_VALUE)
                        .addComponent(contentPanel))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonSave, 30, 30, 30)
                        .addComponent(buttonCancel, 30, 30, 30))
                .addGap(10));
    }

    public boolean getResult(){
        return result;
    }

    public WizardContentPanel getContentPanel(){
        return contentPanel;
    }
}

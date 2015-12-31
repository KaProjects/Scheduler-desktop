package org.kaleta.scheduler.frontend.wizard.content;

import javax.swing.*;
import java.awt.Font;

/**
 * Created by Stanislav Kaleta on 01.08.2015.
 */
public class UserSettingsPanel extends JPanel implements WizardPanel {
    private JTextField textFieldName;
    private JComboBox<String> comboBoxUiScheme;
    private JComboBox<String> comboBoxCurrency;
    private JComboBox<String> comboBoxLanguage;

    public UserSettingsPanel(){
        initComponents();
    }

    private void initComponents() {
        Font font = new Font(new JLabel().getFont().getName(), Font.PLAIN, 15);

        JLabel labelName = new JLabel("User Name");
        labelName.setFont(font);
        textFieldName = new JTextField();

        JLabel labelUiScheme = new JLabel("UI Scheme");
        labelUiScheme.setFont(font);
        UIManager.LookAndFeelInfo[] lookAndFeelInfos = UIManager.getInstalledLookAndFeels();
        String [] lookAndFeelNames = new String[lookAndFeelInfos.length];
        for(int i=0; i<lookAndFeelInfos.length;i++){
            lookAndFeelNames[i] = lookAndFeelInfos[i].getName();
        }
        comboBoxUiScheme = new JComboBox<>(lookAndFeelNames);
        comboBoxUiScheme.setSelectedIndex(0);
        comboBoxUiScheme.setEnabled(false);

        JLabel labelCurrency = new JLabel("Currency");
        labelCurrency.setFont(font);
        comboBoxCurrency = new JComboBox<>(new String[]{"EUR", "CZK"});
        comboBoxCurrency.setEditable(true);
        comboBoxCurrency.setSelectedIndex(-1);

        JLabel labelLanguage = new JLabel("Language");
        labelLanguage.setFont(font);
        comboBoxLanguage = new JComboBox<>(new String[]{"ENG"});
        comboBoxLanguage.setEnabled(false);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        int width = 100;
        int height = 25;

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelName, height, height, height)
                        .addComponent(textFieldName, height, height, height))
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelUiScheme, height, height, height)
                        .addComponent(comboBoxUiScheme, height, height, height))
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelCurrency, height, height, height)
                        .addComponent(comboBoxCurrency, height, height, height))
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelLanguage, height, height, height)
                        .addComponent(comboBoxLanguage, height, height, height)));

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelName, width, width, width)
                        .addGap(10)
                        .addComponent(textFieldName, width, width, width))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelUiScheme, width, width, width)
                        .addGap(10)
                        .addComponent(comboBoxUiScheme, width, width, width))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelCurrency, width, width, width)
                        .addGap(10)
                        .addComponent(comboBoxCurrency, width, width, width))
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelLanguage, width, width, width)
                        .addGap(10)
                        .addComponent(comboBoxLanguage, width, width, width)));
    }


     @Override
    public boolean isFilled() {
         boolean userName = !textFieldName.getText().trim().isEmpty();
         boolean uiScheme = comboBoxUiScheme.getSelectedIndex() >= 0;
         boolean currency = (comboBoxCurrency.getSelectedItem() != null)
                 && !((String) comboBoxCurrency.getSelectedItem()).trim().isEmpty();
         boolean language = comboBoxLanguage.getSelectedIndex() >= 0;
        return userName && uiScheme && currency && language;
    }

    public String[] getFilledValues(){
        String[] values = new String[4];
        values[0] = textFieldName.getText();
        values[1] = (String) comboBoxUiScheme.getSelectedItem();
        values[2] = (String) comboBoxCurrency.getSelectedItem();
        values[3] = (String) comboBoxLanguage.getSelectedItem();
        return values;
    }

    public void setFilledValues(String[] values){
        textFieldName.setText(values[0]);
        comboBoxUiScheme.setSelectedItem(values[1]);
        comboBoxCurrency.setSelectedItem(values[2]);
        comboBoxLanguage.setSelectedItem(values[3]);
    }
}

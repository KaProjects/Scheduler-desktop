package org.kaleta.scheduler.frontend.dialog;


import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.UserType;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 14.09.2015.
 *
 * Dialog which provides adding/editing item to/in selected month.
 */
public class AddEditItemDialog extends JDialog {
    private boolean result;
    private List<UserType> types;

    private JToggleButton incomeToggleButton;
    private JToggleButton expenseToggleButton;
    private JTextField textFieldAmount;
    private JComboBox<String> comboBoxType;
    private JComboBox<String> comboBoxDesc;

    private JButton buttonRecentAdds;
    private JButton buttonFinish;

    public AddEditItemDialog(List<UserType> types, List<Item> recentItems){
        this.types = types;
        result = false;
        this.setTitle("Add Item");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        initComponents(recentItems);
        this.setModal(true);
        this.pack();
    }

    private void initComponents(List<Item> recentItems) {
        JLabel labelIncExp = new JLabel();

        UIManager.put("ToggleButton.select", Color.WHITE);

        incomeToggleButton = new JToggleButton("Income");
        incomeToggleButton.setSelected(true);
        SwingUtilities.updateComponentTreeUI(incomeToggleButton);
        incomeToggleButton.setBackground(Color.getHSBColor(240 / 360f, 0.5f, 1f));

        expenseToggleButton = new JToggleButton("Expense");
        expenseToggleButton.setSelected(true);
        SwingUtilities.updateComponentTreeUI(expenseToggleButton);
        expenseToggleButton.setBackground(Color.getHSBColor(240 / 360f, 0.5f, 1f));

        JLabel labelAmount = new JLabel("Amount: ");

        textFieldAmount = new JTextField();
        textFieldAmount.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel labelType = new JLabel("Type: ");

        comboBoxType = new JComboBox<>();
        comboBoxType.setEditable(true);

        JLabel labelDesc = new JLabel("Description: ");

        comboBoxDesc = new JComboBox<>();
        comboBoxDesc.setEditable(true);

        buttonFinish = new JButton("Add");
        buttonFinish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = true;
                dispose();
            }
        });

        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                result = false;
                dispose();
            }
        });

        incomeToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (incomeToggleButton.isSelected()) {
                    incomeToggleButton.setSelected(false);
                } else {
                    expenseToggleButton.setSelected(true);
                    setUpTypes(true);
                }
            }
        });
        expenseToggleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (expenseToggleButton.isSelected()) {
                    expenseToggleButton.setSelected(false);
                } else {
                    incomeToggleButton.setSelected(true);
                    setUpTypes(false);
                }
            }
        });
        comboBoxType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setUpDescriptions();
            }
        });

        JPanel panelRecentAdds = new JPanel();
        panelRecentAdds.setVisible(false);
        panelRecentAdds.setLayout(new BoxLayout(panelRecentAdds, BoxLayout.Y_AXIS));

        buttonRecentAdds = new JButton("Recently Added");
        buttonRecentAdds.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                buttonRecentAdds.setEnabled(false);
                panelRecentAdds.setVisible(true);
                AddEditItemDialog.this.pack();
            }
        });

        for (Item item : recentItems){
            String sign = (item.getIncome()) ? "[+]" : "[-]";
            JLabel labelRecentItem = new JLabel(sign + "[" + item.getAmount().toString() + "]"
                    + " " + item.getType() + " | " + item.getDescription(), JLabel.CENTER);
            JPanel bgForRecentItem = new JPanel();
            bgForRecentItem.add(labelRecentItem);
            bgForRecentItem.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            bgForRecentItem.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (item.getIncome()) {
                        incomeToggleButton.setSelected(false);
                        expenseToggleButton.setSelected(true);
                        setUpTypes(true);
                    } else {
                        incomeToggleButton.setSelected(true);
                        expenseToggleButton.setSelected(false);
                        setUpTypes(false);
                    }
                    textFieldAmount.setText(String.valueOf(item.getAmount()));
                    ((JTextField)comboBoxType.getEditor().getEditorComponent()).setText(item.getType());
                    setUpDescriptions();
                    ((JTextField)comboBoxDesc.getEditor().getEditorComponent()).setText(item.getDescription());

                    buttonRecentAdds.setEnabled(true);
                    panelRecentAdds.setVisible(false);
                    AddEditItemDialog.this.pack();
                }
            });
            panelRecentAdds.add(bgForRecentItem);
        }

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);

        int height = 25;
        int labelWidth = 100;

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelIncExp, labelWidth, labelWidth, labelWidth)
                                .addComponent(incomeToggleButton, 80, 80, 80)
                                .addComponent(expenseToggleButton, 80, 80, 80))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelAmount, labelWidth, labelWidth, labelWidth)
                                .addComponent(textFieldAmount, 80, 80, 80))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelType, labelWidth, labelWidth, labelWidth)
                                .addComponent(comboBoxType, 110, 110, 110))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(labelDesc, labelWidth, labelWidth, labelWidth)
                                .addComponent(comboBoxDesc, 200, 200, 200))
                        .addComponent(buttonRecentAdds, GroupLayout.Alignment.TRAILING)
                        .addComponent(panelRecentAdds)
                        .addGroup(GroupLayout.Alignment.CENTER, layout.createSequentialGroup()
                                .addComponent(buttonFinish, 80, 80, 80)
                                .addGap(5)
                                .addComponent(buttonCancel, 80, 80, 80)))
                .addGap(10));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelIncExp, height, height, height)
                        .addComponent(incomeToggleButton, height, height, height)
                        .addComponent(expenseToggleButton, height, height, height))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelAmount, height, height, height)
                        .addComponent(textFieldAmount, height, height, height))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelType, height, height, height)
                        .addComponent(comboBoxType, height, height, height))
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelDesc, height, height, height)
                        .addComponent(comboBoxDesc, height, height, height))
                .addGap(5)
                .addComponent(buttonRecentAdds, height, height, height)
                .addComponent(panelRecentAdds)
                .addGap(10)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonFinish, height, height, height)
                        .addComponent(buttonCancel, height, height, height))
                .addGap(10));
    }

    private void setUpTypes(boolean sign){
        ((DefaultComboBoxModel) comboBoxType.getModel()).removeAllElements();
        for (UserType type : types) {
            if (type.getSign() == sign) {
                ((DefaultComboBoxModel) comboBoxType.getModel()).addElement(type.getName());
            }
        }
        comboBoxType.setSelectedIndex(-1);
        ((DefaultComboBoxModel) comboBoxDesc.getModel()).removeAllElements();
    }

    private void setUpDescriptions(){
        for (UserType type : types){
            if (type.getName().equals(((JTextField)comboBoxType.getEditor().getEditorComponent()).getText())){
                DefaultComboBoxModel model = (DefaultComboBoxModel) comboBoxDesc.getModel();
                model.removeAllElements();
                for (String desc : type.getPreparedDescriptions()){
                    model.addElement(desc);
                }
                break;
            }
        }
        comboBoxDesc.setSelectedIndex(-1);
    }

    public boolean getResult(){
        return result;
    }

    public Item getCreatedItem(){
        Item item = new Item();
        item.setIncome(expenseToggleButton.isSelected());

        item.setAmount((textFieldAmount.getText().contains(".")) ?
                BigDecimal.valueOf(Double.parseDouble(textFieldAmount.getText())) :
                BigDecimal.valueOf(Integer.parseInt(textFieldAmount.getText())));

        item.setType(((JTextField)comboBoxType.getEditor().getEditorComponent()).getText());

        item.setDescription(((JTextField)comboBoxDesc.getEditor().getEditorComponent()).getText());

        return item;
    }

    public void setItem(Item item){
        buttonRecentAdds.setVisible(false);
        buttonFinish.setText("Edit");

        if (item.getIncome()) {
            incomeToggleButton.setSelected(false);
            expenseToggleButton.setSelected(true);
            setUpTypes(true);
        } else {
            incomeToggleButton.setSelected(true);
            expenseToggleButton.setSelected(false);
            setUpTypes(false);
        }
        textFieldAmount.setText(String.valueOf(item.getAmount()));
        ((JTextField)comboBoxType.getEditor().getEditorComponent()).setText(item.getType());
        setUpDescriptions();
        ((JTextField)comboBoxDesc.getEditor().getEditorComponent()).setText(item.getDescription());
    }
}

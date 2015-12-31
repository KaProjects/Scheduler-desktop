package org.kaleta.scheduler.frontend.wizard.content;

import org.kaleta.scheduler.backend.entity.UserType;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 01.09.2015.
 */
public class TypePanel extends JPanel {
    private String name;
    private List<DescriptionPanel> descriptionPanelList;
    private ColorPickerPanel panelColorPicker;
    private JSpinner spinnerSign;

    private Boolean settableColor;
    private Boolean settableSign;

    private JPanel panelDescriptions;

    public TypePanel(String name, Boolean settableColor, Boolean settableSign){
        this.name = name;
        this.settableColor = settableColor;
        this.settableSign = settableSign;
        descriptionPanelList = new ArrayList<>();
        initComponents();
    }

    public TypePanel(UserType type, Boolean settableColor, Boolean settableSign){
        this.name = type.getName();
        this.settableColor = settableColor;
        this.settableSign = settableSign;
        descriptionPanelList = new ArrayList<>();
        initComponents();
        panelColorPicker.setPredefinedColor(type.getColor());
        spinnerSign.getModel().setValue((type.getSign()) ? "+" : "-");
        for (String description : type.getPreparedDescriptions()){
            DescriptionPanel panelDescription = new DescriptionPanel(description);
            panelDescriptions.add(panelDescription);
            descriptionPanelList.add(panelDescription);
        }
        revalidate();
    }

    private void initComponents() {
        JLabel labelName = new JLabel(name);
        labelName.setFont(new Font(labelName.getName(), Font.BOLD, 12));

        panelColorPicker = new ColorPickerPanel();
        if (!settableColor){
            panelColorPicker.setVisible(false);
        }

        spinnerSign = new JSpinner(new SpinnerListModel(new String[]{"+","-"}));
        ((JSpinner.DefaultEditor) spinnerSign.getEditor()).getTextField().setEditable(false);
        ((JSpinner.DefaultEditor) spinnerSign.getEditor()).getTextField().setHorizontalAlignment(SwingConstants.CENTER);
        if (!settableSign){
            spinnerSign.setVisible(false);
        }

        panelDescriptions = new JPanel();
        panelDescriptions.setLayout(new BoxLayout(panelDescriptions, BoxLayout.X_AXIS));

        JButton buttonAddDescription = new JButton("+");
        buttonAddDescription.setBackground(Color.GREEN);
        buttonAddDescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String description = JOptionPane.showInputDialog(TypePanel.this, "Insert description:");
                if (description != null) {
                    DescriptionPanel panelDescription = new DescriptionPanel(description);
                    TypePanel.this.panelDescriptions.add(panelDescription);
                    descriptionPanelList.add(panelDescription);
                    panelDescriptions.revalidate();
                    TypePanel.this.revalidate();
                }
            }
        });

        JButton buttonDelete = new JButton("X");
        buttonDelete.setBackground(Color.RED);
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int result = JOptionPane.showConfirmDialog(TypePanel.this,
                        "Are you sure?",
                        "Deleting type",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if (result == 0) {
                    TypePanel.this.setVisible(false);
                    TypePanel.this.name = null;
                }
            }
        });

        JScrollPane scrollPaneTypes = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED){
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, getFixedHeight());
            }

            @Override
            public Dimension getMaximumSize() {
                return new Dimension(super.getMaximumSize().width, getFixedHeight());
            }

            @Override
            public Dimension getMinimumSize() {
                return new Dimension(super.getMinimumSize().width, getFixedHeight());
            }

            private int getFixedHeight(){
                int height = 3;
                for (int i=0;i<panelDescriptions.getComponentCount();i++){
                    DescriptionPanel comp = (DescriptionPanel)panelDescriptions.getComponent(i);
                    if (comp.getDescription() != null){
                       height += comp.getHeight();
                       break;
                    }
                }
                if (this.horizontalScrollBar != null && this.horizontalScrollBar.isVisible()){
                    height += this.horizontalScrollBar.getHeight();
                }
                return height;
            }
        };
        scrollPaneTypes.setViewportView(panelDescriptions);


        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(panelColorPicker, 25, 25, 25)
                        .addComponent(spinnerSign, 25, 25, 25)
                        .addComponent(labelName, 25, 25, 25)
                        .addComponent(buttonAddDescription, 25, 25, 25)
                        .addComponent(buttonDelete, 25, 25, 25))
                .addComponent(scrollPaneTypes));
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(panelColorPicker, 25, 25, 25)
                        .addComponent(spinnerSign, 30, 30, 30)
                        .addGap(5)
                        .addComponent(labelName)
                        .addGap(5, 10, Short.MAX_VALUE)
                        .addComponent(buttonAddDescription, 45, 45, 45)
                        .addGap(5)
                        .addComponent(buttonDelete, 45, 45, 45))
                .addComponent(scrollPaneTypes, 100, 140, Short.MAX_VALUE));
    }

    public UserType getType(){
        if (name == null){
            return null;
        }
        UserType type = new UserType();
        type.setName(name);
        type.setColor(panelColorPicker.getPickedColor());
        type.setSign(spinnerSign.getValue().equals("+"));
        for (DescriptionPanel descriptionPanel : descriptionPanelList){
            String description = descriptionPanel.getDescription();
            if (description != null){
                type.getPreparedDescriptions().add(description);
            }
        }
        return type;
    }

}

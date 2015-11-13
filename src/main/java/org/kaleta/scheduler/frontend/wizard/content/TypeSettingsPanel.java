package org.kaleta.scheduler.frontend.wizard.content;

import org.kaleta.scheduler.backend.entity.UserType;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stanislav Kaleta
 * Date: 1.8.2015
 */
public class TypeSettingsPanel extends JPanel implements WizardPanel {
    public static final int TASK = 0;
    public static final int ITEM = 1;
    public static final int GLOBAL_TASK = 2;

    private final int type;
    private List<TypePanel> typePanelList;

    private JPanel panelTypes;

    public TypeSettingsPanel(int type){
        if (type != TASK && type != ITEM && type != GLOBAL_TASK){
            throw new IllegalArgumentException("Wrong type!");
        }
        this.type = type;
        typePanelList = new ArrayList<>();
        initComponents();
    }

    private void initComponents() {
        final String title;
        final Boolean settableColor;
        final Boolean settableSign;
        switch (type) {
            case 0:
                title = "Task types";
                settableColor = Boolean.TRUE;
                settableSign = Boolean.FALSE;
                break;
            case 1:
                title = "Item types";
                settableColor = Boolean.FALSE;
                settableSign = Boolean.TRUE;
                break;
            case 2:
                title = "Global Task types";
                settableColor = Boolean.FALSE;
                settableSign = Boolean.FALSE;
                break;
            default:
                title = null;
                settableColor = null;
                settableSign = null;
        }

        JLabel labelTitle = new JLabel(title);
        labelTitle.setFont(new Font(labelTitle.getName(), Font.BOLD, 15));

        panelTypes = new JPanel();
        panelTypes.setLayout(new BoxLayout(panelTypes, BoxLayout.Y_AXIS));

        JButton buttonAddType = new JButton("+");
        buttonAddType.setBackground(Color.GREEN);
        buttonAddType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String typeName = JOptionPane.showInputDialog(TypeSettingsPanel.this, "Insert type name:");
                if (typeName != null) {
                    TypePanel panelType = new TypePanel(typeName, settableColor,settableSign);
                    TypeSettingsPanel.this.panelTypes.add(panelType);
                    typePanelList.add(panelType);
                    TypeSettingsPanel.this.revalidate();
                }
            }
        });

        JScrollPane scrollPaneTypes = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneTypes.setViewportView(panelTypes);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                        .addComponent(labelTitle, 25, 25, 25)
                        .addComponent(buttonAddType, 25, 25, 25))
                .addComponent(scrollPaneTypes));
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitle)
                        .addGap(5, 10, Short.MAX_VALUE)
                        .addComponent(buttonAddType, 45, 45, 45))
                .addComponent(scrollPaneTypes));
    }

    @Override
    public boolean isFilled() {
        return true;
    }

    public List<UserType> getTypes(){
        List<UserType> types = new ArrayList<>();
        for (TypePanel typePanel : typePanelList){
            UserType type = typePanel.getType();
            if (type != null){
                types.add(type);
            }
        }
        return types;
    }

    public void setTypes(List<UserType> types){
        Boolean settableColor = null;
        Boolean settableSign = null;
        switch (type) {
            case 0:
                settableColor = Boolean.TRUE;
                settableSign = Boolean.FALSE;
                break;
            case 1:
                settableColor = Boolean.FALSE;
                settableSign = Boolean.TRUE;
                break;
            case 2:
                settableColor = Boolean.FALSE;
                settableSign = Boolean.FALSE;
                break;
        }

        for (UserType type : types){
            TypePanel panelType = new TypePanel(type, settableColor, settableSign);
            panelTypes.add(panelType);
            typePanelList.add(panelType);
        }
        revalidate();
    }
}

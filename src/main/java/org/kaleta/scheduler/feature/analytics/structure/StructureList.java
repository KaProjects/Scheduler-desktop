package org.kaleta.scheduler.feature.analytics.structure;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.UserType;
import org.kaleta.scheduler.feature.analytics.SourceOptionsEditor;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 30.09.2016.
 */
public class StructureList extends JPanel {
    private List<UserType> expenseTypes;
    private List<UserType> incomeTypes;

    public StructureList(){
        expenseTypes = Service.itemService().getItemTypes(false);
        incomeTypes = Service.itemService().getItemTypes(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void update(SourceOptionsEditor sourceEditor, StructureViewOptionsEditor viewEditor){
        Map<String, Map<String, Integer>> items = new HashMap<>();
        for (UserType type : viewEditor.isIncome() ? incomeTypes : expenseTypes){
            items.put(type.getName(), new HashMap<>());
        }
        for (Month month : sourceEditor.getSelectedMonths()) {
            for (Item item : month.getItems()) {
                if (item.getIncome().equals(viewEditor.isIncome())) {
                    if (!items.keySet().contains(item.getType())){
                        items.put(item.getType(), new HashMap<>());
                    }
                    if (items.get(item.getType()).keySet().contains(item.getDescription())) {
                        items.get(item.getType()).put(item.getDescription(), items.get(item.getType()).get(item.getDescription()) + item.getAmount().intValue());
                    } else {
                        items.get(item.getType()).put(item.getDescription(), item.getAmount().intValue());
                    }
                }
            }
        }

        int totalSum = 0;
        List<JPanel> typePanels = new ArrayList<>();
        for (String type : items.keySet()){
            int typeSum = 0;
            List<JPanel> descriptionPanels = new ArrayList<>();
            for (String description : items.get(type).keySet()){
                int descriptionSum = items.get(type).get(description);
                if (!description.equals("")){
                    descriptionPanels.add(getRowPanel(description, descriptionSum, 2));
                }
                typeSum += descriptionSum;
            }
            typePanels.add(getRowPanel(type, typeSum, 1));
            typePanels.addAll(descriptionPanels);
            totalSum += typeSum;
        }
        this.removeAll();
        this.add(getRowPanel("Total", totalSum, 0));
        typePanels.forEach(this::add);
        this.repaint();
        this.revalidate();
    }

    private JPanel getRowPanel(String name, int value, int lvl){
        Font font;
        JPanel panel = new JPanel();
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        switch (lvl){
            case 0:
                font = new Font(panel.getFont().getName(), Font.PLAIN, 25);
                panel.setBackground(Color.GRAY);
                panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                break;
            case 1:
                font = new Font(panel.getFont().getName(), Font.PLAIN, 20);
                panel.setBackground(Color.LIGHT_GRAY);
                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                break;
            case 2:
                font = new Font(panel.getFont().getName(), Font.PLAIN, 15);
                panel.setBackground(Color.WHITE);
                break;
            default: throw new IllegalArgumentException(lvl + " is not correct!");
        }

        JLabel labelName = new JLabel(name);
        labelName.setFont(font);
        JLabel labelValue = new JLabel(String.valueOf(value), SwingConstants.RIGHT);
        labelValue.setFont(font);

        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        layout.setVerticalGroup(layout.createParallelGroup().addComponent(labelName).addComponent(labelValue));
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(10*lvl).addComponent(labelName,200,200,200).addComponent(labelValue,100-10*lvl,100-10*lvl,100-10*lvl).addGap(10));

        return panel;
    }

}

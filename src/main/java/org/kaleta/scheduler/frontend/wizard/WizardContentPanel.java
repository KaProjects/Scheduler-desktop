package org.kaleta.scheduler.frontend.wizard;

import org.kaleta.scheduler.backend.entity.UserType;
import org.kaleta.scheduler.frontend.wizard.content.TypeSettingsPanel;
import org.kaleta.scheduler.frontend.wizard.content.UserSettingsPanel;
import org.kaleta.scheduler.frontend.wizard.content.WizardPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stanislav Kaleta
 * Date: 1.8.2015
 */
public class WizardContentPanel extends JPanel {

    private List<WizardPanel> contextPanels;

    public WizardContentPanel(){
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        contextPanels = new ArrayList<>();

        UserSettingsPanel userSettingsPanel = new UserSettingsPanel();
        contextPanels.add(0,userSettingsPanel);
        this.add(userSettingsPanel);

        TypeSettingsPanel itemTypeSettingsPanel = new TypeSettingsPanel(TypeSettingsPanel.ITEM);
        contextPanels.add(1,itemTypeSettingsPanel);
        this.add(itemTypeSettingsPanel);

        TypeSettingsPanel taskTypeSettingsPanel = new TypeSettingsPanel(TypeSettingsPanel.TASK);
        contextPanels.add(2, taskTypeSettingsPanel);
        this.add(taskTypeSettingsPanel);

        TypeSettingsPanel globalTaskTypeSettingsPanel = new TypeSettingsPanel(TypeSettingsPanel.GLOBAL_TASK);
        contextPanels.add(3,globalTaskTypeSettingsPanel);
        this.add(globalTaskTypeSettingsPanel);
    }

    public void setPanelVisible(int panelNumber){
        for (int i=0;i<contextPanels.size();i++){
            if (i == panelNumber){
                contextPanels.get(i).setVisible(true);
            } else {
                contextPanels.get(i).setVisible(false);
            }
        }
    }

    public boolean isPanelFilled(int panelNumber){
        return contextPanels.get(panelNumber).isFilled();
    }

    public String[] getUserSettings(){
        return ((UserSettingsPanel)contextPanels.get(0)).getFilledValues();
    }

    public void setUserSettings(String[] userSettings){
        ((UserSettingsPanel)contextPanels.get(0)).setFilledValues(userSettings);
    }

    public List<UserType> getItemTypes(){
        return ((TypeSettingsPanel)contextPanels.get(1)).getTypes();
    }

    public void setItemTypes(List<UserType> types){
        ((TypeSettingsPanel)contextPanels.get(1)).setTypes(types);
    }

    public List<UserType> getTaskTypes(){
        return ((TypeSettingsPanel)contextPanels.get(2)).getTypes();
    }

    public void setTaskTypes(List<UserType> types){
        ((TypeSettingsPanel)contextPanels.get(2)).setTypes(types);
    }

    public List<UserType> getGlobalTaskTypes(){
        return ((TypeSettingsPanel)contextPanels.get(3)).getTypes();
    }

    public void setGlobalTaskTypes(List<UserType> types){
        ((TypeSettingsPanel)contextPanels.get(3)).setTypes(types);
    }
}

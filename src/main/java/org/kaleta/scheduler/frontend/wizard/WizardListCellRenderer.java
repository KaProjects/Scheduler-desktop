package org.kaleta.scheduler.frontend.wizard;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Author: Stanislav Kaleta
 * Date: 1.8.2015
 */
public class WizardListCellRenderer extends DefaultListCellRenderer {
    private WizardContentPanel contentPanel;
    private boolean isSelected;

        public WizardListCellRenderer(WizardContentPanel contentPanel){
            this.contentPanel = contentPanel;
            isSelected = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (isSelected) {
            g.setColor(Color.DARK_GRAY);
            g.drawRect(0, 0, this.getWidth() - 1, this.getHeight() - 1);
            g.drawRect(1, 1, this.getWidth() - 3, this.getHeight() - 3);
        }
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        this.isSelected = isSelected;

        Color backgroundColor = (contentPanel.isPanelFilled(index))
                ? Color.getHSBColor(120/360f,0.5f,1)
                : Color.getHSBColor(0/360f,0.5f,1);

        super.setText(" " + value + " ");
        super.setFont(new Font(this.getFont().getName(), this.getFont().getStyle(), 15));
        super.setBackground(backgroundColor);
        super.setForeground(Color.DARK_GRAY);

        return this;
    }
}

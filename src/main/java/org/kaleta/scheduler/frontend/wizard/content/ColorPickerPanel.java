package org.kaleta.scheduler.frontend.wizard.content;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 08.09.2015.
 */
public class ColorPickerPanel extends JPanel {
    private Color pickedColor;

    public ColorPickerPanel(){
        pickedColor = Color.LIGHT_GRAY;
        this.setForeground(pickedColor);
        this.setBackground(pickedColor);
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JColorChooser colorChooser = new JColorChooser(pickedColor);
                int result = JOptionPane.showConfirmDialog(ColorPickerPanel.this,
                        colorChooser,
                        "Pick Color for Type",
                        JOptionPane.YES_NO_OPTION);

                if (result == 0){
                    pickedColor = colorChooser.getColor();
                    ColorPickerPanel.this.setForeground(pickedColor);
                    ColorPickerPanel.this.setBackground(pickedColor);
                }
            }
        });
    }

    public Color getPickedColor() {
        return pickedColor;
    }

    public void setPredefinedColor(Color color) {
        pickedColor = color;
        this.setForeground(pickedColor);
        this.setBackground(pickedColor);
    }
}

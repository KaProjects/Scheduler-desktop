package org.kaleta.scheduler.frontend.wizard.content;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 01.09.2015.
 */
public class  DescriptionPanel extends JPanel {
    private String text;

    private Dimension size;

    public DescriptionPanel(String text){
        this.text = text;
        size = new Dimension(20,20);
        initComponents();
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, 10, 10);
    }

    private void initComponents() {
        this.setLayout(new GridLayout(1, 1));

        JLabel labelText = new JLabel(text);
        labelText.setFont(new Font(labelText.getFont().getName(), Font.PLAIN, 12));
        labelText.setHorizontalAlignment(JLabel.CENTER);
        this.add(labelText);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int result = JOptionPane.showConfirmDialog(DescriptionPanel.this,
                            "Are you sure?",
                            "Deleting description",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE);
                    if (result == 0) {
                        DescriptionPanel.this.setVisible(false);
                        DescriptionPanel.this.text = null;
                        DescriptionPanel.this.getParent().getParent().getParent().getParent().revalidate();
                    }
                }
            }
        });

        size.width = 10 + labelText.getFontMetrics(labelText.getFont()).stringWidth(text) + 10;
    }

    @Override
    public Dimension getPreferredSize() {
        return size;
    }

    @Override
    public Dimension getMaximumSize() {
        return size;
    }

    @Override
    public Dimension getMinimumSize() {
        return size;
    }

    public String getDescription(){
        return text;
    }
}

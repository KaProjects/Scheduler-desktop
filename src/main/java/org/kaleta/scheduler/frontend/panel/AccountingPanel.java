package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.InitConfigurableAction;
import org.kaleta.scheduler.frontend.action.configuration.AccountingPanelDayChanged;
import org.kaleta.scheduler.frontend.action.keyboard.AccountingPanelTableDelete;
import org.kaleta.scheduler.frontend.action.mouse.AccountingPanelAddItemClicked;
import org.kaleta.scheduler.frontend.action.mouse.AccountingPanelTableClicked;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class AccountingPanel extends JPanel implements Configurable {
    private Configuration configuration;


    public AccountingPanel(){
        initComponents();
    }

    private void initComponents(){
        JTable tableAccounting = new JTable();
        tableAccounting.setModel(new AccountingPanelTableModel());
        tableAccounting.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableAccounting.setRowSelectionAllowed(true);
        tableAccounting.setColumnSelectionAllowed(false);
        tableAccounting.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                Color color = ((AccountingPanelTableModel) table.getModel()).getRowColor(row);
                if (isSelected){
                    color = color.darker();
                }
                c.setBackground(color);
                c.setForeground(Color.BLACK);
                return c;
            }
        };
        cellRenderer.setHorizontalAlignment(JLabel.CENTER);
        tableAccounting.setDefaultRenderer(Object.class, cellRenderer);
        TableColumnModel columnModel = tableAccounting.getColumnModel();
        columnModel.getColumn(0).setMinWidth(30);
        columnModel.getColumn(0).setMaxWidth(30);
        columnModel.getColumn(1).setMinWidth(50);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setMinWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);

        tableAccounting.addMouseListener(new AccountingPanelTableClicked(this, tableAccounting));
        tableAccounting.addKeyListener(new AccountingPanelTableDelete(this, tableAccounting));

        JButton buttonAdd = new JButton("Add Item");
        buttonAdd.addMouseListener(new AccountingPanelAddItemClicked(this));

        JScrollPane scrollPane = new JScrollPane(tableAccounting){
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
                return tableAccounting.getHeight() + 30;
            }
        };

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(scrollPane)
                .addComponent(buttonAdd, GroupLayout.Alignment.CENTER));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addGap(5)
                .addComponent(buttonAdd));

        this.getActionMap().put(Configuration.INIT_CONFIG, new InitConfigurableAction(this));
        this.getActionMap().put(Configuration.DAY_CHANGED, new AccountingPanelDayChanged(this,tableAccounting));
    }

    @Override
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
}

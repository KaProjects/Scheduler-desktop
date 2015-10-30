package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.GuiModel;
import org.kaleta.scheduler.frontend.ConfigurationAction;
import org.kaleta.scheduler.frontend.dialog.AddEditItemDialog;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class AccountingPanel extends JPanel{
    private GuiModel model;

    private JTable tableAccounting;

    public AccountingPanel(){
        initComponents();
    }

    private void initComponents(){
        tableAccounting = new JTable();
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
        columnModel.getColumn(1).setMinWidth(100);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setMinWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);

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

        JButton buttonAdd = new JButton("Add Item");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddEditItemDialog dialog = new AddEditItemDialog(model.getItemTypes(), model.getRecentlyUsedItems());
                dialog.setLocationRelativeTo((Component) model);
                dialog.setVisible(true);

                if (dialog.getResult()) {
                    Item item = dialog.getCreatedItem();
                    model.addItem(item);
                }
            }
        });

        tableAccounting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    AddEditItemDialog dialog = new AddEditItemDialog(model.getItemTypes(), new ArrayList<>());
                    Item itemToEdit = (Item) tableAccounting.getModel().getValueAt(tableAccounting.getSelectedRow(), 4);
                    dialog.setItem(itemToEdit);
                    dialog.setLocationRelativeTo((Component) model);
                    dialog.setVisible(true);

                    if (dialog.getResult()) {
                        Item createdItem = dialog.getCreatedItem();
                        itemToEdit.setIncome(createdItem.getIncome());
                        itemToEdit.setAmount(createdItem.getAmount());
                        itemToEdit.setType(createdItem.getType());
                        itemToEdit.setDescription(createdItem.getDescription());
                        model.updateItem(itemToEdit);
                        tableAccounting.repaint();
                    }
                }
            }
        });

        tableAccounting.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int selectedRow = tableAccounting.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }
                int result = JOptionPane.showConfirmDialog(AccountingPanel.this,
                        "Are you sure?", "Deleting Item", JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == 0) {
                    model.deleteItem((Item) tableAccounting.getModel().getValueAt(selectedRow, 4));
                }
            }
        }, KeyStroke.getKeyStroke("DELETE"), JComponent.WHEN_FOCUSED);

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(scrollPane)
                .addComponent(buttonAdd, GroupLayout.Alignment.CENTER));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(scrollPane)
                .addGap(5)
                .addComponent(buttonAdd));


        this.getActionMap().put(GuiModel.DAY_CHANGED, new ConfigurationAction() {
            @Override
            protected void actionPerformed(GuiModel guiModel) {
                if (model == null) {
                    model = guiModel;
                }

                Day day = model.getDay(model.getSelectedDayNumber());
                ((AccountingPanelTableModel) tableAccounting.getModel()).setData(day.getItems());
                tableAccounting.clearSelection();
                tableAccounting.revalidate();
                tableAccounting.repaint();
                AccountingPanel.this.revalidate();
                AccountingPanel.this.repaint();
            }
        });
    }
}

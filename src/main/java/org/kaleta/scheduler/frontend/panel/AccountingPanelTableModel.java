package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.common.ColorConstants;

import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 14.09.2015.
 */
public class AccountingPanelTableModel extends AbstractTableModel{
    private List<Item> rowData;

    public AccountingPanelTableModel(){
        rowData = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        return rowData.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0: return "+/-";
            case 1: return "Amount";
            case 2: return "Type";
            case 3: return "Description";
            default: throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public Object getValueAt(int row, int column) {
        Item item = rowData.get(row);
        switch (column){
            case 0: return (item.getIncome()) ? "+" : "-";
            case 1: return item.getAmount();
            case 2: return item.getType();
            case 3: return item.getDescription();
            case 4: return item;
            default: throw new IllegalArgumentException("columnIndex");
        }
    }

    public Color getRowColor(int row){
        return (rowData.get(row).getIncome()) ? ColorConstants.INCOME_GREEN : ColorConstants.EXPENSE_RED;
    }

    public void setData(List<Item> itemList){
        rowData.clear();
        rowData.addAll(itemList);
    }
}

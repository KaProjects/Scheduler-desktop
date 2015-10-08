package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 25.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class TableModelAccounting extends AbstractTableModel {
    private List<Item> items;
    private List<Color> rowColours;

    public TableModelAccounting(List<Item> items){
        this.items = items;             /*TODO sort*/
        rowColours = new ArrayList<Color>();
        for (int i=0;i<items.size();i++){
            rowColours.add(i,Color.white);
        }

    }
    @Override
    public int getRowCount() {
        return items.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Item item = items.get(rowIndex);
        switch (columnIndex){
            case 0: if (item.getIncome()){
                        rowColours.add(rowIndex,Color.getHSBColor(120/360f,0.5f,1));

                        return '+';
                    } else {
                        rowColours.add(rowIndex,Color.getHSBColor(0/360f,0.5f,1));
                        return '-';
                    }
            case 1: return item.getType();
            case 2: return item.getAmount();
            default: throw new IllegalArgumentException("columnIndex");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        final  java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("l18n/L18nStrings");
        switch (columnIndex){
            case 0: return "+/-";
            case 1: return bundle.getString("TYPE");
            case 2: return bundle.getString("AMOUNT");
            default: throw new IllegalArgumentException("columnIndex");
        }
    }

//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        switch (columnIndex) {
//            case 0: return Character.class;
//            case 1: return String.class;
//            case 2: return Integer.class;
//            default: throw new IllegalArgumentException("columnIndex");
//        }
//    }

    public Color getRowColour(int row) {
        return rowColours.get(row);
    }
}

package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.CustomUnit;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class AddedCustomUnitTableModel extends AbstractTableModel {

    private List<Triplet<String, String, String>> data = new ArrayList<>();

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Name";
            case 1:
                return "Abbreviation";
            case 2:
                return "Base Unit";
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return 0;
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Triplet<String, String, String> row = data.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return row.getA();
            case 1:
                return row.getB();
            case 2:
                return row.getC();
        }
        return null;
    }

    public AddedCustomUnitTableModel addRow(Triplet<String, String, String> row) {
        data.add(row);
        return this;
    }
}

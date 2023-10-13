package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class AddedIngredientsTableModel extends AbstractTableModel {

    private List<Triplet<Ingredient, Double, Unit>> data = new ArrayList<>();

    @Override
    public String getColumnName(int index) {
        switch (index) {
            case 0:
                return "Ingredient";
            case 1:
                return "Amount";
            case 2:
                return "Unit";
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
        Triplet<Ingredient, Double, Unit> row = data.get(rowIndex);
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

    public AddedIngredientsTableModel addRow(Triplet<Ingredient, Double, Unit> row) {
        data.add(row);
        return this;
    }
}

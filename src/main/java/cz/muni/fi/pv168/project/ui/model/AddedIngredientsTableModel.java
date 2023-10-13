package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;


public class AddedIngredientsTableModel extends AbstractTableModel {

    private List<Triplet> data = new ArrayList<>();



    private final List<Column<Triplet, ?>> columns = List.of(
            Column.readonly("Ingredient", Ingredient.class, Triplet::getA),
            Column.readonly("amount", double.class, Triplet::getB),
            Column.readonly("Unit", Unit.class, Triplet::getC)
    );


    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Triplet triplet = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(triplet);
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns.get(columnIndex).getName();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columns.get(columnIndex).getColumnType();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columns.get(columnIndex).isEditable();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Triplet triplet = getEntity(rowIndex);
        columns.get(columnIndex).setValue(value, triplet);
    }

    public void deleteRow(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Triplet triplet) {
        int newRowIndex = data.size();
        data.add(triplet);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Triplet triplet) {
        int rowIndex = data.indexOf(triplet);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public Triplet getEntity(int rowIndex) {
        return data.get(rowIndex);
    }

}

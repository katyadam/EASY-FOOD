package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.CustomUnit;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomUnitTableModel extends AbstractTableModel {
    private final List<CustomUnit> customUnits;

    private final List<Column<CustomUnit, ?>> columns = List.of(
            Column.readonly("Name", String.class, CustomUnit::getFullName),
            Column.readonly("Abbreviation", String.class, CustomUnit::getAbbreviation),
            Column.readonly("BaseAmount", String.class, CustomUnit::getBaseAmount)
    );

    public CustomUnitTableModel(List<CustomUnit> customUnits) {
        this.customUnits = new ArrayList<>(customUnits);
    }

    @Override
    public int getRowCount() {
        return customUnits.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CustomUnit customUnit = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(customUnit);
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
        CustomUnit customUnit = getEntity(rowIndex);
        columns.get(columnIndex).setValue(value, customUnit);
    }

    public void deleteRow(int rowIndex) {
        customUnits.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(CustomUnit customUnit) {
        int newRowIndex = customUnits.size();
        customUnits.add(customUnit);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(CustomUnit customUnit) {
        int rowIndex = customUnits.indexOf(customUnit);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public CustomUnit getEntity(int rowIndex) {
        return customUnits.get(rowIndex);
    }
}

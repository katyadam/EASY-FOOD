package cz.muni.fi.pv168.project.ui.model;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityTableModel<T> extends AbstractTableModel {

    protected final List<T> data;
    private final List<Column<T, ?>> columns;

    public AbstractEntityTableModel(List<Column<T, ?>> columns, List<T> data) {
        this.columns = columns;
        this.data = new ArrayList<>(data);
    }

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
        T entity = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(entity);
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
        T entity = getEntity(rowIndex);
        columns.get(columnIndex).setValue(value, entity);
    }

    public void deleteRow(int rowIndex) {
        data.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(T entity) {
        int newRowIndex = data.size();
        data.add(entity);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(T entity) {
        int rowIndex = data.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public T getEntity(int rowIndex) {
        return data.get(rowIndex);
    }

    public List<T> getData() {
        return data;
    }
}

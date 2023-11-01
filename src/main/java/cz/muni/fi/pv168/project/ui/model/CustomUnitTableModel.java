package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class CustomUnitTableModel extends AbstractTableModel implements EntityTableModel<CustomUnit> {
    private final CrudService<CustomUnit> customUnitCrudService;
    public List<CustomUnit> customUnits;

    private final List<Column<CustomUnit, ?>> columns = List.of(
            Column.readonly("Name", String.class, CustomUnit::getFullName),
            Column.readonly("Abbreviation", String.class, CustomUnit::getAbbreviation),
            Column.readonly("BaseAmount", String.class, CustomUnit::getBaseAmount)
    );

    public CustomUnitTableModel(CrudService<CustomUnit> customUnitCrudService) {
        this.customUnitCrudService = customUnitCrudService;
        this.customUnits = new ArrayList<>(customUnitCrudService.findAll());
    }

    @Override
    public CustomUnit getEntity(int rowIndex) {
        return customUnits.get(rowIndex);
    }

    @Override
    public void deleteRow(int rowIndex) {
        CustomUnit entity = getEntity(rowIndex);
        customUnitCrudService.deleteByGuid(entity.getGuid());
        customUnits.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public void addRow(CustomUnit entity) {
        customUnitCrudService.create(entity).intoException();
        int newIndex = customUnits.size();
        customUnits.add(entity);
        fireTableRowsInserted(newIndex, newIndex);
    }

    @Override
    public void updateRow(CustomUnit entity) {
        customUnitCrudService.update(entity).intoException();
        int rowIndex = customUnits.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.customUnits = new ArrayList<>(customUnitCrudService.findAll());
        fireTableDataChanged();
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
    public Object getValueAt(int rowIndex, int columnIndex) {
        var recipe = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(recipe);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue != null) {
            var recipe = getEntity(rowIndex);
            columns.get(columnIndex).setValue(aValue, recipe);
            updateRow(recipe);
        }
    }
}

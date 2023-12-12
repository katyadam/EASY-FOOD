package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;
import cz.muni.fi.pv168.project.business.service.validation.ValidationResult;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityTableModel<T extends Entity> extends AbstractTableModel implements EntityTableModel<T> {

    public List<T> entities;
    private final List<Column<T, ?>> columns;
    public final CrudService<T> crudService;

    public AbstractEntityTableModel(List<Column<T, ?>> columns, List<T> entities, CrudService<T> crudService) {
        this.columns = columns;
        this.entities = new ArrayList<>(entities);
        this.crudService = crudService;
    }

    @Override
    public int getRowCount() {
        return entities.size();
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
        T entity = getEntity(rowIndex);
        entities.remove(rowIndex);
        crudService.deleteByGuid(entity.getGuid());
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(T entity) {
        ValidationResult validationResult = crudService.create(entity);
        if (validationResult.isValid()) {
            entities.add(entity);
            refresh();
        } else {
            JOptionPane.showMessageDialog(
                    new JPanel(),
                    String.join(", ", validationResult.getValidationErrors())
            );
        }
    }

    public void updateRow(T entity) {
        ValidationResult validationResult = crudService.update(entity);
        if (validationResult.isValid()) {
            refresh();
        } else {
            JOptionPane.showMessageDialog(
                    new JPanel(),
                    String.join(", ", validationResult.getValidationErrors())
            );
        }
    }

    public T getEntity(int rowIndex) {
        return entities.get(rowIndex);
    }

    public List<T> getEntities() {
        return entities;
    }

    public void refresh() {
        this.entities = new ArrayList<>(crudService.findAll());
        fireTableDataChanged();
    }

}

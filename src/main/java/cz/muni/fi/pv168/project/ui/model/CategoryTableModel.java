package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.renderers.ColorRenderer;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryTableModel extends AbstractTableModel implements EntityTableModel<Category> {

    private final CrudService<Category> categoryCrudService;
    public List<Category> categories;
    private final List<Column<Category, ?>> columns = List.of(
            Column.readonly("Name", String.class, Category::getName),
            Column.readonly("Color", Color.class, Category::getColor)
    );

    public CategoryTableModel(CrudService<Category> categoryCrudService) {
        this.categoryCrudService = categoryCrudService;
        this.categories = new ArrayList<>(categoryCrudService.findAll());
    }

    @Override
    public Category getEntity(int rowIndex) {
        return categories.get(rowIndex);
    }

    @Override
    public void deleteRow(int rowIndex) {
        Category entity = getEntity(rowIndex);
        categoryCrudService.deleteByGuid(entity.getGuid());
        categories.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public void addRow(Category entity) {
        categoryCrudService.create(entity).intoException();
        int newIndex = categories.size();
        categories.add(entity);
        fireTableRowsInserted(newIndex, newIndex);
    }

    @Override
    public void updateRow(Category entity) {
        categoryCrudService.update(entity).intoException();
        int rowIndex = categories.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    @Override
    public void refresh() {
        this.categories = new ArrayList<>(categoryCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return categories.size();
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
        var category = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(category);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue != null) {
            var category = getEntity(rowIndex);
            columns.get(columnIndex).setValue(aValue, category);
            updateRow(category);
        }
    }
}

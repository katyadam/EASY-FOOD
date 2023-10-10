package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.table.AbstractTableModel;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RecipeTableModel extends AbstractTableModel {
    private final List<Recipe> recipes;

    private final List<Column<Recipe, ?>> columns = List.of(
            Column.editable("Recipe name", String.class, Recipe::getRecipeName, Recipe::setRecipeName),
            Column.editable("Category name", Category.class, Recipe::getCategory, Recipe::setCategory),
            Column.readonly("Nutritional value", int.class, Recipe::getNutritionalValue),
            Column.editable("Portions", int.class, Recipe::getPortions, Recipe::setPortions),
            Column.editable("Time to prepare", LocalTime.class, Recipe::getPreparationTime, Recipe::setPreparationTime)

    );

    public RecipeTableModel(List<Recipe> recipes) {
        this.recipes = new ArrayList<>(recipes);
    }

    @Override
    public int getRowCount() {
        return recipes.size();
    }

    @Override
    public int getColumnCount() {
        return columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Recipe Recipe = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(Recipe);
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
        Recipe Recipe = getEntity(rowIndex);
        columns.get(columnIndex).setValue(value, Recipe);
    }

    public void deleteRow(int rowIndex) {
        recipes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void addRow(Recipe Recipe) {
        int newRowIndex = recipes.size();
        recipes.add(Recipe);
        fireTableRowsInserted(newRowIndex, newRowIndex);
    }

    public void updateRow(Recipe Recipe) {
        int rowIndex = recipes.indexOf(Recipe);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public Recipe getEntity(int rowIndex) {
        return recipes.get(rowIndex);
    }
}

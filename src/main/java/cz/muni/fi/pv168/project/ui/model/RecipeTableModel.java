package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.PreparationTime;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class RecipeTableModel extends AbstractTableModel implements EntityTableModel<Recipe> {

    private final CrudService<Recipe> recipeCrudService;
    public List<Recipe> recipes;

    private final List<Column<Recipe, ?>> columns = List.of(
            Column.editable("Recipe name", String.class, Recipe::getRecipeName, Recipe::setRecipeName),
            Column.readonly("Category name", String.class, Recipe::getCategoryName),
            Column.readonly("Nutritional value [KCAL]", int.class, Recipe::getRecipeNutritionalValue),
            Column.editable("Portions", int.class, Recipe::getPortions, Recipe::setPortions),
            Column.editable(
                    "Time to prepare",
                    PreparationTime.class,
                    Recipe::getPreparationTime,
                    Recipe::setPreparationTime
            )
    );

    public RecipeTableModel(CrudService<Recipe> recipeCrudService) {
        this.recipeCrudService = recipeCrudService;
        this.recipes = new ArrayList<>(recipeCrudService.findAll());
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    @Override
    public Recipe getEntity(int rowIndex) {
        return recipes.get(rowIndex);
    }

    @Override
    public void deleteRow(int rowIndex) {
        Recipe entity = getEntity(rowIndex);
        recipeCrudService.deleteByGuid(entity.getGuid());
        recipes.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public void addRow(Recipe entity) {
        recipeCrudService.create(entity).intoException();
        int newIndex = recipes.size();
        recipes.add(entity);
        fireTableRowsInserted(newIndex, newIndex);
    }

    @Override
    public void updateRow(Recipe entity) {
        recipeCrudService.update(entity).intoException();
        int rowIndex = recipes.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.recipes = new ArrayList<>(recipeCrudService.findAll());
        fireTableDataChanged();
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

package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class IngredientTableModel extends AbstractTableModel implements EntityTableModel<Ingredient> {
    private final CrudService<Ingredient> ingredientCrudService;
    public List<Ingredient> ingredients;

    private final List<Column<Ingredient, ?>> columns = List.of(
            Column.readonly("Ingredient name", String.class, Ingredient::getName),
            Column.readonly("Nutritional value [KCAL]", int.class, Ingredient::getNutritionalValue),
            Column.readonly("Abbreviation", String.class, Ingredient::getUnitAbbreviation)
    );

    public IngredientTableModel(CrudService<Ingredient> ingredientCrudService) {
        this.ingredientCrudService = ingredientCrudService;
        this.ingredients = new ArrayList<>(ingredientCrudService.findAll());
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public Ingredient getEntity(int rowIndex) {
        return ingredients.get(rowIndex);
    }

    @Override
    public void deleteRow(int rowIndex) {
        Ingredient entity = getEntity(rowIndex);
        ingredientCrudService.deleteByGuid(entity.getGuid());
        ingredients.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    @Override
    public void addRow(Ingredient entity) {
        ingredientCrudService.create(entity).intoException();
        int newIndex = ingredients.size();
        ingredients.add(entity);
        fireTableRowsInserted(newIndex, newIndex);
    }

    @Override
    public void updateRow(Ingredient entity) {
        ingredientCrudService.update(entity).intoException();
        int rowIndex = ingredients.indexOf(entity);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    public void refresh() {
        this.ingredients = new ArrayList<>(ingredientCrudService.findAll());
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return ingredients.size();
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
        var ingredient = getEntity(rowIndex);
        return columns.get(columnIndex).getValue(ingredient);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (aValue != null) {
            var ingredient = getEntity(rowIndex);
            columns.get(columnIndex).setValue(aValue, ingredient);
            updateRow(ingredient);
        }
    }

    public Ingredient[] getArrayOfIngredients() {
        return ingredients.toArray(new Ingredient[0]);
    }
}
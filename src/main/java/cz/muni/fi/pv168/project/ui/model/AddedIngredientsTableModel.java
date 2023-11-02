package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

import java.util.ArrayList;
import java.util.List;


public class AddedIngredientsTableModel extends AbstractEntityTableModel<AddedIngredient> {

    private Integer totalNutritionalValue = 0;

    public AddedIngredientsTableModel() {
        super(List.of(
                Column.readonly("Ingredient", Ingredient.class, AddedIngredient::ingredient),
                Column.readonly("amount", double.class, AddedIngredient::value),
                Column.readonly("Unit", Unit.class, AddedIngredient::unit)
        ), new ArrayList<>());
    }

    public int getTotalNutritionalValue() {
        return totalNutritionalValue;
    }

    @Override
    public void addRow(AddedIngredient entity) {
        totalNutritionalValue += entity.ingredient().getNutritionalValue();
        super.addRow(entity);
    }

    public boolean contains(Ingredient ingredient) {
        for (AddedIngredient addedIngredient : data) {
            if (addedIngredient.ingredient() == ingredient) {
                return true;
            }
        }
        return false;
    }
}

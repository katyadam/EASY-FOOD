package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;

import java.util.List;

public class IngredientTableModel extends AbstractEntityTableModel<Ingredient> {
    public IngredientTableModel(List<Ingredient> ingredients) {
        super(List.of(
                Column.readonly("Ingredient name", String.class, Ingredient::getName),
                Column.readonly("Nutritional value [KCAL]", int.class, Ingredient::getNutritionalValue),
                Column.readonly("Abbreviation", String.class, Ingredient::getUnitAbbreviation)
        ), ingredients);
    }

    public Ingredient[] toArray() {
        return data.toArray(new Ingredient[data.size()]);
    }
}
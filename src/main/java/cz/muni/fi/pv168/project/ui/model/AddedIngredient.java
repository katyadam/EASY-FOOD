package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Entity;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

public class AddedIngredient extends Entity {

    private final Ingredient ingredient;
    private final Double quantity;
    private final Unit unit;

    public AddedIngredient(Ingredient ingredient, Double quantity, Unit unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }
}

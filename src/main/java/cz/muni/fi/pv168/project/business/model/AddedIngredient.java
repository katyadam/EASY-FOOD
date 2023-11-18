package cz.muni.fi.pv168.project.business.model;

public class AddedIngredient extends Entity {

    private Ingredient ingredient;
    private Double quantity;
    private Unit unit;

    public AddedIngredient() {
    }

    public AddedIngredient(Ingredient ingredient, Double quantity, Unit unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(Unit unit) {
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

package cz.muni.fi.pv168.project.business.model;

import java.text.DecimalFormat;

public class AddedIngredient extends Entity {

    private Ingredient ingredient;
    private Double quantity;
    private CustomUnit unit;
    private Recipe recipe;
    private final DecimalFormat amountFormat = new DecimalFormat("#.##");

    public AddedIngredient() {
    }

    public AddedIngredient(Ingredient ingredient, Double quantity, CustomUnit unit) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
    }

    public AddedIngredient(
            String guid,
            Ingredient ingredient,
            Recipe recipe,
            CustomUnit unit,
            Double quantity
    ) {
        super(guid);
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.unit = unit;
        this.quantity = quantity;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(CustomUnit unit) {
        this.unit = unit;
    }

    public CustomUnit getUnit() {
        return unit;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

}

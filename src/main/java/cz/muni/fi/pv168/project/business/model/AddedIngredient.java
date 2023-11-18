package cz.muni.fi.pv168.project.business.model;

public class AddedIngredient extends Entity {

    private Ingredient ingredient;
    private Double quantity;
    private Unit unit;
    private double amount;
    private Recipe recipe;

    public AddedIngredient() {
    }

    public AddedIngredient(Ingredient ingredient, Double quantity, Unit unit, Recipe recipe) {
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.unit = unit;
        this.recipe = recipe;
    }
    public AddedIngredient(
            String guid,
            Recipe recipe,
            Ingredient ingredient,
            Unit unit,
            double amount
    ) {
        super(guid);
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.unit = unit;
        this.amount = amount;
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

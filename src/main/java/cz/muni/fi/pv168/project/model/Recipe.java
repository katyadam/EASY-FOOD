package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import java.util.List;
import java.util.stream.IntStream;

public class Recipe extends Entity{
    private String recipeName;
    private PreparationTime preparationTime;
    private int portions;
    private Category category;
    private String description = "No recipe description";
    private final AddedIngredientsTableModel usedIngredients = new AddedIngredientsTableModel();


    public Recipe(
            String recipeName,
            Category category,
            int portions,
            PreparationTime preparationTime
    ) {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.category = category;
        //this.nutritionalValue = calculateNutritionalValue(ingredientList); TODO
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setPreparationTime(PreparationTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public String getDescription() {
        return description;
    }

    public PreparationTime getPreparationTime() {
        int hours = preparationTime.hours();
        int minutes = preparationTime.minutes();
        return new PreparationTime(hours, minutes);
    }

    public int getPortions() {
        return portions;
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        return category != null ? category.getName() : "";
    }

    public int getRecipeNutritionalValue() {
        return usedIngredients.getTotalNutritionalValue();
    }

    public AddedIngredientsTableModel getUsedIngredients() {
        return usedIngredients;
    }

    public List<Ingredient> getIngredientList() {
        if (this.getUsedIngredients().getRowCount() == 0) {
            return List.of();
        }
        return IntStream.range(0, this.getUsedIngredients().getRowCount())
                .mapToObj(i -> this.getUsedIngredients().getEntity(i))
                .map(Triplet::ingredient)
                .toList();
    }
}

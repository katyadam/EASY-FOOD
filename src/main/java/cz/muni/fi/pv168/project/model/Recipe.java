package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.service.crud.AddedIngredientCrudService;
import cz.muni.fi.pv168.project.service.validation.AddedIngredientValidator;
import cz.muni.fi.pv168.project.storage.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;

import java.awt.*;
import java.util.ArrayList;

public class Recipe extends Entity {
    private String recipeName;
    private PreparationTime preparationTime;
    private int portions;
    private Category category;
    private String description = "No recipe description";
    private final AddedIngredientsTableModel usedIngredients = new AddedIngredientsTableModel(
            new AddedIngredientCrudService(
                    new InMemoryRepository<>(new ArrayList<>()),
                    new AddedIngredientValidator(),
                    new UuidGuidProvider()
            ));

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

    public Color getCategoryColor() {
        return category != null ? category.getColor() : Color.black;
    }

    public int getRecipeNutritionalValue() {
        return usedIngredients.getTotalNutritionalValue();
    }

    public AddedIngredientsTableModel getUsedIngredients() {
        return usedIngredients;
    }

    @Override
    public String toString() {
        return recipeName;
    }
}

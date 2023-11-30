package cz.muni.fi.pv168.project.business.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Recipe extends Entity {
    private PreparationTime preparationTime;
    private int portions;
    private Category category;
    private String description = "No recipe description";
    private int nutritionalValue = 0;

    private List<AddedIngredient> addedIngredients = new ArrayList<>();

    public Recipe() {
    }

    public Recipe(
            String guid,
            String recipeName,
            Category category,
            PreparationTime preparationTime,
            int portions,
            String description
    ) {
        super(guid);
        this.name = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.category = category;
        this.description = description;
    }

    public String getRecipeName() {
        return super.name;
    }

    public void setRecipeName(String recipeName) {
        super.name = recipeName;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public void decrementNutritionalValue(int value) {
        nutritionalValue -= value;
    }

    public void incrementNutritionalValue(int value) {
        nutritionalValue += value;
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


    public String getDescription() {
        return description;
    }

    public PreparationTime getPreparationTime() {
        int hours = preparationTime.hours();
        int minutes = preparationTime.minutes();
        return new PreparationTime(hours, minutes);
    }

    public void destroy() {
        if (category != null) {
            category.removeRecipe(this);
        }
        for (AddedIngredient addedIngredient : addedIngredients) {
            addedIngredient.getIngredient().removeRecipe(this);
            if (addedIngredient.getUnit() instanceof Entity) {
                ((Entity) addedIngredient.getUnit()).removeRecipe(this);
            }
        }
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
        return nutritionalValue;
    }


    public void addIngredient(AddedIngredient addedIngredient) {
        nutritionalValue += addedIngredient.getIngredient().getNutritionalValue();
        addedIngredients.add(addedIngredient);
    }

    public void removeIngredient(AddedIngredient addedIngredient) {
        nutritionalValue -= addedIngredient.getIngredient().getNutritionalValue();
        addedIngredients.remove(addedIngredient);
    }

    public void updateNutritionalValue() {
        nutritionalValue = 0;
        addedIngredients.forEach(
                ai -> {
                    if (ai.getIngredient() != null) {
                        nutritionalValue += ai.getIngredient().getNutritionalValue();
                    }
                }
        );
    }

    public List<AddedIngredient> getAddedIngredients() {
        return addedIngredients;
    }

    @Override
    public String toString() {
        return name;
    }
}

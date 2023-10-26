package cz.muni.fi.pv168.project.model;

import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

public class Recipe {
    private String recipeName;
    private LocalTime preparationTime;
    private int portions;
    private Category category;


    private int nutritionalValue;

    private String description = "No recipe description";

    private AddedIngredientsTableModel usedIngredients = new AddedIngredientsTableModel();


    public Recipe(
            String recipeName,
            Category category,
            int calories,
            int portions,
            LocalTime preparationTime
    ) {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.category = category;
        this.nutritionalValue = calories;
        //this.nutritionalValue = calculateNutritionalValue(ingredientList); TODO
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setPreparationTime(LocalTime preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setPortions(int portions) {
        this.portions = portions;
    }

    public void setNutritionalValue(int value) {
        this.nutritionalValue = value;
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

    public String getDesription() {
        return description;
    }

    private LocalTime getParsePreparationTime() {
        int hours = preparationTime.getHour();
        int minutes = preparationTime.getMinute();
        //int seconds = preparationTime.getSecond();

        return LocalTime.of(hours, minutes);
    }

    public LocalTime getPreparationTime() {
        return getParsePreparationTime();
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

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public AddedIngredientsTableModel getUsedIngredients() {
        return usedIngredients;
    }

    public List<Ingredient> getIngredients() {
        if (this.getUsedIngredients().getRowCount() == 0) {
            return List.of();
        }
        return IntStream.range(0, this.getUsedIngredients().getRowCount())
                .mapToObj(i -> this.getUsedIngredients().getEntity(i))
                .map(Triplet::getA)
                .toList();
    }
}

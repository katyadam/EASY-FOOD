package cz.muni.fi.pv168.project.model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Recipe {
    private String recipeName;
    private LocalTime preparationTime;
    private int portions;
    private Category category = null;
    private ArrayList<Ingredient> ingredientList;
    private int nutritionalValue = 0;


    public Recipe(String recipeName, LocalTime preparationTime, int portions, ArrayList<Ingredient> ingredientList, Category category) {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.ingredientList = ingredientList; // TODO have something like Dictionary
        this.nutritionalValue = 0;
        this.category = category;
        //this.nutritionalValue = calculateNutritionalValue(ingredientList); TODO
    }


    /*
    Setters
     */
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

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    /*
    Getters
     */
    public String getRecipeName() {
        return recipeName;
    }

    private LocalTime getParsePreparationTime() {
        int hours = preparationTime.getHour();
        int minutes = preparationTime.getMinute();
        int seconds = preparationTime.getSecond();

        return LocalTime.of(hours, minutes, seconds);
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

}

package cz.muni.fi.pv168.project.model;

import java.util.ArrayList;

public class Recipe {
    private String recipeName;
    private String preparationTime;

    private long portions;
    private Category category;

    private ArrayList<Ingredient> ingredientList;

    public Recipe(String recipeName, String preparationTime, long portions, ArrayList<Ingredient> ingredientList) {
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.ingredientList = ingredientList;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }

    public void setPreparationTime(String preparationTime) {
        this.preparationTime = preparationTime;
    }

    public void setPortions(long portions) {
        this.portions = portions;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}

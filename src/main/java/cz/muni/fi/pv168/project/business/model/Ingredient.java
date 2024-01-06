package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.ui.MainWindow;

import java.util.List;

public class Ingredient extends Entity {
    private int nutritionalValue;

    public Ingredient() {
    }

    public Ingredient(
            String guid,
            String name,
            int nutritionalValue
    ) {
        super(guid);
        this.name = name;
        this.nutritionalValue = nutritionalValue;
    }

    public Ingredient(String name, int nutritionalValue) {
        this.name = name;
        this.nutritionalValue = nutritionalValue;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }


    public int getNutritionalValue() {
        return nutritionalValue;
    }

    @Override
    public String toString() {
        return name;
    }

    /*public void calculateUsage(List<Recipe> recipes) {
        for ( Recipe recipe : recipes) {
            for (AddedIngredient addedIngredient : recipe.getAddedIngredients()) {
                if (addedIngredient.getIngredient().equals(this)) {
                    usage++;
                    break;
                }
            }
        }
    }*/

    public static boolean contains(List<AddedIngredient> addedIngredients, Ingredient ingredient) {
        boolean flag = false;
        for (AddedIngredient added: addedIngredients) {
            if (added.getIngredient().equals(ingredient)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    public int getUsage() {
        int usage = 0;
        for ( Recipe recipe : MainWindow.commonDependencyProvider.getRecipeCrudService().findAll()) {
            for (AddedIngredient addedIngredient : recipe.getAddedIngredients()) {
                if (addedIngredient.getIngredient().equals(this)) {
                    usage++;
                    break;
                }
            }
        }
        return usage;
    }

}

package cz.muni.fi.pv168.project.business.model;

import cz.muni.fi.pv168.project.ui.MainWindow;
import cz.muni.fi.pv168.project.wiring.CommonDependencyProvider;

import java.util.List;

public class Ingredient extends Entity {
    private int nutritionalValue;
    private int usage = 0;

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

    public void calculateUsage() {
        CommonDependencyProvider dependencyProvider = MainWindow.commonDependencyProvider;
        for ( Recipe recipe : dependencyProvider.getRecipeCrudService().findAll()) {
            for (AddedIngredient addedIngredient : recipe.getAddedIngredients()) {
                if (addedIngredient.getIngredient().equals(this)) {
                    usage++;
                    break;
                }
            }
        }
    }

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
        return usage;
    }
    public void setUsage(int i) {
        usage = i;
    }
}

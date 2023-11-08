package cz.muni.fi.pv168.project.ui.filters.recipes;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.filters.EntityMatcher;

public class RecipeMatcher extends EntityMatcher<Recipe> {
    private final RecipeFilterAttributes recipeFilterAttributes;

    public RecipeMatcher(RecipeFilterAttributes recipeFilterAttributes) {
        this.recipeFilterAttributes = recipeFilterAttributes;
    }

    @Override
    public boolean evaluate(Recipe recipe) {
        if (recipeFilterAttributes.recipeName() != null) {
            return recipe.getRecipeName().startsWith(recipeFilterAttributes.recipeName());
        }
        return ingredientMatch(recipe)
                && categoryMatch(recipe)
                && caloriesRangeMatch(recipe)
                && portionsRangeMatch(recipe);
    }

    private boolean ingredientMatch(Recipe recipe) {
        if (recipeFilterAttributes == null || recipeFilterAttributes.ingredients().isEmpty()) {
            return true;
        }
        for (Ingredient ingredient: recipeFilterAttributes.ingredients()) {
            if ( !recipe.getUsedIngredients().contains(ingredient)) {
                return false;
            }
        }
        return true;
    }

    private boolean categoryMatch(Recipe recipe) {
        if ( recipeFilterAttributes.category().isEmpty() ) { // TODO: zmenit na null check!!!
            return true;
        }
        if ( recipe.getCategory() == null ) {
            return false;
        }
        for (Category category: recipeFilterAttributes.category()) {
            if ( recipe.getCategory() == category) {
                return true;
            }
        }
        return false;
    }

    private boolean caloriesRangeMatch(Recipe recipe) {
        if (recipeFilterAttributes.calMin() == null) {
            return true;
        }
        return recipeFilterAttributes.calMin() <= recipe.getRecipeNutritionalValue()
                && recipeFilterAttributes.calMax() >= recipe.getRecipeNutritionalValue();
    }

    private boolean portionsRangeMatch(Recipe recipe) {
        if (recipeFilterAttributes.portionsMin() == null) {
            return true;
        }
        return recipeFilterAttributes.portionsMin() <= recipe.getPortions()
                && recipeFilterAttributes.portionsMax() >= recipe.getPortions();
    }
}

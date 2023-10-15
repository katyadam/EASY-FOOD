package cz.muni.fi.pv168.project.ui.filters.recipes;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.filters.EntityMatcher;

public class RecipeMatcher extends EntityMatcher<Recipe> {
    private final RecipeFilterAttributes recipeFilterAttributes;

    public RecipeMatcher(RecipeFilterAttributes recipeFilterAttributes) {
        this.recipeFilterAttributes = recipeFilterAttributes;
    }

    @Override
    public boolean evaluate(Recipe recipe) {
        return ingredientMatch(recipe)
                && categoryMatch(recipe)
                && caloriesRangeMatch(recipe)
                && portionsRangeMatch(recipe);
    }

    private boolean ingredientMatch(Recipe recipe) {
        return recipe.getIngredients().contains(recipeFilterAttributes.ingredient());
    }

    private boolean categoryMatch(Recipe recipe) {
        if (recipe.getCategory().getName().isEmpty()) {
            return true;
        }
        return recipe.getCategory().equals(recipeFilterAttributes.category());
    }

    private boolean caloriesRangeMatch(Recipe recipe) {
        return recipeFilterAttributes.calMin() <= recipe.getNutritionalValue()
                && recipeFilterAttributes.calMax() >= recipe.getNutritionalValue();
    }

    private boolean portionsRangeMatch(Recipe recipe) {
        return recipeFilterAttributes.portionsMin() <= recipe.getPortions()
                && recipeFilterAttributes.portionsMax() >= recipe.getPortions();
    }
}

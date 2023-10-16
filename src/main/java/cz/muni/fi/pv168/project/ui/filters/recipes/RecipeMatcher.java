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
        if (recipeFilterAttributes.recipeName() != null) {
            return recipe.getRecipeName().startsWith(recipeFilterAttributes.recipeName());
        }
        return ingredientMatch(recipe)
                && categoryMatch(recipe)
                && caloriesRangeMatch(recipe)
                && portionsRangeMatch(recipe);
    }

    private boolean ingredientMatch(Recipe recipe) {
        if (recipeFilterAttributes.ingredient() == null) {
            return true;
        }
        return recipe.getIngredients().contains(recipeFilterAttributes.ingredient());
    }

    private boolean categoryMatch(Recipe recipe) {
        if (recipe.getCategory().getName().isEmpty()) { // TODO: zmenit na null check!!!
            return true;
        }
        return recipe.getCategory().equals(recipeFilterAttributes.category());
    }

    private boolean caloriesRangeMatch(Recipe recipe) {
        if (recipeFilterAttributes.calMin() == null) {
            return true;
        }
        return recipeFilterAttributes.calMin() <= recipe.getNutritionalValue()
                && recipeFilterAttributes.calMax() >= recipe.getNutritionalValue();
    }

    private boolean portionsRangeMatch(Recipe recipe) {
        if (recipeFilterAttributes.portionsMin() == null) {
            return true;
        }
        return recipeFilterAttributes.portionsMin() <= recipe.getPortions()
                && recipeFilterAttributes.portionsMax() >= recipe.getPortions();
    }
}

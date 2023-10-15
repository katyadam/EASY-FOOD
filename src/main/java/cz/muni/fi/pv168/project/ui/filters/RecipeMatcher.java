package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.model.Recipe;

public class RecipeMatcher extends EntityMatcher<Recipe> {
    private final FilterAttributes filterAttributes;

    public RecipeMatcher(FilterAttributes filterAttributes) {
        this.filterAttributes = filterAttributes;
    }

    @Override
    public boolean evaluate(Recipe recipe) {
        return ingredientMatch(recipe)
                && categoryMatch(recipe)
                && caloriesRangeMatch(recipe)
                && portionsRangeMatch(recipe);
    }

    private boolean ingredientMatch(Recipe recipe) {
        return recipe.getIngredients().contains(filterAttributes.ingredient());
    }

    private boolean categoryMatch(Recipe recipe) {
        if (recipe.getCategory().getName().isEmpty()) {
            return true;
        }
        return recipe.getCategory().equals(filterAttributes.category());
    }

    private boolean caloriesRangeMatch(Recipe recipe) {
        return filterAttributes.calMin() <= recipe.getNutritionalValue()
                && filterAttributes.calMax() >= recipe.getNutritionalValue();
    }

    private boolean portionsRangeMatch(Recipe recipe) {
        return filterAttributes.portionsMin() <= recipe.getPortions()
                && filterAttributes.portionsMax() >= recipe.getPortions();
    }
}

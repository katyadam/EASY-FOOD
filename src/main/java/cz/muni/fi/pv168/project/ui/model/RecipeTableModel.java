package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.PreparationTime;
import cz.muni.fi.pv168.project.model.Recipe;

import java.time.LocalTime;
import java.util.List;

public class RecipeTableModel extends AbstractEntityTableModel<Recipe> {
    public RecipeTableModel(List<Recipe> recipes) {
        super(List.of(
                Column.editable("Recipe name", String.class, Recipe::getRecipeName, Recipe::setRecipeName),
                Column.readonly("Category name", String.class, Recipe::getCategoryName),
                Column.readonly("Nutritional value", int.class, Recipe::getNutritionalValue),
                Column.editable("Portions", int.class, Recipe::getPortions, Recipe::setPortions),
                Column.editable(
                        "Time to prepare",
                        PreparationTime.class,
                        Recipe::getPreparationTime,
                        Recipe::setPreparationTime
                )

        ), recipes);
    }
}

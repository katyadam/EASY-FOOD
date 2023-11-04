package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.PreparationTime;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.service.crud.CrudService;

import java.awt.*;
import java.util.List;

public class RecipeTableModel extends AbstractEntityTableModel<Recipe> {

    private final CrudService<Recipe> recipeCrudService;

    public RecipeTableModel(CrudService<Recipe> recipeCrudService) {
        super(List.of(
                Column.editable("Recipe name", String.class, Recipe::getRecipeName, Recipe::setRecipeName),
                Column.readonly("Category name", String.class, Recipe::getCategoryName),
                Column.readonly("Color", Color.class, Recipe::getCategoryColor),
                Column.readonly("Nutritional value [KCAL]", int.class, Recipe::getRecipeNutritionalValue),
                Column.editable("Portions", int.class, Recipe::getPortions, Recipe::setPortions),
                Column.editable(
                        "Time to prepare",
                        PreparationTime.class,
                        Recipe::getPreparationTime,
                        Recipe::setPreparationTime
                )
        ), recipeCrudService.findAll(), recipeCrudService);
        this.recipeCrudService = recipeCrudService;
    }

}

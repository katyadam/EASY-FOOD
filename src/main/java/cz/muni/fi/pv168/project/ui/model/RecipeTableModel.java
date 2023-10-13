package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.table.AbstractTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
                       LocalTime.class,
                       Recipe::getPreparationTime,
                       Recipe::setPreparationTime
               )

       ),recipes);
    }
}

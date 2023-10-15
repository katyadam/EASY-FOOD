package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.ui.filters.FilterAttributes;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.ui.filters.RecipeRowFilter;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;

public class FilterRecipesAction extends AbstractAction {

    JComboBox<Ingredient> ingredientFilter;
    JComboBox<String> categoryFilter;
    JSpinner caloriesMinFilter;
    JSpinner caloriesMaxFilter;
    JSpinner portionsMinFilter;
    JSpinner portionsMaxFilter;
    JTable recipeTable;
    private final TableRowSorter<RecipeTableModel> recipeTableRowSorter;

    public FilterRecipesAction(
            JComboBox<Ingredient> ingredientFilter,
            JComboBox<String> categoryFilter,
            JSpinner caloriesMinFilter,
            JSpinner caloriesMaxFilter,
            JSpinner portionsMinFilter,
            JSpinner portionsMaxFilter,
            JTable recipeTable,
            TableRowSorter<RecipeTableModel> recipeTableRowSorter
    ) {
        super("", Icons.FILTER_ICON);
        this.ingredientFilter = ingredientFilter;
        this.categoryFilter = categoryFilter;
        this.caloriesMinFilter = caloriesMinFilter;
        this.caloriesMaxFilter = caloriesMaxFilter;
        this.portionsMinFilter = portionsMinFilter;
        this.portionsMaxFilter = portionsMaxFilter;
        this.recipeTable = recipeTable;
        this.recipeTableRowSorter = recipeTableRowSorter;

        putValue(SHORT_DESCRIPTION, "Filter recipes");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        FilterAttributes attributes = new FilterAttributes(
                (Ingredient) ingredientFilter.getSelectedItem(),
                (Category) categoryFilter.getSelectedItem(),
                (Integer) caloriesMinFilter.getValue(),
                (Integer) caloriesMaxFilter.getValue(),
                (Integer) portionsMinFilter.getValue(),
                (Integer) portionsMaxFilter.getValue()
        );
        recipeTableRowSorter.setRowFilter(new RecipeRowFilter(attributes));
//        RecipeTableFilter recipeTableFilter = new RecipeTableFilter(new TableRowSorter<>(recipeTableModel));
//        recipeTableFilter.filterRecipes(attributes);
    }
}

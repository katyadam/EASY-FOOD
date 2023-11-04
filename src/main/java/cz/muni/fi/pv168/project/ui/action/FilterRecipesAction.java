package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeFilterAttributes;
import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeRowFilter;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;

public class FilterRecipesAction extends AbstractAction {

    private final TableRowSorter<RecipeTableModel> recipeTableRowSorter;
    JComboBox<Ingredient> ingredientFilter;
    JComboBox<Category> categoryFilter;
    JSpinner caloriesMinFilter;
    JSpinner caloriesMaxFilter;
    JSpinner portionsMinFilter;
    JSpinner portionsMaxFilter;
    JTable recipeTable;

    public FilterRecipesAction(
            JComboBox<Ingredient> ingredientFilter,
            JComboBox<Category> categoryFilter,
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
        RecipeFilterAttributes attributes = new RecipeFilterAttributes(
                null,
                (Ingredient) ingredientFilter.getSelectedItem(),
                (Category) categoryFilter.getSelectedItem(),
                (Integer) caloriesMinFilter.getValue(),
                (Integer) caloriesMaxFilter.getValue(),
                (Integer) portionsMinFilter.getValue(),
                (Integer) portionsMaxFilter.getValue()
        );
        recipeTableRowSorter.setRowFilter(new RecipeRowFilter(attributes, false));
        StatisticsUpdater.reload();
    }
}

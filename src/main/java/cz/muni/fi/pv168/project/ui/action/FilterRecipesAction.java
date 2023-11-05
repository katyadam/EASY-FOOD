package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeFilterAttributes;
import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeRowFilter;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.specialComponents.MultiSelectCombobox;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.util.List;

public class FilterRecipesAction extends AbstractAction {

    private final TableRowSorter<RecipeTableModel> recipeTableRowSorter;
    MultiSelectCombobox<Ingredient> ingredientFilter;
    JComboBox<Category> categoryFilter;
    JSpinner caloriesMinFilter;
    JSpinner caloriesMaxFilter;
    JSpinner portionsMinFilter;
    JSpinner portionsMaxFilter;
    JTable recipeTable;

    public FilterRecipesAction(
            MultiSelectCombobox<Ingredient> ingredientFilter,
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
                (List<Ingredient>) ingredientFilter.reap(),
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

package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class FilterRecipesAction extends AbstractAction {

    JComboBox<Ingredient> ingredientFilter;
    JComboBox<String> categoryFilter;
    JSpinner caloriesMinFilter;
    JSpinner caloriesMaxFilter;
    JSpinner portionsMinFilter;
    JSpinner portionsMaxFilter;
    JTable recipeTable;

    public FilterRecipesAction(
            JComboBox<Ingredient> ingredientFilter,
            JComboBox<String> categoryFilter,
            JSpinner caloriesMinFilter,
            JSpinner caloriesMaxFilter,
            JSpinner portionsMinFilter,
            JSpinner portionsMaxFilter,
            JTable recipeTable
    ) {
        super("", Icons.FILTER_ICON);
        this.ingredientFilter = ingredientFilter;
        this.categoryFilter = categoryFilter;
        this.caloriesMinFilter = caloriesMinFilter;
        this.caloriesMaxFilter = caloriesMaxFilter;
        this.portionsMinFilter = portionsMinFilter;
        this.portionsMaxFilter = portionsMaxFilter;
        this.recipeTable = recipeTable;

        putValue(SHORT_DESCRIPTION, "Filter recipes");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println();
        return;
    }
}

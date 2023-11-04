package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeRowFilter;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;

public class RemoveRecipesFilterAction extends AbstractAction {

    private final TableRowSorter<RecipeTableModel> recipeTableRowSorter;

    public RemoveRecipesFilterAction(TableRowSorter<RecipeTableModel> recipeTableRowSorter) {
        super("", Icons.DELETE_ICON);
        this.recipeTableRowSorter = recipeTableRowSorter;

        putValue(SHORT_DESCRIPTION, "Remove filter");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        recipeTableRowSorter.setRowFilter(new RecipeRowFilter(null, true));
        StatisticsUpdater.reload();
    }
}

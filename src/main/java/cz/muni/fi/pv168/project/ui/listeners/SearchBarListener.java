package cz.muni.fi.pv168.project.ui.listeners;

import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeFilterAttributes;
import cz.muni.fi.pv168.project.ui.filters.recipes.RecipeRowFilter;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SearchBarListener extends KeyAdapter {
    private final JTextField searchBar;
    private final TableRowSorter<RecipeTableModel> recipeTableRowSorter;

    public SearchBarListener(
            JTextField searchBar,
            TableRowSorter<RecipeTableModel> recipeTableRowSorter
    ) {
        this.searchBar = searchBar;
        this.recipeTableRowSorter = recipeTableRowSorter;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        super.keyReleased(e);
        RecipeFilterAttributes attributes = new RecipeFilterAttributes(
                searchBar.getText().isEmpty() ? null : searchBar.getText(),
                null,
                null,
                null,
                null,
                null,
                null
        );
        if (searchBar.getText().isEmpty()) {
            recipeTableRowSorter.setRowFilter(new RecipeRowFilter(attributes, true));
        } else {
            recipeTableRowSorter.setRowFilter(new RecipeRowFilter(attributes, false));
        }
    }
}

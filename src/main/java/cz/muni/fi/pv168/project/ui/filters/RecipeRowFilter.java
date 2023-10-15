package cz.muni.fi.pv168.project.ui.filters;

import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;

public class RecipeRowFilter extends RowFilter<RecipeTableModel, Integer> {

    private final RecipeMatcher matcher;

    public RecipeRowFilter(FilterAttributes filterAttributes) {
        this.matcher = new RecipeMatcher(filterAttributes);
    }

    @Override
    public boolean include(Entry<? extends RecipeTableModel, ? extends Integer> entry) {
        RecipeTableModel tableModel = entry.getModel();

        return matcher.evaluate(tableModel.getEntity(entry.getIdentifier()));
    }
}

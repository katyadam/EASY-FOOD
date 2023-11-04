package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.filters.ingredients.IngredientFilterAttributes;
import cz.muni.fi.pv168.project.ui.filters.ingredients.IngredientRowFilter;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;

public class FilterIngredientsAction extends AbstractAction {

    private final TableRowSorter<IngredientTableModel> ingredientTableSorter;
    private final JSpinner caloriesMinFilter;
    private final JSpinner caloriesMaxFilter;

    public FilterIngredientsAction(
            TableRowSorter<IngredientTableModel> ingredientTableSorter,
            JSpinner caloriesMinFilter,
            JSpinner caloriesMaxFilter
    ) {
        super("", Icons.FILTER_ICON);
        this.ingredientTableSorter = ingredientTableSorter;
        this.caloriesMinFilter = caloriesMinFilter;
        this.caloriesMaxFilter = caloriesMaxFilter;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        IngredientFilterAttributes attributes = new IngredientFilterAttributes(
                (Integer) caloriesMinFilter.getValue(),
                (Integer) caloriesMaxFilter.getValue()
        );

        ingredientTableSorter.setRowFilter(new IngredientRowFilter(attributes));
        StatisticsUpdater.reload();
    }
}

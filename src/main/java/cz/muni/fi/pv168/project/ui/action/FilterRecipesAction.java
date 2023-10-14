package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * @author Filip Skvara
 */
public class FilterRecipesAction extends AbstractAction {

    JComboBox<Ingredient> ingredeintFilter;
    JComboBox<String> categoryFilter;
    JSpinner caloriesMinFilter;
    JSpinner caloriesMaxFilter;
    JSpinner portionsMinFilter;
    JSpinner portionsMaxFilter;

    public FilterRecipesAction(JComboBox<Ingredient> ingredeintFilter,
                               JComboBox<String> categoryFilter,
                               JSpinner caloriesMinFilter,
                               JSpinner caloriesMaxFilter,
                               JSpinner portionsMinFilter,
                               JSpinner portionsMaxFilter) {
        super("",Icons.FILTER_ICON);
        this.ingredeintFilter = ingredeintFilter;
        this.categoryFilter = categoryFilter;
        this.caloriesMinFilter = caloriesMinFilter;
        this.caloriesMaxFilter = caloriesMaxFilter;
        this.portionsMinFilter = portionsMinFilter;
        this.portionsMaxFilter = portionsMaxFilter;

        putValue(SHORT_DESCRIPTION, "Adds new recipe");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        return;
    }
}

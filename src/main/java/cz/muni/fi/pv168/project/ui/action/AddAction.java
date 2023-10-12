package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class AddAction extends ContextAction {
    private int activeTab = 0;

//    private final ListModel<Recipe> departmentListModel;

    public AddAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable) {
        super(recipeTable, ingredientTable, unitsTable, "Add", Icons.ADD_ICON);


//        this.departmentListModel = departmentListModel;
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    public void setActiveTab(int i) {
        activeTab = i;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (activeTab) {
            case 0:
                RecipeTableModel recipeTableModel = (RecipeTableModel) recipeTable.getModel();
                RecipeDialog recipeDialog = new RecipeDialog(null, (IngredientTableModel) ingredientTable.getModel());
                recipeDialog.show(recipeTable, "Add Employee")
                        .ifPresent(recipeTableModel::addRow);
                break;
            case 1:
                IngredientTableModel ingredientTableModel = (IngredientTableModel) ingredientTable.getModel();
                IngredientDialog ingredientDialog = new IngredientDialog(null);
                ingredientDialog.show(ingredientTable, "Add ingredient")
                        .ifPresent(ingredientTableModel::addRow);
                break;
            case 2:
        }

    }
}

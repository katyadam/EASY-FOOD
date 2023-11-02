package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.CustomUnitDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class AddAction extends ContextAction {


    public AddAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable, JTable categoryTable) {
        super(recipeTable, ingredientTable, unitsTable, categoryTable, "Add", Icons.ADD_ICON);
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (activeTab) {
            case 0:
                RecipeTableModel recipeTableModel = (RecipeTableModel) recipeTable.getModel();
                RecipeDialog recipeDialog = new RecipeDialog(null, (IngredientTableModel) ingredientTable.getModel(), (CategoryTableModel) categoryTable.getModel());
                recipeDialog.show(recipeTable, "Add Recipe")
                        .ifPresent(recipeTableModel::addRow);
                break;
            case 1:
                IngredientTableModel ingredientTableModel = (IngredientTableModel) ingredientTable.getModel();
                IngredientDialog ingredientDialog = new IngredientDialog(null, (RecipeTableModel) recipeTable.getModel());
                ingredientDialog.show(ingredientTable, "Add ingredient")
                        .ifPresent(ingredientTableModel::addRow);
                break;
            case 2:
                CustomUnitTableModel customUnitTableModel = (CustomUnitTableModel) unitsTable.getModel();
                CustomUnitDialog customUnitDialog = new CustomUnitDialog(null);
                customUnitDialog.show(unitsTable, "Add Custom Unit")
                        .ifPresent(customUnitTableModel::addRow);
                break;
            case 3:
                CategoryTableModel categoryTableModel = (CategoryTableModel) categoryTable.getModel();
                CategoryDialog categoryDialog = new CategoryDialog(null);
                categoryDialog.show(categoryTable, "Add Category")
                        .ifPresent(categoryTableModel::addRow);
        }

    }
}

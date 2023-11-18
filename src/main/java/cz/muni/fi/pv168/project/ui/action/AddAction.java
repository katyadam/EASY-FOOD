package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.ui.dialog.CategoryDialog;
import cz.muni.fi.pv168.project.ui.dialog.CustomUnitDialog;
import cz.muni.fi.pv168.project.ui.dialog.IngredientDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;

public final class AddAction extends ContextAction {


    public AddAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable, JTable categoryTable) {
        super(recipeTable, ingredientTable, unitsTable, categoryTable, "Add", Icons.ADD_ICON);
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (TabbedPanelContext.getActiveTab()) {
            case 0 -> {
                RecipeTableModel recipeTableModel = (RecipeTableModel) recipeTable.getModel();
                RecipeDialog recipeDialog = new RecipeDialog(null,
                        (RecipeTableModel) recipeTable.getModel(),
                        (IngredientTableModel) ingredientTable.getModel(),
                        (CategoryTableModel) categoryTable.getModel(),
                        (CustomUnitTableModel) unitsTable.getModel());
                Optional<Recipe> newRecipe =  recipeDialog.show(recipeTable, "Add Recipe");
                if (newRecipe.isPresent()) {
                    Recipe recipe = newRecipe.get();
                    if ( recipe.getCategory() != null) {
                        recipe.getCategory().addRecipe(recipe);
                    }
                    for (AddedIngredient addedIngredient: recipe.getUsedIngredients().getEntities()) {
                        addedIngredient.getIngredient().addRecipe(recipe);
                        if ( addedIngredient.getUnit() instanceof Entity) {
                            ((Entity) addedIngredient.getUnit()).addRecipe(recipe);
                        }
                    }
                    recipeTableModel.addRow(recipe);
                }
            }
            case 1 -> {
                IngredientTableModel ingredientTableModel = (IngredientTableModel) ingredientTable.getModel();
                IngredientDialog ingredientDialog = new IngredientDialog(null,(IngredientTableModel) ingredientTable.getModel() ,(RecipeTableModel) recipeTable.getModel());
                ingredientDialog.show(ingredientTable, "Add ingredient")
                        .ifPresent(ingredientTableModel::addRow);
            }
            case 2 -> {
                CustomUnitTableModel customUnitTableModel = (CustomUnitTableModel) unitsTable.getModel();
                CustomUnitDialog customUnitDialog = new CustomUnitDialog(null, (CustomUnitTableModel) unitsTable.getModel());
                customUnitDialog.show(unitsTable, "Add Custom Unit")
                        .ifPresent(customUnitTableModel::addRow);
            }
            case 3 -> {
                CategoryTableModel categoryTableModel = (CategoryTableModel) categoryTable.getModel();
                CategoryDialog categoryDialog = new CategoryDialog(null, (CategoryTableModel) categoryTable.getModel());
                categoryDialog.show(categoryTable, "Add Category")
                        .ifPresent(categoryTableModel::addRow);
            }
        }
        StatisticsUpdater.reload();

    }
}

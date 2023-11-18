package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.Entity;
import cz.muni.fi.pv168.project.business.model.Ingredient;
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

public final class EditAction extends ContextAction {


    public EditAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable, JTable categoryTable) {
        super(recipeTable, ingredientTable, unitsTable, categoryTable, "Edit", Icons.EDIT_ICON);
        putValue(SHORT_DESCRIPTION, "Edits selected recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JTable activeTable = TabbedPanelContext.getActiveTable();
        int[] selectedRows = activeTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (activeTable.isEditing()) {
            activeTable.getCellEditor().cancelCellEditing();
        }
        switch (TabbedPanelContext.getActiveTab()) {
            case 0: {
                RecipeTableModel recipeTableModel = (RecipeTableModel) activeTable.getModel();
                int modelRow = activeTable.convertRowIndexToModel(selectedRows[0]);
                Recipe recipe = recipeTableModel.getEntity(modelRow);
                RecipeDialog dialog = new RecipeDialog(recipe,
                        (RecipeTableModel) recipeTable.getModel(),
                        (IngredientTableModel) ingredientTable.getModel(),
                        (CategoryTableModel) categoryTable.getModel(),
                        (CustomUnitTableModel) unitsTable.getModel());
                Optional<Recipe> optionalRecipe = dialog.show(recipeTable, "Edit Recipe");
                if (optionalRecipe.isPresent()) {
                    Recipe newRecipe = optionalRecipe.get();
                    newRecipe.getCategory().addRecipe(newRecipe);
                    for (AddedIngredient addedIngredient : newRecipe.getUsedIngredients().getEntities()) {
                        addedIngredient.getIngredient().addRecipe(newRecipe);
                        if (addedIngredient.getUnit() instanceof Entity) {
                            ((Entity) addedIngredient.getUnit()).addRecipe(newRecipe);
                        }
                    }
                    recipeTableModel.updateRow(newRecipe);
                }
                break;
            }
            case 1: {
                IngredientTableModel ingredientTableModel = (IngredientTableModel) activeTable.getModel();
                int modelRow = activeTable.convertRowIndexToModel(selectedRows[0]);
                Ingredient ingredient = ingredientTableModel.getEntity(modelRow);
                IngredientDialog dialog = new IngredientDialog(ingredient,(IngredientTableModel) ingredientTable.getModel(), (RecipeTableModel) recipeTable.getModel());
                dialog.show(activeTable, "Edit Ingredient")
                        .ifPresent(ingredientTableModel::updateRow);
                break;
            }
            case 2: {
                CustomUnitTableModel customUnitTableModel = (CustomUnitTableModel) activeTable.getModel();
                int modelRow = activeTable.convertRowIndexToModel(selectedRows[0]);
                Unit unit = customUnitTableModel.getEntity(modelRow);
                CustomUnitDialog dialog = new CustomUnitDialog(unit, (CustomUnitTableModel) unitsTable.getModel());
                dialog.show(activeTable, "Edit Custom Unit")
                        .ifPresent(customUnitTableModel::updateRow);
                break;
            }
            case 3: {
                CategoryTableModel categoryTableModel = (CategoryTableModel) activeTable.getModel();
                int modelRow = activeTable.convertRowIndexToModel(selectedRows[0]);
                Category category = categoryTableModel.getEntity(modelRow);
                CategoryDialog categoryDialog = new CategoryDialog(category, (CategoryTableModel) categoryTable.getModel());
                categoryDialog.show(activeTable, "Edit Category")
                        .ifPresent(categoryTableModel::updateRow);
            }
        }
        StatisticsUpdater.reload();

    }
}

package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class EditAction extends ContextAction {


    public EditAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable) {
        super(recipeTable, ingredientTable, unitsTable, "Edit", Icons.EDIT_ICON);

//        this.departmentListModel = departmentListModel; might use similar for ingredients
        putValue(SHORT_DESCRIPTION, "Edits selected recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_E);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl E"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int[] selectedRows = recipeTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (recipeTable.isEditing()) {
            recipeTable.getCellEditor().cancelCellEditing();
        }
        RecipeTableModel employeeTableModel = (RecipeTableModel) recipeTable.getModel();
        int modelRow = recipeTable.convertRowIndexToModel(selectedRows[0]);
        Recipe recipe = employeeTableModel.getEntity(modelRow);
        RecipeDialog dialog = new RecipeDialog(recipe, (IngredientTableModel) ingredientTable.getModel());
        dialog.show(recipeTable, "Edit Employee")
                .ifPresent(employeeTableModel::updateRow);
    }
}

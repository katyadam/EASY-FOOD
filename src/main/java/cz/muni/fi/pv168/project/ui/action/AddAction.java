package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.data.TestDataGenerator;

import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.dialog.RecipeDialog;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.AbstractAction;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public final class AddAction extends AbstractAction {

    private final JTable recipeTable;
    private final TestDataGenerator testDataGenerator;
//    private final ListModel<Recipe> departmentListModel;

    public AddAction(JTable recipeTable, TestDataGenerator testDataGenerator) {
        super("Add", Icons.ADD_ICON);
        this.recipeTable = recipeTable;
        this.testDataGenerator = testDataGenerator;
//        this.departmentListModel = departmentListModel;
        putValue(SHORT_DESCRIPTION, "Adds new recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl N"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        RecipeTableModel employeeTableModel = (RecipeTableModel) recipeTable.getModel();
        RecipeDialog dialog = new RecipeDialog(testDataGenerator.createTestRecipe());
        dialog.show(recipeTable, "Add Employee")
                .ifPresent(employeeTableModel::addRow);
    }
}

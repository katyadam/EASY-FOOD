package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.model.AbstractEntityTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;

public final class DeleteAction extends ContextAction {


    public DeleteAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable) {
        super(recipeTable, ingredientTable, unitsTable, "Delete", Icons.DELETE_ICON);

        putValue(SHORT_DESCRIPTION, "Deletes selected items");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JTable activeTable = getActiveTable();
        AbstractEntityTableModel tableModel = (AbstractEntityTableModel) activeTable.getModel();
        Arrays.stream(activeTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(activeTable::convertRowIndexToModel)
                .boxed()
                // We need to delete rows in descending order to not change index of rows
                // which are not deleted yet
                .sorted(Comparator.reverseOrder())
                .forEach(tableModel::deleteRow);
    }
}

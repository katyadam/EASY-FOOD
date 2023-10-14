package cz.muni.fi.pv168.project.ui.action;

import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

/**
 * @author Adam Juhas
 */
public class ShowAction extends ContextAction{
    public ShowAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable) {
        super(recipeTable, ingredientTable, unitsTable, "Show Recipe", Icons.SHOW_ICON);
        putValue(SHORT_DESCRIPTION, "Shows recipe");
        putValue(MNEMONIC_KEY, KeyEvent.VK_S);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl S"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JTable activeTable = getActiveTable();
        int[] selectedRows = activeTable.getSelectedRows();
        if (selectedRows.length != 1) {
            throw new IllegalStateException("Invalid selected rows count (must be 1): " + selectedRows.length);
        }
        if (activeTable.isEditing()) {
            activeTable.getCellEditor().cancelCellEditing();
        }

    }
}

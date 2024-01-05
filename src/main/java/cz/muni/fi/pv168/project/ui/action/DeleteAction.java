package cz.muni.fi.pv168.project.ui.action;


import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.ui.model.AbstractEntityTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public final class DeleteAction extends ContextAction {


    public DeleteAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable, JTable categoryTable) {
        super(recipeTable, ingredientTable, unitsTable, categoryTable, "Delete", Icons.DELETE_ICON);

        putValue(SHORT_DESCRIPTION, "Deletes selected items");
        putValue(MNEMONIC_KEY, KeyEvent.VK_D);
        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke("ctrl D"));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JTable activeTable = TabbedPanelContext.getActiveTable();
        int confirm = JOptionPane.showOptionDialog(activeTable,"Confirm",
                "Delete confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE,null,null,null);
        if ( confirm != JOptionPane.OK_OPTION) {
            return;
        }
        System.out.println(activeTable.getRowCount());
        AbstractEntityTableModel tableModel = (AbstractEntityTableModel) activeTable.getModel();
        List<Integer> indexes = Arrays.stream(activeTable.getSelectedRows())
                // view row index must be converted to model row index
                .map(activeTable::convertRowIndexToModel)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .toList();
        if (tableModel instanceof RecipeTableModel) {
            for (Integer i : indexes) {
                ((Recipe) tableModel.getEntity(i)).destroy();
                tableModel.deleteRow(i);
            }
        } else {
        StringBuilder builder = new StringBuilder();
        for (Integer i : indexes) {
            if (tableModel.getEntity(i).usedCount() > 0) {
                builder.append("Deletion denied for ")
                        .append(tableModel.getEntity(i).getName())
                        .append(" used in recipes:\n");
                tableModel.getEntity(i.intValue()).getRecipes().stream().forEach(y -> builder.append(" -> ").append(y).append(System.lineSeparator()));
                JOptionPane.showMessageDialog(activeTable, builder.toString());

                builder.setLength(0);
            } else {
                tableModel.deleteRow(i);
            }
        }
        }
    }
}

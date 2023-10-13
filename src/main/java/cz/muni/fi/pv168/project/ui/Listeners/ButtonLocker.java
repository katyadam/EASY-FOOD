package cz.muni.fi.pv168.project.ui.Listeners;

import cz.muni.fi.pv168.project.ui.action.ActionFactory;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author Filip Skvara
 */
public class ButtonLocker implements ListSelectionListener {

    ActionFactory buttons;
    JTable table;

    public ButtonLocker(ActionFactory buttons, JTable table) {
        this.buttons = buttons;
        this.table = table;
    }
    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selected = table.getSelectedRowCount();
        buttons.getDeleteAction().setEnabled(selected > 0);
        buttons.getEditAction().setEnabled(selected == 1);
    }
    public static void reload(ActionFactory buttons, JTable table) {
        int selected = table.getSelectedRowCount();
        buttons.getDeleteAction().setEnabled(selected > 0);
        buttons.getEditAction().setEnabled(selected == 1);
    }
}
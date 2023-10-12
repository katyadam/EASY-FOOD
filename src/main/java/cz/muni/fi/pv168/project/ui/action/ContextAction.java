package cz.muni.fi.pv168.project.ui.action;

import javax.swing.*;

/**
 * @author Filip Skvara
 */
abstract public class ContextAction extends AbstractAction {
    protected final JTable recipeTable;
    protected final JTable ingredientTable;
    protected final JTable unitsTable;

    private int activeTab = 0;

    public ContextAction(JTable recipeTable, JTable ingredientTable, JTable unitsTable, String name, Icon icon) {
        super(name, icon);
        this.recipeTable = recipeTable;
        this.ingredientTable = ingredientTable;
        this.unitsTable = unitsTable;
    }

    public void setActiveTab(int i) {
        activeTab = i;
    }
}

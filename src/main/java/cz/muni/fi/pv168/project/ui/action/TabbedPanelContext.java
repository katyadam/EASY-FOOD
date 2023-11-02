package cz.muni.fi.pv168.project.ui.action;

import javax.swing.*;

/**
 * @author Filip Skvara
 */
public class TabbedPanelContext {
    private static int activeTab = 0;
    private static JTable recipeTable;
    private static JTable ingredientTable;
    protected static JTable unitsTable;
    protected static JTable categoryTable;


    public static void setTables( JTable rt, JTable it, JTable ut, JTable ct){
        recipeTable = rt;
        ingredientTable = it;
        unitsTable = ut;
        categoryTable = ct;
    }

    public static void setActiveTab(int i) {
        activeTab = i;
    }

    public static JTable getActiveTable() {
        switch (activeTab) {
            case 0:
                return recipeTable;
            case 1:
                return ingredientTable;
            case 2:
                return unitsTable;
            default:
                return categoryTable;
        }
    }
    public static int getActiveTab(){
        return activeTab;
    }

}

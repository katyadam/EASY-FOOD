package cz.muni.fi.pv168.project;

import javax.swing.*;

public class GUILayout {
    private JPanel mainPanel;
    private JTextField searchRecipesTextField;
    private JTabbedPane tabbedPanels;
    private JComboBox comboBoxFilters;
    private JComboBox comboBoxCategories;
    private JList recipeList;
    private JList ingredientList;
    private JList unitList;
    private JList categoryList;
    private JPanel recipeTab;
    private JPanel ingredientTab;
    private JPanel unitTab;
    private JPanel categoryTab;
    private JToolBar toolBarTop;
    private JPanel sidePanel;




    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JTextField getSearchRecipesTextField() {
        return searchRecipesTextField;
    }

    public JList getRecipeList() {
        return recipeList;
    }

    public JList getIngerdientList() {
        return ingredientList;
    }

    public JList getUnitList() {
        return unitList;
    }

    public JList getCategoryList() {
        return categoryList;
    }



    public JTabbedPane getTabbedPanels() {
        return tabbedPanels;
    }

    public JComboBox getComboBoxFilters() {
        return comboBoxFilters;
    }

    public JComboBox getComboBoxCategories() {
        return comboBoxCategories;
    }

    public JToolBar getToolBarTop() {
        return toolBarTop;
    }

    public JPanel getSidePanel() {
        return sidePanel;
    }
}


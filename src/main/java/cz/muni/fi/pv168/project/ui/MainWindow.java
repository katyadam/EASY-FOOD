package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.GUILayout;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.action.ActionFactory;
import cz.muni.fi.pv168.project.ui.action.ContextAction;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;


public class MainWindow {

    private final JFrame frame;
    private final GUILayout layout;

    private boolean isCleared = false;

    private final ActionFactory actions;

    private final List<Recipe> recipesList;
    private final List<Ingredient> ingredientList;
    private final List<CustomUnit> customUnitList;

    private final JTable recipeTable;
    private final JTable ingredientTable;
    private final JTable customUnitTable;

    private final JScrollPane recipeScroll;
    private final JScrollPane ingredientScroll;
    private final JScrollPane customUnitScroll;

    private JMenuBar menuBar;

    private TestDataGenerator testDataGen = new TestDataGenerator();

    public MainWindow() {
        this.recipesList = testDataGen.getTestRecipes();
        this.ingredientList = testDataGen.getTestIngredients();
        this.customUnitList = testDataGen.getTestCustomUnits();

        this.recipeTable = createRecipeTable(recipesList);
        this.ingredientTable = createIngredientTable(ingredientList);
        this.customUnitTable = createCustomUnitTable(customUnitList);


        this.actions = new ActionFactory(recipeTable, ingredientTable, customUnitTable);

        this.recipeScroll = new JScrollPane(recipeTable);
        this.ingredientScroll = new JScrollPane(ingredientTable);
        this.customUnitScroll = new JScrollPane(customUnitTable);

        this.layout = new GUILayout();
        this.menuBar = createMenuBar();
        this.frame = createFrame();


        setActiveButtons();
        layout.getTabbedPanels().add("Recipes", recipeScroll);
        layout.getTabbedPanels().add("Ingredients", ingredientScroll);
        layout.getTabbedPanels().add("Custom Units", customUnitScroll);
        layout.getTabbedPanels().addChangeListener(new TabbedChange());

        // removes text from Search Bar after typing
        layout.getSearchRecipesTextField().addFocusListener(new ClearTextFieldKeyListener());

        JToolBar statistics = (JToolBar) layout.getMainPanel().getComponent(3);
        recipeTable.getModel().addTableModelListener(new StatisticsUpdater(recipeTable,0,"Total recipes: ",statistics));
        ingredientTable.getModel().addTableModelListener(new StatisticsUpdater(recipeTable,2,"Total ingredients: ",statistics));
        ingredientTable.getModel().addTableModelListener(new StatisticsUpdater(recipeTable,4,"Total units: ",statistics));

        ((JLabel) statistics.getComponent(0))
                .setText("Total recipes: " + recipeTable.getModel().getRowCount());
        ((JLabel) statistics.getComponent(2))
                .setText("Total ingredients: " + ingredientTable.getModel().getRowCount());
        ((JLabel) statistics.getComponent(4))
                .setText("Total units: " + customUnitTable.getModel().getRowCount());
    }
    private class StatisticsUpdater implements TableModelListener{
        private JTable table;
        private int componentIndex;
        private String text;
        private JToolBar statistics;
        public StatisticsUpdater(JTable table, int componentIndex, String text,JToolBar statistics) {
            this.table = table;
            this.componentIndex = componentIndex;
            this.text = text;
            this.statistics = statistics;
        }
        @Override
        public void tableChanged(TableModelEvent e) {
            ((JLabel) statistics.getComponent(componentIndex))
                    .setText(text + table.getModel().getRowCount());
        }
    }


    private class TabbedChange implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            ContextAction.setActiveTab(layout.getTabbedPanels().getSelectedIndex());
        }
    }


    private class ClearTextFieldKeyListener extends FocusAdapter {
        @Override
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            layout.getSearchRecipesTextField().setText("");
        }
    }

    private void setActiveButtons() {
        layout.getAddButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getAddButton().setAction(actions.getAddAction());

        layout.getEditButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getEditButton().setAction(actions.getEditAction());

        layout.getDeleteButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getDeleteButton().setAction(actions.getDeleteAction());

        layout.getTabbedPanels().setCursor(new Cursor(Cursor.HAND_CURSOR));

    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Easy Food");
        frame.add(menuBar, BorderLayout.NORTH);
        frame.add(layout.getMainPanel(), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        return frame;
    }

    private JTable createRecipeTable(List<Recipe> recipes) {
        RecipeTableModel model = new RecipeTableModel(recipes);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
//        JComboBox genderComboBox = new JComboBox<>(Gender.values());
//        table.setDefaultEditor(Gender.class, new DefaultCellEditor(genderComboBox));
        return table;
    }

    private JTable createCustomUnitTable(List<CustomUnit> customUnitList) {
        CustomUnitTableModel model = new CustomUnitTableModel(customUnitList);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);

        return table;
    }

    private JTable createIngredientTable(List<Ingredient> ingredientList) {
        IngredientTableModel model = new IngredientTableModel(ingredientList);
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        return table;
    }

    private JPopupMenu createEmployeeTablePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(actions.getDeleteAction());
        menu.add(actions.getEditAction());
        return menu;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu editMenu = new JMenu("Edit");
        editMenu.setMnemonic('e');
        editMenu.add(actions.getAddAction());
        editMenu.addSeparator();
        editMenu.add(actions.getQuitAction());

        JMenu importMenu = new JMenu("Import");
        editMenu.setMnemonic('i');

        JMenu exportMenu = new JMenu("Export");
        editMenu.setMnemonic('x');

        menuBar.add(editMenu);
        menuBar.add(importMenu);
        menuBar.add(exportMenu);

        return menuBar;
    }

    private JToolBar createToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.add(actions.getQuitAction());
        toolbar.addSeparator();
        return toolbar;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        return;
    }
}

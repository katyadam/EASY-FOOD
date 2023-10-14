package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.GUILayout;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.Listeners.ButtonLocker;
import cz.muni.fi.pv168.project.ui.Listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.action.ActionFactory;
import cz.muni.fi.pv168.project.ui.action.ContextAction;
import cz.muni.fi.pv168.project.ui.action.FilterIngredientsAction;
import cz.muni.fi.pv168.project.ui.action.FilterRecipesAction;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;


public class MainWindow {

    private final JFrame frame;
    private final GUILayout layout;

    private boolean isCleared = false;

    private final ActionFactory actions;

    private List<Recipe> recipesList;
    private List<Ingredient> ingredientList;
    private List<CustomUnit> customUnitList;

    private JTable recipeTable;
    private JTable ingredientTable;
    private JTable customUnitTable;

    private JScrollPane recipeScroll;
    private JScrollPane ingredientScroll;
    private JScrollPane customUnitScroll;

    private JMenuBar menuBar;

    private JPopupMenu popupMenu;

    private final TestDataGenerator testDataGen = new TestDataGenerator();

    public MainWindow() {
        setDataGeneration();
        createTables();
        createScrollPanes();

        this.actions = new ActionFactory(recipeTable, ingredientTable, customUnitTable);
        this.layout = new GUILayout();
        this.menuBar = createMenuBar();
        this.frame = createFrame();

        setActiveButtons();
        setTabbedPannels();
        setStatistics();
        setButtonListeners();
        setPopUpMenus();

        // removes text from Search Bar after typing
        layout.getSearchRecipesTextField().addFocusListener(new ClearTextFieldKeyListener());
    }

    private void setPopUpMenus() {
        this.popupMenu = createRecipePopupMenu();
        recipeTable.setComponentPopupMenu(popupMenu);
        ingredientTable.setComponentPopupMenu(popupMenu);
        customUnitTable.setComponentPopupMenu(popupMenu);
    }

    private void createScrollPanes() {
        this.recipeScroll = new JScrollPane(recipeTable);
        this.ingredientScroll = new JScrollPane(ingredientTable);
        this.customUnitScroll = new JScrollPane(customUnitTable);
    }

    private void createTables() {
        this.recipeTable = createRecipeTable(recipesList);
        this.ingredientTable = createIngredientTable(ingredientList);
        this.customUnitTable = createCustomUnitTable(customUnitList);
    }

    private void setDataGeneration() {
        this.recipesList = testDataGen.getTestRecipes();
        this.ingredientList = testDataGen.getTestIngredients();
        this.customUnitList = testDataGen.getTestCustomUnits();
    }

    private void setTabbedPannels() {
        layout.getTabbedPanels().add("Recipes", createRecipeTab());
        layout.getTabbedPanels().add("Ingredients", createIngredientsTab());
        layout.getTabbedPanels().add("Custom Units", customUnitScroll);
        layout.getTabbedPanels().addChangeListener(new TabbedChange());
    }

    private void setButtonListeners() {
        recipeTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, recipeTable));
        ingredientTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, ingredientTable));
        customUnitTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, customUnitTable));

        actions.getEditAction().setEnabled(false);
        actions.getDeleteAction().setEnabled(false);
        actions.getShowAction().setEnabled(false);
    }

    private void setStatistics() {
        JToolBar statistics = (JToolBar) layout.getMainPanel().getComponent(3);
        recipeTable.getModel().addTableModelListener(new StatisticsUpdater(recipeTable, 0, "Total recipes: ", statistics));
        ingredientTable.getModel().addTableModelListener(new StatisticsUpdater(recipeTable, 2, "Total ingredients: ", statistics));
        ingredientTable.getModel().addTableModelListener(new StatisticsUpdater(recipeTable, 4, "Total units: ", statistics));

        ((JLabel) statistics.getComponent(0))
                .setText("Total recipes: " + recipeTable.getModel().getRowCount());
        ((JLabel) statistics.getComponent(2))
                .setText("Total ingredients: " + ingredientTable.getModel().getRowCount());
        ((JLabel) statistics.getComponent(4))
                .setText("Total units: " + customUnitTable.getModel().getRowCount());
    }


    private class TabbedChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            ContextAction.setActiveTab(layout.getTabbedPanels().getSelectedIndex());
            ButtonLocker.reload(actions, actions.getAddAction().getActiveTable());
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
        layout.getShowRecipeButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getShowRecipeButton().setAction(actions.getShowAction());

        layout.getAddButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getAddButton().setAction(actions.getAddAction());

        layout.getEditButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getEditButton().setAction(actions.getEditAction());

        layout.getDeleteButton().setCursor(new Cursor(Cursor.HAND_CURSOR));
        layout.getDeleteButton().setAction(actions.getDeleteAction());

        layout.getShowRecipeButton().setIcon(Icons.SHOW_ICON);

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

    private JPopupMenu createRecipePopupMenu() {
        JPopupMenu menu = new JPopupMenu();
        menu.add(actions.getDeleteAction());
        menu.add(actions.getEditAction());
        menu.add(actions.getShowAction());
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

    private JComponent createRecipeTab() {
        JPanel recipePanel = new JPanel(new MigLayout("fillx"));
        JComboBox<Ingredient> ingredientFilter = new JComboBox<>(ingredientList.toArray(new Ingredient[0]));
        JComboBox<String> categoryFilter = new JComboBox<>(new String[0]);
        JSpinner caloriesMinFilter = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 20));
        JSpinner caloriesMaxFilter = new JSpinner(new SpinnerNumberModel(50000, 0, 50000, 20));
        JSpinner portionsMinFilter = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
        JSpinner portionsMaxFilter = new JSpinner(new SpinnerNumberModel(200, 1, 200, 1));
        JLabel ingredients = new JLabel("Ingredients:");
        JLabel categories = new JLabel("Categories:");
        JLabel nutritions = new JLabel("Calories min");
        JLabel max = new JLabel("max");
        JLabel max2 = new JLabel("max");
        JLabel portions = new JLabel("Portions min");
        JButton fireFilter = new JButton(new FilterRecipesAction(ingredientFilter, categoryFilter, caloriesMinFilter, caloriesMaxFilter, portionsMinFilter, portionsMaxFilter, recipeTable));
        recipePanel.add(ingredients);
        recipePanel.add(ingredientFilter);
        recipePanel.add(categories, "gapleft 3%, al right");
        recipePanel.add(categoryFilter, ", gapright 3%");
        recipePanel.add(nutritions, "right");
        recipePanel.add(caloriesMinFilter);
        recipePanel.add(max2, "al left");
        recipePanel.add(caloriesMaxFilter, "al left, gapright 3%");
        recipePanel.add(portions, "al right");
        recipePanel.add(portionsMinFilter);
        recipePanel.add(max, "al left");
        recipePanel.add(portionsMaxFilter, "al left, gapright 25%");
        recipePanel.add(fireFilter, "al right,wrap");
        recipePanel.add(recipeScroll, "span 13, grow, height 99% ");
        return recipePanel;
    }

    private JComponent createIngredientsTab() {
        JPanel ingredientsPanel = new JPanel(new MigLayout("fillx"));
        JSpinner caloriesMinFilter = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 20));
        JSpinner caloriesMaxFilter = new JSpinner(new SpinnerNumberModel(50000, 0, 50000, 20));
        JLabel nutritions = new JLabel("Calories");
        JLabel max = new JLabel("-");
        JButton fireFilter = new JButton(new FilterIngredientsAction());

        ingredientsPanel.add(nutritions, "left");
        ingredientsPanel.add(caloriesMinFilter, "left");
        ingredientsPanel.add(max, "left");
        ingredientsPanel.add(caloriesMaxFilter, "left, gapright 78%");
        ingredientsPanel.add(fireFilter, "right, wrap");
        ingredientsPanel.add(ingredientScroll, "span 5, grow, height 99%");
        return ingredientsPanel;
    }

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        return;
    }
}

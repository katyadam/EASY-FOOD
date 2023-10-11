package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.GUILayout;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.action.ActionFactory;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;


public class MainWindow {

    private final JFrame frame;
    private final GUILayout layout;
    private boolean isCleared = false;

    private final ActionFactory actions;

    private final List<Recipe> recipesList;

    private final JTable recipeTable;

    private final JScrollPane recipeScroll;

    private JMenuBar menuBar;

    private TestDataGenerator testDataGen = new TestDataGenerator();

    public MainWindow() {
        this.layout = new GUILayout();
        this.frame = createFrame();
        this.recipesList = testDataGen.createTestEmployees(10);
        this.recipeTable = createRecipeTable(recipesList);
        this.actions = new ActionFactory(recipeTable, testDataGen);
        this.recipeScroll = new JScrollPane(recipeTable);
        this.menuBar = createMenuBar();

        setActiveButtons();
        layout.getTabbedPanels().add("Recipes", recipeScroll);

        // removes text from Search Bar after typing
        layout.getSearchRecipesTextField().addKeyListener(new ClearTextFieldKeyListener());
    }

    private class ClearTextFieldKeyListener extends KeyAdapter {
        @Override
        public void keyTyped(KeyEvent e) {
            if (!isCleared) {
                layout.getSearchRecipesTextField().setText("");
                isCleared = true;
            }
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

        layout.getComboBoxCategories().setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void show() {
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Easy Food");
        frame.setContentPane(layout.getMainPanel());
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
        menuBar.add(editMenu);
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

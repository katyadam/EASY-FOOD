package cz.muni.fi.pv168.project.ui;

import cz.muni.fi.pv168.project.GUILayout;
import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.model.GuidProvider;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.UuidGuidProvider;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.business.service.crud.CategoryCrudService;
import cz.muni.fi.pv168.project.business.service.crud.UnitService;
import cz.muni.fi.pv168.project.business.service.crud.IngredientCrudService;
import cz.muni.fi.pv168.project.business.service.crud.RecipeCrudService;
import cz.muni.fi.pv168.project.business.service.validation.CategoryValidator;
import cz.muni.fi.pv168.project.business.service.validation.CustomUnitValidator;
import cz.muni.fi.pv168.project.business.service.validation.IngredientValidator;
import cz.muni.fi.pv168.project.business.service.validation.RecipeValidator;
import cz.muni.fi.pv168.project.data.TestDataGenerator;
import cz.muni.fi.pv168.project.storage.memory.InMemoryRepository;
import cz.muni.fi.pv168.project.ui.action.ActionFactory;
import cz.muni.fi.pv168.project.ui.action.ExportAction;
import cz.muni.fi.pv168.project.ui.action.FilterIngredientsAction;
import cz.muni.fi.pv168.project.ui.action.FilterRecipesAction;
import cz.muni.fi.pv168.project.ui.action.ImportAction;
import cz.muni.fi.pv168.project.ui.action.RemoveRecipesFilterAction;
import cz.muni.fi.pv168.project.ui.action.TabbedPanelContext;
import cz.muni.fi.pv168.project.ui.listeners.ButtonLocker;
import cz.muni.fi.pv168.project.ui.listeners.SearchBarListener;
import cz.muni.fi.pv168.project.ui.listeners.StatisticsUpdater;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;
import cz.muni.fi.pv168.project.ui.renderers.ColorRenderer;
import cz.muni.fi.pv168.project.ui.resources.Icons;
import cz.muni.fi.pv168.project.ui.specialComponents.MultiSelectCombobox;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.List;


public class MainWindow {

    private final JFrame frame;
    private final GUILayout layout;

    private final ActionFactory actions;

    private List<Recipe> recipesList;
    private List<Ingredient> ingredientList;
    private List<Unit> unitList;
    private List<Category> categoryList;

    private JTable recipeTable;
    private JTable ingredientTable;
    private JTable customUnitTable;
    private JTable categoryTable;

    private JScrollPane recipeScroll;
    private JScrollPane ingredientScroll;
    private JScrollPane customUnitScroll;
    private JScrollPane categoryScroll;

    private final JMenuBar menuBar;
    private final TestDataGenerator testDataGen = new TestDataGenerator();

    //    MODELS
    private final RecipeTableModel recipeTableModel;
    private final IngredientTableModel ingredientTableModel;
    private final CustomUnitTableModel customUnitTableModel;
    private final CategoryTableModel categoryTableModel;

    //    SORTERS
    private final TableRowSorter<RecipeTableModel> recipeTableSorter;
    private final TableRowSorter<IngredientTableModel> ingredientTableSorter;
    private final TableRowSorter<CustomUnitTableModel> customUnitTableSorter;
    private final TableRowSorter<CategoryTableModel> categoryTableSorter;

    // GuidProviders
    private final GuidProvider uuidProvider = new UuidGuidProvider();

    // Repositories
    private final Repository<Recipe> recipeRepository;
    private final Repository<Category> categoryRepository;
    private final Repository<Ingredient> ingredientRepository;
    private final Repository<Unit> customUnitRepository;

    // CRUD services
    private final RecipeCrudService recipeCrudService;
    private final CategoryCrudService categoryCrudService;
    private final IngredientCrudService ingredientCrudService;
    private final UnitService unitService;

    private MultiSelectCombobox<Ingredient> ingredientsFilter;
    private MultiSelectCombobox<Category> categoriesFilter;

    public MainWindow() {
        setDataGeneration();
        this.recipeRepository = new InMemoryRepository<>(this.recipesList);
        this.recipeCrudService = new RecipeCrudService(recipeRepository, new RecipeValidator(), uuidProvider, addedIngredientCrudService);
        this.recipeTableModel = new RecipeTableModel(this.recipeCrudService);

        this.ingredientRepository = new InMemoryRepository<>(this.ingredientList);
        this.ingredientCrudService = new IngredientCrudService(ingredientRepository, new IngredientValidator(), uuidProvider);
        this.ingredientTableModel = new IngredientTableModel(this.ingredientCrudService);

        this.customUnitRepository = new InMemoryRepository<>(this.unitList);
        this.unitService = new UnitService(customUnitRepository, new CustomUnitValidator(), uuidProvider);
        this.customUnitTableModel = new CustomUnitTableModel(this.unitService);

        this.categoryRepository = new InMemoryRepository<>(this.categoryList);
        this.categoryCrudService = new CategoryCrudService(categoryRepository, new CategoryValidator(), uuidProvider);
        this.categoryTableModel = new CategoryTableModel(this.categoryCrudService);

        this.recipeTableSorter = new TableRowSorter<>(recipeTableModel);
        this.ingredientTableSorter = new TableRowSorter<>(ingredientTableModel);
        this.customUnitTableSorter = new TableRowSorter<>(customUnitTableModel);
        this.categoryTableSorter = new TableRowSorter<>(categoryTableModel);
        createTables();
        createScrollPanes();

        this.actions = new ActionFactory(recipeTable, ingredientTable, customUnitTable, categoryTable);
        this.layout = new GUILayout();
        this.menuBar = createMenuBar();
        this.frame = createFrame();
        setActiveButtons();
        setTabbedPannels();
        setStatistics();
        setButtonListeners();
        setPopUpMenus();

        // removes text from Search Bar after typing
        //JTextField searchBar = layout.getSearchRecipesTextField();
        /*
        seachBar.addFocusListener(new ClearTextFieldKeyListener(seachBar));
        seachBar.addKeyListener(new SearchBarListener<>(seachBar, recipeTableSorter));
        seachBar.addKeyListener(new SearchBarListener<>(seachBar, ingredientTableSorter));
        seachBar.addKeyListener(new SearchBarListener<>(seachBar, customUnitTableSorter));
        seachBar.addKeyListener(new SearchBarListener<>(seachBar, categoryTableSorter));*/

    }

    private void setPopUpMenus() {
        JPopupMenu popupMenu = createRecipePopupMenu();
        recipeTable.setComponentPopupMenu(popupMenu);
        ingredientTable.setComponentPopupMenu(popupMenu);
        customUnitTable.setComponentPopupMenu(popupMenu);
        categoryTable.setComponentPopupMenu(popupMenu);
    }

    private void createScrollPanes() {
        this.recipeScroll = new JScrollPane(recipeTable);
        this.ingredientScroll = new JScrollPane(ingredientTable);
        this.customUnitScroll = new JScrollPane(customUnitTable);
        this.categoryScroll = new JScrollPane(categoryTable);
    }

    private void createTables() {
        this.recipeTable = createRecipeTable();
        this.ingredientTable = createIngredientTable();
        this.customUnitTable = createCustomUnitTable();
        this.categoryTable = createCategoryTable();
        recipeTable.getTableHeader().setReorderingAllowed(false);
        ingredientTable.getTableHeader().setReorderingAllowed(false);
        customUnitTable.getTableHeader().setReorderingAllowed(false);
        categoryTable.getTableHeader().setReorderingAllowed(false);
        TabbedPanelContext.setTables(this.recipeTable, this.ingredientTable, this.customUnitTable, this.categoryTable);
    }

    private void setDataGeneration() {
        this.recipesList = testDataGen.getRecipes();
        this.ingredientList = testDataGen.getIngredients();
        this.unitList = testDataGen.getCustomUnits();
        this.categoryList = testDataGen.getCategories();
    }

    private void setTabbedPannels() {
        layout.getTabbedPanels().add("Recipes", createRecipeTab());
        layout.getTabbedPanels().add("Ingredients", createIngredientsTab());
        layout.getTabbedPanels().add("Custom Units", createUnitsTab());
        layout.getTabbedPanels().add("Categories", createCategoryTab());
        layout.getTabbedPanels().addChangeListener(new TabbedChange());
    }

    private void setButtonListeners() {
        recipeTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, recipeTable));
        ingredientTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, ingredientTable));
        customUnitTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, customUnitTable));
        categoryTable.getSelectionModel().addListSelectionListener(new ButtonLocker(actions, categoryTable));

        actions.getEditAction().setEnabled(false);
        actions.getDeleteAction().setEnabled(false);
        actions.getShowAction().setEnabled(false);
    }

    private void setStatistics() {
        JLabel statistics = (JLabel) ((JToolBar) layout.getMainPanel().getComponent(2)).getComponent(0);
        StatisticsUpdater.setLabel(statistics);
        statistics.setText("Showing recipes " + recipeTable.getRowCount() + " out of " + recipeTable.getModel().getRowCount());
    }


    private class TabbedChange implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            TabbedPanelContext.setActiveTab(layout.getTabbedPanels().getSelectedIndex());
            ButtonLocker.reload(actions, TabbedPanelContext.getActiveTable());
            StatisticsUpdater.reload();
            ingredientsFilter.reload(ingredientCrudService.findAll());
        }
    }


    private class ClearTextFieldKeyListener extends FocusAdapter {
        private final JTextField bar;

        public ClearTextFieldKeyListener(JTextField field) {
            bar = field;
        }

        @Override
        public void focusGained(FocusEvent e) {
            super.focusGained(e);
            bar.setText("");
        }

        @Override
        public void focusLost(FocusEvent e) {
            super.focusLost(e);
            if (bar.getText().isEmpty()) {
                bar.setText("Search...");
            }
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
        frame.setMinimumSize(new Dimension(1100, 500));
        frame.setSize(1300, 600);
        return frame;
    }

    private JTable createRecipeTable() {
        JTable table = new JTable(this.recipeTableModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowSorter(recipeTableSorter);
        table.getColumnModel().getColumn(2).setMaxWidth(50);
        TableColumn colorColumn = table.getColumnModel().getColumn(2);
        colorColumn.setCellRenderer(new ColorRenderer());
        return table;
    }

    private JTable createCustomUnitTable() {
        JTable table = new JTable(this.customUnitTableModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowSorter(customUnitTableSorter);
        return table;
    }

    private JTable createCategoryTable() {
        JTable table = new JTable(this.categoryTableModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowSorter(categoryTableSorter);
        TableColumn colorColumn = table.getColumnModel().getColumn(1);
        colorColumn.setCellRenderer(new ColorRenderer());

        return table;
    }

    private JTable createIngredientTable() {
        JTable table = new JTable(this.ingredientTableModel);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        table.setRowSorter(ingredientTableSorter);
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
        //menuBar.setLayout(new MigLayout("","[]20[]20[]"));
        editMenu.setMnemonic('e');
        editMenu.add(actions.getAddAction());
        editMenu.addSeparator();
        editMenu.add(actions.getEditAction());
        editMenu.addSeparator();
        editMenu.add(actions.getDeleteAction());
        editMenu.addSeparator();
        editMenu.add(actions.getQuitAction());

        JMenu filesMenu = new JMenu("Files");
        filesMenu.add(new ImportAction(
                "Import",
                categoryCrudService,
                unitService,
                ingredientCrudService,
                recipeCrudService,
                this::refresh
        ));
        filesMenu.addSeparator();
        filesMenu.add(new ExportAction("Export",
                categoryCrudService, unitService, ingredientCrudService, recipeCrudService));

        menuBar.add(editMenu);
        menuBar.add(filesMenu);

        return menuBar;
    }

    private JComponent createRecipeTab() {
        JPanel recipePanel = new JPanel(new MigLayout("fillx, insets 2"));
        ingredientsFilter = new MultiSelectCombobox<>(ingredientList, "Ingredients");
        categoriesFilter = new MultiSelectCombobox<>(categoryList, "Categories");
        JSpinner caloriesMinFilter = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 20));
        JSpinner caloriesMaxFilter = new JSpinner(new SpinnerNumberModel(50000, 0, 50000, 20));
        JSpinner portionsMinFilter = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
        JSpinner portionsMaxFilter = new JSpinner(new SpinnerNumberModel(200, 1, 200, 1));
        JLabel categories = new JLabel("Categories:");
        JLabel nutrition = new JLabel("Calories min");
        JLabel max = new JLabel("max");
        JLabel max2 = new JLabel("max");
        JLabel portions = new JLabel("Portions min");
        JTextField searchBar = new JTextField("Search...");
        searchBar.addFocusListener(new ClearTextFieldKeyListener(searchBar));
        searchBar.addKeyListener(new SearchBarListener<>(searchBar, recipeTableSorter));
        JButton fireFilter = new JButton(new FilterRecipesAction(
                ingredientsFilter,
                categoriesFilter,
                caloriesMinFilter,
                caloriesMaxFilter,
                portionsMinFilter,
                portionsMaxFilter,
                recipeTable,
                recipeTableSorter)
        );
        JButton removeFilter = new JButton(new RemoveRecipesFilterAction(recipeTableSorter));
        recipePanel.add(searchBar, " left, grow, wmin 90");
        recipePanel.add(ingredientsFilter, " right, hmin 30");
        recipePanel.add(categoriesFilter, "right, hmin 30");
        recipePanel.add(nutrition, "right, split 4");
        recipePanel.add(caloriesMinFilter, "wmax 80, hmin 30");
        recipePanel.add(max2);
        recipePanel.add(caloriesMaxFilter, "wmax 80, hmin 30");
        recipePanel.add(portions, "al right, split 4");
        recipePanel.add(portionsMinFilter, "wmax 70, hmin 30");
        recipePanel.add(max);
        recipePanel.add(portionsMaxFilter, "wmax 70, hmin 30");
        recipePanel.add(fireFilter, " r, split 2");
        recipePanel.add(removeFilter, " wrap");
        recipePanel.add(recipeScroll, "span 9, grow, height 99% ");
        return recipePanel;
    }

    private JComponent createIngredientsTab() {
        JPanel ingredientsPanel = new JPanel(new MigLayout("fillx, insets 2"));
        JSpinner caloriesMinFilter = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 20));
        JSpinner caloriesMaxFilter = new JSpinner(new SpinnerNumberModel(50000, 0, 50000, 20));
        JLabel nutritions = new JLabel("Calories");
        JLabel max = new JLabel("-");
        JTextField searchBar = new JTextField("Search...");
        searchBar.addFocusListener(new ClearTextFieldKeyListener(searchBar));
        searchBar.addKeyListener(new SearchBarListener<>(searchBar, ingredientTableSorter));
        JButton fireFilter = new JButton(new FilterIngredientsAction(
                ingredientTableSorter,
                caloriesMinFilter,
                caloriesMaxFilter
        ));
        JButton removeFilter = new JButton(new RemoveRecipesFilterAction(ingredientTableSorter));
        ingredientsPanel.add(searchBar, " grow, width 18%");
        ingredientsPanel.add(nutritions, "left, split 4");
        ingredientsPanel.add(caloriesMinFilter, "left, hmin 30");
        ingredientsPanel.add(max, "left");
        ingredientsPanel.add(caloriesMaxFilter, "left, gapright push, hmin 30");
        ingredientsPanel.add(fireFilter, "al right, split 2");
        ingredientsPanel.add(removeFilter, "wrap");
        ingredientsPanel.add(ingredientScroll, "span 3, grow, height 99%");
        return ingredientsPanel;
    }

    private JComponent createUnitsTab() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 2"));
        JTextField searchBar = new JTextField("Search...");
        searchBar.addFocusListener(new ClearTextFieldKeyListener(searchBar));
        searchBar.addKeyListener(new SearchBarListener<>(searchBar, customUnitTableSorter));

        panel.add(searchBar, "grow, height 72, wrap, gapright 70%");
        panel.add(customUnitScroll, " grow, height 99%");
        return panel;
    }

    private JComponent createCategoryTab() {
        JPanel panel = new JPanel(new MigLayout("fillx, insets 2"));
        JTextField searchBar = new JTextField("Search...");
        searchBar.addFocusListener(new ClearTextFieldKeyListener(searchBar));
        searchBar.addKeyListener(new SearchBarListener<>(searchBar, categoryTableSorter));

        panel.add(searchBar, "grow, height 72, wrap, gapright 70%");
        panel.add(categoryScroll, " grow, height 99%");
        return panel;
    }

   /* private JComponent createMultiselectComboBox() {
        JPopupMenu menu = new JPopupMenu();
        JButton button = new JButton("Ingredients");
        for (Ingredient ingredient : ingredientList) {
            menu.add(new SelectedIngredientAction(ingredient, menu, button));
        }
        button.addActionListener(e -> {
            if (!menu.isVisible()) {
                Point p = button.getLocationOnScreen();
                menu.setInvoker(button);
                menu.setLocation((int) p.getX(),
                        (int) p.getY() + button.getHeight());
                menu.setVisible(true);
            } else {
                menu.setVisible(false);
            }

        });
        return button;
    }
    private class SelectedIngredientAction extends AbstractAction {

        private final Ingredient ingredient;
        private final JPopupMenu menu;
        private final JButton button;

        private boolean selected = false;
        public SelectedIngredientAction( Ingredient ingredient, JPopupMenu menu, JButton button) {
            super(ingredient.getName());
            this.ingredient = ingredient;
            this.menu = menu;
            this.button = button;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            menu.show(button, 0, button.getHeight());
            if ( selected ) {
                putValue(Action.SMALL_ICON, null);
                selected = false;
            } else {
                putValue(Action.SMALL_ICON, Icons.SELECTED_ICON);
                selected = true;
            }
        }
    }*/

    private void rowSelectionChanged(ListSelectionEvent listSelectionEvent) {
        return;
    }

    private void refresh() {
        ingredientTableModel.refresh();
        recipeTableModel.refresh();
        categoryTableModel.refresh();
        customUnitTableModel.refresh();
    }
}

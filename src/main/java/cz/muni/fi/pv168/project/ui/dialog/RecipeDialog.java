package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.AddedIngredient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

public final class RecipeDialog extends EntityDialog<Recipe> {

    private final IngredientTableModel ingredientTableModel;
    private final CategoryTableModel categoryTableModel;
    private final JTextField recipeNameField = new JTextField();
    private final JSpinner recipePortionsField = new JSpinner(
            new SpinnerNumberModel(1, 1, 200, 1)
    );
    private final JSpinner amount = new JSpinner(
            new SpinnerNumberModel(1, 0, 100000, 0.1)
    );
    private final JTextField categoryNameField = new JTextField();
    private final JComboBox<Category> categoryJComboBox = new JComboBox<>();
    private final JColorChooser categoryColor = new JColorChooser();
    private final JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
    private final JComboBox<Ingredient> ingredients;
    private Recipe recipe;
    private final AddedIngredientsTableModel addedIngredientsTableModel;
    private final JComboBox<BaseUnits> units = new JComboBox<>(BaseUnits.values());
    private final JTextArea recipeDescriptionTextField = new JTextArea();
    private final JTable addedIngredientsTable = new JTable();

    private final JButton addIngredient = new JButton(new AbstractAction("Add ingredient") {
        @Override
        public void actionPerformed(ActionEvent e) {

            addedIngredientsTableModel.addRow(new AddedIngredient((Ingredient) ingredients.getSelectedItem(), (double) amount.getValue(), (Unit) units.getSelectedItem()));
        }
    });
    private final JButton removeIngredient = new JButton(new AbstractAction("Remove ingredient") {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(addedIngredientsTable.getSelectedRows().length);
            Arrays.stream(addedIngredientsTable.getSelectedRows())
                    .map(addedIngredientsTable::convertRowIndexToModel)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .forEach(addedIngredientsTableModel::deleteRow);
        }
    });

    public RecipeDialog(Recipe recipe, IngredientTableModel ingredientTableModel, CategoryTableModel categoryTableModel) {
        super(true);

        this.ingredientTableModel = ingredientTableModel;
        this.categoryTableModel = categoryTableModel;
        this.recipe = recipe;
        ingredients = new JComboBox<>(ingredientTableModel.getArrayOfIngredients());
        timeSpinner.setValue(new Date(0));
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        removeIngredient.setEnabled(false);
        if (recipe != null) {
            setValues();
        } else {
            this.recipe = new Recipe(null, null, 0, new PreparationTime(1, 50));
        }
        categoryJComboBox.setModel(new DefaultComboBoxModel<>(categoryTableModel.getAllCategories().toArray(new Category[0])));
        addedIngredientsTableModel = this.recipe.getUsedIngredients();
        addedIngredientsTable.setModel(addedIngredientsTableModel);
        addedIngredientsTable.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        addedIngredientsTable.setAutoCreateRowSorter(true);
        addFields();
    }

    private void rowSelectionChanged(ListSelectionEvent e) {
        removeIngredient.setEnabled(addedIngredientsTable.getSelectedRows().length > 0);
    }

    private void setValues() {
        recipeNameField.setText(recipe.getRecipeName());
        categoryNameField.setText(recipe.getCategoryName());
        categoryColor.setColor(recipe.getCategory().getColor());
        recipePortionsField.setModel(new SpinnerNumberModel(recipe.getPortions(), 1, 200, 1));
        recipeDescriptionTextField.setText(recipe.getDescription());
    }

    private void addFields() {

        addLeft("Recipe Name:", recipeNameField);
        addLeft("Category:", categoryJComboBox);
        addLeft("Portions", recipePortionsField);
        addLeft("Preparation time: [HH:SS]", timeSpinner);
        addLeft(ingredients, amount, units, addIngredient, removeIngredient);
        addLeft(new JScrollPane(addedIngredientsTable), "span 5, grow");
        addRight("Description", new JScrollPane(recipeDescriptionTextField), "wmin 250lp, hmin 580lp, grow");
    }

    @Override
    Recipe getEntity() {
        recipe.setRecipeName(recipeNameField.getText());
        recipe.setCategory((Category) categoryJComboBox.getSelectedItem());
        recipe.setPortions((int) recipePortionsField.getValue());
        recipe.setPreparationTime(new PreparationTime(
                ((Date) timeSpinner.getValue()).getHours(),
                ((Date) timeSpinner.getValue()).getMinutes()
        ));
        recipe.setDescription(recipeDescriptionTextField.getText());
        return recipe;
    }
}

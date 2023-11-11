package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import cz.muni.fi.pv168.project.ui.model.CategoryTableModel;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
    private final AddedIngredientsTableModel addedIngredientsTableModel;
    private final JComboBox<Unit> units;
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

    public RecipeDialog(Recipe recipe, RecipeTableModel recipeTableModel, IngredientTableModel ingredientTableModel, CategoryTableModel categoryTableModel, CustomUnitTableModel unitTableModel) {
        super(recipe, recipeTableModel.getEntities());
        setTwoPanels();
        this.ingredientTableModel = ingredientTableModel;
        this.categoryTableModel = categoryTableModel;
        ingredients = new JComboBox<>(ingredientTableModel.getArrayOfIngredients());

        List<Unit> unitList = new LinkedList<>();
        unitList.addAll(List.of( BaseUnits.values()));
        unitList.addAll(unitTableModel.getEntities());
        units = new JComboBox<>(unitList.toArray( new Unit[0]));
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));
        removeIngredient.setEnabled(false);
        if (recipe != null) {
            setValues();
        } else {
            entity = new Recipe(null, null, 0, new PreparationTime(1, 50));
        }
        Date new_date = new Date();
        new_date.setHours(entity.getPreparationTime().hours());
        new_date.setMinutes(entity.getPreparationTime().minutes());
        timeSpinner.setValue(new_date);
        List<Category> categories = categoryTableModel.getEntities();
        categories.add(0,null);
        categoryJComboBox.setModel(new DefaultComboBoxModel<>(categories.toArray(new Category[0])));
        categoryJComboBox.setSelectedItem(entity.getCategory());
        addedIngredientsTableModel = entity.getUsedIngredients();
        addedIngredientsTable.setModel(addedIngredientsTableModel);
        addedIngredientsTable.getSelectionModel().addListSelectionListener(this::rowSelectionChanged);
        addedIngredientsTable.setAutoCreateRowSorter(true);
        addFields();
    }

    private void rowSelectionChanged(ListSelectionEvent e) {
        removeIngredient.setEnabled(addedIngredientsTable.getSelectedRows().length > 0);
    }

    private void setValues() {
        recipeNameField.setText(entity.getName());
        categoryNameField.setText(entity.getCategoryName());
        categoryColor.setColor(entity.getCategory() == null
                        ? new Color(0, 0, 0)
                        : entity.getCategory().getColor());
        recipePortionsField.setModel(new SpinnerNumberModel(entity.getPortions(), 1, 200, 1));
        recipeDescriptionTextField.setText(entity.getDescription());
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
        entity.setName(recipeNameField.getText());
        entity.setCategory((Category) categoryJComboBox.getSelectedItem());
        entity.setPortions((int) recipePortionsField.getValue());
        entity.setPreparationTime(new PreparationTime(
                ((Date) timeSpinner.getValue()).getHours(),
                ((Date) timeSpinner.getValue()).getMinutes()
        ));
        entity.setDescription(recipeDescriptionTextField.getText());
        return entity;
    }
}

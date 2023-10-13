package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.BaseUnits;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class RecipeDialog extends EntityDialog<Recipe> {

    private final IngredientTableModel ingredientTableModel;
    private final JTextField recipeNameField = new JTextField();
    private final JSpinner recipePortionsField = new JSpinner(
            new SpinnerNumberModel(1, 1, 200, 1)
    );
    private final JSpinner recipeNutritionalValue = new JSpinner(
            new SpinnerNumberModel(0, 0, 50000, 20)
    );
    private final JSpinner amount = new JSpinner(
            new SpinnerNumberModel(1, 0, 100000, 0.1)
    );
    private final JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
    private JComboBox<Ingredient> ingredients;
    private Recipe recipe;
    private AddedIngredientsTableModel addedIngredientsTableModel = new AddedIngredientsTableModel();
    private final JComboBox<BaseUnits> units = new JComboBox<>(BaseUnits.values());

    private final JButton addIngredient = new JButton(new AbstractAction("Add ingredient") {
        @Override
        public void actionPerformed(ActionEvent e) {
            addedIngredientsTableModel.addRow(new Triplet((Ingredient) ingredients.getSelectedItem(), (double) amount.getValue(), (Unit) units.getSelectedItem()));
        }
    });

    public RecipeDialog(Recipe recipe, IngredientTableModel ingredientTableModel) {
        this.ingredientTableModel = ingredientTableModel;
        this.recipe = recipe;
        ingredients = new JComboBox<>(ingredientTableModel.toArray());
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        if (recipe != null) {
            setValues();
        } else {
            this.recipe = new Recipe(null, null, 1, null, null);
        }
        addFields();
    }

    private void setValues() {
        recipeNameField.setText(recipe.getRecipeName());
        recipeNutritionalValue.setModel(new SpinnerNumberModel(recipe.getNutritionalValue(), 0, 50000, 20));
        recipePortionsField.setModel(new SpinnerNumberModel(recipe.getPortions(), 1, 200, 1));
    }

    private void addFields() {
        add("First Name:", recipeNameField);
        add("Portions", recipePortionsField);
        add("Nutritional Value", recipeNutritionalValue);
        add("Preparation time:", timeSpinner);
        add(ingredients, amount, units, addIngredient);
        add("Ingredients",  new JScrollPane(new JTable(addedIngredientsTableModel)));
    }

    @Override
    Recipe getEntity() {
        recipe.setRecipeName(recipeNameField.getText());
        recipe.setPortions((int) recipePortionsField.getValue());
        recipe.setNutritionalValue((int) recipeNutritionalValue.getValue());
        String stringDate = timeSpinner.getValue().toString().split(" ")[3];
        recipe.setPreparationTime(LocalTime.parse(stringDate, DateTimeFormatter.ofPattern("HH:mm:ss")));
        return recipe;
    }
}

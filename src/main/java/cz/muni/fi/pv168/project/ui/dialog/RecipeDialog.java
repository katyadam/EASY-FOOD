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

public final class RecipeDialog extends EntityDialog<Recipe> {

    IngredientTableModel ingredientTableModel;
    private final JTextField recipeNameField = new JTextField();
    private final JSpinner recipePortionsField = new JSpinner(new SpinnerNumberModel(1, 1, 200, 1));
    private final JSpinner recipeNutritionalValue = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 20));
    private final JTextField recipePrepareTime = new JTextField(); // make something better
    private JComboBox<Ingredient> ingredients;
    private Recipe recipe;
    private AddedIngredientsTableModel addedIngredientsTableModel = new AddedIngredientsTableModel();
    private final JComboBox<BaseUnits> units = new JComboBox<>(BaseUnits.values());
    private final JSpinner amount = new JSpinner(new SpinnerNumberModel(1, 0, 100000, 0.1));
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
        add(ingredients, amount, units, addIngredient);
        JScrollPane tmp = new JScrollPane();
        tmp.add(new JTable(addedIngredientsTableModel));
        //JTable tmp = new JTable(addedIngredientsTableModel);
        add(tmp);
    }

    @Override
    Recipe getEntity() {
        recipe.setRecipeName(recipeNameField.getText());
        recipe.setPortions((int) recipePortionsField.getValue());
        recipe.setNutritionalValue((int) recipeNutritionalValue.getValue());
        return recipe;
    }
}

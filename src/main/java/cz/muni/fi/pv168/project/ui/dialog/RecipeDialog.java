package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.*;
import cz.muni.fi.pv168.project.ui.model.AddedIngredientsTableModel;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;

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
    private final JTextField categoryNameField = new JTextField();
    private final JSpinner timeSpinner = new JSpinner(new SpinnerDateModel());
    private final JComboBox<Ingredient> ingredients;
    private Recipe recipe;
    private final AddedIngredientsTableModel addedIngredientsTableModel;
    private final JComboBox<BaseUnits> units = new JComboBox<>(BaseUnits.values());

    private final JTextArea recipeDescriptionTextField = new JTextArea();

    private final JScrollPane textScrollPane = new JScrollPane();


    private final JButton addIngredient = new JButton(new AbstractAction("Add ingredient") {
        @Override
        public void actionPerformed(ActionEvent e) {
            addedIngredientsTableModel.addRow(new Triplet((Ingredient) ingredients.getSelectedItem(), (double) amount.getValue(), (Unit) units.getSelectedItem()));
        }
    });

    public RecipeDialog(Recipe recipe, IngredientTableModel ingredientTableModel) {
        super(true);
        this.ingredientTableModel = ingredientTableModel;
        this.recipe = recipe;
        ingredients = new JComboBox<>(ingredientTableModel.toArray());
        timeSpinner.setValue(new Date(0));
        timeSpinner.setEditor(new JSpinner.DateEditor(timeSpinner, "HH:mm"));

        if (recipe != null) {
            setValues();
        } else {
            this.recipe = new Recipe(null, null, 1, 0, new PreparationTime(1, 50));
        }
        addedIngredientsTableModel = this.recipe.getUsedIngredients();
        addFields();
    }

    private void setValues() {
        recipeNameField.setText(recipe.getRecipeName());
        categoryNameField.setText(recipe.getCategoryName());
        recipeNutritionalValue.setModel(new SpinnerNumberModel(recipe.getNutritionalValue(), 0, 50000, 20));
        recipePortionsField.setModel(new SpinnerNumberModel(recipe.getPortions(), 1, 200, 1));
        recipeDescriptionTextField.setText(recipe.getDesription());
    }

    private void addFields() {

        addLeft("Recipe Name:", recipeNameField);
        addLeft("Category Name:", categoryNameField);
        addLeft("Nutritional Value", recipeNutritionalValue);
        addLeft("Portions", recipePortionsField);
        addLeft("Preparation time:", timeSpinner);
        addLeft(ingredients, amount, units, addIngredient);
        addLeft("Ingredients", new JScrollPane(new JTable(addedIngredientsTableModel)));
        addRight("Description", new JScrollPane(recipeDescriptionTextField), "w 250lp, h 500lp, grow");
    }

    @Override
    Recipe getEntity() {
        recipe.setRecipeName(recipeNameField.getText());
        recipe.setCategory(new Category(categoryNameField.getText(), new Color(123, 123, 123)));
        recipe.setPortions((int) recipePortionsField.getValue());
        recipe.setNutritionalValue((int) recipeNutritionalValue.getValue());
        recipe.setPreparationTime(new PreparationTime(
                ((Date) timeSpinner.getValue()).getHours(),
                ((Date) timeSpinner.getValue()).getMinutes()
        ));
        recipe.setDescription(recipeDescriptionTextField.getText());
        return recipe;
    }
}

package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.BaseUnits;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.Unit;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;
import java.util.List;


public class IngredientDialog extends EntityDialog<Ingredient> {

    private Ingredient ingredient;
    private int statistic = 0;
    private final JTextField nameField = new JTextField();
    private final JSpinner nutritionalValueSpinner = new JSpinner(
            new SpinnerNumberModel(0, 0, 50000, 20)
    );

    // insert array of base units here
    private final JComboBox<BaseUnits> ingredientJComboBox = new JComboBox<>(BaseUnits.values());
    private RecipeTableModel recipeData;

    public IngredientDialog(Ingredient ingredient, RecipeTableModel recipeTableModel) {
        this.ingredient = ingredient;
        this.recipeData = recipeTableModel;

        if (ingredient != null) {
            statistic = countIngredientUsage();
            setFields();
        } else {
            this.ingredient = new Ingredient(null, 0, BaseUnits.GRAM);
        }
        addFields();

    }

    private int countIngredientUsage() {
        int result = 0;
        List<Recipe> recipes = recipeData.getData();
        for (Recipe recipe : recipes) {
            if (recipe.getUsedIngredients().contains(ingredient)) {
                result++;
            }
        }
        return result;
    }

    private void addFields() {
        add("Name", nameField);
        add("Measurement unit", ingredientJComboBox);
        add("Nutritional value [KCAL]", nutritionalValueSpinner);
        add("Ingredient used in " + statistic + " recipes");
    }

    private void setFields() {
        nameField.setText(ingredient.getName());
        nutritionalValueSpinner.setModel(new SpinnerNumberModel(ingredient.getNutritionalValue(), 0, 50000, 20));
        ingredientJComboBox.setSelectedItem(ingredient.getUnitType());
    }

    @Override
    Ingredient getEntity() {
        ingredient.setName(nameField.getText());
        ingredient.setNutritionalValue((int) nutritionalValueSpinner.getValue());
        ingredient.setUnitType((Unit) ingredientJComboBox.getSelectedItem());
        return ingredient;
    }
}

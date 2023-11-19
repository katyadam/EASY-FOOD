package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.BaseUnits;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.ui.model.IngredientTableModel;
import cz.muni.fi.pv168.project.ui.model.RecipeTableModel;

import javax.swing.*;
import java.util.List;


public class IngredientDialog extends EntityDialog<Ingredient> {

    private int statistic = 0;
    private final JTextField nameField = new JTextField();
    private final JSpinner nutritionalValueSpinner = new JSpinner(
            new SpinnerNumberModel(0, 0, 50000, 20)
    );

    // insert array of base units here
    private final JComboBox<Unit> ingredientJComboBox = new JComboBox<>(BaseUnits.getBaseUnitList().toArray(new Unit[0]));
    private RecipeTableModel recipeData;

    public IngredientDialog(Ingredient ingredient, IngredientTableModel ingredientTableModel, RecipeTableModel recipeTableModel) {
        super(ingredient, ingredientTableModel.getEntities());
        this.recipeData = recipeTableModel;

        if (ingredient != null) {
            statistic = countIngredientUsage();
            setFields();
        } else {
            entity = new Ingredient(null, 0, BaseUnits.getBaseUnitList().get(0));
        }
        addFields();

    }

    private int countIngredientUsage() {
        int result = 0;
        List<Recipe> recipes = recipeData.getEntities();
        for (Recipe recipe : recipes) {
            if (recipe.getUsedIngredients().contains(entity)) {
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
        nameField.setText(entity.getName());
        nutritionalValueSpinner.setModel(new SpinnerNumberModel(entity.getNutritionalValue(), 0, 50000, 20));
        ingredientJComboBox.setSelectedItem(entity.getUnitType());
    }

    @Override
    Ingredient getEntity() {
        entity.setName(nameField.getText());
        entity.setNutritionalValue((int) nutritionalValueSpinner.getValue());
        entity.setUnitType((Unit) ingredientJComboBox.getSelectedItem());
        return entity;
    }
}

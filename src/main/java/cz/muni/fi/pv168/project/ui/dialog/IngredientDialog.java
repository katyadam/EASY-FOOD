package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.BaseUnits;
import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.model.Unit;

import javax.swing.*;

/**
 * @author Filip Skvara
 */
public class IngredientDialog extends EntityDialog<Ingredient>{

    private Ingredient ingredient;
    private JTextField nameField = new JTextField();
    private final JSpinner nutritionalValueSpinner = new JSpinner( new SpinnerNumberModel(0,0,50000,20));
    private JComboBox<BaseUnits> ingredientJComboBox = new JComboBox<>(); // insert array of base units here

    public IngredientDialog(Ingredient ingredient) {
        this.ingredient = ingredient;

        if ( ingredient != null) {
            setFields();
        } else {
            this.ingredient = new Ingredient(null,0, BaseUnits.GRAM);
        }
        addFields();

    }
    private void addFields() {
        add("Name",nameField);
        add("Measurement unit", ingredientJComboBox);
        add("Nutritional value", nutritionalValueSpinner);
    }
    private void setFields() {
        nameField.setText(ingredient.getName());
        nutritionalValueSpinner.setModel(new SpinnerNumberModel(ingredient.getNutritionalValue(),0,50000,20));
    }

    @Override
    Ingredient getEntity() {
        ingredient.setName(nameField.getText());
        ingredient.setNutritionalValue((int) nutritionalValueSpinner.getValue());
        return ingredient;
    }
}

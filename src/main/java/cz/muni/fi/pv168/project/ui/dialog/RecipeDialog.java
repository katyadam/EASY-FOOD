package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.*;
import java.awt.event.KeyAdapter;

public final class RecipeDialog extends cz.muni.fi.pv168.project.ui.dialog.EntityDialog<Recipe> {

    private final JTextField recipeNameField = new JTextField();
    private final JSpinner recipePortionsField = new JSpinner( new SpinnerNumberModel(0,1,200,1));
    private final JSpinner recipeNutritionalValue = new JSpinner( new SpinnerNumberModel(0,0,50000,20));
    private final JTextField recipePrepareTime = new JTextField();

//    private final JTextField lastNameField = new JTextField();
//    private final ComboBoxModel<Recipe> genderModel = new DefaultComboBoxModel<>(Recipe.values());
//    private final ComboBoxModel<Department> departmentModel;
//    private final DateModel<LocalDate> birthDateModel = new LocalDateModel();

    private Recipe recipe;

    public RecipeDialog(Recipe recipe) {
        this.recipe = recipe;
//        this.departmentModel = new ComboBoxModelAdapter<>(departmentModel);
        if ( recipe != null) {
            setValues();
        } else  {
            this.recipe = new Recipe(null,null,0,null,null);
        }
        addFields();
    }

    private void setValues() {
        recipeNameField.setText(recipe.getRecipeName());
        recipeNutritionalValue.setModel(new SpinnerNumberModel(recipe.getNutritionalValue(),0,50000,20));
        recipePortionsField.setModel(new SpinnerNumberModel(recipe.getPortions(), 1,200,1));


//        lastNameField.setText(recipe.getLastName());
//        genderModel.setSelectedItem(recipe.getGender());
//        departmentModel.setSelectedItem(recipe.getDepartment());
//        birthDateModel.setValue(employee.getBirthDate());
    }

    private void addFields() {
        add("First Name:", recipeNameField);
        add("Portions", recipePortionsField );
        add("Nutritional Value",recipeNutritionalValue);
//        add("Last Name:", lastNameField);
//        add("Gender:", new JComboBox<>(genderModel));
//        add("Birth Date:", new JDatePicker(birthDateModel));
//        add("Department:", new JComboBox<>(departmentModel));
    }

    @Override
    Recipe getEntity() {
        recipe.setRecipeName(recipeNameField.getText());
        recipe.setPortions((int) recipePortionsField.getValue());
        recipe.setNutritionalValue((int) recipeNutritionalValue.getValue());
//        employee.setLastName(lastNameField.getText());
//        employee.setGender((Gender) genderModel.getSelectedItem());
//        employee.setDepartment((Department) departmentModel.getSelectedItem());
//        employee.setBirthDate(birthDateModel.getValue());
        return recipe;
    }
}

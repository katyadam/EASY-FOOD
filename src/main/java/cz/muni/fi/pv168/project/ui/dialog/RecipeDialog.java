package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.Recipe;

import javax.swing.JTextField;

public final class RecipeDialog extends cz.muni.fi.pv168.project.ui.dialog.EntityDialog<Recipe> {

    private final JTextField recipeNameField = new JTextField();
//    private final JTextField lastNameField = new JTextField();
//    private final ComboBoxModel<Recipe> genderModel = new DefaultComboBoxModel<>(Recipe.values());
//    private final ComboBoxModel<Department> departmentModel;
//    private final DateModel<LocalDate> birthDateModel = new LocalDateModel();

    private final Recipe recipe;

    public RecipeDialog(Recipe recipe) {
        this.recipe = recipe;
//        this.departmentModel = new ComboBoxModelAdapter<>(departmentModel);
        setValues();
        addFields();
    }

    private void setValues() {
        recipeNameField.setText(recipe.getRecipeName());
//        lastNameField.setText(recipe.getLastName());
//        genderModel.setSelectedItem(recipe.getGender());
//        departmentModel.setSelectedItem(recipe.getDepartment());
//        birthDateModel.setValue(employee.getBirthDate());
    }

    private void addFields() {
        add("First Name:", recipeNameField);
//        add("Last Name:", lastNameField);
//        add("Gender:", new JComboBox<>(genderModel));
//        add("Birth Date:", new JDatePicker(birthDateModel));
//        add("Department:", new JComboBox<>(departmentModel));
    }

    @Override
    Recipe getEntity() {
        recipe.setRecipeName(recipeNameField.getText());
//        employee.setLastName(lastNameField.getText());
//        employee.setGender((Gender) genderModel.getSelectedItem());
//        employee.setDepartment((Department) departmentModel.getSelectedItem());
//        employee.setBirthDate(birthDateModel.getValue());
        return recipe;
    }
}

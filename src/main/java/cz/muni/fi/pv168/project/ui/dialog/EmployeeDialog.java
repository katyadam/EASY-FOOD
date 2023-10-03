package cz.muni.fi.pv168.project.ui.dialog;


import cz.muni.fi.pv168.project.model.Recipe;
import cz.muni.fi.pv168.project.ui.model.ComboBoxModelAdapter;
import cz.muni.fi.pv168.project.ui.model.LocalDateModel;
import org.jdatepicker.DateModel;
import org.jdatepicker.JDatePicker;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import java.time.LocalDate;

public final class EmployeeDialog extends cz.muni.fi.pv168.project.ui.dialog.EntityDialog<Recipe> {
    @Override
    Recipe getEntity() {
        return null;
    }

//    private final JTextField firstNameField = new JTextField();
//    private final JTextField lastNameField = new JTextField();
//    private final ComboBoxModel<Gender> genderModel = new DefaultComboBoxModel<>(Gender.values());
//    private final ComboBoxModel<Department> departmentModel;
//    private final DateModel<LocalDate> birthDateModel = new LocalDateModel();
//
//    private final Employee employee;
//
//    public EmployeeDialog(Employee employee, ListModel<Department> departmentModel) {
//        this.employee = employee;
//        this.departmentModel = new ComboBoxModelAdapter<>(departmentModel);
//        setValues();
//        addFields();
//    }
//
//    private void setValues() {
//        firstNameField.setText(employee.getFirstName());
//        lastNameField.setText(employee.getLastName());
//        genderModel.setSelectedItem(employee.getGender());
//        departmentModel.setSelectedItem(employee.getDepartment());
//        birthDateModel.setValue(employee.getBirthDate());
//    }
//
//    private void addFields() {
//        add("First Name:", firstNameField);
//        add("Last Name:", lastNameField);
//        add("Gender:", new JComboBox<>(genderModel));
//        add("Birth Date:", new JDatePicker(birthDateModel));
//        add("Department:", new JComboBox<>(departmentModel));
//    }
//
//    @Override
//    Employee getEntity() {
//        employee.setFirstName(firstNameField.getText());
//        employee.setLastName(lastNameField.getText());
//        employee.setGender((Gender) genderModel.getSelectedItem());
//        employee.setDepartment((Department) departmentModel.getSelectedItem());
//        employee.setBirthDate(birthDateModel.getValue());
//        return employee;
//    }
}

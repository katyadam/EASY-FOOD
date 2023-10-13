package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.BaseUnits;
import cz.muni.fi.pv168.project.model.CustomUnit;

import javax.swing.*;

public class CustomUnitDialog extends EntityDialog<CustomUnit> {

    private CustomUnit customUnit;

    private final JTextField customUnitNameField = new JTextField();
    private final JTextField customUnitAbbreviationField = new JTextField();
    private final JSpinner customUnitAmount = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 50000.0, 1.0));
    private final JComboBox<BaseUnits> units = new JComboBox<>(BaseUnits.values());

    public CustomUnitDialog(CustomUnit customUnit) {
        this.customUnit = customUnit;

        if (customUnit != null) {
            setValues();
        } else {
            this.customUnit = new CustomUnit(null, null, 0, null);
        }
        addFields();
    }

    private void setValues() {
        customUnitNameField.setText(customUnit.getFullName());
        customUnitAbbreviationField.setText(customUnit.getAbbreviation());
        customUnitAmount.setValue(Double.parseDouble(customUnit.getBaseAmountNumber()));
    }

    private void addFields() {
        add("Name:", customUnitNameField);
        add("Abbreviation", customUnitAbbreviationField);
        add("Base Amount", customUnitAmount);
        add("Base Unit", units);
    }

    @Override
    CustomUnit getEntity() {
        customUnit.setUnitName(customUnitNameField.getText());
        customUnit.setAbbreviation(customUnitAbbreviationField.getText());
        customUnit.setAmount((double) customUnitAmount.getValue());
        customUnit.setBaseUnit((BaseUnits) units.getSelectedItem());
        return customUnit;
    }
}

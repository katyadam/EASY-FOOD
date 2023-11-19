package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.business.model.BaseUnits;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;

import javax.swing.*;

public class CustomUnitDialog extends EntityDialog<Unit> {


    private final JTextField customUnitNameField = new JTextField();
    private final JTextField customUnitAbbreviationField = new JTextField();
    private final JSpinner customUnitAmount = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 50000.0, 1.0));
    private final JComboBox<Unit> units = new JComboBox<>(BaseUnits.getBaseUnitList().toArray(new Unit[0]));

    public CustomUnitDialog(Unit unit, CustomUnitTableModel unitTableModel) {
        super(unit, unitTableModel.getEntities());

        if (unit != null) {
            setValues();
        } else {
            entity = new Unit(null, null, 0, null);
        }
        addFields();
    }

    private void setValues() {
        customUnitNameField.setText(entity.getName());
        customUnitAbbreviationField.setText(entity.getAbbreviation());
        customUnitAmount.setValue(Double.parseDouble(entity.getBaseAmountNumber()));
    }

    private void addFields() {
        add("Name:", customUnitNameField);
        add("Abbreviation", customUnitAbbreviationField);
        add("Base Amount", customUnitAmount);
        add("Base Unit", units);
    }

    @Override
    Unit getEntity() {
        entity.setName(customUnitNameField.getText());
        entity.setAbbreviation(customUnitAbbreviationField.getText());
        entity.setAmount((double) customUnitAmount.getValue());
        entity.setBaseUnit((Unit) units.getSelectedItem());
        return entity;
    }
}

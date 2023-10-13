package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.BaseUnits;
import cz.muni.fi.pv168.project.model.CustomUnit;
import cz.muni.fi.pv168.project.ui.model.AddedCustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.CustomUnitTableModel;
import cz.muni.fi.pv168.project.ui.model.Triplet;

import javax.swing.*;

/**
 * @author Adam Juhas
 */
public class CustomUnitDialog extends EntityDialog<CustomUnit> {

    private CustomUnit customUnit;

    CustomUnitTableModel customUnitTableModel;

    private final JTextField customUnitNameField = new JTextField();
    private final JTextField customUnitAbbreviationField = new JTextField();

    private AddedCustomUnitTableModel addedCustomUnitTableModel =
            new AddedCustomUnitTableModel().addRow(new Triplet<>("daco", "da", "10 " + BaseUnits.GRAM));
    private final JSpinner customUnitAmount = new JSpinner(new SpinnerNumberModel(0, 0, 50000, 1));
    private final JComboBox<BaseUnits> units = new JComboBox<>(BaseUnits.values());

    public CustomUnitDialog(CustomUnit customUnit, CustomUnitTableModel customUnitTableModel) {
        this.customUnitTableModel = customUnitTableModel;
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
        customUnitAmount.setModel(new SpinnerNumberModel(Integer.parseInt(customUnit.getBaseAmount()), 1, 200, 1));
    }

    private void addFields() {
        add("Name:", customUnitNameField);
        add("Abbreviation", customUnitAbbreviationField);
        add("Base Amount", customUnitAmount);
        add("Base Unit", units);
        JScrollPane tmp = new JScrollPane();
        tmp.add(new JTable(addedCustomUnitTableModel));
        //JTable tmp = new JTable(addedIngredientsTableModel);
        add(tmp);
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

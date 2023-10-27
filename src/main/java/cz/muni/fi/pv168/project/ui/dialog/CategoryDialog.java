package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.BaseUnits;
import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.CustomUnit;

import javax.swing.*;
import java.awt.*;

public class CategoryDialog extends EntityDialog<Category> {

    private Category category;

    private final JTextField categoryNameField = new JTextField();
    private final JTextField categoryColor = new JTextField();

    public CategoryDialog(Category category) {
        this.category = category;

        if (category != null) {
            setValues();
        } else {
            this.category = new Category(null, null);
        }
        addFields();
    }

    private void setValues() {
        categoryNameField.setText(category.getName());
        categoryColor.setText(category.getColor().toString());
    }

    private void addFields() {
        add("Name:", categoryNameField);
        add("Color", categoryColor);
    }

    @Override
    Category getEntity() {
        category.setName(categoryNameField.getText());
        category.setColor(new Color(255, 255, 255));
        //TODO category.setColor((Color) categoryColor.getText());
        return category;
    }
}

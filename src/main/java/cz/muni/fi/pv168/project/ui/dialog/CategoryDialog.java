package cz.muni.fi.pv168.project.ui.dialog;

import cz.muni.fi.pv168.project.model.Category;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;

public class CategoryDialog extends EntityDialog<Category> {

    private Category category;

    private final JTextField categoryNameField = new JTextField();
    private final JColorChooser categoryColor = new JColorChooser();

    public CategoryDialog(Category category) {
        this.category = category;

        for (AbstractColorChooserPanel panel : categoryColor.getChooserPanels()) {
            if (!panel.getDisplayName().equals("RGB")) {
                categoryColor.removeChooserPanel(panel);
            }
        }
        categoryColor.setPreviewPanel(new JPanel());

        if (category != null) {
            setValues();
        } else {
            this.category = new Category(null, null);
        }
        addFields();
    }

    private void setValues() {
        categoryNameField.setText(category.getName());
        categoryColor.setColor(category.getColor());
    }

    private void addFields() {
        add("Name:", categoryNameField);
        add("Color", categoryColor);
    }

    @Override
    Category getEntity() {
        category.setName(categoryNameField.getText());
        category.setColor(categoryColor.getColor());
        return category;
    }
}

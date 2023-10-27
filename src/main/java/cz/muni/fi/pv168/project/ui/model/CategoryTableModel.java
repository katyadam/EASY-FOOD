package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;

import java.awt.*;
import java.util.List;

public class CategoryTableModel extends AbstractEntityTableModel<Category> {

    public CategoryTableModel(List<Category> categories) {
        super(List.of(
                Column.readonly("Name", String.class, Category::getName),
                Column.readonly("Color", Color.class, Category::getColor)
        ), categories);
    }

}

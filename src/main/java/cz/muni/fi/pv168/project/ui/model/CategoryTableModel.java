package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.service.crud.CrudService;
import cz.muni.fi.pv168.project.ui.renderers.ColorRenderer;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryTableModel extends AbstractEntityTableModel<Category> {

    private final CrudService<Category> categoryCrudService;

    public CategoryTableModel(CrudService<Category> categoryCrudService) {
        super(List.of(
                Column.readonly("Name", String.class, Category::getName),
                Column.readonly("Color", Color.class, Category::getColor)
        ), categoryCrudService.findAll(), categoryCrudService);
        this.categoryCrudService = categoryCrudService;
    }

}

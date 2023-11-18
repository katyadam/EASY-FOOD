package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.service.crud.CrudService;

import java.util.List;

public class CustomUnitTableModel extends AbstractEntityTableModel<Unit> {
    private final CrudService<Unit> customUnitCrudService;

    public CustomUnitTableModel(CrudService<Unit> customUnitCrudService) {
        super(List.of(
                Column.readonly("Name", String.class, Unit::getName),
                Column.readonly("Abbreviation", String.class, Unit::getAbbreviation),
                Column.readonly("BaseAmount", String.class, Unit::getBaseAmount)
        ), customUnitCrudService.findAll(), customUnitCrudService);
        this.customUnitCrudService = customUnitCrudService;
    }

}

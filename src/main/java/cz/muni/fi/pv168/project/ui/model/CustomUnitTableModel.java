package cz.muni.fi.pv168.project.ui.model;


import cz.muni.fi.pv168.project.model.CustomUnit;

import java.util.List;

public class CustomUnitTableModel extends AbstractEntityTableModel<CustomUnit> {

    public CustomUnitTableModel(List<CustomUnit> customUnits) {
        super(List.of(
                Column.readonly("Name", String.class, CustomUnit::getFullName),
                Column.readonly("Abbreviation", String.class, CustomUnit::getAbbreviation),
                Column.readonly("BaseAmount", String.class, CustomUnit::getBaseAmount)
        ), customUnits);
    }

}

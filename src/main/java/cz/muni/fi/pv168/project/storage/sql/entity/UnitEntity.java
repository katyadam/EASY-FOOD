package cz.muni.fi.pv168.project.storage.sql.entity;

public record UnitEntity(
        Long id,
        String guid,
        String unitName,
        String abbreviation,
        double amount,
        String baseUnit // base units name
) {

    public UnitEntity(Long id, String guid, String unitName, String abbreviation, double amount, String baseUnit) {
        this.id = id;
        this.guid = guid;
        this.unitName = unitName;
        this.abbreviation = abbreviation;
        this.amount = amount;
        this.baseUnit = baseUnit;
    }

    public UnitEntity(String guid, String unitName, String abbreviation, double amount, String baseUnit) {
        this(null, guid, unitName, abbreviation, amount, baseUnit);
    }
}


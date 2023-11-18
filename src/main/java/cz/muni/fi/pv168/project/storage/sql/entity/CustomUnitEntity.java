package cz.muni.fi.pv168.project.storage.sql.entity;

import java.time.LocalDate;

public record CustomUnitEntity(
        Long id,
        String guid,
        String unitName,
        String abbreviation,
        double amount,
        String baseUnit,
        LocalDate birthDate) {

    public CustomUnitEntity(Long id, String guid, String unitName, String abbreviation, double amount, String baseUnit, LocalDate birthDate) {
        this.id = id;
        this.guid = guid;
        this.unitName = unitName;
        this.abbreviation = abbreviation;
        this.amount = amount;
        this.baseUnit = baseUnit;
        this.birthDate = birthDate;
    }

    public CustomUnitEntity(String guid, String unitName, String abbreviation, double amount, String baseUnit, LocalDate birthDate) {
        this(null, guid, unitName, abbreviation, amount, baseUnit, birthDate);
    }
}


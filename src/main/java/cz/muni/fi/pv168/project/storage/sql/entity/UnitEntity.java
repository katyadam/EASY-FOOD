package cz.muni.fi.pv168.project.storage.sql.entity;

public record UnitEntity(
        Long id,
        String guid,
        String unitName,
        String abbreviation,
        double amount,
        int baseUnitId,
        Long userID// base units name
) {

    public UnitEntity(Long id, String guid, String unitName, String abbreviation, double amount, int baseUnitId, Long userID) {
        this.id = id;
        this.guid = guid;
        this.unitName = unitName;
        this.abbreviation = abbreviation;
        this.amount = amount;
        this.baseUnitId = baseUnitId;
        this.userID = userID;
    }

    public UnitEntity(String guid, String unitName, String abbreviation, double amount, int baseUnitId, Long userID) {
        this(null, guid, unitName, abbreviation, amount, baseUnitId,userID);
    }
}


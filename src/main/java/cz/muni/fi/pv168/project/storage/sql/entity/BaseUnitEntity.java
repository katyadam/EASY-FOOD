package cz.muni.fi.pv168.project.storage.sql.entity;

public record BaseUnitEntity(
        Long id,
        String guid,
        String baseUnitName,
        String abbreviation
) {
    public BaseUnitEntity(String guid, String baseUnitName, String abbreviation) {
        this(null, guid, baseUnitName, abbreviation);
    }
}

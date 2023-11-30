package cz.muni.fi.pv168.project.business.model;

public class BaseUnit extends Entity {

    private final String baseUnitName;
    private final String abbreviation;

    public BaseUnit(String guid, String baseUnitName, String abbreviation) {
        super(guid);
        this.baseUnitName = baseUnitName;
        this.abbreviation = abbreviation;
    }

    public BaseUnit(String baseUnitName, String abbreviation) {
        this.baseUnitName = baseUnitName;
        this.abbreviation = abbreviation;
    }

    public String getBaseUnitName() {
        return baseUnitName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return baseUnitName;
    }
}

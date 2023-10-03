package cz.muni.fi.pv168.project.model;

public class CustomUnit implements Unit {
    private final String unitName;

    private String abbreviation = "";

    private double amount;

    private BaseUnits baseUnit;

    public CustomUnit(String unitName, double amount, BaseUnits baseUnit) {
        this.unitName = unitName;
        this.amount = amount;
        this.baseUnit = baseUnit;
    }

    public CustomUnit(String unitName, double amount, BaseUnits baseUnit, String abbreviation) {
        this(unitName, amount, baseUnit);
        this.abbreviation = abbreviation;
    }



    @Override
    public String getFullName() {
        return unitName;
    }

    @Override
    public String getAbbreviation() {
        return null;
    }
}

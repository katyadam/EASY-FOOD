package cz.muni.fi.pv168.project.model;

public class CustomUnit implements Unit {
    private  String unitName;
    private String abbreviation;
    private double amount;
    private  BaseUnits baseUnit;

    public CustomUnit(String unitName, String abbreviation, double amount, BaseUnits baseUnit) {
        this.unitName = unitName;
        this.abbreviation = abbreviation;
        this.amount = amount;
        this.baseUnit = baseUnit;
    }

    @Override
    public String getFullName() {
        return unitName;
    }

    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    public String getBaseAmount() {
        return String.format("%.2f", amount) + " " + baseUnit.getAbbreviation();
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setBaseUnit(BaseUnits baseUnit) {
        this.baseUnit = baseUnit;
    }
}

package cz.muni.fi.pv168.project.model;

public class customUnit {
    private String unitName;

    private double amount;

    private BaseUnits baseUnit;

    public customUnit(String unitName, double amount, BaseUnits baseUnit) {
        this.unitName = unitName;
        this.amount = amount;
        this.baseUnit = baseUnit;
    }

    public customUnit(String unitName, long amount, BaseUnits baseUnit) {
        this.unitName = unitName;
        this.amount = (double) amount;
        this.baseUnit = baseUnit;
    }
}

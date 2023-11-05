package cz.muni.fi.pv168.project.model;

public class Ingredient extends Entity {
    private String name;
    private int nutritionalValue;

    private Unit unitType;

    public Ingredient() {
    }

    public Ingredient(String name, int nutritionalValue, Unit unitType) {
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.unitType = unitType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public String getName() {
        return name;
    }

    public int getNutritionalValue() {
        return nutritionalValue;
    }

    public Unit getUnitType() {
        return unitType;
    }

    public String getUnitAbbreviation() {
        return unitType.getAbbreviation();
    }

    public void setUnitType(Unit unitType) {
        this.unitType = unitType;
    }

    @Override
    public String toString() {
        return name;
    }

}

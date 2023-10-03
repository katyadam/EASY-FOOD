package cz.muni.fi.pv168.project.model;

public class Ingredient {
    private String name;
    private long nutritionalValue;

    public Ingredient(String name, long nutritionalValue) {
        this.name = name;
        this.nutritionalValue = nutritionalValue;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNutritionalValue(long nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }

    public String getName() {
        return name;
    }

    public long getNutritionalValue() {
        return nutritionalValue;
    }
}

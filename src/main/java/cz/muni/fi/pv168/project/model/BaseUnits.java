package cz.muni.fi.pv168.project.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public enum BaseUnits implements Unit {
    TEASPOON("teaspoon", "tsp"),
    TABLESPOON("tablespoon", "tbsp"),
    CUP("cup", "c"),
    FLUID_OUNCE("fluid ounce", "fl oz"),
    PINT("pint", "pt"),
    QUART("quart", "qt"),
    GALLON("gallon", "gal"),
    MILLILITER("milliliter", "ml"),
    LITER("liter", "l"),
    GRAM("gram", "g"),
    KILOGRAM("kilogram", "kg"),
    OUNCE("ounce", "oz"),
    POUND("pound", "lb"),
    DROP("drop", "drop"),
    PIECE("piece", "pc");

    private String fullName;
    private String abbreviation;

    BaseUnits(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    public static List<BaseUnits> getBaseUnitList(){
        return List.of(TEASPOON,TABLESPOON,CUP,FLUID_OUNCE,PINT,QUART,
                GALLON,MILLILITER,LITER,GRAM,KILOGRAM,OUNCE,POUND,DROP,PIECE);
    }
}

package cz.muni.fi.pv168.project.model;

public enum BaseUnits {
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

    private final String fullName;
    private final String abbreviation;

    BaseUnits(String fullName, String abbreviation) {
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public String getFullName() {
        return fullName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return fullName;
    }
}

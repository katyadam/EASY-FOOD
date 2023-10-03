package cz.muni.fi.pv168.project.model;

public enum BaseUnits implements Unit{


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
}

package cz.muni.fi.pv168.project.business.model;

/**
 * @author Filip Skvara
 */
public enum BaseUnit implements Unit {

    KILOGRAM("kg",0),
    GRAM("g",1),
    MILLILITER("ml",2),
    LITER("l",3),
    PIECE("pcs",4);
    private final String abbreviation;
    private final int index;

    BaseUnit(String abbreviation, int i) {
        index = i;
        this.abbreviation = abbreviation;
    }

    public static BaseUnit indexToUnit(int i) {
        return BaseUnit.values()[i];
    }
    public String getName() {
        return this.name();
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public int getIndex() {return index;}


    @Override
    public String toString() {
        return this.name();
    }
}

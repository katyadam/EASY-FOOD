package cz.muni.fi.pv168.project.business.model;

/**
 * @author Filip Skvara
 */
public enum BaseUnit_ implements Unit {

    KILOGRAM("kg"),GRAM("g"),MILLILITER("ml"),LITER("l"),PIECE("pcs");
    private final String abbreviation;

    BaseUnit_( String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getName() {
        return this.name();
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public String toString() {
        return this.name();
    }
}

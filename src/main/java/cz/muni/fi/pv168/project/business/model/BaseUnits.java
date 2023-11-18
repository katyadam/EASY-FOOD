package cz.muni.fi.pv168.project.business.model;

import java.util.List;

public class BaseUnits extends Entity {
    private static final UuidGuidProvider uuidGuidProvider = new UuidGuidProvider();
    public static final List<Unit> baseUnits = List.of(
            new Unit(uuidGuidProvider.newGuid(), "piece", "pc", 1, null),
            new Unit(uuidGuidProvider.newGuid(), "mililiter", "ml", 1, null),
            new Unit(uuidGuidProvider.newGuid(), "liter", "l", 1, null),
            new Unit(uuidGuidProvider.newGuid(), "gram", "g", 1, null),
            new Unit(uuidGuidProvider.newGuid(), "kilogram", "kg", 1, null)
    );

    public BaseUnits() {
    }

    public static List<Unit> getBaseUnitList() {
        return baseUnits;
    }
}

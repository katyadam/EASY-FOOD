package cz.muni.fi.pv168.project.business.model;

import java.util.List;

public class BaseUnits extends Entity {
    public static final List<BaseUnit> baseUnits = List.of(
            new BaseUnit("bu-pc", "piece", "pc"),
            new BaseUnit("bu-ml", "mililiter", "ml"),
            new BaseUnit("bu-l", "liter", "l"),
            new BaseUnit("bu-g", "gram", "g"),
            new BaseUnit("bu-kg", "kilogram", "kg")
    );

    public BaseUnits() {
    }

    public static List<BaseUnit> getBaseUnitList() {
        return baseUnits;
    }
}

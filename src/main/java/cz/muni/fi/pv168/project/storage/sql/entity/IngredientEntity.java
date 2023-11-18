package cz.muni.fi.pv168.project.storage.sql.entity;

import java.time.LocalDate;

public record IngredientEntity(
        Long id,
        String guid,
        String ingredientName,
        int nutritionalValue,
        String unitType,
        LocalDate birthDate) {
    public IngredientEntity(Long id, String guid, String ingredientName, int nutritionalValue, String unitType, LocalDate birthDate) {
        this.id = id;
        this.guid = guid;
        this.ingredientName = ingredientName;
        this.nutritionalValue = nutritionalValue;
        this.unitType = unitType;
        this.birthDate = birthDate;
    }

    public IngredientEntity(String guid, String ingredientName,int nutritionalValue, String unitType, LocalDate birthDate) {
        this(null, guid, ingredientName,nutritionalValue, unitType, birthDate);
    }
}

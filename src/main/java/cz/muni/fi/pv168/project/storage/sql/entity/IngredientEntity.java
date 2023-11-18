package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.Unit;

import java.time.LocalDate;

public record IngredientEntity(
        Long id,
        String guid,
        String ingredientName,
        int nutritionalValue,
        Unit unitType) {
    public IngredientEntity(Long id, String guid, String ingredientName, int nutritionalValue, Unit unitType) {
        this.id = id;
        this.guid = guid;
        this.ingredientName = ingredientName;
        this.nutritionalValue = nutritionalValue;
        this.unitType = unitType;
    }

    public IngredientEntity(String guid, String ingredientName,int nutritionalValue, Unit unitType) {
        this(null, guid, ingredientName,nutritionalValue, unitType);
    }
}

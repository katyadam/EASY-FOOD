package cz.muni.fi.pv168.project.storage.sql.entity;

public record IngredientEntity(
        Long id,
        String guid,
        String ingredientName,
        int nutritionalValue,
        Long unitId
) {
    public IngredientEntity(Long id, String guid, String ingredientName, int nutritionalValue, Long unitId) {
        this.id = id;
        this.guid = guid;
        this.ingredientName = ingredientName;
        this.nutritionalValue = nutritionalValue;
        this.unitId = unitId;
    }

    public IngredientEntity(String guid, String ingredientName, int nutritionalValue, Long unitId) {
        this(null, guid, ingredientName, nutritionalValue, unitId);
    }
}

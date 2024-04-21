package cz.muni.fi.pv168.project.storage.sql.entity;

public record IngredientEntity(
        Long id,
        String guid,
        String ingredientName,
        int nutritionalValue,
        Long userID
) {
    public IngredientEntity(Long id, String guid, String ingredientName, int nutritionalValue, Long userID) {
        this.id = id;
        this.guid = guid;
        this.ingredientName = ingredientName;
        this.nutritionalValue = nutritionalValue;
        this.userID = userID;
    }

    public IngredientEntity(String guid, String ingredientName, int nutritionalValue, Long userID) {
        this(null, guid, ingredientName, nutritionalValue, userID);
    }
}

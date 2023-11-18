package cz.muni.fi.pv168.project.storage.sql.entity;

public record AddedIngredientEntity(
        Long id,
        Long recipeId,
        String guid,
        Long ingredientId,
        Long unitId,
        Double amount) {

    public AddedIngredientEntity(Long id, Long recipeId, String guid, Long ingredientId, Long unitId, Double amount) {
        this.id = id;
        this.recipeId = recipeId;
        this.guid = guid;
        this.ingredientId = ingredientId;
        this.unitId = unitId;
        this.amount = amount;
    }
}

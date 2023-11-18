package cz.muni.fi.pv168.project.storage.sql.entity;

public record RecipeEntity(
        Long id,
        String guid,
        long categoryId,
        String recipeName,
        int preparationTime,
        int portions,
        int nutritionalValue,
        String description) {
    public RecipeEntity(Long id, String guid, long categoryId, String recipeName, int preparationTime, int portions, int nutritionalValue, String description) {
        this.id = id;
        this.guid = guid;
        this.categoryId = categoryId;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.nutritionalValue = nutritionalValue;
        this.description = description;
    }

    public RecipeEntity(String guid, long categoryId, String recipeName, int preparationTime, int portions, int nutritionalValue, String description) {
        this(null, guid, categoryId, recipeName, preparationTime, portions, nutritionalValue, description);
    }
}

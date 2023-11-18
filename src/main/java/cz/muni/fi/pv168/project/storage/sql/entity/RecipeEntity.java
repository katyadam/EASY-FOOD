package cz.muni.fi.pv168.project.storage.sql.entity;

import java.time.LocalDate;

public record RecipeEntity(
        Long id,
        String guid,
        long categoryId,
        String recipeName,
        String preparationTime,
        int portions,
        int nutritionalValue,
        String description,
        LocalDate birthDate) {
    public RecipeEntity(Long id, String guid, long categoryId, String recipeName, String preparationTime, int portions, int nutritionalValue, String description, LocalDate birthDate) {
        this.id = id;
        this.guid = guid;
        this.categoryId = categoryId;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.nutritionalValue = nutritionalValue;
        this.description = description;
        this.birthDate = birthDate;
    }

    public RecipeEntity(String guid, long categoryId, String recipeName, String preparationTime, int portions, int nutritionalValue, String description, LocalDate birthDate) {
        this(null, guid, categoryId, recipeName, preparationTime, portions, nutritionalValue, description, birthDate);
    }
}

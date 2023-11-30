package cz.muni.fi.pv168.project.storage.sql.entity;

import cz.muni.fi.pv168.project.business.model.PreparationTime;

public record RecipeEntity(
        Long id,
        String guid,
        long categoryId,
        String recipeName,
        PreparationTime preparationTime,
        int portions,
        String description) {
    public RecipeEntity(
            Long id,
            String guid,
            long categoryId,
            String recipeName,
            PreparationTime preparationTime,
            int portions,
            String description
    ) {
        this.id = id;
        this.guid = guid;
        this.categoryId = categoryId;
        this.recipeName = recipeName;
        this.preparationTime = preparationTime;
        this.portions = portions;
        this.description = description;
    }

    public RecipeEntity(String guid, long categoryId, String recipeName,
                        PreparationTime preparationTime, int portions, String description) {
        this(null, guid, categoryId, recipeName, preparationTime, portions, description);
    }
}

package cz.muni.fi.pv168.project.storage.sql.entity.mapper;


import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;

public class IngredientMapper implements EntityMapper<IngredientEntity, Ingredient> {
    @Override
    public Ingredient mapToBusiness(IngredientEntity dbIngredient) {
        return new Ingredient(
                dbIngredient.guid(),
                dbIngredient.ingredientName(),
                dbIngredient.nutritionalValue(),
                dbIngredient.unitType()
        );
    }

    @Override
    public IngredientEntity mapNewEntityToDatabase(Ingredient entity) {
        return getIngredientEntity(entity, null);
    }

    @Override
    public IngredientEntity mapExistingEntityToDatabase(Ingredient entity, Long dbId) {
        return getIngredientEntity(entity, dbId);
    }

    private static IngredientEntity getIngredientEntity(Ingredient entity, Long dbId) {
        return new IngredientEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getNutritionalValue(),
                entity.getUnitType()
        );
    }
}

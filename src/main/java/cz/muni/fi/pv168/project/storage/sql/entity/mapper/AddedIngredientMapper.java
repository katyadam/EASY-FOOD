package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.AddedIngredientEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;

public class AddedIngredientMapper implements EntityMapper<AddedIngredientEntity, AddedIngredient> {
    private final DataAccessObject<RecipeEntity> recipeDao;
    private final EntityMapper<RecipeEntity, Recipe> recipeMapper;
    private final DataAccessObject<IngredientEntity> ingredientDao;
    private final EntityMapper<IngredientEntity, Ingredient> ingredientMapper;
    private final DataAccessObject<UnitEntity> unitDao;
    private final EntityMapper<UnitEntity, Unit> unitMapper;

    public AddedIngredientMapper(DataAccessObject<RecipeEntity> recipeDao, EntityMapper<RecipeEntity, Recipe> recipeMapper, DataAccessObject<IngredientEntity> ingredientDao, EntityMapper<IngredientEntity, Ingredient> ingredientMapper, DataAccessObject<UnitEntity> unitDao, EntityMapper<UnitEntity, Unit> unitMapper) {
        this.recipeDao = recipeDao;
        this.recipeMapper = recipeMapper;
        this.ingredientDao = ingredientDao;
        this.ingredientMapper = ingredientMapper;
        this.unitDao = unitDao;
        this.unitMapper = unitMapper;
    }

    @Override
    public AddedIngredient mapToBusiness(AddedIngredientEntity entity) {
        var recipe = recipeDao
                .findById(entity.recipeId())
                .map(recipeMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Recipe not found, id: " +
                        entity.recipeId()));
        var ingredient = ingredientDao
                .findById(entity.ingredientId())
                .map(ingredientMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Ingredient not found, id: " +
                        entity.ingredientId()));
        var unit = unitDao
                .findById(entity.unitId())
                .map(unitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Unit not found, id: " +
                        entity.unitId()));
        return new AddedIngredient(
                entity.guid(),
                recipe,
                ingredient,
                unit,
                entity.amount()
        );
    }

    @Override
    public AddedIngredientEntity mapNewEntityToDatabase(AddedIngredient entity) {
        return getAddedIngredientEntity(entity, null);
    }

    @Override
    public AddedIngredientEntity mapExistingEntityToDatabase(AddedIngredient entity, Long dbId) {
        return getAddedIngredientEntity(entity, dbId);
    }

    private AddedIngredientEntity getAddedIngredientEntity(AddedIngredient entity, Long dbId) {
        var recipeEntity = recipeDao
                .findByGuid(entity.getRecipe().getGuid())
                .orElseThrow(() -> new DataStorageException("Recipe not found, guid: " +
                        entity.getRecipe().getGuid()));
        var ingredientEntity = ingredientDao
                .findByGuid(entity.getIngredient().getGuid())
                .orElseThrow(() -> new DataStorageException("Ingredient not found, guid: " +
                        entity.getIngredient().getGuid()));
        var unitEntity = unitDao
                .findByGuid(entity.getUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("Unit not found, guid: " +
                        entity.getUnit().getGuid()));
        return new AddedIngredientEntity(
                dbId,
                recipeEntity.id(),
                entity.getGuid(),
                ingredientEntity.id(),
                unitEntity.id(),
                entity.getQuantity()
        );
    }
}

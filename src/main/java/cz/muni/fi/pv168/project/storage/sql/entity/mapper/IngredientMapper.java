package cz.muni.fi.pv168.project.storage.sql.entity.mapper;


import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CustomUnitEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;

public class IngredientMapper implements EntityMapper<IngredientEntity, Ingredient> {

    private final DataAccessObject<CustomUnit> customUnitDao;
    private final EntityMapper<CustomUnitEntity, CustomUnit> categoryMapper;

    public IngredientMapper(
            DataAccessObject<CustomUnit> customUnitDao,
            EntityMapper<CustomUnitEntity, CustomUnit> categoryMapper
    ) {
        this.customUnitDao = customUnitDao;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Ingredient mapToBusiness(IngredientEntity dbIngredient) {
        return new Ingredient(
                dbIngredient.guid(),
                dbIngredient.ingredientName(),
                dbIngredient.nutritionalValue(),
                customUnitDao.findById(dbIngredient.unitId()).orElseThrow(
                        () -> new DataStorageException("Custom unit with id: " + dbIngredient.unitId() + "was not found!")
                )
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

    private IngredientEntity getIngredientEntity(Ingredient entity, Long dbId) {
        var customUnitEntity = customUnitDao
                .findByGuid(entity.getUnitType().)
        return new IngredientEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getNutritionalValue(),

        );
    }
}

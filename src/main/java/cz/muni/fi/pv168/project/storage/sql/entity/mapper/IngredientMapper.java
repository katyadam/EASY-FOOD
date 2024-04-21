package cz.muni.fi.pv168.project.storage.sql.entity.mapper;


import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.Ingredient;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.IngredientEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UserEntity;

public class IngredientMapper implements EntityMapper<IngredientEntity, Ingredient> {

    private final DataAccessObject<UnitEntity> unitDao;
    private final EntityMapper<UnitEntity, CustomUnit> unitMapper;
    private final DataAccessObject<UserEntity> userDao;
    private final UserMapper userMapper;

    public IngredientMapper(
            DataAccessObject<UnitEntity> unitDao,
            EntityMapper<UnitEntity, CustomUnit> unitMapper,
            DataAccessObject<UserEntity> userDao,
            UserMapper userMapper
    ) {
        this.unitDao = unitDao;
        this.unitMapper = unitMapper;
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Override
    public Ingredient mapToBusiness(IngredientEntity dbIngredient) {
        RegisteredUser user = userDao
                .findById(dbIngredient.userID())
                .map(userMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("User not found, id: " +
                        dbIngredient.userID()));
        return new Ingredient(
                dbIngredient.guid(),
                dbIngredient.ingredientName(),
                dbIngredient.nutritionalValue(),
                user
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
        UserEntity userEntity = userDao
                .findByGuid(entity.getUser().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getUser().getGuid()));
        return new IngredientEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getNutritionalValue(),
                userEntity.id()
        );
    }
}

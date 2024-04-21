package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Recipe;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.RecipeEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UserEntity;

public class RecipeMapper implements EntityMapper<RecipeEntity, Recipe> {

    private final DataAccessObject<CategoryEntity> categoryDao;
    private final CategoryMapper categoryMapper;
    private final DataAccessObject<UserEntity> userDao;
    private final UserMapper userMapper;

    public RecipeMapper(
            DataAccessObject<CategoryEntity> categoryDao,
            CategoryMapper categoryMapper,
            DataAccessObject<UserEntity> userDao,
            UserMapper userMapper
    ) {
        this.categoryDao = categoryDao;
        this.categoryMapper = categoryMapper;
        this.userDao = userDao;
        this.userMapper = userMapper;
    }


    @Override
    public Recipe mapToBusiness(RecipeEntity entity) {
        var category = categoryDao
                .findById(entity.categoryId())
                .map(categoryMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("Category not found, id: " +
                        entity.categoryId()));
        RegisteredUser user = userDao
                .findById(entity.userID())
                .map(userMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("User not found, id: " +
                        entity.userID()));
        return new Recipe(
                entity.guid(),
                entity.recipeName(),
                category,
                entity.prepMinutes(),
                entity.portions(),
                entity.description(),
                user);
    }

    @Override
    public RecipeEntity mapNewEntityToDatabase(Recipe entity) {
        var categoryEntity = categoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var userEntity = userDao
                .findByGuid(entity.getUser().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getUser().getGuid()));
        return new RecipeEntity(
                entity.getGuid(),
                categoryEntity.id(),
                entity.getRecipeName(),
                entity.getPrepMinutes(),
                entity.getPortions(),
                entity.getDescription(),
                userEntity.id());
    }

    @Override
    public RecipeEntity mapExistingEntityToDatabase(Recipe entity, Long dbId) {
        var categoryEntity = categoryDao
                .findByGuid(entity.getCategory().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getCategory().getGuid()));
        var userEntity = userDao
                .findByGuid(entity.getUser().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getUser().getGuid()));
        return new RecipeEntity(
                dbId,
                entity.getGuid(),
                categoryEntity.id(),
                entity.getRecipeName(),
                entity.getPrepMinutes(),
                entity.getPortions(),
                entity.getDescription(),
                userEntity.id());
    }
}

package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.Category;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.CategoryEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UserEntity;

public class CategoryMapper implements EntityMapper<CategoryEntity, Category> {

    private final DataAccessObject<UserEntity> userDao;
    private final UserMapper userMapper;

    public CategoryMapper(DataAccessObject<UserEntity> userDao, UserMapper userMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public Category mapToBusiness(CategoryEntity dbCategory) {
        RegisteredUser user = userDao
                .findById(dbCategory.userID())
                .map(userMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("User not found, id: " +
                        dbCategory.userID()));
        return new Category(
                dbCategory.guid(),
                dbCategory.categoryName(),
                dbCategory.color(),
                user
        );
    }

    @Override
    public CategoryEntity mapNewEntityToDatabase(Category entity) {
        return getCategoryEntity(entity, null);
    }

    @Override
    public CategoryEntity mapExistingEntityToDatabase(Category entity, Long dbId) {
        return getCategoryEntity(entity, dbId);
    }

    private CategoryEntity getCategoryEntity(Category entity, Long dbId) {
        UserEntity userEntity = userDao
                .findByGuid(entity.getUser().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getUser().getGuid()));
        return new CategoryEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getColor(),
                userEntity.id()
        );
    }
}

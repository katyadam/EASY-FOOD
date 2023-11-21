package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.AddedIngredient;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.AddedIngredientEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class AddedIngredientSqlRepository implements Repository<AddedIngredient> {

    private final DataAccessObject<AddedIngredientEntity> addedIngredientDao;
    private final EntityMapper<AddedIngredientEntity, AddedIngredient> addedIngredientMapper;

    public AddedIngredientSqlRepository(
            DataAccessObject<AddedIngredientEntity> addedIngredientDao,
            EntityMapper<AddedIngredientEntity, AddedIngredient> addedIngredientMapper) {
        this.addedIngredientDao = addedIngredientDao;
        this.addedIngredientMapper = addedIngredientMapper;
    }

    @Override
    public List<AddedIngredient> findAll() {
        return addedIngredientDao
                .findAll()
                .stream()
                .map(addedIngredientMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(AddedIngredient newEntity) {
        addedIngredientDao.create(addedIngredientMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(AddedIngredient entity) {
        var existingDepartment = addedIngredientDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " + entity.getGuid()));
        var updatedCategory = addedIngredientMapper.mapExistingEntityToDatabase(entity, existingDepartment.id());

        addedIngredientDao.update(updatedCategory);
    }

    @Override
    public void deleteByGuid(String guid) {
        addedIngredientDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        addedIngredientDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return addedIngredientDao.existsByGuid(guid);
    }

    @Override
    public Optional<AddedIngredient> findByGuid(String guid) {
        return addedIngredientDao
                .findByGuid(guid)
                .map(addedIngredientMapper::mapToBusiness);
    }

    @Override
    public Optional<AddedIngredient> findById(Long id) {
        return addedIngredientDao
                .findById(id)
                .map(addedIngredientMapper::mapToBusiness);
    }
}

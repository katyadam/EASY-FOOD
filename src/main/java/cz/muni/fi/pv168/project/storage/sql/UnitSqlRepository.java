package cz.muni.fi.pv168.project.storage.sql;

import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.business.repository.Repository;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class UnitSqlRepository implements Repository<Unit> {

    private final DataAccessObject<UnitEntity> customUnitDao;
    private final EntityMapper<UnitEntity, Unit> customUnitMapper;

    public UnitSqlRepository(
            DataAccessObject<UnitEntity> customUnitDao,
            EntityMapper<UnitEntity, Unit> customUnitMapper) {
        this.customUnitDao = customUnitDao;
        this.customUnitMapper = customUnitMapper;
    }

    @Override
    public List<Unit> findAll() {
        return customUnitDao
                .findAll()
                .stream()
                .map(customUnitMapper::mapToBusiness)
                .toList();
    }

    @Override
    public void create(Unit newEntity) {
        customUnitDao.create(customUnitMapper.mapNewEntityToDatabase(newEntity));
    }

    @Override
    public void update(Unit entity) {
        var existingDepartment = customUnitDao.findByGuid(entity.getGuid())
                .orElseThrow(() -> new DataStorageException("CustomUnit not found, guid: " + entity.getGuid()));
        var updatedCustomUnit = customUnitMapper.mapExistingEntityToDatabase(entity, existingDepartment.id());

        customUnitDao.update(updatedCustomUnit);
    }

    @Override
    public void deleteByGuid(String guid) {
        customUnitDao.deleteByGuid(guid);
    }

    @Override
    public void deleteAll() {
        customUnitDao.deleteAll();
    }

    @Override
    public boolean existsByGuid(String guid) {
        return customUnitDao.existsByGuid(guid);
    }

    @Override
    public boolean existsByName(String name) {
        return findAll().stream()
                .anyMatch(entity -> entity.getName().equals(name));
    }

    @Override
    public Optional<Unit> findByGuid(String guid) {
        return customUnitDao
                .findByGuid(guid)
                .map(customUnitMapper::mapToBusiness);
    }

    @Override
    public Optional<Unit> findById(Long id) {
        return customUnitDao
                .findById(id)
                .map(customUnitMapper::mapToBusiness);
    }
}


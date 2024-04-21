package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.BaseUnit;
import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.business.model.RegisteredUser;
import cz.muni.fi.pv168.project.storage.sql.dao.DataAccessObject;
import cz.muni.fi.pv168.project.storage.sql.dao.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UserEntity;

/**
 * @author Adam Juhas
 */
public class UnitMapper implements EntityMapper<UnitEntity, CustomUnit> {

    //private final BaseUnitDao baseUnitDao;
    //private final EntityMapper<BaseUnitEntity, BaseUnit> baseUnitMapper;
    private final DataAccessObject<UserEntity> userDao;
    private final UserMapper userMapper;

    public UnitMapper(
            DataAccessObject<UserEntity> userDao,
            UserMapper userMapper
    ) {
        //this.baseUnitDao = baseUnitDao;
        //this.baseUnitMapper = baseUnitMapper;
        this.userDao = userDao;
        this.userMapper = userMapper;
    }

    @Override
    public CustomUnit mapToBusiness(UnitEntity dbCustomUnit) {
        /*var baseUnit = baseUnitDao
                .findById(dbCustomUnit.baseUnitId())
                .map(baseUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("BaseUnit not found, id: " +
                        dbCustomUnit.baseUnitId()));*/
        RegisteredUser user = userDao
                .findById(dbCustomUnit.userID())
                .map(userMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("User not found, id: " +
                        dbCustomUnit.userID()));
        return new CustomUnit(
                dbCustomUnit.guid(),
                dbCustomUnit.unitName(),
                dbCustomUnit.abbreviation(),
                dbCustomUnit.amount(),
                BaseUnit.indexToUnit(dbCustomUnit.baseUnitId()),
                user
        );
    }

    @Override
    public UnitEntity mapNewEntityToDatabase(CustomUnit entity) {
        return getUnitEntity(entity, null);
    }

    @Override
    public UnitEntity mapExistingEntityToDatabase(CustomUnit entity, Long dbId) {
        return getUnitEntity(entity, dbId);
    }

    private UnitEntity getUnitEntity(CustomUnit entity, Long dbId) {
        /*var baseUnitEntity = baseUnitDao
                .findByGuid(entity.getBaseUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("BaseUnit not found, guid: " +
                        entity.getBaseUnit().getGuid()));*/
        UserEntity userEntity = userDao
                .findByGuid(entity.getUser().getGuid())
                .orElseThrow(() -> new DataStorageException("Category not found, guid: " +
                        entity.getUser().getGuid()));
        return new UnitEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getAbbreviation(),
                entity.getAmount(),
                entity.getBaseUnit().getIndex(),
                userEntity.id()
        );
    }
}

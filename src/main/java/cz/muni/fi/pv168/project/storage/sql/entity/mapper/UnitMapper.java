package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.BaseUnit;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.dao.BaseUnitDao;
import cz.muni.fi.pv168.project.storage.sql.entity.BaseUnitEntity;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;

/**
 * @author Adam Juhas
 */
public class UnitMapper implements EntityMapper<UnitEntity, Unit> {

    private final BaseUnitDao baseUnitDao;
    private final EntityMapper<BaseUnitEntity, BaseUnit> baseUnitMapper;

    public UnitMapper(
            BaseUnitDao baseUnitDao,
            EntityMapper<BaseUnitEntity, BaseUnit> baseUnitMapper
    ) {
        this.baseUnitDao = baseUnitDao;
        this.baseUnitMapper = baseUnitMapper;
    }

    @Override
    public Unit mapToBusiness(UnitEntity dbCustomUnit) {
        var baseUnit = baseUnitDao
                .findById(dbCustomUnit.baseUnitId())
                .map(baseUnitMapper::mapToBusiness)
                .orElseThrow(() -> new DataStorageException("BaseUnit not found, id: " +
                        dbCustomUnit.baseUnitId()));
        return new Unit(
                dbCustomUnit.guid(),
                dbCustomUnit.unitName(),
                dbCustomUnit.abbreviation(),
                dbCustomUnit.amount(),
                baseUnit
        );
    }

    @Override
    public UnitEntity mapNewEntityToDatabase(Unit entity) {
        return getUnitEntity(entity, null);
    }

    @Override
    public UnitEntity mapExistingEntityToDatabase(Unit entity, Long dbId) {
        return getUnitEntity(entity, dbId);
    }

    private UnitEntity getUnitEntity(Unit entity, Long dbId) {
        var baseUnitEntity = baseUnitDao
                .findByGuid(entity.getBaseUnit().getGuid())
                .orElseThrow(() -> new DataStorageException("BaseUnit not found, guid: " +
                        entity.getBaseUnit().getGuid()));
        return new UnitEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getAbbreviation(),
                entity.getAmount(),
                baseUnitEntity.id()
        );
    }
}

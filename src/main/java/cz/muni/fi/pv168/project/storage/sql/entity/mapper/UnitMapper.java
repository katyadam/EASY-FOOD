package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.BaseUnits;
import cz.muni.fi.pv168.project.business.model.Unit;
import cz.muni.fi.pv168.project.storage.DataStorageException;
import cz.muni.fi.pv168.project.storage.sql.entity.UnitEntity;

/**
 * @author Adam Juhas
 */
public class UnitMapper implements EntityMapper<UnitEntity, Unit> {
    @Override
    public Unit mapToBusiness(UnitEntity dbCustomUnit) {
        return new Unit(
                dbCustomUnit.guid(),
                dbCustomUnit.unitName(),
                dbCustomUnit.abbreviation(),
                dbCustomUnit.amount(),
                BaseUnits.getBaseUnitList().stream()
                        .filter(unit -> unit.getName().equals(dbCustomUnit.baseUnit()))
                        .findFirst().orElseThrow(
                                () -> new DataStorageException(dbCustomUnit.baseUnit() + " is not valid base unit!")
                        )
        );
    }

    @Override
    public UnitEntity mapNewEntityToDatabase(Unit entity) {
        return getCategoryEntity(entity, null);
    }

    @Override
    public UnitEntity mapExistingEntityToDatabase(Unit entity, Long dbId) {
        return getCategoryEntity(entity, dbId);
    }

    private static UnitEntity getCategoryEntity(Unit entity, Long dbId) {
        return new UnitEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getAbbreviation(),
                entity.getAmount(),
                entity.getBaseUnit().getName()
        );
    }
}

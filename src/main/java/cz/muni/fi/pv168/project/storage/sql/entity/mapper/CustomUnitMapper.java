package cz.muni.fi.pv168.project.storage.sql.entity.mapper;

import cz.muni.fi.pv168.project.business.model.BaseUnits;
import cz.muni.fi.pv168.project.business.model.CustomUnit;
import cz.muni.fi.pv168.project.storage.sql.entity.CustomUnitEntity;

/**
 * @author Adam Juhas
 */
public class CustomUnitMapper implements EntityMapper<CustomUnitEntity, CustomUnit> {
    @Override
    public CustomUnit mapToBusiness(CustomUnitEntity dbCustomUnit) {
        return new CustomUnit(
                dbCustomUnit.guid(),
                dbCustomUnit.unitName(),
                dbCustomUnit.abbreviation(),
                dbCustomUnit.amount(),
                BaseUnits.GRAM //TODO
        );
    }

    @Override
    public CustomUnitEntity mapNewEntityToDatabase(CustomUnit entity) {
        return getCategoryEntity(entity, null);
    }

    @Override
    public CustomUnitEntity mapExistingEntityToDatabase(CustomUnit entity, Long dbId) {
        return getCategoryEntity(entity, dbId);
    }

    private static CustomUnitEntity getCategoryEntity(CustomUnit entity, Long dbId) {
        return new CustomUnitEntity(
                dbId,
                entity.getGuid(),
                entity.getName(),
                entity.getAbbreviation(),
                entity.getAmount(),
                entity.getBaseUnit().toString() //TODO
        );
    }
}

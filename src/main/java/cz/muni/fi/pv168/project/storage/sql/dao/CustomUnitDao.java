package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.db.ConnectionHandler;
import cz.muni.fi.pv168.project.storage.sql.entity.CustomUnitEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class CustomUnitDao implements DataAccessObject<CustomUnitEntity> {
    private final Supplier<ConnectionHandler> connections;

    public CustomUnitDao(Supplier<ConnectionHandler> connections) {
        this.connections = connections;
    }

    @Override
    public CustomUnitEntity create(CustomUnitEntity newCustomUnit) {
        var sql = """
                INSERT INTO CustomUnit(
                    guid,
                    unitName,
                    abbreviation,
                    amount,
                    baseUnit
                )
                VALUES (?, ?, ?, ?, ?);
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, newCustomUnit.guid());
            statement.setString(2, newCustomUnit.unitName());
            statement.setString(3, newCustomUnit.abbreviation());
            statement.setDouble(4, newCustomUnit.amount());
            statement.setString(5, newCustomUnit.baseUnit());
            statement.executeUpdate();

            try (var keyResultSet = statement.getGeneratedKeys()) {
                long customUnitId;

                if (keyResultSet.next()) {
                    customUnitId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + newCustomUnit);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + newCustomUnit);
                }

                return findById(customUnitId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + newCustomUnit, ex);
        }
    }

    @Override
    public Collection<CustomUnitEntity> findAll() {
        var sql = """
                SELECT id,
                    guid,
                    unitName,
                    abbreviation,
                    amount,
                    baseUnit
                FROM CustomUnit
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            List<CustomUnitEntity> customUnits = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var customUnit = customUnitFromResultSet(resultSet);
                    customUnits.add(customUnit);
                }
            }

            return customUnits;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all customUnits", ex);
        }
    }

    @Override
    public Optional<CustomUnitEntity> findById(long id) {
        var sql = """
                SELECT id,
                    guid,
                    unitName,
                    abbreviation,
                    amount,
                    baseUnit
                FROM CustomUnit
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(customUnitFromResultSet(resultSet));
            } else {
                // recipe not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load customUnit by id", ex);
        }
    }

    @Override
    public Optional<CustomUnitEntity> findByGuid(String guid) {
        var sql = """
                SELECT id,
                    guid,
                    unitName,
                    abbreviation,
                    amount,
                    baseUnit
                FROM CustomUnit
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(customUnitFromResultSet(resultSet));
            } else {
                // recipe not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load customUnit by id", ex);
        }
    }

    @Override
    public CustomUnitEntity update(CustomUnitEntity entity) {
        var sql = """
                UPDATE CustomUnit
                SET unitName = ?,
                    abbreviation = ?,
                    amount = ?,
                    baseUnit = ?
                WHERE id = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.unitName());
            statement.setString(2, entity.abbreviation());
            statement.setDouble(3, entity.amount());
            statement.setString(4, entity.baseUnit());
            statement.setLong(5, entity.id());
            statement.executeUpdate();

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("CustomUnit not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 CustomUnit (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update CustomUnit: " + entity, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = "DELETE FROM CustomUnit WHERE guid = ?";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("CustomUnit not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 CustomUnit (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete CustomUnit, guid: " + guid, ex);
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM CustomUnit";
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all CustomUnits", ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM CustomUnit
                WHERE guid = ?
                """;
        try (
                var connection = connections.get();
                var statement = connection.use().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if CustomUnit exists: " + guid, ex);
        }
    }


    private static CustomUnitEntity customUnitFromResultSet(ResultSet resultSet) throws SQLException {
        return new CustomUnitEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getString("unitName"),
                resultSet.getString("abbreviation"),
                resultSet.getDouble("amount"),
                resultSet.getString("baseUnit")
        );
    }
}

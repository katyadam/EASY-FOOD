package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.entity.RegisteredUserEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RegisteredUserDao implements DataAccessObject<RegisteredUserEntity>{
    private final Connection connection;

    public RegisteredUserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public RegisteredUserEntity create(RegisteredUserEntity entity) {
        var sql = """
                INSERT INTO RegisteredUser (
                    guid,
                    name,
                    password
                ) VALUES (?, ?, ?);
                """;
        try(
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.guid());
            statement.setString(2,entity.name());
            statement.setString(3,entity.password());
            statement.execute();
            try (var keyResultSet = statement.getGeneratedKeys()) {
                long recipeId;

                if (keyResultSet.next()) {
                    recipeId = keyResultSet.getLong(1);
                } else {
                    throw new DataStorageException("Failed to fetch generated key for: " + entity);
                }
                if (keyResultSet.next()) {
                    throw new DataStorageException("Multiple keys returned for: " + entity);
                }

                return findById(recipeId).orElseThrow();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to store: " + entity, ex);
        }
    }




    @Override
    public Collection<RegisteredUserEntity> findAll() {
        var sql = """
                SELECT 
                    id,
                    guid,
                    name,
                    password
                FROM RegisteredUser
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            List<RegisteredUserEntity> users = new ArrayList<>();
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var user = userFromResultSet(resultSet);
                    users.add(user);
                }
            }

            return users;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load all employees", ex);
        }
    }

    @Override
    public Collection<RegisteredUserEntity> findAll(Long userId) {
        return List.of();
    }

    @Override
    public Optional<RegisteredUserEntity> findById(long id) {
        var sql = """
                SELECT 
                    id,
                    guid,
                    name,
                    password
                FROM RegisteredUser
                WHERE id = ?
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(userFromResultSet(resultSet));
            } else {
                // not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load User by id", ex);
        }
    }

    @Override
    public Optional<RegisteredUserEntity> findByGuid(String guid) {
        var sql = """
                SELECT 
                    id,
                    guid,
                    name,
                    password
                FROM RegisteredUser
                WHERE guid = ?
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(userFromResultSet(resultSet));
            } else {
                // not found
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to load User by id", ex);
        }
    }

    @Override
    public RegisteredUserEntity update(RegisteredUserEntity entity) {
        var sql = """
                UPDATE RegisteredUser
                SET name = ?,
                    password = ?
                WHERE id = ?
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.name());
            statement.setString(2, entity.password());
            statement.setLong(3, entity.id());
            statement.executeUpdate();

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("User not found, id: " + entity.id());
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 user (rows=%d) has been updated: %s"
                        .formatted(rowsUpdated, entity));
            }
            return entity;
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to update user: " + entity, ex);
        }
    }

    @Override
    public void deleteByGuid(String guid) {
        var sql = " DELETE FROM RegisteredUser WHERE guid = ? ";
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new DataStorageException("User not found, guid: " + guid);
            }
            if (rowsUpdated > 1) {
                throw new DataStorageException("More then 1 user (rows=%d) has been deleted: %s"
                        .formatted(rowsUpdated, guid));
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete user, guid: " + guid, ex);
        }
    }


    @Override
    public void deleteAll() {
        var sql = "DELETE FROM RegisteredUser";
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to delete all recipes", ex);
        }
    }

    @Override
    public boolean existsByGuid(String guid) {
        var sql = """
                SELECT id
                FROM RegisteredUser
                WHERE guid = ?
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, guid);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if user exists: " + guid, ex);
        }
    }

    public boolean existsByUsername(String username) {
        System.out.println(username + "\n");
        var sql = """
                SELECT 
                    id,
                    guid,
                    name,
                    password
                FROM RegisteredUser
                WHERE name = ?
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, username);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if user exists by name: " + username, ex);
        }
    }

    public Optional<RegisteredUserEntity> existsByLogin(String name, String password) {
        var sql = """
                SELECT 
                    id,
                    guid,
                    name,
                    password
                FROM RegisteredUser
                WHERE name = ? AND password = ?
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, name);
            statement.setString(2, password);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(userFromResultSet(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException ex) {
            throw new DataStorageException("Failed to check if user exists login ", ex);
        }
    }



    private static RegisteredUserEntity userFromResultSet(ResultSet resultSet) throws SQLException {
        return new RegisteredUserEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getString("name"),
                resultSet.getString("password")
        );
    }
}



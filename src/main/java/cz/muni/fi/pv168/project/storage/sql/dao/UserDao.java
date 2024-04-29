package cz.muni.fi.pv168.project.storage.sql.dao;

import cz.muni.fi.pv168.project.storage.sql.entity.UserEntity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UserDao implements DataAccessObject<UserEntity>{
    private final Connection connection;

    public UserDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserEntity create(UserEntity entity) {
        var sql = """
                INSERT INTO User (
                    guid,
                    username,
                    password
                ) 
                VALUES (?, ?, ?);
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
    public Collection<UserEntity> findAll() {
        var sql = """
                SELECT id,
                guid,
                name,
                password
                FROM User
                """;
        try (
                var statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            List<UserEntity> users = new ArrayList<>();
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
    public Collection<UserEntity> findAll(Long userId) {
        return List.of();
    }

    @Override
    public Optional<UserEntity> findById(long id) {
        var sql = """
                SELECT id,
                    guid,
                    name,
                    password,
                FROM User
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
    public Optional<UserEntity> findByGuid(String guid) {
        var sql = """
                SELECT id,
                    guid,
                    name,
                    password,
                FROM User
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
    public UserEntity update(UserEntity entity) {
        var sql = """
                UPDATE User
                SET name = ?,
                    password = ?,
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
        var sql = " DELETE FROM User WHERE guid = ? ";
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
        var sql = "DELETE FROM User";
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
                FROM User
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
                SELECT id
                FROM "User"
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

    public Optional<UserEntity> existsByLogin(String name, String password) {
        var sql = """
                SELECT id,
                    guid,
                    name,
                    password
                FROM User
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



    private static UserEntity userFromResultSet(ResultSet resultSet) throws SQLException {
        return new UserEntity(
                resultSet.getLong("id"),
                resultSet.getString("guid"),
                resultSet.getString("name"),
                resultSet.getString("password")
        );
    }
}



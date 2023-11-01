package cz.muni.fi.pv168.project.repository;

import java.util.List;

/**
 * Represents a repository for any entity.
 *
 * @param <T> the type of the entity.
 */
public interface Repository<T> {

    /**
     * Find all entities.
     */
    List<T> findAll();

    /**
     * Persist given {@code newEntity}.
     */
    void create(T newEntity);

    /**
     * Update given {@code entity}.
     */
    void update(T entity);

    /**
     * Delete entity with given {@code guid}.
     */
    void deleteByGuid(String guid);

    /**
     * Delete all entities.
     */
    void deleteAll();

    /**
     * Check if there is an existing Entity with given {@code guid}
     *
     * @return true, if an Entity with given {@code} is found, false otherwise
     */
    boolean existsByGuid(String guid);
}

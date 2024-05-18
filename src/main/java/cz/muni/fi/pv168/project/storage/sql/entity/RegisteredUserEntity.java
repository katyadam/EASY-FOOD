package cz.muni.fi.pv168.project.storage.sql.entity;

public record RegisteredUserEntity(
    Long id,
    String guid,
    String name,
    String password
)
{}

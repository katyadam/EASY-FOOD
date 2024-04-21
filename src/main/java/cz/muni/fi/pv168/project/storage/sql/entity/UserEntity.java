package cz.muni.fi.pv168.project.storage.sql.entity;

public record UserEntity(
    Long id,
    String guid,
    String name,
    String password
)
{}

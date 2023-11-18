package cz.muni.fi.pv168.project.storage.sql.entity;

import java.time.LocalDate;

public record CategoryEntity(
        Long id,
        String guid,
        String categoryName,
        String color,
        LocalDate birthDate) {

    public CategoryEntity(Long id, String guid, String categoryName, String color, LocalDate birthDate) {
        this.id = id;
        this.guid = guid;
        this.categoryName = categoryName;
        this.color = color;
        this.birthDate = birthDate;
    }

    public CategoryEntity(String guid, String categoryName, String color, LocalDate birthDate) {
        this(null, guid, categoryName, color, birthDate);
    }
}

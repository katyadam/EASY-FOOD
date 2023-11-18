package cz.muni.fi.pv168.project.storage.sql.entity;

import java.awt.*;
import java.time.LocalDate;

public record CategoryEntity(
        Long id,
        String guid,
        String categoryName,
        Color color) {

    public CategoryEntity(Long id, String guid, String categoryName, Color color) {
        this.id = id;
        this.guid = guid;
        this.categoryName = categoryName;
        this.color = color;
    }

    public CategoryEntity(String guid, String categoryName, Color color) {
        this(null, guid, categoryName, color);
    }
}

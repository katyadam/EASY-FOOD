package cz.muni.fi.pv168.project.storage.sql.entity;

import java.awt.*;

public record CategoryEntity(
        Long id,
        String guid,
        String categoryName,
        Color color,
        Long userID) {

    public CategoryEntity(Long id, String guid, String categoryName, Color color, Long userID) {
        this.id = id;
        this.guid = guid;
        this.categoryName = categoryName;
        this.color = color;
        this.userID = userID;
    }

    public CategoryEntity(String guid, String categoryName, Color color, Long userID) {
        this(null, guid, categoryName, color, userID);
    }
}

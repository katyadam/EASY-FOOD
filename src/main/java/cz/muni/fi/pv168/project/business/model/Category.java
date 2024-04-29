package cz.muni.fi.pv168.project.business.model;

import java.awt.*;

public class Category extends Entity {
    private Color color;
    private RegisteredUser user;

    public Category() {
    }

    public Category(
            String guid,
            String categoryName,
            Color color,
            RegisteredUser user
    ) {
        super(guid);
        this.name = categoryName;
        this.color = color;
        this.user = user;
    }

    public Category(String name, Color color,RegisteredUser user) {
        this.name = name;
        this.color = color;
        this.user = user;
    }

    public String getColorCode() {
        return String.format("%d,%d,%d", color.getRed(), color.getGreen(), color.getBlue());
    }
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return name;
    }

    public RegisteredUser getUser() {
        return user;
    }
    public void setUser(RegisteredUser user) {
        this.user = user;
    }
}

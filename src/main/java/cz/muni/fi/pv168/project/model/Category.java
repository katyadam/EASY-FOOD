package cz.muni.fi.pv168.project.model;

import java.awt.*;

public class Category {
    private String name;

    private Color color;

    public Category(String name, Color color) {
        name = name;
        color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}

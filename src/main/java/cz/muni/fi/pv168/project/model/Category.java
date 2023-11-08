package cz.muni.fi.pv168.project.model;

import java.awt.*;

public class Category extends Entity {
    private String name;
    private Color color;

    public Category() {
    }

    public Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getColorCode() {
        return String.format("%d,%d,%d", color.getRed(), color.getGreen(), color.getBlue());
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

    @Override
    public String toString() {
        return name;
    }
}

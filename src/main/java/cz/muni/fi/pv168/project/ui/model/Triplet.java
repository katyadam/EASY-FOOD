package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

public record Triplet(Ingredient ingredient, double value, Unit unit) {
}

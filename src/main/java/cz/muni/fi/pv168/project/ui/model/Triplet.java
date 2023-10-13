package cz.muni.fi.pv168.project.ui.model;

import cz.muni.fi.pv168.project.model.Ingredient;
import cz.muni.fi.pv168.project.model.Unit;

/**
 * @author Filip Skvara
 */
public class Triplet {

    private Ingredient a;
    private double b;
    private Unit c;

    public Triplet(Ingredient a, double b, Unit c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Ingredient getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Unit getC() {
        return c;
    }
}

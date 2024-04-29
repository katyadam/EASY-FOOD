package cz.muni.fi.pv168.project.business.model;

public class Ingredient extends Entity {
    private int nutritionalValue;
    private RegisteredUser user;
    public Ingredient() {
    }

    public Ingredient(
            String guid,
            String name,
            int nutritionalValue,
            RegisteredUser user
    ) {
        super(guid);
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.user = user;
    }

    public Ingredient(String name, int nutritionalValue, RegisteredUser user) {
        this.name = name;
        this.nutritionalValue = nutritionalValue;
        this.user = user;
    }

    public void setNutritionalValue(int nutritionalValue) {
        this.nutritionalValue = nutritionalValue;
    }


    public int getNutritionalValue() {
        return nutritionalValue;
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

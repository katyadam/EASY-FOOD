package cz.muni.fi.pv168.project.business.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegisteredUser extends Entity{
    private String password;
    private String name;

    List<Recipe> recipes = new ArrayList<Recipe>();
    List<Ingredient> ingredients = new ArrayList<Ingredient>();
    List<Category> categories = new ArrayList<Category>();

    public RegisteredUser( String guid, String name,  String password ) {
        super(guid);
        this.name = name;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
    }
    public void removeRecipe(Recipe recipe) {
        recipes.remove(recipe);
    }
    public List<Ingredient> getIngredients() {
        return ingredients;
    }
    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }
    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
    }
    public List<Category> getCategories() {
        return categories;
    }
    public void addCategory(Category category) {
        categories.add(category);
    }
    public void removeCategory(Category category) {
        categories.remove(category);
    }


}

package cz.muni.fi.pv168.project.data;


import cz.muni.fi.pv168.project.model.*;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.temporal.ChronoUnit.DAYS;

public final class TestDataGenerator {

    private static final List<String> RECIPE_NAMES = List.of("Smažák", "Pizza", "Vývar", "Guláš", "Mýchané vajíčka",
            "Hranolky", "Řízek", "Americké brambory", "Pečené kuře", "Grilled cheese");

    private static final List<String> INGREDIENT_NAMES = List.of("Paprika", "Brambory", "Máslo", "Rýže", "Vejce",
            "Hovězí maso", "Kuřecí maso", "Eidam", "Mouka", "Špagety", "Bílý jogurt", "Fazole", "Sůl", "Cukr", "Pepř", "Kari");

    public static final List<String> NATIONAL_CATEGORIES = List.of("Italian", "French", "Chinese",
            "Mexican", "Japanese", "Indian", "Thai", "Greek", "Spanish",
            "Middle Eastern");

    public static final List<String> FOOD_CATEGORIES = List.of("Appetizers", "Pizza", "Soup", "Stew", "Egg Dishes",
            "Side Dishes", "Potato Dishes", "Chicken", "Sandwiches", "Vegan", "Non-vegan", "Main Course", "Lunch", "Desert");

    private static final List<String> CUSTOM_UNIT_NAMES = new ArrayList<>();
    private static final List<String> CUSTOM_UNIT_ABBREVIATIONS = new ArrayList<>();

    public List<BaseUnits> getBaseUnits() {
        return baseUnits;
    }

    public List<CustomUnit> getTestCustomUnits() {
        return testCustomUnits;
    }

    static {
        CUSTOM_UNIT_NAMES.add("pinch");
        CUSTOM_UNIT_ABBREVIATIONS.add("pn");
        CUSTOM_UNIT_NAMES.add("slice");
        CUSTOM_UNIT_ABBREVIATIONS.add("sl");
        CUSTOM_UNIT_NAMES.add("handful");
        CUSTOM_UNIT_ABBREVIATIONS.add("hd");
        CUSTOM_UNIT_NAMES.add("sprig");
        CUSTOM_UNIT_ABBREVIATIONS.add("spg");
        CUSTOM_UNIT_NAMES.add("strip");
        CUSTOM_UNIT_ABBREVIATIONS.add("str");
        CUSTOM_UNIT_NAMES.add("slab");
        CUSTOM_UNIT_ABBREVIATIONS.add("slb");
        CUSTOM_UNIT_NAMES.add("bunch");
        CUSTOM_UNIT_ABBREVIATIONS.add("bnch");
        CUSTOM_UNIT_NAMES.add("packet");
        CUSTOM_UNIT_ABBREVIATIONS.add("pkt");
        // Add more custom units as needed
    }

    private final List<Recipe> testRecipes;
    private final List<Ingredient> testIngredients;
    private final List<BaseUnits> baseUnits = BaseUnits.getBaseUnitList();
    private final List<CustomUnit> testCustomUnits;


    public TestDataGenerator() {
        this.testCustomUnits = createTestCustomUnits(5);
        this.testIngredients = createTestIngredients(10);
        this.testRecipes = createTestRecipes(10);

    }


//    private static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1950, JANUARY, 1);
//    private static final LocalDate MAX_BIRTH_DATE = LocalDate.of(2002, DECEMBER, 31);

    private final Random random = new Random(2L);

    private List<Recipe> createTestRecipes(int count) {
        return Stream
                .generate(this::createTestRecipe)
                .limit(count)
                .collect(Collectors.toList());
    }


    public List<Ingredient> createTestIngredients(int count) {
        return Stream
                .generate(this::createTestIngredient)
                .limit(count)
                .collect(Collectors.toList());
    }

    public List<CustomUnit> createTestCustomUnits(int count) {
        return Stream
                .generate(this::createTestCustomUnit)
                .limit(count)
                .collect(Collectors.toList());

    }

    private CustomUnit createTestCustomUnit() {
        int position = random.nextInt(CUSTOM_UNIT_NAMES.size());
        String customUnitName = CUSTOM_UNIT_NAMES.get(position);
        String customUnitAbbreviation = CUSTOM_UNIT_ABBREVIATIONS.get(position);
        double amount = random.nextDouble() * 100;
        BaseUnits baseUnit = selectRandom(baseUnits);

        return new CustomUnit(customUnitName, customUnitAbbreviation, amount, baseUnit);
    }

    public Recipe createTestRecipe() {

        String recipeName = selectRandom(RECIPE_NAMES);
        PreparationTime preparationTime = new PreparationTime(LocalTime.now().getHour(), LocalTime.now().getMinute());
        int portions = random.nextInt(10);
        return new Recipe(recipeName, createTestCategory(), 0, portions, preparationTime);
    }

    private Ingredient createTestIngredient() {
        String ingredientName = selectRandom(INGREDIENT_NAMES);
        int nutritionalValue = random.nextInt(10000);
        Unit unit = selectRandom(testCustomUnits);
        return new Ingredient(ingredientName, nutritionalValue, unit);
    }

    private Category createTestCategory() {
        List<String> ALL_CATEGORIES = new ArrayList<>();
        ALL_CATEGORIES.addAll(NATIONAL_CATEGORIES);
        ALL_CATEGORIES.addAll(FOOD_CATEGORIES);
        String categoryName = selectRandom(ALL_CATEGORIES);
        Color categoryColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return new Category(categoryName, categoryColor);
    }


    private <T> T selectRandom(List<T> data) {
        int index = random.nextInt(data.size());
        return data.get(index);
    }

    private LocalDate selectRandomLocalDate(LocalDate min, LocalDate max) {
        int maxDays = Math.toIntExact(DAYS.between(min, max) + 1);
        int days = random.nextInt(maxDays);
        return min.plusDays(days);
    }

    public List<Recipe> getTestRecipes() {
        return testRecipes;
    }

    public List<Ingredient> getTestIngredients() {
        return testIngredients;
    }


}

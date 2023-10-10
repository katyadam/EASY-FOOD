package cz.muni.fi.pv168.project.data;


import cz.muni.fi.pv168.project.model.Category;
import cz.muni.fi.pv168.project.model.Recipe;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.time.Month.DECEMBER;
import static java.time.Month.JANUARY;
import static java.time.temporal.ChronoUnit.DAYS;

public final class TestDataGenerator {

    private static final List<String> RECIPE_NAMES = List.of("Smažák", "Pizza", "Vývar", "Guláš", "Mýchané vajíčka",
            "Hranolky", "Řízek", "Americké brambory", "Pečené kuře", "Grilled cheese");

//    private static final Map<Gender, List<String>> LAST_NAMES = Map.of(
//            Gender.MALE, List.of("Novák", "Novotný", "Dvořák", "Černý", "Procházka", "Šťastný", "Veselý", "Horák", "Němec", "Pokorný"),
//            Gender.FEMALE, List.of("Nováková", "Novotná", "Dvořáková", "Černá", "Procházková", "Šťastná", "Veselá", "Horáková", "Němcová", "Pokorná")
//    );

//    private static final List<Department> DEPARTMENTS = List.of(
//            new Department("IT", "007"),
//            new Department("Sales", "666"),
//            new Department("HR", "112")
//    );

//    private static final LocalDate MIN_BIRTH_DATE = LocalDate.of(1950, JANUARY, 1);
//    private static final LocalDate MAX_BIRTH_DATE = LocalDate.of(2002, DECEMBER, 31);

    private final Random random = new Random(2L);

    public Recipe createTestRecipe() {

        String recipeName = selectRandom(RECIPE_NAMES);
        LocalTime preparationTime = LocalTime.now();
        int portions = random.nextInt(10);
        Category category = new Category("None", new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256)));

        return new Recipe(recipeName, preparationTime, portions, null);
    }

    public List<Recipe> createTestEmployees(int count) {
        return Stream
                .generate(this::createTestRecipe)
                .limit(count)
                .collect(Collectors.toList());
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
}

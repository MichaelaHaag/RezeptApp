package model;

import util.IPersistable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Rezept Klasse: Definiert ein Rezept */
public class Recipe implements IPersistable {
    private final UUID recipeID;
    private String title;
    private ArrayList<Category> categories;
    private ArrayList<Ingredient> ingredients;
    private String description;
    private Difficulty difficulty;
    private Picture picture;

    public Recipe(String title, ArrayList<Category> categories, ArrayList<Ingredient> ingredients, String description, Difficulty difficulty, Picture picture) {
        this.recipeID = UUID.randomUUID();
        this.title = title;
        this.categories = categories;
        this.ingredients = ingredients;
        this.description = description;
        this.difficulty = difficulty;
        this.picture = picture;
    }

    public Recipe(UUID recipeID, String title, ArrayList<Category> categories, ArrayList<Ingredient> ingredients, String description, Difficulty difficulty) {
        this.recipeID = recipeID;
        this.title = title;
        this.categories = categories;
        this.ingredients = ingredients;
        this.description = description;
        this.difficulty = difficulty;
    }

    public Recipe(UUID recipeID, String title, ArrayList<Category> categories, ArrayList<Ingredient> ingredients, String description, Difficulty difficulty, Picture picture) {
        this.recipeID = recipeID;
        this.title = title;
        this.categories = categories;
        this.ingredients = ingredients;
        this.description = description;
        this.difficulty = difficulty;
        this.picture = picture;
    }

    public Recipe() {
        this.recipeID = UUID.randomUUID();
    }

    public enum CSVPositions {
        RECIPEID,
        TITLE,
        DIFFICULTY,
        DESCRIPTION
    }

    public UUID getUUID() {
        return recipeID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"RecipeID","Title","Difficulty","Description"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[CSVPositions.values().length];
        data[CSVPositions.RECIPEID.ordinal()] = String.valueOf(this.recipeID);
        data[CSVPositions.TITLE.ordinal()] = String.valueOf(this.title);
        data[CSVPositions.DIFFICULTY.ordinal()] = String.valueOf(this.difficulty.getDifficultyID());
        data[CSVPositions.DESCRIPTION.ordinal()] = String.valueOf(this.description);
        return data;
    }

    public List<String[]> getCategoriesCSV() {
        List<String[]> csvData = new ArrayList<>();
        for (Category category : this.getCategories()) {
            String[] data = new String[2];
            data[0] = String.valueOf(this.recipeID);
            data[1] = String.valueOf(category.getUUID());
            csvData.add(data);

        }
       return csvData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setLabels(ArrayList<Category> labels) {
        this.categories = labels;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }
}

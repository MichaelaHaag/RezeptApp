package model;

import util.IPersistable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Rezept Klasse: Definiert ein Rezept */
public class Recipe implements IPersistable {
    private final UUID recipeID;
    private String title;
    private ArrayList<Kategorie> categories;
    private ArrayList<Zutat> ingredients;
    private String description;
    private Schwierigkeit schwierigkeit;
    private Bilder bilder;

    public Recipe(String title, ArrayList<Kategorie> categories, ArrayList<Zutat> ingredients, String description, Schwierigkeit schwierigkeit, Bilder bilder) {
        this.recipeID = UUID.randomUUID();
        this.title = title;
        this.categories = categories;
        this.ingredients = ingredients;
        this.description = description;
        this.schwierigkeit = schwierigkeit;
        this.bilder = bilder;
    }

    public Recipe(UUID recipeID, String title, ArrayList<Kategorie> categories, ArrayList<Zutat> ingredients, String description, Schwierigkeit schwierigkeit) {
        this.recipeID = recipeID;
        this.title = title;
        this.categories = categories;
        this.ingredients = ingredients;
        this.description = description;
        this.schwierigkeit = schwierigkeit;
    }

    public Recipe(UUID recipeID, String title, ArrayList<Kategorie> categories, ArrayList<Zutat> ingredients, String description, Schwierigkeit schwierigkeit, Bilder bilder) {
        this.recipeID = recipeID;
        this.title = title;
        this.categories = categories;
        this.ingredients = ingredients;
        this.description = description;
        this.schwierigkeit = schwierigkeit;
        this.bilder = bilder;
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
        data[CSVPositions.DIFFICULTY.ordinal()] = String.valueOf(this.schwierigkeit.getDifficultyID());
        data[CSVPositions.DESCRIPTION.ordinal()] = String.valueOf(this.description);
        return data;
    }

    public List<String[]> getCategoriesCSV() {
        List<String[]> csvData = new ArrayList<>();
        for (Kategorie kategorie : this.getCategories()) {
            String[] data = new String[2];
            data[0] = String.valueOf(this.recipeID);
            data[1] = String.valueOf(kategorie.getUUID());
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

    public ArrayList<Kategorie> getCategories() {
        return categories;
    }

    public void setLabels(ArrayList<Kategorie> labels) {
        this.categories = labels;
    }

    public ArrayList<Zutat> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Zutat> ingredients) {
        this.ingredients = ingredients;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Schwierigkeit getDifficulty() {
        return schwierigkeit;
    }

    public void setDifficulty(Schwierigkeit schwierigkeit) {
        this.schwierigkeit = schwierigkeit;
    }

    public Bilder getPicture() {
        return bilder;
    }

    public void setPicture(Bilder bilder) {
        this.bilder = bilder;
    }
}

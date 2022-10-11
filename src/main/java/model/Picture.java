package model;

import util.IPersistable;

import java.util.UUID;

/* Bild Klasse: Definiert ein Bild eines Rezeptes */
public class Picture implements IPersistable {
    private final UUID pictureID;
    private final UUID recipeID;
    private String path;

    public Picture(UUID pictureID, UUID recipeID, String path) {
        this.pictureID = pictureID;
        this.recipeID = recipeID;
        this.path = path;
    }

    public Picture(UUID recipeID, String path) {
        this.pictureID = UUID.randomUUID();
        this.recipeID = recipeID;
        this.path = path;
    }

    public enum CSVPositions {
        PICTUREID,
        RECIPEID,
        PATH
    }

    public UUID getUUID() {
        return pictureID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"PictureID", "ReipeID", "Path"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Picture.CSVPositions.values().length];
        data[CSVPositions.PICTUREID.ordinal()] = String.valueOf(this.pictureID);
        data[CSVPositions.RECIPEID.ordinal()] = String.valueOf(this.recipeID);
        data[CSVPositions.PATH.ordinal()] = String.valueOf(this.path);
        return data;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UUID getRecipeID() {
        return recipeID;
    }

}

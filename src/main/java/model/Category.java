package model;

import util.IPersistable;

import java.util.UUID;

/* Kategorie Klasse: Definiert eine Kategorie eines Rezeptes */
public class Category implements IPersistable {
    private final UUID categoryID;
    private String name;
    private String tag;
    private String description;

    public Category(String name, String tag, String description) {
        this.categoryID = UUID.randomUUID();
        this.name = name;
        this.tag = tag;
        this.description = description;
    }

    public Category(UUID categoryID, String name, String tag,String description) {
        this.categoryID = categoryID;
        this.name = name;
        this.tag = tag;
        this.description = description;
    }

    public enum CSVPositions {
        CATEGORYID,
        NAME,
        TAG,
        DESCRIPTION
    }
    public String toString() {
        return this.name;
    }

    @Override
    public Object getUUID() {
        return categoryID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"CategoryID","Name","Tag","Description"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Category.CSVPositions.values().length];
        data[CSVPositions.CATEGORYID.ordinal()] = String.valueOf(this.categoryID);
        data[CSVPositions.NAME.ordinal()] = String.valueOf(this.name);
        data[CSVPositions.TAG.ordinal()] = String.valueOf(this.tag);
        data[Category.CSVPositions.DESCRIPTION.ordinal()] = String.valueOf(this.description);
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

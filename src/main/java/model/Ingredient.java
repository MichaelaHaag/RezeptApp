package model;

import util.IPersistable;

import java.util.UUID;

/* Zutat Klasse: Definiert eine Zutat eines Rezeptes */
public class Ingredient implements IPersistable {
    private final UUID ingredientID;
    private UUID recipeID;
    private long amount;
    private Unit unit;
    private String name;

    public Ingredient(UUID recipeID, long amount, Unit unit, String name) {
        this.ingredientID = UUID.randomUUID();
        this.recipeID = recipeID;
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    public Ingredient(UUID ingredientID, UUID recipeID, long amount, Unit unit, String name) {
        this.ingredientID = ingredientID;
        this.recipeID = recipeID;
        this.amount = amount;
        this.unit = unit;
        this.name = name;
    }

    public enum CSVPositions {
        INGREDIENTID,
        RECIPEID,
        AMOUNT,
        UNIT,
        NAME
    }

    public UUID getUUID() {
        return ingredientID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"IngedientID","RecipeID","Amount","UnitID","Name"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Ingredient.CSVPositions.values().length];
        data[CSVPositions.INGREDIENTID.ordinal()] = String.valueOf(this.ingredientID);
        data[CSVPositions.RECIPEID.ordinal()] = String.valueOf(this.recipeID);
        data[CSVPositions.AMOUNT.ordinal()] = String.valueOf(this.amount);
        data[CSVPositions.UNIT.ordinal()] = String.valueOf(this.unit.getUUID());
        data[CSVPositions.NAME.ordinal()] = String.valueOf(this.name);
        return data;
    }

    public UUID getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(UUID recipeID) {
        this.recipeID = recipeID;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

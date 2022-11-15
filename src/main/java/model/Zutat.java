package model;

import util.IPersistable;

import java.util.UUID;

/* Zutat Klasse: Definiert eine Zutat eines Rezeptes */
public class Zutat implements IPersistable {
    private final UUID zutatID;
    private UUID rezeptID;
    private long menge;
    private Einheit einheit;
    private String name;

    public Zutat(UUID rezeptID, long menge, Einheit einheit, String name) {
        this.zutatID = UUID.randomUUID();
        this.rezeptID = rezeptID;
        this.menge = menge;
        this.einheit = einheit;
        this.name = name;
    }

    public Zutat(UUID zutatID, UUID rezeptID, long menge, Einheit einheit, String name) {
        this.zutatID = zutatID;
        this.rezeptID = rezeptID;
        this.menge = menge;
        this.einheit = einheit;
        this.name = name;
    }

    public enum CSVPositions {
        ZUATATID,
        REZEPTID,
        MENGE,
        EINHEIT,
        NAME
    }

    public UUID getUUID() {
        return zutatID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"ZutatID","RezeptID","Menge","EinheitID","Name"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Zutat.CSVPositions.values().length];
        data[CSVPositions.ZUATATID.ordinal()] = String.valueOf(this.zutatID);
        data[CSVPositions.REZEPTID.ordinal()] = String.valueOf(this.rezeptID);
        data[CSVPositions.MENGE.ordinal()] = String.valueOf(this.menge);
        data[CSVPositions.EINHEIT.ordinal()] = String.valueOf(this.einheit.getUUID());
        data[CSVPositions.NAME.ordinal()] = String.valueOf(this.name);
        return data;
    }

    public UUID getRezeptID() {
        return rezeptID;
    }

    public void setRecipeID(UUID recipeID) {
        this.rezeptID = recipeID;
    }

    public long getMenge() {
        return menge;
    }

    public void setAmount(long amount) {
        this.menge = amount;
    }

    public Einheit getEinheit() {
        return einheit;
    }

    public void setEinheit(Einheit einheit) {
        this.einheit = einheit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

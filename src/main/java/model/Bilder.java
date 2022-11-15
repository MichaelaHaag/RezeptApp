package model;

import util.IPersistable;

import java.util.UUID;

/* Bild Klasse: Definiert ein Bild eines Rezeptes */
public class Bilder implements IPersistable {
    private final UUID bildID;
    private final UUID rezeptID;
    private String pfad;

    public Bilder(UUID bildID, UUID recipeID, String pfad) {
        this.bildID = bildID;
        this.rezeptID = recipeID;
        this.pfad = pfad;
    }

    public Bilder(UUID recipeID, String pfad) {
        this.bildID = UUID.randomUUID();
        this.rezeptID = recipeID;
        this.pfad = pfad;
    }

    public enum CSVPositions {
        BILDID,
        REZEPTID,
        PFAD
    }

    public UUID getUUID() {
        return bildID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"BildID", "RezeptID", "Pfad"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Bilder.CSVPositions.values().length];
        data[CSVPositions.BILDID.ordinal()] = String.valueOf(this.bildID);
        data[CSVPositions.REZEPTID.ordinal()] = String.valueOf(this.rezeptID);
        data[CSVPositions.PFAD.ordinal()] = String.valueOf(this.pfad);
        return data;
    }

    public String getPfad() {
        return pfad;
    }

    public void setPfad(String pfad) {
        this.pfad = pfad;
    }

    public UUID getRezeptID() {
        return rezeptID;
    }

}

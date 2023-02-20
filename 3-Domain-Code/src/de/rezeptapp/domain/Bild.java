package de.rezeptapp.domain;

import util.IPersistierbar;

import java.util.UUID;

/* Bild Klasse: Definiert ein Bild eines Rezeptes */
public class Bild implements IPersistierbar {
    private final UUID bildID;
    private final UUID rezeptID;
    private final String pfad;

    public Bild(UUID bildID, UUID rezeptID, String pfad) {
        this.bildID = bildID;
        this.rezeptID = rezeptID;
        this.pfad = pfad;
    }

    public Bild(UUID rezeptID, String pfad) {
        this.bildID = UUID.randomUUID();
        this.rezeptID = rezeptID;
        this.pfad = pfad;
    }

    public enum CSVPosition {
        BILDID,
        REZEPTID,
        PFAD
    }

    public UUID bekommeUUID() {
        return bildID;
    }

    @Override
    public String[] bekommeCSVKopf() {
        return new String[]{"BildID", "RezeptID", "Pfad"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] data = new String[CSVPosition.values().length];
        data[CSVPosition.BILDID.ordinal()] = String.valueOf(this.bildID);
        data[CSVPosition.REZEPTID.ordinal()] = String.valueOf(this.rezeptID);
        data[CSVPosition.PFAD.ordinal()] = String.valueOf(this.pfad);
        return data;
    }

    public String bekommePfad() {
        return pfad;
    }

    public UUID bekommeRezeptID() {
        return rezeptID;
    }

}

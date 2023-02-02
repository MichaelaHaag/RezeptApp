package model;

import util.IPersistierbar;

import java.util.UUID;

/* Zutat Klasse: Definiert eine Zutat eines Rezeptes */
public class Zutat implements IPersistierbar {
    private final UUID zutatID;
    private UUID rezeptID;
    private Menge menge;
    private String name;

    public Zutat(UUID rezeptID, Menge menge, String name) {
        this.zutatID = UUID.randomUUID();
        this.rezeptID = rezeptID;
        this.menge = menge;
        this.name = name;
    }

    public Zutat(UUID zutatID, UUID rezeptID, Menge menge, String name) {
        this.zutatID = zutatID;
        this.rezeptID = rezeptID;
        this.name = name;
    }

    public enum CSVPosition {
        ZUATATID,
        REZEPTID,
        MENGE,
        EINHEITID,
        NAME
    }

    public UUID bekommeUUID() {
        return zutatID;
    }

    @Override
    public String[] bekommeCSVKopf() {
        return new String[]{"ZutatID","RezeptID","Menge","EinheitID","Name"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.ZUATATID.ordinal()] = String.valueOf(this.zutatID);
        daten[CSVPosition.REZEPTID.ordinal()] = String.valueOf(this.rezeptID);
        daten[CSVPosition.MENGE.ordinal()] = String.valueOf(this.menge);
        daten[CSVPosition.NAME.ordinal()] = String.valueOf(this.name);
        return daten;
    }

    public UUID bekommeRezeptID() {
        return rezeptID;
    }

    public Menge bekommeMenge() {
        return menge;
    }

    public String bekommeName() {
        return name;
    }
}

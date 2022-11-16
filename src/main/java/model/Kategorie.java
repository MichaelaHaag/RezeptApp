package model;

import util.IPersistierbar;

import java.util.UUID;

/* Kategorie Klasse: Definiert eine Kategorie eines Rezeptes */
public class Kategorie implements IPersistierbar {
    private final UUID kategorieId;
    private String name;
    private String kurzformName;
    private String beschreibung;

    public Kategorie(String name, String kurzformName, String beschreibung) {
        this.kategorieId = UUID.randomUUID();
        this.name = name;
        this.kurzformName = kurzformName;
        this.beschreibung = beschreibung;
    }

    public Kategorie(UUID kategorieId, String name, String kurzformName, String beschreibung) {
        this.kategorieId = kategorieId;
        this.name = name;
        this.kurzformName = kurzformName;
        this.beschreibung = beschreibung;
    }

    public enum CSVPosition {
        KATEGORIEID,
        NAME,
        TAG,
        BESCHREIBUNG
    }

    public String toString() {
        return this.name;
    }

    @Override
    public Object bekommeUUID() {
        return kategorieId;
    }

    @Override
    public String[] bekommeCSVKopf() {
        return new String[]{"KategorieID","Name","Tag","Beschreibung"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.KATEGORIEID.ordinal()] = String.valueOf(this.kategorieId);
        daten[CSVPosition.NAME.ordinal()] = String.valueOf(this.name);
        daten[CSVPosition.TAG.ordinal()] = String.valueOf(this.kurzformName);
        daten[CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(this.beschreibung);
        return daten;
    }

    public String bekommeName() {
        return name;
    }

    public String bekommeKurzformName() {
        return kurzformName;
    }

}

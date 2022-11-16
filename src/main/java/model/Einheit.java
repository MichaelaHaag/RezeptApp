package model;

import util.IPersistierbar;

import java.util.UUID;

/* Einheit Klasse: Definiert eine Einheit einer Zutat */
public class Einheit implements IPersistierbar {
    private final UUID einheitID;
    private String name;
    private String beschreibung;

    public Einheit(String name, String beschreibung) {
        this.einheitID = UUID.randomUUID();
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public Einheit(UUID einheitID, String name, String beschreibung) {
        this.einheitID = einheitID;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public enum CSVPosition {
        EINEHEITID,
        NAME,
        BESCHREIBUNG
    }

    public UUID bekommeUUID() {
        return einheitID;
    }

    @Override
    public String[] bekommeCSVKopf() {
        return new String[]{"EinheitID","Name","Beschreibung"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.EINEHEITID.ordinal()] = String.valueOf(this.einheitID);
        daten[CSVPosition.NAME.ordinal()] = String.valueOf(this.name);
        daten[CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(this.beschreibung);
        return daten;
    }

    public String bekommeName() {
        return name;
    }
}

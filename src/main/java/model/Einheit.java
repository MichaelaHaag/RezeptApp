package model;

import util.IPersistable;

import java.util.UUID;

/* Einheit Klasse: Definiert eine Einheit einer Zutat */
public class Einheit implements IPersistable {
    private final UUID einheitID;
    private String name;
    private String beschreibung;

    public Einheit(String name, String beschreibung) {
        this.einheitID = UUID.randomUUID();
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public Einheit(UUID unitID, String name, String beschreibung) {
        this.einheitID = unitID;
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public enum CSVPositionen {
        EINEHEITID,
        NAME,
        BESCHREIBUNG
    }

    public UUID getUUID() {
        return einheitID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"UnitID","Name","Description"};
    }

    @Override
    public String[] getCSVData() {
        String[] daten = new String[Einheit.CSVPositionen.values().length];
        daten[CSVPositionen.EINEHEITID.ordinal()] = String.valueOf(this.einheitID);
        daten[CSVPositionen.NAME.ordinal()] = String.valueOf(this.name);
        daten[Einheit.CSVPositionen.BESCHREIBUNG.ordinal()] = String.valueOf(this.beschreibung);
        return daten;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}

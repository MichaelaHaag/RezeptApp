package model;

import util.IPersistable;

import java.util.UUID;

/* Kategorie Klasse: Definiert eine Kategorie eines Rezeptes */
public class Kategorie implements IPersistable {
    private final UUID kategorieId;
    private String name;
    private String tag;
    private String beschreibung;

    public Kategorie(String name, String tag, String beschreibung) {
        this.kategorieId = UUID.randomUUID();
        this.name = name;
        this.tag = tag;
        this.beschreibung = beschreibung;
    }

    public Kategorie(UUID kategorieId, String name, String tag, String beschreibung) {
        this.kategorieId = kategorieId;
        this.name = name;
        this.tag = tag;
        this.beschreibung = beschreibung;
    }

    public enum CSVPositions {
        KATEGORIEID,
        NAME,
        TAG,
        BESCHREIBUNG
    }
    public String toString() {
        return this.name;
    }

    @Override
    public Object getUUID() {
        return kategorieId;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"KategorieID","Name","Tag","Beschreibung"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Kategorie.CSVPositions.values().length];
        data[CSVPositions.KATEGORIEID.ordinal()] = String.valueOf(this.kategorieId);
        data[CSVPositions.NAME.ordinal()] = String.valueOf(this.name);
        data[CSVPositions.TAG.ordinal()] = String.valueOf(this.tag);
        data[Kategorie.CSVPositions.BESCHREIBUNG.ordinal()] = String.valueOf(this.beschreibung);
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
        return beschreibung;
    }

    public void setDescription(String beschreibung) {
        this.beschreibung = beschreibung;
    }
}

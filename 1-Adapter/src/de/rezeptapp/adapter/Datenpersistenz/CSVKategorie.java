package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.Kategorie.Kategorie;

public class CSVKategorie implements ICSVPersistierbar {
    Kategorie kategorie;

    public CSVKategorie(Kategorie kategorie) {
        this.kategorie = kategorie;
    }

    public enum CSVPosition {
        KATEGORIEID,
        NAME,
        TAG,
        BESCHREIBUNG
    }

    @Override
    public Object bekommeUUID() {
        return this.kategorie.bekommeUUID();
    }

    public String[] bekommeCSVKopf() {
        return new String[]{"KategorieID","Name","Tag","Beschreibung"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVKategorie.CSVPosition.values().length];
        daten[CSVKategorie.CSVPosition.KATEGORIEID.ordinal()] = String.valueOf(this.kategorie.bekommeUUID());
        daten[CSVKategorie.CSVPosition.NAME.ordinal()] = String.valueOf(this.kategorie.bekommeName());
        daten[CSVKategorie.CSVPosition.TAG.ordinal()] = String.valueOf(this.kategorie.bekommeKurzformName());
        daten[CSVKategorie.CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(this.kategorie.bekommeKategorieBeschreibung());
        return daten;
    }
}

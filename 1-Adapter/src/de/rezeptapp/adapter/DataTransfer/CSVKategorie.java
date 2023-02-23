package de.rezeptapp.adapter.DataTransfer;

import de.rezeptapp.domain.Kategorie.Kategorie;
public class CSVKategorie {

    public enum CSVPosition {
        KATEGORIEID,
        NAME,
        TAG,
        BESCHREIBUNG
    }
    public String[] bekommeCSVKopf() {
        return new String[]{"KategorieID","Name","Tag","Beschreibung"};
    }

    public String[] bekommeCSVDaten(Kategorie kategorie) {
        String[] daten = new String[CSVKategorie.CSVPosition.values().length];
        daten[CSVKategorie.CSVPosition.KATEGORIEID.ordinal()] = String.valueOf(kategorie.bekommeUUID());
        daten[CSVKategorie.CSVPosition.NAME.ordinal()] = String.valueOf(kategorie.bekommeName());
        daten[CSVKategorie.CSVPosition.TAG.ordinal()] = String.valueOf(kategorie.bekommeKurzformName());
        daten[CSVKategorie.CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(kategorie.bekommeKategorieBeschreibung());
        return daten;
    }
}

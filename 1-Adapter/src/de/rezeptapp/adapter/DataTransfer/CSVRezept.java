package de.rezeptapp.adapter.DataTransfer;

import de.rezeptapp.domain.Rezept.Rezept;
import de.rezeptapp.domain.Kategorie.Kategorie;

import java.util.ArrayList;
import java.util.List;

public class CSVRezept {

    public enum CSVPosition {
        REZEPTID,
        TITEL,
        SCHWIERIGKEITSGRAD,
        BESCHREIBUNG
    }

    public static String[] bekommeCSVKopf() {
        return new String[]{"RezeptID","Titel","Schwierigkeitsgrad","Beschreibung"};
    }

    public String[] bekommeCSVDaten(Rezept rezept) {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.REZEPTID.ordinal()] = String.valueOf(rezept.bekommeUUID());
        daten[CSVPosition.TITEL.ordinal()] = String.valueOf(rezept.bekommeTitel());
        daten[CSVPosition.SCHWIERIGKEITSGRAD.ordinal()] = String.valueOf(rezept.bekommeSchwierigkeitsgrad());
        daten[CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(rezept.bekommeBeschreibung());
        return daten;
    }

    public List<String[]> bekommeKategorienCSV(Rezept rezept) {
        List<String[]> csvDaten = new ArrayList<>();
        for (Kategorie kategorie : rezept.bekommeKategorien()) {
            String[] daten = new String[2];
            daten[0] = String.valueOf(rezept.bekommeUUID());
            daten[1] = String.valueOf(kategorie.bekommeUUID());
            csvDaten.add(daten);
        }
        return csvDaten;
    }
}

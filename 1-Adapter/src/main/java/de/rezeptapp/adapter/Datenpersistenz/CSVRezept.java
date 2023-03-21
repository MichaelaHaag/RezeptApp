package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.Rezept.Rezept;
import de.rezeptapp.domain.Kategorie.Kategorie;

import java.util.ArrayList;
import java.util.List;

public class CSVRezept implements ICSVPersistierbar {
    Rezept rezept;

    public CSVRezept(Rezept rezept) {
        this.rezept = rezept;
    }

    public enum CSVPosition {
        REZEPTID,
        TITEL,
        SCHWIERIGKEITSGRAD,
        BESCHREIBUNG
    }

    @Override
    public Object bekommeUUID() {
        return this.rezept.bekommeUUID();
    }

    public String[] bekommeCSVKopf() {
        return new String[]{"RezeptID","Titel","Schwierigkeitsgrad","Beschreibung"};
    }

    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.REZEPTID.ordinal()] = String.valueOf(this.rezept.bekommeUUID());
        daten[CSVPosition.TITEL.ordinal()] = String.valueOf(this.rezept.bekommeTitel());
        daten[CSVPosition.SCHWIERIGKEITSGRAD.ordinal()] = String.valueOf(this.rezept.bekommeSchwierigkeitsgrad());
        daten[CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(this.rezept.bekommeBeschreibung());
        return daten;
    }

    public List<String[]> bekommeKategorienArray() {
        List<String[]> kategorienListe = new ArrayList<>();
        for (Kategorie kategorie : this.rezept.bekommeKategorien()) {
            String[] daten = new String[2];
            daten[0] = String.valueOf(this.rezept.bekommeUUID());
            daten[1] = String.valueOf(kategorie.bekommeUUID());
            kategorienListe.add(daten);
        }
        return kategorienListe;
    }
}

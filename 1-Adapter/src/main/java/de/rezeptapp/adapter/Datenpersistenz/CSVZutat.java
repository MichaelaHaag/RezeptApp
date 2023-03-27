package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.Rezept.Zutat;

public class CSVZutat implements ICSVPersistierbar {
    Zutat zutat;

    public CSVZutat(Zutat zutat) {
        this.zutat = zutat;
    }

    public enum CSVPosition {
        ZUATATID,
        REZEPTID,
        MENGE,
        NAME
    }

    @Override
    public Object bekommeUUID() {
        return this.zutat.bekommeUUID();
    }

    public String[] bekommeCSVKopf() {
        return new String[]{"ZutatID","RezeptID","Menge","Name"};
    }

    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.ZUATATID.ordinal()] = String.valueOf(this.zutat.bekommeUUID());
        daten[CSVPosition.REZEPTID.ordinal()] = String.valueOf(this.zutat.bekommeRezeptID());
        daten[CSVPosition.MENGE.ordinal()] = String.valueOf(this.zutat.bekommeMenge());
        daten[CSVPosition.NAME.ordinal()] = String.valueOf(this.zutat.bekommeName());
        return daten;
    }
}

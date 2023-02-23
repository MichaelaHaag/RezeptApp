package de.rezeptapp.adapter.DataTransfer;

import de.rezeptapp.domain.Zutat;
import java.util.UUID;

public class CSVZutat {
    public enum CSVPosition {
        ZUATATID,
        REZEPTID,
        MENGE,
        NAME
    }

    public static String[] bekommeCSVKopf() {
        return new String[]{"ZutatID","RezeptID","Menge","Name"};
    }

    public String[] bekommeCSVDaten(Zutat zutat) {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.ZUATATID.ordinal()] = String.valueOf(zutat.bekommeUUID());
        daten[CSVPosition.REZEPTID.ordinal()] = String.valueOf(zutat.bekommenRezeptID());
        daten[CSVPosition.MENGE.ordinal()] = String.valueOf(zutat.bekommeMenge());
        daten[CSVPosition.NAME.ordinal()] = String.valueOf(zutat.bekommeName());
        return daten;
    }
}

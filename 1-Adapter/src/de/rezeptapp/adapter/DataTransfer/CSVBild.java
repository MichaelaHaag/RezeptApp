package de.rezeptapp.adapter.DataTransfer;

import de.rezeptapp.domain.Rezept.Bild;

public class CSVBild {

    public enum CSVPosition {
        BILDID,
        REZEPTID,
        PFAD
    }

    public String[] bekommeCSVKopf() {
        return new String[]{"BildID", "RezeptID", "Pfad"};
    }

    public String[] bekommeCSVDaten(Bild bild) {
        String[] data = new String[CSVPosition.values().length];
        data[CSVPosition.BILDID.ordinal()] = String.valueOf(bild.bekommeUUID());
        data[CSVPosition.REZEPTID.ordinal()] = String.valueOf(bild.bekommeRezeptID());
        data[CSVPosition.PFAD.ordinal()] = String.valueOf(bild.bekommePfad());
        return data;
    }
}

package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.Rezept.Bild;

public class CSVBild implements ICSVPersistierbar {

    Bild bild;

    public CSVBild(Bild bild) {
        this.bild = bild;
    }

    public enum CSVPosition {
        BILDID,
        REZEPTID,
        PFAD
    }

    @Override
    public Object bekommeUUID() {
        return bild.bekommeUUID();
    }

    public String[] bekommeCSVKopf() {
        return new String[]{"BildID", "RezeptID", "Pfad"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] data = new String[CSVPosition.values().length];
        data[CSVPosition.BILDID.ordinal()] = String.valueOf(this.bild.bekommeUUID());
        data[CSVPosition.REZEPTID.ordinal()] = String.valueOf(this.bild.bekommeRezeptID());
        data[CSVPosition.PFAD.ordinal()] = String.valueOf(this.bild.bekommePfad());
        return data;
    }
}

package model;

import util.IPersistierbar;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Rezept Klasse: Definiert ein Rezept */
public class Rezept implements IPersistierbar {
    private final UUID rezeptID;
    private String titel;
    private ArrayList<Kategorie> kategorien;
    private ArrayList<Zutat> zutaten;
    private String beschreibung;
    private Schwierigkeit schwierigkeitsgrad;
    private Bild bild;

    public Rezept(String titel, ArrayList<Kategorie> kategorien, ArrayList<Zutat> zutaten, String beschreibung, Schwierigkeit schwierigkeitsgrad, Bild bild) {
        this.rezeptID = UUID.randomUUID();
        this.titel = titel;
        this.kategorien = kategorien;
        this.zutaten = zutaten;
        this.beschreibung = beschreibung;
        this.schwierigkeitsgrad = schwierigkeitsgrad;
        this.bild = bild;
    }

    public Rezept(UUID rezeptID, String titel, ArrayList<Kategorie> kategorien, ArrayList<Zutat> zutaten, String beschreibung, Schwierigkeit schwierigkeitsgrad) {
        this.rezeptID = rezeptID;
        this.titel = titel;
        this.kategorien = kategorien;
        this.zutaten = zutaten;
        this.beschreibung = beschreibung;
        this.schwierigkeitsgrad = schwierigkeitsgrad;
    }

    public Rezept(UUID rezeptID, String titel, ArrayList<Kategorie> kategorien, ArrayList<Zutat> zutaten, String beschreibung, Schwierigkeit schwierigkeitsgrad, Bild bild) {
        this.rezeptID = rezeptID;
        this.titel = titel;
        this.kategorien = kategorien;
        this.zutaten = zutaten;
        this.beschreibung = beschreibung;
        this.schwierigkeitsgrad = schwierigkeitsgrad;
        this.bild = bild;
    }

    public Rezept() {
        this.rezeptID = UUID.randomUUID();
    }

    public enum CSVPosition {
        REZEPTID,
        TITEL,
        SCHWIERIGKEITSGRAD,
        BESCHREIBUNG
    }

    public UUID bekommeUUID() {
        return rezeptID;
    }

    @Override
    public String[] bekommeCSVKopf() {
        return new String[]{"RezeptID","Titel","Schwierigkeitsgrad","Beschreibung"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] daten = new String[CSVPosition.values().length];
        daten[CSVPosition.REZEPTID.ordinal()] = String.valueOf(this.rezeptID);
        daten[CSVPosition.TITEL.ordinal()] = String.valueOf(this.titel);
        daten[CSVPosition.SCHWIERIGKEITSGRAD.ordinal()] = String.valueOf(this.schwierigkeitsgrad);
        daten[CSVPosition.BESCHREIBUNG.ordinal()] = String.valueOf(this.beschreibung);
        return daten;
    }

    public List<String[]> bekommeKategorienCSV() {
        List<String[]> csvDaten = new ArrayList<>();
        for (Kategorie kategorie : this.bekommeKategorien()) {
            String[] daten = new String[2];
            daten[0] = String.valueOf(this.rezeptID);
            daten[1] = String.valueOf(kategorie.bekommeUUID());
            csvDaten.add(daten);

        }
       return csvDaten;
    }

    public String bekommeTitel() {
        return titel;
    }

    public ArrayList<Kategorie> bekommeKategorien() {
        return kategorien;
    }

    public ArrayList<Zutat> bekommeZutaten() {
        return zutaten;
    }

    public String bekommeBeschreibung() {
        return beschreibung;
    }

    public Schwierigkeit bekommeSchwierigkeitsgrad() {
        return schwierigkeitsgrad;
    }

    public Bild bekommeBild() {
        return bild;
    }

    public void setzeTitel(String titel) {
        this.titel = titel;
    }

    public void setzeKategorien(ArrayList<Kategorie> kategorien) {
        this.kategorien = kategorien;
    }

    public void setzeZutaten(ArrayList<Zutat> zutaten) {
        this.zutaten = zutaten;
    }

    public void setzeBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public void setzeSchwierigkeitsgrad(Schwierigkeit schwierigkeitsgrad) {
        this.schwierigkeitsgrad = schwierigkeitsgrad;
    }

    public void setzeBild(Bild bild) {
        this.bild = bild;
    }
}

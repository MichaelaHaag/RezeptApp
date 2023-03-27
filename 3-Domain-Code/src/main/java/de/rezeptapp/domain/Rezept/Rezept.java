package de.rezeptapp.domain.Rezept;

import de.rezeptapp.domain.IPersistierbar;
import de.rezeptapp.domain.Kategorie.Kategorie;

import java.util.ArrayList;
import java.util.UUID;

/* Rezept Klasse: Definiert ein Rezept */
public class Rezept implements IPersistierbar{
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

    public Rezept(String titel, ArrayList<Kategorie> kategorien, ArrayList<Zutat> zutaten, String beschreibung, Schwierigkeit schwierigkeitsgrad) {
        this.rezeptID = UUID.randomUUID();
        this.titel = titel;
        this.kategorien = kategorien;
        this.zutaten = zutaten;
        this.beschreibung = beschreibung;
        this.schwierigkeitsgrad = schwierigkeitsgrad;
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

    public UUID bekommeUUID() {
        return rezeptID;
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

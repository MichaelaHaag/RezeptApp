package de.rezeptapp.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

public class RezeptRepository {

    public boolean existiertRezept( Rezept rezept ) {
        return controller.entityManager.existiert(rezept);
    }

    public void speichereRezept(Rezept rezept ) throws Exception {
        controller.entityManager.speichere(rezept);
    }

    public Rezept findeRezept(UUID key) {
        return controller.entityManager.finde(Rezept.class, key);
    }

    public List<Rezept> findeAlleRezepte() {
        return controller.entityManager.findeAlle(Rezept.class);
    }

    public void entferneRezept(Rezept rezept) {
        controller.entityManager.entferne(rezept);
    }

    // Methode um die zugehörigen Kategorien zu einem Rezept zu finden
    public String[][] findeRezepteZuKategorie(Kategorie eingabeKategorie){
        List<Rezept> alleRezepte = rezeptRepository.findeAlleRezepte();
        List<String[]> ausgewähltesRezept = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            ArrayList<Kategorie> rezeptKategorien = rezept.bekommeKategorien();
            for (Kategorie rezeptKategorie : rezeptKategorien){
                if (rezeptKategorie.equals(eingabeKategorie)){
                    ausgewähltesRezept.add(rezept.bekommeCSVDaten());
                }
            }

        }
        String[][] out = new String[ausgewähltesRezept.size()][ausgewähltesRezept.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = ausgewähltesRezept.get(i);
        }
        return out;
    }

    //Methode um alle Rezepte zu finden
    public String[][] findeAlleRezepteUI(){
        List<Rezept> alleRezepte = findeAlleRezepte();
        List<String[]> rezepte = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            rezepte.add(rezept.bekommeCSVDaten());
        }

        String[][] out = new String[rezepte.size()][rezepte.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = rezepte.get(i);
        }
        return out;
    }

    /*Bild Funktionen*/
    public boolean existiertBild( Bild bild ) {
        return controller.entityManager.existiert(bild);
    }

    public void speichereBild(Bild bild ) throws Exception {
        controller.entityManager.speichere(bild);
    }

    public Bild findeBild(UUID key) {
        return controller.entityManager.finde(Bild.class, key);
    }

    public List<Bild> findeAlleBilder() {
        return controller.entityManager.findeAlle(Bild.class);
    }

    public void entferneBild(Bild bild) {
        controller.entityManager.entferne(bild);
    }


    /* Zutat Funktionen */
    public boolean existiertZutat( Zutat zutat ) {
        return controller.entityManager.existiert(zutat);
    }

    public void speichereZutat(Zutat zutat ) throws Exception {
        controller.entityManager.speichere(zutat);
    }

    public Zutat findeZutat(UUID key) {
        return controller.entityManager.finde(Zutat.class, key);
    }

    public List<Zutat> findeAlleZutaten() {
        return controller.entityManager.findeAlle(Zutat.class);
    }

    public void entferneZutat(Zutat zutat) {
        controller.entityManager.entferne(zutat);
    }
}

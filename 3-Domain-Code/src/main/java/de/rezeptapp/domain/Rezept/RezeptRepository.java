package de.rezeptapp.domain.Rezept;

import de.rezeptapp.domain.IEntityManager;
import de.rezeptapp.domain.Kategorie.Kategorie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RezeptRepository {

    public boolean existiertRezept( Rezept rezept, IEntityManager entityManager ) {
        return entityManager.existiert(rezept);
    }

    public void speichereRezept(Rezept rezept, IEntityManager entityManager ) throws Exception {
        entityManager.speichere(rezept);
    }

    public Rezept findeRezept(UUID key, IEntityManager entityManager) {
        return entityManager.finde(Rezept.class, key);
    }

    public List<Rezept> findeAlleRezepte(IEntityManager entityManager) {
        return entityManager.findeAlle(Rezept.class);
    }

    public void entferneRezept(Rezept rezept, IEntityManager entityManager) {
        entityManager.entferne(rezept);
    }

    // Methode um die zugehörigen Kategorien zu einem Rezept zu finden
    public String[][] findeRezepteZuKategorie(Kategorie eingabeKategorie, IEntityManager entityManager){
        List<String[]> ausgewähltesRezept = new ArrayList<>();

        for (Rezept rezept : findeAlleRezepte(entityManager)) {
            ArrayList<Kategorie> rezeptKategorien = rezept.bekommeKategorien();
            if (rezeptKategorien.stream().anyMatch(k -> k.equals(eingabeKategorie))) {
                String[] rezeptDaten = new String[4];
                rezeptDaten[0] = String.valueOf(rezept.bekommeUUID());
                rezeptDaten[1] = String.valueOf(rezept.bekommeTitel());
                rezeptDaten[2] = String.valueOf(rezept.bekommeSchwierigkeitsgrad());
                rezeptDaten[3] = String.valueOf(rezept.bekommeBeschreibung());
                ausgewähltesRezept.add(rezeptDaten);
            }
        }
        return ausgewähltesRezept.toArray(new String[ausgewähltesRezept.size()][]);
    }

    //Methode um alle Rezepte zu finden
    public String[][] findeAlleRezepteUI(IEntityManager entityManager){
        List<Rezept> alleRezepte = findeAlleRezepte(entityManager);
        List<String[]> rezepte = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            String[] rezeptDaten = new String[4];
            rezeptDaten[0] = String.valueOf(rezept.bekommeUUID());
            rezeptDaten[1] = String.valueOf(rezept.bekommeTitel());
            rezeptDaten[2] = String.valueOf(rezept.bekommeSchwierigkeitsgrad());
            rezeptDaten[3] = String.valueOf(rezept.bekommeBeschreibung());
            rezepte.add(rezeptDaten);
        }

        String[][] out = new String[rezepte.size()][rezepte.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = rezepte.get(i);
        }
        return out;
    }

    /*Bild Funktionen*/
    public boolean existiertBild( Bild bild, IEntityManager entityManager ) {
        return entityManager.existiert(bild);
    }

    public void speichereBild(Bild bild, IEntityManager entityManager ) throws Exception {
        entityManager.speichere(bild);
    }

    public Bild findeBild(UUID key, IEntityManager entityManager) {
        return entityManager.finde(Bild.class, key);
    }

    public List<Bild> findeAlleBilder(IEntityManager entityManager) {
        return entityManager.findeAlle(Bild.class);
    }

    public void entferneBild(Bild bild, IEntityManager entityManager) {
        entityManager.entferne(bild);
    }


    /* Zutat Funktionen */
    public boolean existiertZutat( Zutat zutat, IEntityManager entityManager ) {
        return entityManager.existiert(zutat);
    }

    public void speichereZutat(Zutat zutat, IEntityManager entityManager ) throws Exception {
        entityManager.speichere(zutat);
    }

    public Zutat findeZutat(UUID key, IEntityManager entityManager) {
        return entityManager.finde(Zutat.class, key);
    }

    public List<Zutat> findeAlleZutaten(IEntityManager entityManager) {
        return entityManager.findeAlle(Zutat.class);
    }

    public void entferneZutat(Zutat zutat, IEntityManager entityManager) {
        entityManager.entferne(zutat);
    }
}

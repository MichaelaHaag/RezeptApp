package de.rezeptapp.domain;

import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

public class KategorieRepository {

    public boolean existiertKategorie(Kategorie kategorie) {
        return controller.entityManager.existiert(kategorie);
    }

    public void speichereKategorie(Kategorie kategorie) throws Exception {
        controller.entityManager.speichere(kategorie);
    }

    public Kategorie findeKategorie(UUID key) {
        return controller.entityManager.finde(Kategorie.class, key);
    }

    public List<Kategorie> findeAlleKategorien() {
        return controller.entityManager.findeAlle(Kategorie.class);
    }

    public void entferneKategorie(Kategorie kategorie) {
        controller.entityManager.entferne(kategorie);
    }
}

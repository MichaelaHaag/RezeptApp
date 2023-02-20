package model;

import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

public class ZutatRepository {

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

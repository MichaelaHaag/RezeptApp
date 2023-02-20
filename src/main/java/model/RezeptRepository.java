package model;

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
}

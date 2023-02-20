package model;

import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

public class BildRepository {

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
}

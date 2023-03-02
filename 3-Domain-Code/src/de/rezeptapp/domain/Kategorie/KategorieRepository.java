package de.rezeptapp.domain.Kategorie;

import de.rezeptapp.domain.IEntityManager;

import java.util.List;
import java.util.UUID;

public class KategorieRepository {

    public boolean existiertKategorie(Kategorie kategorie, IEntityManager entityManager) {
        return entityManager.existiert(kategorie);
    }

    public void speichereKategorie(Kategorie kategorie, IEntityManager entityManager) throws Exception {
        entityManager.speichere(kategorie);
    }

    public Kategorie findeKategorie(UUID key, IEntityManager entityManager) {
        return entityManager.finde(Kategorie.class, key);
    }

    public List<Kategorie> findeAlleKategorien(IEntityManager entityManager) {
        return entityManager.findeAlle(Kategorie.class);
    }

    public void entferneKategorie(Kategorie kategorie, IEntityManager entityManager) {
        entityManager.entferne(kategorie);
    }
}

package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.IPersistierbar;
import de.rezeptapp.domain.Rezept.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EntityManagerTest {

    @Test
    public void test_existiert() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        EntityManager entityManager = new EntityManager();
        try {
            entityManager.speichere(zutat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(entityManager.existiert(zutat));
    }

    @Test
    public void test_speichere() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        EntityManager entityManager = new EntityManager();
        Map<Object, IPersistierbar> gefüllterEntityManager = null;
        try {
            // zugriff auf private Variable allElements
            Field elementeInEntityManager = entityManager.getClass().getDeclaredField("allElements");
            elementeInEntityManager.setAccessible(true);
            gefüllterEntityManager = (Map<Object, IPersistierbar>) elementeInEntityManager.get(entityManager);

            entityManager.speichere(zutat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assert gefüllterEntityManager != null;
        boolean existiertZutatinEntityManager = gefüllterEntityManager.containsValue(zutat);
        assertTrue(existiertZutatinEntityManager);
    }

    @Test
    public void test_finde() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        EntityManager entityManager = new EntityManager();
        try {
            entityManager.speichere(zutat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Zutat gefundeneZutat = entityManager.finde(Zutat.class, zutat.bekommeUUID());
        assertEquals(zutat, gefundeneZutat);
    }

    @Test
    public void test_findeAlle() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat1 = new Zutat(UUID.randomUUID(), menge, "Zucker");
        Zutat zutat2 = new Zutat(UUID.randomUUID(), menge, "Mehl");
        List<Zutat> zutatenListe = new ArrayList<Zutat>();
        zutatenListe.add(zutat1);
        zutatenListe.add(zutat2);

        List<Zutat> zutatenListe2 = new ArrayList<Zutat>();
        zutatenListe2.add(zutat1);

        EntityManager entityManager = new EntityManager();
        try {
            entityManager.speichere(zutat1);
            entityManager.speichere(zutat2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Zutat> gefundeneZutaten = entityManager.findeAlle(Zutat.class);
        Assertions.assertThat(gefundeneZutaten).containsExactlyInAnyOrder(zutat1,zutat2);
    }

    @Test
    public void test_entferne() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat1 = new Zutat(UUID.randomUUID(), menge, "Zucker");
        Zutat zutat2 = new Zutat(UUID.randomUUID(), menge, "Mehl");

        EntityManager entityManager = new EntityManager();
        Map<Object, IPersistierbar> gefüllterEntityManager = null;
        try {
            // zugriff auf private Variable allElements
            Field elementeInEntityManager = entityManager.getClass().getDeclaredField("allElements");
            elementeInEntityManager.setAccessible(true);
            gefüllterEntityManager = (Map<Object, IPersistierbar>) elementeInEntityManager.get(entityManager);

            entityManager.speichere(zutat1);
            entityManager.speichere(zutat2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        entityManager.entferne(zutat1);

        assert gefüllterEntityManager != null;
        assertFalse(gefüllterEntityManager.containsKey(zutat1.bekommeUUID()));
        assertTrue(gefüllterEntityManager.containsKey(zutat2.bekommeUUID()));
    }
}
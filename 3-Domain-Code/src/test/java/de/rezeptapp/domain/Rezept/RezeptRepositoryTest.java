package de.rezeptapp.domain.Rezept;

import de.rezeptapp.domain.IEntityManager;
import de.rezeptapp.domain.Kategorie.Kategorie;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.easymock.EasyMock;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RezeptRepositoryTest {

    @Test
    public void test_findeZutat() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat orginalZutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        IEntityManager mockedEntityManager = EasyMock.mock(IEntityManager.class);
        EasyMock.expect(mockedEntityManager.finde(Zutat.class, orginalZutat.bekommeUUID())).andReturn(orginalZutat);
        EasyMock.replay(mockedEntityManager);

        RezeptRepository repository = new RezeptRepository();
        Zutat erhalteneZutat = repository.findeZutat(orginalZutat.bekommeUUID(), mockedEntityManager );
        assertEquals(orginalZutat, erhalteneZutat);

        EasyMock.verify(mockedEntityManager);
    }

    @Test
    public void test_existiertZutat() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat orginalZutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        IEntityManager mockedEntityManager = EasyMock.mock(IEntityManager.class);
        EasyMock.expect(mockedEntityManager.existiert(orginalZutat)).andReturn(true);
        EasyMock.replay(mockedEntityManager);

        RezeptRepository repository = new RezeptRepository();
        Boolean existiertZutat = repository.existiertZutat(orginalZutat, mockedEntityManager );
        assertEquals(true, existiertZutat);

        EasyMock.verify(mockedEntityManager);
    }

    @Test
    public void test_speichereZutat() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        IEntityManager mockedEntityManager = EasyMock.mock(IEntityManager.class);
        try {
            mockedEntityManager.speichere(zutat);
            EasyMock.replay(mockedEntityManager);

            RezeptRepository rezeptRepository = new RezeptRepository();
            rezeptRepository.speichereZutat(zutat, mockedEntityManager);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EasyMock.verify(mockedEntityManager);
        }
    }

    @Test
    public void test_entferneZutat() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        IEntityManager mockedEntityManager = EasyMock.mock(IEntityManager.class);
        try {
            mockedEntityManager.entferne(zutat);
            EasyMock.replay(mockedEntityManager);

            RezeptRepository rezeptRepository = new RezeptRepository();
            rezeptRepository.entferneZutat(zutat, mockedEntityManager);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            EasyMock.verify(mockedEntityManager);
        }
    }

    @Test
    public void test_findeRezepteZuKategorie() {

        Kategorie kategorie1 = new Kategorie("Nudelgerichte","Nudeln","Rezepte für Nudelgrichte");
        ArrayList<Kategorie> kategorieListe2 = new ArrayList<>();
        ArrayList<Kategorie> kategorieListe1 = new ArrayList<>();
        kategorieListe1.add(kategorie1);
        ArrayList<Zutat> zutaten = new ArrayList<>();
        String beschreibung = "";
        Schwierigkeit schwierigkeitsgrad = Schwierigkeit.Einfach;

        Rezept rezept1 = new Rezept("Rezept1", kategorieListe1, zutaten, beschreibung, schwierigkeitsgrad );
        Rezept rezept2 = new Rezept("Rezept2", kategorieListe2, zutaten, beschreibung, schwierigkeitsgrad );
        List<Rezept> rezeptListe = new ArrayList<>();
        rezeptListe.add(rezept1);
        rezeptListe.add(rezept2);

        IEntityManager mockedEntityManager = EasyMock.mock(IEntityManager.class);
        EasyMock.expect(mockedEntityManager.findeAlle(Rezept.class)).andReturn(rezeptListe);
        EasyMock.replay(mockedEntityManager);

        RezeptRepository repository = new RezeptRepository();
        String[][] ausgewähltesRezept = repository.findeRezepteZuKategorie(kategorie1, mockedEntityManager );
        String[][] actual = {{rezept1.bekommeUUID().toString(), rezept1.bekommeTitel(), rezept1.bekommeSchwierigkeitsgrad().toString(), rezept1.bekommeBeschreibung()}};
        assertArrayEquals(actual, ausgewähltesRezept);

        EasyMock.verify(mockedEntityManager);
    }
}

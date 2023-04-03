package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.Rezept.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class CSVZutatTest {

    @Test
    public void test_CSVZutat_constructor() {
        Menge menge = new Menge( 300 , Einheit.Gramm);
        Zutat zutat = new Zutat(UUID.randomUUID(), menge, "Zucker");

        CSVZutat csvzutat = new CSVZutat(zutat);
        String[] erhaltenerWert = csvzutat.bekommeCSVDaten();
        String[] erwarteterWert = {zutat.bekommeUUID().toString(), zutat.bekommeRezeptID().toString(), "300-g", "Zucker"};
        assertArrayEquals(erwarteterWert, erhaltenerWert);
    }
}

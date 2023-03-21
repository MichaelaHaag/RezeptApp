package de.rezeptapp.domain.Rezept;


import de.rezeptapp.domain.Kategorie.Kategorie;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class RezeptTest {

    @Test
    public void test_rezept_constructor() {
        String titel = "Test Rezept";
        ArrayList<Kategorie> kategorien = new ArrayList<>();
        ArrayList<Zutat> zutaten = new ArrayList<>();
        String beschreibung = "";
        Schwierigkeit schwierigkeitsgrad = Schwierigkeit.Einfach;

        Rezept rezept = new Rezept(titel, kategorien, zutaten, beschreibung, schwierigkeitsgrad);
        String actual = rezept.bekommeTitel();
        assertThat(actual, is("Test Rezept"));
    }
}

package de.rezeptapp.adapter;

import de.rezeptapp.domain.*;
import view.ListenÜbersicht;
import view.Startseite;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.UUID;

public class FunktionenStartseite {

    final static KategorieRepository kategorieRepository = new KategorieRepository();

    /*Methode für die Funktionalität der Buttons. Wird ein Button geklickt, so öffnet sich die UI der KLasse
  Listpage (eine Liste mit allen Rezepten der ausgewählten Kategorie */

    public static void kategorieHinzufügen(String name, String tag, String beschreibung){
        Kategorie neueKategorie = new Kategorie(UUID.randomUUID(), name, tag, beschreibung);
        try {
            kategorieRepository.speichereKategorie( neueKategorie );
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Kategorie> alleKategorien = kategorieRepository.findeAlleKategorien();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Kategorie.csv", alleKategorien);
    }

}

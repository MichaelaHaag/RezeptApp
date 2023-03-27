package de.rezeptapp.adapter.GUIFunktionen;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.Datenpersistenz.CSVKategorie;
import de.rezeptapp.domain.Kategorie.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FunktionenStartseite {

    final static KategorieRepository kategorieRepository = new KategorieRepository();

    /*Methode für die Funktionalität der Buttons. Wird ein Button geklickt, so öffnet sich die UI der KLasse
  Listpage (eine Liste mit allen Rezepten der ausgewählten Kategorie */

    public static void kategorieHinzufügen(String name, String tag, String beschreibung, DataReader dataReader){
        Kategorie neueKategorie = new Kategorie(UUID.randomUUID(), name, tag, beschreibung);
        try {
            kategorieRepository.speichereKategorie( neueKategorie, dataReader.entityManager );
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Kategorie> alleKategorien = kategorieRepository.findeAlleKategorien(dataReader.entityManager);
        List<CSVKategorie> alleKategorienCSV = new ArrayList<>();
        for(Kategorie einzelneKategorie: alleKategorien){
            CSVKategorie kategorieCSV = new CSVKategorie(einzelneKategorie);
            alleKategorienCSV.add(kategorieCSV);
        }
        dataReader.speichereCSVDaten(dataReader.csvDateienPfad + "Kategorie.csv", alleKategorienCSV);
    }

}

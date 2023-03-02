package de.rezeptapp.adapter.GUIFunktionen;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.Datenpersistenz.*;
import de.rezeptapp.domain.Kategorie.*;
import de.rezeptapp.domain.Rezept.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FunktionenNeuesRezept {

    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();

    public static void neuesRezeptSpeichern(String titel, String beschreibung, ArrayList<String> checkedKategorien, String ausgewaelteSchwierigkeit, String pfadBild, ArrayList<String[]> zutatenListe, DataReader dataReader){
        UUID rezeptID = UUID.randomUUID();
        ArrayList<Kategorie> rezeptKategorien = new ArrayList<>();
        ArrayList<Zutat> rezeptZutaten = new ArrayList<>();
        Schwierigkeit schwierigkeit = null;

        List<Kategorie> alleKategorien = kategorieRepository.findeAlleKategorien(dataReader.entityManager);
        // hier kann man evtl. noch optimieren
        for (int i = 0; i < checkedKategorien.size(); i++) {
            for (Kategorie kategorie : alleKategorien){
                if (kategorie.bekommeKurzformName().equals(checkedKategorien.get(i))){
                    rezeptKategorien.add(kategorie);
                }
            }
        }
        System.out.println("Alle Kategorien: " + rezeptKategorien);

        for(Schwierigkeit enumSchwierigkeit : Schwierigkeit.values()){
            if(enumSchwierigkeit.toString().equals(ausgewaelteSchwierigkeit)){
                schwierigkeit = enumSchwierigkeit;
            }
        }

        for (String[] zutatEintrag : zutatenListe) {
            String mengeText = zutatEintrag[0];
            long mengeLong = Long.parseLong(mengeText);
            String einheitText = zutatEintrag[1];
            String name = zutatEintrag[2];

            Einheit ausgewählteEinheit = null;
            Einheit[] alleEinheit = Einheit.values();
            for (Einheit einheit : alleEinheit) {
                if (einheit.bekommeName().equals(einheitText)) {
                    ausgewählteEinheit = einheit;
                }
            }
            Menge menge = new Menge(mengeLong, ausgewählteEinheit);
            Zutat zutat = new Zutat(rezeptID, menge, name);
            rezeptZutaten.add(zutat);

            try {
                rezeptRepository.speichereZutat(zutat, dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (pfadBild != null) {
            String[] aufgeteilterPfad = pfadBild.split("(?=src)");
            String stringPfad = aufgeteilterPfad[1].replace("\\", "/");
            Bild bildElement = new Bild(rezeptID, stringPfad);

            //Bild und Rezept im EntityManager speichern
            try {
                rezeptRepository.speichereBild(bildElement, dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptZutaten, beschreibung, schwierigkeit, bildElement);
            try {
                rezeptRepository.speichereRezept(rezeptElement, dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Bild in der CVS Datei speichern
            List<Bild> alleBilder = rezeptRepository.findeAlleBilder(dataReader.entityManager);
            List<CSVBild> alleBilderCSV = new ArrayList<>();
            for(Bild einzelnesBild: alleBilder){
                CSVBild bildCSV = new CSVBild(einzelnesBild);
                alleBilderCSV.add(bildCSV);
            }
            dataReader.speichereCSVDaten(dataReader.csvDateienPfad + "Bild.csv", alleBilderCSV);

        } else {
            Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptZutaten, beschreibung, schwierigkeit);
            try {
                rezeptRepository.speichereRezept(rezeptElement, dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Zutaten und Rezept in CSV Speichern
        List<Zutat> alleZutaten = rezeptRepository.findeAlleZutaten(dataReader.entityManager);
        List<CSVZutat> alleZutatenCSV = new ArrayList<>();
        for(Zutat einzelneZutat: alleZutaten){
            CSVZutat zutatCSV = new CSVZutat(einzelneZutat);
            alleZutatenCSV.add(zutatCSV);
        }
        dataReader.speichereCSVDaten(dataReader.csvDateienPfad + "Zutaten.csv", alleZutatenCSV);
        List<Rezept> alleRezepte = rezeptRepository.findeAlleRezepte(dataReader.entityManager);
        List<CSVRezept> alleRezepteCSV = new ArrayList<>();
        for(Rezept einzelnesRezept: alleRezepte){
            CSVRezept rezeptCSV = new CSVRezept(einzelnesRezept);
            alleRezepteCSV.add(rezeptCSV);
        }
        dataReader.speichereCSVDaten(dataReader.csvDateienPfad + "Rezept.csv", alleRezepteCSV);
    }
}

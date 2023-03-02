package de.rezeptapp.adapter.GUIFunktionen;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.adapter.Datenpersistenz.CSVBild;
import de.rezeptapp.adapter.Datenpersistenz.CSVRezept;
import de.rezeptapp.adapter.Datenpersistenz.CSVZutat;
import de.rezeptapp.domain.Kategorie.Kategorie;
import de.rezeptapp.domain.Kategorie.KategorieRepository;
import de.rezeptapp.domain.Rezept.*;

import java.util.ArrayList;
import java.util.List;

public class FunktionenRezeptBearbeiten {

    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();


    public static void rezeptBearbeitungSpeichern(Rezept rezept, String titel, String beschreibung, ArrayList<String> checkedKategorien, String ausgewaelteSchwierigkeit, String pfadBild, ArrayList<String[]> zutatenListe, DataReader dataReader) {
        ArrayList<Kategorie> rezeptKategorien = new ArrayList<>();
        ArrayList<Zutat> rezeptZutaten = new ArrayList<>();
        Schwierigkeit schwierigkeit = null;

        List<Kategorie> alleKategorien = kategorieRepository.findeAlleKategorien(dataReader.entityManager);
        // hier kann man evtl. noch optimieren
        for (String kategorieString : checkedKategorien) {
            for (Kategorie kategorie : alleKategorien) {
                if (kategorie.bekommeKurzformName().equals(kategorieString)) {
                    rezeptKategorien.add(kategorie);
                }
            }
        }

        for(Schwierigkeit enumSchwierigkeit : Schwierigkeit.values()){
            if(enumSchwierigkeit.toString().equals(ausgewaelteSchwierigkeit)){
                schwierigkeit = enumSchwierigkeit;
            }
        }

        //lösche alte Zutaten im EntityManager
        ArrayList<Zutat> alteRezeptZutaten = rezept.bekommeZutaten();
        for(Zutat zutat: alteRezeptZutaten){
            rezeptRepository.entferneZutat(zutat, dataReader.entityManager);
        }

        //lege neue Zutaten an im EntityManger
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
            Zutat zutat = new Zutat(rezept.bekommeUUID(), menge, name);
            rezeptZutaten.add(zutat);

            try {
                rezeptRepository.speichereZutat(zutat, dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        rezept.setzeTitel(titel);
        rezept.setzeBeschreibung(beschreibung);
        rezept.setzeKategorien(rezeptKategorien);
        rezept.setzeSchwierigkeitsgrad(schwierigkeit);
        rezept.setzeZutaten(rezeptZutaten);

        if (pfadBild != null) {
            //falls altes Bild existiert dieses löschen
            if (rezept.bekommeBild() != null) {
                rezeptRepository.entferneBild(rezept.bekommeBild(), dataReader.entityManager);
            }

            String[] aufgeteilterPfad = pfadBild.split("(?=resources)");
            String stringPfad = aufgeteilterPfad[1].replace("\\", "/");
            Bild bildElement = new Bild(rezept.bekommeUUID(), stringPfad);

            //Bild im EntityManager speichern
            try {
                rezeptRepository.speichereBild(bildElement, dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rezept.setzeBild(bildElement);

            //Bild in der CVS Datei speichern
            List<Bild> alleBilder = rezeptRepository.findeAlleBilder(dataReader.entityManager);
            List<CSVBild> alleBilderCSV = new ArrayList<>();
            for(Bild einzelnesBild: alleBilder){
                CSVBild bildCSV = new CSVBild(einzelnesBild);
                alleBilderCSV.add(bildCSV);
            }
            dataReader.speichereCSVDaten(dataReader.csvDateienPfad + "Bild.csv", alleBilderCSV);
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
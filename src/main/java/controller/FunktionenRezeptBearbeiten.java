package controller;

import model.*;
import view.NeuesRezept;
import view.RezeptBearbeiten;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

public class FunktionenRezeptBearbeiten {

    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static ZutatRepository zutatRepository = new ZutatRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();
    final static BildRepository bildRepository = new BildRepository();

    public static void rezeptBearbeiten(Rezept rezept) {
        new RezeptBearbeiten(rezept);

    }

    public static void rezeptBearbeitungSpeichern(Rezept rezept, String titel, String beschreibung, ArrayList<String> checkedKategorien, String ausgewaelteSchwierigkeit, String pfadBild, ArrayList<String[]> zutatenListe) {
        ArrayList<Kategorie> rezeptKategorien = new ArrayList<>();
        ArrayList<Zutat> rezeptZutaten = new ArrayList<>();
        Schwierigkeit schwierigkeit = null;

        List<Kategorie> alleKategorien = kategorieRepository.findeAlleKategorien();
        // hier kann man evtl. noch optimieren
        for (int i = 0; i < checkedKategorien.size(); i++) {
            for (Kategorie kategorie : alleKategorien){
                if (kategorie.bekommeKurzformName().equals(checkedKategorien.get(i))){
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
            zutatRepository.entferneZutat(zutat);
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
                zutatRepository.speichereZutat(zutat);
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
                bildRepository.entferneBild(rezept.bekommeBild());
            }

            String[] aufgeteilterPfad = pfadBild.split("(?=src)");
            String stringPfad = aufgeteilterPfad[1].replace("\\", "/");
            Bild bildElement = new Bild(rezept.bekommeUUID(), stringPfad);

            //Bild im EntityManager speichern
            try {
                bildRepository.speichereBild(bildElement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rezept.setzeBild(bildElement);

            //Bild in der CVS Datei speichern
            List<Bild> alleBilder = bildRepository.findeAlleBilder();
            controller.speichereCSVDaten(controller.csvDateienPfad + "Bild.csv", alleBilder);
        }

        //Zutaten und Rezept in CSV Speichern
        List<Zutat> alleZutaten = zutatRepository.findeAlleZutaten();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Zutaten.csv", alleZutaten);
        List<Rezept> alleRezepte = rezeptRepository.findeAlleRezepte();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Rezept.csv", alleRezepte);
    }
}
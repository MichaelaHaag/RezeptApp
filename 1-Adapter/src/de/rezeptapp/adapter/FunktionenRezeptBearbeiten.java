package de.rezeptapp.adapter;

import de.rezeptapp.domain.Kategorie.Kategorie;
import de.rezeptapp.domain.Kategorie.KategorieRepository;
import de.rezeptapp.domain.Rezept.*;
import view.RezeptBearbeiten;

import java.util.ArrayList;
import java.util.List;

public class FunktionenRezeptBearbeiten {

    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();


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
            rezeptRepository.entferneZutat(zutat);
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
                rezeptRepository.speichereZutat(zutat);
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
                rezeptRepository.entferneBild(rezept.bekommeBild());
            }

            String[] aufgeteilterPfad = pfadBild.split("(?=src)");
            String stringPfad = aufgeteilterPfad[1].replace("\\", "/");
            Bild bildElement = new Bild(rezept.bekommeUUID(), stringPfad);

            //Bild im EntityManager speichern
            try {
                rezeptRepository.speichereBild(bildElement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            rezept.setzeBild(bildElement);

            //Bild in der CVS Datei speichern
            List<Bild> alleBilder = rezeptRepository.findeAlleBilder();
            controller.speichereCSVDaten(controller.csvDateienPfad + "Bild.csv", alleBilder);
        }

        //Zutaten und Rezept in CSV Speichern
        List<Zutat> alleZutaten = rezeptRepository.findeAlleZutaten();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Zutaten.csv", alleZutaten);
        List<Rezept> alleRezepte = rezeptRepository.findeAlleRezepte();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Rezept.csv", alleRezepte);
    }
}
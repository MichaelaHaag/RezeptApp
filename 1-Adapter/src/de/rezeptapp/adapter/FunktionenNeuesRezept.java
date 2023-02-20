package de.rezeptapp.adapter;

import de.rezeptapp.domain.*;
import view.NeuesRezept;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FunktionenNeuesRezept {

    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static ZutatRepository zutatRepository = new ZutatRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();
    final static BildRepository bildRepository = new BildRepository();

    public static void neuesRezeptErstellen(JFrame frame){
        new NeuesRezept();
        frame.dispose();
    }

    public static void neuesRezeptSpeichern(String titel, String beschreibung, ArrayList<String> checkedKategorien, String ausgewaelteSchwierigkeit, String pfadBild, ArrayList<String[]> zutatenListe){
        UUID rezeptID = UUID.randomUUID();
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
                zutatRepository.speichereZutat(zutat);
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
                bildRepository.speichereBild(bildElement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptZutaten, beschreibung, schwierigkeit, bildElement);
            try {
                rezeptRepository.speichereRezept(rezeptElement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Bild in der CVS Datei speichern
            List<Bild> alleBilder = bildRepository.findeAlleBilder();
            controller.speichereCSVDaten(controller.csvDateienPfad + "Bild.csv", alleBilder);

        } else {
            Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptZutaten, beschreibung, schwierigkeit);
            try {
                rezeptRepository.speichereRezept(rezeptElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Zutaten und Rezept in CSV Speichern
        List<Zutat> alleZutaten = zutatRepository.findeAlleZutaten();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Zutaten.csv", alleZutaten);
        List<Rezept> alleRezepte = rezeptRepository.findeAlleRezepte();
        controller.speichereCSVDaten(controller.csvDateienPfad + "Rezept.csv", alleRezepte);
    }
}

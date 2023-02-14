package controller;

import model.*;
import view.NeuesRezept;
import view.RezeptAnsicht;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static app.RezeptApp.controller;

public class FunktionenNeuesRezept {

    public static void neuesRezeptErstellen(JFrame frame){
        new NeuesRezept();
        frame.dispose();
    }

    public static void neuesRezeptSpeichern(String titel, String beschreibung, ArrayList<String> checkedKategorien, String ausgewaelteSchwierigkeit, String pfadBild, ArrayList<String[]> zutatenListe){
        UUID rezeptID = UUID.randomUUID();
        ArrayList<Kategorie> rezeptKategorien = new ArrayList<>();
        ArrayList<Zutat> rezeptZutaten = new ArrayList<>();
        Schwierigkeit schwierigkeit = null;

        List<Kategorie> alleKategorien = controller.entityManager.findeAlle(Kategorie.class);
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
                controller.entityManager.speichere(zutat);
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
                controller.entityManager.speichere(bildElement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptZutaten, beschreibung, schwierigkeit, bildElement);
            try {
                controller.entityManager.speichere(rezeptElement);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //Bild in der CVS Datei speichern
            List<Bild> alleBilder = controller.entityManager.findeAlle(Bild.class);
            controller.speichereCSVDaten(controller.csvDateienPfad + "Bild.csv", alleBilder);

        } else {
            Rezept rezeptElement = new Rezept(rezeptID, titel, rezeptKategorien, rezeptZutaten, beschreibung, schwierigkeit);
            try {
                controller.entityManager.speichere(rezeptElement);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //Zutaten und Rezept in CSV Speichern
        List<Zutat> alleZutaten = controller.entityManager.findeAlle(Zutat.class);
        controller.speichereCSVDaten(controller.csvDateienPfad + "Zutaten.csv", alleZutaten);
        List<Rezept> alleRezepte = controller.entityManager.findeAlle(Rezept.class);
        controller.speichereCSVDaten(controller.csvDateienPfad + "Rezept.csv", alleRezepte);
    }
}

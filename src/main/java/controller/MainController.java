package controller;

import model.*;
import util.CSVReader;
import util.CSVWriter;
import util.EntityManager;
import util.IPersistierbar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Main Controller: Beinhaltet alle Funktionalitäten des Backends */
public class MainController {

    public final String csvBilderPfad = "src\\main\\resources\\CSVFiles\\";
    public EntityManager entityManager = new EntityManager();
    private IPersistierbar element = null;

    public void init() {
        try {
            ladeCSVDaten();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Methode zum laden der Daten aus den CSV Dateien und Erstellung von Einträgen im Entitymanager
    private void ladeCSVDaten() throws IOException {
        CSVReader csvReader = new CSVReader(csvBilderPfad + "Kategorie.csv");
        List<String[]> csvDaten = csvReader.leseDaten();
        csvDaten.forEach(csvZeile -> {
            try {
                UUID kategorieID = UUID.fromString(csvZeile[ Kategorie.CSVPosition.KATEGORIEID.ordinal() ]);
                String name = csvZeile[ Kategorie.CSVPosition.NAME.ordinal() ];
                String tag = csvZeile[ Kategorie.CSVPosition.TAG.ordinal() ];
                String beschreibung = csvZeile[ Kategorie.CSVPosition.BESCHREIBUNG.ordinal() ];

                element = new Kategorie(kategorieID, name, tag, beschreibung);
                entityManager.speichere( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();


        csvReader = new CSVReader(csvBilderPfad + "Zutaten.csv");
        csvDaten = csvReader.leseDaten();
        csvDaten.forEach(csvZeile -> {
            try {
                UUID zutatenID = UUID.fromString(csvZeile[ Zutat.CSVPosition.ZUATATID.ordinal() ]);
                UUID zutatenRezeptID = UUID.fromString(csvZeile[ Zutat.CSVPosition.REZEPTID.ordinal() ]);
                String mengeString = csvZeile[ Zutat.CSVPosition.MENGE.ordinal() ];
                String zutatName = csvZeile[ Zutat.CSVPosition.NAME.ordinal() ];

                String[] mengeStringParts = mengeString.split("-");
                long mengeAnzahl = Long.parseLong( mengeStringParts[0]);
                String einheitString = mengeStringParts[1];

                Einheit einheit = null;
                for(Einheit enumEinheit : Einheit.values()){
                    if(enumEinheit.bekommeName().equals(einheitString)){
                        einheit = enumEinheit;
                    }
                }
                Menge menge = new Menge(mengeAnzahl, einheit);

                element = new Zutat(zutatenID, zutatenRezeptID, menge, zutatName);
                entityManager.speichere( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();

        csvReader = new CSVReader(csvBilderPfad + "Bild.csv");
        csvDaten = csvReader.leseDaten();
        csvDaten.forEach(csvZeile -> {
            try {
                UUID bildID = UUID.fromString(csvZeile[ Bild.CSVPosition.BILDID.ordinal() ]);
                UUID rezeptID = UUID.fromString(csvZeile[ Bild.CSVPosition.REZEPTID.ordinal() ]);
                String pfad = csvZeile[ Bild.CSVPosition.PFAD.ordinal() ];

                element = new Bild(bildID, rezeptID, pfad);
                entityManager.speichere( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();

        csvReader = new CSVReader(csvBilderPfad + "RezeptKategorie.csv");
        List<String[]> csvDatenRezeptKategorie = csvReader.leseDaten();

        csvReader = new CSVReader(csvBilderPfad + "Rezept.csv");
        csvDaten = csvReader.leseDaten();
        csvDaten.forEach(csvZeile -> {
            try {
                UUID rezeptID = UUID.fromString(csvZeile[ Rezept.CSVPosition.REZEPTID.ordinal() ]);
                String titel = csvZeile[ Rezept.CSVPosition.TITEL.ordinal() ];
                int schwierigkeitsgrad = Integer.parseInt(csvZeile[ Rezept.CSVPosition.SCHWIERIGKEITSGRAD.ordinal() ]);
                String beschreibung = csvZeile[ Rezept.CSVPosition.BESCHREIBUNG.ordinal() ];
                Bild rezeptBild = null;
                ArrayList<Zutat> zutaten = new ArrayList<>();
                ArrayList<Kategorie> kategorien = new ArrayList<>();
                Schwierigkeit schwierigkeit = null;

                List<Bild> bilderListe = entityManager.findeAlle(Bild.class);
                for (Bild bild : bilderListe){
                    if (bild.bekommeRezeptID().equals(rezeptID)){
                        rezeptBild = bild;
                    }
                }

                List<Zutat> zutatenListe = entityManager.findeAlle(Zutat.class);
                for (Zutat zutat : zutatenListe){
                    if (zutat.bekommeRezeptID().equals(rezeptID)){
                        zutaten.add(zutat);
                    }
                }

                List<Kategorie> kategorieListe = entityManager.findeAlle(Kategorie.class);
                csvDatenRezeptKategorie.forEach(csvZeileKategorie -> {
                    try {
                        if (UUID.fromString(csvZeileKategorie[0]).equals(rezeptID)){
                            for (Kategorie kategorie : kategorieListe){
                                if (kategorie.bekommeUUID().equals(UUID.fromString(csvZeileKategorie[1]))){
                                    kategorien.add(kategorie);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                for(Schwierigkeit enumSchwierigkeit : Schwierigkeit.values()){
                    if(enumSchwierigkeit.bekommeSchwierigkeitsgradID() == schwierigkeitsgrad){
                        schwierigkeit = enumSchwierigkeit;
                    }
                }

                element = new Rezept(rezeptID, titel, kategorien, zutaten, beschreibung, schwierigkeit, rezeptBild);
                entityManager.speichere( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();
    }

    // Methode zur Datenspeicherung aus dem Entitymanager in CSV Dateien
    public <T extends IPersistierbar> void speichereCSVDaten(String pfad, List<T> objekte) {
        CSVWriter writer = new CSVWriter(pfad, true);  // createIfNotExists

        List<Object[]> csvDaten = new ArrayList<>();

        objekte.forEach(e -> csvDaten.add(e.bekommeCSVDaten()));

        if(objekte.get(0).getClass().equals(Rezept.class)){
            CSVWriter writerKategorie = new CSVWriter(csvBilderPfad + "RezeptKategorie.csv", true);
            String[] kategorieKopf = new String[]{"RezeptID", "KategorieID"};
            List<Object[]> kategorieCSVDaten = new ArrayList<>();
            objekte.forEach(e -> {
                List<String[]> kategorieArray = ((Rezept) e).bekommeKategorienCSV();
                kategorieCSVDaten.addAll(kategorieArray);
            });

            try {
                writerKategorie.schreibeDaten(kategorieCSVDaten, kategorieKopf);
            } catch (IllegalArgumentException | IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            writer.schreibeDaten(csvDaten, objekte.get(0).bekommeCSVKopf());
        } catch (IllegalArgumentException | IOException e1) {
            e1.printStackTrace();
        }
    }

    // Methode um die zugehörigen Kategorien zu einem Rezept zu finden
    public String[][] findeRezepteZuKategorie(Kategorie eingabeKategorie){
        List<Rezept> alleRezepte = entityManager.findeAlle(Rezept.class);
        List<String[]> ausgewähltesRezept = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            ArrayList<Kategorie> rezeptKategorien = rezept.bekommeKategorien();
            for (Kategorie rezeptKategorie : rezeptKategorien){
                if (rezeptKategorie.equals(eingabeKategorie)){
                    ausgewähltesRezept.add(rezept.bekommeCSVDaten());
                }
            }

        }
        String[][] out = new String[ausgewähltesRezept.size()][ausgewähltesRezept.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = ausgewähltesRezept.get(i);
        }
        return out;
    }

    //Methode um alle Rezepte zu finden
    public String[][] findeAlleRezepte(){
        List<Rezept> alleRezepte = entityManager.findeAlle(Rezept.class);
        List<String[]> rezepte = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            rezepte.add(rezept.bekommeCSVDaten());
        }

        String[][] out = new String[rezepte.size()][rezepte.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = rezepte.get(i);
        }
        return out;
    }
}

package controller;

import model.*;
import util.CSVReader;
import util.CSVWriter;
import util.EntityManager;
import util.IPersistable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* Main Controller: Beinhaltet alle Funktionalitäten des Backends */
public class MainController {

    public final String csvBilderPfad = "src\\main\\resources\\CSVFiles\\";
    public EntityManager entityManager = new EntityManager();
    private IPersistable element = null;

    public void init() {
        try {
            loadCSVData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Methode zum laden der Daten aus den CSV Dateien und Erstellung von Einträgen im Entitymanager
    private void loadCSVData() throws IOException {
        CSVReader csvReader = new CSVReader(csvBilderPfad + "Kategorie.csv");
        List<String[]> csvDaten = csvReader.readData();
        csvDaten.forEach(csvLine -> {
            try {
                UUID kategorieID = UUID.fromString(csvLine[ Kategorie.CSVPositions.KATEGORIEID.ordinal() ]);
                String name = csvLine[ Kategorie.CSVPositions.NAME.ordinal() ];
                String tag = csvLine[ Kategorie.CSVPositions.TAG.ordinal() ];
                String beschreibung = csvLine[ Kategorie.CSVPositions.BESCHREIBUNG.ordinal() ];

                element = new Kategorie(kategorieID, name, tag, beschreibung);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();

        csvReader = new CSVReader(csvBilderPfad + "Einheit.csv");
        csvDaten = csvReader.readData();
        csvDaten.forEach(csvLine -> {
            try {
                UUID einheitID = UUID.fromString(csvLine[ Einheit.CSVPositionen.EINEHEITID.ordinal() ]);
                String name = csvLine[ Einheit.CSVPositionen.NAME.ordinal() ];
                String beschreibung = csvLine[ Einheit.CSVPositionen.BESCHREIBUNG.ordinal() ];

                element = new Einheit(einheitID, name, beschreibung);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();

        csvReader = new CSVReader(csvBilderPfad + "Zutaten.csv");
        csvDaten = csvReader.readData();
        csvDaten.forEach(csvLine -> {
            try {
                UUID zutatenID = UUID.fromString(csvLine[ Zutat.CSVPositions.ZUATATID.ordinal() ]);
                UUID zutatenRezeptID = UUID.fromString(csvLine[ Zutat.CSVPositions.REZEPTID.ordinal() ]);
                long menge = Long.parseLong( csvLine[ Zutat.CSVPositions.MENGE.ordinal() ]);
                UUID zutatenEinheitID = UUID.fromString(csvLine[ Zutat.CSVPositions.EINHEIT.ordinal() ]);
                String name = csvLine[ Zutat.CSVPositions.NAME.ordinal() ];

                Einheit einheit = entityManager.find(Einheit.class, zutatenEinheitID);

                element = new Zutat(zutatenID, zutatenRezeptID, menge, einheit, name);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();

        csvReader = new CSVReader(csvBilderPfad + "Bild.csv");
        csvDaten = csvReader.readData();
        csvDaten.forEach(csvLine -> {
            try {
                UUID bildID = UUID.fromString(csvLine[ Bilder.CSVPositions.BILDID.ordinal() ]);
                UUID rezeptID = UUID.fromString(csvLine[ Bilder.CSVPositions.REZEPTID.ordinal() ]);
                String pfad = csvLine[ Bilder.CSVPositions.PFAD.ordinal() ];

                element = new Bilder(bildID, rezeptID, pfad);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();

        csvReader = new CSVReader(csvBilderPfad + "RezeptKategorie.csv");
        List<String[]> csvDataCategoryOfRecipe = csvReader.readData();

        csvReader = new CSVReader(csvBilderPfad + "Rezept.csv");
        csvDaten = csvReader.readData();
        csvDaten.forEach(csvLine -> {
            try {
                UUID rezeptID = UUID.fromString(csvLine[ Rezept.CSVPositions.RECIPEID.ordinal() ]);
                String titel = csvLine[ Rezept.CSVPositions.TITLE.ordinal() ];
                int schwierigkeitsGrad = Integer.parseInt(csvLine[ Rezept.CSVPositions.DIFFICULTY.ordinal() ]);
                String beschreibung = csvLine[ Rezept.CSVPositions.DESCRIPTION.ordinal() ];
                Bilder rezeptBilder = null;
                ArrayList<Zutat> zutaten = new ArrayList<>();
                ArrayList<Kategorie> kategorien = new ArrayList<>();
                Schwierigkeit schwierigkeit = null;

                List<Bilder> bilderListe = entityManager.findAll(Bilder.class);
                for (Bilder bilder : bilderListe){
                    if (bilder.getRezeptID().equals(rezeptID)){
                        rezeptBilder = bilder;
                    }
                }

                List<Zutat> zutatenListe = entityManager.findAll(Zutat.class);
                for (Zutat ingredient : zutatenListe){
                    if (ingredient.getRezeptID().equals(rezeptID)){
                        zutaten.add(ingredient);
                    }
                }

                List<Kategorie> kategorieListe = entityManager.findAll(Kategorie.class);
                csvDataCategoryOfRecipe.forEach(csvLineCategory -> {
                    try {
                        if (UUID.fromString(csvLineCategory[0]).equals(rezeptID)){
                            for (Kategorie kategorie : kategorieListe){
                                if (kategorie.getUUID().equals(UUID.fromString(csvLineCategory[1]))){
                                    kategorien.add(kategorie);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                for(Schwierigkeit enumSchwierigkeit : Schwierigkeit.values()){
                    if(enumSchwierigkeit.getDifficultyID() == schwierigkeitsGrad){
                        schwierigkeit = enumSchwierigkeit;
                    }
                }

                element = new Rezept(rezeptID, titel, kategorien, zutaten, beschreibung, schwierigkeit, rezeptBilder);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvDaten.clear();
    }

    // Methode zur Datenspeicherung aus dem Entitymanager in CSV Dateien
    public <T extends IPersistable> void speichereCSVDaten(String path, List<T> objekte) {
        CSVWriter writer = new CSVWriter(path, true);  // createIfNotExists

        List<Object[]> csvDaten = new ArrayList<>();

        objekte.forEach(e -> csvDaten.add(e.getCSVData()));

        if(objekte.get(0).getClass().equals(Rezept.class)){
            CSVWriter writerKategorie = new CSVWriter(csvBilderPfad + "RezeptKategorie.csv", true);
            String[] kategorieKopf = new String[]{"RezeptID", "KategorieID"};
            List<Object[]> kategorieCSVDaten = new ArrayList<>();
            objekte.forEach(e -> {
                List<String[]> kategorieArray = ((Rezept) e).getCategoriesCSV();
                kategorieCSVDaten.addAll(kategorieArray);
            });

            try {
                writerKategorie.writeDataToFile(kategorieCSVDaten, kategorieKopf);
            } catch (IllegalArgumentException | IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            writer.writeDataToFile(csvDaten, objekte.get(0).getCSVHeader());
        } catch (IllegalArgumentException | IOException e1) {
            e1.printStackTrace();
        }
    }

    // Methode um die zugehörigen Kategorien zu einem Rezept zu finden
    public String[][] findeRezeptKategorien(Kategorie eingabeKategorie){
        List<Rezept> alleRezepte = entityManager.findAll(Rezept.class);
        List<String[]> ausgewähltesRezept = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            ArrayList<Kategorie> rezeptKategorien = rezept.getCategories();
            for (Kategorie rezeptKategorie : rezeptKategorien){
                if (rezeptKategorie.equals(eingabeKategorie)){
                    ausgewähltesRezept.add(rezept.getCSVData());
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
        List<Rezept> alleRezepte = entityManager.findAll(Rezept.class);
        List<String[]> rezepte = new ArrayList<>();

        for (Rezept rezept: alleRezepte){
            rezepte.add(rezept.getCSVData());
        }

        String[][] out = new String[rezepte.size()][rezepte.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = rezepte.get(i);
        }
        return out;
    }
}

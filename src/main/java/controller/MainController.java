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

    public final String csvFilePath = "src\\main\\resources\\CSVFiles\\";
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
        CSVReader csvReader = new CSVReader(csvFilePath + "Category.csv");
        List<String[]> csvData = csvReader.readData();
        csvData.forEach(csvLine -> {
            try {
                UUID categoryID = UUID.fromString(csvLine[ Kategorie.CSVPositions.KATEGORIEID.ordinal() ]);
                String name = csvLine[ Kategorie.CSVPositions.NAME.ordinal() ];
                String tag = csvLine[ Kategorie.CSVPositions.TAG.ordinal() ];
                String description = csvLine[ Kategorie.CSVPositions.BESCHREIBUNG.ordinal() ];

                element = new Kategorie(categoryID, name, tag, description);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvData.clear();

        csvReader = new CSVReader(csvFilePath + "Unit.csv");
        csvData = csvReader.readData();
        csvData.forEach(csvLine -> {
            try {
                UUID unitID = UUID.fromString(csvLine[ Einheit.CSVPositionen.EINEHEITID.ordinal() ]);
                String name = csvLine[ Einheit.CSVPositionen.NAME.ordinal() ];
                String description = csvLine[ Einheit.CSVPositionen.BESCHREIBUNG.ordinal() ];

                element = new Einheit(unitID, name, description);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvData.clear();

        csvReader = new CSVReader(csvFilePath + "Ingredient.csv");
        csvData = csvReader.readData();
        csvData.forEach(csvLine -> {
            try {
                UUID ingredientID = UUID.fromString(csvLine[ Zutat.CSVPositions.ZUATATID.ordinal() ]);
                UUID ingredientRecipeID = UUID.fromString(csvLine[ Zutat.CSVPositions.REZEPTID.ordinal() ]);
                long amount = Long.parseLong( csvLine[ Zutat.CSVPositions.MENGE.ordinal() ]);
                UUID ingredientUnitID = UUID.fromString(csvLine[ Zutat.CSVPositions.EINHEIT.ordinal() ]);
                String name = csvLine[ Zutat.CSVPositions.NAME.ordinal() ];

                Einheit einheit = entityManager.find(Einheit.class, ingredientUnitID);

                element = new Zutat(ingredientID, ingredientRecipeID, amount, einheit, name);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvData.clear();

        csvReader = new CSVReader(csvFilePath + "Picture.csv");
        csvData = csvReader.readData();
        csvData.forEach(csvLine -> {
            try {
                UUID pictureID = UUID.fromString(csvLine[ Bilder.CSVPositions.BILDID.ordinal() ]);
                UUID recipeID = UUID.fromString(csvLine[ Bilder.CSVPositions.REZEPTID.ordinal() ]);
                String path = csvLine[ Bilder.CSVPositions.PFAD.ordinal() ];

                element = new Bilder(pictureID, recipeID, path);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvData.clear();

        csvReader = new CSVReader(csvFilePath + "CategoryOfRecipe.csv");
        List<String[]> csvDataCategoryOfRecipe = csvReader.readData();

        csvReader = new CSVReader(csvFilePath + "Recipe.csv");
        csvData = csvReader.readData();
        csvData.forEach(csvLine -> {
            try {
                UUID recipeID = UUID.fromString(csvLine[ Recipe.CSVPositions.RECIPEID.ordinal() ]);
                String title = csvLine[ Recipe.CSVPositions.TITLE.ordinal() ];
                int difficultyInt = Integer.parseInt(csvLine[ Recipe.CSVPositions.DIFFICULTY.ordinal() ]);
                String description = csvLine[ Recipe.CSVPositions.DESCRIPTION.ordinal() ];
                Bilder rezeptBilder = null;
                ArrayList<Zutat> ingredients = new ArrayList<>();
                ArrayList<Kategorie> categories = new ArrayList<>();
                Schwierigkeit schwierigkeit = null;

                List<Bilder> picturesList = entityManager.findAll(Bilder.class);
                for (Bilder bilder : picturesList){
                    if (bilder.getRezeptID().equals(recipeID)){
                        rezeptBilder = bilder;
                    }
                }

                List<Zutat> ingredientsList = entityManager.findAll(Zutat.class);
                for (Zutat ingredient : ingredientsList){
                    if (ingredient.getRezeptID().equals(recipeID)){
                        ingredients.add(ingredient);
                    }
                }

                List<Kategorie> categoriesList = entityManager.findAll(Kategorie.class);
                csvDataCategoryOfRecipe.forEach(csvLineCategory -> {
                    try {
                        if (UUID.fromString(csvLineCategory[0]).equals(recipeID)){
                            for (Kategorie kategorie : categoriesList){
                                if (kategorie.getUUID().equals(UUID.fromString(csvLineCategory[1]))){
                                    categories.add(kategorie);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                for(Schwierigkeit enumSchwierigkeitEnum : Schwierigkeit.values()){
                    if(enumSchwierigkeitEnum.getDifficultyID() == difficultyInt){
                        schwierigkeit = enumSchwierigkeitEnum;
                    }
                }

                element = new Recipe(recipeID, title, categories, ingredients, description, schwierigkeit, rezeptBilder);
                entityManager.persist( element );
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        csvData.clear();
    }

    // Methode zur Datenspeicherung aus dem Entitymanager in CSV Dateien
    public <T extends IPersistable> void saveCSVData(String path, List<T> objects) {
        CSVWriter writer = new CSVWriter(path, true);  // createIfNotExists

        List<Object[]> csvData = new ArrayList<>();

        objects.forEach(e -> csvData.add(e.getCSVData()));

        if(objects.get(0).getClass().equals(Recipe.class)){
            CSVWriter writerCategory = new CSVWriter(csvFilePath + "CategoryOfRecipe.csv", true);
            String[] categoriesHeader = new String[]{"RecipeID", "CategoryID"};
            List<Object[]> categoriesCSVData = new ArrayList<>();
            objects.forEach(e -> {
                List<String[]> categoriesArray = ((Recipe) e).getCategoriesCSV();
                categoriesCSVData.addAll(categoriesArray);
            });

            try {
                writerCategory.writeDataToFile(categoriesCSVData, categoriesHeader);
            } catch (IllegalArgumentException | IOException e1) {
                e1.printStackTrace();
            }
        }

        try {
            writer.writeDataToFile(csvData, objects.get(0).getCSVHeader());
        } catch (IllegalArgumentException | IOException e1) {
            e1.printStackTrace();
        }
    }

    // Methode um die zugehörigen Kategorien zu einem Rezept zu finden
    public String[][] findRecipeOfCategory(Kategorie eingabeKategorie){
        List<Recipe> allRecipies = entityManager.findAll(Recipe.class);
        List<String[]> selectedRecpies = new ArrayList<>();

        for (Recipe recipe: allRecipies){
            ArrayList<Kategorie> categoriesOfRecipe = recipe.getCategories();
            for (Kategorie rezeptKategorie : categoriesOfRecipe){
                if (rezeptKategorie.equals(eingabeKategorie)){
                    selectedRecpies.add(recipe.getCSVData());
                }
            }

        }
        String[][] out = new String[selectedRecpies.size()][selectedRecpies.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = selectedRecpies.get(i);
        }
        return out;
    }

    //Methode um alle Rezepte zu finden
    public String[][] findAllRecipies(){
        List<Recipe> allRecipies = entityManager.findAll(Recipe.class);
        List<String[]> recipies = new ArrayList<>();

        for (Recipe recipe: allRecipies){
            recipies.add(recipe.getCSVData());
        }

        String[][] out = new String[recipies.size()][recipies.get(0).length];
        for (int i = 0; i < out.length; i++){
            out[i] = recipies.get(i);
        }
        return out;
    }
}

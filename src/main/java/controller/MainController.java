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
                UUID categoryID = UUID.fromString(csvLine[ Category.CSVPositions.CATEGORYID.ordinal() ]);
                String name = csvLine[ Category.CSVPositions.NAME.ordinal() ];
                String tag = csvLine[ Category.CSVPositions.TAG.ordinal() ];
                String description = csvLine[ Category.CSVPositions.DESCRIPTION.ordinal() ];

                element = new Category(categoryID, name, tag, description);
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
                UUID unitID = UUID.fromString(csvLine[ Unit.CSVPositions.UNITID.ordinal() ]);
                String name = csvLine[ Unit.CSVPositions.NAME.ordinal() ];
                String description = csvLine[ Unit.CSVPositions.DESCRIPTION.ordinal() ];

                element = new Unit(unitID, name, description);
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
                UUID ingredientID = UUID.fromString(csvLine[ Ingredient.CSVPositions.INGREDIENTID.ordinal() ]);
                UUID ingredientRecipeID = UUID.fromString(csvLine[ Ingredient.CSVPositions.RECIPEID.ordinal() ]);
                long amount = Long.parseLong( csvLine[ Ingredient.CSVPositions.AMOUNT.ordinal() ]);
                UUID ingredientUnitID = UUID.fromString(csvLine[ Ingredient.CSVPositions.UNIT.ordinal() ]);
                String name = csvLine[ Ingredient.CSVPositions.NAME.ordinal() ];

                Unit unit = entityManager.find(Unit.class, ingredientUnitID);

                element = new Ingredient(ingredientID, ingredientRecipeID, amount, unit, name);
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
                UUID pictureID = UUID.fromString(csvLine[ Picture.CSVPositions.PICTUREID.ordinal() ]);
                UUID recipeID = UUID.fromString(csvLine[ Picture.CSVPositions.RECIPEID.ordinal() ]);
                String path = csvLine[ Picture.CSVPositions.PATH.ordinal() ];

                element = new Picture(pictureID, recipeID, path);
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
                Picture recipePicture = null;
                ArrayList<Ingredient> ingredients = new ArrayList<>();
                ArrayList<Category> categories = new ArrayList<>();
                Difficulty difficulty = null;

                List<Picture> picturesList = entityManager.findAll(Picture.class);
                for (Picture picture : picturesList){
                    if (picture.getRecipeID().equals(recipeID)){
                        recipePicture = picture;
                    }
                }

                List<Ingredient> ingredientsList = entityManager.findAll(Ingredient.class);
                for (Ingredient ingredient : ingredientsList){
                    if (ingredient.getRecipeID().equals(recipeID)){
                        ingredients.add(ingredient);
                    }
                }

                List<Category> categoriesList = entityManager.findAll(Category.class);
                csvDataCategoryOfRecipe.forEach(csvLineCategory -> {
                    try {
                        if (UUID.fromString(csvLineCategory[0]).equals(recipeID)){
                            for (Category category : categoriesList){
                                if (category.getUUID().equals(UUID.fromString(csvLineCategory[1]))){
                                    categories.add(category);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

                for(Difficulty difficultyEnum : Difficulty.values()){
                    if(difficultyEnum.getDifficultyID() == difficultyInt){
                        difficulty = difficultyEnum;
                    }
                }

                element = new Recipe(recipeID, title, categories, ingredients, description, difficulty, recipePicture );
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
    public String[][] findRecipeOfCategory(Category inputCategory){
        List<Recipe> allRecipies = entityManager.findAll(Recipe.class);
        List<String[]> selectedRecpies = new ArrayList<>();

        for (Recipe recipe: allRecipies){
            ArrayList<Category> categoriesOfRecipe = recipe.getCategories();
            for (Category categoryOfRecipe : categoriesOfRecipe){
                if (categoryOfRecipe.equals(inputCategory)){
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

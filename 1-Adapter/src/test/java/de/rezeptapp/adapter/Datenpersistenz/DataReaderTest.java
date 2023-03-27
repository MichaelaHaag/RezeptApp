package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.Kategorie.Kategorie;
import de.rezeptapp.domain.Rezept.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DataReaderTest {

    @Test
    public void test_ladeCSVDaten() {
        // Pareameter für erwartetes Objekt
        UUID rezeptUUID = UUID.fromString("ece61344-6509-447b-b5ab-6d8c2c8ce86b");
        String titel = "Rezept";
        String beschreibung = "Testrezept";
        Schwierigkeit schwierigkeit = Schwierigkeit.Normal;
        Bild bild = new Bild(UUID.fromString("3da0154b-0303-4bd6-a812-d706a27c5663"), rezeptUUID, "resources/Pictures/Food/spaghetti-bolognese.jpg");
        ArrayList<Kategorie> kategorien = new ArrayList<>();
            kategorien.add(new Kategorie(UUID.fromString("9d5834d8-8d28-41f3-80dc-b7646718984a"), "Nudelgerichte", "Nudeln", "Rezepte für Nudelgrichte"));
            kategorien.add(new Kategorie(UUID.fromString("9ca185aa-b4bc-45c8-a309-a52145ee6be3"),"Fleischgerichte","Fleisch","Rezepte für Fleischgerichte"));
        ArrayList<Zutat> zutaten = new ArrayList<>();
            zutaten.add(new Zutat(UUID.fromString("1b459d8d-253f-4232-a2a7-0e0477fbcd09"), rezeptUUID, new Menge( 500 , Einheit.Gramm), "Zucker"));
            zutaten.add(new Zutat(UUID.fromString("4158ee00-0423-48a4-a61b-28afc5b76a41"), rezeptUUID, new Menge( 1 , Einheit.Kilogramm), "Mehl"));

        DataReader dataReader = new DataReader();
        try {
            dataReader.erstelleKategorieAusCSV(new String[]{"9d5834d8-8d28-41f3-80dc-b7646718984a","Nudelgerichte","Nudeln","Rezepte für Nudelgrichte"});
            dataReader.erstelleKategorieAusCSV(new String[]{"9ca185aa-b4bc-45c8-a309-a52145ee6be3","Fleischgerichte","Fleisch","Rezepte für Fleischgerichte"});
            dataReader.erstelleZutatAusCSV(new String[]{"1b459d8d-253f-4232-a2a7-0e0477fbcd09","ece61344-6509-447b-b5ab-6d8c2c8ce86b","500-g","Zucker"});
            dataReader.erstelleZutatAusCSV(new String[]{"4158ee00-0423-48a4-a61b-28afc5b76a41","ece61344-6509-447b-b5ab-6d8c2c8ce86b","1-kg","Mehl"});
            dataReader.erstelleBildAusCSV(new String[]{"3da0154b-0303-4bd6-a812-d706a27c5663","ece61344-6509-447b-b5ab-6d8c2c8ce86b","resources/Pictures/Food/spaghetti-bolognese.jpg"});

            List<String[]> csvDatenRezeptKategorie = new ArrayList<>();
            csvDatenRezeptKategorie.add(new String[]{"ece61344-6509-447b-b5ab-6d8c2c8ce86b","9d5834d8-8d28-41f3-80dc-b7646718984a"});
            csvDatenRezeptKategorie.add(new String[]{"ece61344-6509-447b-b5ab-6d8c2c8ce86b","9ca185aa-b4bc-45c8-a309-a52145ee6be3"});

            dataReader.erstelleRezeptAusCSV(new String[]{"ece61344-6509-447b-b5ab-6d8c2c8ce86b","Rezept","Normal","Testrezept"}, csvDatenRezeptKategorie);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Rezept erstelltesRezept = dataReader.entityManager.finde(Rezept.class, rezeptUUID);
        Rezept erwartetesRezept = new Rezept(rezeptUUID, titel,kategorien, zutaten,beschreibung, schwierigkeit, bild);

        assertThat(erwartetesRezept).isEqualToComparingFieldByFieldRecursively((erstelltesRezept));
    }
}

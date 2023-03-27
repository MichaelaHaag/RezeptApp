package de.rezeptapp.adapter.GUIFunktionen;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.domain.Kategorie.Kategorie;
import de.rezeptapp.domain.Kategorie.KategorieRepository;
import de.rezeptapp.domain.Rezept.RezeptRepository;

import java.util.UUID;

public class FunktionenListenÜbersicht {
    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();

    public static  String[][] alleRezepte(UUID id, DataReader dataReader) {
        String[][] alleRezept;
        String name = "11111111-1111-1111-1111-111111111111";
        UUID idAlleRezepte = UUID.fromString(name);
        if(id.equals(idAlleRezepte)){
            try {
                alleRezept = rezeptRepository.findeAlleRezepteUI(dataReader.entityManager);
            } catch (Exception e) {
                e.printStackTrace();
                alleRezept = new String[0][0];
            }
        }else{
            Kategorie eingegbeneKategorie = kategorieRepository.findeKategorie(id, dataReader.entityManager);
            try {
                alleRezept = rezeptRepository.findeRezepteZuKategorie(eingegbeneKategorie, dataReader.entityManager);
            }catch (Exception e) {
                e.printStackTrace();
                alleRezept = new String[0][0];
            }
        }
        return alleRezept;
    }
}

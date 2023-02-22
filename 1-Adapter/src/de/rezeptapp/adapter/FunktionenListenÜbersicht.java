package de.rezeptapp.adapter;

import de.rezeptapp.domain.*;

import javax.swing.*;
import java.util.UUID;

public class FunktionenListenÜbersicht {
    final static KategorieRepository kategorieRepository = new KategorieRepository();
    final static RezeptRepository rezeptRepository = new RezeptRepository();

    public static  String[][] alleRezepte(UUID id) {
        String name = "11111111-1111-1111-1111-111111111111";
        UUID idAlleRezepte = UUID.fromString(name);
        if(id.equals(idAlleRezepte)){
            try {
                String[][] alleRezept = rezeptRepository.findeAlleRezepteUI();
                return alleRezept;

            } catch (Exception e) {
                e.printStackTrace();
                String[][] alleRezept = new String[0][0];
                return alleRezept;
            }

        }else{
            Kategorie eingegbeneKategorie = kategorieRepository.findeKategorie(id);
            try {
                String[][] rezepte = rezeptRepository.findeRezepteZuKategorie(eingegbeneKategorie);
                return rezepte;
            }catch (Exception e) {
                e.printStackTrace();
                String[][] alleRezept = new String[0][0];
                return alleRezept;
            }
        }
    }

    public static void elementAusgwewählt(UUID id, JFrame frame){
        new RezeptAnsicht(id, frame);
    }
}

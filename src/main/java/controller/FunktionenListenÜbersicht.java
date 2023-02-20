package controller;

import model.Kategorie;
import model.KategorieRepository;
import model.Rezept;
import view.ListenÜbersicht;
import view.RezeptAnsicht;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.UUID;

import static app.RezeptApp.controller;

public class FunktionenListenÜbersicht {
    final static KategorieRepository kategorieRepository = new KategorieRepository();

    public static  String[][] alleRezepte(UUID id) {
        String name = "11111111-1111-1111-1111-111111111111";
        UUID idAlleRezepte = UUID.fromString(name);
        if(id.equals(idAlleRezepte)){
            try {
                String[][] alleRezept = controller.findeAlleRezepte();
                return alleRezept;

            } catch (Exception e) {
                e.printStackTrace();
                String[][] alleRezept = new String[0][0];
                return alleRezept;
            }

        }else{
            Kategorie eingegbeneKategorie = kategorieRepository.findeKategorie(id);
            try {
                String[][] rezepte = controller.findeRezepteZuKategorie(eingegbeneKategorie);
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

package controller;

import model.Rezept;
import view.NeuesRezept;
import view.ZufallsGenerator;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static app.RezeptApp.controller;

public class FunktionenZufallsGenerator {

    public static void neuerZufallsGenerator(JFrame frame){
        new ZufallsGenerator();
        frame.dispose();
    }

    //Erzeugt aus den vorhandenen UUIDs eine zufällige UUID
    public static UUID zufälligeRezeptUUID(){
        List<Rezept> listeAlleRezepte = controller.entityManager.findeAlle(Rezept.class);
        Random zufallsGenerator =new Random();
        int zufallszahl = zufallsGenerator.nextInt(listeAlleRezepte.size());
        Rezept zufallsRezept = listeAlleRezepte.get(zufallszahl);
        return zufallsRezept.bekommeUUID();
    }
}

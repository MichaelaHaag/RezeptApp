package de.rezeptapp.adapter;

import de.rezeptapp.domain.*;
import view.ZufallsGenerator;

import javax.swing.*;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FunktionenZufallsGenerator {
    final static RezeptRepository rezeptRepository = new RezeptRepository();

    public static void neuerZufallsGenerator(JFrame frame){
        new ZufallsGenerator();
        frame.dispose();
    }

    //Erzeugt aus den vorhandenen UUIDs eine zufällige UUID
    public static UUID zufälligeRezeptUUID(){
        List<Rezept> listeAlleRezepte = rezeptRepository.findeAlleRezepte();
        Random zufallsGenerator =new Random();
        int zufallszahl = zufallsGenerator.nextInt(listeAlleRezepte.size());
        Rezept zufallsRezept = listeAlleRezepte.get(zufallszahl);
        return zufallsRezept.bekommeUUID();
    }
}

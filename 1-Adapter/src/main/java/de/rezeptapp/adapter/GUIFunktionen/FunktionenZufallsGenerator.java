package de.rezeptapp.adapter.GUIFunktionen;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.domain.Rezept.Rezept;
import de.rezeptapp.domain.Rezept.RezeptRepository;

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FunktionenZufallsGenerator {
    final static RezeptRepository rezeptRepository = new RezeptRepository();

    //Erzeugt aus den vorhandenen UUIDs eine zufällige UUID
    public static UUID zufälligeRezeptUUID(DataReader dataReader){
        List<Rezept> alleRezepte = rezeptRepository.findeAlleRezepte(dataReader.entityManager);
        int zufallszahl = new Random().nextInt(alleRezepte.size());
        return alleRezepte.get(zufallszahl).bekommeUUID();
    }
}

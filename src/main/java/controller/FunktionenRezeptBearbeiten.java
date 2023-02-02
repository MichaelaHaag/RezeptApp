package controller;

import model.Rezept;
import view.NeuesRezept;
import view.RezeptBearbeiten;

import java.util.UUID;

public class FunktionenRezeptBearbeiten {

    public static void rezeptBearbeiten(Rezept rezept) {
        new RezeptBearbeiten(rezept);

    }


}

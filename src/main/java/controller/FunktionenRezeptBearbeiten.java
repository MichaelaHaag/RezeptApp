package controller;

import model.Rezept;
import model.Schwierigkeit;
import view.NeuesRezept;
import view.RezeptBearbeiten;

import javax.swing.*;
import java.util.Enumeration;
import java.util.UUID;

public class FunktionenRezeptBearbeiten {

    public static void rezeptBearbeiten(Rezept rezept) {
        new RezeptBearbeiten(rezept);

    }

    public static void rezeptBearbeitungSpeichern() {
        //TODO: Hier muss der Code rein, wie das Bearbeitete rezept gespeichert werden muss

    }



}

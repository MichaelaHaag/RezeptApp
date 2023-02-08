package controller;

import view.NeuesRezept;
import view.RezeptAnsicht;

import javax.swing.*;
import java.util.UUID;

public class FunktionenNeuesRezept {

    public static void neuesRezeptErstellen(JFrame frame){
        new NeuesRezept();
        frame.dispose();
    }

    public static void neuesRezeptSpeichern(){

    }
}

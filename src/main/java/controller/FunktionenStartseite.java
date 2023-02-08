package controller;

import view.ListenÜbersicht;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.UUID;

public class FunktionenStartseite {

    /*Methode für die Funktionalität der Buttons. Wird ein Button geklickt, so öffnet sich die UI der KLasse
  Listpage (eine Liste mit allen Rezepten der ausgewählten Kategorie */
    public static void listenÜbersichtÖffnen(ActionEvent ae) {
        JButton angeklickterButton = (JButton)ae.getSource();
        String name = angeklickterButton.getName();
        UUID id = UUID.fromString(name);
        new ListenÜbersicht(id);
    }
    public static void kategorieHinzufügen(String message){
        //TODO: hier muss die neue Kategorie in der CSV gespeichert werden. In Message steht der Name der Kategorie
        System.out.print(message);
    }
}

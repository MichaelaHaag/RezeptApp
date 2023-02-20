package de.rezeptapp.plugins.main;

import de.rezeptapp.adapter.FunktionenStartseite;
import de.rezeptapp.application.MainController;

/* Main Klasse: Startet die Startseite und den Controller */
public class RezeptApp {
    public static MainController controller;

    public static void main(String[] args) {

        controller = new MainController();
        controller.init();

        FunktionenStartseite.startseiteStarten();
    }
}

package app;

import controller.MainController;
import view.Startseite;

/* Main Klasse: Startet die Startseite und den Controller */
public class RezeptApp {
    public static MainController controller;

    public static void main(String[] args) {

        controller = new MainController();
        controller.init();

        Startseite startseite = new Startseite();
        startseite.setVisible(true);
        startseite.setBounds(300,70,900,650);
    }
}

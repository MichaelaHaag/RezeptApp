package de.rezeptapp.plugins.main;

import de.rezeptapp.adapter.Datenpersistenz.DataReader;
import de.rezeptapp.plugins.gui.Startseite;

import java.io.IOException;

/* Main Klasse: Startet die Startseite und den Controller */
public class RezeptApp {
    public static DataReader dataReader;

    public static void main(String[] args) {
        dataReader = new DataReader();
        try {
            dataReader.ladeCSVDaten();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Startseite startseite = new Startseite(dataReader);
        startseite.setVisible(true);
        startseite.setBounds(300,70,900,650);
    }
}

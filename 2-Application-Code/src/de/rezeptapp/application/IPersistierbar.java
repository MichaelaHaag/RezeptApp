package de.rezeptapp.application;

/* IPersistierbar Interface: Enthält Methoden zur Speicherung der Objekte */
public interface IPersistierbar {

    Object bekommeUUID();
    String[] bekommeCSVKopf();
    String[] bekommeCSVDaten();

}

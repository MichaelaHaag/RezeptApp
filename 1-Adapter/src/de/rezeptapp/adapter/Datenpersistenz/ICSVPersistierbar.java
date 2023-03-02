package de.rezeptapp.adapter.Datenpersistenz;

/* ICSVPersistierbar Interface: Enthält Methoden zur Speicherung der Objekte */
public interface ICSVPersistierbar {
    Object bekommeUUID();
    String[] bekommeCSVKopf();
    String[] bekommeCSVDaten();
}

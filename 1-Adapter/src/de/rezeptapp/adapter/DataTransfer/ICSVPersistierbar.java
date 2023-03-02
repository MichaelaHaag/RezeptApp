package de.rezeptapp.adapter.DataTransfer;

/* ICSVPersistierbar Interface: Enthält Methoden zur Speicherung der Objekte */
public interface ICSVPersistierbar {
    Object bekommeUUID();
    String[] bekommeCSVKopf();
    String[] bekommeCSVDaten();
}

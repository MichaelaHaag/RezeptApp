package de.rezeptapp.domain.Kategorie;



import de.rezeptapp.domain.IPersistierbar;

import java.util.UUID;

/* Kategorie Klasse: Definiert eine Kategorie eines Rezeptes */
public class Kategorie implements IPersistierbar{
    private final UUID kategorieId;
    private String name;
    private String kurzformName;
    private String beschreibung;

    public Kategorie(String name, String kurzformName, String beschreibung) {
        this.kategorieId = UUID.randomUUID();
        this.name = name;
        this.kurzformName = kurzformName;
        this.beschreibung = beschreibung;
    }

    public Kategorie(UUID kategorieId, String name, String kurzformName, String beschreibung) {
        this.kategorieId = kategorieId;
        this.name = name;
        this.kurzformName = kurzformName;
        this.beschreibung = beschreibung;
    }


    public String toString() {
        return this.name;
    }

    public Object bekommeUUID() {
        return kategorieId;
    }

    public String bekommeName() {
        return name;
    }

    public String bekommeKurzformName() {
        return kurzformName;
    }

    public String bekommeKategorieBeschreibung() {
        return beschreibung;
    }

}

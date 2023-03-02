package de.rezeptapp.domain.Rezept;

import de.rezeptapp.domain.IPersistierbar;

import java.util.UUID;

/* Bild Klasse: Definiert ein Bild eines Rezeptes */
public class Bild implements IPersistierbar{
    private final UUID bildID;
    private final UUID rezeptID;
    private final String pfad;

    public Bild(UUID bildID, UUID rezeptID, String pfad) {
        this.bildID = bildID;
        this.rezeptID = rezeptID;
        this.pfad = pfad;
    }

    public Bild(UUID rezeptID, String pfad) {
        this.bildID = UUID.randomUUID();
        this.rezeptID = rezeptID;
        this.pfad = pfad;
    }

    public UUID bekommeUUID() {
        return bildID;
    }

    public String bekommePfad() {
        return pfad;
    }

    public UUID bekommeRezeptID() {
        return rezeptID;
    }

}

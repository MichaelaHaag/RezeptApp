package de.rezeptapp.domain;

import util.IPersistierbar;

import java.util.UUID;

/* Zutat Klasse: Definiert eine Zutat eines Rezeptes */
public class Zutat implements IPersistierbar {
    private final UUID zutatID;
    private UUID rezeptID;
    private Menge menge;
    private String name;

    public Zutat(UUID rezeptID, Menge menge, String name) {
        this.zutatID = UUID.randomUUID();
        this.rezeptID = rezeptID;
        this.menge = menge;
        this.name = name;
    }

    public Zutat(UUID zutatID, UUID rezeptID, Menge menge, String name) {
        this.zutatID = zutatID;
        this.rezeptID = rezeptID;
        this.menge = menge;
        this.name = name;
    }

    public UUID bekommeUUID() {
        return zutatID;
    }

    public Menge bekommeMenge() {
        return menge;
    }

    public String bekommeName() {
        return name;
    }

    public UUID bekommenRezeptID() { return rezeptID; }
}

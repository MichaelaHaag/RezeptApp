package model;

import util.IPersistierbar;

import java.util.Objects;
import java.util.UUID;

public final class Menge implements IPersistierbar {
    private final UUID mengeID;
    private final long menge;
    private final Einheit einheit;

    public Menge(long menge, Einheit einheit){
        super();
        if(menge<0){
            throw new IllegalArgumentException(
                    "Gewicht kann nicht neagativ sein: " + menge);
        }
        this.mengeID = UUID.randomUUID();
        this.menge = menge;
        this.einheit = einheit;
    }

    public Menge(UUID mengeID, long menge, Einheit einheit){
        super();
        if(menge<0){
            throw new IllegalArgumentException(
                    "Gewicht kann nicht neagativ sein: " + menge);
        }
        this.mengeID = mengeID;
        this.menge = menge;
        this.einheit = einheit;
    }

    public long dieMenge(){
        return this.menge;
    }

    public Einheit dieEinheit(){
        return this.einheit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menge menge1 = (Menge) o;
        return Objects.equals(menge, menge1.menge) &&
                Objects.equals(einheit, menge1.einheit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(menge, einheit);
    }

    public enum CSVPosition {
        MENGEID,
        MENGE,
        EINHEIT
    }

    @Override
    public Object bekommeUUID() {
        return this.mengeID;
    }

    @Override
    public String[] bekommeCSVKopf() {
        return new String[]{"MengeID","Menge","Einheit"};
    }

    @Override
    public String[] bekommeCSVDaten() {
        String[] daten = new String[Menge.CSVPosition.values().length];
        daten[Menge.CSVPosition.MENGEID.ordinal()] = String.valueOf(this.mengeID);
        daten[Menge.CSVPosition.MENGE.ordinal()] = String.valueOf(this.menge);
        daten[Menge.CSVPosition.EINHEIT.ordinal()] = String.valueOf(this.einheit);
        return daten;
    }
}

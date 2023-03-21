package de.rezeptapp.domain.Rezept;

import java.util.Objects;

public class Menge {
    private final long menge;
    private final Einheit einheit;

    public Menge(long menge, Einheit einheit){
        super();
        if(menge<0){
            throw new IllegalArgumentException(
                    "Gewicht kann nicht neagativ sein: " + menge);
        }
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

    @Override
    public String toString() {
        return menge + "-" + einheit;
    }
}

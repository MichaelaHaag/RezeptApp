package de.rezeptapp.domain.Rezept;

public enum Einheit {

    Kilogramm("kg", "Kilogramm"),
    Gramm("g", "Gramm"),
    Milliliter("ml", "Milliliter"),
    Liter("l", "Liter"),
    Esslöffel("EL", "Esslöffel"),
    Teelöffel("TL", "Teelöffel"),
    Stück("Stück", "Stück"),
    Packung("Pck", "Packung"),
    Priese("Priese", "Priese"),
    Glas("Glas", "Glas"),
    Dose("Dose", "Dose");

    private final String name;
    private final String beschreibung;

    Einheit(String name, String beschreibung) {
        if (name.isEmpty()||beschreibung.isEmpty()) {
            throw new IllegalArgumentException(
                    "Name oder Beschreibung dürfen nicht leer gelassen werden!");
        }
        this.name = name;
        this.beschreibung = beschreibung;
    }

    public String bekommeName() {
        return this.name;
    }

    public String bekommeBeschreibung() {
        return this.beschreibung;
    }

    @Override
    public String toString() {
        return name;
    }
}

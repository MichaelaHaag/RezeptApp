package model;

/* Schwiergigkeitsenum: Definiert den Schwierigkeitsgrad eines Rezeptes */
public enum Schwierigkeit {

    Einfach(1, "Einfach"),
    Normal(2, "Normal"),
    Schwer(3, "Schwer");

    private final int schwierigkeitsgradID;
    private final String name;

    Schwierigkeit(int schwierigkeitsgradID, String name) {
        if (schwierigkeitsgradID < 0) {
            throw new IllegalArgumentException(
                    "Schwierigkeitgrad darf nicht negativ sein " + schwierigkeitsgradID);
        }

        this.schwierigkeitsgradID = schwierigkeitsgradID;
        this.name = name;
    }

    public int bekommeSchwierigkeitsgradID() {
        return this.schwierigkeitsgradID;
    }

    public String bekomeName() {
        return this.name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}

package model;

/* Schwiergigkeitsenum: Definiert den Schwierigkeitsgrad eines Rezeptes */
public enum Schwierigkeit {

    Einfach(1, "Einfach"),
    Normal(2, "Normal"),
    Schwer(3, "Schwer");

    private final int schwierigkeitID;
    private final String name;

    Schwierigkeit(int schwierigkeitID, String name) {
        this.schwierigkeitID = schwierigkeitID;
        this.name = name;
    }

    public int getDifficultyID() {
        return schwierigkeitID;
    }

    public String getName() {
        return name;
    }

    public static String[] getAlleSchwierigkeiten(){
        String[] schwierigkeiten = new String[Schwierigkeit.values().length];
        for (int i = 0; i < Schwierigkeit.values().length; i++ ){
            schwierigkeiten[i] = Schwierigkeit.values()[i].getName();
        }
        return schwierigkeiten;
    }

    @Override
    public String toString() {
        return name;
    }

}

package model;

/* Schwiergigkeitsenum: Definiert den Schwierigkeitsgrad eines Rezeptes */
public enum Schwierigkeit {

    Einfach(1, "Einfach"),
    Normal(2, "Normal"),
    Schwer(3, "Schwer");

    private final int schwierigkeitsgradID;
    private final String name;

    Schwierigkeit(int schwierigkeitsgradID, String name) {
        this.schwierigkeitsgradID = schwierigkeitsgradID;
        this.name = name;
    }

    public int bekommeSchwierigkeitsgradID() {
        return schwierigkeitsgradID;
    }

    public String bekomeName() {
        return name;
    }

    public static String[] bekommeAlleSchwierigkeiten(){
        String[] schwierigkeiten = new String[Schwierigkeit.values().length];
        for (int i = 0; i < Schwierigkeit.values().length; i++ ){
            schwierigkeiten[i] = Schwierigkeit.values()[i].bekomeName();
        }
        return schwierigkeiten;
    }

    @Override
    public String toString() {
        return name;
    }

}

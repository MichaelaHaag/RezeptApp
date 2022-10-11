package model;

/* Schwiergigkeitsenum: Definiert den Schwierigkeitsgrad eines Rezeptes */
public enum Difficulty {

    Easy(1, "Easy"),
    Normal(2, "Normal"),
    Difficult(3, "Difficult");

    private final int difficultyID;
    private final String name;

    Difficulty(int difficultyID, String name) {
        this.difficultyID = difficultyID;
        this.name = name;
    }

    public int getDifficultyID() {
        return difficultyID;
    }

    public String getName() {
        return name;
    }

    public static String[] getAllDifficulties(){
        String[] difficulties = new String[Difficulty.values().length];
        for (int i = 0; i <Difficulty.values().length; i++ ){
            difficulties[i] = Difficulty.values()[i].getName();
        }
        return difficulties;
    }

    @Override
    public String toString() {
        return name;
    }

}

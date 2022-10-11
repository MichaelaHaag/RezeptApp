package model;

import util.IPersistable;

import java.util.UUID;

/* Einheit Klasse: Definiert eine Einheit einer Zutat */
public class Unit implements IPersistable {
    private final UUID unitID;
    private String name;
    private String description;

    public Unit(String name, String description) {
        this.unitID = UUID.randomUUID();
        this.name = name;
        this.description = description;
    }

    public Unit(UUID unitID, String name, String description) {
        this.unitID = unitID;
        this.name = name;
        this.description = description;
    }

    public enum CSVPositions {
        UNITID,
        NAME,
        DESCRIPTION
    }

    public UUID getUUID() {
        return unitID;
    }

    @Override
    public String[] getCSVHeader() {
        return new String[]{"UnitID","Name","Description"};
    }

    @Override
    public String[] getCSVData() {
        String[] data = new String[Unit.CSVPositions.values().length];
        data[CSVPositions.UNITID.ordinal()] = String.valueOf(this.unitID);
        data[CSVPositions.NAME.ordinal()] = String.valueOf(this.name);
        data[Unit.CSVPositions.DESCRIPTION.ordinal()] = String.valueOf(this.description);
        return data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

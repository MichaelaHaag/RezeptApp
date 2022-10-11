package util;

/* IPersistable Interface: Enthält Methoden zur Speicherung der Objekte */
public interface IPersistable {

    Object getUUID();
    String[] getCSVHeader();
    String[] getCSVData();

}

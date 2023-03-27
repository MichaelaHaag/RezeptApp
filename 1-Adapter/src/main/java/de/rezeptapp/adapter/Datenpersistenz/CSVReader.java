package de.rezeptapp.adapter.Datenpersistenz;

import java.io.*;
import java.util.*;

/* CSV Reader Klasse: Ist zuständig für das Auslesen der CSV Dateien */
public class CSVReader {

    private final File csvFile;

    public CSVReader( String csvFileName ) throws IllegalArgumentException {
        this.csvFile = this.checkFile(csvFileName);
    }

    private File checkFile(String fileName ) throws IllegalArgumentException {
        if( fileName == null || fileName.length() == 0 )
            throw new IllegalArgumentException( "File name must be given!" );
        File csvFile = new File( fileName );
        if( !csvFile.exists() ) throw new IllegalArgumentException( "File '" + fileName + "' does not exist" );

        return csvFile;
    }

    public final List<String[]> leseDaten()
            throws IOException {
        int expectedColumns = 0;
        String encoding =  "UTF-8";
        char comment = '#';
        char delimiter = ';';

        List<String[]> allLines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.csvFile),
                encoding))) {
            String line = "";
            String[] lineElements;

            while (line != null) {
                line = reader.readLine();
                if (line == null) break;

                if (line.startsWith("" + comment) || line.length() == 0) {
                    continue;
                }

                lineElements = line.split("" + delimiter);
                if (expectedColumns > 0 && expectedColumns != lineElements.length) {

                    String[] sArr = new String[expectedColumns];
                    if (expectedColumns <= lineElements.length) {

                        for (int i = 0; i < expectedColumns; i++) {
                            sArr[i] = lineElements[i] == null ? "" : lineElements[i];
                        }
                    } else if (expectedColumns > lineElements.length) {
                        System.arraycopy(lineElements, 0, sArr, 0, lineElements.length);
                        for (int i = lineElements.length; i < expectedColumns; i++) {
                            sArr[i] = "";
                        }
                    }
                    lineElements = sArr;
                }
                allLines.add(lineElements);
            }
        }

        return allLines;
    }
}

package de.rezeptapp.adapter.Datenpersistenz;

import java.io.*;
import java.util.List;

/* CSV Writer Klasse: Ist zuständig für das Schreiben der CSV Dateien */
public class CSVWriter {

    private final File csvFile;

    public CSVWriter( String csvFileName, boolean createIfNotExists ) throws IllegalArgumentException {
        this.csvFile = this.checkFile(csvFileName, createIfNotExists );
    }

    private File checkFile(String fileName, boolean create ) throws IllegalArgumentException {
        if( fileName == null || fileName.length() == 0 )
            throw new IllegalArgumentException( "File name must be given!" );
        File csvFile = new File( fileName );
        if( !csvFile.exists() ){
            if( create ){
                try{
                    csvFile.createNewFile();
                }
                catch( IOException e ){
                    throw new IllegalArgumentException( "File could not be created: " + e.getMessage() );
                }
            }
            else{
                throw new IllegalArgumentException( "File does not exist! If it should be created automatically, use argument value create=true" );
            }
        }
        return csvFile;
    }

    public final void schreibeDaten(List<Object[]> data, String[] header) throws IOException, IllegalArgumentException {
        char delimiter = ';';
        char commentChar = '#';
        String encodingName = "UTF-8";

        if( data == null ) throw new IllegalArgumentException( "Data must be given!" );

        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream( this.csvFile ),
                encodingName ) );
        if( data.get( 0 ) == null ){
            writer.close();
            throw new IllegalArgumentException( "first data line is not given!" );
        }

        // write the header
        if( header != null && header.length > 0 ){
            try{
                writer.write( commentChar );
                int arrLen = header.length;
                for( int i = 0 ; i < arrLen ; i++ ){
                    if( header[ i ] != null ) // if objArr[i] == null: ignore
                        // writing (-> empty value)
                        writer.write( header[ i ] );
                    if( arrLen - i > 1 ) writer.write( delimiter );
                }
                writer.newLine();
            }
            catch( IOException e ){
                writer.flush();
                writer.close();
                throw e;
            }
        }

        // write the data
        try{
            int arrLen = data.get( 0 ).length;
            data.forEach( e -> {
                try{
                    for( int i = 0 ; i < arrLen ; i++ ){
                        if( e[ i ] != null ) // if objArr[i] == null: ignore
                            // writing (-> empty value)
                            writer.write( e[ i ].toString() );
                        if( arrLen - i > 1 ) writer.write( delimiter );
                    }
                    writer.newLine();
                }
                catch( IOException ex ){
                    ex.printStackTrace();
                }
            } );
        } finally{
            writer.flush();
            writer.close();
        }
    }
}


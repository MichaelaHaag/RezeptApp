package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* EntityManager Klasse: Ist zuständig für die Datenhaltung */
public class EntityManager {

    private final Map<Object,IPersistable> allElements = new HashMap<>();

    public boolean contains( IPersistable ip ) {
        return this.allElements.containsValue( ip );
    }

    // Speicherung der Objekte im EntityManager
    public void persist( IPersistable ip ) throws Exception {
        if( this.contains( ip ) )
            throw new Exception( "Element exsistiert bereits! " );
        this.allElements.put( ip.getUUID(), ip );
    }

    // Suche einzelnes Objekt im EntityManger
    public <T extends IPersistable> T find( Class<T> c, Object key ) {
        for( IPersistable ip: this.allElements.values() ){
            // hardcoded:
            if( c.isInstance(ip) && ip.getUUID().equals(key) ) return (T) ip;
        }
        return null;
    }

    // Suche alle Objekte der selben Klasse im EntityManager
    public <T extends IPersistable> List<T> findAll( Class<T> c ) {
        List<T> foundElements = new ArrayList<>();
        for( IPersistable ip: this.allElements.values() ){
            if( c.isInstance(ip) ) foundElements.add((T) ip);
        }
        return foundElements;
    }
}

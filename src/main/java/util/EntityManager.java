package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* EntityManager Klasse: Ist zuständig für die Datenhaltung */
public class EntityManager {

    private final Map<Object, IPersistierbar> allElements = new HashMap<>();

    public boolean contains( IPersistierbar ip ) {
        return this.allElements.containsValue( ip );
    }

    // Speicherung der Objekte im EntityManager
    public void speichere(IPersistierbar ip ) throws Exception {
        if( this.contains( ip ) )
            throw new Exception( "Element exsistiert bereits! " );
        this.allElements.put( ip.bekommeUUID(), ip );
    }

    // Suche einzelnes Objekt im EntityManger
    public <T extends IPersistierbar> T finde(Class<T> c, Object key ) {
        for( IPersistierbar ip: this.allElements.values() ){
            // hardcoded:
            if( c.isInstance(ip) && ip.bekommeUUID().equals(key) ) return (T) ip;
        }
        return null;
    }

    // Suche alle Objekte der selben Klasse im EntityManager
    public <T extends IPersistierbar> List<T> findeAlle(Class<T> c ) {
        List<T> foundElements = new ArrayList<>();
        for( IPersistierbar ip: this.allElements.values() ){
            if( c.isInstance(ip) ) foundElements.add((T) ip);
        }
        return foundElements;
    }

    // Enfterne Objekt aus EntityManger
    public void entferne(IPersistierbar ip ) {
        this.allElements.remove( ip.bekommeUUID() );
    }
}

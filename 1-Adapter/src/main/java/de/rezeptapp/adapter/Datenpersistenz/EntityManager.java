package de.rezeptapp.adapter.Datenpersistenz;

import de.rezeptapp.domain.IPersistierbar;
import de.rezeptapp.domain.IEntityManager;

import java.util.*;

/* EntityManager Klasse: Ist zuständig für die Datenhaltung */
public class EntityManager implements IEntityManager{

    private final Map<Object, IPersistierbar> allElements = new HashMap<>();

    public boolean existiert(IPersistierbar ip ) {
        return this.allElements.containsValue( ip );
    }

    // Speicherung der Objekte im EntityManager
    public void speichere(IPersistierbar ip ) throws Exception {
        if( this.existiert( ip ) )
            throw new Exception( "Element exsistiert bereits! " );
        this.allElements.put( ip.bekommeUUID(), ip );
    }

    // Suche einzelnes Objekt im EntityManger
    public <T extends IPersistierbar> T finde(Class<T> c, UUID key ) {
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

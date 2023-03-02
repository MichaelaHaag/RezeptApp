package de.rezeptapp.domain;

import java.util.List;
import java.util.UUID;

public interface IEntityManager {
    boolean existiert(IPersistierbar ip );

    // Speicherung der Objekte im EntityManager
    void speichere(IPersistierbar ip) throws Exception;

    // Suche einzelnes Objekt im EntityManger
    <T extends IPersistierbar> T finde(Class<T> c, UUID key );

    // Suche alle Objekte der selben Klasse im EntityManager
    <T extends IPersistierbar> List<T> findeAlle(Class<T> c );

    // Enfterne Objekt aus EntityManger
    void entferne(IPersistierbar ip );
}

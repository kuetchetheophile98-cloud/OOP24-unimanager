package it.unibo.unimanager.model;

/**
 * Osservatore dei cambiamenti dell'ateneo (pattern Observer). La vista si registra
 * per aggiornarsi ogni volta che i dati cambiano.
 */
@FunctionalInterface
public interface AteneoListener {

    /** Invocato dopo ogni modifica riuscita ai dati dell'ateneo. */
    void ateneoCambiato();
}

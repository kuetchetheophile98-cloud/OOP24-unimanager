package it.unibo.unimanager.model;

/**
 * Eccezione base per tutte le violazioni delle regole di dominio sollevate dal modello.
 * La vista cattura questo tipo per mostrare un messaggio comprensibile all'utente.
 */
public class DominioException extends RuntimeException {

    private static final long serialVersionUID = 1L;

   
    public DominioException(final String messaggio) {
        super(messaggio);
    }
}

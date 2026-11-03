package it.unibo.unimanager.model;

/**
 * Sollevata quando un'operazione richiede o impedisce un'iscrizione a un appello:
 * iscrizione doppia oppure voto per uno studente non iscritto.
 */
public class IscrizioneNonValidaException extends DominioException {

    private static final long serialVersionUID = 1L;

    /**
     * @param messaggio descrizione del problema
     */
    public IscrizioneNonValidaException(final String messaggio) {
        super(messaggio);
    }
}

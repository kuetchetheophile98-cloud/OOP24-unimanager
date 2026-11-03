package it.unibo.unimanager.model;

/** Sollevata quando un identificativo non corrisponde a nessuna entità dell'ateneo. */
public class EntitaNonTrovataException extends DominioException {

    private static final long serialVersionUID = 1L;

    /**
     * @param tipo nome dell'entità cercata (es. "Studente")
     * @param id identificativo non trovato
     */
    public EntitaNonTrovataException(final String tipo, final int id) {
        super(tipo + " con id " + id + " non trovato.");
    }
}

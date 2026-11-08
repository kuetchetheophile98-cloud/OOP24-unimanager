package it.unibo.unimanager.model;

/* Sollevata quando per uno studente   esiste già un voto registrato per lo stesso appello. */
 public class VotoDuplicatoException extends DominioException {

private static final long serialVersionUID = 1L;


    
    public VotoDuplicatoException(final   Studente studente, final Appello appello) {
    super("Per " + studente.getNomeCompleto()   + " esiste già un voto per l'appello " + appello.getId() + ".");
    }
}

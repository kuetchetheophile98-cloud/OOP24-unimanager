package it.unibo.unimanager.model;

/* Sollevata quando si tenta di inserire uno studente con una matricola già presente. */
     public class MatricolaDuplicataException extends DominioException {

     private static final long serialVersionUID = 1L;

  
         public MatricolaDuplicataException(final String matricola ) {
         super("Esiste già uno studente con matricola "    + matricola + ".");
    }
}

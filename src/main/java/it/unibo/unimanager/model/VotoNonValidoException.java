    package it.unibo.unimanager.model ;

/* Sollevata quando un voto     non rispetta i vincoli    (18-30, lode solo con 30). */
public class VotoNonValidoException extends DominioException {

    private static final long serialVersionUID = 1L;

    public VotoNonValidoException(final String messaggio) {
          super(messaggio) ;



  
        }
}

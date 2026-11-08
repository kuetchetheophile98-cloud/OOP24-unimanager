package it.unibo.unimanager.model;

/** Sollevata quando un identificativo non corrisponde a nessuna entità dell'ateneo. */
public class EntitaNonTrovataException extends DominioException {

    private static final long serialVersionUID = 1L;

    
     public EntitaNonTrovataException(  final String tipo,  final int id) {
 super(tipo + " con id " + id + "    non trovato.") ;
    }
 }

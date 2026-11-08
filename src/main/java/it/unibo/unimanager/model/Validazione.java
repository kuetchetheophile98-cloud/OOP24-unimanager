package it.unibo.unimanager.model;

/*Utilità di validazione condivisa dalle entità del modello*/
final class Validazione {
 private Validazione() {
        // classe di utilità: non istanziabile
    }



    static String testoNonVuoto(final String valore, final String campo) {
    if (valore == null ||  valore.isBlank()) {
    throw new DominioException(campo + " obbligatorio.");
        }
    return valore.trim();
    }
}

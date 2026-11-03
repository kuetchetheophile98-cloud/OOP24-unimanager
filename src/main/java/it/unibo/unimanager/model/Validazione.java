package it.unibo.unimanager.model;

/** Utilità di validazione condivisa dalle entità del modello. */
final class Validazione {

    private Validazione() {
        // classe di utilità: non istanziabile
    }

    /**
     * Verifica che il testo sia presente e non composto solo da spazi.
     *
     * @param valore testo da controllare
     * @param campo nome del campo, usato nel messaggio d'errore
     * @return il testo senza spazi iniziali e finali
     * @throws DominioException se il testo è nullo o vuoto
     */
    static String testoNonVuoto(final String valore, final String campo) {
        if (valore == null || valore.isBlank()) {
            throw new DominioException(campo + " obbligatorio.");
        }
        return valore.trim();
    }
}

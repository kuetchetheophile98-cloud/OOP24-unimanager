package it.unibo.unimanager.model;

/**
 * Uno studente dell'ateneo. L'oggetto è immutabile: identificativo, matricola e
 * dati anagrafici non cambiano dopo la creazione.
 */
public final class Studente {

    private final int id;
    private final String nome;
    private final String cognome;
    private final String matricola;

    /**
     * @param id identificativo interno
     * @param nome nome (obbligatorio)
     * @param cognome cognome (obbligatorio)
     * @param matricola matricola (obbligatoria)
     * @throws DominioException se un dato testuale è vuoto
     */
    public Studente(final int id, final String nome, final String cognome, final String matricola) {
        this.id = id;
        this.nome = Validazione.testoNonVuoto(nome, "Nome");
        this.cognome = Validazione.testoNonVuoto(cognome, "Cognome");
        this.matricola = Validazione.testoNonVuoto(matricola, "Matricola");
    }

    /** @return l'identificativo interno */
    public int getId() {
        return id;
    }

    /** @return il nome */
    public String getNome() {
        return nome;
    }

    /** @return il cognome */
    public String getCognome() {
        return cognome;
    }

    /** @return la matricola */
    public String getMatricola() {
        return matricola;
    }

    /** @return nome e cognome separati da uno spazio */
    public String getNomeCompleto() {
        return nome + " " + cognome;
    }

    @Override
    public boolean equals(final Object altro) {
        return altro instanceof Studente && ((Studente) altro).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return id + " - " + cognome + " " + nome + " (" + matricola + ")";
    }
}

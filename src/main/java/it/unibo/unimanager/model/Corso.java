package it.unibo.unimanager.model;

/** Un insegnamento dell'ateneo, con il suo peso in crediti formativi (CFU). Immutabile. */
public final class Corso {

    /** Numero minimo di CFU di un corso. */
    public static final int CFU_MIN = 1;
    /** Numero massimo di CFU di un corso. */
    public static final int CFU_MAX = 30;

    private final int id;
    private final String nome;
    private final int cfu;

    /**
     * @param id identificativo interno
     * @param nome nome del corso (obbligatorio)
     * @param cfu crediti formativi, tra {@value #CFU_MIN} e {@value #CFU_MAX}
     * @throws DominioException se il nome è vuoto o i CFU sono fuori intervallo
     */
    public Corso(final int id, final String nome, final int cfu) {
        if (cfu < CFU_MIN || cfu > CFU_MAX) {
            throw new DominioException("I CFU devono essere tra " + CFU_MIN + " e " + CFU_MAX + ".");
        }
        this.id = id;
        this.nome = Validazione.testoNonVuoto(nome, "Nome del corso");
        this.cfu = cfu;
    }

    /** @return l'identificativo interno */
    public int getId() {
        return id;
    }

    /** @return il nome del corso */
    public String getNome() {
        return nome;
    }

    /** @return i crediti formativi */
    public int getCfu() {
        return cfu;
    }

    @Override
    public boolean equals(final Object altro) {
        return altro instanceof Corso && ((Corso) altro).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return id + " - " + nome + " (" + cfu + " CFU)";
    }
}

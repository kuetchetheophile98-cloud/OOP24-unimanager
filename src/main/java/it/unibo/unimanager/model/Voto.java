package it.unibo.unimanager.model;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Il voto ottenuto da uno studente a un appello. L'oggetto è immutabile e valida
 * i propri vincoli alla creazione: valore tra 18 e 30, lode ammessa solo con 30.
 */
public final class Voto {

    /** Voto minimo di superamento. */
    public static final int VALORE_MIN = 18;
    /** Voto massimo. */
    public static final int VALORE_MAX = 30;

    private final int id;
    private final Studente studente;
    private final Appello appello;
    private final int valore;
    private final boolean lode;
    private final LocalDate dataRegistrazione;

    /**
     * @param id identificativo interno
     * @param studente lo studente valutato
     * @param appello l'appello in cui il voto è stato ottenuto
     * @param valore il voto, tra {@value #VALORE_MIN} e {@value #VALORE_MAX}
     * @param lode {@code true} per "30 e lode"
     * @param dataRegistrazione data in cui il voto è stato registrato
     * @throws VotoNonValidoException se il voto è fuori intervallo o la lode non è con 30
     */
    public Voto(final int id, final Studente studente, final Appello appello, final int valore,
            final boolean lode, final LocalDate dataRegistrazione) {
        if (valore < VALORE_MIN || valore > VALORE_MAX) {
            throw new VotoNonValidoException(
                    "Il voto deve essere tra " + VALORE_MIN + " e " + VALORE_MAX + ".");
        }
        if (lode && valore != VALORE_MAX) {
            throw new VotoNonValidoException("La lode può essere assegnata solo con 30.");
        }
        this.id = id;
        this.studente = Objects.requireNonNull(studente, "Lo studente è obbligatorio.");
        this.appello = Objects.requireNonNull(appello, "L'appello è obbligatorio.");
        this.valore = valore;
        this.lode = lode;
        this.dataRegistrazione = Objects.requireNonNull(dataRegistrazione, "La data è obbligatoria.");
    }

    /** @return l'identificativo interno */
    public int getId() {
        return id;
    }

    /** @return lo studente valutato */
    public Studente getStudente() {
        return studente;
    }

    /** @return l'appello del voto */
    public Appello getAppello() {
        return appello;
    }

    /** @return il corso a cui il voto si riferisce */
    public Corso getCorso() {
        return appello.getCorso();
    }

    /** @return il valore numerico (30 anche per "30 e lode") */
    public int getValore() {
        return valore;
    }

    /** @return {@code true} se il voto è "30 e lode" */
    public boolean isLode() {
        return lode;
    }

    /** @return la data di registrazione */
    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    /** @return il voto come testo, ad esempio "28" oppure "30L" */
    public String getEtichetta() {
        return lode ? valore + "L" : String.valueOf(valore);
    }

    @Override
    public boolean equals(final Object altro) {
        return altro instanceof Voto && ((Voto) altro).id == id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return id + " - " + studente.getNomeCompleto() + " -> " + getEtichetta()
                + " (" + getCorso().getNome() + " il " + appello.getData() + ")";
    }
}

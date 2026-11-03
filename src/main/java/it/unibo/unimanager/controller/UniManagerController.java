package it.unibo.unimanager.controller;

import it.unibo.unimanager.model.Appello;
import it.unibo.unimanager.model.Ateneo;
import it.unibo.unimanager.model.AteneoListener;
import it.unibo.unimanager.model.CalcoloMedia;
import it.unibo.unimanager.model.Corso;
import it.unibo.unimanager.model.DominioException;
import it.unibo.unimanager.model.MediaAritmetica;
import it.unibo.unimanager.model.MediaPonderata;
import it.unibo.unimanager.model.Studente;
import it.unibo.unimanager.model.Voto;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;
import java.util.OptionalDouble;

/**
 * Controller dell'applicazione: traduce le azioni dell'utente, espresse come dati
 * grezzi provenienti dalla vista, in operazioni sul modello {@link Ateneo}.
 * Non dipende da Swing, quindi si può testare senza interfaccia grafica.
 */
public final class UniManagerController {

    private final Ateneo ateneo;
    private final CalcoloMedia aritmetica = new MediaAritmetica();
    private final CalcoloMedia ponderata = new MediaPonderata();

    /**
     * @param ateneo il modello da controllare
     */
    public UniManagerController(final Ateneo ateneo) {
        this.ateneo = Objects.requireNonNull(ateneo, "Il modello è obbligatorio.");
    }

    /**
     * Registra un osservatore notificato a ogni modifica dei dati.
     *
     * @param osservatore l'osservatore da registrare
     */
    public void aggiungiOsservatore(final AteneoListener osservatore) {
        ateneo.aggiungiOsservatore(osservatore);
    }

    /**
     * @param nome nome dello studente
     * @param cognome cognome dello studente
     * @param matricola matricola dello studente
     * @return lo studente creato
     * @throws DominioException se i dati non sono validi o la matricola è duplicata
     */
    public Studente aggiungiStudente(final String nome, final String cognome, final String matricola) {
        return ateneo.aggiungiStudente(nome, cognome, matricola);
    }

    /**
     * @param nome nome del corso
     * @param cfu crediti formativi
     * @return il corso creato
     * @throws DominioException se i dati non sono validi
     */
    public Corso aggiungiCorso(final String nome, final int cfu) {
        return ateneo.aggiungiCorso(nome, cfu);
    }

    /**
     * @param corsoId identificativo del corso
     * @param data data in formato AAAA-MM-GG
     * @param luogo luogo dell'appello
     * @return l'appello creato
     * @throws DominioException se la data non è leggibile, il corso non esiste o il luogo è vuoto
     */
    public Appello creaAppello(final int corsoId, final String data, final String luogo) {
        return ateneo.creaAppello(corsoId, leggiData(data), luogo);
    }

    /**
     * @param studenteId identificativo dello studente
     * @param appelloId identificativo dell'appello
     * @throws DominioException se studente o appello non esistono o l'iscrizione è doppia
     */
    public void iscrivi(final int studenteId, final int appelloId) {
        ateneo.iscriviStudente(studenteId, appelloId);
    }

    /**
     * @param studenteId identificativo dello studente
     * @param appelloId identificativo dell'appello
     * @param valore voto tra 18 e 30
     * @param lode {@code true} per "30 e lode"
     * @return il voto registrato
     * @throws DominioException se il voto o l'iscrizione non sono validi
     */
    public Voto registraVoto(final int studenteId, final int appelloId, final int valore, final boolean lode) {
        return ateneo.registraVoto(studenteId, appelloId, valore, lode);
    }

    /**
     * @param studenteId identificativo dello studente
     * @return media aritmetica dei voti dello studente, vuota se non ha voti
     * @throws DominioException se lo studente non esiste
     */
    public OptionalDouble mediaAritmetica(final int studenteId) {
        return ateneo.mediaStudente(studenteId, aritmetica);
    }

    /**
     * @param studenteId identificativo dello studente
     * @return media dei voti pesata sui CFU, vuota se lo studente non ha voti
     * @throws DominioException se lo studente non esiste
     */
    public OptionalDouble mediaPonderata(final int studenteId) {
        return ateneo.mediaStudente(studenteId, ponderata);
    }

    /**
     * @param corsoId identificativo del corso
     * @return media aritmetica dei voti del corso, vuota se non ci sono voti
     * @throws DominioException se il corso non esiste
     */
    public OptionalDouble mediaCorso(final int corsoId) {
        return ateneo.mediaCorso(corsoId);
    }

    /** @return gli studenti, in ordine di inserimento */
    public List<Studente> studenti() {
        return ateneo.getStudenti();
    }

    /** @return i corsi, in ordine di inserimento */
    public List<Corso> corsi() {
        return ateneo.getCorsi();
    }

    /** @return gli appelli, in ordine di inserimento */
    public List<Appello> appelli() {
        return ateneo.getAppelli();
    }

    /** @return i voti, in ordine di registrazione */
    public List<Voto> voti() {
        return ateneo.getVoti();
    }

    private static LocalDate leggiData(final String testo) {
        if (testo == null) {
            throw new DominioException("Data obbligatoria.");
        }
        try {
            return LocalDate.parse(testo.trim());
        } catch (DateTimeParseException e) {
            throw new DominioException("Data non valida: usa il formato AAAA-MM-GG.");
        }
    }
}

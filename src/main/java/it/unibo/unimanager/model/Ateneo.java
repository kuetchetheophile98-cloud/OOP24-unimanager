package it.unibo.unimanager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;

/**
 * Punto di accesso al modello: custodisce studenti, corsi, appelli e voti e fa
 * rispettare le regole di dominio. Le entità restituite sono immutabili oppure
 * esposte in sola lettura.
 */
public final class Ateneo {

    private final Map<Integer, Studente> studenti = new LinkedHashMap<>();
    private final Map<Integer, Corso> corsi = new LinkedHashMap<>();
    private final Map<Integer, Appello> appelli = new LinkedHashMap<>();
    private final List<Voto> voti = new ArrayList<>();
    private final Set<String> matricole = new HashSet<>();
    private final List<AteneoListener> osservatori = new ArrayList<>();

    private int prossimoStudente = 1;
    private int prossimoCorso = 1;
    private int prossimoAppello = 1;
    private int prossimoVoto = 1;

    /**
     * Registra un osservatore, notificato dopo ogni modifica riuscita.
     *
     * @param osservatore l'osservatore da aggiungere
     */
    public void aggiungiOsservatore(final AteneoListener osservatore) {
        osservatori.add(osservatore);
    }

    /**
     * Aggiunge uno studente.
     *
     * @param nome nome dello studente
     * @param cognome cognome dello studente
     * @param matricola matricola, unica nell'ateneo
     * @return lo studente creato
     * @throws MatricolaDuplicataException se la matricola è già in uso
     * @throws DominioException se un dato è vuoto
     */
    public Studente aggiungiStudente(final String nome, final String cognome, final String matricola) {
        final Studente studente = new Studente(prossimoStudente, nome, cognome, matricola);
        if (!matricole.add(studente.getMatricola())) {
            throw new MatricolaDuplicataException(studente.getMatricola());
        }
        prossimoStudente++;
        studenti.put(studente.getId(), studente);
        notifica();
        return studente;
    }

    /**
     * Aggiunge un corso.
     *
     * @param nome nome del corso
     * @param cfu crediti formativi
     * @return il corso creato
     * @throws DominioException se il nome è vuoto o i CFU non sono validi
     */
    public Corso aggiungiCorso(final String nome, final int cfu) {
        final Corso corso = new Corso(prossimoCorso, nome, cfu);
        prossimoCorso++;
        corsi.put(corso.getId(), corso);
        notifica();
        return corso;
    }

    /**
     * Crea un appello per un corso esistente.
     *
     * @param corsoId identificativo del corso
     * @param data data dell'appello
     * @param luogo luogo dell'appello
     * @return l'appello creato
     * @throws EntitaNonTrovataException se il corso non esiste
     * @throws DominioException se il luogo è vuoto
     */
    public Appello creaAppello(final int corsoId, final LocalDate data, final String luogo) {
        final Corso corso = trovaCorso(corsoId);
        final Appello appello = new Appello(prossimoAppello, corso, data, luogo);
        prossimoAppello++;
        appelli.put(appello.getId(), appello);
        notifica();
        return appello;
    }

    /**
     * Iscrive uno studente a un appello.
     *
     * @param studenteId identificativo dello studente
     * @param appelloId identificativo dell'appello
     * @throws EntitaNonTrovataException se studente o appello non esistono
     * @throws IscrizioneNonValidaException se lo studente è già iscritto
     */
    public void iscriviStudente(final int studenteId, final int appelloId) {
        final Studente studente = trovaStudente(studenteId);
        trovaAppello(appelloId).iscrivi(studente);
        notifica();
    }

    /**
     * Registra il voto di uno studente a un appello. Lo studente deve essere
     * iscritto e non deve avere già un voto per lo stesso appello.
     *
     * @param studenteId identificativo dello studente
     * @param appelloId identificativo dell'appello
     * @param valore voto tra 18 e 30
     * @param lode {@code true} per "30 e lode"
     * @return il voto registrato
     * @throws EntitaNonTrovataException se studente o appello non esistono
     * @throws IscrizioneNonValidaException se lo studente non è iscritto all'appello
     * @throws VotoDuplicatoException se esiste già un voto dello studente per l'appello
     * @throws VotoNonValidoException se il voto non rispetta i vincoli
     */
    public Voto registraVoto(final int studenteId, final int appelloId, final int valore, final boolean lode) {
        final Studente studente = trovaStudente(studenteId);
        final Appello appello = trovaAppello(appelloId);
        if (!appello.isIscritto(studente)) {
            throw new IscrizioneNonValidaException(
                    studente.getNomeCompleto() + " non è iscritto all'appello " + appelloId + ".");
        }
        final boolean giaRegistrato = voti.stream()
                .anyMatch(v -> v.getStudente().equals(studente) && v.getAppello().equals(appello));
        if (giaRegistrato) {
            throw new VotoDuplicatoException(studente, appello);
        }
        final Voto voto = new Voto(prossimoVoto, studente, appello, valore, lode, LocalDate.now());
        prossimoVoto++;
        voti.add(voto);
        notifica();
        return voto;
    }

    /**
     * Calcola la media dei voti di uno studente con la strategia indicata.
     *
     * @param studenteId identificativo dello studente
     * @param calcolo la strategia di calcolo
     * @return la media, oppure un valore vuoto se lo studente non ha voti
     * @throws EntitaNonTrovataException se lo studente non esiste
     */
    public OptionalDouble mediaStudente(final int studenteId, final CalcoloMedia calcolo) {
        trovaStudente(studenteId);
        return calcolo.calcola(votiDiStudente(studenteId));
    }

    /**
     * Calcola la media aritmetica dei voti di un corso, su tutti gli studenti.
     *
     * @param corsoId identificativo del corso
     * @return la media, oppure un valore vuoto se il corso non ha voti
     * @throws EntitaNonTrovataException se il corso non esiste
     */
    public OptionalDouble mediaCorso(final int corsoId) {
        trovaCorso(corsoId);
        return new MediaAritmetica().calcola(votiDiCorso(corsoId));
    }

    /**
     * @param studenteId identificativo dello studente
     * @return i voti dello studente, in ordine di registrazione
     */
    public List<Voto> votiDiStudente(final int studenteId) {
        return voti.stream().filter(v -> v.getStudente().getId() == studenteId).toList();
    }

    /**
     * @param corsoId identificativo del corso
     * @return i voti ottenuti negli appelli del corso, in ordine di registrazione
     */
    public List<Voto> votiDiCorso(final int corsoId) {
        return voti.stream().filter(v -> v.getCorso().getId() == corsoId).toList();
    }

    /**
     * @param id identificativo dello studente
     * @return lo studente
     * @throws EntitaNonTrovataException se non esiste
     */
    public Studente trovaStudente(final int id) {
        final Studente studente = studenti.get(id);
        if (studente == null) {
            throw new EntitaNonTrovataException("Studente", id);
        }
        return studente;
    }

    /**
     * @param id identificativo del corso
     * @return il corso
     * @throws EntitaNonTrovataException se non esiste
     */
    public Corso trovaCorso(final int id) {
        final Corso corso = corsi.get(id);
        if (corso == null) {
            throw new EntitaNonTrovataException("Corso", id);
        }
        return corso;
    }

    /**
     * @param id identificativo dell'appello
     * @return l'appello
     * @throws EntitaNonTrovataException se non esiste
     */
    public Appello trovaAppello(final int id) {
        final Appello appello = appelli.get(id);
        if (appello == null) {
            throw new EntitaNonTrovataException("Appello", id);
        }
        return appello;
    }

    /** @return copia non modificabile degli studenti, in ordine di inserimento */
    public List<Studente> getStudenti() {
        return List.copyOf(studenti.values());
    }

    /** @return copia non modificabile dei corsi, in ordine di inserimento */
    public List<Corso> getCorsi() {
        return List.copyOf(corsi.values());
    }

    /** @return copia non modificabile degli appelli, in ordine di inserimento */
    public List<Appello> getAppelli() {
        return List.copyOf(appelli.values());
    }

    /** @return copia non modificabile dei voti, in ordine di registrazione */
    public List<Voto> getVoti() {
        return List.copyOf(voti);
    }

    private void notifica() {
        for (final AteneoListener osservatore : osservatori) {
            osservatore.ateneoCambiato();
        }
    }
}

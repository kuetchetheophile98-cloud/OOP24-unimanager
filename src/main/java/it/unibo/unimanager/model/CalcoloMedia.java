package it.unibo.unimanager.model;

import java.util.Collection;
import java.util.OptionalDouble;

/**
 * Strategia di calcolo della media di un insieme di voti (pattern Strategy).
 * Permette di aggiungere nuovi tipi di media senza modificare {@link Ateneo}.
 */
public interface CalcoloMedia {

    /**
     * @param voti i voti da considerare
     * @return la media, oppure un valore vuoto se non ci sono voti
     */
    OptionalDouble calcola(Collection<Voto> voti);

    /** @return il nome della media, adatto alla visualizzazione */
    String getNome();
}

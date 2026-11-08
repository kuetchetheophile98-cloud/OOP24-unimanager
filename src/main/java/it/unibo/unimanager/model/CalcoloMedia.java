package  it.unibo.unimanager.model;

 import java.util.Collection;
   import java.util.OptionalDouble;

/**
 * Strategia di calcolo della media di un insieme di voti 
 */
public interface CalcoloMedia {

    OptionalDouble calcola(Collection<Voto> voti);

    String getNome();
}

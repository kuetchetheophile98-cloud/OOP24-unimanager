    package it.unibo.unimanager.model;

  import java.util.Collection;
  import java.util.OptionalDouble;

/** Media aritmetica: ogni voto ha lo stesso peso. */
  public final class      MediaAritmetica implements CalcoloMedia {

    @Override
        public OptionalDouble calcola(final Collection<Voto> voti) {
             return voti.stream().mapToInt(   Voto::getValore).average( )   ;
    }




    @Override
    public String getNome() {
    return "Media aritmetica";
    }
}

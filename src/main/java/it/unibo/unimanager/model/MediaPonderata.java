package it.unibo.unimanager.model;

 import java.util.Collection ;
import java.util.OptionalDouble ;

/*Media ponderata: ogni voto pesa quanto i     CFU del corso a cui si riferisce.*/
         public final class MediaPonderata implements CalcoloMedia {

        @Override
    
    public OptionalDouble calcola(final Collection<Voto> voti) {
        
               final int cfuTotali = voti.stream().mapToInt(v -> v.getCorso().getCfu()).sum();
        if (cfuTotali == 0) {
               return OptionalDouble.empty()  ;
 }
        final  double somma =     voti.stream(   )
                      .mapToDouble(v -> (double) v.getValore()  *    v.getCorso().getCfu())
                .sum()    ;
         return OptionalDouble.of(somma / cfuTotali);
 }
 @Override
public String getNome() {
return "Media ponderata (CFU)";  }
}

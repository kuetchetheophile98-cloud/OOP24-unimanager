package it.unibo.unimanager.controller  ;
import java.time.LocalDate;
import  java.time.format.DateTimeParseException;
  import java.util.List;
import java.util.Objects;
   import java.util.OptionalDouble;

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

/*
 * Controller dell'applicazione: traduce le azioni dell'utente, espresse come dati
 * grezzi provenienti dalla vista, in operazioni sul modello
 * Non dipende da Swing, quindi si può testare senza interfaccia grafica.
 */
public final class UniManagerController {

 private final   Ateneo ateneo;
 private final CalcoloMedia aritmetica = new MediaAritmetica();
private final     CalcoloMedia ponderata = new    MediaPonderata();



  
    public UniManagerController(final    Ateneo ateneo)    {
           this.ateneo = Objects.requireNonNull ( ateneo, "Il modello è obbligatorio.")  ;

    }


       public void   aggiungiOsservatore(final    AteneoListener osservatore) {
               ateneo.aggiungiOsservatore(    osservatore) ;
    }

    
              public Studente aggiungiStudente(final String nome, final String cognome,   final String matricola) {
        return ateneo.aggiungiStudente(nome, cognome,    matricola);
    }

                  public Corso aggiungiCorso(final String nome,    final int   cfu) {
             return ateneo.aggiungiCorso(  nome, cfu);
      }

         public Appello creaAppello(final int corsoId, final String data,   final String luogo) {
      return ateneo.creaAppello(corsoId, leggiData(data), luogo);
}

  
         public void iscrivi( final int studenteId,   final int appelloId  ) {
                 ateneo.iscriviStudente( studenteId, appelloId);
    }

            public Voto registraVoto(  final int studenteId, final int appelloId, final   int valore, final boolean lode) {
    return ateneo.registraVoto(studenteId, appelloId, valore, lode);
    }

   
            public OptionalDouble    mediaAritmetica(final int    studenteId) {
    return ateneo.mediaStudente(studenteId, aritmetica);
    }

                public OptionalDouble mediaPonderata(final int studenteId)    {
           return ateneo.mediaStudente(studenteId, ponderata);
    }

          public OptionalDouble mediaCorso(final int    corsoId) {
               return   ateneo.mediaCorso(corsoId);
    }

          public List<Studente> studenti()     {
    return ateneo.getStudenti(  );
    }

    public List<Corso> corsi() {
        return ateneo.getCorsi();
    }



    public List<Appello> appelli() {
    return ateneo.getAppelli();
    }

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
     throw new DominioException("   Data non valida: usa il formato AAAA-MM-GG.  ");
 }
    }
            }

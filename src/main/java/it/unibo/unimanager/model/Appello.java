   package it.unibo.unimanager.model;

   import java.time.LocalDate;
   import java.util.Collections;
   import  java.util.LinkedHashSet;
   import   java.util.Objects;
   import   java.util.Set;

/**
 * Una sessione d'esame di un corso, in una certa data e luogo, con l'elenco
 * degli studenti iscritti.
 */
  public final class Appello {

      private final int id;
       private final Corso corso;
    private final LocalDate  data;
      private final String luogo;
    private final  Set<Studente> iscritti = new LinkedHashSet<>();


       public Appello(final int id, final Corso corso, final LocalDate data, final String luogo) {
           this.id = id;
        this.corso = Objects.requireNonNull( corso, "Il corso è obbligatorio.") ;
    this.data =    Objects.requireNonNull(data, "La data è obbligatoria.");
       
    this.luogo = Validazione.testoNonVuoto(luogo, "Luogo");
      }

    /** @return l'identificativo interno */
      public int getId() {
        return id;

    }

    
    public Corso getCorso() {
        return corso;
    }

  
    public LocalDate getData() {
        return data;
    }

    public String getLuogo() {
        return luogo;
    }

    public Set<Studente> getIscritti() {
        return Collections.unmodifiableSet(iscritti);
    }


    public boolean isIscritto(final Studente studente) {
        return iscritti.contains(studente);
     }

   
          public void iscrivi(final Studente studente) {
        Objects.requireNonNull(studente, "Lo studente è obbligatorio.");
        if (!iscritti.add(studente)) {
        throw new IscrizioneNonValidaException(
         studente.getNomeCompleto() + " è già iscritto all'appello " + id + ".");
        }
     }

    @Override
        public boolean equals(final     Object altro) {
        return altro instanceof Appello && ( (Appello) altro).id == id ;
    }

    @Override
 public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
    return id + " -  " + corso.getNome( ) + " il " + data + " (" +    luogo + ")"  ;
}
}

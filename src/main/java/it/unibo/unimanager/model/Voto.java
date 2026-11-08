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

            private    final int id;
            private final    Studente studente;
            private final   Appello  appello;
            private final int valore   ;
         private final boolean   lode;
    private   final LocalDate  dataRegistrazione;

   
 public Voto(final int id, final    Studente studente, final Appello appello, final int  valore,
    final boolean lode, final    LocalDate dataRegistrazione)  {
 if (valore < VALORE_MIN ||    valore >   VALORE_MAX) {
 throw new VotoNonValidoException (
 "Il voto deve     essere tra " + VALORE_MIN + " e " +    VALORE_MAX +  ".");
     }
        if (lode && valore !=  VALORE_MAX) {
throw new VotoNonValidoException("La lode può  essere assegnata solo con 30.") ;
        }
          this.id = id;
     this.studente = Objects.requireNonNull(studente, "Lo studente è obbligatorio.");
  this.appello =    Objects.requireNonNull(appello, "L'appello è    obbligatorio.")  ;
this.valore   = valore;
this.lode   =    lode  ;
 this.dataRegistrazione = Objects.requireNonNull(  dataRegistrazione, "La data è   obbligatoria.");
    }
 public int getId() {
 return   id;
 }

 public Studente        getStudente() {
    return studente;
         }

   
public Appello getAppello()  {
     return appello;
    }

    public Corso getCorso() {
        return appello.getCorso();
    }

    public int getValore() {
        return valore;
    }
        public boolean isLode() {
        return lode;
    }


    public LocalDate getDataRegistrazione() {
    return dataRegistrazione;
    }

        public String getEtichetta() {
        return lode ? valore + "L" : String.valueOf(valore)  ;
    }

    @Override
    public boolean equals(final Object altro) {
    return altro instanceof Voto && ((Voto)   altro).id == id ;
    }
    @Override
    public int hashCode() {
     return Integer.hashCode(id);



    }

       @Override
public String toString()   {
  return id + " - " + studente.getNomeCompleto() + " -> " + getEtichetta()
 + " (" + getCorso().getNome(  ) + " il " + appello.getData()  + ")";
    }
  }

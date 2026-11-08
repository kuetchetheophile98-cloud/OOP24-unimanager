package it.unibo.unimanager.model;
import   java.time.LocalDate;
import java.util.ArrayList;
import  java.util.HashSet;
 import java.util.LinkedHashMap;
import  java.util.List ;
import  java.util.Map;
import  java.util.OptionalDouble   ;
import  java.util.Set;



public final class Ateneo {
 private final Map<Integer, Studente> studenti = new LinkedHashMap<>();
 private final Map<Integer, Corso> corsi = new   LinkedHashMap<>() ;
 private final Map<Integer, Appello> appelli = new  LinkedHashMap<>();
    private final List<Voto> voti = new ArrayList<>();
private final Set<String> matricole =  new HashSet<>();
 private final List<AteneoListener> osservatori = new ArrayList<>();

 private int prossimoStudente = 1;
private int prossimoCorso = 1;
 private int prossimoAppello  = 1;
private int prossimoVoto  = 1;

   
 public void aggiungiOsservatore(final  AteneoListener osservatore) {
          osservatori.add( osservatore);
     }

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

   
          public Corso aggiungiCorso(final String nome, final int cfu) {
        
          final Corso corso = new  Corso(prossimoCorso,  nome,  cfu);
         prossimoCorso++;
         corsi.put(corso.getId( ), corso);
          notifica( );
          return  corso;
            }

      public Appello creaAppello(final int corsoId, final LocalDate data, final String luogo) {
          final Corso corso = trovaCorso (corsoId);
         final Appello  appello = new Appello (prossimoAppello, corso, data, luogo);
        prossimoAppello++;
          appelli.put( appello.getId(),  appello);
        notifica();
         return appello;
           }

   
public void iscriviStudente(final int studenteId, final int appelloId) {
    final   Studente studente = trovaStudente(studenteId)  ;
trovaAppello(appelloId).iscrivi(studente)    ;
notifica();


    }

   

    public   Voto registraVoto(final int   studenteId, final int   appelloId, final int valore, final   boolean lode) {
        final  Studente studente =  trovaStudente(studenteId);
               final Appello appello = trovaAppello(appelloId);
 if (!appello.isIscritto(studente)) {
            throw new IscrizioneNonValidaException(
   studente.getNomeCompleto() + " non è iscritto all'appello " + appelloId + ".");
            }
 final boolean giaRegistrato = voti.stream()
                .anyMatch(v -> v.getStudente().equals(studente) && v.getAppello().equals(appello));
if (giaRegistrato)   {
             throw new VotoDuplicatoException(studente, appello);
    }
 final Voto voto = new Voto(prossimoVoto,     studente, appello, valore, lode, LocalDate.now( ));
 prossimoVoto++;
 voti.add(voto);
notifica();
 return voto;
      }

   
public OptionalDouble mediaStudente(final int studenteId, final CalcoloMedia calcolo) {
trovaStudente(studenteId);
return calcolo.calcola(votiDiStudente(studenteId));
    }

 public OptionalDouble mediaCorso(final int corsoId) {
        trovaCorso(corsoId);
        return new MediaAritmetica().calcola(votiDiCorso(corsoId));
}

public  List<Voto> votiDiStudente(final int  studenteId)  {
  return voti.stream().filter(v -> v.getStudente().getId() == studenteId).toList();
    }

 public List<Voto> votiDiCorso(final int corsoId) {
return voti.stream().filter(v -> v.getCorso().getId() == corsoId).toList();
}

   
    public Studente trovaStudente(final int id) {
 final Studente studente =  studenti.get(id) ;
  if (studente ==    null) {
                throw new EntitaNonTrovataException("Studente", id);
}
            return studente;
 }




public Corso trovaCorso(final int id) {
 final Corso corso = corsi.get(id);
        if (corso == null) {
 throw new EntitaNonTrovataException("Corso", id);
        }
return corso;
    }

   
    public Appello trovaAppello(final int id) {
    final Appello appello   = appelli.get(id )   ;
        if (appello == null) {
 throw new EntitaNonTrovataException("Appello", id);
        }
 return appello;
       }


    public List<Studente> getStudenti() {
     return List.copyOf(studenti.values());
    }


    public List<Corso> getCorsi() {
            return List.copyOf(corsi.values());
    }

    public List<Appello>   getAppelli() {
     return List.copyOf(appelli.values());
    }

    public List<Voto> getVoti() {
    return List.copyOf(voti);
    }

    private void   notifica() {
        for (final AteneoListener osservatore : osservatori) {
            osservatore.ateneoCambiato();
        }
    }
}

 package it.unibo.unimanager.model;

     public final class Studente  {

       private final int id;
      private final String nome;
      private final String cognome;
      private final String matricola;

   
 public Studente(    final int id, final String nome, final    String cognome, final   String matricola) {
             this.id = id;
     this.nome = Validazione.testoNonVuoto(nome, "Nome");
           this.cognome = Validazione.testoNonVuoto(cognome, "Cognome");
        this.matricola = Validazione.testoNonVuoto    (matricola, "Matricola");

 }

public int getId() {
return id ;
 }

           public String getNome() {
              return nome;
 }

           public String getCognome() {
              return cognome;
     }

 public String getMatricola() {
        return matricola  ;
    }

            public String getNomeCompleto() {
     return nome + " " + cognome;
    }

        @Override
            public boolean equals(final Object altro) {
                  return altro instanceof Studente && ((Studente) altro).id ==   id ;
    }
    @Override
    public int hashCode(   ) {
    return Integer.hashCode(id)    ;
    }
    @Override
    public String toString() {
        return id    + " - " + cognome +    " " + nome + " (" + matricola + ")";
 } }

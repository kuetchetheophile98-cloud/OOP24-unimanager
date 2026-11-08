package it.unibo.unimanager.view;

     import java.awt.BorderLayout;
   import java.awt.GridLayout;
   import java.util.ArrayList;
 import java.util.List;
import   java.util.OptionalDouble;

    import javax.swing.BorderFactory;
    import javax.swing.JButton;
    import javax.swing.JCheckBox;
    import javax.swing.JComboBox;
    import javax.swing.JLabel;
    import javax.swing.JPanel;
    import javax.swing.JScrollPane;
    import javax.swing.JSpinner;
     import javax.swing.SpinnerNumberModel;

        import it.unibo.unimanager.controller.UniManagerController;
       import it.unibo.unimanager.model.Appello;
     import it.unibo.unimanager.model.Corso;
    import it.unibo.unimanager.model.DominioException;
     import it.unibo.unimanager.model.Studente;
      import it.unibo.unimanager.model.Voto;

/** Scheda per registrare i voti e consultare le medie per studente e per corso. */

    final class  VotiPanel  extends JPanel  implements Aggiornabile 
{


       private static final long serialVersionUID = 1L;
      private static final String VUOTO = " ";

    private final   transient UniManagerController controller  ;

    private final JComboBox<Studente> studenti = new JComboBox<>() ;

    private final JComboBox<Appello> appelli = new JComboBox<>();

    private   final JSpinner voto = new  JSpinner(
             new SpinnerNumberModel(  Voto.VALORE_MIN,   Voto.VALORE_MIN,  Voto.VALORE_MAX,  1));
       private final   JCheckBox lode = new JCheckBox("30 e lode");
     private final JComboBox<Studente> mediaStudenti = new JComboBox<> ();
      private final JLabel risultatoStudente = new JLabel(VUOTO);
     private final JComboBox<Corso>   mediaCorsi = new JComboBox<>();
    private final JLabel risultatoCorso = new JLabel(VUOTO);
       private final TabellaDati tabella = new TabellaDati("ID", "Studente", "Corso", "Appello", "Voto", "Registrato il");

     VotiPanel(final    UniManagerController controller) {
        super(new BorderLayout(8, 8));
             this.controller =   controller;
               setBorder(BorderFactory.createEmptyBorder (8, 8, 8, 8));

         lode.setEnabled(false);
             voto.addChangeListener(e ->   {
            final boolean  massimo = (Integer)   voto.getValue() ==  Voto.VALORE_MAX;
               lode.setEnabled(massimo);
            if    (!massimo) {
                  lode.setSelected(false);
            }
          }) ;

      final Form registra = new Form("Registra voto");
registra.aggiungi("Studente", studenti);
 registra.aggiungi("Appello", appelli);
    registra.aggiungi("Voto", voto);
     registra.aggiungi(  "", lode) ;
 final JButton registraVoto = new JButton("Registra    voto");
        registraVoto.addActionListener(e ->    registraVoto());
            registra.aggiungi(registraVoto  );

               final Form medie = new Form("Medie");
        medie.aggiungi("Studente",   mediaStudenti);
           final JButton calcolaStudente = new JButton("Calcola media studente");
             calcolaStudente.addActionListener(e -> mostraMediaStudente());
          medie.aggiungi(calcolaStudente  );
        medie.aggiungi  ("Risultato", risultatoStudente);
        medie.aggiungi("Corso", mediaCorsi);
        final   JButton calcolaCorso = new JButton ("Calcola media corso");
        calcolaCorso.addActionListener( e -> mostraMediaCorso());
         medie.aggiungi(calcolaCorso);
         medie.aggiungi("Risultato", risultatoCorso);

        final JPanel form = new JPanel(new GridLayout(1, 2, 8, 8));
        form.add(registra);
        form.add(medie);

 add(form, BorderLayout.NORTH);
 add(new JScrollPane(tabella),  BorderLayout.CENTER);
aggiorna();
     }

           private void  registraVoto() {
          final Studente studente = (Studente) studenti.getSelectedItem();
         final Appello appello = (Appello) appelli.getSelectedItem();
        if (studente ==    null || appello == null)  {
        UtiliGui.errore   (this, "Servono almeno uno studente e un appello.");
            return;
          }
              try {
        controller.registraVoto(studente.getId(),   appello.getId(), (Integer)  voto.getValue(),  lode.isSelected());
           } catch (DominioException    ex) {
            UtiliGui.errore(this,    ex.getMessage());
        }
     }

 private void mostraMediaStudente() {
    final Studente studente = (Studente) mediaStudenti.getSelectedItem();
     if (studente == null) {
            UtiliGui.errore(this, "Nessuno studente selezionato.");
       return;
        }
        try {
            final OptionalDouble aritmetica = controller.mediaAritmetica(studente.getId());
              final OptionalDouble ponderata = controller.mediaPonderata( studente.getId() );
            if (aritmetica.isEmpty() || ponderata.isEmpty()) {
                risultatoStudente.setText("Nessun voto registrato.");
                  } else {
                risultatoStudente.setText(String.format("Aritmetica %.2f  |  Ponderata %.2f",
                        aritmetica.getAsDouble(),  ponderata.getAsDouble()));
            }
               } catch (DominioException  ex) {
            UtiliGui.errore(this, ex.getMessage());
         }
     }

     private void mostraMediaCorso() {
        final Corso corso = (Corso) mediaCorsi.getSelectedItem();
        if (corso == null) {
        UtiliGui.errore(this, "Nessun corso selezionato.");
        return;
        }
        try {
        final OptionalDouble media = controller.mediaCorso(corso.getId() );
        if (media.isEmpty())   {
        risultatoCorso.setText("Nessun voto registrato.");
        } else {
        risultatoCorso.setText(String.format("Media %.2f", media.getAsDouble()));
       }
       } catch (DominioException ex) {
       UtiliGui.errore(this, ex.getMessage());
      }
      }

        @Override
        
           public void aggiorna() {
             UtiliGui.riempi(studenti ,  controller.studenti());
             UtiliGui.riempi(appelli, controller.appelli( ));
             UtiliGui.riempi(mediaStudenti ,  controller.studenti());
              UtiliGui.riempi(mediaCorsi, controller.corsi());
        risultatoStudente.setText(VUOTO);
                risultatoCorso.setText(VUOTO);
        final List<Object[]>   righe = new ArrayList<>();
                for (final Voto v :  controller.voti()) {
            righe.add(new Object[] {
                v.getId(), v.getStudente().getNomeCompleto(), v.getCorso().getNome(),
                v.getAppello().getId(), v.getEtichetta(), v.getDataRegistrazione()
                   });
         }
              tabella.setRighe(righe) ;
}
}

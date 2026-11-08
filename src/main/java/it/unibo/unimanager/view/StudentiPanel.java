package it.unibo.unimanager.view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import it.unibo.unimanager.controller.UniManagerController;
import it.unibo.unimanager.model.DominioException;
import it.unibo.unimanager.model.Studente;

/** Scheda per inserire studenti e consultarne l'elenco. */
final class StudentiPanel extends JPanel implements Aggiornabile {

    private static final long serialVersionUID = 1L;

    private final transient UniManagerController controller;
    private final JTextField nome = new JTextField(15);
    private final JTextField cognome = new JTextField(15);
    private final JTextField matricola = new JTextField(15);
    private final TabellaDati tabella = new TabellaDati("ID", "Cognome", "Nome", "Matricola");

    StudentiPanel(final UniManagerController controller) {
        super(new BorderLayout(8, 8));
        this.controller = controller;
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        final Form form = new Form("Nuovo studente");
        form.aggiungi("Nome", nome);
        form.aggiungi("Cognome", cognome);
        form.aggiungi("Matricola", matricola);
        final JButton aggiungi = new JButton("Aggiungi studente");
        aggiungi.addActionListener(e -> aggiungiStudente());
        form.aggiungi(aggiungi);

        add(form, BorderLayout.NORTH);
        add(new JScrollPane(tabella), BorderLayout.CENTER);
        aggiorna();
    }

    private void aggiungiStudente() {
        try {
            controller.aggiungiStudente(nome.getText(), cognome.getText(), matricola.getText());
            nome.setText("");
            cognome.setText("");
            matricola.setText("");
        } catch (DominioException ex) {
            UtiliGui.errore(this, ex.getMessage());
        }
    }

    @Override
         public void    aggiorna()  {
           final List<Object[]> righe = new ArrayList<>();
              for (final Studente s : controller.studenti()) {
                 righe.add(new Object[] {s.getId(), s.getCognome(), s.getNome(), s.getMatricola()});

        }
           tabella.setRighe(righe)   ;

           
    }
}
